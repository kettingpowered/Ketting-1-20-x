package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.decoration.HangingEntity;
import org.bukkit.Art;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R2.CraftArt;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Painting;

public class CraftPainting extends CraftHanging implements Painting {

    public CraftPainting(CraftServer server, net.minecraft.world.entity.decoration.Painting entity) {
        super(server, (HangingEntity) entity);
    }

    public Art getArt() {
        return CraftArt.minecraftHolderToBukkit(this.getHandle().getVariant());
    }

    public boolean setArt(Art art) {
        return this.setArt(art, false);
    }

    public boolean setArt(Art art, boolean force) {
        net.minecraft.world.entity.decoration.Painting painting = this.getHandle();
        Holder oldArt = painting.getVariant();

        painting.setVariant(CraftArt.bukkitToMinecraftHolder(art));
        painting.setDirection(painting.getDirection());
        if (!force && !this.getHandle().generation && !painting.survives()) {
            painting.setVariant(oldArt);
            painting.setDirection(painting.getDirection());
            return false;
        } else {
            this.update();
            return true;
        }
    }

    public boolean setFacingDirection(BlockFace face, boolean force) {
        if (super.setFacingDirection(face, force)) {
            this.update();
            return true;
        } else {
            return false;
        }
    }

    public net.minecraft.world.entity.decoration.Painting getHandle() {
        return (net.minecraft.world.entity.decoration.Painting) this.entity;
    }

    public String toString() {
        return "CraftPainting{art=" + this.getArt() + "}";
    }
}
