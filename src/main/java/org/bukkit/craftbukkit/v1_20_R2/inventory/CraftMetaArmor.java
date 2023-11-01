package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaArmor extends CraftMetaItem implements ArmorMeta {

    private static final Set ARMOR_MATERIALS = Sets.newHashSet(new Material[]{Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS, Material.TURTLE_HELMET});
    static final CraftMetaItem.ItemMetaKey TRIM = new CraftMetaItem.ItemMetaKey("Trim", "trim");
    static final CraftMetaItem.ItemMetaKey TRIM_MATERIAL = new CraftMetaItem.ItemMetaKey("material");
    static final CraftMetaItem.ItemMetaKey TRIM_PATTERN = new CraftMetaItem.ItemMetaKey("pattern");
    private ArmorTrim trim;

    CraftMetaArmor(CraftMetaItem meta) {
        super(meta);
        CraftMetaArmor armorMeta;

        if (meta instanceof CraftMetaArmor && (armorMeta = (CraftMetaArmor) meta) == (CraftMetaArmor) meta) {
            this.trim = armorMeta.trim;
        }

    }

    CraftMetaArmor(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaArmor.TRIM.NBT)) {
            CompoundTag trimCompound = tag.getCompound(CraftMetaArmor.TRIM.NBT);

            if (trimCompound.contains(CraftMetaArmor.TRIM_MATERIAL.NBT) && trimCompound.contains(CraftMetaArmor.TRIM_PATTERN.NBT)) {
                TrimMaterial trimMaterial = (TrimMaterial) Registry.TRIM_MATERIAL.get(NamespacedKey.fromString(trimCompound.getString(CraftMetaArmor.TRIM_MATERIAL.NBT)));
                TrimPattern trimPattern = (TrimPattern) Registry.TRIM_PATTERN.get(NamespacedKey.fromString(trimCompound.getString(CraftMetaArmor.TRIM_PATTERN.NBT)));

                this.trim = new ArmorTrim(trimMaterial, trimPattern);
            }
        }

    }

    CraftMetaArmor(Map map) {
        super(map);
        Map trimData = (Map) CraftMetaItem.SerializableMeta.getObject(Map.class, map, CraftMetaArmor.TRIM.BUKKIT, true);

        if (trimData != null) {
            String materialKeyString = CraftMetaItem.SerializableMeta.getString(trimData, CraftMetaArmor.TRIM_MATERIAL.BUKKIT, true);
            String patternKeyString = CraftMetaItem.SerializableMeta.getString(trimData, CraftMetaArmor.TRIM_PATTERN.BUKKIT, true);

            if (materialKeyString != null && patternKeyString != null) {
                NamespacedKey materialKey = NamespacedKey.fromString(materialKeyString);
                NamespacedKey patternKey = NamespacedKey.fromString(patternKeyString);

                if (materialKey != null && patternKey != null) {
                    TrimMaterial trimMaterial = (TrimMaterial) Registry.TRIM_MATERIAL.get(materialKey);
                    TrimPattern trimPattern = (TrimPattern) Registry.TRIM_PATTERN.get(patternKey);

                    if (trimMaterial != null && trimPattern != null) {
                        this.trim = new ArmorTrim(trimMaterial, trimPattern);
                    }
                }
            }
        }

    }

    void applyToItem(CompoundTag itemTag) {
        super.applyToItem(itemTag);
        if (this.hasTrim()) {
            CompoundTag trimCompound = new CompoundTag();

            trimCompound.putString(CraftMetaArmor.TRIM_MATERIAL.NBT, this.trim.getMaterial().getKey().toString());
            trimCompound.putString(CraftMetaArmor.TRIM_PATTERN.NBT, this.trim.getPattern().getKey().toString());
            itemTag.put(CraftMetaArmor.TRIM.NBT, trimCompound);
        }

    }

    boolean applicableTo(Material type) {
        return CraftMetaArmor.ARMOR_MATERIALS.contains(type);
    }

    boolean equalsCommon(CraftMetaItem that) {
        CraftMetaArmor armorMeta;

        return !super.equalsCommon(that) ? false : (that instanceof CraftMetaArmor && (armorMeta = (CraftMetaArmor) that) == (CraftMetaArmor) that ? Objects.equals(this.trim, armorMeta.trim) : true);
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaArmor || this.isArmorEmpty());
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isArmorEmpty();
    }

    private boolean isArmorEmpty() {
        return !this.hasTrim();
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasTrim()) {
            hash = 61 * hash + this.trim.hashCode();
        }

        return original != hash ? CraftMetaArmor.class.hashCode() ^ hash : hash;
    }

    public CraftMetaArmor clone() {
        CraftMetaArmor meta = (CraftMetaArmor) super.clone();

        meta.trim = this.trim;
        return meta;
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasTrim()) {
            HashMap trimData = new HashMap();

            trimData.put(CraftMetaArmor.TRIM_MATERIAL.BUKKIT, this.trim.getMaterial().getKey().toString());
            trimData.put(CraftMetaArmor.TRIM_PATTERN.BUKKIT, this.trim.getPattern().getKey().toString());
            builder.put(CraftMetaArmor.TRIM.BUKKIT, trimData);
        }

        return builder;
    }

    public boolean hasTrim() {
        return this.trim != null;
    }

    public void setTrim(ArmorTrim trim) {
        this.trim = trim;
    }

    public ArmorTrim getTrim() {
        return this.trim;
    }
}
