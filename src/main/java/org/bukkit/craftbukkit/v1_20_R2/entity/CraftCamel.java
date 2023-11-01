package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Horse.Variant;

public class CraftCamel extends CraftAbstractHorse implements Camel {

    public CraftCamel(CraftServer server, net.minecraft.world.entity.animal.camel.Camel entity) {
        super(server, (AbstractHorse) entity);
    }

    public net.minecraft.world.entity.animal.camel.Camel getHandle() {
        return (net.minecraft.world.entity.animal.camel.Camel) super.getHandle();
    }

    public String toString() {
        return "CraftCamel";
    }

    public Variant getVariant() {
        return Variant.CAMEL;
    }

    public boolean isDashing() {
        return this.getHandle().isDashing();
    }

    public void setDashing(boolean dashing) {
        this.getHandle().setDashing(dashing);
    }

    public boolean isSitting() {
        return this.getHandle().getPose() == Pose.SITTING;
    }

    public void setSitting(boolean sitting) {
        if (sitting) {
            this.getHandle().sitDown();
        } else {
            this.getHandle().standUp();
        }

    }
}
