package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftDropper extends CraftBlockData implements Dispenser, Directional {

    private static final BooleanProperty TRIGGERED = getBoolean(DropperBlock.class, "triggered");
    private static final EnumProperty FACING = getEnum(DropperBlock.class, "facing");

    public CraftDropper() {}

    public CraftDropper(BlockState state) {
        super(state);
    }

    public boolean isTriggered() {
        return (Boolean) this.get(CraftDropper.TRIGGERED);
    }

    public void setTriggered(boolean triggered) {
        this.set((Property) CraftDropper.TRIGGERED, (Comparable) triggered);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftDropper.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftDropper.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftDropper.FACING, BlockFace.class);
    }
}
