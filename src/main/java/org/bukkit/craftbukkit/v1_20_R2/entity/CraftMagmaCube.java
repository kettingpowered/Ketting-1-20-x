package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Slime;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.MagmaCube;

public class CraftMagmaCube extends CraftSlime implements MagmaCube {

    public CraftMagmaCube(CraftServer server, net.minecraft.world.entity.monster.MagmaCube entity) {
        super(server, (Slime) entity);
    }

    public net.minecraft.world.entity.monster.MagmaCube getHandle() {
        return (net.minecraft.world.entity.monster.MagmaCube) this.entity;
    }

    public String toString() {
        return "CraftMagmaCube";
    }
}
