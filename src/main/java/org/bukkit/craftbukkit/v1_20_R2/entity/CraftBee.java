package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.Bee;

public class CraftBee extends CraftAnimals implements Bee {

    public CraftBee(CraftServer server, net.minecraft.world.entity.animal.Bee entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.Bee getHandle() {
        return (net.minecraft.world.entity.animal.Bee) this.entity;
    }

    public String toString() {
        return "CraftBee";
    }

    public Location getHive() {
        BlockPos hive = this.getHandle().getHivePos();

        return hive == null ? null : CraftLocation.toBukkit(hive, this.getWorld());
    }

    public void setHive(Location location) {
        Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Hive must be in same world");
        this.getHandle().hivePos = location == null ? null : CraftLocation.toBlockPosition(location);
    }

    public Location getFlower() {
        BlockPos flower = this.getHandle().getSavedFlowerPos();

        return flower == null ? null : CraftLocation.toBukkit(flower, this.getWorld());
    }

    public void setFlower(Location location) {
        Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Flower must be in same world");
        this.getHandle().setSavedFlowerPos(location == null ? null : CraftLocation.toBlockPosition(location));
    }

    public boolean hasNectar() {
        return this.getHandle().hasNectar();
    }

    public void setHasNectar(boolean nectar) {
        this.getHandle().setHasNectar(nectar);
    }

    public boolean hasStung() {
        return this.getHandle().hasStung();
    }

    public void setHasStung(boolean stung) {
        this.getHandle().setHasStung(stung);
    }

    public int getAnger() {
        return this.getHandle().getRemainingPersistentAngerTime();
    }

    public void setAnger(int anger) {
        this.getHandle().setRemainingPersistentAngerTime(anger);
    }

    public int getCannotEnterHiveTicks() {
        return this.getHandle().stayOutOfHiveCountdown;
    }

    public void setCannotEnterHiveTicks(int ticks) {
        this.getHandle().setStayOutOfHiveCountdown(ticks);
    }
}
