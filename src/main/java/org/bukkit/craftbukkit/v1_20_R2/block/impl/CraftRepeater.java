package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftRepeater extends CraftBlockData implements Repeater, Directional, Powerable {

    private static final IntegerProperty DELAY = getInteger(RepeaterBlock.class, "delay");
    private static final BooleanProperty LOCKED = getBoolean(RepeaterBlock.class, "locked");
    private static final EnumProperty FACING = getEnum(RepeaterBlock.class, "facing");
    private static final BooleanProperty POWERED = getBoolean(RepeaterBlock.class, "powered");

    public CraftRepeater() {}

    public CraftRepeater(BlockState state) {
        super(state);
    }

    public int getDelay() {
        return (Integer) this.get(CraftRepeater.DELAY);
    }

    public void setDelay(int delay) {
        this.set((Property) CraftRepeater.DELAY, (Comparable) delay);
    }

    public int getMinimumDelay() {
        return getMin(CraftRepeater.DELAY);
    }

    public int getMaximumDelay() {
        return getMax(CraftRepeater.DELAY);
    }

    public boolean isLocked() {
        return (Boolean) this.get(CraftRepeater.LOCKED);
    }

    public void setLocked(boolean locked) {
        this.set((Property) CraftRepeater.LOCKED, (Comparable) locked);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftRepeater.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftRepeater.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftRepeater.FACING, BlockFace.class);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftRepeater.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftRepeater.POWERED, (Comparable) powered);
    }
}
