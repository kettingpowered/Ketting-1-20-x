package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.EntityFlying;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Flying;

public class CraftFlying extends CraftMob implements Flying {

    public CraftFlying(CraftServer server, EntityFlying entity) {
        super(server, entity);
    }

    @Override
    public EntityFlying getHandle() {
        return (EntityFlying) entity;
    }

    @Override
    public String toString() {
        return "CraftFlying";
    }
}
