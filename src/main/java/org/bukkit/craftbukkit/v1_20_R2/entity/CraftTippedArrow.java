package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionEffectType;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionType;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

public class CraftTippedArrow extends CraftArrow implements Arrow {

    public CraftTippedArrow(CraftServer server, net.minecraft.world.entity.projectile.Arrow entity) {
        super(server, (AbstractArrow) entity);
    }

    public net.minecraft.world.entity.projectile.Arrow getHandle() {
        return (net.minecraft.world.entity.projectile.Arrow) this.entity;
    }

    public String toString() {
        return "CraftTippedArrow";
    }

    public boolean addCustomEffect(PotionEffect effect, boolean override) {
        MobEffect minecraft = CraftPotionEffectType.bukkitToMinecraft(effect.getType());
        MobEffectInstance existing = null;
        Iterator iterator = this.getHandle().effects.iterator();

        while (iterator.hasNext()) {
            MobEffectInstance mobEffect = (MobEffectInstance) iterator.next();

            if (mobEffect.getEffect() == minecraft) {
                existing = mobEffect;
            }
        }

        if (existing != null) {
            if (!override) {
                return false;
            }

            this.getHandle().effects.remove(existing);
        }

        this.getHandle().addEffect(CraftPotionUtil.fromBukkit(effect));
        this.getHandle().updateColor();
        return true;
    }

    public void clearCustomEffects() {
        this.getHandle().effects.clear();
        this.getHandle().updateColor();
    }

    public List getCustomEffects() {
        Builder builder = ImmutableList.builder();
        Iterator iterator = this.getHandle().effects.iterator();

        while (iterator.hasNext()) {
            MobEffectInstance effect = (MobEffectInstance) iterator.next();

            builder.add(CraftPotionUtil.toBukkit(effect));
        }

        return builder.build();
    }

    public boolean hasCustomEffect(PotionEffectType type) {
        Iterator iterator = this.getHandle().effects.iterator();

        while (iterator.hasNext()) {
            MobEffectInstance effect = (MobEffectInstance) iterator.next();

            if (CraftPotionUtil.equals(effect.getEffect(), type)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasCustomEffects() {
        return !this.getHandle().effects.isEmpty();
    }

    public boolean removeCustomEffect(PotionEffectType effect) {
        MobEffect minecraft = CraftPotionEffectType.bukkitToMinecraft(effect);
        MobEffectInstance existing = null;
        Iterator iterator = this.getHandle().effects.iterator();

        while (iterator.hasNext()) {
            MobEffectInstance mobEffect = (MobEffectInstance) iterator.next();

            if (mobEffect.getEffect() == minecraft) {
                existing = mobEffect;
            }
        }

        if (existing == null) {
            return false;
        } else {
            this.getHandle().effects.remove(existing);
            this.getHandle().updateColor();
            return true;
        }
    }

    public void setBasePotionData(PotionData data) {
        Preconditions.checkArgument(data != null, "PotionData cannot be null");
        this.getHandle().potion = CraftPotionType.bukkitToMinecraft(CraftPotionUtil.fromBukkit(data));
    }

    public PotionData getBasePotionData() {
        return CraftPotionUtil.toBukkit(CraftPotionType.minecraftToBukkit(this.getHandle().potion));
    }

    public void setBasePotionType(@NotNull PotionType potionType) {
        Preconditions.checkArgument(potionType != null, "PotionType cannot be null use PotionType.UNCRAFTABLE to represent no effect instead.");
        this.getHandle().potion = CraftPotionType.bukkitToMinecraft(potionType);
    }

    @NotNull
    public PotionType getBasePotionType() {
        return CraftPotionType.minecraftToBukkit(this.getHandle().potion);
    }

    public void setColor(Color color) {
        int colorRGB = color == null ? -1 : color.asRGB();

        this.getHandle().setFixedColor(colorRGB);
    }

    public Color getColor() {
        return this.getHandle().getColor() <= -1 ? null : Color.fromRGB(this.getHandle().getColor());
    }
}
