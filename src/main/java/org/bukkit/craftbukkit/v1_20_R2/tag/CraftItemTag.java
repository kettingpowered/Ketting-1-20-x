package org.bukkit.craftbukkit.v1_20_R2.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;

public class CraftItemTag extends CraftTag {

    public CraftItemTag(Registry registry, TagKey tag) {
        super(registry, tag);
    }

    public boolean isTagged(Material item) {
        Item minecraft = CraftMagicNumbers.getItem(item);

        return minecraft == null ? false : minecraft.builtInRegistryHolder().is(this.tag);
    }

    public Set getValues() {
        return (Set) this.getHandle().stream().map((itemx) -> {
            return CraftMagicNumbers.getMaterial((Item) itemx.value());
        }).collect(Collectors.toUnmodifiableSet());
    }
}
