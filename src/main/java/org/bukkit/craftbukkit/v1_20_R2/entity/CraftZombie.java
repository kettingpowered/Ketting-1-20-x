package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombieVillager;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Zombie;

public class CraftZombie extends CraftMonster implements Zombie {

    public CraftZombie(CraftServer server, net.minecraft.world.entity.monster.Zombie entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.monster.Zombie getHandle() {
        return (net.minecraft.world.entity.monster.Zombie) this.entity;
    }

    public String toString() {
        return "CraftZombie";
    }

    public boolean isBaby() {
        return this.getHandle().isBaby();
    }

    public void setBaby(boolean flag) {
        this.getHandle().setBaby(flag);
    }

    public boolean isVillager() {
        return this.getHandle() instanceof ZombieVillager;
    }

    public void setVillager(boolean flag) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void setVillagerProfession(Profession profession) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public Profession getVillagerProfession() {
        return null;
    }

    public boolean isConverting() {
        return this.getHandle().isUnderWaterConverting();
    }

    public int getConversionTime() {
        Preconditions.checkState(this.isConverting(), "Entity not converting");
        return this.getHandle().conversionTime;
    }

    public void setConversionTime(int time) {
        if (time < 0) {
            this.getHandle().conversionTime = -1;
            this.getHandle().getEntityData().set(net.minecraft.world.entity.monster.Zombie.DATA_DROWNED_CONVERSION_ID, false);
        } else {
            this.getHandle().startUnderWaterConversion(time);
        }

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

    public boolean canBreakDoors() {
        return this.getHandle().canBreakDoors();
    }

    public void setCanBreakDoors(boolean flag) {
        this.getHandle().setCanBreakDoors(flag);
    }
}
