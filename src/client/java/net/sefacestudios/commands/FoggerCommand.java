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

package net.sefacestudios.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.sefacestudios.commands.subcommands.*;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.fogpack.FogpackProviders;
import org.jetbrains.annotations.Nullable;

public class FoggerCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, @Nullable CommandRegistryAccess registryAccess) {
        dispatcher.register(
            ClientCommandManager.literal("fogger")
                    .then(ClientCommandManager.literal("use")
                            .then(ClientCommandManager.argument("fogpack", StringArgumentType.greedyString())
                                    .suggests(FogpackProviders::getUseSuggestions)
                                    .executes(FoggerUseSubcommand::execute)
                            )
                    )

                    // GENERATE SUBCOMMAND
                    .then(ClientCommandManager.literal("generate")
                            .then(ClientCommandManager.argument("identifier", IdentifierArgumentType.identifier())
                                    .then(ClientCommandManager.argument("name", StringArgumentType.greedyString())
                                            .executes(FoggerGenerateSubcommand::execute)
                                    )
                            )
                    )

                    .then(ClientCommandManager.literal("info")
                            .executes(FoggerInfoSubcommand::execute)
                    )

                    // RELOAD SUBCOMMAND
                    .then(ClientCommandManager.literal("reload")
                            .executes(FoggerReloadSubcommand::execute)
                    )

                    // RESET SUBCOMMAND
                    .then(ClientCommandManager.literal("reset")
                            .executes(FoggerResetSubcommand::execute)
                    )
        );
    }
}
