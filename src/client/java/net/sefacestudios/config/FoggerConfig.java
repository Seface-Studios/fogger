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
import net.sefacestudios.Fogger;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.utils.FoggerUtils;
import net.sefacestudios.utils.SavedPositions;

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

        private static transient final Config data = FoggerUtils.getData(FoggerConfig.Config.class, FOGPACK_CONFIG_PATH);

        @Getter
        @SerializedName("saved_positions")
        private ArrayList<SavedPositions> savedPositions = new ArrayList<>();

        public void setAppliedFogpack(Fogpack fogpack) {
            this.appliedFogpack = fogpack.getIdentifier();
            FoggerUtils.createOrUpdate(FoggerConfig.Config.class, FOGPACK_CONFIG_PATH, this, true);
        }

        public void setLatestWaterColor(int color) {
            this.latestWaterColor = color;
            FoggerUtils.createOrUpdate(FoggerConfig.Config.class, FOGPACK_CONFIG_PATH, this, true);
        }

        public static Fogpack getAppliedFogpack() {
            return FogpackManager.getFogpackFromIdentifier(data.appliedFogpack);
        }

        public static int getLatestWaterColor() { return data.latestWaterColor; }
    }

    public static void initialize() {
        FoggerUtils.createOrUpdate(FoggerConfig.Config.class, FOGPACK_CONFIG_PATH);
        FogpackManager.applyFogpack(FoggerConfig.Config.getAppliedFogpack(), true);
    }
}