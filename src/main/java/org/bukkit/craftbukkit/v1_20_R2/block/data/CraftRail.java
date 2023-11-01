package org.bukkit.craftbukkit.v1_20_R2.block.data;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rail.Shape;

public abstract class CraftRail extends CraftBlockData implements Rail {

    private static final EnumProperty SHAPE = getEnum("shape");

    public Shape getShape() {
        return (Shape) this.get(CraftRail.SHAPE, Shape.class);
    }

    public void setShape(Shape shape) {
        this.set(CraftRail.SHAPE, (Enum) shape);
    }

    public Set getShapes() {
        return this.getValues(CraftRail.SHAPE, Shape.class);
    }
}
