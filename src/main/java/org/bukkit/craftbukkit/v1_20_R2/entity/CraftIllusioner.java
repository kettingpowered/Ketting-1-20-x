package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.SpellcasterIllager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Illusioner;

public class CraftIllusioner extends CraftSpellcaster implements Illusioner {

    public CraftIllusioner(CraftServer server, net.minecraft.world.entity.monster.Illusioner entity) {
        super(server, (SpellcasterIllager) entity);
    }

    public net.minecraft.world.entity.monster.Illusioner getHandle() {
        return (net.minecraft.world.entity.monster.Illusioner) super.getHandle();
    }

    public String toString() {
        return "CraftIllusioner";
    }
}
