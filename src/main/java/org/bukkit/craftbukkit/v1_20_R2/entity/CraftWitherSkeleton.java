package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.EntitySkeletonWither;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.WitherSkeleton;

public class CraftWitherSkeleton extends CraftAbstractSkeleton implements WitherSkeleton {

    public CraftWitherSkeleton(CraftServer server, EntitySkeletonWither entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftWitherSkeleton";
    }

    @Override
    public SkeletonType getSkeletonType() {
        return SkeletonType.WITHER;
    }
}
