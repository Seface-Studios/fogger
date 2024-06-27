package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sefacestudios.Fogger;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;

public class FoggerUseSubcommand {
    public static int execute(CommandContext<FabricClientCommandSource> ctx) {
        ClientPlayerEntity player = ctx.getSource().getPlayer();
        String identifier = ctx.getArgument("fogpack", String.class);
        Fogpack fogpack = FogpackManager.getFogpackFromIdentifier(identifier);

        if (fogpack == null) {
            ctx.getSource().getPlayer().sendMessage(
                    Fogger.MESSAGES_PREFIX.copy().append(
                                    Text.translatable("commands.fogger.use.failed.fogpackDoesNotExist", identifier)
                            )
            );

            return 1;
        }

        FogpackManager.applyFogpack(fogpack);

        player.sendMessage(
                Fogger.MESSAGES_PREFIX.copy().append(
                        Text.translatable("commands.fogger.use.success", fogpack.getIdentifier())
                )
        );

        return 1;
    }
}
