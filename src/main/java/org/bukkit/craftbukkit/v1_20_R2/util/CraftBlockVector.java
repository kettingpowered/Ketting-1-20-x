package org.bukkit.craftbukkit.v1_20_R2.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import org.bukkit.util.BlockVector;

public final class CraftBlockVector {

    private CraftBlockVector() {
    }

    public static Vec3i toBlockPosition(BlockVector blockVector) {
        return new Vec3i(blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ());
    }

    public static BlockVector toBukkit(BlockPos baseBlockPosition) {
        return new BlockVector(baseBlockPosition.getX(), baseBlockPosition.getY(), baseBlockPosition.getZ());
    }
}
