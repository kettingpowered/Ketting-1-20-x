package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.EntityCaveSpider;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.CaveSpider;

public class CraftCaveSpider extends CraftSpider implements CaveSpider {
    public CraftCaveSpider(CraftServer server, EntityCaveSpider entity) {
        super(server, entity);
    }

    @Override
    public EntityCaveSpider getHandle() {
        return (EntityCaveSpider) entity;
    }

    @Override
    public String toString() {
        return "CraftCaveSpider";
    }
}
