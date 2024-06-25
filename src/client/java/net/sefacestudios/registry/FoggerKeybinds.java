package net.sefacestudios.registry;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.sefacestudios.utils.IFoggerGameOptionsMixin;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FoggerKeybinds {
    private static final KeyBinding openFogPacksList = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "fogger.key.openFogPacks",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "fogger.key.categories.fogger"
    ));

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(FoggerKeybinds::openFogPacksLogic);
    }

    private static void openFogPacksLogic(MinecraftClient client) {
        while (FoggerKeybinds.openFogPacksList.wasPressed()) {
            client.player.sendMessage(Text.literal("AAA"));
        }
    }
}
