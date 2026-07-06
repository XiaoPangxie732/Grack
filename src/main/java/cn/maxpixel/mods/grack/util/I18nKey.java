package cn.maxpixel.mods.grack.util;

import cn.maxpixel.mods.grack.GrackMod;

public class I18nKey {
    public static final String KEY_CONFIG_TITLE = I18nKey.config("title");
    public static final String KEY_CONFIG_SECTION_COMMON = I18nKey.config("section.grack.common.toml");
    public static final String KEY_CONFIG_SECTION_COMMON_TITLE = I18nKey.config("section.grack.common.toml.title");

    public static String commands(String cmd, String key) {
        return "commands." + GrackMod.MODID + '.' + cmd + '.' + key;
    }

    public static String config(String key) {
        return GrackMod.MODID + ".configuration." + key;
    }
}