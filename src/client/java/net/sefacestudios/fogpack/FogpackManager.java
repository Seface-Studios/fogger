package net.sefacestudios.fogpack;

import com.google.common.io.Resources;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.MinecraftClient;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.config.FoggerConfig;
import net.sefacestudios.utils.FoggerUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class FogpackManager {
    public static final String VANILLA_FOGPACK = "minecraft:vanilla";
    private static final String FOG_PACK_SUFFIX = ".fogpack.json";
    
    @Getter private static Fogpack appliedFogpack = null;
    @Getter private static Collection<Fogpack> loadedFogpacks;

    public FogpackManager() {}

    @SneakyThrows
    public void loadOrReloadFogpacks() {
        loadedFogpacks = new ArrayList<>();
        File gameDirFogpacksFolder = FoggerConfig.FOGPACK_PATH.toFile();

        URI resourcesURI = Resources.getResource("assets/fogger/fogpacks/").toURI();
        File builtInFogpacksFolder = new File(resourcesURI);

        this
          .loadOrReloadFogpacksFrom(gameDirFogpacksFolder)
          .loadOrReloadFogpacksFrom(builtInFogpacksFolder);
    }

    public FogpackManager loadOrReloadFogpacksFrom(File directory) {
        if (!directory.isDirectory()) {
            Fogger.LOGGER.warn("The path " + directory + " is not a directory!");
            return this;
        }

        File[] fogpacks = directory.listFiles((dir, name) -> name.endsWith(FogpackManager.FOG_PACK_SUFFIX));

        if (fogpacks == null) {
            Fogger.LOGGER.warn("No fogpacks were found in the directory: " + directory);
            return this;
        }

        for (File fogpack : fogpacks) {
            loadedFogpacks.add(
                    FoggerUtils.getData(Fogpack.class, fogpack.toPath())
            );
        }

        return this;
    }




    public static void applyFogpack(Fogpack fogpack) {
        applyFogpack(fogpack, false);
    }

    public static void applyFogpack(String identifier) {
        for (Fogpack fogpack : getLoadedFogpacks()) {
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
        for (Fogpack fogPack : loadedFogpacks) {
            if (fogPack.getIdentifier().equals(identifier)) return fogPack;
        }

        return null;
    }
}
