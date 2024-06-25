package net.sefacestudios;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Fogger implements ModInitializer {
    public static final String MOD_ID = "fogger";
    public static final String MOD_NAME = "Fogger";
    public static final Logger LOGGER = LoggerFactory.getLogger("Fogger");
    public static final Path FOGPACKS_FOLDER_PATH = FabricLoader.getInstance().getGameDir().resolve("fogpacks");

    @Override
    public void onInitialize() {}
}
