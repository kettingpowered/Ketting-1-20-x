package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.Mob;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftMob implements Slime, CraftEnemy {

    public CraftSlime(CraftServer server, net.minecraft.world.entity.monster.Slime entity) {
        super(server, (Mob) entity);
    }

    public int getSize() {
        return this.getHandle().getSize();
    }

    public void setSize(int size) {
        this.getHandle().setSize(size, true);
    }

    public net.minecraft.world.entity.monster.Slime getHandle() {
        return (net.minecraft.world.entity.monster.Slime) this.entity;
    }

    public String toString() {
        return "CraftSlime";
    }
}
