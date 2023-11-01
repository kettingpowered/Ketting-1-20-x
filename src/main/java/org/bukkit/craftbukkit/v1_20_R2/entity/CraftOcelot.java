package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;

public class CraftOcelot extends CraftAnimals implements Ocelot {

    public CraftOcelot(CraftServer server, net.minecraft.world.entity.animal.Ocelot ocelot) {
        super(server, (Animal) ocelot);
    }

    public net.minecraft.world.entity.animal.Ocelot getHandle() {
        return (net.minecraft.world.entity.animal.Ocelot) this.entity;
    }

    public boolean isTrusting() {
        return this.getHandle().isTrusting();
    }

    public void setTrusting(boolean trust) {
        this.getHandle().setTrusting(trust);
    }

    public Type getCatType() {
        return Type.WILD_OCELOT;
    }

    public void setCatType(Type type) {
        throw new UnsupportedOperationException("Cats are now a different entity!");
    }

    public String toString() {
        return "CraftOcelot";
    }
}
