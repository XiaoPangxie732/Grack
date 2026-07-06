package cn.maxpixel.mods.grack.client.commands;

import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class ClientCommands {
    public static void register(RegisterClientCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        var buildContext = event.getBuildContext();
        GroovyEvalCommands.register(dispatcher);
    }
}