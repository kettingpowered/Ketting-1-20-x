package org.bukkit.craftbukkit.v1_20_R2.entity;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.entity.Minecart;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public abstract class CraftMinecart extends CraftVehicle implements Minecart {

    public CraftMinecart(CraftServer server, AbstractMinecart entity) {
        super(server, entity);
    }

    public void setDamage(double damage) {
        this.getHandle().setDamage((float) damage);
    }

    public double getDamage() {
        return (double) this.getHandle().getDamage();
    }

    public double getMaxSpeed() {
        return this.getHandle().maxSpeed;
    }

    public void setMaxSpeed(double speed) {
        if (speed >= 0.0D) {
            this.getHandle().maxSpeed = speed;
        }

    }

    public boolean isSlowWhenEmpty() {
        return this.getHandle().slowWhenEmpty;
    }

    public void setSlowWhenEmpty(boolean slow) {
        this.getHandle().slowWhenEmpty = slow;
    }

    public Vector getFlyingVelocityMod() {
        return this.getHandle().getFlyingVelocityMod();
    }

    public void setFlyingVelocityMod(Vector flying) {
        this.getHandle().setFlyingVelocityMod(flying);
    }

    public Vector getDerailedVelocityMod() {
        return this.getHandle().getDerailedVelocityMod();
    }

    public void setDerailedVelocityMod(Vector derailed) {
        this.getHandle().setDerailedVelocityMod(derailed);
    }

    public AbstractMinecart getHandle() {
        return (AbstractMinecart) this.entity;
    }

    public void setDisplayBlock(MaterialData material) {
        if (material != null) {
            BlockState block = CraftMagicNumbers.getBlock(material);

            this.getHandle().setDisplayBlockState(block);
        } else {
            this.getHandle().setDisplayBlockState(Blocks.AIR.defaultBlockState());
            this.getHandle().setCustomDisplay(false);
        }

    }

    public void setDisplayBlockData(BlockData blockData) {
        if (blockData != null) {
            BlockState block = ((CraftBlockData) blockData).getState();

            this.getHandle().setDisplayBlockState(block);
        } else {
            this.getHandle().setDisplayBlockState(Blocks.AIR.defaultBlockState());
            this.getHandle().setCustomDisplay(false);
        }

    }

    public MaterialData getDisplayBlock() {
        BlockState blockData = this.getHandle().getDisplayBlockState();

        return CraftMagicNumbers.getMaterial(blockData);
    }

    public BlockData getDisplayBlockData() {
        BlockState blockData = this.getHandle().getDisplayBlockState();

        return CraftBlockData.fromData(blockData);
    }

    public void setDisplayBlockOffset(int offset) {
        this.getHandle().setDisplayOffset(offset);
    }

    public int getDisplayBlockOffset() {
        return this.getHandle().getDisplayOffset();
    }
}
