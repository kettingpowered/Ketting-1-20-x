package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Endermite;

public class CraftEndermite extends CraftMonster implements Endermite {

    public CraftEndermite(CraftServer server, net.minecraft.world.entity.monster.Endermite entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.Endermite getHandle() {
        return (net.minecraft.world.entity.monster.Endermite) super.getHandle();
    }

    public String toString() {
        return "CraftEndermite";
    }

    public boolean isPlayerSpawned() {
        return false;
    }

    public void setPlayerSpawned(boolean playerSpawned) {}
}
