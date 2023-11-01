package org.bukkit.craftbukkit.v1_20_R2.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import org.bukkit.Fluid;
import org.bukkit.craftbukkit.v1_20_R2.CraftFluid;

public class CraftFluidTag extends CraftTag {

    public CraftFluidTag(Registry registry, TagKey tag) {
        super(registry, tag);
    }

    public boolean isTagged(Fluid fluid) {
        return CraftFluid.bukkitToMinecraft(fluid).is(this.tag);
    }

    public Set getValues() {
        return (Set) this.getHandle().stream().map(Holder::value).map(CraftFluid::minecraftToBukkit).collect(Collectors.toUnmodifiableSet());
    }
}
