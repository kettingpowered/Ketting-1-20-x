package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Rabbit.Type;

public class CraftRabbit extends CraftAnimals implements Rabbit {

    public CraftRabbit(CraftServer server, net.minecraft.world.entity.animal.Rabbit entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.Rabbit getHandle() {
        return (net.minecraft.world.entity.animal.Rabbit) this.entity;
    }

    public String toString() {
        return "CraftRabbit{RabbitType=" + this.getRabbitType() + "}";
    }

    public Type getRabbitType() {
        return Type.values()[this.getHandle().getVariant().ordinal()];
    }

    public void setRabbitType(Type type) {
        this.getHandle().setVariant(net.minecraft.world.entity.animal.Rabbit.Variant.values()[type.ordinal()]);
    }
}
