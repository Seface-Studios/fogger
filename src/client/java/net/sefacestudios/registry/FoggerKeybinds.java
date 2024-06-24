package net.sefacestudios.registry;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.sefacestudios.utils.IFoggerGameOptionsMixin;
import org.lwjgl.glfw.GLFW;

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
            /*IFoggerGameOptionsMixin options = (IFoggerGameOptionsMixin) client.options;
            options.getAppliedFogpack().setValue(UUID.randomUUID().toString());
            ((GameOptions) options).write();*/

            client.player.sendMessage(client.player.getName(), false);
        }
    }
}
