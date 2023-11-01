package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCommand extends CraftBlockData implements CommandBlock, Directional {

    private static final BooleanProperty CONDITIONAL = getBoolean(net.minecraft.world.level.block.CommandBlock.class, "conditional");
    private static final EnumProperty FACING = getEnum(net.minecraft.world.level.block.CommandBlock.class, "facing");

    public CraftCommand() {}

    public CraftCommand(BlockState state) {
        super(state);
    }

    public boolean isConditional() {
        return (Boolean) this.get(CraftCommand.CONDITIONAL);
    }

    public void setConditional(boolean conditional) {
        this.set((Property) CraftCommand.CONDITIONAL, (Comparable) conditional);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftCommand.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftCommand.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftCommand.FACING, BlockFace.class);
    }
}
