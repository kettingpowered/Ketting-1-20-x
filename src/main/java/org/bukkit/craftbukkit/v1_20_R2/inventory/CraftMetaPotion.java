package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionType;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionUtil;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaPotion extends CraftMetaItem implements PotionMeta {

    private static final Set POTION_MATERIALS = Sets.newHashSet(new Material[]{Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.TIPPED_ARROW});
    static final CraftMetaItem.ItemMetaKey AMPLIFIER = new CraftMetaItem.ItemMetaKey("amplifier", "amplifier");
    static final CraftMetaItem.ItemMetaKey AMBIENT = new CraftMetaItem.ItemMetaKey("ambient", "ambient");
    static final CraftMetaItem.ItemMetaKey DURATION = new CraftMetaItem.ItemMetaKey("duration", "duration");
    static final CraftMetaItem.ItemMetaKey SHOW_PARTICLES = new CraftMetaItem.ItemMetaKey("show_particles", "has-particles");
    static final CraftMetaItem.ItemMetaKey SHOW_ICON = new CraftMetaItem.ItemMetaKey("show_icon", "has-icon");
    static final CraftMetaItem.ItemMetaKey POTION_EFFECTS = new CraftMetaItem.ItemMetaKey("custom_potion_effects", "custom-effects");
    static final CraftMetaItem.ItemMetaKey POTION_COLOR = new CraftMetaItem.ItemMetaKey("CustomPotionColor", "custom-color");
    static final CraftMetaItem.ItemMetaKey ID = new CraftMetaItem.ItemMetaKey("id", "potion-id");
    static final CraftMetaItem.ItemMetaKey DEFAULT_POTION = new CraftMetaItem.ItemMetaKey("Potion", "potion-type");
    private PotionType type;
    private List customEffects;
    private Color color;

    CraftMetaPotion(CraftMetaItem meta) {
        super(meta);
        this.type = PotionType.UNCRAFTABLE;
        CraftMetaPotion potionMeta;

        if (meta instanceof CraftMetaPotion && (potionMeta = (CraftMetaPotion) meta) == (CraftMetaPotion) meta) {
            this.type = potionMeta.type;
            this.color = potionMeta.color;
            if (potionMeta.hasCustomEffects()) {
                this.customEffects = new ArrayList(potionMeta.customEffects);
            }

        }
    }

    CraftMetaPotion(CompoundTag tag) {
        super(tag);
        this.type = PotionType.UNCRAFTABLE;
        if (tag.contains(CraftMetaPotion.DEFAULT_POTION.NBT)) {
            this.type = CraftPotionType.stringToBukkit(tag.getString(CraftMetaPotion.DEFAULT_POTION.NBT));
            if (this.type == null) {
                this.type = PotionType.UNCRAFTABLE;
            }
        }

        if (tag.contains(CraftMetaPotion.POTION_COLOR.NBT)) {
            try {
                this.color = Color.fromRGB(tag.getInt(CraftMetaPotion.POTION_COLOR.NBT));
            } catch (IllegalArgumentException illegalargumentexception) {
                ;
            }
        }

        if (tag.contains(CraftMetaPotion.POTION_EFFECTS.NBT)) {
            ListTag list = tag.getList(CraftMetaPotion.POTION_EFFECTS.NBT, 10);
            int length = list.size();

            this.customEffects = new ArrayList(length);

            for (int i = 0; i < length; ++i) {
                CompoundTag effect = list.getCompound(i);
                PotionEffectType type = PotionEffectType.getByKey(NamespacedKey.fromString(effect.getString(CraftMetaPotion.ID.NBT)));

                if (type != null) {
                    byte amp = effect.getByte(CraftMetaPotion.AMPLIFIER.NBT);
                    int duration = effect.getInt(CraftMetaPotion.DURATION.NBT);
                    boolean ambient = effect.getBoolean(CraftMetaPotion.AMBIENT.NBT);
                    boolean particles = effect.contains(CraftMetaPotion.SHOW_PARTICLES.NBT, 1) ? effect.getBoolean(CraftMetaPotion.SHOW_PARTICLES.NBT) : true;
                    boolean icon = effect.contains(CraftMetaPotion.SHOW_ICON.NBT, 1) ? effect.getBoolean(CraftMetaPotion.SHOW_ICON.NBT) : particles;

                    this.customEffects.add(new PotionEffect(type, duration, amp, ambient, particles, icon));
                }
            }
        }

    }

    CraftMetaPotion(Map map) {
        super(map);
        this.type = PotionType.UNCRAFTABLE;
        String typeString = CraftMetaItem.SerializableMeta.getString(map, CraftMetaPotion.DEFAULT_POTION.BUKKIT, true);

        if (typeString != null) {
            this.type = CraftPotionType.stringToBukkit(typeString);
        }

        if (this.type == null) {
            this.type = PotionType.UNCRAFTABLE;
        }

        Color color = (Color) CraftMetaItem.SerializableMeta.getObject(Color.class, map, CraftMetaPotion.POTION_COLOR.BUKKIT, true);

        if (color != null) {
            this.setColor(color);
        }

        Iterable rawEffectList = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaPotion.POTION_EFFECTS.BUKKIT, true);

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
        tag.putString(CraftMetaPotion.DEFAULT_POTION.NBT, CraftPotionType.bukkitToString(this.type));
        if (this.hasColor()) {
            tag.putInt(CraftMetaPotion.POTION_COLOR.NBT, this.color.asRGB());
        }

        if (this.customEffects != null) {
            ListTag effectList = new ListTag();

            tag.put(CraftMetaPotion.POTION_EFFECTS.NBT, effectList);
            Iterator iterator = this.customEffects.iterator();

            while (iterator.hasNext()) {
                PotionEffect effect = (PotionEffect) iterator.next();
                CompoundTag effectData = new CompoundTag();

                effectData.putString(CraftMetaPotion.ID.NBT, effect.getType().getKey().toString());
                effectData.putByte(CraftMetaPotion.AMPLIFIER.NBT, (byte) effect.getAmplifier());
                effectData.putInt(CraftMetaPotion.DURATION.NBT, effect.getDuration());
                effectData.putBoolean(CraftMetaPotion.AMBIENT.NBT, effect.isAmbient());
                effectData.putBoolean(CraftMetaPotion.SHOW_PARTICLES.NBT, effect.hasParticles());
                effectData.putBoolean(CraftMetaPotion.SHOW_ICON.NBT, effect.hasIcon());
                effectList.add(effectData);
            }
        }

    }

    boolean isEmpty() {
        return super.isEmpty() && this.isPotionEmpty();
    }

    boolean isPotionEmpty() {
        return this.type == PotionType.UNCRAFTABLE && !this.hasCustomEffects() && !this.hasColor();
    }

    boolean applicableTo(Material type) {
        return CraftMetaPotion.POTION_MATERIALS.contains(type);
    }

    public CraftMetaPotion clone() {
        CraftMetaPotion clone = (CraftMetaPotion) super.clone();

        clone.type = this.type;
        if (this.customEffects != null) {
            clone.customEffects = new ArrayList(this.customEffects);
        }

        return clone;
    }

    public void setBasePotionData(PotionData data) {
        Preconditions.checkArgument(data != null, "PotionData cannot be null");
        this.type = CraftPotionUtil.fromBukkit(data);
    }

    public PotionData getBasePotionData() {
        return CraftPotionUtil.toBukkit(this.type);
    }

    public void setBasePotionType(@NotNull PotionType potionType) {
        Preconditions.checkArgument(potionType != null, "PotionType cannot be null use PotionType.UNCRAFTABLE to represent no effect instead.");
        this.type = potionType;
    }

    @NotNull
    public PotionType getBasePotionType() {
        return this.type;
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

                if (old.getAmplifier() == effect.getAmplifier() && old.getDuration() == effect.getDuration() && old.isAmbient() == effect.isAmbient()) {
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

    public boolean setMainEffect(PotionEffectType type) {
        Preconditions.checkArgument(type != null, "Potion effect type cannot be null");
        int index = this.indexOfEffect(type);

        if (index != -1 && index != 0) {
            PotionEffect old = (PotionEffect) this.customEffects.get(0);

            this.customEffects.set(0, (PotionEffect) this.customEffects.get(index));
            this.customEffects.set(index, old);
            return true;
        } else {
            return false;
        }
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

    public boolean hasColor() {
        return this.color != null;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.type != PotionType.UNCRAFTABLE) {
            hash = 73 * hash + this.type.hashCode();
        }

        if (this.hasColor()) {
            hash = 73 * hash + this.color.hashCode();
        }

        if (this.hasCustomEffects()) {
            hash = 73 * hash + this.customEffects.hashCode();
        }

        return original != hash ? CraftMetaPotion.class.hashCode() ^ hash : hash;
    }

    public boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaPotion)) {
            return true;
        } else {
            CraftMetaPotion that = (CraftMetaPotion) meta;

            if (this.type.equals(that.type)) {
                if (this.hasCustomEffects()) {
                    if (!that.hasCustomEffects() || !this.customEffects.equals(that.customEffects)) {
                        return false;
                    }
                } else if (that.hasCustomEffects()) {
                    return false;
                }

                if (this.hasColor()) {
                    if (!that.hasColor() || !this.color.equals(that.color)) {
                        return false;
                    }
                } else if (that.hasColor()) {
                    return false;
                }

                return true;
            } else {
                return false;
            }
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaPotion || this.isPotionEmpty());
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.type != PotionType.UNCRAFTABLE) {
            builder.put(CraftMetaPotion.DEFAULT_POTION.BUKKIT, CraftPotionType.bukkitToString(this.type));
        }

        if (this.hasColor()) {
            builder.put(CraftMetaPotion.POTION_COLOR.BUKKIT, this.getColor());
        }

        if (this.hasCustomEffects()) {
            builder.put(CraftMetaPotion.POTION_EFFECTS.BUKKIT, ImmutableList.copyOf(this.customEffects));
        }

        return builder;
    }
}
