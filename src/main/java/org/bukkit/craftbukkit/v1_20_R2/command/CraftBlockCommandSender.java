package org.bukkit.craftbukkit.v1_20_R2.command;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;

public class CraftBlockCommandSender extends ServerCommandSender implements BlockCommandSender {

    private final CommandSourceStack block;
    private final BlockEntity tile;

    public CraftBlockCommandSender(CommandSourceStack commandBlockListenerAbstract, BlockEntity tile) {
        this.block = commandBlockListenerAbstract;
        this.tile = tile;
    }

    public Block getBlock() {
        return CraftBlock.at(this.tile.getLevel(), this.tile.getBlockPos());
    }

    public void sendMessage(String message) {
        Component[] acomponent;
        int i = (acomponent = CraftChatMessage.fromString(message)).length;

        for (int j = 0; j < i; ++j) {
            Component component = acomponent[j];

            this.block.source.sendSystemMessage(component);
        }

    }

    public void sendMessage(String... messages) {
        String[] astring = messages;
        int i = messages.length;

        for (int j = 0; j < i; ++j) {
            String message = astring[j];

            this.sendMessage(message);
        }

    }

    public String getName() {
        return this.block.getTextName();
    }

    public boolean isOp() {
        return true;
    }

    public void setOp(boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of a block");
    }

    public CommandSourceStack getWrapper() {
        return this.block;
    }
}
