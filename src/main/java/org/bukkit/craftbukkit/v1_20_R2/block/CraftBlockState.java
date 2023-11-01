package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import java.lang.ref.WeakReference;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CraftBlockState implements BlockState {

    protected final CraftWorld world;
    private final BlockPos position;
    protected net.minecraft.world.level.block.state.BlockState data;
    protected int flag;
    private WeakReference weakWorld;

    protected CraftBlockState(Block block) {
        this(block.getWorld(), ((CraftBlock) block).getPosition(), ((CraftBlock) block).getNMS());
        this.flag = 3;
        this.setWorldHandle(((CraftBlock) block).getHandle());
    }

    protected CraftBlockState(Block block, int flag) {
        this(block);
        this.flag = flag;
    }

    protected CraftBlockState(@Nullable World world, BlockPos blockPosition, net.minecraft.world.level.block.state.BlockState blockData) {
        this.world = (CraftWorld) world;
        this.position = blockPosition;
        this.data = blockData;
    }

    protected CraftBlockState(CraftBlockState state) {
        this.world = null;
        this.position = state.getPosition().immutable();
        this.data = state.data;
        this.flag = state.flag;
        this.setWorldHandle(state.getWorldHandle());
    }

    public void setWorldHandle(LevelAccessor generatorAccess) {
        if (generatorAccess instanceof Level) {
            this.weakWorld = null;
        } else {
            this.weakWorld = new WeakReference(generatorAccess);
        }

    }

    public LevelAccessor getWorldHandle() {
        if (this.weakWorld == null) {
            return this.isPlaced() ? this.world.getHandle() : null;
        } else {
            LevelAccessor access = (LevelAccessor) this.weakWorld.get();

            if (access == null) {
                this.weakWorld = null;
                return this.isPlaced() ? this.world.getHandle() : null;
            } else {
                return access;
            }
        }
    }

    protected final boolean isWorldGeneration() {
        LevelAccessor generatorAccess = this.getWorldHandle();

        return generatorAccess != null && !(generatorAccess instanceof Level);
    }

    protected final void ensureNoWorldGeneration() {
        Preconditions.checkState(!this.isWorldGeneration(), "This operation is not supported during world generation!");
    }

    public World getWorld() {
        this.requirePlaced();
        return this.world;
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    public int getZ() {
        return this.position.getZ();
    }

    public Chunk getChunk() {
        this.requirePlaced();
        return this.world.getChunkAt(this.getX() >> 4, this.getZ() >> 4);
    }

    public void setData(net.minecraft.world.level.block.state.BlockState data) {
        this.data = data;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public net.minecraft.world.level.block.state.BlockState getHandle() {
        return this.data;
    }

    public BlockData getBlockData() {
        return CraftBlockData.fromData(this.data);
    }

    public void setBlockData(BlockData data) {
        Preconditions.checkArgument(data != null, "BlockData cannot be null");
        this.data = ((CraftBlockData) data).getState();
    }

    public void setData(MaterialData data) {
        Material mat = CraftMagicNumbers.getMaterial(this.data).getItemType();

        if (mat != null && mat.getData() != null) {
            Preconditions.checkArgument(data.getClass() == mat.getData() || data.getClass() == MaterialData.class, "Provided data is not of type %s, found %s", mat.getData().getName(), data.getClass().getName());
            this.data = CraftMagicNumbers.getBlock(data);
        } else {
            this.data = CraftMagicNumbers.getBlock(data);
        }

    }

    public MaterialData getData() {
        return CraftMagicNumbers.getMaterial(this.data);
    }

    public void setType(Material type) {
        Preconditions.checkArgument(type != null, "Material cannot be null");
        Preconditions.checkArgument(type.isBlock(), "Material must be a block!");
        if (this.getType() != type) {
            this.data = CraftMagicNumbers.getBlock(type).defaultBlockState();
        }

    }

    public Material getType() {
        return CraftMagicNumbers.getMaterial(this.data.getBlock());
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return this.flag;
    }

    public byte getLightLevel() {
        return this.getBlock().getLightLevel();
    }

    public CraftBlock getBlock() {
        this.requirePlaced();
        return CraftBlock.at(this.getWorldHandle(), this.position);
    }

    public boolean update() {
        return this.update(false);
    }

    public boolean update(boolean force) {
        return this.update(force, true);
    }

    public boolean update(boolean force, boolean applyPhysics) {
        if (!this.isPlaced()) {
            return true;
        } else {
            LevelAccessor access = this.getWorldHandle();
            CraftBlock block = this.getBlock();

            if (block.getType() != this.getType() && !force) {
                return false;
            } else {
                net.minecraft.world.level.block.state.BlockState newBlock = this.data;

                block.setTypeAndData(newBlock, applyPhysics);
                if (access instanceof Level) {
                    this.world.getHandle().sendBlockUpdated(this.position, block.getNMS(), newBlock, 3);
                }

                return true;
            }
        }
    }

    public byte getRawData() {
        return CraftMagicNumbers.toLegacyData(this.data);
    }

    public Location getLocation() {
        return CraftLocation.toBukkit(this.position, (World) this.world);
    }

    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(this.world);
            loc.setX((double) this.getX());
            loc.setY((double) this.getY());
            loc.setZ((double) this.getZ());
            loc.setYaw(0.0F);
            loc.setPitch(0.0F);
        }

        return loc;
    }

    public void setRawData(byte data) {
        this.data = CraftMagicNumbers.getBlock(this.getType(), data);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            CraftBlockState other = (CraftBlockState) obj;

            return this.world != other.world && (this.world == null || !this.world.equals(other.world)) ? false : (this.position != other.position && (this.position == null || !this.position.equals(other.position)) ? false : this.data == other.data || this.data != null && this.data.equals(other.data));
        }
    }

    public int hashCode() {
        byte hash = 7;
        int hash = 73 * hash + (this.world != null ? this.world.hashCode() : 0);

        hash = 73 * hash + (this.position != null ? this.position.hashCode() : 0);
        hash = 73 * hash + (this.data != null ? this.data.hashCode() : 0);
        return hash;
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        this.requirePlaced();
        this.world.getBlockMetadata().setMetadata((Block) this.getBlock(), metadataKey, newMetadataValue);
    }

    public List getMetadata(String metadataKey) {
        this.requirePlaced();
        return this.world.getBlockMetadata().getMetadata((Block) this.getBlock(), metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        this.requirePlaced();
        return this.world.getBlockMetadata().hasMetadata((Block) this.getBlock(), metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        this.requirePlaced();
        this.world.getBlockMetadata().removeMetadata((Block) this.getBlock(), metadataKey, owningPlugin);
    }

    public boolean isPlaced() {
        return this.world != null;
    }

    protected void requirePlaced() {
        Preconditions.checkState(this.isPlaced(), "The blockState must be placed to call this method");
    }

    public CraftBlockState copy() {
        return new CraftBlockState(this);
    }
}
