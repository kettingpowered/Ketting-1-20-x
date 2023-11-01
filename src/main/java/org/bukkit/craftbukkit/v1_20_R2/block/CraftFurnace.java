package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Furnace;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Recipe;

public abstract class CraftFurnace extends CraftContainer implements Furnace {

    public CraftFurnace(World world, AbstractFurnaceBlockEntity tileEntity) {
        super(world, (BaseContainerBlockEntity) tileEntity);
    }

    protected CraftFurnace(CraftFurnace state) {
        super((CraftContainer) state);
    }

    public FurnaceInventory getSnapshotInventory() {
        return new CraftInventoryFurnace((AbstractFurnaceBlockEntity) this.getSnapshot());
    }

    public FurnaceInventory getInventory() {
        return (FurnaceInventory) (!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryFurnace((AbstractFurnaceBlockEntity) this.getTileEntity()));
    }

    public short getBurnTime() {
        return (short) ((AbstractFurnaceBlockEntity) this.getSnapshot()).litTime;
    }

    public void setBurnTime(short burnTime) {
        ((AbstractFurnaceBlockEntity) this.getSnapshot()).litTime = burnTime;
        this.data = (BlockState) this.data.setValue(AbstractFurnaceBlock.LIT, burnTime > 0);
    }

    public short getCookTime() {
        return (short) ((AbstractFurnaceBlockEntity) this.getSnapshot()).cookingProgress;
    }

    public void setCookTime(short cookTime) {
        ((AbstractFurnaceBlockEntity) this.getSnapshot()).cookingProgress = cookTime;
    }

    public int getCookTimeTotal() {
        return ((AbstractFurnaceBlockEntity) this.getSnapshot()).cookingTotalTime;
    }

    public void setCookTimeTotal(int cookTimeTotal) {
        ((AbstractFurnaceBlockEntity) this.getSnapshot()).cookingTotalTime = cookTimeTotal;
    }

    public Map getRecipesUsed() {
        Builder recipesUsed = ImmutableMap.builder();
        Iterator iterator = ((AbstractFurnaceBlockEntity) this.getSnapshot()).getRecipesUsed().object2IntEntrySet().iterator();

        while (iterator.hasNext()) {
            Entry entrySet = (Entry) iterator.next();
            Recipe recipe = Bukkit.getRecipe(CraftNamespacedKey.fromMinecraft((ResourceLocation) entrySet.getKey()));
            CookingRecipe cookingRecipe;

            if (recipe instanceof CookingRecipe && (cookingRecipe = (CookingRecipe) recipe) == (CookingRecipe) recipe) {
                recipesUsed.put(cookingRecipe, (Integer) entrySet.getValue());
            }
        }

        return recipesUsed.build();
    }

    public abstract CraftFurnace copy();
}
