package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.EntityIllagerIllusioner;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Illusioner;

public class CraftIllusioner extends CraftSpellcaster implements Illusioner {

    public CraftIllusioner(CraftServer server, EntityIllagerIllusioner entity) {
        super(server, entity);
    }

    @Override
    public EntityIllagerIllusioner getHandle() {
        return (EntityIllagerIllusioner) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftIllusioner";
    }
}
