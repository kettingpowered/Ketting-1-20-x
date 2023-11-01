package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.LeashHitch;

public class CraftLeash extends CraftHanging implements LeashHitch {

    public CraftLeash(CraftServer server, LeashFenceKnotEntity entity) {
        super(server, (HangingEntity) entity);
    }

    public boolean setFacingDirection(BlockFace face, boolean force) {
        Preconditions.checkArgument(face == BlockFace.SELF, "%s is not a valid facing direction", face);
        return force || this.getHandle().generation || this.getHandle().survives();
    }

    public BlockFace getFacing() {
        return BlockFace.SELF;
    }

    public LeashFenceKnotEntity getHandle() {
        return (LeashFenceKnotEntity) this.entity;
    }

    public String toString() {
        return "CraftLeash";
    }
}
