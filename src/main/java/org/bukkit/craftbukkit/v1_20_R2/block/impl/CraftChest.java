package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.ChestBlock;
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

public final class CraftChest extends CraftBlockData implements Chest, Directional, Waterlogged {

    private static final EnumProperty TYPE = getEnum(ChestBlock.class, "type");
    private static final EnumProperty FACING = getEnum(ChestBlock.class, "facing");
    private static final BooleanProperty WATERLOGGED = getBoolean(ChestBlock.class, "waterlogged");

    public CraftChest() {}

    public CraftChest(BlockState state) {
        super(state);
    }

    public Type getType() {
        return (Type) this.get(CraftChest.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftChest.TYPE, (Enum) type);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftChest.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftChest.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftChest.FACING, BlockFace.class);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftChest.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftChest.WATERLOGGED, (Comparable) waterlogged);
    }
}
