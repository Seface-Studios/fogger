package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;


public class FoggerReloadSubcommand {
  public static int execute(CommandContext<FabricClientCommandSource> ctx) {
    ClientPlayerEntity player = ctx.getSource().getPlayer();

    Fogpack fogpack = FoggerClient.getFogpackManager().getFogpackFromIdentifier(FoggerClient.getFogpackManager().getAppliedFogpackInstance().getIdentifier());
    FoggerClient.getFogpackManager().loadOrReloadFogpacks();
    FoggerClient.getFogpackManager().applyFogpack(fogpack);

    player.sendMessage(
      Fogger.MESSAGES_PREFIX.copy().append(
        Text.translatable("commands.fogger.reload.success")
      )
    );

    return 1;
  }
}
