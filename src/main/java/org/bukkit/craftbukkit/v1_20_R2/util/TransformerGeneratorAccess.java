package org.bukkit.craftbukkit.v1_20_R2.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.material.FluidState;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlockStates;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class TransformerGeneratorAccess extends DelegatedGeneratorAccess {

    private CraftStructureTransformer structureTransformer;

    public void setStructureTransformer(CraftStructureTransformer structureTransformer) {
        this.structureTransformer = structureTransformer;
    }

    public CraftStructureTransformer getStructureTransformer() {
        return this.structureTransformer;
    }

    public boolean addFreshEntity(Entity arg0) {
        return this.structureTransformer != null && !this.structureTransformer.transformEntity(arg0) ? false : super.addFreshEntity(arg0);
    }

    public boolean addFreshEntity(Entity arg0, SpawnReason arg1) {
        return this.structureTransformer != null && !this.structureTransformer.transformEntity(arg0) ? false : super.addFreshEntity(arg0, arg1);
    }

    public void addFreshEntityWithPassengers(Entity arg0) {
        if (this.structureTransformer == null || this.structureTransformer.transformEntity(arg0)) {
            super.addFreshEntityWithPassengers(arg0);
        }
    }

    public void addFreshEntityWithPassengers(Entity arg0, SpawnReason arg1) {
        if (this.structureTransformer == null || this.structureTransformer.transformEntity(arg0)) {
            super.addFreshEntityWithPassengers(arg0, arg1);
        }
    }

    public boolean setCraftBlock(BlockPos position, CraftBlockState craftBlockState, int i, int j) {
        if (this.structureTransformer != null) {
            craftBlockState = this.structureTransformer.transformCraftState(craftBlockState);
        }

        BlockState iblockdata = craftBlockState.getHandle();
        boolean result = super.setBlock(position, iblockdata, i, j);
        FluidState fluid = this.getFluidState(position);

        if (!fluid.isEmpty()) {
            this.scheduleTick(position, fluid.getType(), 0);
        }

        if (StructurePiece.SHAPE_CHECK_BLOCKS.contains(iblockdata.getBlock())) {
            this.getChunk(position).markPosForPostprocessing(position);
        }

        BlockEntity tileEntity = this.getBlockEntity(position);
        CraftBlockEntityState craftEntityState;

        if (tileEntity != null && craftBlockState instanceof CraftBlockEntityState && (craftEntityState = (CraftBlockEntityState) craftBlockState) == (CraftBlockEntityState) craftBlockState) {
            tileEntity.load(craftEntityState.getSnapshotNBT());
        }

        return result;
    }

    public boolean setCraftBlock(BlockPos position, CraftBlockState craftBlockState, int i) {
        return this.setCraftBlock(position, craftBlockState, i, 512);
    }

    public boolean setBlock(BlockPos position, BlockState iblockdata, int i, int j) {
        return this.structureTransformer != null && this.structureTransformer.canTransformBlocks() ? this.setCraftBlock(position, (CraftBlockState) CraftBlockStates.getBlockState(position, iblockdata, (CompoundTag) null), i, j) : super.setBlock(position, iblockdata, i, j);
    }

    public boolean setBlock(BlockPos position, BlockState iblockdata, int i) {
        return this.setBlock(position, iblockdata, i, 512);
    }
}
