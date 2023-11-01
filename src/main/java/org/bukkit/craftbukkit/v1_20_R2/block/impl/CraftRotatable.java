package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftRotatable extends CraftBlockData implements Orientable {

    private static final EnumProperty AXIS = getEnum(RotatedPillarBlock.class, "axis");

    public CraftRotatable() {}

    public CraftRotatable(BlockState state) {
        super(state);
    }

    public Axis getAxis() {
        return (Axis) this.get(CraftRotatable.AXIS, Axis.class);
    }

    public void setAxis(Axis axis) {
        this.set(CraftRotatable.AXIS, (Enum) axis);
    }

    public Set getAxes() {
        return this.getValues(CraftRotatable.AXIS, Axis.class);
    }
}
