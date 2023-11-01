package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Spider;

public class CraftSpider extends CraftMonster implements Spider {

    public CraftSpider(CraftServer server, net.minecraft.world.entity.monster.Spider entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.Spider getHandle() {
        return (net.minecraft.world.entity.monster.Spider) this.entity;
    }

    public String toString() {
        return "CraftSpider";
    }
}
