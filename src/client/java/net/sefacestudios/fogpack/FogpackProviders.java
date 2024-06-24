package net.sefacestudios.fogpack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.concurrent.CompletableFuture;

public class FogpackProviders {
    public static CompletableFuture<Suggestions> getUseSuggestions(CommandContext<FabricClientCommandSource> ctx, SuggestionsBuilder bld) throws CommandSyntaxException {
        for (Fogpack fogPack : FogpackManager.getLoadedFogPacks()) {
            bld.suggest(String.valueOf(fogPack.getIdentifier()));
        }

        return bld.buildFuture();
    }
}
