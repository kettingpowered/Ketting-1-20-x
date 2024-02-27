package org.kettingpowered.ketting.entity;

import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftAbstractHorse;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftChestedHorse;
import org.bukkit.entity.Horse;
import org.jetbrains.annotations.NotNull;

public class CraftCustomHorse extends CraftAbstractHorse {

    public CraftCustomHorse(CraftServer server, AbstractHorse entity) {
        super(server, entity);
    }

    public @NotNull Horse.Variant getVariant() {
        return Horse.Variant.MODDED;
    }

    public static class Chested extends CraftChestedHorse {

        public Chested(CraftServer server, AbstractChestedHorse entity) {
            super(server, entity);
        }

        public @NotNull Horse.Variant getVariant() {
            return Horse.Variant.MODDED;
        }
    }
}
