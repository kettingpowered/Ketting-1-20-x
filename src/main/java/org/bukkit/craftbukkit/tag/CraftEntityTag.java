package org.bukkit.craftbukkit.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.bukkit.craftbukkit.entity.CraftEntityType;
//import org.bukkit.entity.EntityType;

public class CraftEntityTag extends CraftTag<EntityType<?>, org.bukkit.entity.EntityType> {

    public CraftEntityTag(Registry<EntityType<?>> registry, TagKey<EntityType<?>> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(org.bukkit.entity.EntityType entity) {
        return CraftEntityType.bukkitToMinecraft(entity).is(tag);
    }

    @Override
    public Set<org.bukkit.entity.EntityType> getValues() {
        return getHandle().stream().map(Holder::value).map(CraftEntityType::minecraftToBukkit).collect(Collectors.toUnmodifiableSet());
    }
}
