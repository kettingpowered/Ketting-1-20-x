package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Snowable;

public abstract class CraftSnowable extends CraftBlockData implements Snowable {

    private static final BooleanProperty SNOWY = getBoolean("snowy");

    public boolean isSnowy() {
        return (Boolean) this.get(CraftSnowable.SNOWY);
    }

    public void setSnowy(boolean snowy) {
        this.set((Property) CraftSnowable.SNOWY, (Comparable) snowy);
    }
}
