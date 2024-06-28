package net.sefacestudios;

import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.sefacestudios.event.ClientStartEvent;
import net.sefacestudios.fogpack.FogpackManager;
import net.sefacestudios.registry.FoggerCommands;
import net.sefacestudios.registry.FoggerKeybinds;

public class FoggerClient implements ClientModInitializer {
  @Getter
  private static final ClassLoader classLoader = Fogger.class.getClassLoader();

  @Getter
  private static FogpackManager fogpackManager = new FogpackManager();

  @Override
  public void onInitializeClient() {
    FoggerKeybinds.register();
    FoggerCommands.register();

    ClientStartEvent.register();
  }
}
