package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaArmorStand extends CraftMetaItem {

    static final CraftMetaItem.ItemMetaKey ENTITY_TAG = new CraftMetaItem.ItemMetaKey("EntityTag", "entity-tag");
    CompoundTag entityTag;

    CraftMetaArmorStand(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaArmorStand) {
            CraftMetaArmorStand armorStand = (CraftMetaArmorStand) meta;

            this.entityTag = armorStand.entityTag;
        }
    }

    CraftMetaArmorStand(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaArmorStand.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaArmorStand.ENTITY_TAG.NBT).copy();
        }

    }

    CraftMetaArmorStand(Map map) {
        super(map);
    }

    void deserializeInternal(CompoundTag tag, Object context) {
        super.deserializeInternal(tag, context);
        if (tag.contains(CraftMetaArmorStand.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaArmorStand.ENTITY_TAG.NBT);
        }

    }

    void serializeInternal(Map internalTags) {
        if (this.entityTag != null && !this.entityTag.isEmpty()) {
            internalTags.put(CraftMetaArmorStand.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.entityTag != null) {
            tag.put(CraftMetaArmorStand.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    boolean applicableTo(Material type) {
        return type == Material.ARMOR_STAND;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isArmorStandEmpty();
    }

    boolean isArmorStandEmpty() {
        return this.entityTag == null;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaArmorStand)) {
            return true;
        } else {
            CraftMetaArmorStand that = (CraftMetaArmorStand) meta;

            return this.entityTag != null ? that.entityTag != null && this.entityTag.equals(that.entityTag) : this.entityTag == null;
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaArmorStand || this.isArmorStandEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.entityTag != null) {
            hash = 73 * hash + this.entityTag.hashCode();
        }

        return original != hash ? CraftMetaArmorStand.class.hashCode() ^ hash : hash;
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        return builder;
    }

    public CraftMetaArmorStand clone() {
        CraftMetaArmorStand clone = (CraftMetaArmorStand) super.clone();

        if (this.entityTag != null) {
            clone.entityTag = this.entityTag.copy();
        }

        return clone;
    }
}
