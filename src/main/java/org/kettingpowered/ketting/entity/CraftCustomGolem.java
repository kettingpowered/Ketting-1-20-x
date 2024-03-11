package org.kettingpowered.ketting.entity;

import net.minecraft.world.entity.animal.AbstractGolem;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftGolem;

public class CraftCustomGolem extends CraftGolem {

    public CraftCustomGolem(CraftServer server, AbstractGolem entity) {
        super(server, entity);
    }
}
