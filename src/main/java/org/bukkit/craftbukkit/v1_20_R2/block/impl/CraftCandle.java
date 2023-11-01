package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Candle;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCandle extends CraftBlockData implements Candle, Lightable, Waterlogged {

    private static final IntegerProperty CANDLES = getInteger(CandleBlock.class, "candles");
    private static final BooleanProperty LIT = getBoolean(CandleBlock.class, "lit");
    private static final BooleanProperty WATERLOGGED = getBoolean(CandleBlock.class, "waterlogged");

    public CraftCandle() {}

    public CraftCandle(BlockState state) {
        super(state);
    }

    public int getCandles() {
        return (Integer) this.get(CraftCandle.CANDLES);
    }

    public void setCandles(int candles) {
        this.set((Property) CraftCandle.CANDLES, (Comparable) candles);
    }

    public int getMaximumCandles() {
        return getMax(CraftCandle.CANDLES);
    }

    public boolean isLit() {
        return (Boolean) this.get(CraftCandle.LIT);
    }

    public void setLit(boolean lit) {
        this.set((Property) CraftCandle.LIT, (Comparable) lit);
    }

    public boolean isWaterlogged() {
        return (Boolean) this.get(CraftCandle.WATERLOGGED);
    }

    public void setWaterlogged(boolean waterlogged) {
        this.set((Property) CraftCandle.WATERLOGGED, (Comparable) waterlogged);
    }
}
