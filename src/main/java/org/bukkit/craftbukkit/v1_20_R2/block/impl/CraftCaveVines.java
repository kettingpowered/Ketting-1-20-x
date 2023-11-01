package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftCaveVines extends CraftBlockData implements CaveVines, Ageable, CaveVinesPlant {

    private static final IntegerProperty AGE = getInteger(CaveVinesBlock.class, "age");
    private static final BooleanProperty BERRIES = getBoolean(CaveVinesBlock.class, "berries");

    public CraftCaveVines() {}

    public CraftCaveVines(BlockState state) {
        super(state);
    }

    public int getAge() {
        return (Integer) this.get(CraftCaveVines.AGE);
    }

    public void setAge(int age) {
        this.set((Property) CraftCaveVines.AGE, (Comparable) age);
    }

    public int getMaximumAge() {
        return getMax(CraftCaveVines.AGE);
    }

    public boolean isBerries() {
        return (Boolean) this.get(CraftCaveVines.BERRIES);
    }

    public void setBerries(boolean berries) {
        this.set((Property) CraftCaveVines.BERRIES, (Comparable) berries);
    }
}
