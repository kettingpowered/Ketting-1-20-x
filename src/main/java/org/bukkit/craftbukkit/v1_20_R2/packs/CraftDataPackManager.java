package org.bukkit.craftbukkit.v1_20_R2.packs;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntityType;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.packs.DataPack;
import org.bukkit.packs.DataPackManager;

public class CraftDataPackManager implements DataPackManager {

    private final PackRepository handle;

    public CraftDataPackManager(PackRepository resourcePackRepository) {
        this.handle = resourcePackRepository;
    }

    public PackRepository getHandle() {
        return this.handle;
    }

    public Collection getDataPacks() {
        this.getHandle().reload();
        Collection availablePacks = this.getHandle().getAvailablePacks();

        return (Collection) availablePacks.stream().map(CraftDataPack::new).collect(Collectors.toUnmodifiableList());
    }

    public DataPack getDataPack(NamespacedKey namespacedKey) {
        Preconditions.checkArgument(namespacedKey != null, "namespacedKey cannot be null");
        return new CraftDataPack(this.getHandle().getPack(namespacedKey.getKey()));
    }

    public Collection getEnabledDataPacks(World world) {
        Preconditions.checkArgument(world != null, "world cannot be null");
        CraftWorld craftWorld = (CraftWorld) world;

        return (Collection) craftWorld.getHandle().K.getDataConfiguration().dataPacks().getEnabled().stream().map((packNamex) -> {
            Pack resourcePackLoader = this.getHandle().getPack(packNamex);

            return resourcePackLoader != null ? new CraftDataPack(resourcePackLoader) : null;
        }).filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
    }

    public Collection getDisabledDataPacks(World world) {
        Preconditions.checkArgument(world != null, "world cannot be null");
        CraftWorld craftWorld = (CraftWorld) world;

        return (Collection) craftWorld.getHandle().K.getDataConfiguration().dataPacks().getDisabled().stream().map((packNamex) -> {
            Pack resourcePackLoader = this.getHandle().getPack(packNamex);

            return resourcePackLoader != null ? new CraftDataPack(resourcePackLoader) : null;
        }).filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
    }

    public boolean isEnabledByFeature(Material material, World world) {
        Preconditions.checkArgument(material != null, "material cannot be null");
        Preconditions.checkArgument(material.isItem() || material.isBlock(), "material need to be a item or block");
        Preconditions.checkArgument(world != null, "world cannot be null");
        CraftWorld craftWorld = (CraftWorld) world;

        return material.isItem() ? CraftMagicNumbers.getItem(material).isEnabled(craftWorld.getHandle().enabledFeatures()) : (material.isBlock() ? CraftMagicNumbers.getBlock(material).isEnabled(craftWorld.getHandle().enabledFeatures()) : false);
    }

    public boolean isEnabledByFeature(EntityType entityType, World world) {
        Preconditions.checkArgument(entityType != null, "entityType cannot be null");
        Preconditions.checkArgument(world != null, "world cannot be null");
        Preconditions.checkArgument(entityType != EntityType.UNKNOWN, "EntityType.UNKNOWN its not allowed here");
        CraftWorld craftWorld = (CraftWorld) world;
        net.minecraft.world.entity.EntityType nmsEntity = CraftEntityType.bukkitToMinecraft(entityType);

        return nmsEntity.isEnabled(craftWorld.getHandle().enabledFeatures());
    }
}
