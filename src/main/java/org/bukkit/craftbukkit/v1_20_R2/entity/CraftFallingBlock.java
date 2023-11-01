package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.item.FallingBlockEntity;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.entity.FallingBlock;

public class CraftFallingBlock extends CraftEntity implements FallingBlock {

    public CraftFallingBlock(CraftServer server, FallingBlockEntity entity) {
        super(server, entity);
    }

    public FallingBlockEntity getHandle() {
        return (FallingBlockEntity) this.entity;
    }

    public String toString() {
        return "CraftFallingBlock";
    }

    public Material getMaterial() {
        return this.getBlockData().getMaterial();
    }

    public BlockData getBlockData() {
        return CraftBlockData.fromData(this.getHandle().getBlockState());
    }

    public boolean getDropItem() {
        return this.getHandle().dropItem;
    }

    public void setDropItem(boolean drop) {
        this.getHandle().dropItem = drop;
    }

    public boolean getCancelDrop() {
        return this.getHandle().cancelDrop;
    }

    public void setCancelDrop(boolean cancelDrop) {
        this.getHandle().cancelDrop = cancelDrop;
    }

    public boolean canHurtEntities() {
        return this.getHandle().hurtEntities;
    }

    public void setHurtEntities(boolean hurtEntities) {
        this.getHandle().hurtEntities = hurtEntities;
    }

    public void setTicksLived(int value) {
        super.setTicksLived(value);
        this.getHandle().time = value;
    }

    public float getDamagePerBlock() {
        return this.getHandle().fallDamagePerDistance;
    }

    public void setDamagePerBlock(float damage) {
        Preconditions.checkArgument((double) damage >= 0.0D, "damage must be >= 0.0, given %s", damage);
        this.getHandle().fallDamagePerDistance = damage;
        if ((double) damage > 0.0D) {
            this.setHurtEntities(true);
        }

    }

    public int getMaxDamage() {
        return this.getHandle().fallDamageMax;
    }

    public void setMaxDamage(int damage) {
        Preconditions.checkArgument(damage >= 0, "damage must be >= 0, given %s", damage);
        this.getHandle().fallDamageMax = damage;
        if (damage > 0) {
            this.setHurtEntities(true);
        }

    }
}
