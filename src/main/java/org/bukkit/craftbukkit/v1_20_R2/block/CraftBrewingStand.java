package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.bukkit.World;
import org.bukkit.block.BrewingStand;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryBrewer;
import org.bukkit.inventory.BrewerInventory;

public class CraftBrewingStand extends CraftContainer implements BrewingStand {

    public CraftBrewingStand(World world, BrewingStandBlockEntity tileEntity) {
        super(world, (BaseContainerBlockEntity) tileEntity);
    }

    protected CraftBrewingStand(CraftBrewingStand state) {
        super((CraftContainer) state);
    }

    public BrewerInventory getSnapshotInventory() {
        return new CraftInventoryBrewer((Container) this.getSnapshot());
    }

    public BrewerInventory getInventory() {
        return (BrewerInventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryBrewer((Container) this.getTileEntity()));
    }

    public int getBrewingTime() {
        return ((BrewingStandBlockEntity) this.getSnapshot()).brewTime;
    }

    public void setBrewingTime(int brewTime) {
        ((BrewingStandBlockEntity) this.getSnapshot()).brewTime = brewTime;
    }

    public int getFuelLevel() {
        return ((BrewingStandBlockEntity) this.getSnapshot()).fuel;
    }

    public void setFuelLevel(int level) {
        ((BrewingStandBlockEntity) this.getSnapshot()).fuel = level;
    }

    public CraftBrewingStand copy() {
        return new CraftBrewingStand(this);
    }
}
