package org.bukkit.craftbukkit.v1_20_R2.tag;

import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;

public abstract class CraftTag implements Tag {

    protected final Registry registry;
    protected final TagKey tag;
    private HolderSet.Named handle;

    public CraftTag(Registry registry, TagKey tag) {
        this.registry = registry;
        this.tag = tag;
        this.handle = (HolderSet.Named) registry.getTag(this.tag).orElseThrow();
    }

    protected HolderSet.Named getHandle() {
        return this.handle;
    }

    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(this.tag.location());
    }
}
