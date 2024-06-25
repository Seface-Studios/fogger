package net.sefacestudios.commands.subcommands;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sefacestudios.FoggerClient;
import net.sefacestudios.fogpack.Fogpack;
import net.sefacestudios.fogpack.FogpackManager;

public class FoggerUseSubcommand {
    public static int execute(CommandContext<FabricClientCommandSource> ctx) {
        ClientPlayerEntity player = ctx.getSource().getPlayer();
        String identifier = ctx.getArgument("fogpack", String.class);
        Fogpack fogpack = FogpackManager.getFogpackFromIdentifier(identifier);

        if (fogpack == null) {
            ctx.getSource().getPlayer().sendMessage(
                    Text.literal("[!] ")
                            .append(
                                    Text.translatable("fogger.message.invalidFogPackName", identifier)
                            ).formatted(Formatting.RESET)


            );
            return 1;
        }

        FogpackManager.applyFogpack(fogpack);
        return 1;
    }
}
