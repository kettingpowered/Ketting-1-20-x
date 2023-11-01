package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Mule;

public class CraftMule extends CraftChestedHorse implements Mule {

    public CraftMule(CraftServer server, net.minecraft.world.entity.animal.horse.Mule entity) {
        super(server, (AbstractChestedHorse) entity);
    }

    public String toString() {
        return "CraftMule";
    }

    public Variant getVariant() {
        return Variant.MULE;
    }
}
