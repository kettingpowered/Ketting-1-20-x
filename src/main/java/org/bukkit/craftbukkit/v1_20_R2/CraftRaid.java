package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.Raid;
import org.bukkit.Raid.RaidStatus;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.Raider;

public final class CraftRaid implements Raid {

    private final net.minecraft.world.entity.raid.Raid handle;

    public CraftRaid(net.minecraft.world.entity.raid.Raid handle) {
        this.handle = handle;
    }

    public boolean isStarted() {
        return this.handle.isStarted();
    }

    public long getActiveTicks() {
        return this.handle.ticksActive;
    }

    public int getBadOmenLevel() {
        return this.handle.badOmenLevel;
    }

    public void setBadOmenLevel(int badOmenLevel) {
        int max = this.handle.getMaxBadOmenLevel();

        Preconditions.checkArgument(badOmenLevel >= 0 && badOmenLevel <= max, "Bad Omen level must be between 0 and %s", max);
        this.handle.badOmenLevel = badOmenLevel;
    }

    public Location getLocation() {
        BlockPos pos = this.handle.getCenter();
        Level world = this.handle.getLevel();

        return CraftLocation.toBukkit(pos, (World) world.getWorld());
    }

    public RaidStatus getStatus() {
        return this.handle.isStopped() ? RaidStatus.STOPPED : (this.handle.isVictory() ? RaidStatus.VICTORY : (this.handle.isLoss() ? RaidStatus.LOSS : RaidStatus.ONGOING));
    }

    public int getSpawnedGroups() {
        return this.handle.getGroupsSpawned();
    }

    public int getTotalGroups() {
        return this.handle.numGroups + (this.handle.badOmenLevel > 1 ? 1 : 0);
    }

    public int getTotalWaves() {
        return this.handle.numGroups;
    }

    public float getTotalHealth() {
        return this.handle.getHealthOfLivingRaiders();
    }

    public Set getHeroes() {
        return Collections.unmodifiableSet(this.handle.heroesOfTheVillage);
    }

    public List getRaiders() {
        return (List) this.handle.getRaiders().stream().map(new Function() {
            public Raider apply(net.minecraft.world.entity.raid.Raider entityRaider) {
                return (Raider) entityRaider.getBukkitEntity();
            }
        }).collect(ImmutableList.toImmutableList());
    }

    public net.minecraft.world.entity.raid.Raid getHandle() {
        return this.handle;
    }
}
