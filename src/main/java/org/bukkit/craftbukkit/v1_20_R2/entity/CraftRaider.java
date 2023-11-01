package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Monster;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R2.CraftRaid;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.CraftSound;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.entity.Raider;

public abstract class CraftRaider extends CraftMonster implements Raider {

    public CraftRaider(CraftServer server, net.minecraft.world.entity.raid.Raider entity) {
        super(server, (Monster) entity);
    }

    public net.minecraft.world.entity.raid.Raider getHandle() {
        return (net.minecraft.world.entity.raid.Raider) super.getHandle();
    }

    public String toString() {
        return "CraftRaider";
    }

    public void setRaid(Raid raid) {
        this.getHandle().setCurrentRaid(raid != null ? ((CraftRaid) raid).getHandle() : null);
    }

    public Raid getRaid() {
        return this.getHandle().getCurrentRaid() == null ? null : new CraftRaid(this.getHandle().getCurrentRaid());
    }

    public void setWave(int wave) {
        Preconditions.checkArgument(wave >= 0, "wave must be >= 0");
        this.getHandle().setWave(wave);
    }

    public int getWave() {
        return this.getHandle().getWave();
    }

    public Block getPatrolTarget() {
        return this.getHandle().getPatrolTarget() == null ? null : CraftBlock.at(this.getHandle().level(), this.getHandle().getPatrolTarget());
    }

    public void setPatrolTarget(Block block) {
        if (block == null) {
            this.getHandle().setPatrolTarget((BlockPos) null);
        } else {
            Preconditions.checkArgument(block.getWorld().equals(this.getWorld()), "Block must be in same world");
            this.getHandle().setPatrolTarget(((CraftBlock) block).getPosition());
        }

    }

    public boolean isPatrolLeader() {
        return this.getHandle().isPatrolLeader();
    }

    public void setPatrolLeader(boolean leader) {
        this.getHandle().setPatrolLeader(leader);
    }

    public boolean isCanJoinRaid() {
        return this.getHandle().canJoinRaid();
    }

    public void setCanJoinRaid(boolean join) {
        this.getHandle().setCanJoinRaid(join);
    }

    public boolean isCelebrating() {
        return this.getHandle().isCelebrating();
    }

    public void setCelebrating(boolean celebrating) {
        this.getHandle().setCelebrating(celebrating);
    }

    public int getTicksOutsideRaid() {
        return this.getHandle().getTicksOutsideRaid();
    }

    public void setTicksOutsideRaid(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks must be >= 0");
        this.getHandle().setTicksOutsideRaid(ticks);
    }

    public Sound getCelebrationSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().getCelebrateSound());
    }
}
