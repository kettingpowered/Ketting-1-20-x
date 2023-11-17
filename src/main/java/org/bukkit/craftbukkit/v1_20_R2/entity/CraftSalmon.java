package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.EntitySalmon;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Salmon;

public class CraftSalmon extends CraftFish implements Salmon {

    public CraftSalmon(CraftServer server, EntitySalmon entity) {
        super(server, entity);
    }

    @Override
    public EntitySalmon getHandle() {
        return (EntitySalmon) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftSalmon";
    }
}
