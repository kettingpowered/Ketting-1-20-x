package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftLeaves extends CraftBlockData implements Leaves {

    private static final IntegerProperty DISTANCE = getInteger("distance");
    private static final BooleanProperty PERSISTENT = getBoolean("persistent");

    public boolean isPersistent() {
        return (Boolean) this.get(CraftLeaves.PERSISTENT);
    }

    public void setPersistent(boolean persistent) {
        this.set((Property) CraftLeaves.PERSISTENT, (Comparable) persistent);
    }

    public int getDistance() {
        return (Integer) this.get(CraftLeaves.DISTANCE);
    }

    public void setDistance(int distance) {
        this.set((Property) CraftLeaves.DISTANCE, (Comparable) distance);
    }
}
