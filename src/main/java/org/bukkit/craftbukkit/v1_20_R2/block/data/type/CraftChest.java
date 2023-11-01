package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftChest extends CraftBlockData implements Chest {

    private static final EnumProperty TYPE = getEnum("type");

    public Type getType() {
        return (Type) this.get(CraftChest.TYPE, Type.class);
    }

    public void setType(Type type) {
        this.set(CraftChest.TYPE, (Enum) type);
    }
}
