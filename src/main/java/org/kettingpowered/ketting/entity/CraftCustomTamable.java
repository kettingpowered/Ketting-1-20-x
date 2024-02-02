package org.kettingpowered.ketting.entity;

import net.minecraft.world.entity.TamableAnimal;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftTameableAnimal;

public class CraftCustomTamable extends CraftTameableAnimal {

    public CraftCustomTamable(CraftServer server, TamableAnimal entity) {
        super(server, entity);
    }
}
