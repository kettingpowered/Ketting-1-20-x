package org.bukkit.craftbukkit.v1_20_R1.command;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftChatMessage;
import org.jetbrains.annotations.NotNull;

public class DebugCommandCommandSender extends ServerCommandSender {
    private CommandSourceStack stack;
    private net.minecraft.server.commands.DebugCommand.Tracer tracer;
    public DebugCommandCommandSender(CommandSourceStack stack, net.minecraft.server.commands.DebugCommand.Tracer tracer){
        this.stack = stack;
        this.tracer = tracer;
    }
    @Override
    public void sendMessage(@NotNull String message) {
        for (Component component : CraftChatMessage.fromString(message)) {
            tracer.sendSystemMessage(component);
        }
    }

    @Override
    public void sendMessage(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public @NotNull String getName() {
        return "DebugCommandCommandSender";
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of a DebugCommand CommandSender");
    }
}
