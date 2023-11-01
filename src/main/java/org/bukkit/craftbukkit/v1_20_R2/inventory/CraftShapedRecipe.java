package org.bukkit.craftbukkit.v1_20_R2.inventory;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe {

    private net.minecraft.world.item.crafting.ShapedRecipe recipe;

    public CraftShapedRecipe(NamespacedKey key, ItemStack result) {
        super(key, result);
    }

    public CraftShapedRecipe(NamespacedKey key, ItemStack result, net.minecraft.world.item.crafting.ShapedRecipe recipe) {
        this(key, result);
        this.recipe = recipe;
    }

    public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
        if (recipe instanceof CraftShapedRecipe) {
            return (CraftShapedRecipe) recipe;
        } else {
            CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getKey(), recipe.getResult());

            ret.setGroup(recipe.getGroup());
            ret.setCategory(recipe.getCategory());
            String[] shape = recipe.getShape();

            ret.shape(shape);
            Map ingredientMap = recipe.getChoiceMap();
            Iterator iterator = ingredientMap.keySet().iterator();

            while (iterator.hasNext()) {
                char c = (Character) iterator.next();
                RecipeChoice stack = (RecipeChoice) ingredientMap.get(c);

                if (stack != null) {
                    ret.setIngredient(c, stack);
                }
            }

            return ret;
        }
    }

    public void addToCraftingManager() {
        String[] shape = this.getShape();
        Map ingred = this.getChoiceMap();
        int width = shape[0].length();
        NonNullList data = NonNullList.withSize(shape.length * width, Ingredient.EMPTY);

        for (int i = 0; i < shape.length; ++i) {
            String row = shape[i];

            for (int j = 0; j < row.length(); ++j) {
                data.set(i * width + j, this.toNMS((RecipeChoice) ingred.get(row.charAt(j)), false));
            }
        }

        MinecraftServer.getServer().getRecipeManager().addRecipe(new RecipeHolder(CraftNamespacedKey.toMinecraft(this.getKey()), new net.minecraft.world.item.crafting.ShapedRecipe(this.getGroup(), CraftRecipe.getCategory(this.getCategory()), width, shape.length, data, CraftItemStack.asNMSCopy(this.getResult()))));
    }
}
