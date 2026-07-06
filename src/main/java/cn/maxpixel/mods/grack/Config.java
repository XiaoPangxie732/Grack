package cn.maxpixel.mods.grack;

import java.util.List;

import cn.maxpixel.mods.grack.util.I18nKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final String KEY_EXHIBITION_MODE = I18nKey.config("exhibitionMode");
    public static final String KEY_EXHIBITION_MODE_TOOLTIP = I18nKey.config("exhibitionMode.tooltip");
    public static final ModConfigSpec.BooleanValue EXHIBITION_MODE = BUILDER
            .comment("Whether to enable the exhibition mode")
            .define("exhibitionMode", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
