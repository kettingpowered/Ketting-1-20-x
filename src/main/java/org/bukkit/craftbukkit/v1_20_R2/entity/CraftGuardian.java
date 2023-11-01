package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;

public class CraftGuardian extends CraftMonster implements Guardian {

    private static final int MINIMUM_ATTACK_TICKS = -10;

    public CraftGuardian(CraftServer server, net.minecraft.world.entity.monster.Guardian entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.Guardian getHandle() {
        return (net.minecraft.world.entity.monster.Guardian) super.getHandle();
    }

    public String toString() {
        return "CraftGuardian";
    }

    public void setTarget(LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            this.getHandle().setActiveAttackTarget(0);
        }

    }

    public boolean setLaser(boolean activated) {
        if (activated) {
            CraftLivingEntity target = this.getTarget();

            if (target == null) {
                return false;
            }

            this.getHandle().setActiveAttackTarget(target.getEntityId());
        } else {
            this.getHandle().setActiveAttackTarget(0);
        }

        return true;
    }

    public boolean hasLaser() {
        return this.getHandle().hasActiveAttackTarget();
    }

    public int getLaserDuration() {
        return this.getHandle().getAttackDuration();
    }

    public void setLaserTicks(int ticks) {
        Preconditions.checkArgument(ticks >= -10, "ticks must be >= %s. Given %s", -10, ticks);
        net.minecraft.world.entity.monster.Guardian.GuardianAttackGoal goal = this.getHandle().guardianAttackGoal;

        if (goal != null) {
            goal.attackTime = ticks;
        }

    }

    public int getLaserTicks() {
        net.minecraft.world.entity.monster.Guardian.GuardianAttackGoal goal = this.getHandle().guardianAttackGoal;

        return goal != null ? goal.attackTime : -10;
    }

    public boolean isElder() {
        return false;
    }

    public void setElder(boolean shouldBeElder) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean isMoving() {
        return this.getHandle().isMoving();
    }
}
