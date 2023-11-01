package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.SculkCatalyst;

public class CraftSculkCatalyst extends CraftBlockEntityState implements SculkCatalyst {

    public CraftSculkCatalyst(World world, SculkCatalystBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftSculkCatalyst(CraftSculkCatalyst state) {
        super((CraftBlockEntityState) state);
    }

    public void bloom(Block block, int charge) {
        Preconditions.checkArgument(block != null, "block cannot be null");
        Preconditions.checkArgument(charge > 0, "charge must be positive");
        this.requirePlaced();
        ((SculkCatalystBlockEntity) this.getTileEntity()).getListener().bloom(this.world.getHandle(), this.getPosition(), this.getHandle(), this.world.getHandle().getRandom());
        ((SculkCatalystBlockEntity) this.getTileEntity()).getListener().getSculkSpreader().addCursors(new BlockPos(block.getX(), block.getY(), block.getZ()), charge);
    }

    public CraftSculkCatalyst copy() {
        return new CraftSculkCatalyst(this);
    }
}
