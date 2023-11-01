package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftTrapdoor extends CraftBlockData implements TrapDoor, Bisected, Directional, Openable, Powerable, Waterlogged {

    private static final EnumProperty HALF = getEnum(TrapDoorBlock.class, "half");
    private static final EnumProperty FACING = getEnum(TrapDoorBlock.class, "facing");
    private static final BooleanProperty OPEN = getBoolean(TrapDoorBlock.class, "open");
    private static final BooleanProperty POWERED = getBoolean(TrapDoorBlock.class, "powered");
    private static final BooleanProperty WATERLOGGED = getBoolean(TrapDoorBlock.class, "waterlogged");

    public CraftTrapdoor() {}

    public CraftTrapdoor(BlockState state) {
        super(state);
    }

    public Half getHalf() {
        return (Half) this.get(CraftTrapdoor.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftTrapdoor.HALF, (Enum) half);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftTrapdoor.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftTrapdoor.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftTrapdoor.FACING, BlockFace.class);
    }

    public boolean isOpen() {
        return (Boolean) this.get(CraftTrapdoor.OPEN);
    }

    public void setOpen(boolean open) {
        this.set((Property) CraftTrapdoor.OPEN, (Comparable) open);
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftTrapdoor.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftTrapdoor.POWERED, (Comparable) powered);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftTrapdoor.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftTrapdoor.WATERLOGGED, (Comparable) waterlogged);
    }
}
