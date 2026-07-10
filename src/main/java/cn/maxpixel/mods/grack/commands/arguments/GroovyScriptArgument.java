package cn.maxpixel.mods.grack.commands.arguments;

import cn.maxpixel.mods.grack.GrackScript;
import cn.maxpixel.mods.grack.server.ServerScriptLibrary;
import cn.maxpixel.mods.grack.util.I18nKey;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GroovyScriptArgument implements ArgumentType<GroovyScriptArgument.Result> {
    public static final String NAME = "gscript";
    public static final String KEY_ERROR_UNKNOWN_SCRIPT = I18nKey.arguments(NAME, "unknown_script");
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SCRIPT = new DynamicCommandExceptionType(
            value -> Component.translatableEscape(KEY_ERROR_UNKNOWN_SCRIPT, value)
    );
    private static final List<String> EXAMPLES = List.of("foo", "foo:bar");

    public static GroovyScriptArgument scripts() {
        return new GroovyScriptArgument();
    }

    @Override
    public Result parse(StringReader reader) throws CommandSyntaxException {
        Identifier id = Identifier.read(reader);
        return new Result() {
            @Override
            public GrackScript create(CommandContext<CommandSourceStack> c) throws CommandSyntaxException {
                return c.getSource().getServer().getServerResources().managers()
                        .getListener(ServerScriptLibrary.LISTENER_KEY).getScript(id)
                        .orElseThrow(() -> ERROR_UNKNOWN_SCRIPT.create(id.toString()));
            }

            @Override
            public Identifier id() {
                return id;
            }
        };
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static GrackScript getScript(CommandContext<CommandSourceStack> c, String name) throws CommandSyntaxException {
        return c.getArgument(name, Result.class).create(c);
    }

    public interface Result {
        GrackScript create(CommandContext<CommandSourceStack> context) throws CommandSyntaxException;

        Identifier id();
    }
}