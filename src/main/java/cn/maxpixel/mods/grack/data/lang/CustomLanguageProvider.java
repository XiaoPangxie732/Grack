package cn.maxpixel.mods.grack.data.lang;

import cn.maxpixel.mods.grack.GrackMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class CustomLanguageProvider extends LanguageProvider {
    public CustomLanguageProvider(PackOutput output, String locale) {
        super(output, GrackMod.MODID, locale);
    }
}