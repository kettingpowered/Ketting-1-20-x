package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaEnchantedBook extends CraftMetaItem implements EnchantmentStorageMeta {

    static final CraftMetaItem.ItemMetaKey STORED_ENCHANTMENTS = new CraftMetaItem.ItemMetaKey("StoredEnchantments", "stored-enchants");
    private Map enchantments;

    CraftMetaEnchantedBook(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaEnchantedBook) {
            CraftMetaEnchantedBook that = (CraftMetaEnchantedBook) meta;

            if (that.hasEnchants()) {
                this.enchantments = new LinkedHashMap(that.enchantments);
            }

        }
    }

    CraftMetaEnchantedBook(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaEnchantedBook.STORED_ENCHANTMENTS.NBT)) {
            this.enchantments = buildEnchantments(tag, CraftMetaEnchantedBook.STORED_ENCHANTMENTS);
        }
    }

    CraftMetaEnchantedBook(Map map) {
        super(map);
        this.enchantments = buildEnchantments(map, CraftMetaEnchantedBook.STORED_ENCHANTMENTS);
    }

    void applyToItem(CompoundTag itemTag) {
        super.applyToItem(itemTag);
        applyEnchantments(this.enchantments, itemTag, CraftMetaEnchantedBook.STORED_ENCHANTMENTS);
    }

    boolean applicableTo(Material type) {
        return type == Material.ENCHANTED_BOOK;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isEnchantedEmpty();
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaEnchantedBook)) {
            return true;
        } else {
            CraftMetaEnchantedBook that = (CraftMetaEnchantedBook) meta;

            return this.hasStoredEnchants() ? that.hasStoredEnchants() && this.enchantments.equals(that.enchantments) : !that.hasStoredEnchants();
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaEnchantedBook || this.isEnchantedEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasStoredEnchants()) {
            hash = 61 * hash + this.enchantments.hashCode();
        }

        return original != hash ? CraftMetaEnchantedBook.class.hashCode() ^ hash : hash;
    }

    public CraftMetaEnchantedBook clone() {
        CraftMetaEnchantedBook meta = (CraftMetaEnchantedBook) super.clone();

        if (this.enchantments != null) {
            meta.enchantments = new LinkedHashMap(this.enchantments);
        }

        return meta;
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        serializeEnchantments(this.enchantments, builder, CraftMetaEnchantedBook.STORED_ENCHANTMENTS);
        return builder;
    }

    boolean isEnchantedEmpty() {
        return !this.hasStoredEnchants();
    }

    public boolean hasStoredEnchant(Enchantment ench) {
        return this.hasStoredEnchants() && this.enchantments.containsKey(ench);
    }

    public int getStoredEnchantLevel(Enchantment ench) {
        Integer level = this.hasStoredEnchants() ? (Integer) this.enchantments.get(ench) : null;

        return level == null ? 0 : level;
    }

    public Map getStoredEnchants() {
        return this.hasStoredEnchants() ? ImmutableMap.copyOf(this.enchantments) : ImmutableMap.of();
    }

    public boolean addStoredEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
        if (this.enchantments == null) {
            this.enchantments = new LinkedHashMap(4);
        }

        if (!ignoreRestrictions && (level < ench.getStartLevel() || level > ench.getMaxLevel())) {
            return false;
        } else {
            Integer old = (Integer) this.enchantments.put(ench, level);

            return old == null || old != level;
        }
    }

    public boolean removeStoredEnchant(Enchantment ench) {
        return this.hasStoredEnchants() && this.enchantments.remove(ench) != null;
    }

    public boolean hasStoredEnchants() {
        return this.enchantments != null && !this.enchantments.isEmpty();
    }

    public boolean hasConflictingStoredEnchant(Enchantment ench) {
        return checkConflictingEnchants(this.enchantments, ench);
    }
}
