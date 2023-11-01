package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.PiglinAbstract;

public class CraftPiglinAbstract extends CraftMonster implements PiglinAbstract {

    public CraftPiglinAbstract(CraftServer server, AbstractPiglin entity) {
        super(server, (Monster) entity);
    }

    public boolean isImmuneToZombification() {
        return this.getHandle().isImmuneToZombification();
    }

    public void setImmuneToZombification(boolean flag) {
        this.getHandle().setImmuneToZombification(flag);
    }

    public int getConversionTime() {
        Preconditions.checkState(this.isConverting(), "Entity not converting");
        return this.getHandle().timeInOverworld;
    }

    public void setConversionTime(int time) {
        if (time < 0) {
            this.getHandle().timeInOverworld = -1;
            this.getHandle().setImmuneToZombification(false);
        } else {
            this.getHandle().timeInOverworld = time;
        }

    }

    public boolean isConverting() {
        return this.getHandle().isConverting();
    }

    public boolean isBaby() {
        return this.getHandle().isBaby();
    }

    public void setBaby(boolean flag) {
        this.getHandle().setBaby(flag);
    }

    public int getAge() {
        return this.getHandle().isBaby() ? -1 : 0;
    }

    public void setAge(int i) {
        this.getHandle().setBaby(i < 0);
    }

    public void setAgeLock(boolean b) {}

    public boolean getAgeLock() {
        return false;
    }

    public void setBaby() {
        this.getHandle().setBaby(true);
    }

    public void setAdult() {
        this.getHandle().setBaby(false);
    }

    public boolean isAdult() {
        return !this.getHandle().isBaby();
    }

    public boolean canBreed() {
        return false;
    }

    public void setBreed(boolean b) {}

    public AbstractPiglin getHandle() {
        return (AbstractPiglin) super.getHandle();
    }
}
