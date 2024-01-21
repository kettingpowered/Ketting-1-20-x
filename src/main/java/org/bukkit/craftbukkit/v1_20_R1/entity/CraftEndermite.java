package org.bukkit.craftbukkit.v1_20_R1.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.entity.Endermite;

public class CraftEndermite extends CraftMonster implements Endermite {

    public CraftEndermite(CraftServer server, Monster entity) {
        super(server, entity);
    }

    @Override
    public Monster getHandle() {
        return (Monster) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftEndermite";
    }

    @Override
    public boolean isPlayerSpawned() {
        return false;
    }

    @Override
    public void setPlayerSpawned(boolean playerSpawned) {
        // Nop
    }
}
