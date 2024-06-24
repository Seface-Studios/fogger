package net.sefacestudios.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class FogPackScreen extends Screen {
    public static final MutableText TITLE = Text.translatable("fogPack.title");

    protected FogPackScreen() {
        super(FogPackScreen.TITLE);
    }
}
