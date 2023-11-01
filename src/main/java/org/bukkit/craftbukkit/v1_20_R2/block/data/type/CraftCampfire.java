package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftCampfire extends CraftBlockData implements Campfire {

    private static final BooleanProperty SIGNAL_FIRE = getBoolean("signal_fire");

    public boolean isSignalFire() {
        return (Boolean) this.get(CraftCampfire.SIGNAL_FIRE);
    }

    public void setSignalFire(boolean signalFire) {
        this.set((Property) CraftCampfire.SIGNAL_FIRE, (Comparable) signalFire);
    }
}
