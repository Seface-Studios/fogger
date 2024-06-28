package net.sefacestudios.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.sefacestudios.commands.subcommands.*;
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

        // INFO SUBCOMMAND
        .then(ClientCommandManager.literal("info").executes(FoggerInfoSubcommand::execute))

        // RELOAD SUBCOMMAND
        .then(ClientCommandManager.literal("reload").executes(FoggerReloadSubcommand::execute))

        // RESET SUBCOMMAND
        .then(ClientCommandManager.literal("reset").executes(FoggerResetSubcommand::execute))
    );
  }
}
