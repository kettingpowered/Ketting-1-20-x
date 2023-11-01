package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.decoration.ItemFrame;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.GlowItemFrame;

public class CraftGlowItemFrame extends CraftItemFrame implements GlowItemFrame {

    public CraftGlowItemFrame(CraftServer server, net.minecraft.world.entity.decoration.GlowItemFrame entity) {
        super(server, (ItemFrame) entity);
    }

    public net.minecraft.world.entity.decoration.GlowItemFrame getHandle() {
        return (net.minecraft.world.entity.decoration.GlowItemFrame) super.getHandle();
    }

    public String toString() {
        return "CraftGlowItemFrame{item=" + this.getItem() + ", rotation=" + this.getRotation() + "}";
    }
}
