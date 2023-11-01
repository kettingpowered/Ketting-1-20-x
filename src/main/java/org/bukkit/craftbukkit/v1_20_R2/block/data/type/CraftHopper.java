package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftHopper extends CraftBlockData implements Hopper {

    private static final BooleanProperty ENABLED = getBoolean("enabled");

    public boolean isEnabled() {
        return (Boolean) this.get(CraftHopper.ENABLED);
    }

    public void setEnabled(boolean enabled) {
        this.set((Property) CraftHopper.ENABLED, (Comparable) enabled);
    }
}
