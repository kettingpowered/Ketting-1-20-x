package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Skeleton.SkeletonType;

public abstract class CraftAbstractSkeleton extends CraftMonster implements AbstractSkeleton {

    public CraftAbstractSkeleton(CraftServer server, net.minecraft.world.entity.monster.AbstractSkeleton entity) {
        super(server, (Monster) entity);
    }

    public void setSkeletonType(SkeletonType type) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
