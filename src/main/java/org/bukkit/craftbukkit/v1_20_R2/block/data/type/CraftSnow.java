package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Snow;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public class CraftSnow extends CraftBlockData implements Snow {

    private static final IntegerProperty LAYERS = getInteger("layers");

    public int getLayers() {
        return (Integer) this.get(CraftSnow.LAYERS);
    }

    public void setLayers(int layers) {
        this.set((Property) CraftSnow.LAYERS, (Comparable) layers);
    }

    public int getMinimumLayers() {
        return getMin(CraftSnow.LAYERS);
    }

    public int getMaximumLayers() {
        return getMax(CraftSnow.LAYERS);
    }
}
