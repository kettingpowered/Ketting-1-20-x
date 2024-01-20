package org.bukkit.craftbukkit.v1_20_R1.tag;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;
//import org.bukkit.entity.EntityType;

public class CraftEntityTag extends CraftTag<EntityType<?>, org.bukkit.entity.EntityType> {

    public CraftEntityTag(net.minecraft.core.Registry<EntityType<?>> registry, TagKey<EntityType<?>> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(org.bukkit.entity.EntityType entity) {
        return registry.getHolderOrThrow(ResourceKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, CraftNamespacedKey.toMinecraft(entity.getKey()))).is(tag);
    }

    @Override
    public Set<org.bukkit.entity.EntityType> getValues() {
        return getHandle().stream().map((nms) -> Registry.ENTITY_TYPE.get(CraftNamespacedKey.fromMinecraft(EntityType.getKey(nms.value())))).filter(Objects::nonNull).collect(Collectors.toUnmodifiableSet());
    }
}
