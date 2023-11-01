package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaSuspiciousStew extends CraftMetaItem implements SuspiciousStewMeta {

    static final CraftMetaItem.ItemMetaKey DURATION = new CraftMetaItem.ItemMetaKey("EffectDuration", "duration");
    static final CraftMetaItem.ItemMetaKey EFFECTS = new CraftMetaItem.ItemMetaKey("Effects", "effects");
    static final CraftMetaItem.ItemMetaKey ID = new CraftMetaItem.ItemMetaKey("id", "id");
    private List customEffects;

    CraftMetaSuspiciousStew(CraftMetaItem meta) {
        super(meta);
        CraftMetaSuspiciousStew stewMeta;

        if (meta instanceof CraftMetaSuspiciousStew && (stewMeta = (CraftMetaSuspiciousStew) meta) == (CraftMetaSuspiciousStew) meta) {
            if (stewMeta.hasCustomEffects()) {
                this.customEffects = new ArrayList(stewMeta.customEffects);
            }

        }
    }

    CraftMetaSuspiciousStew(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaSuspiciousStew.EFFECTS.NBT)) {
            ListTag list = tag.getList(CraftMetaSuspiciousStew.EFFECTS.NBT, 10);
            int length = list.size();

            this.customEffects = new ArrayList(length);

            for (int i = 0; i < length; ++i) {
                CompoundTag effect = list.getCompound(i);
                PotionEffectType type = PotionEffectType.getByKey(NamespacedKey.fromString(effect.getString(CraftMetaSuspiciousStew.ID.NBT)));

                if (type != null) {
                    int duration = effect.getInt(CraftMetaSuspiciousStew.DURATION.NBT);

                    this.customEffects.add(new PotionEffect(type, duration, 0));
                }
            }
        }

    }

    CraftMetaSuspiciousStew(Map map) {
        super(map);
        Iterable rawEffectList = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaSuspiciousStew.EFFECTS.BUKKIT, true);

        if (rawEffectList != null) {
            Iterator iterator = rawEffectList.iterator();

            while (iterator.hasNext()) {
                Object obj = iterator.next();

                Preconditions.checkArgument(obj instanceof PotionEffect, "Object (%s) in effect list is not valid", obj.getClass());
                this.addCustomEffect((PotionEffect) obj, true);
            }

        }
    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.customEffects != null) {
            ListTag effectList = new ListTag();

            tag.put(CraftMetaSuspiciousStew.EFFECTS.NBT, effectList);
            Iterator iterator = this.customEffects.iterator();

            while (iterator.hasNext()) {
                PotionEffect effect = (PotionEffect) iterator.next();
                CompoundTag effectData = new CompoundTag();

                effectData.putString(CraftMetaSuspiciousStew.ID.NBT, effect.getType().getKey().toString());
                effectData.putInt(CraftMetaSuspiciousStew.DURATION.NBT, effect.getDuration());
                effectList.add(effectData);
            }
        }

    }

    boolean isEmpty() {
        return super.isEmpty() && this.isStewEmpty();
    }

    boolean isStewEmpty() {
        return !this.hasCustomEffects();
    }

    boolean applicableTo(Material type) {
        return type == Material.SUSPICIOUS_STEW;
    }

    public CraftMetaSuspiciousStew clone() {
        CraftMetaSuspiciousStew clone = (CraftMetaSuspiciousStew) super.clone();

        if (this.customEffects != null) {
            clone.customEffects = new ArrayList(this.customEffects);
        }

        return clone;
    }

    public boolean hasCustomEffects() {
        return this.customEffects != null;
    }

    public List getCustomEffects() {
        return this.hasCustomEffects() ? ImmutableList.copyOf(this.customEffects) : ImmutableList.of();
    }

    public boolean addCustomEffect(PotionEffect effect, boolean overwrite) {
        Preconditions.checkArgument(effect != null, "Potion effect cannot be null");
        int index = this.indexOfEffect(effect.getType());

        if (index != -1) {
            if (overwrite) {
                PotionEffect old = (PotionEffect) this.customEffects.get(index);

                if (old.getDuration() == effect.getDuration()) {
                    return false;
                } else {
                    this.customEffects.set(index, effect);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            if (this.customEffects == null) {
                this.customEffects = new ArrayList();
            }

            this.customEffects.add(effect);
            return true;
        }
    }

    public boolean removeCustomEffect(PotionEffectType type) {
        Preconditions.checkArgument(type != null, "Potion effect type cannot be null");
        if (!this.hasCustomEffects()) {
            return false;
        } else {
            boolean changed = false;
            Iterator iterator = this.customEffects.iterator();

            while (iterator.hasNext()) {
                PotionEffect effect = (PotionEffect) iterator.next();

                if (type.equals(effect.getType())) {
                    iterator.remove();
                    changed = true;
                }
            }

            if (this.customEffects.isEmpty()) {
                this.customEffects = null;
            }

            return changed;
        }
    }

    public boolean hasCustomEffect(PotionEffectType type) {
        Preconditions.checkArgument(type != null, "Potion effect type cannot be null");
        return this.indexOfEffect(type) != -1;
    }

    private int indexOfEffect(PotionEffectType type) {
        if (!this.hasCustomEffects()) {
            return -1;
        } else {
            for (int i = 0; i < this.customEffects.size(); ++i) {
                if (((PotionEffect) this.customEffects.get(i)).getType().equals(type)) {
                    return i;
                }
            }

            return -1;
        }
    }

    public boolean clearCustomEffects() {
        boolean changed = this.hasCustomEffects();

        this.customEffects = null;
        return changed;
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasCustomEffects()) {
            hash = 73 * hash + this.customEffects.hashCode();
        }

        return original != hash ? CraftMetaSuspiciousStew.class.hashCode() ^ hash : hash;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        CraftMetaSuspiciousStew that;

        return !super.equalsCommon(meta) ? false : (meta instanceof CraftMetaSuspiciousStew && (that = (CraftMetaSuspiciousStew) meta) == (CraftMetaSuspiciousStew) meta ? (this.hasCustomEffects() ? that.hasCustomEffects() && this.customEffects.equals(that.customEffects) : !that.hasCustomEffects()) : true);
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaSuspiciousStew || this.isStewEmpty());
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasCustomEffects()) {
            builder.put(CraftMetaSuspiciousStew.EFFECTS.BUKKIT, ImmutableList.copyOf(this.customEffects));
        }

        return builder;
    }
}
