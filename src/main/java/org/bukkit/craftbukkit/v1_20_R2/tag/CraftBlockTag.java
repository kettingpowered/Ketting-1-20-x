package org.bukkit.craftbukkit.v1_20_R2.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;

public class CraftBlockTag extends CraftTag {

    public CraftBlockTag(Registry registry, TagKey tag) {
        super(registry, tag);
    }

    public boolean isTagged(Material item) {
        Block block = CraftMagicNumbers.getBlock(item);

        return block == null ? false : block.builtInRegistryHolder().is(this.tag);
    }

    public Set getValues() {
        return (Set) this.getHandle().stream().map((blockx) -> {
            return CraftMagicNumbers.getMaterial((Block) blockx.value());
        }).collect(Collectors.toUnmodifiableSet());
    }
}
