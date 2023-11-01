package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R2.CraftParticle;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionEffectType;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionType;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionUtil;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;

public class CraftAreaEffectCloud extends CraftEntity implements AreaEffectCloud {

    public CraftAreaEffectCloud(CraftServer server, net.minecraft.world.entity.AreaEffectCloud entity) {
        super(server, entity);
    }

    public net.minecraft.world.entity.AreaEffectCloud getHandle() {
        return (net.minecraft.world.entity.AreaEffectCloud) super.getHandle();
    }

    public String toString() {
        return "CraftAreaEffectCloud";
    }

    public int getDuration() {
        return this.getHandle().getDuration();
    }

    public void setDuration(int duration) {
        this.getHandle().setDuration(duration);
    }

    public int getWaitTime() {
        return this.getHandle().waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.getHandle().setWaitTime(waitTime);
    }

    public int getReapplicationDelay() {
        return this.getHandle().reapplicationDelay;
    }

    public void setReapplicationDelay(int delay) {
        this.getHandle().reapplicationDelay = delay;
    }

    public int getDurationOnUse() {
        return this.getHandle().durationOnUse;
    }

    public void setDurationOnUse(int duration) {
        this.getHandle().durationOnUse = duration;
    }

    public float getRadius() {
        return this.getHandle().getRadius();
    }

    public void setRadius(float radius) {
        this.getHandle().setRadius(radius);
    }

    public float getRadiusOnUse() {
        return this.getHandle().radiusOnUse;
    }

    public void setRadiusOnUse(float radius) {
        this.getHandle().setRadiusOnUse(radius);
    }

    public float getRadiusPerTick() {
        return this.getHandle().radiusPerTick;
    }

    public void setRadiusPerTick(float radius) {
        this.getHandle().setRadiusPerTick(radius);
    }

    public Particle getParticle() {
        return CraftParticle.minecraftToBukkit(this.getHandle().getParticle().getType());
    }

    public void setParticle(Particle particle) {
        this.setParticle(particle, (Object) null);
    }

    public void setParticle(Particle particle, Object data) {
        particle = CraftParticle.convertLegacy(particle);
        data = CraftParticle.convertLegacy(data);
        if (data != null) {
            Preconditions.checkArgument(particle.getDataType().isInstance(data), "data (%s) should be %s", data.getClass(), particle.getDataType());
        }

        this.getHandle().setParticle(CraftParticle.createParticleParam(particle, data));
    }

    public Color getColor() {
        return Color.fromRGB(this.getHandle().getColor());
    }

    public void setColor(Color color) {
        this.getHandle().setFixedColor(color.asRGB());
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
        this.getHandle().setPotion(CraftPotionType.bukkitToMinecraft(CraftPotionUtil.fromBukkit(data)));
    }

    public PotionData getBasePotionData() {
        return CraftPotionUtil.toBukkit(CraftPotionType.minecraftToBukkit(this.getHandle().getPotion()));
    }

    public void setBasePotionType(@NotNull PotionType potionType) {
        Preconditions.checkArgument(potionType != null, "PotionType cannot be null use PotionType.UNCRAFTABLE to represent no effect instead.");
        this.getHandle().setPotion(CraftPotionType.bukkitToMinecraft(potionType));
    }

    @NotNull
    public PotionType getBasePotionType() {
        return CraftPotionType.minecraftToBukkit(this.getHandle().getPotion());
    }

    public ProjectileSource getSource() {
        LivingEntity source = this.getHandle().getOwner();

        return source == null ? null : (org.bukkit.entity.LivingEntity) source.getBukkitEntity();
    }

    public void setSource(ProjectileSource shooter) {
        CraftLivingEntity craftLivingEntity;

        if (shooter instanceof CraftLivingEntity && (craftLivingEntity = (CraftLivingEntity) shooter) == (CraftLivingEntity) shooter) {
            this.getHandle().setOwner(craftLivingEntity.getHandle());
        } else {
            this.getHandle().setOwner((LivingEntity) null);
        }

    }
}
