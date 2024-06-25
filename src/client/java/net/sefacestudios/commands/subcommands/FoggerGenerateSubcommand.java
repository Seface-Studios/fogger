package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sefacestudios.Fogger;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.utils.FoggerUtils;
import net.sefacestudios.utils.SefaceStudiosMembers;

import java.nio.file.Path;
import java.util.Collection;
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
            player.sendMessage(Text.translatable("fogger.message.notAllowedNamespace", identifier.getNamespace()));
            return 1;
        }

        Collection<Fogpack> fogpacks = FogpackManager.getLoadedFogpacks();

        for (Fogpack fogpack : fogpacks) {
            if (fogpack.getIdentifier().equals(identifier.toString())) {
                player.sendMessage(Text.translatable("fogger.message.fogpackAlreadyExists", fogpack.getIdentifier()));
                return 1;
            }
        }

        Fogpack fogpack = new Fogpack(identifier, name);
        Path path = Fogger.FOGPACKS_FOLDER_PATH.resolve(identifier.getPath().concat(".fogpack.json"));

        FoggerUtils.createOrUpdate(Fogpack.class, path, fogpack, true);
        FoggerClient.getFogpackManager().loadOrReloadFogpacks();

        player.sendMessage(Text.translatable("fogger.message.baseFogpackFileGenerated", identifier.getPath()));

        return 1;
    }
}
