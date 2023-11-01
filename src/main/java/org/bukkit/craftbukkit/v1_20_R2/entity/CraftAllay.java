package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.Allay;
import org.bukkit.inventory.Inventory;

public class CraftAllay extends CraftCreature implements Allay {

    public CraftAllay(CraftServer server, net.minecraft.world.entity.animal.allay.Allay entity) {
        super(server, (PathfinderMob) entity);
    }

    public net.minecraft.world.entity.animal.allay.Allay getHandle() {
        return (net.minecraft.world.entity.animal.allay.Allay) this.entity;
    }

    public String toString() {
        return "CraftAllay";
    }

    public Inventory getInventory() {
        return new CraftInventory(this.getHandle().getInventory());
    }

    public boolean canDuplicate() {
        return this.getHandle().canDuplicate();
    }

    public void setCanDuplicate(boolean canDuplicate) {
        this.getHandle().setCanDuplicate(canDuplicate);
    }

    public long getDuplicationCooldown() {
        return this.getHandle().duplicationCooldown;
    }

    public void setDuplicationCooldown(long l) {
        this.getHandle().duplicationCooldown = l;
    }

    public void resetDuplicationCooldown() {
        this.getHandle().resetDuplicationCooldown();
    }

    public boolean isDancing() {
        return this.getHandle().isDancing();
    }

    public void startDancing(Location location) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(location.getBlock().getType().equals(Material.JUKEBOX), "The Block in the Location need to be a JukeBox");
        this.getHandle().setJukeboxPlaying(CraftLocation.toBlockPosition(location), true);
    }

    public void startDancing() {
        this.getHandle().forceDancing = true;
        this.getHandle().setDancing(true);
    }

    public void stopDancing() {
        this.getHandle().forceDancing = false;
        this.getHandle().jukeboxPos = null;
        this.getHandle().setJukeboxPlaying((BlockPos) null, false);
    }

    public Allay duplicateAllay() {
        net.minecraft.world.entity.animal.allay.Allay nmsAllay = this.getHandle().duplicateAllay();

        return nmsAllay != null ? (Allay) nmsAllay.getBukkitEntity() : null;
    }

    public Location getJukebox() {
        BlockPos nmsJukeboxPos = this.getHandle().jukeboxPos;

        return nmsJukeboxPos != null ? CraftLocation.toBukkit(nmsJukeboxPos, this.getWorld()) : null;
    }
}
