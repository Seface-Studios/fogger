/*
 * This file is part of the Kaleidoscope project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024 1024_byteeeee and contributors
 *
 * Kaleidoscope is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kaleidoscope is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Kaleidoscope. If not, see <https://www.gnu.org/licenses/>.
 */

package net.sefacestudios.config;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import net.fabricmc.loader.api.FabricLoader;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class FoggerConfig {

    public static final Path FOGPACK_PATH = FabricLoader.getInstance().getGameDir().resolve("fogpacks");
    public static final Path FOGPACK_CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fogger.json");


    //private static final String CONFIG_FILE = FOGPACK_PATH.resolve("colors.json").toString();

    public static class Config {
        @SerializedName("applied_fogpack")
        private String appliedFogpack = "fogger:vanilla";

        public static void createOrUpdate(boolean force, FoggerConfig.Config instance) {
            if (Files.exists(FOGPACK_CONFIG_PATH) && !force) return;

            try (FileWriter writer = new FileWriter(FOGPACK_CONFIG_PATH.toFile())) {
                FoggerClient.GSON.toJson(instance, writer);
            } catch (IOException e) {}
        }

        public void setAppliedFogpack(Fogpack fogpack) {
            this.appliedFogpack = fogpack.getIdentifier();
            createOrUpdate(true, this);
        }

        public static FoggerConfig.Config getAppliedFogpack() {
            try (Reader reader = new FileReader(FOGPACK_CONFIG_PATH.toFile())) {
                return FoggerClient.GSON.fromJson(reader, FoggerConfig.Config.class);
            } catch (IOException e) {}

            return null;
        }
    }

    public static void initialize() {
        FoggerConfig.Config.createOrUpdate(false, new FoggerConfig.Config());
    }



    public static void loadFromConfig() {
        //MinecraftClient.getInstance().options.load();
        //Fogger.LOGGER.info(client.player.getName().toString());
        /*createConfigPath();
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            ConfigData config = gson.fromJson(reader, ConfigData.class);
            if (config != null) {
                loadConfig(config);
            }
        } catch (IOException e) {
            FoggerClient.LOGGER.warn("Failed to load configuration" + e);
        }*/
    }

    /*public static void saveToConfig() {
        createConfigPath();
        ConfigData config = new ConfigData();
        saveConfig(config);
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            FoggerClient.LOGGER.warn("Failed to save configuration" + e);
        }
    }*/

    /*private static void createConfigPath() {
        try {
            if (!Files.exists(FOGPACK_PATH)) {
                Files.createDirectories(FOGPACK_PATH);
                Files.createFile(FOGPACK_PATH.resolve("colors.json"));
            }
        } catch (IOException e) {
            FoggerClient.LOGGER.warn("Failed to creat configuration" + e);
        }
    }*/

    /*private static void loadConfig(ConfigData config) {
        skyConfigData.loadFromConfig(config);
        fogConfigData.loadFromConfig(config);
        waterConfigData.loadFromConfig(config);
        waterFogConfigData.loadFromConfig(config);
        blockOutlineConfigData.loadFromConfig(config);
    }

    private static void saveConfig(ConfigData config) {
        skyConfigData.saveToConfig(config);
        fogConfigData.saveToConfig(config);
        waterConfigData.saveToConfig(config);
        waterFogConfigData.saveToConfig(config);
        blockOutlineConfigData.saveToConfig(config);
    }

    public static class ConfigData {
        public SkyConfigData skyConfigData = new SkyConfigData();
        public FogConfigData fogConfigData = new FogConfigData();
        public WaterConfigData waterConfigData = new WaterConfigData();
        public WaterFogConfigData waterFogConfigData = new WaterFogConfigData();
        public BlockOutlineConfigData blockOutlineConfigData = new BlockOutlineConfigData();
    }*/
}