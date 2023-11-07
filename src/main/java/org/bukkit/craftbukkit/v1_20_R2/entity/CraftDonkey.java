package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Horse.Variant;

public class CraftDonkey extends CraftChestedHorse implements Donkey {

    public CraftDonkey(CraftServer server, net.minecraft.world.entity.animal.horse.Donkey entity) {
        super(server, (AbstractChestedHorse) entity);
    }

    public String toString() {
        return "CraftDonkey";
    }

    public Variant getVariant() {
        return Variant.DONKEY;
    }
}