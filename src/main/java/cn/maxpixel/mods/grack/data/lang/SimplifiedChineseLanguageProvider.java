package cn.maxpixel.mods.grack.data.lang;

import cn.maxpixel.mods.grack.Config;
import cn.maxpixel.mods.grack.GrackMod;
import cn.maxpixel.mods.grack.client.commands.GroovyEvalCommands;
import cn.maxpixel.mods.grack.util.I18nKey;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SimplifiedChineseLanguageProvider extends CustomLanguageProvider {
    public SimplifiedChineseLanguageProvider(PackOutput output) {
        super(output, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(I18nKey.KEY_CONFIG_TITLE, "Grack 配置");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON, "Grack 配置");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON_TITLE, "Grack 配置");
        add(Config.KEY_EXHIBITION_MODE, "展会模式");

        add(GroovyEvalCommands.KEY_EXPRESSION_COMPILATION_FAILURE, "表达式编译错误，错误如下:\n%s");
        add(GroovyEvalCommands.KEY_EXPRESSION_RUNTIME_FAILURE, "表达式求值失败，错误如下:\n%s");
    }
}
