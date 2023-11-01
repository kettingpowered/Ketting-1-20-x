package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.horse.Llama;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.TraderLlama;

public class CraftTraderLlama extends CraftLlama implements TraderLlama {

    public CraftTraderLlama(CraftServer server, net.minecraft.world.entity.animal.horse.TraderLlama entity) {
        super(server, (Llama) entity);
    }

    public net.minecraft.world.entity.animal.horse.TraderLlama getHandle() {
        return (net.minecraft.world.entity.animal.horse.TraderLlama) super.getHandle();
    }

    public String toString() {
        return "CraftTraderLlama";
    }
}
