package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.SpellcasterIllager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Evoker.Spell;

public class CraftEvoker extends CraftSpellcaster implements Evoker {

    public CraftEvoker(CraftServer server, net.minecraft.world.entity.monster.Evoker entity) {
        super(server, (SpellcasterIllager) entity);
    }

    public net.minecraft.world.entity.monster.Evoker getHandle() {
        return (net.minecraft.world.entity.monster.Evoker) super.getHandle();
    }

    public String toString() {
        return "CraftEvoker";
    }

    public Spell getCurrentSpell() {
        return Spell.values()[this.getHandle().getCurrentSpell().ordinal()];
    }

    public void setCurrentSpell(Spell spell) {
        this.getHandle().setIsCastingSpell(spell == null ? SpellcasterIllager.IllagerSpell.NONE : SpellcasterIllager.IllagerSpell.byId(spell.ordinal()));
    }
}
