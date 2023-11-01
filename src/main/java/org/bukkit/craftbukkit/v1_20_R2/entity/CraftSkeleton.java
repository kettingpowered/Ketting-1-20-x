package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;

public class CraftSkeleton extends CraftAbstractSkeleton implements Skeleton {

    public CraftSkeleton(CraftServer server, net.minecraft.world.entity.monster.Skeleton entity) {
        super(server, (AbstractSkeleton) entity);
    }

    public boolean isConverting() {
        return this.getHandle().isFreezeConverting();
    }

    public int getConversionTime() {
        Preconditions.checkState(this.isConverting(), "Entity is not converting");
        return this.getHandle().conversionTime;
    }

    public void setConversionTime(int time) {
        if (time < 0) {
            this.getHandle().conversionTime = -1;
            this.getHandle().getEntityData().set(net.minecraft.world.entity.monster.Skeleton.DATA_STRAY_CONVERSION_ID, false);
        } else {
            this.getHandle().startFreezeConversion(time);
        }

    }

    public net.minecraft.world.entity.monster.Skeleton getHandle() {
        return (net.minecraft.world.entity.monster.Skeleton) this.entity;
    }

    public String toString() {
        return "CraftSkeleton";
    }

    public SkeletonType getSkeletonType() {
        return SkeletonType.NORMAL;
    }
}
