package net.sefacestudios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

public class Fogger implements ModInitializer {
  public static final String MOD_ID = "fogger";
  public static final String MOD_NAME = "Fogger";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
  public static final MutableText MESSAGES_PREFIX = Text.literal("Fogger » ");

  /* PATHS */
  public static final Path FOGPACK_CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fogger.json");
  public static final Path GAMEDIR_FOGPACKS_PATH = FabricLoader.getInstance().getGameDir().resolve("fogpacks");

  public static final Gson GSON = new GsonBuilder()
    .setPrettyPrinting()
    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
    .create();

  @Override
  public void onInitialize() {}
}
