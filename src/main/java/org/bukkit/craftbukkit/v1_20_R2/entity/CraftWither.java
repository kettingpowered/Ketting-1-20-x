package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.boss.CraftBossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wither.Head;

public class CraftWither extends CraftMonster implements Wither {

    private BossBar bossBar;

    public CraftWither(CraftServer server, WitherBoss entity) {
        super(server, (Monster) entity);
        if (entity.bossEvent != null) {
            this.bossBar = new CraftBossBar(entity.bossEvent);
        }

    }

    public WitherBoss getHandle() {
        return (WitherBoss) this.entity;
    }

    public String toString() {
        return "CraftWither";
    }

    public BossBar getBossBar() {
        return this.bossBar;
    }

    public void setTarget(Head head, LivingEntity livingEntity) {
        Preconditions.checkArgument(head != null, "head cannot be null");
        int entityId = livingEntity != null ? livingEntity.getEntityId() : 0;

        this.getHandle().setAlternativeTarget(head.ordinal(), entityId);
    }

    public LivingEntity getTarget(Head head) {
        Preconditions.checkArgument(head != null, "head cannot be null");
        int entityId = this.getHandle().getAlternativeTarget(head.ordinal());

        if (entityId == 0) {
            return null;
        } else {
            Entity target = this.getHandle().level().getEntity(entityId);

            return target != null ? (LivingEntity) target.getBukkitEntity() : null;
        }
    }

    public int getInvulnerabilityTicks() {
        return this.getHandle().getInvulnerableTicks();
    }

    public void setInvulnerabilityTicks(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks must be >=0");
        this.getHandle().setInvulnerableTicks(ticks);
    }
}
