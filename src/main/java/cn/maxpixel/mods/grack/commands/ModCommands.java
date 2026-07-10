package cn.maxpixel.mods.grack.commands;

import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class ModCommands {
    public static void register(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        var commandSelection = event.getCommandSelection();
        var buildContext = event.getBuildContext();
        GroovyScriptCommands.register(dispatcher);
    }
}