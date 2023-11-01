package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Jigsaw;
import org.bukkit.block.data.type.Jigsaw.Orientation;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftJigsaw extends CraftBlockData implements Jigsaw {

    private static final EnumProperty ORIENTATION = getEnum("orientation");

    public Orientation getOrientation() {
        return (Orientation) this.get(CraftJigsaw.ORIENTATION, Orientation.class);
    }

    public void setOrientation(Orientation orientation) {
        this.set(CraftJigsaw.ORIENTATION, (Enum) orientation);
    }
}
