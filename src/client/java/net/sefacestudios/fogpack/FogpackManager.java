package net.sefacestudios.fogpack;

import com.google.common.io.Resources;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.MinecraftClient;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.config.FoggerConfig;
import net.sefacestudios.utils.FoggerUtils;
import net.sefacestudios.utils.FogpacksSource;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class FogpackManager {
    public static final String VANILLA_FOGPACK = "minecraft:vanilla";
    private static final String FOGPACK_SUFFIX = ".fogpack.json";
    
    @Getter private static Fogpack appliedFogpack = null;

    @Getter private static Collection<Fogpack> loadedFogpacks = new ArrayList<>();

    public FogpackManager() {}

    @SneakyThrows
    public void loadOrReloadFogpacks() {
        loadedFogpacks = new ArrayList<>();
        File gameDirFogpacksFolder = FoggerConfig.FOGPACK_PATH.toFile();

        URI resourcesURI = Resources.getResource("assets/fogger/fogpacks/").toURI();
        File builtInFogpacksFolder = new File(resourcesURI);

        this
           .loadOrReloadFogpacksFrom(builtInFogpacksFolder, FogpacksSource.RESOURCES)
           .loadOrReloadFogpacksFrom(gameDirFogpacksFolder, FogpacksSource.GAME_DIRECTORY);
    }

    public FogpackManager loadOrReloadFogpacksFrom(File directory, FogpacksSource source) {
        if (!directory.isDirectory()) {
            Fogger.LOGGER.warn("The path " + directory + " is not a directory!");
            return this;
        }

        File[] fogpacks = directory.listFiles((dir, name) -> name.endsWith(FogpackManager.FOGPACK_SUFFIX));

        if (fogpacks == null) {
            Fogger.LOGGER.warn("No fogpacks were found in the directory: " + directory);
            return this;
        }

        for (File fogpackFile : fogpacks) {
            Fogpack fogpack = FoggerUtils.getData(Fogpack.class, fogpackFile.toPath());
            fogpack.setPath(fogpackFile.getPath());

            if (isFogpackLoaded(fogpack.getIdentifier())) {
                Fogger.LOGGER.warn("A Fogpack with the identifier \""+ fogpack.getIdentifier() +"\" has already been loaded. Ignoring...");
                continue;
            }

            loadedFogpacks.add(fogpack);
        }

        return this;
    }

    /**
     * Checks a Fogpack with specific identifier are loaded.
     * @param identifier The Fogpack identifier.
     * @return true if some Fogpack with parsed identifier are loaded.
     */
    public static boolean isFogpackLoaded(String identifier) {
        for (Fogpack loadedFogpack : loadedFogpacks) {
            if (loadedFogpack.getIdentifier().equals(identifier)) {
                return true;
            }
        }

        return false;
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
        Fogpack fp = fogpack;

        if (fogpack == null) {
            fp = FogpackManager.getFogpackFromIdentifier(VANILLA_FOGPACK);
            Fogger.LOGGER.warn("The attempt to apply a Fogpack failed. Applying the \"" + VANILLA_FOGPACK + "\" instead.");
        }

        appliedFogpack = fp;

        FoggerClient.getConfig().setAppliedFogpack(fp);

        /*if (!ignoreReload && (FoggerConfig.Config.getLatestWaterColor() != fp.getConfig().getWater().getColor())) {
            MinecraftClient.getInstance().reloadResources();
        }*/

        FoggerClient.getConfig().setLatestWaterColor(fp.getConfig().getWater().getColor());
    }

    @Nullable
    public static Fogpack getFogpackFromIdentifier(String identifier) {
        for (Fogpack fogPack : loadedFogpacks) {
            if (fogPack.getIdentifier().equals(identifier)) return fogPack;
        }

        return null;
    }
}
