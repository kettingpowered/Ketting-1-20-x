package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Blaze;

public class CraftBlaze extends CraftMonster implements Blaze {

    public CraftBlaze(CraftServer server, net.minecraft.world.entity.monster.Blaze entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.Blaze getHandle() {
        return (net.minecraft.world.entity.monster.Blaze) this.entity;
    }

    public String toString() {
        return "CraftBlaze";
    }
}
