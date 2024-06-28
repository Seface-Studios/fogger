package net.sefacestudios.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.sefacestudios.FoggerClient;

public class ClientStartEvent {
  public static void register() {
    ClientLifecycleEvents.CLIENT_STARTED.register(client -> { FoggerClient.getFogpackManager().onClientStarted(); });
  }
}
