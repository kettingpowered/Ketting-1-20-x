package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;

public interface CraftRecipe extends Recipe {

    void addToCraftingManager();

    default Ingredient toNMS(RecipeChoice bukkit, boolean requireNotEmpty) {
        Ingredient stack;

        if (bukkit == null) {
            stack = Ingredient.EMPTY;
        } else if (bukkit instanceof MaterialChoice) {
            stack = new Ingredient(((MaterialChoice) bukkit).getChoices().stream().map((matx) -> {
                return new Ingredient.ItemValue(CraftItemStack.asNMSCopy(new ItemStack(matx)));
            }));
        } else {
            if (!(bukkit instanceof ExactChoice)) {
                throw new IllegalArgumentException("Unknown recipe stack instance " + bukkit);
            }

            stack = new Ingredient(((ExactChoice) bukkit).getChoices().stream().map((matx) -> {
                return new Ingredient.ItemValue(CraftItemStack.asNMSCopy(matx));
            }));
            stack.exact = true;
        }

        stack.getItems();
        if (requireNotEmpty) {
            Preconditions.checkArgument(stack.itemStacks.length != 0, "Recipe requires at least one non-air choice");
        }

        return stack;
    }

    static RecipeChoice toBukkit(Ingredient list) {
        list.getItems();
        if (list.itemStacks.length == 0) {
            return null;
        } else {
            ArrayList choices;
            net.minecraft.world.item.ItemStack[] anet_minecraft_world_item_itemstack;
            int i;
            net.minecraft.world.item.ItemStack i;
            int j;

            if (list.exact) {
                choices = new ArrayList(list.itemStacks.length);
                anet_minecraft_world_item_itemstack = list.itemStacks;
                i = list.itemStacks.length;

                for (j = 0; j < i; ++j) {
                    i = anet_minecraft_world_item_itemstack[j];
                    choices.add(CraftItemStack.asBukkitCopy(i));
                }

                return new ExactChoice(choices);
            } else {
                choices = new ArrayList(list.itemStacks.length);
                anet_minecraft_world_item_itemstack = list.itemStacks;
                i = list.itemStacks.length;

                for (j = 0; j < i; ++j) {
                    i = anet_minecraft_world_item_itemstack[j];
                    choices.add(CraftMagicNumbers.getMaterial(i.getItem()));
                }

                return new MaterialChoice(choices);
            }
        }
    }

    static CraftingBookCategory getCategory(org.bukkit.inventory.recipe.CraftingBookCategory bukkit) {
        return CraftingBookCategory.valueOf(bukkit.name());
    }

    static org.bukkit.inventory.recipe.CraftingBookCategory getCategory(CraftingBookCategory nms) {
        return org.bukkit.inventory.recipe.CraftingBookCategory.valueOf(nms.name());
    }

    static CookingBookCategory getCategory(org.bukkit.inventory.recipe.CookingBookCategory bukkit) {
        return CookingBookCategory.valueOf(bukkit.name());
    }

    static org.bukkit.inventory.recipe.CookingBookCategory getCategory(CookingBookCategory nms) {
        return org.bukkit.inventory.recipe.CookingBookCategory.valueOf(nms.name());
    }
}
