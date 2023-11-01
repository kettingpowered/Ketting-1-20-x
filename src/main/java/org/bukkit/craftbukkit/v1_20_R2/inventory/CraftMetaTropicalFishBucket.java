package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftTropicalFish;
import org.bukkit.entity.TropicalFish.Pattern;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaTropicalFishBucket extends CraftMetaItem implements TropicalFishBucketMeta {

    static final CraftMetaItem.ItemMetaKey VARIANT = new CraftMetaItem.ItemMetaKey("BucketVariantTag", "fish-variant");
    static final CraftMetaItem.ItemMetaKey ENTITY_TAG = new CraftMetaItem.ItemMetaKey("EntityTag", "entity-tag");
    private Integer variant;
    private CompoundTag entityTag;

    CraftMetaTropicalFishBucket(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaTropicalFishBucket) {
            CraftMetaTropicalFishBucket bucket = (CraftMetaTropicalFishBucket) meta;

            this.variant = bucket.variant;
            this.entityTag = bucket.entityTag;
        }
    }

    CraftMetaTropicalFishBucket(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaTropicalFishBucket.VARIANT.NBT, 3)) {
            this.variant = tag.getInt(CraftMetaTropicalFishBucket.VARIANT.NBT);
        }

        if (tag.contains(CraftMetaTropicalFishBucket.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaTropicalFishBucket.ENTITY_TAG.NBT).copy();
        }

    }

    CraftMetaTropicalFishBucket(Map map) {
        super(map);
        Integer integer = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaTropicalFishBucket.VARIANT.BUKKIT, true);

        if (integer != null) {
            this.variant = integer;
        }

    }

    void deserializeInternal(CompoundTag tag, Object context) {
        super.deserializeInternal(tag, context);
        if (tag.contains(CraftMetaTropicalFishBucket.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaTropicalFishBucket.ENTITY_TAG.NBT);
        }

    }

    void serializeInternal(Map internalTags) {
        if (this.entityTag != null && !this.entityTag.isEmpty()) {
            internalTags.put(CraftMetaTropicalFishBucket.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.hasVariant()) {
            tag.putInt(CraftMetaTropicalFishBucket.VARIANT.NBT, this.variant);
        }

        if (this.entityTag != null) {
            tag.put(CraftMetaTropicalFishBucket.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    boolean applicableTo(Material type) {
        return type == Material.TROPICAL_FISH_BUCKET;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isBucketEmpty();
    }

    boolean isBucketEmpty() {
        return !this.hasVariant() && this.entityTag == null;
    }

    public DyeColor getPatternColor() {
        return CraftTropicalFish.getPatternColor(this.variant);
    }

    public void setPatternColor(DyeColor color) {
        if (this.variant == null) {
            this.variant = 0;
        }

        this.variant = CraftTropicalFish.getData(color, this.getPatternColor(), this.getPattern());
    }

    public DyeColor getBodyColor() {
        return CraftTropicalFish.getBodyColor(this.variant);
    }

    public void setBodyColor(DyeColor color) {
        if (this.variant == null) {
            this.variant = 0;
        }

        this.variant = CraftTropicalFish.getData(this.getPatternColor(), color, this.getPattern());
    }

    public Pattern getPattern() {
        return CraftTropicalFish.getPattern(this.variant);
    }

    public void setPattern(Pattern pattern) {
        if (this.variant == null) {
            this.variant = 0;
        }

        this.variant = CraftTropicalFish.getData(this.getPatternColor(), this.getBodyColor(), pattern);
    }

    public boolean hasVariant() {
        return this.variant != null;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaTropicalFishBucket)) {
            return true;
        } else {
            CraftMetaTropicalFishBucket that = (CraftMetaTropicalFishBucket) meta;

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
        return super.notUncommon(meta) && (meta instanceof CraftMetaTropicalFishBucket || this.isBucketEmpty());
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

        return original != hash ? CraftMetaTropicalFishBucket.class.hashCode() ^ hash : hash;
    }

    public CraftMetaTropicalFishBucket clone() {
        CraftMetaTropicalFishBucket clone = (CraftMetaTropicalFishBucket) super.clone();

        if (this.entityTag != null) {
            clone.entityTag = this.entityTag.copy();
        }

        return clone;
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasVariant()) {
            builder.put(CraftMetaTropicalFishBucket.VARIANT.BUKKIT, this.variant);
        }

        return builder;
    }
}
