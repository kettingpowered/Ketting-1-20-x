package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Silverfish;

public class CraftSilverfish extends CraftMonster implements Silverfish {

    public CraftSilverfish(CraftServer server, net.minecraft.world.entity.monster.Silverfish entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.Silverfish getHandle() {
        return (net.minecraft.world.entity.monster.Silverfish) this.entity;
    }

    public String toString() {
        return "CraftSilverfish";
    }
}
