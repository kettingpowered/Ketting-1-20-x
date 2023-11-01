package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftDispenser extends CraftBlockData implements Dispenser {

    private static final BooleanProperty TRIGGERED = getBoolean("triggered");

    public boolean isTriggered() {
        return (Boolean) this.get(CraftDispenser.TRIGGERED);
    }

    public void setTriggered(boolean triggered) {
        this.set((Property) CraftDispenser.TRIGGERED, (Comparable) triggered);
    }
}
