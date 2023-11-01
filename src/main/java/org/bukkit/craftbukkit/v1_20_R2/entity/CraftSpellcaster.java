package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Spellcaster.Spell;

public class CraftSpellcaster extends CraftIllager implements Spellcaster {

    public CraftSpellcaster(CraftServer server, SpellcasterIllager entity) {
        super(server, (AbstractIllager) entity);
    }

    public SpellcasterIllager getHandle() {
        return (SpellcasterIllager) super.getHandle();
    }

    public String toString() {
        return "CraftSpellcaster";
    }

    public Spell getSpell() {
        return toBukkitSpell(this.getHandle().getCurrentSpell());
    }

    public void setSpell(Spell spell) {
        Preconditions.checkArgument(spell != null, "Use Spell.NONE");
        this.getHandle().setIsCastingSpell(toNMSSpell(spell));
    }

    public static Spell toBukkitSpell(SpellcasterIllager.IllagerSpell spell) {
        return Spell.valueOf(spell.name());
    }

    public static SpellcasterIllager.IllagerSpell toNMSSpell(Spell spell) {
        return SpellcasterIllager.IllagerSpell.byId(spell.ordinal());
    }
}
