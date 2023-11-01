package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Guardian;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.ElderGuardian;

public class CraftElderGuardian extends CraftGuardian implements ElderGuardian {

    public CraftElderGuardian(CraftServer server, net.minecraft.world.entity.monster.ElderGuardian entity) {
        super(server, (Guardian) entity);
    }

    public String toString() {
        return "CraftElderGuardian";
    }

    public boolean isElder() {
        return true;
    }
}
