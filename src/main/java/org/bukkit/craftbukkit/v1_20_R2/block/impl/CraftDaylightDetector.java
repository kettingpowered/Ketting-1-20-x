package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.DaylightDetectorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftDaylightDetector extends CraftBlockData implements DaylightDetector, AnaloguePowerable {

    private static final BooleanProperty INVERTED = getBoolean(DaylightDetectorBlock.class, "inverted");
    private static final IntegerProperty POWER = getInteger(DaylightDetectorBlock.class, "power");

    public CraftDaylightDetector() {}

    public CraftDaylightDetector(BlockState state) {
        super(state);
    }

    public boolean isInverted() {
        return (Boolean) this.get(CraftDaylightDetector.INVERTED);
    }

    public void setInverted(boolean inverted) {
        this.set((Property) CraftDaylightDetector.INVERTED, (Comparable) inverted);
    }

    public int getPower() {
        return (Integer) this.get(CraftDaylightDetector.POWER);
    }

    public void setPower(int power) {
        this.set((Property) CraftDaylightDetector.POWER, (Comparable) power);
    }

    public int getMaximumPower() {
        return getMax(CraftDaylightDetector.POWER);
    }
}
