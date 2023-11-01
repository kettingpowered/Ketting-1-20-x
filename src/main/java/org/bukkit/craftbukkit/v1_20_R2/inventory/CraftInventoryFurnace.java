package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryFurnace extends CraftInventory implements FurnaceInventory {

    public CraftInventoryFurnace(AbstractFurnaceBlockEntity inventory) {
        super(inventory);
    }

    public ItemStack getResult() {
        return this.getItem(2);
    }

    public ItemStack getFuel() {
        return this.getItem(1);
    }

    public ItemStack getSmelting() {
        return this.getItem(0);
    }

    public void setFuel(ItemStack stack) {
        this.setItem(1, stack);
    }

    public void setResult(ItemStack stack) {
        this.setItem(2, stack);
    }

    public void setSmelting(ItemStack stack) {
        this.setItem(0, stack);
    }

    public Furnace getHolder() {
        return (Furnace) this.inventory.getOwner();
    }
}
