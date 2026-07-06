package cn.maxpixel.mods.grack;

import cn.maxpixel.mods.grack.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = GrackMod.MODID)
public class Registries {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        Commands.register(event);
    }
}