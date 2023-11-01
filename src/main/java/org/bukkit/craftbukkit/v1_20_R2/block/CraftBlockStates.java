package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;

public final class CraftBlockStates {

    private static final Map FACTORIES = new HashMap();
    private static final CraftBlockStates.BlockStateFactory DEFAULT_FACTORY = new CraftBlockStates.BlockStateFactory(CraftBlockState.class) {
        public CraftBlockState createBlockState(World world, BlockPos blockPosition, BlockState blockData, BlockEntity tileEntity) {
            if (tileEntity != null) {
                return new CraftBlockEntityState(world, tileEntity);
            } else {
                Preconditions.checkState(tileEntity == null, "Unexpected BlockState for %s", CraftMagicNumbers.getMaterial(blockData.getBlock()));
                return new CraftBlockState(world, blockPosition, blockData);
            }
        }
    };

    static {
        register(Arrays.asList(Material.ACACIA_SIGN, Material.ACACIA_WALL_SIGN, Material.BAMBOO_SIGN, Material.BAMBOO_WALL_SIGN, Material.BIRCH_SIGN, Material.BIRCH_WALL_SIGN, Material.CHERRY_SIGN, Material.CHERRY_WALL_SIGN, Material.CRIMSON_SIGN, Material.CRIMSON_WALL_SIGN, Material.DARK_OAK_SIGN, Material.DARK_OAK_WALL_SIGN, Material.JUNGLE_SIGN, Material.JUNGLE_WALL_SIGN, Material.MANGROVE_SIGN, Material.MANGROVE_WALL_SIGN, Material.OAK_SIGN, Material.OAK_WALL_SIGN, Material.SPRUCE_SIGN, Material.SPRUCE_WALL_SIGN, Material.WARPED_SIGN, Material.WARPED_WALL_SIGN), CraftSign.class, CraftSign::new, SignBlockEntity::new);
        register(Arrays.asList(Material.ACACIA_HANGING_SIGN, Material.ACACIA_WALL_HANGING_SIGN, Material.BAMBOO_HANGING_SIGN, Material.BAMBOO_WALL_HANGING_SIGN, Material.BIRCH_HANGING_SIGN, Material.BIRCH_WALL_HANGING_SIGN, Material.CHERRY_HANGING_SIGN, Material.CHERRY_WALL_HANGING_SIGN, Material.CRIMSON_HANGING_SIGN, Material.CRIMSON_WALL_HANGING_SIGN, Material.DARK_OAK_HANGING_SIGN, Material.DARK_OAK_WALL_HANGING_SIGN, Material.JUNGLE_HANGING_SIGN, Material.JUNGLE_WALL_HANGING_SIGN, Material.MANGROVE_HANGING_SIGN, Material.MANGROVE_WALL_HANGING_SIGN, Material.OAK_HANGING_SIGN, Material.OAK_WALL_HANGING_SIGN, Material.SPRUCE_HANGING_SIGN, Material.SPRUCE_WALL_HANGING_SIGN, Material.WARPED_HANGING_SIGN, Material.WARPED_WALL_HANGING_SIGN), CraftHangingSign.class, CraftHangingSign::new, HangingSignBlockEntity::new);
        register(Arrays.asList(Material.CREEPER_HEAD, Material.CREEPER_WALL_HEAD, Material.DRAGON_HEAD, Material.DRAGON_WALL_HEAD, Material.PIGLIN_HEAD, Material.PIGLIN_WALL_HEAD, Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD, Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL, Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL, Material.ZOMBIE_HEAD, Material.ZOMBIE_WALL_HEAD), CraftSkull.class, CraftSkull::new, SkullBlockEntity::new);
        register(Arrays.asList(Material.COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK), CraftCommandBlock.class, CraftCommandBlock::new, CommandBlockEntity::new);
        register(Arrays.asList(Material.BLACK_BANNER, Material.BLACK_WALL_BANNER, Material.BLUE_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_BANNER, Material.BROWN_WALL_BANNER, Material.CYAN_BANNER, Material.CYAN_WALL_BANNER, Material.GRAY_BANNER, Material.GRAY_WALL_BANNER, Material.GREEN_BANNER, Material.GREEN_WALL_BANNER, Material.LIGHT_BLUE_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.LIGHT_GRAY_BANNER, Material.LIGHT_GRAY_WALL_BANNER, Material.LIME_BANNER, Material.LIME_WALL_BANNER, Material.MAGENTA_BANNER, Material.MAGENTA_WALL_BANNER, Material.ORANGE_BANNER, Material.ORANGE_WALL_BANNER, Material.PINK_BANNER, Material.PINK_WALL_BANNER, Material.PURPLE_BANNER, Material.PURPLE_WALL_BANNER, Material.RED_BANNER, Material.RED_WALL_BANNER, Material.WHITE_BANNER, Material.WHITE_WALL_BANNER, Material.YELLOW_BANNER, Material.YELLOW_WALL_BANNER), CraftBanner.class, CraftBanner::new, BannerBlockEntity::new);
        register(Arrays.asList(Material.SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.LIME_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.BLACK_SHULKER_BOX), CraftShulkerBox.class, CraftShulkerBox::new, ShulkerBoxBlockEntity::new);
        register(Arrays.asList(Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED, Material.CYAN_BED, Material.GRAY_BED, Material.GREEN_BED, Material.LIGHT_BLUE_BED, Material.LIGHT_GRAY_BED, Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED, Material.PINK_BED, Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED, Material.YELLOW_BED), CraftBed.class, CraftBed::new, BedBlockEntity::new);
        register(Arrays.asList(Material.BEEHIVE, Material.BEE_NEST), CraftBeehive.class, CraftBeehive::new, BeehiveBlockEntity::new);
        register(Arrays.asList(Material.CAMPFIRE, Material.SOUL_CAMPFIRE), CraftCampfire.class, CraftCampfire::new, CampfireBlockEntity::new);
        register(Material.BARREL, CraftBarrel.class, CraftBarrel::new, BarrelBlockEntity::new);
        register(Material.BEACON, CraftBeacon.class, CraftBeacon::new, BeaconBlockEntity::new);
        register(Material.BELL, CraftBell.class, CraftBell::new, BellBlockEntity::new);
        register(Material.BLAST_FURNACE, CraftBlastFurnace.class, CraftBlastFurnace::new, BlastFurnaceBlockEntity::new);
        register(Material.BREWING_STAND, CraftBrewingStand.class, CraftBrewingStand::new, BrewingStandBlockEntity::new);
        register(Material.CHEST, CraftChest.class, CraftChest::new, ChestBlockEntity::new);
        register(Material.CHISELED_BOOKSHELF, CraftChiseledBookshelf.class, CraftChiseledBookshelf::new, ChiseledBookShelfBlockEntity::new);
        register(Material.COMPARATOR, CraftComparator.class, CraftComparator::new, ComparatorBlockEntity::new);
        register(Material.CONDUIT, CraftConduit.class, CraftConduit::new, ConduitBlockEntity::new);
        register(Material.DAYLIGHT_DETECTOR, CraftDaylightDetector.class, CraftDaylightDetector::new, DaylightDetectorBlockEntity::new);
        register(Material.DECORATED_POT, CraftDecoratedPot.class, CraftDecoratedPot::new, DecoratedPotBlockEntity::new);
        register(Material.DISPENSER, CraftDispenser.class, CraftDispenser::new, DispenserBlockEntity::new);
        register(Material.DROPPER, CraftDropper.class, CraftDropper::new, DropperBlockEntity::new);
        register(Material.ENCHANTING_TABLE, CraftEnchantingTable.class, CraftEnchantingTable::new, EnchantmentTableBlockEntity::new);
        register(Material.ENDER_CHEST, CraftEnderChest.class, CraftEnderChest::new, EnderChestBlockEntity::new);
        register(Material.END_GATEWAY, CraftEndGateway.class, CraftEndGateway::new, TheEndGatewayBlockEntity::new);
        register(Material.END_PORTAL, CraftEndPortal.class, CraftEndPortal::new, TheEndPortalBlockEntity::new);
        register(Material.FURNACE, CraftFurnaceFurnace.class, CraftFurnaceFurnace::new, FurnaceBlockEntity::new);
        register(Material.HOPPER, CraftHopper.class, CraftHopper::new, HopperBlockEntity::new);
        register(Material.JIGSAW, CraftJigsaw.class, CraftJigsaw::new, JigsawBlockEntity::new);
        register(Material.JUKEBOX, CraftJukebox.class, CraftJukebox::new, JukeboxBlockEntity::new);
        register(Material.LECTERN, CraftLectern.class, CraftLectern::new, LecternBlockEntity::new);
        register(Material.MOVING_PISTON, CraftMovingPiston.class, CraftMovingPiston::new, PistonMovingBlockEntity::new);
        register(Material.SCULK_CATALYST, CraftSculkCatalyst.class, CraftSculkCatalyst::new, SculkCatalystBlockEntity::new);
        register(Material.CALIBRATED_SCULK_SENSOR, CraftCalibratedSculkSensor.class, CraftCalibratedSculkSensor::new, CalibratedSculkSensorBlockEntity::new);
        register(Material.SCULK_SENSOR, CraftSculkSensor.class, CraftSculkSensor::new, SculkSensorBlockEntity::new);
        register(Material.SCULK_SHRIEKER, CraftSculkShrieker.class, CraftSculkShrieker::new, SculkShriekerBlockEntity::new);
        register(Material.SMOKER, CraftSmoker.class, CraftSmoker::new, SmokerBlockEntity::new);
        register(Material.SPAWNER, CraftCreatureSpawner.class, CraftCreatureSpawner::new, SpawnerBlockEntity::new);
        register(Material.STRUCTURE_BLOCK, CraftStructureBlock.class, CraftStructureBlock::new, StructureBlockEntity::new);
        register(Material.SUSPICIOUS_SAND, CraftSuspiciousSand.class, CraftSuspiciousSand::new, BrushableBlockEntity::new);
        register(Material.SUSPICIOUS_GRAVEL, CraftBrushableBlock.class, CraftBrushableBlock::new, BrushableBlockEntity::new);
        register(Material.TRAPPED_CHEST, CraftChest.class, CraftChest::new, TrappedChestBlockEntity::new);
    }

    private static void register(Material blockType, CraftBlockStates.BlockStateFactory factory) {
        CraftBlockStates.FACTORIES.put(blockType, factory);
    }

    private static void register(Material blockType, Class blockStateType, BiFunction blockStateConstructor, BiFunction tileEntityConstructor) {
        register(Collections.singletonList(blockType), blockStateType, blockStateConstructor, tileEntityConstructor);
    }

    private static void register(List blockTypes, Class blockStateType, BiFunction blockStateConstructor, BiFunction tileEntityConstructor) {
        CraftBlockStates.BlockEntityStateFactory factory = new CraftBlockStates.BlockEntityStateFactory(blockStateType, blockStateConstructor, tileEntityConstructor);
        Iterator iterator = blockTypes.iterator();

        while (iterator.hasNext()) {
            Material blockType = (Material) iterator.next();

            register(blockType, factory);
        }

    }

    private static CraftBlockStates.BlockStateFactory getFactory(Material material) {
        return (CraftBlockStates.BlockStateFactory) CraftBlockStates.FACTORIES.getOrDefault(material, CraftBlockStates.DEFAULT_FACTORY);
    }

    public static Class getBlockStateType(Material material) {
        Preconditions.checkNotNull(material, "material is null");
        return getFactory(material).blockStateType;
    }

    public static BlockEntity createNewTileEntity(Material material) {
        CraftBlockStates.BlockStateFactory factory = getFactory(material);

        return factory instanceof CraftBlockStates.BlockEntityStateFactory ? ((CraftBlockStates.BlockEntityStateFactory) factory).createTileEntity(BlockPos.ZERO, CraftMagicNumbers.getBlock(material).defaultBlockState()) : null;
    }

    public static org.bukkit.block.BlockState getBlockState(Block block) {
        Preconditions.checkNotNull(block, "block is null");
        CraftBlock craftBlock = (CraftBlock) block;
        CraftWorld world = (CraftWorld) block.getWorld();
        BlockPos blockPosition = craftBlock.getPosition();
        BlockState blockData = craftBlock.getNMS();
        BlockEntity tileEntity = craftBlock.getHandle().getBlockEntity(blockPosition);
        CraftBlockState blockState = getBlockState(world, blockPosition, blockData, tileEntity);

        blockState.setWorldHandle(craftBlock.getHandle());
        return blockState;
    }

    public static org.bukkit.block.BlockState getBlockState(Material material, @Nullable CompoundTag blockEntityTag) {
        return getBlockState(BlockPos.ZERO, material, blockEntityTag);
    }

    public static org.bukkit.block.BlockState getBlockState(BlockPos blockPosition, Material material, @Nullable CompoundTag blockEntityTag) {
        Preconditions.checkNotNull(material, "material is null");
        BlockState blockData = CraftMagicNumbers.getBlock(material).defaultBlockState();

        return getBlockState(blockPosition, blockData, blockEntityTag);
    }

    public static org.bukkit.block.BlockState getBlockState(BlockState blockData, @Nullable CompoundTag blockEntityTag) {
        return getBlockState(BlockPos.ZERO, blockData, blockEntityTag);
    }

    public static org.bukkit.block.BlockState getBlockState(BlockPos blockPosition, BlockState blockData, @Nullable CompoundTag blockEntityTag) {
        Preconditions.checkNotNull(blockPosition, "blockPosition is null");
        Preconditions.checkNotNull(blockData, "blockData is null");
        BlockEntity tileEntity = blockEntityTag == null ? null : BlockEntity.loadStatic(blockPosition, blockData, blockEntityTag);

        return getBlockState((World) null, blockPosition, blockData, tileEntity);
    }

    private static CraftBlockState getBlockState(World world, BlockPos blockPosition, BlockState blockData, BlockEntity tileEntity) {
        Material material = CraftMagicNumbers.getMaterial(blockData.getBlock());
        CraftBlockStates.BlockStateFactory factory;

        if (world != null && tileEntity == null && isTileEntityOptional(material)) {
            factory = CraftBlockStates.DEFAULT_FACTORY;
        } else {
            factory = getFactory(material);
        }

        return factory.createBlockState(world, blockPosition, blockData, tileEntity);
    }

    public static boolean isTileEntityOptional(Material material) {
        return material == Material.MOVING_PISTON;
    }

    public static CraftBlockState getBlockState(LevelAccessor world, BlockPos pos) {
        return new CraftBlockState(CraftBlock.at(world, pos));
    }

    public static CraftBlockState getBlockState(LevelAccessor world, BlockPos pos, int flag) {
        return new CraftBlockState(CraftBlock.at(world, pos), flag);
    }

    private CraftBlockStates() {}

    private static class BlockEntityStateFactory extends CraftBlockStates.BlockStateFactory {

        private final BiFunction blockStateConstructor;
        private final BiFunction tileEntityConstructor;

        protected BlockEntityStateFactory(Class blockStateType, BiFunction blockStateConstructor, BiFunction tileEntityConstructor) {
            super(blockStateType);
            this.blockStateConstructor = blockStateConstructor;
            this.tileEntityConstructor = tileEntityConstructor;
        }

        public final CraftBlockEntityState createBlockState(World world, BlockPos blockPosition, BlockState blockData, BlockEntity tileEntity) {
            if (world != null) {
                Preconditions.checkState(tileEntity != null, "Tile is null, asynchronous access? %s", CraftBlock.at(((CraftWorld) world).getHandle(), blockPosition));
            } else if (tileEntity == null) {
                tileEntity = this.createTileEntity(blockPosition, blockData);
            }

            return this.createBlockState(world, tileEntity);
        }

        private BlockEntity createTileEntity(BlockPos blockPosition, BlockState blockData) {
            return (BlockEntity) this.tileEntityConstructor.apply(blockPosition, blockData);
        }

        private CraftBlockEntityState createBlockState(World world, BlockEntity tileEntity) {
            return (CraftBlockEntityState) this.blockStateConstructor.apply(world, tileEntity);
        }
    }

    private abstract static class BlockStateFactory {

        public final Class blockStateType;

        public BlockStateFactory(Class blockStateType) {
            this.blockStateType = blockStateType;
        }

        public abstract CraftBlockState createBlockState(World world, BlockPos blockpos, BlockState blockstate, BlockEntity blockentity);
    }
}
