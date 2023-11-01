package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Openable;

public abstract class CraftOpenable extends CraftBlockData implements Openable {

    private static final BooleanProperty OPEN = getBoolean("open");

    public boolean isOpen() {
        return (Boolean) this.get(CraftOpenable.OPEN);
    }

    public void setOpen(boolean open) {
        this.set((Property) CraftOpenable.OPEN, (Comparable) open);
    }
}
