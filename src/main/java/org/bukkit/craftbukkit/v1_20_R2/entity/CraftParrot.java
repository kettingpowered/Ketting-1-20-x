package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.TamableAnimal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Parrot.Variant;

public class CraftParrot extends CraftTameableAnimal implements Parrot {

    public CraftParrot(CraftServer server, net.minecraft.world.entity.animal.Parrot parrot) {
        super(server, (TamableAnimal) parrot);
    }

    public net.minecraft.world.entity.animal.Parrot getHandle() {
        return (net.minecraft.world.entity.animal.Parrot) this.entity;
    }

    public Variant getVariant() {
        return Variant.values()[this.getHandle().getVariant().ordinal()];
    }

    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant1 != null, "variant");
        this.getHandle().setVariant(net.minecraft.world.entity.animal.Parrot.Variant.byId(variant2.ordinal()));
    }

    public String toString() {
        return "CraftParrot";
    }

    public boolean isDancing() {
        return this.getHandle().isPartyParrot();
    }
}
