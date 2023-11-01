package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Stray;

public class CraftStray extends CraftAbstractSkeleton implements Stray {

    public CraftStray(CraftServer server, net.minecraft.world.entity.monster.Stray entity) {
        super(server, (AbstractSkeleton) entity);
    }

    public String toString() {
        return "CraftStray";
    }

    public SkeletonType getSkeletonType() {
        return SkeletonType.STRAY;
    }
}
