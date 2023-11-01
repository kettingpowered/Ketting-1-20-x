package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.PiglinBrute;

public class CraftPiglinBrute extends CraftPiglinAbstract implements PiglinBrute {

    public CraftPiglinBrute(CraftServer server, net.minecraft.world.entity.monster.piglin.PiglinBrute entity) {
        super(server, (AbstractPiglin) entity);
    }

    public net.minecraft.world.entity.monster.piglin.PiglinBrute getHandle() {
        return (net.minecraft.world.entity.monster.piglin.PiglinBrute) super.getHandle();
    }

    public String toString() {
        return "CraftPiglinBrute";
    }
}
