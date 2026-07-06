package cn.maxpixel.mods.grack.client;

import cn.maxpixel.mods.grack.GrackMod;
import cn.maxpixel.mods.grack.client.commands.ClientCommands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = GrackMod.MODID, value = Dist.CLIENT)
public class ClientRegistries {
    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        ClientCommands.register(event);
    }
}