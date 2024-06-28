package net.sefacestudios.utils;

import lombok.SneakyThrows;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FoggerUtils {

  /**
   * Get deserialized data from a specific JSON file.
   * @param clazz Instance of the Class to be used on deserialization.
   * @param path Where the JSON file are located.
   * @return The deserialized JSON file.
   */
  @SneakyThrows
  public static <T> T getData(Class<T> clazz, Path path) {
    try (Reader reader = new FileReader(path.toFile())) {
      return Fogger.GSON.fromJson(reader, clazz);
    } catch (IOException err) {
      Fogger.LOGGER.warn("Error when trying to read the file: " + path + " | " + err);
    }

    return clazz.getConstructor().newInstance();
  }

  /**
   *
   * @param clazz Instance of the Class to be used on deserialization.
   * @param path Where the JSON file should be saved.
   */
  public static <T> void createOrUpdate(Class<T> clazz, Path path) {
    createOrUpdate(clazz, path, null, false);
  }

  /**
   *
   * @param clazz Instance of the Class to be used on deserialization.
   * @param path Where the JSON file should be saved.
   * @param clazzThis Only has effect with update parameter. A "this" class should be parsed to apply the update on
   *                  the correct class instance. This can be null when using this method to create new files.
   * @param update If true, the file will be updated with the new values.
   */
  @SneakyThrows
  public static <T> void createOrUpdate(Class<T> clazz, Path path, @Nullable T clazzThis, boolean update) {
    if (Files.exists(path) && !update) return;

    if (!path.getParent().toFile().exists()) {
      path.getParent().toFile().mkdirs();
    }

    T instance = update && clazzThis != null ? clazzThis : clazz.getConstructor().newInstance();

    try (FileWriter writer = new FileWriter(path.toFile())) {
      Fogger.GSON.toJson(instance, writer);
    } catch (IOException err) {
      String action = update ? "update" : "create";
      String baseWarn = "Error when trying to %ACTION% the file: ".replace("%ACTION%", action);
      Fogger.LOGGER.warn(baseWarn + path + " | " + err);
    }
  }
}
