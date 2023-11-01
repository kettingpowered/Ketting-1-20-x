package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.material.MaterialData;

public class CraftEnderman extends CraftMonster implements Enderman {

    public CraftEnderman(CraftServer server, EnderMan entity) {
        super(server, (Monster) entity);
    }

    public MaterialData getCarriedMaterial() {
        BlockState blockData = this.getHandle().getCarriedBlock();

        return blockData == null ? Material.AIR.getNewData((byte) 0) : CraftMagicNumbers.getMaterial(blockData);
    }

    public BlockData getCarriedBlock() {
        BlockState blockData = this.getHandle().getCarriedBlock();

        return blockData == null ? null : CraftBlockData.fromData(blockData);
    }

    public void setCarriedMaterial(MaterialData data) {
        this.getHandle().setCarriedBlock(CraftMagicNumbers.getBlock(data));
    }

    public void setCarriedBlock(BlockData blockData) {
        this.getHandle().setCarriedBlock(blockData == null ? null : ((CraftBlockData) blockData).getState());
    }

    public EnderMan getHandle() {
        return (EnderMan) this.entity;
    }

    public String toString() {
        return "CraftEnderman";
    }

    public boolean teleport() {
        return this.getHandle().teleport();
    }

    public boolean teleportTowards(Entity entity) {
        Preconditions.checkArgument(entity != null, "entity cannot be null");
        return this.getHandle().teleportTowards(((CraftEntity) entity).getHandle());
    }
}
