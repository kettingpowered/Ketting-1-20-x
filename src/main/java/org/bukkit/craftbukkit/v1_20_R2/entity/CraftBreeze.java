package org.bukkit.craftbukkit.v1_20_R2.entity;

import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Breeze;

public class CraftBreeze extends CraftMonster implements Breeze {

    public CraftBreeze(CraftServer server, net.minecraft.world.entity.monster.breeze.Breeze entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.monster.breeze.Breeze getHandle() {
        return (net.minecraft.world.entity.monster.breeze.Breeze) entity;
    }

    @Override
    public String toString() {
        return "CraftBreeze";
    }
}
