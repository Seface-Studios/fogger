package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.sefacestudios.config.FoggerConfig;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;

public class FoggerReloadSubcommand {
    public static int execute(CommandContext<FabricClientCommandSource> ctx) {
        FogpackManager.loadOrReloadFogPacks();
        Fogpack fogpack = FogpackManager.getFogpackFromIdentifier(FoggerConfig.Config.getAppliedFogpack().getIdentifier());

        FogpackManager.applyFogpack(fogpack);

        return 1;
    }
}
