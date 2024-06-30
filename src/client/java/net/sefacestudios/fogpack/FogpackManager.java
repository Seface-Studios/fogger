package net.sefacestudios.fogpack;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.sefacestudios.Fogger;
import net.sefacestudios.utils.FoggerUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class FogpackManager {
  public static final String VANILLA_FOGPACK = "minecraft:vanilla";
  private static final String FOGPACK_SUFFIX = ".fogpack.json";

  @Getter
  private Fogpack appliedFogpackInstance = null;

  @Getter
  private static Collection<Fogpack> loadedFogpacks;

  @Getter
  private final transient Configuration configuration;

  public FogpackManager() {
    this.createFogpacksDirectory();
    this.loadOrReloadFogpacks();
    this.configuration = FoggerUtils.getData(Configuration.class, Fogger.FOGPACK_CONFIG_PATH);
    this.appliedFogpackInstance = this.getFogpackFromIdentifier(this.configuration.getAppliedFogpack());
  }

  @Getter
  public static class Configuration {
    @SerializedName("applied_fogpack")
    private String appliedFogpack = VANILLA_FOGPACK;

    @SerializedName("latest_water_color")
    private int latestWaterColor = 0;

    public void setAppliedFogpack(Fogpack fogpack) {
      this.appliedFogpack = fogpack.getIdentifier();
      FoggerUtils.createOrUpdate(Configuration.class, Fogger.FOGPACK_CONFIG_PATH, this, true);
    }

    public void setLatestWaterColor(Fogpack fogpack) {
      Integer color = fogpack.getConfig().getWater().getColor();
      this.latestWaterColor = color != null ? color : 0;
      FoggerUtils.createOrUpdate(Configuration.class, Fogger.FOGPACK_CONFIG_PATH, this, true);
    }
  }

  public void onClientStarted() {
    FoggerUtils.createOrUpdate(Configuration.class, Fogger.FOGPACK_CONFIG_PATH);
    this.applyFogpack(this.configuration.appliedFogpack, true);
  }

  @SneakyThrows
  public void loadOrReloadFogpacks() {
    loadedFogpacks = new ArrayList<>();
    File gameDirFogpacksFolder = Fogger.GAMEDIR_FOGPACKS_PATH.toFile();

    this
      .registerBuiltInFogpack("vanilla")
      .registerBuiltInFogpack("blue_world")
      .registerBuiltInFogpack("elden_ring")
      .registerBuiltInFogpack("miami_vibes")
      .registerBuiltInFogpack("swamp_lands")
      .loadOrReloadFogpacksFrom(gameDirFogpacksFolder)
      .printBuiltInFogpacks();
  }

  public FogpackManager registerBuiltInFogpack(String identifierPath) {
    String builtIn = "/fogpacks/" + identifierPath + FOGPACK_SUFFIX;

    try (InputStream stream = Fogger.class.getResourceAsStream(builtIn)) {
      if (stream == null) {
        Fogger.LOGGER.warn("The path " + builtIn + " is not a directory!");
        return this;
      }

      String jsonText = new BufferedReader(new InputStreamReader(stream))
        .lines().collect(Collectors.joining("\n"));

      Fogpack fogpack = Fogger.GSON.fromJson(jsonText, Fogpack.class);
      fogpack.setDescription(
        fogpack.getDescription()
          .concat(" (" + Text.translatable("pack.source.builtin").getString() + ")")
      );

      fogpack.setBuiltIn(true);

      loadedFogpacks.add(fogpack);
    } catch (Exception ignored) {}

    return this;
  }

  public FogpackManager loadOrReloadFogpacksFrom(File directory) {
    if (!directory.isDirectory()) {
      Fogger.LOGGER.warn("The path " + directory + " is not a directory!");
      return this;
    }

    File[] fogpacks = directory.listFiles((dir, name) -> name.endsWith(FogpackManager.FOGPACK_SUFFIX));

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

  public void applyFogpack(String identifier) {
    this.applyFogpack(identifier, false);
  }

  public void applyFogpack(String identifier, boolean ignoreReload) {
    for (Fogpack fogpack : getLoadedFogpacks()) {
      if (fogpack.getIdentifier().equals(identifier)) {
        this.applyFogpack(fogpack, ignoreReload);
      }
    }
  }

  public void applyFogpack(Fogpack fogpack) {
    this.applyFogpack(fogpack, false);
  }

  public void applyFogpack(Fogpack fogpack, boolean ignoreReload) {
    this.appliedFogpackInstance = fogpack;
    this.configuration.setAppliedFogpack(fogpack);

    if (!ignoreReload) {
      MinecraftClient.getInstance().reloadResources();
    }

    this.configuration.setLatestWaterColor(fogpack);
  }

  @Nullable
  public Fogpack getFogpackFromIdentifier(String identifier) {
    for (Fogpack fogPack : loadedFogpacks) {
      if (fogPack.getIdentifier().equals(identifier)) return fogPack;
    }

    return null;
  }

  private void createFogpacksDirectory() {
    try {
      if (Files.notExists(Fogger.GAMEDIR_FOGPACKS_PATH)) {
        Files.createDirectories(Fogger.GAMEDIR_FOGPACKS_PATH);
        Fogger.LOGGER.info("The fogpacks folder was not identified within the game directory. Creating one...");
      }
    } catch (IOException ignored) {}
  }

  private void printBuiltInFogpacks() {
    Fogger.LOGGER.info("The following Fogpacks were loaded internally (built-in).");

    for (Fogpack fogpack : getLoadedFogpacks()) {
      if (!fogpack.isBuiltIn()) continue;

      Fogger.LOGGER.info("  - " + fogpack.getIdentifier() + "@v" + fogpack.getVersion());
    }
  }
}
