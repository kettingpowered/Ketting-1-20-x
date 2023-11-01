package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.AbstractGolem;
import org.bukkit.DyeColor;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.entity.Shulker;

public class CraftShulker extends CraftGolem implements Shulker, CraftEnemy {

    public CraftShulker(CraftServer server, net.minecraft.world.entity.monster.Shulker entity) {
        super(server, (AbstractGolem) entity);
    }

    public String toString() {
        return "CraftShulker";
    }

    public net.minecraft.world.entity.monster.Shulker getHandle() {
        return (net.minecraft.world.entity.monster.Shulker) this.entity;
    }

    public DyeColor getColor() {
        return DyeColor.getByWoolData((Byte) this.getHandle().getEntityData().get(net.minecraft.world.entity.monster.Shulker.DATA_COLOR_ID));
    }

    public void setColor(DyeColor color) {
        this.getHandle().getEntityData().set(net.minecraft.world.entity.monster.Shulker.DATA_COLOR_ID, color == null ? 16 : color.getWoolData());
    }

    public float getPeek() {
        return (float) this.getHandle().getRawPeekAmount() / 100.0F;
    }

    public void setPeek(float value) {
        Preconditions.checkArgument(value >= 0.0F && value <= 1.0F, "value needs to be in between or equal to 0 and 1");
        this.getHandle().setRawPeekAmount((int) (value * 100.0F));
    }

    public BlockFace getAttachedFace() {
        return CraftBlock.notchToBlockFace(this.getHandle().getAttachFace());
    }

    public void setAttachedFace(BlockFace face) {
        Preconditions.checkNotNull(face, "face cannot be null");
        Preconditions.checkArgument(face.isCartesian(), "%s is not a valid block face to attach a shulker to, a cartesian block face is expected", face);
        this.getHandle().setAttachFace(CraftBlock.blockFaceToNotch(face));
    }
}
