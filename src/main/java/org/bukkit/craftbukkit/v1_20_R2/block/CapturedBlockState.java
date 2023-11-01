package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class CapturedBlockState extends CraftBlockState {

    private final boolean treeBlock;

    public CapturedBlockState(Block block, int flag, boolean treeBlock) {
        super(block, flag);
        this.treeBlock = treeBlock;
    }

    protected CapturedBlockState(CapturedBlockState state) {
        super((CraftBlockState) state);
        this.treeBlock = state.treeBlock;
    }

    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (this.treeBlock && this.getType() == Material.BEE_NEST) {
            ServerLevel generatoraccessseed = this.world.getHandle();
            BlockPos blockposition1 = this.getPosition();
            RandomSource random = generatoraccessseed.getRandom();
            BlockEntity tileentity = generatoraccessseed.getBlockEntity(blockposition1);

            if (tileentity instanceof BeehiveBlockEntity) {
                BeehiveBlockEntity tileentitybeehive = (BeehiveBlockEntity) tileentity;
                int j = 2 + random.nextInt(2);

                for (int k = 0; k < j; ++k) {
                    Bee entitybee = new Bee(EntityType.BEE, generatoraccessseed.getMinecraftWorld());

                    tileentitybeehive.addOccupantWithPresetTicks(entitybee, false, random.nextInt(599));
                }
            }
        }

        return result;
    }

    public CapturedBlockState copy() {
        return new CapturedBlockState(this);
    }

    public static CapturedBlockState getBlockState(Level world, BlockPos pos, int flag) {
        return new CapturedBlockState(world.getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ()), flag, false);
    }

    public static CapturedBlockState getTreeBlockState(Level world, BlockPos pos, int flag) {
        return new CapturedBlockState(world.getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ()), flag, true);
    }
}
