package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.FishHook.HookState;

public class CraftFishHook extends CraftProjectile implements FishHook {

    private double biteChance = -1.0D;

    public CraftFishHook(CraftServer server, FishingHook entity) {
        super(server, (Projectile) entity);
    }

    public FishingHook getHandle() {
        return (FishingHook) this.entity;
    }

    public String toString() {
        return "CraftFishingHook";
    }

    public int getMinWaitTime() {
        return this.getHandle().minWaitTime;
    }

    public void setMinWaitTime(int minWaitTime) {
        Preconditions.checkArgument(minWaitTime >= 0 && minWaitTime <= this.getMaxWaitTime(), "The minimum wait time should be between %s and %s (the maximum wait time)", 0, this.getMaxWaitTime());
        FishingHook hook = this.getHandle();

        hook.minWaitTime = minWaitTime;
    }

    public int getMaxWaitTime() {
        return this.getHandle().maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        Preconditions.checkArgument(maxWaitTime >= 0 && maxWaitTime >= this.getMinWaitTime(), "The maximum wait time should be between %s and %s (the minimum wait time)", 0, this.getMinWaitTime());
        FishingHook hook = this.getHandle();

        hook.maxWaitTime = maxWaitTime;
    }

    public void setWaitTime(int min, int max) {
        Preconditions.checkArgument(min >= 0 && max >= 0 && min <= max, "The minimum/maximum wait time should be higher than or equal to 0 and the minimum wait time");
        this.getHandle().minWaitTime = min;
        this.getHandle().maxWaitTime = max;
    }

    public int getMinLureTime() {
        return this.getHandle().minLureTime;
    }

    public void setMinLureTime(int minLureTime) {
        Preconditions.checkArgument(minLureTime >= 0 && minLureTime <= this.getMaxLureTime(), "The minimum lure time (%s) should be between 0 and %s (the maximum wait time)", minLureTime, this.getMaxLureTime());
        this.getHandle().minLureTime = minLureTime;
    }

    public int getMaxLureTime() {
        return this.getHandle().maxLureTime;
    }

    public void setMaxLureTime(int maxLureTime) {
        Preconditions.checkArgument(maxLureTime >= 0 && maxLureTime >= this.getMinLureTime(), "The maximum lure time (%s) should be higher than or equal to 0 and %s (the minimum wait time)", maxLureTime, this.getMinLureTime());
        this.getHandle().maxLureTime = maxLureTime;
    }

    public void setLureTime(int min, int max) {
        Preconditions.checkArgument(min >= 0 && max >= 0 && min <= max, "The minimum/maximum lure time should be higher than or equal to 0 and the minimum wait time.");
        this.getHandle().minLureTime = min;
        this.getHandle().maxLureTime = max;
    }

    public float getMinLureAngle() {
        return this.getHandle().minLureAngle;
    }

    public void setMinLureAngle(float minLureAngle) {
        Preconditions.checkArgument(minLureAngle <= this.getMaxLureAngle(), "The minimum lure angle (%s) should be less than %s (the maximum lure angle)", minLureAngle, this.getMaxLureAngle());
        this.getHandle().minLureAngle = minLureAngle;
    }

    public float getMaxLureAngle() {
        return this.getHandle().maxLureAngle;
    }

    public void setMaxLureAngle(float maxLureAngle) {
        Preconditions.checkArgument(maxLureAngle >= this.getMinLureAngle(), "The minimum lure angle (%s) should be less than %s (the maximum lure angle)", maxLureAngle, this.getMinLureAngle());
        this.getHandle().maxLureAngle = maxLureAngle;
    }

    public void setLureAngle(float min, float max) {
        Preconditions.checkArgument(min <= max, "The minimum lure (%s) angle should be less than the maximum lure angle (%s)", min, max);
        this.getHandle().minLureAngle = min;
        this.getHandle().maxLureAngle = max;
    }

    public boolean isSkyInfluenced() {
        return this.getHandle().skyInfluenced;
    }

    public void setSkyInfluenced(boolean skyInfluenced) {
        this.getHandle().skyInfluenced = skyInfluenced;
    }

    public boolean isRainInfluenced() {
        return this.getHandle().rainInfluenced;
    }

    public void setRainInfluenced(boolean rainInfluenced) {
        this.getHandle().rainInfluenced = rainInfluenced;
    }

    public boolean getApplyLure() {
        return this.getHandle().applyLure;
    }

    public void setApplyLure(boolean applyLure) {
        this.getHandle().applyLure = applyLure;
    }

    public double getBiteChance() {
        FishingHook hook = this.getHandle();

        return this.biteChance == -1.0D ? (hook.level().isRainingAt(BlockPos.containing(hook.position()).offset(0, 1, 0)) ? 0.0033333333333333335D : 0.002D) : this.biteChance;
    }

    public void setBiteChance(double chance) {
        Preconditions.checkArgument(chance >= 0.0D && chance <= 1.0D, "The bite chance must be between 0 and 1");
        this.biteChance = chance;
    }

    public boolean isInOpenWater() {
        return this.getHandle().isOpenWaterFishing();
    }

    public Entity getHookedEntity() {
        net.minecraft.world.entity.Entity hooked = this.getHandle().hookedIn;

        return hooked != null ? hooked.getBukkitEntity() : null;
    }

    public void setHookedEntity(Entity entity) {
        FishingHook hook = this.getHandle();

        hook.hookedIn = entity != null ? ((CraftEntity) entity).getHandle() : null;
        hook.getEntityData().set(FishingHook.DATA_HOOKED_ENTITY, hook.hookedIn != null ? hook.hookedIn.getId() + 1 : 0);
    }

    public boolean pullHookedEntity() {
        FishingHook hook = this.getHandle();

        if (hook.hookedIn == null) {
            return false;
        } else {
            hook.pullEntity(hook.hookedIn);
            return true;
        }
    }

    public HookState getState() {
        return HookState.values()[this.getHandle().currentState.ordinal()];
    }
}
