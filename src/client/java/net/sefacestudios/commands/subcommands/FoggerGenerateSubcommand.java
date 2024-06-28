package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.utils.FoggerUtils;
import net.sefacestudios.utils.SefaceStudiosMembers;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Pattern;

public class FoggerGenerateSubcommand {
  private static final String IDENTIFIER_REGEX_PATTERN = "^(?:(?:(?!minecraft|minecon|mojang|mojang_studios|mojangstudios|fogger))+[a-z]*:[a-z_]*)+$";
  private static final String IDENTIFIER_SEFACE_REGEX_PATTERN = "^(?:(?:(?!seface|seface_studios|sefacestudios))+[a-z]*:[a-z_]*)+$";

  public static int execute(CommandContext<FabricClientCommandSource> ctx) {
    ClientPlayerEntity player = ctx.getSource().getPlayer();

    Identifier identifier = ctx.getArgument("identifier", Identifier.class);
    String name = ctx.getArgument("name", String.class);

    boolean canUseSefaceNamespace = (!Pattern.matches(IDENTIFIER_SEFACE_REGEX_PATTERN, identifier.toString()) && !SefaceStudiosMembers.isSefaceMember(player.getUuid()));

    if (canUseSefaceNamespace || !Pattern.matches(IDENTIFIER_REGEX_PATTERN, identifier.toString())) {
      player.sendMessage(
        Fogger.MESSAGES_PREFIX.copy().append(
          Text.translatable("commands.fogger.generate.failed.notAllowedNamespace", identifier.getNamespace())
        )
      );

      return 1;
    }

    Collection<Fogpack> fogpacks = FoggerClient.getFogpackManager().getLoadedFogpacks();

    for (Fogpack fogpack : fogpacks) {
      if (fogpack.getIdentifier().equals(identifier.toString())) {
        player.sendMessage(
          Fogger.MESSAGES_PREFIX.copy().append(
            Text.translatable("commands.fogger.generate.failed.fogpackAlreadyExist", fogpack.getIdentifier())
          )

        );

        return 1;
      }
    }

    Random random = new Random();
    Fogpack fogpack = new Fogpack(identifier, name).withRandomColors();
    String fileName = identifier.getPath()
      .concat("-")
      .concat(String.valueOf(random.nextInt(100000)))
      .concat(".fogpack.json");

    Path path = Fogger.GAMEDIR_FOGPACKS_PATH.resolve(fileName);

    FoggerUtils.createOrUpdate(Fogpack.class, path, fogpack, true);
    FoggerClient.getFogpackManager().loadOrReloadFogpacks();

    Text text = Text.literal(fileName).formatted(Formatting.UNDERLINE).styled((style) -> {
      return style
        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("commands.fogger.generate.success.fogpackFileGenerated.hover")))
        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, Fogger.GAMEDIR_FOGPACKS_PATH.toString()));
    });

    player.sendMessage(
      Fogger.MESSAGES_PREFIX.copy().append(
        Text.translatable("commands.fogger.generate.success.fogpackFileGenerated", text)
      )
    );

    return 1;
  }
}
