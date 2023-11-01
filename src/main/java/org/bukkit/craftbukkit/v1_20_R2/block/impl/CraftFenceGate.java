package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Gate;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftFenceGate extends CraftBlockData implements Gate, Directional, Openable, Powerable {

    private static final BooleanProperty IN_WALL = getBoolean(FenceGateBlock.class, "in_wall");
    private static final EnumProperty FACING = getEnum(FenceGateBlock.class, "facing");
    private static final BooleanProperty OPEN = getBoolean(FenceGateBlock.class, "open");
    private static final BooleanProperty POWERED = getBoolean(FenceGateBlock.class, "powered");

    public CraftFenceGate() {}

    public CraftFenceGate(BlockState state) {
        super(state);
    }

    public boolean isInWall() {
        return (Boolean) this.get(CraftFenceGate.IN_WALL);
    }

    public void setInWall(boolean inWall) {
        this.set((Property) CraftFenceGate.IN_WALL, (Comparable) inWall);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftFenceGate.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftFenceGate.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftFenceGate.FACING, BlockFace.class);
    }

    public boolean isOpen() {
        return (Boolean) this.get(CraftFenceGate.OPEN);
    }

    public void setOpen(boolean open) {
        this.set((Property) CraftFenceGate.OPEN, (Comparable) open);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftFenceGate.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftFenceGate.POWERED, (Comparable) powered);
    }
}
