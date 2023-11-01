package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftStepAbstract extends CraftBlockData implements Slab, Waterlogged {

    private static final EnumProperty TYPE = getEnum(SlabBlock.class, "type");
    private static final BooleanProperty WATERLOGGED = getBoolean(SlabBlock.class, "waterlogged");

    public CraftStepAbstract() {}

    public CraftStepAbstract(BlockState state) {
        super(state);
    }

    public Type getType() {
        return (Type) this.get(CraftStepAbstract.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftStepAbstract.TYPE, (Enum) type);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftStepAbstract.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftStepAbstract.WATERLOGGED, (Comparable) waterlogged);
    }
}
