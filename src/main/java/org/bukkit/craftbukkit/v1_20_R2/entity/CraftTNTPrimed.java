package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;

public class CraftTNTPrimed extends CraftEntity implements TNTPrimed {

    public CraftTNTPrimed(CraftServer server, PrimedTnt entity) {
        super(server, entity);
    }

    public float getYield() {
        return this.getHandle().yield;
    }

    public boolean isIncendiary() {
        return this.getHandle().isIncendiary;
    }

    public void setIsIncendiary(boolean isIncendiary) {
        this.getHandle().isIncendiary = isIncendiary;
    }

    public void setYield(float yield) {
        this.getHandle().yield = yield;
    }

    public int getFuseTicks() {
        return this.getHandle().getFuse();
    }

    public void setFuseTicks(int fuseTicks) {
        this.getHandle().setFuse(fuseTicks);
    }

    public PrimedTnt getHandle() {
        return (PrimedTnt) this.entity;
    }

    public String toString() {
        return "CraftTNTPrimed";
    }

    public Entity getSource() {
        LivingEntity source = this.getHandle().getOwner();

        return source != null ? source.getBukkitEntity() : null;
    }

    public void setSource(Entity source) {
        if (source instanceof org.bukkit.entity.LivingEntity) {
            this.getHandle().owner = ((CraftLivingEntity) source).getHandle();
        } else {
            this.getHandle().owner = null;
        }

    }
}
