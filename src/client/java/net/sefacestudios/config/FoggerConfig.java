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
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.utils.SavedPositions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FoggerConfig {
    public static final Path FOGPACK_PATH = FabricLoader.getInstance().getGameDir().resolve("fogpacks");
    public static final Path FOGPACK_CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fogger.json");

    public static class Config {
        @SerializedName("applied_fogpack")
        private String appliedFogpack = FogpackManager.VANILLA_FOGPACK;

        @SerializedName("latest_water_color")
        private int latestWaterColor = 0;

        @Getter
        @SerializedName("saved_positions")
        private ArrayList<SavedPositions> savedPositions = new ArrayList<>();

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

        public void setLatestWaterColor(int color) {
            this.latestWaterColor = color;
            createOrUpdate(true, this);
        }

        private static Config getData() {
            try (Reader reader = new FileReader(FOGPACK_CONFIG_PATH.toFile())) {
                return FoggerClient.GSON.fromJson(reader, Config.class);
            } catch (IOException e) {}

            return null;
        }

        public static Fogpack getAppliedFogpack() {
            Config config = getData();
            return FogpackManager.getFogpackFromIdentifier(config.appliedFogpack);
        }

        public static int getLatestWaterColor() {
            FoggerClient.LOGGER.info(getData().latestWaterColor);
            return getData().latestWaterColor;
        }
    }

    public static void initialize() {
        FoggerConfig.Config.createOrUpdate(false, new FoggerConfig.Config());



        FogpackManager.applyFogpack(FoggerConfig.Config.getAppliedFogpack(), true);
    }
}