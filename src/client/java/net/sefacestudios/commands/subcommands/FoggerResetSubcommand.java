package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.sefacestudios.config.FoggerConfig;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;

public class FoggerResetSubcommand {
    public static int execute(CommandContext<FabricClientCommandSource> ctx) {
        FogpackManager.applyFogpack(FogpackManager.VANILLA_FOG_PACK);
        return 1;
    }
}
