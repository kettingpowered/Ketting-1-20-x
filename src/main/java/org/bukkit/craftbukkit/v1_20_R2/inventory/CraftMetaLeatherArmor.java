package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaLeatherArmor extends CraftMetaItem implements LeatherArmorMeta {

    private static final Set LEATHER_ARMOR_MATERIALS = Sets.newHashSet(new Material[]{Material.LEATHER_HELMET, Material.LEATHER_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS});
    static final CraftMetaItem.ItemMetaKey COLOR = new CraftMetaItem.ItemMetaKey("color");
    private Color color;

    CraftMetaLeatherArmor(CraftMetaItem meta) {
        super(meta);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        readColor(this, meta);
    }

    CraftMetaLeatherArmor(CompoundTag tag) {
        super(tag);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        readColor(this, tag);
    }

    CraftMetaLeatherArmor(Map map) {
        super(map);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        readColor(this, map);
    }

    void applyToItem(CompoundTag itemTag) {
        super.applyToItem(itemTag);
        applyColor(this, itemTag);
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isLeatherArmorEmpty();
    }

    boolean isLeatherArmorEmpty() {
        return !this.hasColor();
    }

    boolean applicableTo(Material type) {
        return CraftMetaLeatherArmor.LEATHER_ARMOR_MATERIALS.contains(type);
    }

    public CraftMetaLeatherArmor clone() {
        return (CraftMetaLeatherArmor) super.clone();
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color == null ? CraftItemFactory.DEFAULT_LEATHER_COLOR : color;
    }

    boolean hasColor() {
        return hasColor(this);
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        serialize(this, builder);
        return builder;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (meta instanceof CraftMetaLeatherArmor) {
            CraftMetaLeatherArmor that = (CraftMetaLeatherArmor) meta;

            return this.color.equals(that.color);
        } else {
            return true;
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaLeatherArmor || this.isLeatherArmorEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasColor()) {
            hash ^= this.color.hashCode();
        }

        return original != hash ? CraftMetaLeatherArmor.class.hashCode() ^ hash : hash;
    }

    static void readColor(LeatherArmorMeta meta, CraftMetaItem other) {
        CraftMetaLeatherArmor armorMeta;

        if (other instanceof CraftMetaLeatherArmor && (armorMeta = (CraftMetaLeatherArmor) other) == (CraftMetaLeatherArmor) other) {
            meta.setColor(armorMeta.color);
        }
    }

    static void readColor(LeatherArmorMeta meta, CompoundTag tag) {
        if (tag.contains(CraftMetaLeatherArmor.DISPLAY.NBT)) {
            CompoundTag display = tag.getCompound(CraftMetaLeatherArmor.DISPLAY.NBT);

            if (display.contains(CraftMetaLeatherArmor.COLOR.NBT)) {
                try {
                    meta.setColor(Color.fromRGB(display.getInt(CraftMetaLeatherArmor.COLOR.NBT)));
                } catch (IllegalArgumentException illegalargumentexception) {
                    ;
                }
            }
        }

    }

    static void readColor(LeatherArmorMeta meta, Map map) {
        meta.setColor((Color) CraftMetaItem.SerializableMeta.getObject(Color.class, map, CraftMetaLeatherArmor.COLOR.BUKKIT, true));
    }

    static boolean hasColor(LeatherArmorMeta meta) {
        return !CraftItemFactory.DEFAULT_LEATHER_COLOR.equals(meta.getColor());
    }

    static void applyColor(LeatherArmorMeta meta, CompoundTag tag) {
        if (hasColor(meta)) {
            ((CraftMetaItem) meta).setDisplayTag(tag, CraftMetaLeatherArmor.COLOR.NBT, IntTag.valueOf(meta.getColor().asRGB()));
        }

    }

    static void serialize(LeatherArmorMeta meta, Builder builder) {
        if (hasColor(meta)) {
            builder.put(CraftMetaLeatherArmor.COLOR.BUKKIT, meta.getColor());
        }

    }
}
