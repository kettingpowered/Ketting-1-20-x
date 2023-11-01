package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.SkeletonHorse;

public class CraftSkeletonHorse extends CraftAbstractHorse implements SkeletonHorse {

    public CraftSkeletonHorse(CraftServer server, net.minecraft.world.entity.animal.horse.SkeletonHorse entity) {
        super(server, (AbstractHorse) entity);
    }

    public String toString() {
        return "CraftSkeletonHorse";
    }

    public Variant getVariant() {
        return Variant.SKELETON_HORSE;
    }

    public net.minecraft.world.entity.animal.horse.SkeletonHorse getHandle() {
        return (net.minecraft.world.entity.animal.horse.SkeletonHorse) this.entity;
    }

    public boolean isTrapped() {
        return this.getHandle().isTrap();
    }

    public void setTrapped(boolean trapped) {
        this.getHandle().setTrap(trapped);
    }

    public int getTrapTime() {
        return this.getHandle().trapTime;
    }

    public void setTrapTime(int trapTime) {
        this.getHandle().trapTime = trapTime;
    }
}
