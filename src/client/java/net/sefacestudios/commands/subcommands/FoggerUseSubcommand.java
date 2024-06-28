package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;

public class FoggerUseSubcommand {
  public static int execute(CommandContext<FabricClientCommandSource> ctx) {
    ClientPlayerEntity player = ctx.getSource().getPlayer();
    String identifier = ctx.getArgument("fogpack", String.class);
    Fogpack fogpack = FoggerClient.getFogpackManager().getFogpackFromIdentifier(identifier);

    if (fogpack == null) {
      ctx.getSource().getPlayer().sendMessage(
        Fogger.MESSAGES_PREFIX.copy().append(
          Text.translatable("commands.fogger.use.failed.fogpackDoesNotExist", identifier)
        )
      );

      return 1;
    }

    int waterColor = fogpack.getConfig().getWater().getColor() != null ? fogpack.getConfig().getWater().getColor() : 0;
    boolean isDifferentColorWater = FoggerClient.getFogpackManager().getConfiguration().getLatestWaterColor() != waterColor;

    FoggerClient.getFogpackManager().applyFogpack(fogpack, !isDifferentColorWater);

    player.sendMessage(
      Fogger.MESSAGES_PREFIX.copy().append(
        Text.translatable("commands.fogger.use.success", fogpack.getIdentifier())
      )
    );

    return 1;
  }
}
