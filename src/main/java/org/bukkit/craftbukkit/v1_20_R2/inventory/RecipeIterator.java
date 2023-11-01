package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.inventory.Recipe;

public class RecipeIterator implements Iterator {

    private final Iterator recipes;
    private Iterator current;

    public RecipeIterator() {
        this.recipes = MinecraftServer.getServer().getRecipeManager().recipes.entrySet().iterator();
    }

    public boolean hasNext() {
        if (this.current != null && this.current.hasNext()) {
            return true;
        } else if (this.recipes.hasNext()) {
            this.current = ((Object2ObjectLinkedOpenHashMap) ((Entry) this.recipes.next()).getValue()).values().iterator();
            return this.hasNext();
        } else {
            return false;
        }
    }

    public Recipe next() {
        if (this.current != null && this.current.hasNext()) {
            return ((RecipeHolder) this.current.next()).toBukkitRecipe();
        } else {
            this.current = ((Object2ObjectLinkedOpenHashMap) ((Entry) this.recipes.next()).getValue()).values().iterator();
            return this.next();
        }
    }

    public void remove() {
        Preconditions.checkState(this.current != null, "next() not yet called");
        this.current.remove();
    }
}
