package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Monster;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.Vex;

public class CraftVex extends CraftMonster implements Vex {

    public CraftVex(CraftServer server, net.minecraft.world.entity.monster.Vex entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.Vex getHandle() {
        return (net.minecraft.world.entity.monster.Vex) super.getHandle();
    }

    public String toString() {
        return "CraftVex";
    }

    public boolean isCharging() {
        return this.getHandle().isCharging();
    }

    public void setCharging(boolean charging) {
        this.getHandle().setIsCharging(charging);
    }

    public Location getBound() {
        BlockPos blockPosition = this.getHandle().getBoundOrigin();

        return blockPosition == null ? null : CraftLocation.toBukkit(blockPosition, this.getWorld());
    }

    public void setBound(Location location) {
        if (location == null) {
            this.getHandle().setBoundOrigin((BlockPos) null);
        } else {
            Preconditions.checkArgument(this.getWorld().equals(location.getWorld()), "The bound world cannot be different to the entity's world.");
            this.getHandle().setBoundOrigin(CraftLocation.toBlockPosition(location));
        }

    }

    public int getLifeTicks() {
        return this.getHandle().limitedLifeTicks;
    }

    public void setLifeTicks(int lifeTicks) {
        this.getHandle().setLimitedLife(lifeTicks);
        if (lifeTicks < 0) {
            this.getHandle().hasLimitedLife = false;
        }

    }

    public boolean hasLimitedLife() {
        return this.getHandle().hasLimitedLife;
    }
}
