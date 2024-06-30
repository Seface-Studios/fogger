package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.utils.FoggerUtils;


public class FoggerReloadSubcommand {
  public static int execute(CommandContext<FabricClientCommandSource> ctx) {
    ClientPlayerEntity player = ctx.getSource().getPlayer();

    FoggerClient.getFogpackManager().loadOrReloadFogpacks();

    FogpackManager.Configuration config = FoggerUtils.getData(FogpackManager.Configuration.class, Fogger.FOGPACK_CONFIG_PATH);

    Fogpack fogpack = FoggerClient.getFogpackManager().getFogpackFromIdentifier(config.getAppliedFogpack());
    int waterColor = fogpack.getConfig().getWater().getColor() != null ? fogpack.getConfig().getWater().getColor() : 0;
    boolean isSameWaterColor = FoggerClient.getFogpackManager().getConfiguration().getLatestWaterColor() == waterColor;

    FoggerClient.getFogpackManager().applyFogpack(fogpack, isSameWaterColor);

    player.sendMessage(
      Fogger.MESSAGES_PREFIX.copy().append(
        Text.translatable("commands.fogger.reload.success")
      )
    );

    return 1;
  }
}
