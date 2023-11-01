package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftScaffolding extends CraftBlockData implements Scaffolding {

    private static final BooleanProperty BOTTOM = getBoolean("bottom");
    private static final IntegerProperty DISTANCE = getInteger("distance");

    public boolean isBottom() {
        return (Boolean) this.get(CraftScaffolding.BOTTOM);
    }

    public void setBottom(boolean bottom) {
        this.set((Property) CraftScaffolding.BOTTOM, (Comparable) bottom);
    }

    public int getDistance() {
        return (Integer) this.get(CraftScaffolding.DISTANCE);
    }

    public void setDistance(int distance) {
        this.set((Property) CraftScaffolding.DISTANCE, (Comparable) distance);
    }

    public int getMaximumDistance() {
        return getMax(CraftScaffolding.DISTANCE);
    }
}
