package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.Cow;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.MushroomCow.Variant;

public class CraftMushroomCow extends CraftCow implements MushroomCow {

    public CraftMushroomCow(CraftServer server, net.minecraft.world.entity.animal.MushroomCow entity) {
        super(server, (Cow) entity);
    }

    public net.minecraft.world.entity.animal.MushroomCow getHandle() {
        return (net.minecraft.world.entity.animal.MushroomCow) this.entity;
    }

    public Variant getVariant() {
        return Variant.values()[this.getHandle().getVariant().ordinal()];
    }

    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant1 != null, "variant");
        this.getHandle().setVariant(net.minecraft.world.entity.animal.MushroomCow.MushroomType.values()[variant2.ordinal()]);
    }

    public String toString() {
        return "CraftMushroomCow";
    }
}
