package net.sefacestudios;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fogger implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("kaleidoscope");

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Kaleidoscope");
    }
}