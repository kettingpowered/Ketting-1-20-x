package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.CalibratedSculkSensor;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.SculkSensor.Phase;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCalibratedSculkSensor extends CraftBlockData implements CalibratedSculkSensor, Directional, SculkSensor, AnaloguePowerable, Waterlogged {

    private static final EnumProperty FACING = getEnum(CalibratedSculkSensorBlock.class, "facing");
    private static final EnumProperty PHASE = getEnum(CalibratedSculkSensorBlock.class, "sculk_sensor_phase");
    private static final IntegerProperty POWER = getInteger(CalibratedSculkSensorBlock.class, "power");
    private static final BooleanProperty WATERLOGGED = getBoolean(CalibratedSculkSensorBlock.class, "waterlogged");

    public CraftCalibratedSculkSensor() {}

    public CraftCalibratedSculkSensor(BlockState state) {
        super(state);
    }

    public BlockFace getFacing() {
        return (BlockFace) this.get(CraftCalibratedSculkSensor.FACING, BlockFace.class);
    }

    public void setFacing(BlockFace facing) {
        this.set(CraftCalibratedSculkSensor.FACING, (Enum) facing);
    }

    public Set getFaces() {
        return this.getValues(CraftCalibratedSculkSensor.FACING, BlockFace.class);
    }

    public Phase getPhase() {
        return (Phase) this.get(CraftCalibratedSculkSensor.PHASE, Phase.class);
    }

    public void setPhase(Phase phase) {
        this.set(CraftCalibratedSculkSensor.PHASE, (Enum) phase);
    }

    public int getPower() {
        return (Integer) this.get(CraftCalibratedSculkSensor.POWER);
    }

    public void setPower(int power) {
        this.set((Property) CraftCalibratedSculkSensor.POWER, (Comparable) power);
    }

    public int getMaximumPower() {
        return getMax(CraftCalibratedSculkSensor.POWER);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCalibratedSculkSensor.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCalibratedSculkSensor.WATERLOGGED, (Comparable) waterlogged);
    }
}
