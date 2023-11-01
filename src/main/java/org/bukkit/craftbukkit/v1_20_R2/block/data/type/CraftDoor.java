package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftDoor extends CraftBlockData implements Door {

    private static final EnumProperty HINGE = getEnum("hinge");

    public Hinge getHinge() {
        return (Hinge) this.get(CraftDoor.HINGE, Hinge.class);
    }

    public void setHinge(Hinge hinge) {
        this.set(CraftDoor.HINGE, (Enum) hinge);
    }
}
