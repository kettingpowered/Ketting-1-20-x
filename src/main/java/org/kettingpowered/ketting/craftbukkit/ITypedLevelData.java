package org.kettingpowered.ketting.craftbukkit;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import org.jetbrains.annotations.Nullable;

public interface ITypedLevelData {
    String getOriginalLevelName();

    @Nullable ResourceKey<LevelStem> getTypeKey();
    void setTypeKey(ResourceKey<LevelStem> typeKey);

    default boolean hasNonDefaultTypeKey() {
        return getTypeKey() != null && getTypeKey() != LevelStem.OVERWORLD;
    }
}
