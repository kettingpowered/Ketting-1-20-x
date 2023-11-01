package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.TamableAnimal;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Wolf;

public class CraftWolf extends CraftTameableAnimal implements Wolf {

    public CraftWolf(CraftServer server, net.minecraft.world.entity.animal.Wolf wolf) {
        super(server, (TamableAnimal) wolf);
    }

    public boolean isAngry() {
        return this.getHandle().isAngry();
    }

    public void setAngry(boolean angry) {
        if (angry) {
            this.getHandle().startPersistentAngerTimer();
        } else {
            this.getHandle().stopBeingAngry();
        }

    }

    public net.minecraft.world.entity.animal.Wolf getHandle() {
        return (net.minecraft.world.entity.animal.Wolf) this.entity;
    }

    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) this.getHandle().getCollarColor().getId());
    }

    public void setCollarColor(DyeColor color) {
        this.getHandle().setCollarColor(net.minecraft.world.item.DyeColor.byId(color.getWoolData()));
    }

    public boolean isWet() {
        return this.getHandle().isWet();
    }

    public float getTailAngle() {
        return this.getHandle().getTailAngle();
    }

    public boolean isInterested() {
        return this.getHandle().isInterested();
    }

    public void setInterested(boolean flag) {
        this.getHandle().setIsInterested(flag);
    }
}
