package cn.maxpixel.mods.grack.client.commands;

import cn.maxpixel.mods.grack.GrackMod;
import cn.maxpixel.mods.grack.util.I18nKey;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ContextChain;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.Eval;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.execution.ChainModifiers;
import net.minecraft.commands.execution.CustomCommandExecutor;
import net.minecraft.commands.execution.ExecutionControl;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.codehaus.groovy.control.CompilationFailedException;
import org.jspecify.annotations.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GroovyEvalCommands {// FIXME: There could be RCE through click_event: run_command, check ClientPacketListener.sendUnattendedCommand
    public static final String NAME = "geval";
    public static final String KEY_EXPRESSION_COMPILATION_FAILURE = I18nKey.commands(NAME, "error.expression_compilation_failure");
    public static final String KEY_EXPRESSION_RUNTIME_FAILURE = I18nKey.commands(NAME, "error.expression_runtime_failure");
    private static final DynamicCommandExceptionType EXPRESSION_COMPILATION_FAILURE = new DynamicCommandExceptionType(
            msg -> Component.translatableEscape(KEY_EXPRESSION_COMPILATION_FAILURE, msg)
    );
    private static final DynamicCommandExceptionType EXPRESSION_RUNTIME_FAILURE = new DynamicCommandExceptionType(
            msg -> Component.translatableEscape(KEY_EXPRESSION_RUNTIME_FAILURE, msg)
    );

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var exp = Commands.argument("expression", StringArgumentType.greedyString());
        dispatcher.register(Commands.literal(NAME)
                .then(Commands.argument("arguments", CompoundTagArgument.compoundTag()).then(exp.executes(new EvalCustomExecutor() {
                    @Override
                    protected CompoundTag getArguments(CommandContext<CommandSourceStack> s) {
                        return CompoundTagArgument.getCompoundTag(s, "arguments");
                    }
                })))
                .then(exp.executes(new EvalCustomExecutor()))
        );
    }

    private static class EvalCustomExecutor implements Command<CommandSourceStack> {
        protected CompoundTag getArguments(CommandContext<CommandSourceStack> context) {
            return null;
        }

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            Binding b = new Binding();
            var args = getArguments(context);
            if (args != null) {
                args.forEach(b::setVariable);
            }
            GroovyShell sh = new GroovyShell(EvalCustomExecutor.class.getClassLoader(), b);
            try {
                var result = sh.evaluate(StringArgumentType.getString(context, "expression"));
                context.getSource().sendSuccess(() -> Component.literal(String.valueOf(result)), false);
                return SINGLE_SUCCESS;
            } catch (CompilationFailedException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String s = sw.toString();
                GrackMod.LOGGER.error("Failed to compile the expression with the following error: \n{}", s);
                throw EXPRESSION_COMPILATION_FAILURE.create(s);
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String s = sw.toString();
                GrackMod.LOGGER.error("Failed to evaluate the expression with the following error: \n{}", s);
                throw EXPRESSION_RUNTIME_FAILURE.create(s);
            }
        }
    }
}