package net.sefacestudios.fogpack;

import com.google.common.io.Resources;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.MinecraftClient;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.config.FoggerConfig;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class FogpackManager {
    public static final String VANILLA_FOGPACK = "minecraft:vanilla";
    private static final String FOG_PACK_SUFFIX = ".fogpack.json";
    
    @Getter private static Fogpack appliedFogpack = null;
    @Getter private static Collection<Fogpack> loadedFogPacks;

    @SneakyThrows
    public static void loadOrReloadFogPacks() {
        loadedFogPacks = new ArrayList<>();
        addBuiltInFogpacks();

        File fogPacksFolder = FoggerConfig.FOGPACK_PATH.toFile();

        if (!fogPacksFolder.isDirectory()) return;

        File[] fogPacks = fogPacksFolder.listFiles((dir, name) -> name.endsWith(FogpackManager.FOG_PACK_SUFFIX));

        if (fogPacks == null) return;

        for (File fogPack : fogPacks) {
            String fogPackPath = fogPack.getPath();

            try (Reader reader = new FileReader(fogPackPath)) {
                Fogpack fogPackConfig = FoggerClient.GSON.fromJson(reader, Fogpack.class)
                        .setPath(fogPackPath);

                loadedFogPacks.add(fogPackConfig);
            } catch (IOException ignored) {}
        }
    }

    @SneakyThrows
    public static void addBuiltInFogpacks() {
        URI resourcesURI = Resources.getResource("assets/fogger/fogpacks/").toURI();
        File resourcesFolder = new File(resourcesURI);

        if (!resourcesFolder.isDirectory()) return;

        File[] fogPacks = resourcesFolder.listFiles();
        if (fogPacks == null) return;

        for (File fogPack : fogPacks) {
            String fogPackPath = fogPack.getPath();

            try (Reader reader = new FileReader(fogPackPath)) {
                loadedFogPacks.add(
                        FoggerClient.GSON.fromJson(reader, Fogpack.class)
                                .setPath(fogPackPath)
                );
            } catch (IOException e) {}
        }
    }

    public static void applyFogpack(Fogpack fogpack) {
        applyFogpack(fogpack, false);
    }

    public static void applyFogpack(String identifier) {
        for (Fogpack fogpack : getLoadedFogPacks()) {
            if (fogpack.getIdentifier().equals(identifier)) {
                applyFogpack(fogpack);
            }
        }
    }

    public static void applyFogpack(Fogpack fogpack, boolean ignoreReload) {
        appliedFogpack = fogpack;

        FoggerClient.getConfig().setAppliedFogpack(fogpack);

        if (!ignoreReload && FoggerConfig.Config.getLatestWaterColor() != fogpack.getConfig().getWater().getColor()) {
            MinecraftClient.getInstance().reloadResources();
        }

        FoggerClient.getConfig().setLatestWaterColor(fogpack.getConfig().getWater().getColor());
    }

    @Nullable
    public static Fogpack getFogpackFromIdentifier(String identifier) {
        for (Fogpack fogPack : loadedFogPacks) {
            if (fogPack.getIdentifier().equals(identifier)) return fogPack;
        }

        return null;
    }
}
