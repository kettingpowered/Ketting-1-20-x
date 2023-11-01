package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftChestTrapped extends CraftBlockData implements Chest, Directional, Waterlogged {

    private static final EnumProperty TYPE = getEnum(TrappedChestBlock.class, "type");
    private static final EnumProperty FACING = getEnum(TrappedChestBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(TrappedChestBlock.class, "waterlogged");

    public CraftChestTrapped() {}

    public CraftChestTrapped(BlockState state) {
        super(state);
    }

    public Type getType() {
        return (Type) this.get(CraftChestTrapped.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftChestTrapped.TYPE, (Enum) type);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftChestTrapped.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftChestTrapped.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftChestTrapped.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftChestTrapped.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftChestTrapped.WATERLOGGED, (Comparable) waterlogged);
    }
}
