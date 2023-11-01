package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory {

    private final Container resultInventory;

    public CraftInventoryCrafting(Container inventory, Container resultInventory) {
        super(inventory);
        this.resultInventory = resultInventory;
    }

    public Container getResultInventory() {
        return this.resultInventory;
    }

    public Container getMatrixInventory() {
        return this.inventory;
    }

    public int getSize() {
        return this.getResultInventory().getContainerSize() + this.getMatrixInventory().getContainerSize();
    }

    public void setContents(ItemStack[] items) {
        Preconditions.checkArgument(items.length <= this.getSize(), "Invalid inventory size (%s); expected %s or less", items.length, this.getSize());
        this.setContents(items[0], (ItemStack[]) Arrays.copyOfRange(items, 1, items.length));
    }

    public ItemStack[] getContents() {
        ItemStack[] items = new ItemStack[this.getSize()];
        List mcResultItems = this.getResultInventory().getContents();
        boolean i = false;

        int i;

        for (i = 0; i < mcResultItems.size(); ++i) {
            items[i] = CraftItemStack.asCraftMirror((net.minecraft.world.item.ItemStack) mcResultItems.get(i));
        }

        List mcItems = this.getMatrixInventory().getContents();

        for (int j = 0; j < mcItems.size(); ++j) {
            items[i + j] = CraftItemStack.asCraftMirror((net.minecraft.world.item.ItemStack) mcItems.get(j));
        }

        return items;
    }

    public void setContents(ItemStack result, ItemStack[] contents) {
        this.setResult(result);
        this.setMatrix(contents);
    }

    public CraftItemStack getItem(int index) {
        net.minecraft.world.item.ItemStack item;

        if (index < this.getResultInventory().getContainerSize()) {
            item = this.getResultInventory().getItem(index);
            return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
        } else {
            item = this.getMatrixInventory().getItem(index - this.getResultInventory().getContainerSize());
            return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
        }
    }

    public void setItem(int index, ItemStack item) {
        if (index < this.getResultInventory().getContainerSize()) {
            this.getResultInventory().setItem(index, CraftItemStack.asNMSCopy(item));
        } else {
            this.getMatrixInventory().setItem(index - this.getResultInventory().getContainerSize(), CraftItemStack.asNMSCopy(item));
        }

    }

    public ItemStack[] getMatrix() {
        List matrix = this.getMatrixInventory().getContents();

        return this.asCraftMirror(matrix);
    }

    public ItemStack getResult() {
        net.minecraft.world.item.ItemStack item = this.getResultInventory().getItem(0);

        return !item.isEmpty() ? CraftItemStack.asCraftMirror(item) : null;
    }

    public void setMatrix(ItemStack[] contents) {
        Preconditions.checkArgument(contents.length <= this.getMatrixInventory().getContainerSize(), "Invalid inventory size (%s); expected %s or less", contents.length, this.getMatrixInventory().getContainerSize());

        for (int i = 0; i < this.getMatrixInventory().getContainerSize(); ++i) {
            if (i < contents.length) {
                this.getMatrixInventory().setItem(i, CraftItemStack.asNMSCopy(contents[i]));
            } else {
                this.getMatrixInventory().setItem(i, net.minecraft.world.item.ItemStack.EMPTY);
            }
        }

    }

    public void setResult(ItemStack item) {
        List contents = this.getResultInventory().getContents();

        contents.set(0, CraftItemStack.asNMSCopy(item));
    }

    public Recipe getRecipe() {
        RecipeHolder recipe = this.getInventory().getCurrentRecipe();

        return recipe == null ? null : recipe.toBukkitRecipe();
    }
}
