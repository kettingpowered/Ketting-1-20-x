package org.bukkit.craftbukkit.v1_20_R3.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
//import net.minecraft.world.level.material.Fluid;
import org.bukkit.Fluid;
import org.bukkit.craftbukkit.v1_20_R3.CraftFluid;

public class CraftFluidTag extends CraftTag<net.minecraft.world.level.material.Fluid, Fluid> {

    public CraftFluidTag(Registry<net.minecraft.world.level.material.Fluid> registry, TagKey<net.minecraft.world.level.material.Fluid> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(Fluid fluid) {
        return CraftFluid.bukkitToMinecraft(fluid).is(tag);
    }

    @Override
    public Set<Fluid> getValues() {
        return getHandle().stream().map(Holder::value).map(CraftFluid::minecraftToBukkit).collect(Collectors.toUnmodifiableSet());
    }
}
