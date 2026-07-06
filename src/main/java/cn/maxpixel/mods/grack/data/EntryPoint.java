package cn.maxpixel.mods.grack.data;

import cn.maxpixel.mods.grack.GrackMod;
import cn.maxpixel.mods.grack.data.lang.AmericanEnglishLanguageProvider;
import cn.maxpixel.mods.grack.data.lang.SimplifiedChineseLanguageProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = GrackMod.MODID, value = Dist.CLIENT)
public class EntryPoint {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(AmericanEnglishLanguageProvider::new);
        event.createProvider(SimplifiedChineseLanguageProvider::new);
    }
}