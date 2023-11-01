package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.AbstractIllager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Vindicator;

public class CraftVindicator extends CraftIllager implements Vindicator {

    public CraftVindicator(CraftServer server, net.minecraft.world.entity.monster.Vindicator entity) {
        super(server, (AbstractIllager) entity);
    }

    public net.minecraft.world.entity.monster.Vindicator getHandle() {
        return (net.minecraft.world.entity.monster.Vindicator) super.getHandle();
    }

    public String toString() {
        return "CraftVindicator";
    }

    public boolean isJohnny() {
        return this.getHandle().isJohnny;
    }

    public void setJohnny(boolean johnny) {
        this.getHandle().isJohnny = johnny;
    }
}
