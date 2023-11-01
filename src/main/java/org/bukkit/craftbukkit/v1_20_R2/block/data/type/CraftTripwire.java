package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftTripwire extends CraftBlockData implements Tripwire {

    private static final BooleanProperty DISARMED = getBoolean("disarmed");

    public boolean isDisarmed() {
        return (Boolean) this.get(CraftTripwire.DISARMED);
    }

    public void setDisarmed(boolean disarmed) {
        this.set((Property) CraftTripwire.DISARMED, (Comparable) disarmed);
    }
}
