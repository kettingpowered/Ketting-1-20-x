package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Panda.Gene;

public class CraftPanda extends CraftAnimals implements Panda {

    public CraftPanda(CraftServer server, net.minecraft.world.entity.animal.Panda entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.Panda getHandle() {
        return (net.minecraft.world.entity.animal.Panda) super.getHandle();
    }

    public String toString() {
        return "CraftPanda";
    }

    public Gene getMainGene() {
        return fromNms(this.getHandle().getMainGene());
    }

    public void setMainGene(Gene gene) {
        this.getHandle().setMainGene(toNms(gene));
    }

    public Gene getHiddenGene() {
        return fromNms(this.getHandle().getHiddenGene());
    }

    public void setHiddenGene(Gene gene) {
        this.getHandle().setHiddenGene(toNms(gene));
    }

    public boolean isRolling() {
        return this.getHandle().isRolling();
    }

    public void setRolling(boolean flag) {
        this.getHandle().roll(flag);
    }

    public boolean isSneezing() {
        return this.getHandle().isSneezing();
    }

    public void setSneezing(boolean flag) {
        this.getHandle().sneeze(flag);
    }

    public boolean isSitting() {
        return this.getHandle().isSitting();
    }

    public void setSitting(boolean flag) {
        this.getHandle().sit(flag);
    }

    public boolean isOnBack() {
        return this.getHandle().isOnBack();
    }

    public void setOnBack(boolean flag) {
        this.getHandle().setOnBack(flag);
    }

    public boolean isEating() {
        return this.getHandle().isEating();
    }

    public void setEating(boolean flag) {
        this.getHandle().eat(flag);
    }

    public boolean isScared() {
        return this.getHandle().isScared();
    }

    public int getUnhappyTicks() {
        return this.getHandle().getUnhappyCounter();
    }

    public static Gene fromNms(net.minecraft.world.entity.animal.Panda.Gene gene) {
        Preconditions.checkArgument(gene != null, "Gene may not be null");
        return Gene.values()[gene.ordinal()];
    }

    public static net.minecraft.world.entity.animal.Panda.Gene toNms(Gene gene) {
        Preconditions.checkArgument(gene != null, "Gene may not be null");
        return net.minecraft.world.entity.animal.Panda.Gene.values()[gene.ordinal()];
    }
}
