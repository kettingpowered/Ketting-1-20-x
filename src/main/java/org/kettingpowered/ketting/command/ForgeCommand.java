package org.kettingpowered.ketting.command;

import com.mojang.brigadier.tree.CommandNode;

import java.util.ArrayList;
import java.util.List;

public class ForgeCommand {

    private static final List<CommandNode> FORGE_COMMANDS = new ArrayList<>();

    public static boolean checkIfForgeCommand(CommandNode node) {
        if (FORGE_COMMANDS.isEmpty() || node == null)
            return false;

        return FORGE_COMMANDS.stream().anyMatch(commandNode -> commandNode.equals(node));
    }

    public static void addForgeCommand(CommandNode node) {
        FORGE_COMMANDS.add(node);
    }
}
