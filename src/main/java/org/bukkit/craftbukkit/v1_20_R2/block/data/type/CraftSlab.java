package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftSlab extends CraftBlockData implements Slab {

    private static final EnumProperty TYPE = getEnum("type");

    public Type getType() {
        return (Type) this.get(CraftSlab.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftSlab.TYPE, (Enum) type);
    }
}
