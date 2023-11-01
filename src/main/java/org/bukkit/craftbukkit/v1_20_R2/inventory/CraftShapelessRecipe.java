package org.bukkit.craftbukkit.v1_20_R2.inventory;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftShapelessRecipe extends ShapelessRecipe implements CraftRecipe {

    private net.minecraft.world.item.crafting.ShapelessRecipe recipe;

    public CraftShapelessRecipe(NamespacedKey key, ItemStack result) {
        super(key, result);
    }

    public CraftShapelessRecipe(NamespacedKey key, ItemStack result, net.minecraft.world.item.crafting.ShapelessRecipe recipe) {
        this(key, result);
        this.recipe = recipe;
    }

    public static CraftShapelessRecipe fromBukkitRecipe(ShapelessRecipe recipe) {
        if (recipe instanceof CraftShapelessRecipe) {
            return (CraftShapelessRecipe) recipe;
        } else {
            CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getKey(), recipe.getResult());

            ret.setGroup(recipe.getGroup());
            ret.setCategory(recipe.getCategory());
            Iterator iterator = recipe.getChoiceList().iterator();

            while (iterator.hasNext()) {
                RecipeChoice ingred = (RecipeChoice) iterator.next();

                ret.addIngredient(ingred);
            }

            return ret;
        }
    }

    public void addToCraftingManager() {
        List ingred = this.getChoiceList();
        NonNullList data = NonNullList.withSize(ingred.size(), Ingredient.EMPTY);

        for (int i = 0; i < ingred.size(); ++i) {
            data.set(i, this.toNMS((RecipeChoice) ingred.get(i), true));
        }

        MinecraftServer.getServer().getRecipeManager().addRecipe(new RecipeHolder(CraftNamespacedKey.toMinecraft(this.getKey()), new net.minecraft.world.item.crafting.ShapelessRecipe(this.getGroup(), CraftRecipe.getCategory(this.getCategory()), CraftItemStack.asNMSCopy(this.getResult()), data)));
    }
}
