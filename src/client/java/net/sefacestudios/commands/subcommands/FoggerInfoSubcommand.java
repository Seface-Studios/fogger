package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackConfiguration;

public class FoggerInfoSubcommand {
  public static int execute(CommandContext<FabricClientCommandSource> ctx) {
    ClientPlayerEntity player = ctx.getSource().getPlayer();

    Fogpack fogpack = FoggerClient.getFogpackManager().getAppliedFogpackInstance();
    FogpackConfiguration config = fogpack.getConfig();

    Text fogpackName = Text.literal(fogpack.getName()).styled(style -> {
      return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(fogpack.getIdentifier())));
    });

    Text fogpackVersion = Text.literal("v" + fogpack.getVersion()).formatted(Formatting.BLUE);

    player.sendMessage(Text.empty());
    player.sendMessage(Text.translatable("commands.fogger.info.title", fogpackName, fogpackVersion));

    if (!fogpack.getDescription().isEmpty()) {
      player.sendMessage(Text.literal(fogpack.getDescription()).formatted(Formatting.GRAY));
    }

    player.sendMessage(Text.empty());
    genericSendMessage(player, "Sky", config.getSky());
    fogSendMessage(player, config.getFog());
    genericSendMessage(player, "Clouds", config.getClouds());
    genericSendMessage(player, "Water", config.getWater());
    genericSendMessage(player, "Water Fog", config.getWaterFog());
    player.sendMessage(Text.empty());

    return 1;
  }

  public static void genericSendMessage(ClientPlayerEntity player, String id, FogpackConfiguration.ColorConfiguration configuration) {
    player.sendMessage(
      Text.translatable(
        "commands.fogger.info.generic", id,
        getColorPreview(configuration.getColor(), configuration.getHex())
      )
    );
  }

  public static void fogSendMessage(ClientPlayerEntity player, FogpackConfiguration.FogConfiguration configuration) {
    player.sendMessage(
      Text.translatable(
        "commands.fogger.info.fog",
        getColorPreview(configuration.getColor(), configuration.getHex()),
        configuration.getDistance(),
        String.valueOf(configuration.getStartMultiplier()).concat("%")
      )
    );
  }

  public static MutableText getColorPreview(Integer color, String colorHex) {
    int styleColor = color != null ? color : 0;
    Style style = Style.EMPTY.withColor(TextColor.fromRgb(styleColor))
      .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, colorHex))
      .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(colorHex)));

    return color != null ? Text.literal(colorHex).setStyle(style) : Text.literal("vanilla");
  }
}
