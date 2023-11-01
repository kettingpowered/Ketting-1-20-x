package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.SculkSensor.Phase;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftSculkSensor extends CraftBlockData implements SculkSensor, AnaloguePowerable, Waterlogged {

    private static final EnumProperty PHASE = getEnum(SculkSensorBlock.class, "sculk_sensor_phase");
    private static final IntegerProperty POWER = getInteger(SculkSensorBlock.class, "power");
    private static final BooleanProperty WATERLOGGED = getBoolean(SculkSensorBlock.class, "waterlogged");

    public CraftSculkSensor() {}

    public CraftSculkSensor(BlockState state) {
        super(state);
    }

    public Phase getPhase() {
        return (Phase) this.get(CraftSculkSensor.PHASE, Phase.class);
    }

    public void setPhase(Phase phase) {
        this.set(CraftSculkSensor.PHASE, (Enum) phase);
    }

    public int getPower() {
        return (Integer) this.get(CraftSculkSensor.POWER);
    }

    public void setPower(int power) {
        this.set((Property) CraftSculkSensor.POWER, (Comparable) power);
    }

    public int getMaximumPower() {
        return getMax(CraftSculkSensor.POWER);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftSculkSensor.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftSculkSensor.WATERLOGGED, (Comparable) waterlogged);
    }
}
