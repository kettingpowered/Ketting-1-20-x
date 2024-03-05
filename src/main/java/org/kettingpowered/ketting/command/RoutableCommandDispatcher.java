package org.kettingpowered.ketting.command;

import com.mojang.brigadier.CommandDispatcher;
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
            "org.valkyrienskies.mod.forge.common.ValkyrienSkiesModForge"
    };

    public static boolean needsRedirect() {
        StackWalker walker = StackWalker.getInstance(Collections.emptySet(), 10);
        return walker.walk(frames ->
                frames.map(StackWalker.StackFrame::getClassName)
                        .filter(IgnoredClasses::shouldCheck)
                        .anyMatch(name -> Arrays.stream(REDIRECTIONS).anyMatch(name::startsWith))
        );
    }

    private static CommandDispatcher<CommandSourceStack> fallback;

    public static void setFallback(CommandDispatcher<CommandSourceStack> dispatcher) {
        fallback = dispatcher;
    }

    public static RoutableCommandDispatcher of(CommandDispatcher<CommandSourceStack> dispatcher) {
        return new RoutableCommandDispatcher(dispatcher);
    }



    private final CommandDispatcher<CommandSourceStack> dispatcher;

    private RoutableCommandDispatcher(CommandDispatcher<CommandSourceStack> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public RootCommandNode<CommandSourceStack> getRoot() {
        RootCommandNode<CommandSourceStack> root = dispatcher.getRoot();
        fallback.getRoot().getChildren().forEach(root::addChild);
        return root;
    }
}
