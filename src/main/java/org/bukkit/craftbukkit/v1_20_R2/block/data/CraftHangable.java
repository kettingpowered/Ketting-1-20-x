package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Hangable;

public abstract class CraftHangable extends CraftBlockData implements Hangable {

    private static final BooleanProperty HANGING = getBoolean("hanging");

    public boolean isHanging() {
        return (Boolean) this.get(CraftHangable.HANGING);
    }

    public void setHanging(boolean hanging) {
        this.set((Property) CraftHangable.HANGING, (Comparable) hanging);
    }
}
