package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.entity.EnderCrystal;

public class CraftEnderCrystal extends CraftEntity implements EnderCrystal {

    public CraftEnderCrystal(CraftServer server, EndCrystal entity) {
        super(server, entity);
    }

    public boolean isShowingBottom() {
        return this.getHandle().showsBottom();
    }

    public void setShowingBottom(boolean showing) {
        this.getHandle().setShowBottom(showing);
    }

    public Location getBeamTarget() {
        BlockPos pos = this.getHandle().getBeamTarget();

        return pos == null ? null : CraftLocation.toBukkit(pos, this.getWorld());
    }

    public void setBeamTarget(Location location) {
        if (location == null) {
            this.getHandle().setBeamTarget((BlockPos) null);
        } else {
            if (location.getWorld() != this.getWorld()) {
                throw new IllegalArgumentException("Cannot set beam target location to different world");
            }

            this.getHandle().setBeamTarget(CraftLocation.toBlockPosition(location));
        }

    }

    public EndCrystal getHandle() {
        return (EndCrystal) this.entity;
    }

    public String toString() {
        return "CraftEnderCrystal";
    }
}
