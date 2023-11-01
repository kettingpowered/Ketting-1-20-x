package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Flying;

public class CraftFlying extends CraftMob implements Flying {

    public CraftFlying(CraftServer server, FlyingMob entity) {
        super(server, (Mob) entity);
    }

    public FlyingMob getHandle() {
        return (FlyingMob) this.entity;
    }

    public String toString() {
        return "CraftFlying";
    }
}
