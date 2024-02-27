package org.kettingpowered.ketting.utils;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ServerInitHelper {

    private static final List<Reference> references = new ArrayList<>();

    public static void saveBukkitValues(ServerLevelData serverleveldata, ResourceKey<Level> levelKey, org.bukkit.generator.ChunkGenerator gen, org.bukkit.World.Environment env, org.bukkit.generator.BiomeProvider biomeProvider) {
        references.add(new Reference(serverleveldata, levelKey, new Object[] {gen, env, biomeProvider}));
    }

    /**
     * Retrieve the saved bukkit values
     * @return the saved bukkit values or null if none were saved
     */
    public static @Nullable Object[] retrieveBukkitValues(ServerLevelData serverleveldata, ResourceKey<Level> levelKey) {
        if (references.isEmpty())
            return null;

        final Reference referencer = references.stream()
                .filter(ref -> ref.matches(serverleveldata, levelKey))
                .findFirst().orElse(null);

        if (referencer == null)
            return null;

        references.remove(referencer);
        return referencer.bukkitValues();
    }

    private record Reference(ServerLevelData serverleveldata, ResourceKey<Level> levelKey, Object[] bukkitValues) {
        public boolean matches(ServerLevelData serverleveldata, ResourceKey<Level> levelKey) {
            return this.serverleveldata == serverleveldata && this.levelKey == levelKey;
        }
    }
}
