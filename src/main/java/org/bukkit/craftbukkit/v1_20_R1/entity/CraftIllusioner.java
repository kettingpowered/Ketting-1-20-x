package org.bukkit.craftbukkit.v1_20_R1.entity;

import net.minecraft.world.entity.monster.SpellcasterIllager;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.entity.Illusioner;

public class CraftIllusioner extends CraftSpellcaster implements Illusioner {

    public CraftIllusioner(CraftServer server, SpellcasterIllager entity) {
        super(server, entity);
    }

    @Override
    public SpellcasterIllager getHandle() {
        return (SpellcasterIllager) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftIllusioner";
    }
}
