package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.config.FoggerConfig;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;

import java.util.Set;

public class FoggerReloadSubcommand {
    public static int execute(CommandContext<FabricClientCommandSource> ctx) {
        ClientPlayerEntity player = ctx.getSource().getPlayer();

        Fogpack fogpack = FogpackManager.getFogpackFromIdentifier(FoggerConfig.Config.getAppliedFogpackInstance().getIdentifier());
        FoggerClient.getFogpackManager().loadOrReloadFogpacks();
        FogpackManager.applyFogpack(fogpack);

        player.sendMessage(
                Fogger.MESSAGES_PREFIX.copy().append(
                        Text.translatable("commands.fogger.reload.success")
                )
        );

        return 1;
    }
}
