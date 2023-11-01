package org.bukkit.craftbukkit.v1_20_R2.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntityType;
import org.bukkit.entity.EntityType;

public class CraftEntityTag extends CraftTag {

    public CraftEntityTag(Registry registry, TagKey tag) {
        super(registry, tag);
    }

    public boolean isTagged(EntityType entity) {
        return CraftEntityType.bukkitToMinecraft(entity).is(this.tag);
    }

    public Set getValues() {
        return (Set) this.getHandle().stream().map(Holder::value).map(CraftEntityType::minecraftToBukkit).collect(Collectors.toUnmodifiableSet());
    }
}
