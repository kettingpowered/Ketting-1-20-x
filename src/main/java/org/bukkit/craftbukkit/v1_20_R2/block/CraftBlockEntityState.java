package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CraftBlockEntityState<T extends BlockEntity> extends CraftBlockState implements TileState {

    private final T tileEntity;
    private final T snapshot;

    public CraftBlockEntityState(World world, T tileEntity) {
        super(world, tileEntity.getBlockPos(), tileEntity.getBlockState());

        this.tileEntity = tileEntity;

        // copy tile entity data:
        this.snapshot = this.createSnapshot(tileEntity);
        this.load(snapshot);
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

    private T createSnapshot(BlockEntity tileEntity) {
        if (tileEntity == null) {
            return null;
        }

        CompoundTag nbtTagCompound = tileEntity.saveWithFullMetadata();
        T snapshot = (T) BlockEntity.loadStatic(getPosition(), getHandle(), nbtTagCompound);

        return snapshot;
    }

    public void loadData(CompoundTag nbtTagCompound) {
        this.snapshot.load(nbtTagCompound);
        this.load(this.snapshot);
    }

    private void copyData(T from, T to) {
        CompoundTag nbtTagCompound = from.saveWithFullMetadata();

        to.load(nbtTagCompound);
    }

    protected T getTileEntity() {
        return this.tileEntity;
    }

    protected T getSnapshot() {
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

    protected void load(T tileEntity) {
        if (tileEntity != null && tileEntity != this.snapshot) {
            this.copyData(tileEntity, this.snapshot);
        }

    }

    protected void applyTo(T tileEntity) {
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
            BlockEntity tile = getTileEntityFromWorld();

            if (isApplicable(tile)) {
                applyTo((T) tile);
                tile.setChanged();
            }
        }

        return result;
    }

    public PersistentDataContainer getPersistentDataContainer() {
        return this.getSnapshot().persistentDataContainer;
    }

    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket(@NotNull Location location) {
        T vanillaTileEntitiy = (T) BlockEntity.loadStatic(CraftLocation.toBlockPosition(location), getHandle(), getSnapshotNBT());
        return ClientboundBlockEntityDataPacket.create(vanillaTileEntitiy);
    }

    public CraftBlockEntityState<T> copy() {
        return new CraftBlockEntityState<>(this);
    }
}
