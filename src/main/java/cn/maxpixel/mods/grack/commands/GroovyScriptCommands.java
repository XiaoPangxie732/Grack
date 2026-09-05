package cn.maxpixel.mods.grack.commands;

import cn.maxpixel.mods.grack.GrackMod;
import cn.maxpixel.mods.grack.GrackScript;
import cn.maxpixel.mods.grack.client.commands.GroovyEvalCommands;
import cn.maxpixel.mods.grack.commands.arguments.GroovyScriptArgument;
import cn.maxpixel.mods.grack.server.ServerScriptLibrary;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import groovy.lang.Binding;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public class GroovyScriptCommands {
    public static final String NAME = "gscript";
    public static final SuggestionProvider<CommandSourceStack> SUGGEST_SCRIPT = (c, p) -> {
        ServerScriptLibrary manager = c.getSource().getServer().getServerResources().managers().getListener(ServerScriptLibrary.LISTENER_KEY);
        return SharedSuggestionProvider.suggestResource(manager.getScripts().keySet(), p);
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(NAME)
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.argument("name", GroovyScriptArgument.scripts())
                        .suggests(SUGGEST_SCRIPT)
                        .executes(new Executor())
                        .then(Commands.argument("arguments", CompoundTagArgument.compoundTag()).executes(new Executor() {
                            @Override
                            protected CompoundTag getArguments(CommandContext<CommandSourceStack> s) {
                                return CompoundTagArgument.getCompoundTag(s, "arguments");
                            }
                        }))
                )
        );
    }

    private static class Executor implements Command<CommandSourceStack> {
        protected CompoundTag getArguments(CommandContext<CommandSourceStack> s) {
            return null;
        }

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            GrackScript script = GroovyScriptArgument.getScript(context, "name");
            var args = getArguments(context);
            Binding binding = new Binding();
            binding.setProperty("context", context);
            if (args != null) {
                binding.setProperty("args", args);
            }
            script.setBinding(binding);
            var result = script.run();// TODO: how to deal with the result?
            return SINGLE_SUCCESS;
        }
    }
}