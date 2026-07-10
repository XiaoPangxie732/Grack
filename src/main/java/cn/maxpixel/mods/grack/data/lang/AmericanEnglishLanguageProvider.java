package cn.maxpixel.mods.grack.data.lang;

import cn.maxpixel.mods.grack.Config;
import cn.maxpixel.mods.grack.GrackMod;
import cn.maxpixel.mods.grack.client.commands.GroovyEvalCommands;
import cn.maxpixel.mods.grack.commands.arguments.GroovyScriptArgument;
import cn.maxpixel.mods.grack.util.I18nKey;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class AmericanEnglishLanguageProvider extends CustomLanguageProvider {
    public AmericanEnglishLanguageProvider(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(I18nKey.KEY_CONFIG_TITLE, "Grack Configs");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON, "Grack Configs");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON_TITLE, "Grack Configs");
        add(Config.KEY_EXHIBITION_MODE, "Exhibition Mode");

        add(GroovyEvalCommands.KEY_EXPRESSION_COMPILATION_FAILURE, "Expression compilation failed with the following error:\n%s");
        add(GroovyEvalCommands.KEY_EXPRESSION_RUNTIME_FAILURE, "Failed to evaluate the expression with the following error:\n%s");

        add(GroovyScriptArgument.KEY_ERROR_UNKNOWN_SCRIPT, "Unknown script: %s");
    }
}