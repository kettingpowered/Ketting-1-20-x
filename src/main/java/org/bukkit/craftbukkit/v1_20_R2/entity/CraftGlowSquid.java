package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.Squid;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.GlowSquid;

public class CraftGlowSquid extends CraftSquid implements GlowSquid {

    public CraftGlowSquid(CraftServer server, net.minecraft.world.entity.GlowSquid entity) {
        super(server, (Squid) entity);
    }

    public net.minecraft.world.entity.GlowSquid getHandle() {
        return (net.minecraft.world.entity.GlowSquid) super.getHandle();
    }

    public String toString() {
        return "CraftGlowSquid";
    }

    public int getDarkTicksRemaining() {
        return this.getHandle().getDarkTicksRemaining();
    }

    public void setDarkTicksRemaining(int darkTicksRemaining) {
        Preconditions.checkArgument(darkTicksRemaining >= 0, "darkTicksRemaining must be >= 0");
        this.getHandle().setDarkTicks(darkTicksRemaining);
    }
}
