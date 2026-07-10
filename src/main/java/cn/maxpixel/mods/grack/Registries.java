package cn.maxpixel.mods.grack;

import cn.maxpixel.mods.grack.commands.ModCommands;
import cn.maxpixel.mods.grack.commands.arguments.ArgumentRegistry;
import cn.maxpixel.mods.grack.server.ServerScriptLibrary;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = GrackMod.MODID)
public class Registries {
    public static void register(IEventBus modBus) {
        ArgumentRegistry.register(modBus);
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.register(event);
    }

    @SubscribeEvent
    public static void onAddServerReloadListeners(AddServerReloadListenersEvent event) {
        event.addRetainedListener(ServerScriptLibrary.LISTENER_KEY, new ServerScriptLibrary());
    }
}