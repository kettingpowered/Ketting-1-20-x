package org.bukkit.craftbukkit.v1_20_R2.entity;

import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.Entity;

public class CraftEnderDragonPart extends CraftComplexPart implements EnderDragonPart {

    public CraftEnderDragonPart(CraftServer server, net.minecraft.world.entity.boss.EnderDragonPart entity) {
        super(server, entity);
    }

    public EnderDragon getParent() {
        return (EnderDragon) super.getParent();
    }

    public net.minecraft.world.entity.boss.EnderDragonPart getHandle() {
        return (net.minecraft.world.entity.boss.EnderDragonPart) this.entity;
    }

    public String toString() {
        return "CraftEnderDragonPart";
    }

    public void damage(double amount) {
        this.getParent().damage(amount);
    }

    public void damage(double amount, Entity source) {
        this.getParent().damage(amount, source);
    }

    public double getHealth() {
        return this.getParent().getHealth();
    }

    public void setHealth(double health) {
        this.getParent().setHealth(health);
    }

    public double getAbsorptionAmount() {
        return this.getParent().getAbsorptionAmount();
    }

    public void setAbsorptionAmount(double amount) {
        this.getParent().setAbsorptionAmount(amount);
    }

    public double getMaxHealth() {
        return this.getParent().getMaxHealth();
    }

    public void setMaxHealth(double health) {
        this.getParent().setMaxHealth(health);
    }

    public void resetMaxHealth() {
        this.getParent().resetMaxHealth();
    }
}
