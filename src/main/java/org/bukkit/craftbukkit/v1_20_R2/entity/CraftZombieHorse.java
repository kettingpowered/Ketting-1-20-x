package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.ZombieHorse;

public class CraftZombieHorse extends CraftAbstractHorse implements ZombieHorse {

    public CraftZombieHorse(CraftServer server, net.minecraft.world.entity.animal.horse.ZombieHorse entity) {
        super(server, (AbstractHorse) entity);
    }

    public String toString() {
        return "CraftZombieHorse";
    }

    public Variant getVariant() {
        return Variant.UNDEAD_HORSE;
    }
}
