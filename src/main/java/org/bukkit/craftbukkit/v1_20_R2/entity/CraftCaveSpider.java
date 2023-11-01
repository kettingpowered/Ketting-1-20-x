package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Spider;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.CaveSpider;

public class CraftCaveSpider extends CraftSpider implements CaveSpider {

    public CraftCaveSpider(CraftServer server, net.minecraft.world.entity.monster.CaveSpider entity) {
        super(server, (Spider) entity);
    }

    public net.minecraft.world.entity.monster.CaveSpider getHandle() {
        return (net.minecraft.world.entity.monster.CaveSpider) this.entity;
    }

    public String toString() {
        return "CraftCaveSpider";
    }
}
