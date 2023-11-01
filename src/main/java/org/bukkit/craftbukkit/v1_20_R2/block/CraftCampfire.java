package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Campfire;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class CraftCampfire extends CraftBlockEntityState implements Campfire {

    public CraftCampfire(World world, CampfireBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftCampfire(CraftCampfire state) {
        super((CraftBlockEntityState) state);
    }

    public int getSize() {
        return ((CampfireBlockEntity) this.getSnapshot()).getItems().size();
    }

    public ItemStack getItem(int index) {
        net.minecraft.world.item.ItemStack item = (net.minecraft.world.item.ItemStack) ((CampfireBlockEntity) this.getSnapshot()).getItems().get(index);

        return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
    }

    public void setItem(int index, ItemStack item) {
        ((CampfireBlockEntity) this.getSnapshot()).getItems().set(index, CraftItemStack.asNMSCopy(item));
    }

    public int getCookTime(int index) {
        return ((CampfireBlockEntity) this.getSnapshot()).cookingProgress[index];
    }

    public void setCookTime(int index, int cookTime) {
        ((CampfireBlockEntity) this.getSnapshot()).cookingProgress[index] = cookTime;
    }

    public int getCookTimeTotal(int index) {
        return ((CampfireBlockEntity) this.getSnapshot()).cookingTime[index];
    }

    public void setCookTimeTotal(int index, int cookTimeTotal) {
        ((CampfireBlockEntity) this.getSnapshot()).cookingTime[index] = cookTimeTotal;
    }

    public CraftCampfire copy() {
        return new CraftCampfire(this);
    }
}
