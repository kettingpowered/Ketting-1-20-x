package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Hopper;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftHopper extends CraftLootable implements Hopper {

    public CraftHopper(World world, HopperBlockEntity tileEntity) {
        super(world, (RandomizableContainerBlockEntity) tileEntity);
    }

    protected CraftHopper(CraftHopper state) {
        super((CraftLootable) state);
    }

    public Inventory getSnapshotInventory() {
        return new CraftInventory((Container) this.getSnapshot());
    }

    public Inventory getInventory() {
        return (Inventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory((Container) this.getTileEntity()));
    }

    public CraftHopper copy() {
        return new CraftHopper(this);
    }
}
