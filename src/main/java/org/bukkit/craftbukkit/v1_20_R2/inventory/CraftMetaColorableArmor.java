package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.ColorableArmorMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaColorableArmor extends CraftMetaArmor implements ColorableArmorMeta {

    private static final Set LEATHER_ARMOR_MATERIALS = Sets.newHashSet(new Material[]{Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS});
    private Color color;

    CraftMetaColorableArmor(CraftMetaItem meta) {
        super(meta);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        CraftMetaLeatherArmor.readColor(this, meta);
    }

    CraftMetaColorableArmor(CompoundTag tag) {
        super(tag);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        CraftMetaLeatherArmor.readColor(this, tag);
    }

    CraftMetaColorableArmor(Map map) {
        super(map);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        CraftMetaLeatherArmor.readColor(this, map);
    }

    void applyToItem(CompoundTag itemTag) {
        super.applyToItem(itemTag);
        CraftMetaLeatherArmor.applyColor(this, itemTag);
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isLeatherArmorEmpty();
    }

    boolean isLeatherArmorEmpty() {
        return !this.hasColor();
    }

    boolean applicableTo(Material type) {
        return CraftMetaColorableArmor.LEATHER_ARMOR_MATERIALS.contains(type);
    }

    public CraftMetaColorableArmor clone() {
        CraftMetaColorableArmor clone = (CraftMetaColorableArmor) super.clone();

        clone.color = this.color;
        return clone;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color == null ? CraftItemFactory.DEFAULT_LEATHER_COLOR : color;
    }

    boolean hasColor() {
        return CraftMetaLeatherArmor.hasColor(this);
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        CraftMetaLeatherArmor.serialize(this, builder);
        return builder;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (meta instanceof CraftMetaColorableArmor) {
            CraftMetaColorableArmor that = (CraftMetaColorableArmor) meta;

            return this.color.equals(that.color);
        } else {
            return true;
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaColorableArmor || this.isLeatherArmorEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasColor()) {
            hash ^= this.color.hashCode();
        }

        return original != hash ? CraftMetaColorableArmor.class.hashCode() ^ hash : hash;
    }
}
