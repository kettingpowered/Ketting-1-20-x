package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.AmethystCluster;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftAmethystCluster extends CraftBlockData implements AmethystCluster, Directional, Waterlogged {

    private static final EnumProperty FACING = getEnum(AmethystClusterBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(AmethystClusterBlock.class, "waterlogged");

    public CraftAmethystCluster() {}

    public CraftAmethystCluster(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftAmethystCluster.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftAmethystCluster.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftAmethystCluster.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftAmethystCluster.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftAmethystCluster.WATERLOGGED, (Comparable) waterlogged);
    }
}
