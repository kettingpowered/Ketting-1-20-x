package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.EntityLlamaTrader;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.TraderLlama;

public class CraftTraderLlama extends CraftLlama implements TraderLlama {

    public CraftTraderLlama(CraftServer server, EntityLlamaTrader entity) {
        super(server, entity);
    }

    @Override
    public EntityLlamaTrader getHandle() {
        return (EntityLlamaTrader) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftTraderLlama";
    }
}
