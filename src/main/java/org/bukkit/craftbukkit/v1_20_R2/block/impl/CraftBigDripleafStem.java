package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Dripleaf;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftBigDripleafStem extends CraftBlockData implements Dripleaf, Directional, Waterlogged {

    private static final EnumProperty FACING = getEnum(BigDripleafStemBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(BigDripleafStemBlock.class, "waterlogged");

    public CraftBigDripleafStem() {}

    public CraftBigDripleafStem(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftBigDripleafStem.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftBigDripleafStem.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftBigDripleafStem.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftBigDripleafStem.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftBigDripleafStem.WATERLOGGED, (Comparable) waterlogged);
    }
}
