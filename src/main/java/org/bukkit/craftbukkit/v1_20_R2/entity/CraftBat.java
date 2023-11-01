package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.ambient.AmbientCreature;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Bat;

public class CraftBat extends CraftAmbient implements Bat {

    public CraftBat(CraftServer server, net.minecraft.world.entity.ambient.Bat entity) {
        super(server, (AmbientCreature) entity);
    }

    public net.minecraft.world.entity.ambient.Bat getHandle() {
        return (net.minecraft.world.entity.ambient.Bat) this.entity;
    }

    public String toString() {
        return "CraftBat";
    }

    public boolean isAwake() {
        return !this.getHandle().isResting();
    }

    public void setAwake(boolean state) {
        this.getHandle().setResting(!state);
    }
}
