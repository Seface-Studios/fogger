package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.FogpackManager;

public class FoggerResetSubcommand {
  public static int execute(CommandContext<FabricClientCommandSource> ctx) {
    ClientPlayerEntity player = ctx.getSource().getPlayer();

    FoggerClient.getFogpackManager().applyFogpack(FogpackManager.VANILLA_FOGPACK);

    player.sendMessage(
      Fogger.MESSAGES_PREFIX.copy().append(
        Text.translatable("commands.fogger.reset.success")
      )
    );

    return 1;
  }
}
