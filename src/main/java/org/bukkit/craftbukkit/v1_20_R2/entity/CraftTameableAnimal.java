package org.bukkit.craftbukkit.v1_20_R2.entity;

import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class CraftTameableAnimal extends CraftAnimals implements Tameable, Creature {

    public CraftTameableAnimal(CraftServer server, TamableAnimal entity) {
        super(server, (Animal) entity);
    }

    public TamableAnimal getHandle() {
        return (TamableAnimal) super.getHandle();
    }

    public UUID getOwnerUUID() {
        try {
            return this.getHandle().getOwnerUUID();
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    public void setOwnerUUID(UUID uuid) {
        this.getHandle().setOwnerUUID(uuid);
    }

    public AnimalTamer getOwner() {
        if (this.getOwnerUUID() == null) {
            return null;
        } else {
            Object owner = this.getServer().getPlayer(this.getOwnerUUID());

            if (owner == null) {
                owner = this.getServer().getOfflinePlayer(this.getOwnerUUID());
            }

            return (AnimalTamer) owner;
        }
    }

    public boolean isTamed() {
        return this.getHandle().isTame();
    }

    public void setOwner(AnimalTamer tamer) {
        if (tamer != null) {
            this.setTamed(true);
            this.getHandle().setTarget((LivingEntity) null, (TargetReason) null, false);
            this.setOwnerUUID(tamer.getUniqueId());
        } else {
            this.setTamed(false);
            this.setOwnerUUID((UUID) null);
        }

    }

    public void setTamed(boolean tame) {
        this.getHandle().setTame(tame);
        if (!tame) {
            this.setOwnerUUID((UUID) null);
        }

    }

    public boolean isSitting() {
        return this.getHandle().isInSittingPose();
    }

    public void setSitting(boolean sitting) {
        this.getHandle().setInSittingPose(sitting);
        this.getHandle().setOrderedToSit(sitting);
    }

    public String toString() {
        return this.getClass().getSimpleName() + "{owner=" + this.getOwner() + ",tamed=" + this.isTamed() + "}";
    }
}
