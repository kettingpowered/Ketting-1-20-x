package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftPistonHead extends CraftBlockData implements PistonHead {

    private static final BooleanProperty SHORT = getBoolean("short");

    public boolean isShort() {
        return (Boolean) this.get(CraftPistonHead.SHORT);
    }

    public void setShort(boolean _short) {
        this.set((Property) CraftPistonHead.SHORT, (Comparable) _short);
    }
}
