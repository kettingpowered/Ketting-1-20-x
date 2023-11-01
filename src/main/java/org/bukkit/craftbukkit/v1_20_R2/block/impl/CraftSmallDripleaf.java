package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Dripleaf;
import org.bukkit.block.data.type.SmallDripleaf;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSmallDripleaf extends CraftBlockData implements SmallDripleaf, Dripleaf, Bisected, Directional, Waterlogged {

    private static final EnumProperty HALF = getEnum(SmallDripleafBlock.class, "half");
    private static final EnumProperty FACING = getEnum(SmallDripleafBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(SmallDripleafBlock.class, "waterlogged");

    public CraftSmallDripleaf() {}

    public CraftSmallDripleaf(BlockState state) {
        super(state);
    }

    public Half getHalf() {
        return (Half) this.get(CraftSmallDripleaf.HALF, Half.class);
    }

    public void setHalf(Half half) {
        this.set(CraftSmallDripleaf.HALF, (Enum) half);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftSmallDripleaf.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftSmallDripleaf.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftSmallDripleaf.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftSmallDripleaf.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftSmallDripleaf.WATERLOGGED, (Comparable) waterlogged);
    }
}
