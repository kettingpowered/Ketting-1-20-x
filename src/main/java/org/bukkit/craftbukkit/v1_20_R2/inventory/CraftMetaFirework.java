package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.FireworkMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaFirework extends CraftMetaItem implements FireworkMeta {

    static final CraftMetaItem.ItemMetaKey FIREWORKS = new CraftMetaItem.ItemMetaKey("Fireworks");
    static final CraftMetaItem.ItemMetaKey FLIGHT = new CraftMetaItem.ItemMetaKey("Flight", "power");
    static final CraftMetaItem.ItemMetaKey EXPLOSIONS = new CraftMetaItem.ItemMetaKey("Explosions", "firework-effects");
    static final CraftMetaItem.ItemMetaKey EXPLOSION_COLORS = new CraftMetaItem.ItemMetaKey("Colors");
    static final CraftMetaItem.ItemMetaKey EXPLOSION_TYPE = new CraftMetaItem.ItemMetaKey("Type");
    static final CraftMetaItem.ItemMetaKey EXPLOSION_TRAIL = new CraftMetaItem.ItemMetaKey("Trail");
    static final CraftMetaItem.ItemMetaKey EXPLOSION_FLICKER = new CraftMetaItem.ItemMetaKey("Flicker");
    static final CraftMetaItem.ItemMetaKey EXPLOSION_FADE = new CraftMetaItem.ItemMetaKey("FadeColors");
    private List effects;
    private Integer power;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$FireworkEffect$Type;

    CraftMetaFirework(CraftMetaItem meta) {
        super(meta);
        CraftMetaFirework that;

        if (meta instanceof CraftMetaFirework && (that = (CraftMetaFirework) meta) == (CraftMetaFirework) meta) {
            this.power = that.power;
            if (that.hasEffects()) {
                this.effects = new ArrayList(that.effects);
            }

        }
    }

    CraftMetaFirework(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaFirework.FIREWORKS.NBT)) {
            CompoundTag fireworks = tag.getCompound(CraftMetaFirework.FIREWORKS.NBT);

            this.power = Integer.valueOf(fireworks.getByte(CraftMetaFirework.FLIGHT.NBT));
            if (fireworks.contains(CraftMetaFirework.EXPLOSIONS.NBT)) {
                ListTag fireworkEffects = fireworks.getList(CraftMetaFirework.EXPLOSIONS.NBT, 10);
                List effects = this.effects = new ArrayList(fireworkEffects.size());

                for (int i = 0; i < fireworkEffects.size(); ++i) {
                    try {
                        effects.add(getEffect((CompoundTag) fireworkEffects.get(i)));
                    } catch (IllegalArgumentException illegalargumentexception) {
                        ;
                    }
                }

            }
        }
    }

    static FireworkEffect getEffect(CompoundTag explosion) {
        Builder effect = FireworkEffect.builder().flicker(explosion.getBoolean(CraftMetaFirework.EXPLOSION_FLICKER.NBT)).trail(explosion.getBoolean(CraftMetaFirework.EXPLOSION_TRAIL.NBT)).with(getEffectType(255 & explosion.getByte(CraftMetaFirework.EXPLOSION_TYPE.NBT)));
        int[] colors = explosion.getIntArray(CraftMetaFirework.EXPLOSION_COLORS.NBT);

        if (colors.length == 0) {
            effect.withColor(Color.WHITE);
        }

        int[] aint = colors;
        int i = colors.length;

        int color;
        int j;

        for (j = 0; j < i; ++j) {
            color = aint[j];
            effect.withColor(Color.fromRGB(color));
        }

        i = (aint = explosion.getIntArray(CraftMetaFirework.EXPLOSION_FADE.NBT)).length;

        for (j = 0; j < i; ++j) {
            color = aint[j];
            effect.withFade(Color.fromRGB(color));
        }

        return effect.build();
    }

    static CompoundTag getExplosion(FireworkEffect effect) {
        CompoundTag explosion = new CompoundTag();

        if (effect.hasFlicker()) {
            explosion.putBoolean(CraftMetaFirework.EXPLOSION_FLICKER.NBT, true);
        }

        if (effect.hasTrail()) {
            explosion.putBoolean(CraftMetaFirework.EXPLOSION_TRAIL.NBT, true);
        }

        addColors(explosion, CraftMetaFirework.EXPLOSION_COLORS, effect.getColors());
        addColors(explosion, CraftMetaFirework.EXPLOSION_FADE, effect.getFadeColors());
        explosion.putByte(CraftMetaFirework.EXPLOSION_TYPE.NBT, (byte) getNBT(effect.getType()));
        return explosion;
    }

    static int getNBT(Type type) {
        switch ($SWITCH_TABLE$org$bukkit$FireworkEffect$Type()[type.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 4;
            case 5:
                return 3;
            default:
                throw new IllegalArgumentException("Unknown effect type " + type);
        }
    }

    static Type getEffectType(int nbt) {
        switch (nbt) {
            case 0:
                return Type.BALL;
            case 1:
                return Type.BALL_LARGE;
            case 2:
                return Type.STAR;
            case 3:
                return Type.CREEPER;
            case 4:
                return Type.BURST;
            default:
                throw new IllegalArgumentException("Unknown effect type " + nbt);
        }
    }

    CraftMetaFirework(Map map) {
        super(map);
        Integer power = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaFirework.FLIGHT.BUKKIT, true);

        if (power != null) {
            this.power = power;
        }

        Iterable effects = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaFirework.EXPLOSIONS.BUKKIT, true);

        this.safelyAddEffects(effects);
    }

    public boolean hasEffects() {
        return this.effects != null && !this.effects.isEmpty();
    }

    void safelyAddEffects(Iterable collection) {
        if (collection != null && (!(collection instanceof Collection) || !((Collection) collection).isEmpty())) {
            List effects = this.effects;

            if (effects == null) {
                effects = this.effects = new ArrayList();
            }

            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                Object obj = iterator.next();

                Preconditions.checkArgument(obj instanceof FireworkEffect, "%s in %s is not a FireworkEffect", obj, collection);
                effects.add((FireworkEffect) obj);
            }

        }
    }

    void applyToItem(CompoundTag itemTag) {
        super.applyToItem(itemTag);
        if (!this.isFireworkEmpty()) {
            CompoundTag fireworks = itemTag.getCompound(CraftMetaFirework.FIREWORKS.NBT);

            itemTag.put(CraftMetaFirework.FIREWORKS.NBT, fireworks);
            if (this.hasEffects()) {
                ListTag effects = new ListTag();
                Iterator iterator = this.effects.iterator();

                while (iterator.hasNext()) {
                    FireworkEffect effect = (FireworkEffect) iterator.next();

                    effects.add(getExplosion(effect));
                }

                if (effects.size() > 0) {
                    fireworks.put(CraftMetaFirework.EXPLOSIONS.NBT, effects);
                }
            }

            if (this.hasPower()) {
                fireworks.putByte(CraftMetaFirework.FLIGHT.NBT, this.power.byteValue());
            }

        }
    }

    static void addColors(CompoundTag compound, CraftMetaItem.ItemMetaKey key, List colors) {
        if (!colors.isEmpty()) {
            int[] colorArray = new int[colors.size()];
            int i = 0;

            Color color;

            for (Iterator iterator = colors.iterator(); iterator.hasNext(); colorArray[i++] = color.asRGB()) {
                color = (Color) iterator.next();
            }

            compound.putIntArray(key.NBT, colorArray);
        }
    }

    boolean applicableTo(Material type) {
        return type == Material.FIREWORK_ROCKET;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isFireworkEmpty();
    }

    boolean isFireworkEmpty() {
        return !this.hasEffects() && !this.hasPower();
    }

    boolean hasPower() {
        return this.power != null && this.power != 0;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else {
            CraftMetaFirework that;

            if (meta instanceof CraftMetaFirework && (that = (CraftMetaFirework) meta) == (CraftMetaFirework) meta) {
                if (this.hasPower()) {
                    if (!that.hasPower() || this.power != that.power) {
                        return false;
                    }
                } else if (that.hasPower()) {
                    return false;
                }

                if (this.hasEffects()) {
                    if (!that.hasEffects() || !this.effects.equals(that.effects)) {
                        return false;
                    }
                } else if (that.hasEffects()) {
                    return false;
                }

                return true;
            } else {
                return true;
            }
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaFirework || this.isFireworkEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasPower()) {
            hash = 61 * hash + this.power;
        }

        if (this.hasEffects()) {
            hash = 61 * hash + 13 * this.effects.hashCode();
        }

        return hash != original ? CraftMetaFirework.class.hashCode() ^ hash : hash;
    }

    com.google.common.collect.ImmutableMap.Builder serialize(com.google.common.collect.ImmutableMap.Builder builder) {
        super.serialize(builder);
        if (this.hasEffects()) {
            builder.put(CraftMetaFirework.EXPLOSIONS.BUKKIT, ImmutableList.copyOf(this.effects));
        }

        if (this.hasPower()) {
            builder.put(CraftMetaFirework.FLIGHT.BUKKIT, this.power);
        }

        return builder;
    }

    public CraftMetaFirework clone() {
        CraftMetaFirework meta = (CraftMetaFirework) super.clone();

        if (this.effects != null) {
            meta.effects = new ArrayList(this.effects);
        }

        return meta;
    }

    public void addEffect(FireworkEffect effect) {
        Preconditions.checkArgument(effect != null, "FireworkEffect cannot be null");
        if (this.effects == null) {
            this.effects = new ArrayList();
        }

        this.effects.add(effect);
    }

    public void addEffects(FireworkEffect... effects) {
        Preconditions.checkArgument(effects != null, "effects cannot be null");
        if (effects.length != 0) {
            List list = this.effects;

            if (list == null) {
                list = this.effects = new ArrayList();
            }

            FireworkEffect[] afireworkeffect = effects;
            int i = effects.length;

            for (int j = 0; j < i; ++j) {
                FireworkEffect effect = afireworkeffect[j];

                Preconditions.checkArgument(effect != null, "effects cannot contain null FireworkEffect");
                list.add(effect);
            }

        }
    }

    public void addEffects(Iterable effects) {
        Preconditions.checkArgument(effects != null, "effects cannot be null");
        this.safelyAddEffects(effects);
    }

    public List getEffects() {
        return this.effects == null ? ImmutableList.of() : ImmutableList.copyOf(this.effects);
    }

    public int getEffectsSize() {
        return this.effects == null ? 0 : this.effects.size();
    }

    public void removeEffect(int index) {
        if (this.effects == null) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
        } else {
            this.effects.remove(index);
        }
    }

    public void clearEffects() {
        this.effects = null;
    }

    public int getPower() {
        return this.hasPower() ? this.power : 0;
    }

    public void setPower(int power) {
        Preconditions.checkArgument(power >= 0, "power cannot be less than zero: %s", power);
        Preconditions.checkArgument(power < 128, "power cannot be more than 127: %s", power);
        this.power = power;
    }

    static int[] $SWITCH_TABLE$org$bukkit$FireworkEffect$Type() {
        int[] aint = CraftMetaFirework.$SWITCH_TABLE$org$bukkit$FireworkEffect$Type;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Type.values().length];

            try {
                aint1[Type.BALL.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Type.BALL_LARGE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Type.BURST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Type.CREEPER.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Type.STAR.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            CraftMetaFirework.$SWITCH_TABLE$org$bukkit$FireworkEffect$Type = aint1;
            return aint1;
        }
    }
}
