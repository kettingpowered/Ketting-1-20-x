package org.bukkit.craftbukkit.v1_20_R2.block.data;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;

public class CraftOrientable extends CraftBlockData implements Orientable {

    private static final EnumProperty AXIS = getEnum("axis");

    public Axis getAxis() {
        return (Axis) this.get(CraftOrientable.AXIS, Axis.class);
    }

    public void setAxis(Axis axis) {
        this.set(CraftOrientable.AXIS, (Enum) axis);
    }

    public Set getAxes() {
        return this.getValues(CraftOrientable.AXIS, Axis.class);
    }
}
