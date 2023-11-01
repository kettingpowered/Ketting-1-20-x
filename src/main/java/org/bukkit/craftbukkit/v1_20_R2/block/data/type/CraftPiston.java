package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Piston;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftPiston extends CraftBlockData implements Piston {

    private static final BooleanProperty EXTENDED = getBoolean("extended");

    public boolean isExtended() {
        return (Boolean) this.get(CraftPiston.EXTENDED);
    }

    public void setExtended(boolean extended) {
        this.set((Property) CraftPiston.EXTENDED, (Comparable) extended);
    }
}
