package org.kettingpowered.ketting.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.commands.CommandSourceStack;
import org.kettingpowered.ketting.common.utils.IgnoredClasses;

import java.util.Arrays;
import java.util.Collections;

/**
 * Used for special cases where mods try to access vanilla commands
 */
public class RoutableCommandDispatcher extends CommandDispatcher<CommandSourceStack> {

    public static final String[] REDIRECTIONS = {
            "org.valkyrienskies.mod.forge.common.ValkyrienSkiesModForge",
            "io.github.marcus8448.gamemodeoverhaul.GamemodeOverhaulForge"
    };

    public static boolean needsRedirect() {
        StackWalker walker = StackWalker.getInstance(Collections.emptySet(), 10);
        return walker.walk(frames ->
                frames.map(StackWalker.StackFrame::getClassName)
                        .filter(IgnoredClasses::shouldCheck)
                        .anyMatch(name -> Arrays.stream(REDIRECTIONS).anyMatch(name::startsWith))
        );
    }

    public static RoutableCommandDispatcher INSTANCE;

    private static CommandDispatcher<CommandSourceStack> fallback;

    public static void setFallback(CommandDispatcher<CommandSourceStack> dispatcher) {
        fallback = dispatcher;
    }

    public static RoutableCommandDispatcher of(CommandDispatcher<CommandSourceStack> dispatcher) {
        return new RoutableCommandDispatcher(dispatcher);
    }



    private RoutableCommandDispatcher(CommandDispatcher<CommandSourceStack> dispatcher) {
        super(getRoot(dispatcher));
        this.setConsumer(dispatcher.getConsumer());
    }

    public static RootCommandNode<CommandSourceStack> getRoot(CommandDispatcher<CommandSourceStack> dispatcher) {
        RootCommandNode<CommandSourceStack> root = dispatcher.getRoot();
        fallback.getRoot().getChildren().forEach(root::addChild);
        return root;
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> register(LiteralArgumentBuilder<CommandSourceStack> command) {
        //Register it both in this dispatcher and the fallback
        super.register(command);
        return fallback.register(command);
    }
}
