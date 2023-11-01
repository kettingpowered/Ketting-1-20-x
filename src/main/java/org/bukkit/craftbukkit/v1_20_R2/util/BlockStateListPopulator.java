package org.bukkit.craftbukkit.v1_20_R2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlockState;

public class BlockStateListPopulator extends DummyGeneratorAccess {

    private final LevelAccessor world;
    private final Map dataMap;
    private final Map entityMap;
    private final LinkedHashMap list;

    public BlockStateListPopulator(LevelAccessor world) {
        this(world, new LinkedHashMap());
    }

    private BlockStateListPopulator(LevelAccessor world, LinkedHashMap list) {
        this.dataMap = new HashMap();
        this.entityMap = new HashMap();
        this.world = world;
        this.list = list;
    }

    public BlockState getBlockState(BlockPos bp) {
        BlockState blockData = (BlockState) this.dataMap.get(bp);

        return blockData != null ? blockData : this.world.getBlockState(bp);
    }

    public FluidState getFluidState(BlockPos bp) {
        BlockState blockData = (BlockState) this.dataMap.get(bp);

        return blockData != null ? blockData.getFluidState() : this.world.getFluidState(bp);
    }

    public BlockEntity getBlockEntity(BlockPos blockposition) {
        return this.entityMap.containsKey(blockposition) ? (BlockEntity) this.entityMap.get(blockposition) : this.world.getBlockEntity(blockposition);
    }

    public boolean setBlock(BlockPos position, BlockState data, int flag) {
        position = position.immutable();
        this.list.remove(position);
        this.dataMap.put(position, data);
        if (data.hasBlockEntity()) {
            this.entityMap.put(position, ((EntityBlock) data.getBlock()).newBlockEntity(position, data));
        } else {
            this.entityMap.put(position, (Object) null);
        }

        CraftBlockState state = (CraftBlockState) CraftBlock.at(this, position).getState();

        state.setFlag(flag);
        state.setWorldHandle(this.world);
        this.list.put(position, state);
        return true;
    }

    public ServerLevel getMinecraftWorld() {
        return this.world.getMinecraftWorld();
    }

    public void refreshTiles() {
        Iterator iterator = this.list.values().iterator();

        while (iterator.hasNext()) {
            CraftBlockState state = (CraftBlockState) iterator.next();

            if (state instanceof CraftBlockEntityState) {
                ((CraftBlockEntityState) state).refreshSnapshot();
            }
        }

    }

    public void updateList() {
        Iterator iterator = this.list.values().iterator();

        while (iterator.hasNext()) {
            org.bukkit.block.BlockState state = (org.bukkit.block.BlockState) iterator.next();

            state.update(true);
        }

    }

    public Set getBlocks() {
        return this.list.keySet();
    }

    public List getList() {
        return new ArrayList(this.list.values());
    }

    public LevelAccessor getWorld() {
        return this.world;
    }

    public int getMinBuildHeight() {
        return this.getWorld().getMinBuildHeight();
    }

    public int getHeight() {
        return this.getWorld().getHeight();
    }

    public boolean isStateAtPosition(BlockPos blockposition, Predicate predicate) {
        return predicate.test(this.getBlockState(blockposition));
    }

    public boolean isFluidAtPosition(BlockPos bp, Predicate prdct) {
        return this.world.isFluidAtPosition(bp, prdct);
    }

    public DimensionType dimensionType() {
        return this.world.dimensionType();
    }

    public RegistryAccess registryAccess() {
        return this.world.registryAccess();
    }

    public LevelData getLevelData() {
        return this.world.getLevelData();
    }

    public long nextSubTickCount() {
        return this.world.nextSubTickCount();
    }
}
