package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.type.Candle;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftCandle extends CraftBlockData implements Candle {

    private static final IntegerProperty CANDLES = getInteger("candles");

    public int getCandles() {
        return (Integer) this.get(CraftCandle.CANDLES);
    }

    public void setCandles(int candles) {
        this.set((Property) CraftCandle.CANDLES, (Comparable) candles);
    }

    public int getMaximumCandles() {
        return getMax(CraftCandle.CANDLES);
    }
}
