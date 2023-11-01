package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryAbstractHorse;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.AbstractHorseInventory;

public abstract class CraftAbstractHorse extends CraftAnimals implements AbstractHorse {

    public CraftAbstractHorse(CraftServer server, net.minecraft.world.entity.animal.horse.AbstractHorse entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.horse.AbstractHorse getHandle() {
        return (net.minecraft.world.entity.animal.horse.AbstractHorse) this.entity;
    }

    public void setVariant(Variant variant) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public int getDomestication() {
        return this.getHandle().getTemper();
    }

    public void setDomestication(int value) {
        Preconditions.checkArgument(value >= 0 && value <= this.getMaxDomestication(), "Domestication level (%s) need to be between %s and %s (max domestication)", value, 0, this.getMaxDomestication());
        this.getHandle().setTemper(value);
    }

    public int getMaxDomestication() {
        return this.getHandle().getMaxTemper();
    }

    public void setMaxDomestication(int value) {
        Preconditions.checkArgument(value > 0, "Max domestication (%s) cannot be zero or less", value);
        this.getHandle().maxDomestication = value;
    }

    public double getJumpStrength() {
        return this.getHandle().getCustomJump();
    }

    public void setJumpStrength(double strength) {
        Preconditions.checkArgument(strength >= 0.0D, "Jump strength (%s) cannot be less than zero", strength);
        this.getHandle().getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(strength);
    }

    public boolean isTamed() {
        return this.getHandle().isTamed();
    }

    public void setTamed(boolean tamed) {
        this.getHandle().setTamed(tamed);
    }

    public AnimalTamer getOwner() {
        return this.getOwnerUUID() == null ? null : this.getServer().getOfflinePlayer(this.getOwnerUUID());
    }

    public void setOwner(AnimalTamer owner) {
        if (owner != null) {
            this.setTamed(true);
            this.getHandle().setTarget((LivingEntity) null, (TargetReason) null, false);
            this.setOwnerUUID(owner.getUniqueId());
        } else {
            this.setTamed(false);
            this.setOwnerUUID((UUID) null);
        }

    }

    public UUID getOwnerUUID() {
        return this.getHandle().getOwnerUUID();
    }

    public void setOwnerUUID(UUID uuid) {
        this.getHandle().setOwnerUUID(uuid);
    }

    public boolean isEatingHaystack() {
        return this.getHandle().isEating();
    }

    public void setEatingHaystack(boolean eatingHaystack) {
        this.getHandle().setEating(eatingHaystack);
    }

    public AbstractHorseInventory getInventory() {
        return new CraftInventoryAbstractHorse(this.getHandle().inventory);
    }
}
