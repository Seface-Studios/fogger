package net.sefacestudios;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Fogger implements ModInitializer {
    public static final String MOD_ID = "fogger";
    public static final String MOD_NAME = "Fogger";
    public static final Logger LOGGER = LoggerFactory.getLogger("Fogger");
    public static final Path FOGPACKS_FOLDER_PATH = FabricLoader.getInstance().getGameDir().resolve("fogpacks");
    public static final MutableText MESSAGES_PREFIX = Text.literal("\uE100 | ");

    @Override
    public void onInitialize() {}
}
