package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.block.data.type.PointedDripstone.Thickness;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftPointedDripstone extends CraftBlockData implements PointedDripstone {

    private static final EnumProperty VERTICAL_DIRECTION = getEnum("vertical_direction");
    private static final EnumProperty THICKNESS = getEnum("thickness");

    public BlockFace getVerticalDirection() {
        return (BlockFace) this.get(CraftPointedDripstone.VERTICAL_DIRECTION, BlockFace.class);
    }

    public void setVerticalDirection(BlockFace direction) {
        this.set(CraftPointedDripstone.VERTICAL_DIRECTION, (Enum) direction);
    }

    public Set getVerticalDirections() {
        return this.getValues(CraftPointedDripstone.VERTICAL_DIRECTION, BlockFace.class);
    }

    public Thickness getThickness() {
        return (Thickness) this.get(CraftPointedDripstone.THICKNESS, Thickness.class);
    }

    public void setThickness(Thickness thickness) {
        this.set(CraftPointedDripstone.THICKNESS, (Enum) thickness);
    }
}
