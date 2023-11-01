package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.entity.Axolotl.Variant;
import org.bukkit.inventory.meta.AxolotlBucketMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaAxolotlBucket extends CraftMetaItem implements AxolotlBucketMeta {

    static final CraftMetaItem.ItemMetaKey VARIANT = new CraftMetaItem.ItemMetaKey("Variant", "axolotl-variant");
    static final CraftMetaItem.ItemMetaKey ENTITY_TAG = new CraftMetaItem.ItemMetaKey("EntityTag", "entity-tag");
    private Integer variant;
    private CompoundTag entityTag;

    CraftMetaAxolotlBucket(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaAxolotlBucket) {
            CraftMetaAxolotlBucket bucket = (CraftMetaAxolotlBucket) meta;

            this.variant = bucket.variant;
            this.entityTag = bucket.entityTag;
        }
    }

    CraftMetaAxolotlBucket(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaAxolotlBucket.VARIANT.NBT, 3)) {
            this.variant = tag.getInt(CraftMetaAxolotlBucket.VARIANT.NBT);
        }

        if (tag.contains(CraftMetaAxolotlBucket.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaAxolotlBucket.ENTITY_TAG.NBT).copy();
        }

    }

    CraftMetaAxolotlBucket(Map map) {
        super(map);
        Integer integer = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaAxolotlBucket.VARIANT.BUKKIT, true);

        if (integer != null) {
            this.variant = integer;
        }

    }

    void deserializeInternal(CompoundTag tag, Object context) {
        super.deserializeInternal(tag, context);
        if (tag.contains(CraftMetaAxolotlBucket.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaAxolotlBucket.ENTITY_TAG.NBT);
        }

    }

    void serializeInternal(Map internalTags) {
        if (this.entityTag != null && !this.entityTag.isEmpty()) {
            internalTags.put(CraftMetaAxolotlBucket.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.hasVariant()) {
            tag.putInt(CraftMetaAxolotlBucket.VARIANT.NBT, this.variant);
        }

        if (this.entityTag != null) {
            tag.put(CraftMetaAxolotlBucket.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    boolean applicableTo(Material type) {
        return type == Material.AXOLOTL_BUCKET;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isBucketEmpty();
    }

    boolean isBucketEmpty() {
        return !this.hasVariant() && this.entityTag == null;
    }

    public Variant getVariant() {
        return Variant.values()[this.variant];
    }

    public void setVariant(Variant variant) {
        if (variant1 == null) {
            variant2 = Variant.LUCY;
        }

        this.variant = variant3.ordinal();
    }

    public boolean hasVariant() {
        return this.variant != null;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaAxolotlBucket)) {
            return true;
        } else {
            CraftMetaAxolotlBucket that = (CraftMetaAxolotlBucket) meta;

            if (this.hasVariant()) {
                if (!that.hasVariant() || !this.variant.equals(that.variant)) {
                    return false;
                }
            } else if (that.hasVariant()) {
                return false;
            }

            if (this.entityTag != null) {
                if (that.entityTag == null || !this.entityTag.equals(that.entityTag)) {
                    return false;
                }
            } else if (that.entityTag != null) {
                return false;
            }

            return true;
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaAxolotlBucket || this.isBucketEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasVariant()) {
            hash = 61 * hash + this.variant;
        }

        if (this.entityTag != null) {
            hash = 61 * hash + this.entityTag.hashCode();
        }

        return original != hash ? CraftMetaAxolotlBucket.class.hashCode() ^ hash : hash;
    }

    public CraftMetaAxolotlBucket clone() {
        CraftMetaAxolotlBucket clone = (CraftMetaAxolotlBucket) super.clone();

        if (this.entityTag != null) {
            clone.entityTag = this.entityTag.copy();
        }

        return clone;
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasVariant()) {
            builder.put(CraftMetaAxolotlBucket.VARIANT.BUKKIT, this.variant);
        }

        return builder;
    }
}
