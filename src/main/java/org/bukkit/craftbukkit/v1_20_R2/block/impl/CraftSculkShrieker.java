package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSculkShrieker extends CraftBlockData implements SculkShrieker, Waterlogged {

    private static final BooleanProperty CAN_SUMMON = getBoolean(SculkShriekerBlock.class, "can_summon");
    private static final BooleanProperty SHRIEKING = getBoolean(SculkShriekerBlock.class, "shrieking");
    private static final BooleanProperty WATERLOGGED = getBoolean(SculkShriekerBlock.class, "waterlogged");

    public CraftSculkShrieker() {}

    public CraftSculkShrieker(BlockState state) {
        super(state);
    }

    public boolean isCanSummon() {
        return (Boolean) this.get(CraftSculkShrieker.CAN_SUMMON);
    }

    public void setCanSummon(boolean can_summon) {
        this.set((Property) CraftSculkShrieker.CAN_SUMMON, (Comparable) can_summon);
    }

    public boolean isShrieking() {
        return (Boolean) this.get(CraftSculkShrieker.SHRIEKING);
    }

    public void setShrieking(boolean shrieking) {
        this.set((Property) CraftSculkShrieker.SHRIEKING, (Comparable) shrieking);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftSculkShrieker.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftSculkShrieker.WATERLOGGED, (Comparable) waterlogged);
    }
}
