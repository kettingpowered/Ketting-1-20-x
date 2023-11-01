package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftLadder extends CraftBlockData implements Ladder, Directional, Waterlogged {

    private static final EnumProperty FACING = getEnum(LadderBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(LadderBlock.class, "waterlogged");

    public CraftLadder() {}

    public CraftLadder(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftLadder.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftLadder.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftLadder.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftLadder.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftLadder.WATERLOGGED, (Comparable) waterlogged);
    }
}
