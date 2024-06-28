package net.sefacestudios.registry;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.sefacestudios.commands.FoggerCommand;

public class FoggerCommands {
  public static void register() {
    ClientCommandRegistrationCallback.EVENT.register((FoggerCommand::register));
  }
}
