package org.bukkit.craftbukkit.v1_20_R2.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;

public class CraftDimensionUtil {

    private CraftDimensionUtil() {}

    public static ResourceKey getMainDimensionKey(Level world) {
        ResourceKey typeKey = world.getTypeKey();

        return typeKey == LevelStem.OVERWORLD ? Level.OVERWORLD : (typeKey == LevelStem.NETHER ? Level.NETHER : (typeKey == LevelStem.END ? Level.END : world.dimension()));
    }
}
