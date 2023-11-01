package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import java.util.concurrent.TimeUnit;
import net.minecraft.core.BlockPos;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class CraftWorldBorder implements WorldBorder {

    private final World world;
    private final net.minecraft.world.level.border.WorldBorder handle;

    public CraftWorldBorder(CraftWorld world) {
        this.world = world;
        this.handle = world.getHandle().getWorldBorder();
    }

    public CraftWorldBorder(net.minecraft.world.level.border.WorldBorder handle) {
        this.world = null;
        this.handle = handle;
    }

    public World getWorld() {
        return this.world;
    }

    public void reset() {
        this.getHandle().applySettings(net.minecraft.world.level.border.WorldBorder.DEFAULT_SETTINGS);
    }

    public double getSize() {
        return this.handle.getSize();
    }

    public void setSize(double newSize) {
        this.setSize(newSize, 0L);
    }

    public void setSize(double newSize, long time) {
        this.setSize(Math.min(this.getMaxSize(), Math.max(1.0D, newSize)), TimeUnit.SECONDS, Math.min(9223372036854775L, Math.max(0L, time)));
    }

    public void setSize(double newSize, TimeUnit unit, long time) {
        Preconditions.checkArgument(unit != null, "TimeUnit cannot be null.");
        Preconditions.checkArgument(time >= 0L, "time cannot be lower than 0");
        Preconditions.checkArgument(newSize >= 1.0D && newSize <= this.getMaxSize(), "newSize must be between 1.0D and %s", this.getMaxSize());
        if (time > 0L) {
            this.handle.lerpSizeBetween(this.handle.getSize(), newSize, unit.toMillis(time));
        } else {
            this.handle.setSize(newSize);
        }

    }

    public Location getCenter() {
        double x = this.handle.getCenterX();
        double z = this.handle.getCenterZ();

        return new Location(this.world, x, 0.0D, z);
    }

    public void setCenter(double x, double z) {
        Preconditions.checkArgument(Math.abs(x) <= this.getMaxCenterCoordinate(), "x coordinate cannot be outside +- %s", this.getMaxCenterCoordinate());
        Preconditions.checkArgument(Math.abs(z) <= this.getMaxCenterCoordinate(), "z coordinate cannot be outside +- %s", this.getMaxCenterCoordinate());
        this.handle.setCenter(x, z);
    }

    public void setCenter(Location location) {
        this.setCenter(location.getX(), location.getZ());
    }

    public double getDamageBuffer() {
        return this.handle.getDamageSafeZone();
    }

    public void setDamageBuffer(double blocks) {
        this.handle.setDamageSafeZone(blocks);
    }

    public double getDamageAmount() {
        return this.handle.getDamagePerBlock();
    }

    public void setDamageAmount(double damage) {
        this.handle.setDamagePerBlock(damage);
    }

    public int getWarningTime() {
        return this.handle.getWarningTime();
    }

    public void setWarningTime(int time) {
        this.handle.setWarningTime(time);
    }

    public int getWarningDistance() {
        return this.handle.getWarningBlocks();
    }

    public void setWarningDistance(int distance) {
        this.handle.setWarningBlocks(distance);
    }

    public boolean isInside(Location location) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        return (this.world == null || location.getWorld().equals(this.world)) && this.handle.isWithinBounds(BlockPos.containing(location.getX(), location.getY(), location.getZ()));
    }

    public double getMaxSize() {
        return 5.9999968E7D;
    }

    public double getMaxCenterCoordinate() {
        return 2.9999984E7D;
    }

    public net.minecraft.world.level.border.WorldBorder getHandle() {
        return this.handle;
    }

    public boolean isVirtual() {
        return this.world == null;
    }
}
