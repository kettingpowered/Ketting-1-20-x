package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CraftBlockEntityState extends CraftBlockState implements TileState {

    private final BlockEntity tileEntity;
    private final BlockEntity snapshot;

    public CraftBlockEntityState(World world, BlockEntity tileEntity) {
        super(world, tileEntity.getBlockPos(), tileEntity.getBlockState());
        this.tileEntity = tileEntity;
        this.snapshot = this.createSnapshot(tileEntity);
        this.load(this.snapshot);
    }

    protected CraftBlockEntityState(CraftBlockEntityState state) {
        super((CraftBlockState) state);
        this.tileEntity = this.createSnapshot(state.snapshot);
        this.snapshot = this.tileEntity;
        this.load(this.snapshot);
    }

    public void refreshSnapshot() {
        this.load(this.tileEntity);
    }

    private BlockEntity createSnapshot(BlockEntity tileEntity) {
        if (tileEntity == null) {
            return null;
        } else {
            CompoundTag nbtTagCompound = tileEntity.saveWithFullMetadata();
            BlockEntity snapshot = BlockEntity.loadStatic(this.getPosition(), this.getHandle(), nbtTagCompound);

            return snapshot;
        }
    }

    public void loadData(CompoundTag nbtTagCompound) {
        this.snapshot.load(nbtTagCompound);
        this.load(this.snapshot);
    }

    private void copyData(BlockEntity from, BlockEntity to) {
        CompoundTag nbtTagCompound = from.saveWithFullMetadata();

        to.load(nbtTagCompound);
    }

    protected BlockEntity getTileEntity() {
        return this.tileEntity;
    }

    protected BlockEntity getSnapshot() {
        return this.snapshot;
    }

    protected BlockEntity getTileEntityFromWorld() {
        this.requirePlaced();
        return this.getWorldHandle().getBlockEntity(this.getPosition());
    }

    public CompoundTag getSnapshotNBT() {
        this.applyTo(this.snapshot);
        return this.snapshot.saveWithFullMetadata();
    }

    protected void load(BlockEntity tileEntity) {
        if (tileEntity != null && tileEntity != this.snapshot) {
            this.copyData(tileEntity, this.snapshot);
        }

    }

    protected void applyTo(BlockEntity tileEntity) {
        if (tileEntity != null && tileEntity != this.snapshot) {
            this.copyData(this.snapshot, tileEntity);
        }

    }

    protected boolean isApplicable(BlockEntity tileEntity) {
        return tileEntity != null && this.tileEntity.getClass() == tileEntity.getClass();
    }

    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && this.isPlaced()) {
            BlockEntity tile = this.getTileEntityFromWorld();

            if (this.isApplicable(tile)) {
                this.applyTo(tile);
                tile.setChanged();
            }
        }

        return result;
    }

    public PersistentDataContainer getPersistentDataContainer() {
        return this.getSnapshot().persistentDataContainer;
    }

    @Nullable
    public Packet getUpdatePacket(@NotNull Location location) {
        BlockEntity vanillaTileEntitiy = BlockEntity.loadStatic(CraftLocation.toBlockPosition(location), this.getHandle(), this.getSnapshotNBT());

        return ClientboundBlockEntityDataPacket.create(vanillaTileEntitiy);
    }

    public CraftBlockEntityState copy() {
        return new CraftBlockEntityState(this);
    }
}
