package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftRepeater extends CraftBlockData implements Repeater {

    private static final IntegerProperty DELAY = getInteger("delay");
    private static final BooleanProperty LOCKED = getBoolean("locked");

    public int getDelay() {
        return (Integer) this.get(CraftRepeater.DELAY);
    }

    public void setDelay(int delay) {
        this.set((Property) CraftRepeater.DELAY, (Comparable) delay);
    }

    public int getMinimumDelay() {
        return getMin(CraftRepeater.DELAY);
    }

    public int getMaximumDelay() {
        return getMax(CraftRepeater.DELAY);
    }

    public boolean isLocked() {
        return (Boolean) this.get(CraftRepeater.LOCKED);
    }

    public void setLocked(boolean locked) {
        this.set((Property) CraftRepeater.LOCKED, (Comparable) locked);
    }
}
