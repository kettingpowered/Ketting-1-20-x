package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.bukkit.World;
import org.bukkit.block.Bell;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class CraftBell extends CraftBlockEntityState implements Bell {

    public CraftBell(World world, BellBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftBell(CraftBell state) {
        super((CraftBlockEntityState) state);
    }

    public boolean ring(Entity entity, BlockFace direction) {
        Preconditions.checkArgument(direction == null || direction.isCartesian(), "direction must be cartesian, given %s", direction);
        BlockEntity tileEntity = this.getTileEntityFromWorld();

        if (tileEntity == null) {
            return false;
        } else {
            net.minecraft.world.entity.Entity nmsEntity = entity != null ? ((CraftEntity) entity).getHandle() : null;
            Direction enumDirection = CraftBlock.blockFaceToNotch(direction);

            return ((BellBlock) Blocks.BELL).attemptToRing(nmsEntity, this.world.getHandle(), this.getPosition(), enumDirection);
        }
    }

    public boolean ring(Entity entity) {
        return this.ring(entity, (BlockFace) null);
    }

    public boolean ring(BlockFace direction) {
        return this.ring((Entity) null, direction);
    }

    public boolean ring() {
        return this.ring((Entity) null, (BlockFace) null);
    }

    public boolean isShaking() {
        return ((BellBlockEntity) this.getSnapshot()).shaking;
    }

    public int getShakingTicks() {
        return ((BellBlockEntity) this.getSnapshot()).ticks;
    }

    public boolean isResonating() {
        return ((BellBlockEntity) this.getSnapshot()).resonating;
    }

    public int getResonatingTicks() {
        return this.isResonating() ? ((BellBlockEntity) this.getSnapshot()).ticks : 0;
    }

    public CraftBell copy() {
        return new CraftBell(this);
    }
}
