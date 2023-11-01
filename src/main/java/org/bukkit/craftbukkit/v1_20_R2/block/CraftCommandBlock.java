package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.CommandBlockEntity;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;

public class CraftCommandBlock extends CraftBlockEntityState implements CommandBlock {

    public CraftCommandBlock(World world, CommandBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftCommandBlock(CraftCommandBlock state) {
        super((CraftBlockEntityState) state);
    }

    public String getCommand() {
        return ((CommandBlockEntity) this.getSnapshot()).getCommandBlock().getCommand();
    }

    public void setCommand(String command) {
        ((CommandBlockEntity) this.getSnapshot()).getCommandBlock().setCommand(command != null ? command : "");
    }

    public String getName() {
        return CraftChatMessage.fromComponent(((CommandBlockEntity) this.getSnapshot()).getCommandBlock().getName());
    }

    public void setName(String name) {
        ((CommandBlockEntity) this.getSnapshot()).getCommandBlock().setName(CraftChatMessage.fromStringOrNull(name != null ? name : "@"));
    }

    public CraftCommandBlock copy() {
        return new CraftCommandBlock(this);
    }
}
