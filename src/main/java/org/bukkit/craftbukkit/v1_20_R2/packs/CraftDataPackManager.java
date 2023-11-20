package org.bukkit.craftbukkit.v1_20_R2.packs;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.WorldData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntityType;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.packs.DataPack;
import org.bukkit.packs.DataPackManager;
import org.slf4j.Logger;

public class CraftDataPackManager implements DataPackManager {
    public static final Logger LOGGER = LogUtils.getLogger();
    private final PackRepository handle;

    public CraftDataPackManager(PackRepository resourcePackRepository) {
        this.handle = resourcePackRepository;
    }

    public PackRepository getHandle() {
        return this.handle;
    }

    @Override
    public Collection<DataPack> getDataPacks() {
        // Based in the command for datapacks need reload for get the updated list of datapacks
        this.getHandle().reload();

        Collection<Pack> availablePacks = this.getHandle().getAvailablePacks();
        return availablePacks.stream().map(CraftDataPack::new).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public DataPack getDataPack(NamespacedKey namespacedKey) {
        Preconditions.checkArgument(namespacedKey != null, "namespacedKey cannot be null");

        return new CraftDataPack(this.getHandle().getPack(namespacedKey.getKey()));
    }

    @Override
    public Collection<DataPack> getEnabledDataPacks(World world) {
        Preconditions.checkArgument(world != null, "world cannot be null");

        CraftWorld craftWorld = ((CraftWorld) world);
        WorldData worldData;
        if (craftWorld.getHandle().serverLevelData instanceof WorldData worldData1)
            worldData = worldData1;
        else {
            LOGGER.error("Unable to get per World enabled data packs. Using server-wide enabled data packs as a fallback.", new ClassCastException("Unable to cast ServerLevelData to WorldData"));
            worldData = craftWorld.getHandle().getServer().getWorldData();
        }
        return worldData.getDataConfiguration().dataPacks().getEnabled().stream().map(packName -> {
            Pack resourcePackLoader = this.getHandle().getPack(packName);
            if (resourcePackLoader != null) {
                return new CraftDataPack(resourcePackLoader);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<DataPack> getDisabledDataPacks(World world) {
        Preconditions.checkArgument(world != null, "world cannot be null");

        CraftWorld craftWorld = ((CraftWorld) world);
        WorldData worldData;
        if (craftWorld.getHandle().serverLevelData instanceof WorldData worldData1)
            worldData = worldData1;
        else {
            LOGGER.error("Unable to get per World disabled data packs. Using server-wide disabled data packs as a fallback.", new ClassCastException("Unable to cast ServerLevelData to WorldData"));
            worldData = craftWorld.getHandle().getServer().getWorldData();
        }
        return worldData.getDataConfiguration().dataPacks().getDisabled().stream().map(packName -> {
            Pack resourcePackLoader = this.getHandle().getPack(packName);
            if (resourcePackLoader != null) {
                return new CraftDataPack(resourcePackLoader);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isEnabledByFeature(Material material, World world) {
        Preconditions.checkArgument(material != null, "material cannot be null");
        Preconditions.checkArgument(material.isItem() || material.isBlock(), "material need to be a item or block");
        Preconditions.checkArgument(world != null, "world cannot be null");

        CraftWorld craftWorld = ((CraftWorld) world);
        if (material.isItem()) {
            return CraftMagicNumbers.getItem(material).isEnabled(craftWorld.getHandle().enabledFeatures());
        } else if (material.isBlock()) {
            return CraftMagicNumbers.getBlock(material).isEnabled(craftWorld.getHandle().enabledFeatures());
        }
        return false;
    }

    @Override
    public boolean isEnabledByFeature(EntityType entityType, World world) {
        Preconditions.checkArgument(entityType != null, "entityType cannot be null");
        Preconditions.checkArgument(world != null, "world cannot be null");
        Preconditions.checkArgument(entityType != EntityType.UNKNOWN, "EntityType.UNKNOWN its not allowed here");

        CraftWorld craftWorld = ((CraftWorld) world);
        net.minecraft.world.entity.EntityType<?> nmsEntity = CraftEntityType.bukkitToMinecraft(entityType);
        return nmsEntity.isEnabled(craftWorld.getHandle().enabledFeatures());
    }
}
