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

package net.sefacestudios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.util.ScreenshotRecorder;
import net.sefacestudios.config.FoggerConfig;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.registry.FoggerCommands;
import net.sefacestudios.event.ClientStartEvent;
import net.sefacestudios.registry.FoggerKeybinds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Modifier;

public class FoggerClient implements ClientModInitializer {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create();

    @Getter
    private static final FogpackManager fogpackManager = new FogpackManager();

    @Getter
    public static final FoggerConfig.Config config = new FoggerConfig.Config();

    @Override
    public void onInitializeClient() {
        getFogpackManager().loadOrReloadFogpacks();

        FoggerKeybinds.register();
        FoggerCommands.register();

        ClientStartEvent.event();
    }
}