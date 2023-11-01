package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftSwitch extends CraftBlockData implements Switch {

    private static final EnumProperty FACE = getEnum("face");

    public Face getFace() {
        return (Face) this.get(CraftSwitch.FACE, Face.class);
    }

    public void setFace(Face face) {
        this.set(CraftSwitch.FACE, (Enum) face);
    }
}
