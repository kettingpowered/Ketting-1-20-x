package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.raid.Raider;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Illager;

public class CraftIllager extends CraftRaider implements Illager {

    public CraftIllager(CraftServer server, AbstractIllager entity) {
        super(server, (Raider) entity);
    }

    public AbstractIllager getHandle() {
        return (AbstractIllager) super.getHandle();
    }

    public String toString() {
        return "CraftIllager";
    }
}
