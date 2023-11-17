package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.EntityFish;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Fish;

public class CraftFish extends CraftWaterMob implements Fish {

    public CraftFish(CraftServer server, EntityFish entity) {
        super(server, entity);
    }

    @Override
    public EntityFish getHandle() {
        return (EntityFish) entity;
    }

    @Override
    public String toString() {
        return "CraftFish";
    }
}
