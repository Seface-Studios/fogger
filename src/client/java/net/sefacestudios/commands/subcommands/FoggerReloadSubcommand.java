package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class FoggerReloadSubcommand {
    public static int execute(CommandContext<FabricClientCommandSource> ctx) {
        ctx.getSource().getPlayer().sendMessage(Text.literal("Testando subcommand."));
        return 1;
    }
}
