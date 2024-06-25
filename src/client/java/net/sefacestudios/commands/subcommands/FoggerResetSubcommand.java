package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.sefacestudios.fogpack.FogpackManager;

public class FoggerResetSubcommand {
    public static int execute(CommandContext<FabricClientCommandSource> ctx) {
        FogpackManager.applyFogpack(FogpackManager.VANILLA_FOGPACK);
        return 1;
    }
}
