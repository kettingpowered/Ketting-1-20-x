package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaEntityTag extends CraftMetaItem {

    private static final Set ENTITY_TAGGABLE_MATERIALS = Sets.newHashSet(new Material[]{Material.COD_BUCKET, Material.PUFFERFISH_BUCKET, Material.SALMON_BUCKET, Material.ITEM_FRAME, Material.GLOW_ITEM_FRAME, Material.PAINTING});
    static final CraftMetaItem.ItemMetaKey ENTITY_TAG = new CraftMetaItem.ItemMetaKey("EntityTag", "entity-tag");
    CompoundTag entityTag;

    CraftMetaEntityTag(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaEntityTag) {
            CraftMetaEntityTag entity = (CraftMetaEntityTag) meta;

            this.entityTag = entity.entityTag;
        }
    }

    CraftMetaEntityTag(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaEntityTag.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaEntityTag.ENTITY_TAG.NBT).copy();
        }

    }

    CraftMetaEntityTag(Map map) {
        super(map);
    }

    void deserializeInternal(CompoundTag tag, Object context) {
        super.deserializeInternal(tag, context);
        if (tag.contains(CraftMetaEntityTag.ENTITY_TAG.NBT)) {
            this.entityTag = tag.getCompound(CraftMetaEntityTag.ENTITY_TAG.NBT);
        }

    }

    void serializeInternal(Map internalTags) {
        if (this.entityTag != null && !this.entityTag.isEmpty()) {
            internalTags.put(CraftMetaEntityTag.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.entityTag != null) {
            tag.put(CraftMetaEntityTag.ENTITY_TAG.NBT, this.entityTag);
        }

    }

    boolean applicableTo(Material type) {
        return CraftMetaEntityTag.ENTITY_TAGGABLE_MATERIALS.contains(type);
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isEntityTagEmpty();
    }

    boolean isEntityTagEmpty() {
        return this.entityTag == null;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaEntityTag)) {
            return true;
        } else {
            CraftMetaEntityTag that = (CraftMetaEntityTag) meta;

            return this.entityTag != null ? that.entityTag != null && this.entityTag.equals(that.entityTag) : this.entityTag == null;
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaEntityTag || this.isEntityTagEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.entityTag != null) {
            hash = 73 * hash + this.entityTag.hashCode();
        }

        return original != hash ? CraftMetaEntityTag.class.hashCode() ^ hash : hash;
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        return builder;
    }

    public CraftMetaEntityTag clone() {
        CraftMetaEntityTag clone = (CraftMetaEntityTag) super.clone();

        if (this.entityTag != null) {
            clone.entityTag = this.entityTag.copy();
        }

        return clone;
    }
}
