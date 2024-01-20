package org.bukkit.craftbukkit.v1_20_R1.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Skeleton;

public abstract class CraftAbstractSkeleton extends CraftMonster implements AbstractSkeleton {

    public CraftAbstractSkeleton(CraftServer server, Monster entity) {
        super(server, entity);
    }

    @Override
    public void setSkeletonType(Skeleton.SkeletonType type) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
