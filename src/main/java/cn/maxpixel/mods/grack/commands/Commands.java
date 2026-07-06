package cn.maxpixel.mods.grack.commands;

import cn.maxpixel.mods.grack.GrackMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class Commands {
    public static void register(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        var commandSelection = event.getCommandSelection();
        var buildContext = event.getBuildContext();
    }
}