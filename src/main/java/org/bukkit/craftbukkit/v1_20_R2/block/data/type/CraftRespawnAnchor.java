package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftRespawnAnchor extends CraftBlockData implements RespawnAnchor {

    private static final IntegerProperty CHARGES = getInteger("charges");

    public int getCharges() {
        return (Integer) this.get(CraftRespawnAnchor.CHARGES);
    }

    public void setCharges(int charges) {
        this.set((Property) CraftRespawnAnchor.CHARGES, (Comparable) charges);
    }

    public int getMaximumCharges() {
        return getMax(CraftRespawnAnchor.CHARGES);
    }
}
