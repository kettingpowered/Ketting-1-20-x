package org.kettingpowered.ketting.adapters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.izzel.arclight.api.EnumHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import org.bukkit.World;
import org.kettingpowered.ketting.adapter.DimensionRegistry;
import org.kettingpowered.ketting.inject.ForgeInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DimensionRegistry_1_20_2_Adapter implements DimensionRegistry<Registry<LevelStem>, World.Environment, ResourceKey<LevelStem>> {

    private static BiMap<ResourceKey<LevelStem>, World.Environment> DIMENSION_REGISTRY = null;

    public String getMcVersion() {
        return "1.20.2";
    }

    public void createDefaults() {
        DIMENSION_REGISTRY = HashBiMap.create(ImmutableMap.<ResourceKey<LevelStem>, World.Environment>builder()
                        .put(LevelStem.OVERWORLD, World.Environment.NORMAL)
                        .put(LevelStem.NETHER, World.Environment.NETHER)
                        .put(LevelStem.END, World.Environment.THE_END)
                        .build());
    }

    public void register(Registry<LevelStem> reg) {
        int i = World.Environment.values().length;
        List<World.Environment> newTypes = new ArrayList<>();
        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : reg.entrySet()) {
            ResourceKey<LevelStem> key = entry.getKey();
            World.Environment environment = DIMENSION_REGISTRY.get(key);
            if (environment == null) {
                String name = ForgeInject.standardize(key.location());
                environment = EnumHelper.makeEnum(World.Environment.class, name, i, ImmutableList.of(int.class), ImmutableList.of(i - 1));
                newTypes.add(environment);
                DIMENSION_REGISTRY.put(key, environment);
                ForgeInject.debug("Registered {} as environment {}", key.location(), environment);
                i++;
            }
        }
        EnumHelper.addEnums(World.Environment.class, newTypes);
        ForgeInject.debug("Registered {} environments", newTypes.size());
    }

    public World.Environment getKey(ResourceKey<LevelStem> levelStemResourceKey) {
        return DIMENSION_REGISTRY.getOrDefault(levelStemResourceKey, World.Environment.CUSTOM);
    }
}
