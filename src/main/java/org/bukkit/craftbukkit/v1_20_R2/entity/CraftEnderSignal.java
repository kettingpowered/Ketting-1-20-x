package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Items;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.EnderSignal;
import org.bukkit.inventory.ItemStack;

public class CraftEnderSignal extends CraftEntity implements EnderSignal {

    public CraftEnderSignal(CraftServer server, EyeOfEnder entity) {
        super(server, entity);
    }

    public EyeOfEnder getHandle() {
        return (EyeOfEnder) this.entity;
    }

    public String toString() {
        return "CraftEnderSignal";
    }

    public Location getTargetLocation() {
        return new Location(this.getWorld(), this.getHandle().tx, this.getHandle().ty, this.getHandle().tz, this.getHandle().getYRot(), this.getHandle().getXRot());
    }

    public void setTargetLocation(Location location) {
        Preconditions.checkArgument(this.getWorld().equals(location.getWorld()), "Cannot target EnderSignal across worlds");
        this.getHandle().signalTo(CraftLocation.toBlockPosition(location));
    }

    public boolean getDropItem() {
        return this.getHandle().surviveAfterDeath;
    }

    public void setDropItem(boolean shouldDropItem) {
        this.getHandle().surviveAfterDeath = shouldDropItem;
    }

    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(this.getHandle().getItem());
    }

    public void setItem(ItemStack item) {
        this.getHandle().setItem(item != null ? CraftItemStack.asNMSCopy(item) : Items.ENDER_EYE.getDefaultInstance());
    }

    public int getDespawnTimer() {
        return this.getHandle().life;
    }

    public void setDespawnTimer(int time) {
        this.getHandle().life = time;
    }
}
