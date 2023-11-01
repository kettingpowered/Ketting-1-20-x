package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Beehive;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftBee;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.Bee;

public class CraftBeehive extends CraftBlockEntityState implements Beehive {

    public CraftBeehive(World world, BeehiveBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftBeehive(CraftBeehive state) {
        super((CraftBlockEntityState) state);
    }

    public Location getFlower() {
        BlockPos flower = ((BeehiveBlockEntity) this.getSnapshot()).savedFlowerPos;

        return flower == null ? null : CraftLocation.toBukkit(flower, this.getWorld());
    }

    public void setFlower(Location location) {
        Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Flower must be in same world");
        ((BeehiveBlockEntity) this.getSnapshot()).savedFlowerPos = location == null ? null : CraftLocation.toBlockPosition(location);
    }

    public boolean isFull() {
        return ((BeehiveBlockEntity) this.getSnapshot()).isFull();
    }

    public boolean isSedated() {
        return this.isPlaced() && ((BeehiveBlockEntity) this.getTileEntity()).isSedated();
    }

    public int getEntityCount() {
        return ((BeehiveBlockEntity) this.getSnapshot()).getOccupantCount();
    }

    public int getMaxEntities() {
        return ((BeehiveBlockEntity) this.getSnapshot()).maxBees;
    }

    public void setMaxEntities(int max) {
        Preconditions.checkArgument(max > 0, "Max bees must be more than 0");
        ((BeehiveBlockEntity) this.getSnapshot()).maxBees = max;
    }

    public List releaseEntities() {
        this.ensureNoWorldGeneration();
        ArrayList bees = new ArrayList();

        if (this.isPlaced()) {
            BeehiveBlockEntity beehive = (BeehiveBlockEntity) this.getTileEntityFromWorld();
            Iterator iterator = beehive.releaseBees(this.getHandle(), BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED, true).iterator();

            while (iterator.hasNext()) {
                Entity bee = (Entity) iterator.next();

                bees.add((Bee) bee.getBukkitEntity());
            }
        }

        return bees;
    }

    public void addEntity(Bee entity) {
        Preconditions.checkArgument(entity != null, "Entity must not be null");
        ((BeehiveBlockEntity) this.getSnapshot()).addOccupant(((CraftBee) entity).getHandle(), false);
    }

    public CraftBeehive copy() {
        return new CraftBeehive(this);
    }
}
