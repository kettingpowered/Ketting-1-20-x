package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.boss.EnderDragonPart;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class CraftComplexPart extends CraftEntity implements ComplexEntityPart {

    public CraftComplexPart(CraftServer server, EnderDragonPart entity) {
        super(server, entity);
    }

    public ComplexLivingEntity getParent() {
        return (ComplexLivingEntity) this.getHandle().parentMob.getBukkitEntity();
    }

    public void setLastDamageCause(EntityDamageEvent cause) {
        this.getParent().setLastDamageCause(cause);
    }

    public EntityDamageEvent getLastDamageCause() {
        return this.getParent().getLastDamageCause();
    }

    public boolean isValid() {
        return this.getParent().isValid();
    }

    public EnderDragonPart getHandle() {
        return (EnderDragonPart) this.entity;
    }

    public String toString() {
        return "CraftComplexPart";
    }
}
