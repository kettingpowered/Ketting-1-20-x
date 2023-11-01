package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaKnowledgeBook extends CraftMetaItem implements KnowledgeBookMeta {

    static final CraftMetaItem.ItemMetaKey BOOK_RECIPES = new CraftMetaItem.ItemMetaKey("Recipes");
    static final int MAX_RECIPES = 32767;
    protected List recipes = new ArrayList();

    CraftMetaKnowledgeBook(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaKnowledgeBook) {
            CraftMetaKnowledgeBook bookMeta = (CraftMetaKnowledgeBook) meta;

            this.recipes.addAll(bookMeta.recipes);
        }

    }

    CraftMetaKnowledgeBook(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaKnowledgeBook.BOOK_RECIPES.NBT)) {
            ListTag pages = tag.getList(CraftMetaKnowledgeBook.BOOK_RECIPES.NBT, 8);

            for (int i = 0; i < pages.size(); ++i) {
                String recipe = pages.getString(i);

                this.addRecipe(CraftNamespacedKey.fromString(recipe));
            }
        }

    }

    CraftMetaKnowledgeBook(Map map) {
        super(map);
        Iterable pages = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaKnowledgeBook.BOOK_RECIPES.BUKKIT, true);

        if (pages != null) {
            Iterator iterator = pages.iterator();

            while (iterator.hasNext()) {
                Object page = iterator.next();

                if (page instanceof String) {
                    this.addRecipe(CraftNamespacedKey.fromString((String) page));
                }
            }
        }

    }

    void applyToItem(CompoundTag itemData) {
        super.applyToItem(itemData);
        if (this.hasRecipes()) {
            ListTag list = new ListTag();
            Iterator iterator = this.recipes.iterator();

            while (iterator.hasNext()) {
                NamespacedKey recipe = (NamespacedKey) iterator.next();

                list.add(StringTag.valueOf(recipe.toString()));
            }

            itemData.put(CraftMetaKnowledgeBook.BOOK_RECIPES.NBT, list);
        }

    }

    boolean isEmpty() {
        return super.isEmpty() && this.isBookEmpty();
    }

    boolean isBookEmpty() {
        return !this.hasRecipes();
    }

    boolean applicableTo(Material type) {
        return type == Material.KNOWLEDGE_BOOK;
    }

    public boolean hasRecipes() {
        return !this.recipes.isEmpty();
    }

    public void addRecipe(NamespacedKey... recipes) {
        NamespacedKey[] anamespacedkey = recipes;
        int i = recipes.length;

        for (int j = 0; j < i; ++j) {
            NamespacedKey recipe = anamespacedkey[j];

            if (recipe != null) {
                if (this.recipes.size() >= 32767) {
                    return;
                }

                this.recipes.add(recipe);
            }
        }

    }

    public List getRecipes() {
        return Collections.unmodifiableList(this.recipes);
    }

    public void setRecipes(List recipes) {
        this.recipes.clear();
        Iterator iterator = recipes.iterator();

        while (iterator.hasNext()) {
            NamespacedKey recipe = (NamespacedKey) iterator.next();

            this.addRecipe(recipe);
        }

    }

    public CraftMetaKnowledgeBook clone() {
        CraftMetaKnowledgeBook meta = (CraftMetaKnowledgeBook) super.clone();

        meta.recipes = new ArrayList(this.recipes);
        return meta;
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasRecipes()) {
            hash = 61 * hash + 17 * this.recipes.hashCode();
        }

        return original != hash ? CraftMetaKnowledgeBook.class.hashCode() ^ hash : hash;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaKnowledgeBook)) {
            return true;
        } else {
            CraftMetaKnowledgeBook that = (CraftMetaKnowledgeBook) meta;

            return this.hasRecipes() ? that.hasRecipes() && this.recipes.equals(that.recipes) : !that.hasRecipes();
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaKnowledgeBook || this.isBookEmpty());
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasRecipes()) {
            ArrayList recipesString = new ArrayList();
            Iterator iterator = this.recipes.iterator();

            while (iterator.hasNext()) {
                NamespacedKey recipe = (NamespacedKey) iterator.next();

                recipesString.add(recipe.toString());
            }

            builder.put(CraftMetaKnowledgeBook.BOOK_RECIPES.BUKKIT, recipesString);
        }

        return builder;
    }
}
