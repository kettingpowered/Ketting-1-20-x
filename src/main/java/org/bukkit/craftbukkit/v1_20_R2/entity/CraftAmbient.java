package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.AmbientCreature;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Ambient;

public class CraftAmbient extends CraftMob implements Ambient {

    public CraftAmbient(CraftServer server, AmbientCreature entity) {
        super(server, (Mob) entity);
    }

    public AmbientCreature getHandle() {
        return (AmbientCreature) this.entity;
    }

    public String toString() {
        return "CraftAmbient";
    }
}
