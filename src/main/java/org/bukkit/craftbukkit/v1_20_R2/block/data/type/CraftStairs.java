package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftStairs extends CraftBlockData implements Stairs {

    private static final EnumProperty SHAPE = getEnum("shape");

    public Shape getShape() {
        return (Shape) this.get(CraftStairs.SHAPE, Shape.class);
    }

    public void setShape(Shape shape) {
        this.set(CraftStairs.SHAPE, (Enum) shape);
    }
}
