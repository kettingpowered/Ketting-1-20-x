package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.SculkCatalyst;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftSculkCatalyst extends CraftBlockData implements SculkCatalyst {

    private static final BooleanProperty BLOOM = getBoolean("bloom");

    public boolean isBloom() {
        return (Boolean) this.get(CraftSculkCatalyst.BLOOM);
    }

    public void setBloom(boolean bloom) {
        this.set((Property) CraftSculkCatalyst.BLOOM, (Comparable) bloom);
    }
}
