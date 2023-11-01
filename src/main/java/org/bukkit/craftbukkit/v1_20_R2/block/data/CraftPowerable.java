package org.bukkit.craftbukkit.v1_20_R2.block.data;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Powerable;

public abstract class CraftPowerable extends CraftBlockData implements Powerable {

    private static final BooleanProperty POWERED = getBoolean("powered");

    public boolean isPowered() {
        return (Boolean) this.get(CraftPowerable.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftPowerable.POWERED, (Comparable) powered);
    }
}
