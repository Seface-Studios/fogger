package net.sefacestudios.fogpack;

import com.google.common.io.Resources;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.config.FoggerConfig;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class FogpackManager {
    public static final String VANILLA_FOG_PACK = "fogger:vanilla";
    private static final String FOG_PACK_SUFFIX = ".fogpack.json";
    
    @Getter private static Fogpack appliedFogpack = null;
    @Getter private static Collection<Fogpack> loadedFogPacks;

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

    public static void addBuiltInFogpacks() {
        String vanillaFogpack = Resources.getResource("assets/fogger/fogpacks/vanilla.fogpack.json").getPath();

        try (Reader reader = new FileReader(vanillaFogpack)) {
            loadedFogPacks.add(
                    FoggerClient.GSON.fromJson(reader, Fogpack.class)
                        .setPath(vanillaFogpack)
            );
        } catch (IOException e) {}
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

        if (!ignoreReload) {
            MinecraftClient.getInstance().reloadResources();
        }
    }

    @Nullable
    public static Fogpack getFogpackFromIdentifier(String identifier) {
        for (Fogpack fogPack : loadedFogPacks) {
            if (fogPack.getIdentifier().equals(identifier)) return fogPack;
        }

        return null;
    }
}
