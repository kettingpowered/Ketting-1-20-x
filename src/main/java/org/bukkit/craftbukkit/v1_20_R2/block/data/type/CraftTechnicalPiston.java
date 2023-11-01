package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TechnicalPiston.Type;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftTechnicalPiston extends CraftBlockData implements TechnicalPiston {

    private static final EnumProperty TYPE = getEnum("type");

    public Type getType() {
        return (Type) this.get(CraftTechnicalPiston.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftTechnicalPiston.TYPE, (Enum) type);
    }
}
