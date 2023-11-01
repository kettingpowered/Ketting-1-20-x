package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftFluidCollisionMode;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftRayTraceResult;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftVoxelShape;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class CraftBlock implements Block {

    private final LevelAccessor world;
    private final BlockPos position;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$core$EnumDirection;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$BlockFace;

    public CraftBlock(LevelAccessor world, BlockPos position) {
        this.world = world;
        this.position = position.immutable();
    }

    public static CraftBlock at(LevelAccessor world, BlockPos position) {
        return new CraftBlock(world, position);
    }

    public BlockState getNMS() {
        return this.world.getBlockState(this.position);
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public LevelAccessor getHandle() {
        return this.world;
    }

    public World getWorld() {
        return this.world.getMinecraftWorld().getWorld();
    }

    public CraftWorld getCraftWorld() {
        return (CraftWorld) this.getWorld();
    }

    public Location getLocation() {
        return CraftLocation.toBukkit(this.position, this.getWorld());
    }

    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(this.getWorld());
            loc.setX((double) this.position.getX());
            loc.setY((double) this.position.getY());
            loc.setZ((double) this.position.getZ());
            loc.setYaw(0.0F);
            loc.setPitch(0.0F);
        }

        return loc;
    }

    public BlockVector getVector() {
        return new BlockVector(this.getX(), this.getY(), this.getZ());
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
        return this.getWorld().getChunkAt(this);
    }

    public void setData(byte data) {
        this.setData(data, 3);
    }

    public void setData(byte data, boolean applyPhysics) {
        if (applyPhysics) {
            this.setData(data, 3);
        } else {
            this.setData(data, 2);
        }

    }

    private void setData(byte data, int flag) {
        this.world.setBlock(this.position, CraftMagicNumbers.getBlock(this.getType(), data), flag);
    }

    public byte getData() {
        BlockState blockData = this.world.getBlockState(this.position);

        return CraftMagicNumbers.toLegacyData(blockData);
    }

    public BlockData getBlockData() {
        return CraftBlockData.fromData(this.getNMS());
    }

    public void setType(Material type) {
        this.setType(type, true);
    }

    public void setType(Material type, boolean applyPhysics) {
        Preconditions.checkArgument(type != null, "Material cannot be null");
        this.setBlockData(type.createBlockData(), applyPhysics);
    }

    public void setBlockData(BlockData data) {
        this.setBlockData(data, true);
    }

    public void setBlockData(BlockData data, boolean applyPhysics) {
        Preconditions.checkArgument(data != null, "BlockData cannot be null");
        this.setTypeAndData(((CraftBlockData) data).getState(), applyPhysics);
    }

    boolean setTypeAndData(BlockState blockData, boolean applyPhysics) {
        return setTypeAndData(this.world, this.position, this.getNMS(), blockData, applyPhysics);
    }

    public static boolean setTypeAndData(LevelAccessor world, BlockPos position, BlockState old, BlockState blockData, boolean applyPhysics) {
        if (old.hasBlockEntity() && blockData.getBlock() != old.getBlock()) {
            if (world instanceof Level) {
                ((Level) world).removeBlockEntity(position);
            } else {
                world.setBlock(position, Blocks.AIR.defaultBlockState(), 0);
            }
        }

        if (applyPhysics) {
            return world.setBlock(position, blockData, 3);
        } else {
            boolean success = world.setBlock(position, blockData, 1042);

            if (success && world instanceof Level) {
                world.getMinecraftWorld().sendBlockUpdated(position, old, blockData, 3);
            }

            return success;
        }
    }

    public Material getType() {
        return CraftMagicNumbers.getMaterial(this.world.getBlockState(this.position).getBlock());
    }

    public byte getLightLevel() {
        return (byte) this.world.getMinecraftWorld().getMaxLocalRawBrightness(this.position);
    }

    public byte getLightFromSky() {
        return (byte) this.world.getBrightness(LightLayer.SKY, this.position);
    }

    public byte getLightFromBlocks() {
        return (byte) this.world.getBrightness(LightLayer.BLOCK, this.position);
    }

    public Block getFace(BlockFace face) {
        return this.getRelative(face, 1);
    }

    public Block getFace(BlockFace face, int distance) {
        return this.getRelative(face, distance);
    }

    public Block getRelative(int modX, int modY, int modZ) {
        return this.getWorld().getBlockAt(this.getX() + modX, this.getY() + modY, this.getZ() + modZ);
    }

    public Block getRelative(BlockFace face) {
        return this.getRelative(face, 1);
    }

    public Block getRelative(BlockFace face, int distance) {
        return this.getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
    }

    public BlockFace getFace(Block block) {
        BlockFace[] values = BlockFace.values();
        BlockFace[] ablockface = values;
        int i = values.length;

        for (int j = 0; j < i; ++j) {
            BlockFace face = ablockface[j];

            if (this.getX() + face.getModX() == block.getX() && this.getY() + face.getModY() == block.getY() && this.getZ() + face.getModZ() == block.getZ()) {
                return face;
            }
        }

        return null;
    }

    public String toString() {
        return "CraftBlock{pos=" + this.position + ",type=" + this.getType() + ",data=" + this.getNMS() + ",fluid=" + this.world.getFluidState(this.position) + '}';
    }

    public static BlockFace notchToBlockFace(Direction notch) {
        if (notch == null) {
            return BlockFace.SELF;
        } else {
            switch ($SWITCH_TABLE$net$minecraft$core$EnumDirection()[notch.ordinal()]) {
                case 1:
                    return BlockFace.DOWN;
                case 2:
                    return BlockFace.UP;
                case 3:
                    return BlockFace.NORTH;
                case 4:
                    return BlockFace.SOUTH;
                case 5:
                    return BlockFace.WEST;
                case 6:
                    return BlockFace.EAST;
                default:
                    return BlockFace.SELF;
            }
        }
    }

    public static Direction blockFaceToNotch(BlockFace face) {
        if (face == null) {
            return null;
        } else {
            switch ($SWITCH_TABLE$org$bukkit$block$BlockFace()[face.ordinal()]) {
                case 1:
                    return Direction.NORTH;
                case 2:
                    return Direction.EAST;
                case 3:
                    return Direction.SOUTH;
                case 4:
                    return Direction.WEST;
                case 5:
                    return Direction.UP;
                case 6:
                    return Direction.DOWN;
                default:
                    return null;
            }
        }
    }

    public org.bukkit.block.BlockState getState() {
        return CraftBlockStates.getBlockState(this);
    }

    public Biome getBiome() {
        return this.getWorld().getBiome(this.getX(), this.getY(), this.getZ());
    }

    public void setBiome(Biome bio) {
        this.getWorld().setBiome(this.getX(), this.getY(), this.getZ(), bio);
    }

    public double getTemperature() {
        return (double) ((net.minecraft.world.level.biome.Biome) this.world.getBiome(this.position).value()).getTemperature(this.position);
    }

    public double getHumidity() {
        return this.getWorld().getHumidity(this.getX(), this.getY(), this.getZ());
    }

    public boolean isBlockPowered() {
        return this.world.getMinecraftWorld().getDirectSignalTo(this.position) > 0;
    }

    public boolean isBlockIndirectlyPowered() {
        return this.world.getMinecraftWorld().hasNeighborSignal(this.position);
    }

    public boolean equals(Object o) {
        CraftBlock other;

        return o == this ? true : (o instanceof CraftBlock && (other = (CraftBlock) o) == (CraftBlock) o ? this.position.equals(other.position) && this.getWorld().equals(other.getWorld()) : false);
    }

    public int hashCode() {
        return this.position.hashCode() ^ this.getWorld().hashCode();
    }

    public boolean isBlockFacePowered(BlockFace face) {
        return this.world.getMinecraftWorld().hasSignal(this.position, blockFaceToNotch(face));
    }

    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        int power = this.world.getMinecraftWorld().getSignal(this.position, blockFaceToNotch(face));
        Block relative = this.getRelative(face);

        return relative.getType() == Material.REDSTONE_WIRE ? Math.max(power, relative.getData()) > 0 : power > 0;
    }

    public int getBlockPower(BlockFace face) {
        int power = 0;
        ServerLevel world = this.world.getMinecraftWorld();
        int x = this.getX();
        int y = this.getY();
        int z = this.getZ();

        if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.hasSignal(new BlockPos(x, y - 1, z), Direction.DOWN)) {
            power = getPower(power, world.getBlockState(new BlockPos(x, y - 1, z)));
        }

        if ((face == BlockFace.UP || face == BlockFace.SELF) && world.hasSignal(new BlockPos(x, y + 1, z), Direction.UP)) {
            power = getPower(power, world.getBlockState(new BlockPos(x, y + 1, z)));
        }

        if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.hasSignal(new BlockPos(x + 1, y, z), Direction.EAST)) {
            power = getPower(power, world.getBlockState(new BlockPos(x + 1, y, z)));
        }

        if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.hasSignal(new BlockPos(x - 1, y, z), Direction.WEST)) {
            power = getPower(power, world.getBlockState(new BlockPos(x - 1, y, z)));
        }

        if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.hasSignal(new BlockPos(x, y, z - 1), Direction.NORTH)) {
            power = getPower(power, world.getBlockState(new BlockPos(x, y, z - 1)));
        }

        if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.hasSignal(new BlockPos(x, y, z + 1), Direction.SOUTH)) {
            power = getPower(power, world.getBlockState(new BlockPos(x, y, z + 1)));
        }

        int i;

        if (power > 0) {
            i = power;
        } else {
            label87:
            {
                if (face == BlockFace.SELF) {
                    if (!this.isBlockIndirectlyPowered()) {
                        break label87;
                    }
                } else if (!this.isBlockFaceIndirectlyPowered(face)) {
                    break label87;
                }

                i = 15;
                return i;
            }

            i = 0;
        }

        return i;
    }

    private static int getPower(int i, BlockState iblockdata) {
        if (!iblockdata.is(Blocks.REDSTONE_WIRE)) {
            return i;
        } else {
            int j = (Integer) iblockdata.getValue(RedStoneWireBlock.POWER);

            return j > i ? j : i;
        }
    }

    public int getBlockPower() {
        return this.getBlockPower(BlockFace.SELF);
    }

    public boolean isEmpty() {
        return this.getNMS().isAir();
    }

    public boolean isLiquid() {
        return this.getNMS().liquid();
    }

    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(this.getNMS().getPistonPushReaction().ordinal());
    }

    public boolean breakNaturally() {
        return this.breakNaturally((ItemStack) null);
    }

    public boolean breakNaturally(ItemStack item) {
        BlockState iblockdata = this.getNMS();
        net.minecraft.world.level.block.Block block = iblockdata.getBlock();
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        boolean result = false;

        if (block != Blocks.AIR && (item == null || !iblockdata.requiresCorrectToolForDrops() || nmsItem.isCorrectToolForDrops(iblockdata))) {
            net.minecraft.world.level.block.Block.dropResources(iblockdata, this.world.getMinecraftWorld(), this.position, this.world.getBlockEntity(this.position), (Entity) null, nmsItem);
            result = true;
        }

        return this.world.setBlock(this.position, Blocks.AIR.defaultBlockState(), 3) && result;
    }

    public boolean applyBoneMeal(BlockFace face) {
        Direction direction = blockFaceToNotch(face);
        BlockFertilizeEvent event = null;
        ServerLevel world = this.getCraftWorld().getHandle();
        UseOnContext context = new UseOnContext(world, (Player) null, InteractionHand.MAIN_HAND, Items.BONE_MEAL.getDefaultInstance(), new BlockHitResult(Vec3.ZERO, direction, this.getPosition(), false));

        world.captureTreeGeneration = true;
        InteractionResult result = BoneMealItem.applyBonemeal(context);

        world.captureTreeGeneration = false;
        if (world.capturedBlockStates.size() > 0) {
            TreeType treeType = SaplingBlock.treeType;

            SaplingBlock.treeType = null;
            ArrayList blocks = new ArrayList(world.capturedBlockStates.values());

            world.capturedBlockStates.clear();
            StructureGrowEvent structureEvent = null;

            if (treeType != null) {
                structureEvent = new StructureGrowEvent(this.getLocation(), treeType, true, (org.bukkit.entity.Player) null, blocks);
                Bukkit.getPluginManager().callEvent(structureEvent);
            }

            event = new BlockFertilizeEvent(at(world, this.getPosition()), (org.bukkit.entity.Player) null, blocks);
            event.setCancelled(structureEvent != null && structureEvent.isCancelled());
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                Iterator iterator = blocks.iterator();

                while (iterator.hasNext()) {
                    org.bukkit.block.BlockState blockstate = (org.bukkit.block.BlockState) iterator.next();

                    blockstate.update(true);
                }
            }
        }

        return result == InteractionResult.SUCCESS && (event == null || !event.isCancelled());
    }

    public Collection getDrops() {
        return this.getDrops((ItemStack) null);
    }

    public Collection getDrops(ItemStack item) {
        return this.getDrops(item, (org.bukkit.entity.Entity) null);
    }

    public Collection getDrops(ItemStack item, org.bukkit.entity.Entity entity) {
        BlockState iblockdata = this.getNMS();
        net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(item);

        return (Collection) (item != null && !CraftBlockData.isPreferredTool(iblockdata, nms) ? Collections.emptyList() : (Collection) net.minecraft.world.level.block.Block.getDrops(iblockdata, this.world.getMinecraftWorld(), this.position, this.world.getBlockEntity(this.position), entity == null ? null : ((CraftEntity) entity).getHandle(), nms).stream().map(CraftItemStack::asBukkitCopy).collect(Collectors.toList()));
    }

    public boolean isPreferredTool(ItemStack item) {
        BlockState iblockdata = this.getNMS();
        net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(item);

        return CraftBlockData.isPreferredTool(iblockdata, nms);
    }

    public float getBreakSpeed(org.bukkit.entity.Player player) {
        Preconditions.checkArgument(player != null, "player cannot be null");
        return this.getNMS().getDestroyProgress(((CraftPlayer) player).getHandle(), this.world, this.position);
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        this.getCraftWorld().getBlockMetadata().setMetadata((Block) this, metadataKey, newMetadataValue);
    }

    public List getMetadata(String metadataKey) {
        return this.getCraftWorld().getBlockMetadata().getMetadata((Block) this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return this.getCraftWorld().getBlockMetadata().hasMetadata((Block) this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        this.getCraftWorld().getBlockMetadata().removeMetadata((Block) this, metadataKey, owningPlugin);
    }

    public boolean isPassable() {
        return this.getNMS().getCollisionShape(this.world, this.position).isEmpty();
    }

    public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        Preconditions.checkArgument(start != null, "Location start cannot be null");
        Preconditions.checkArgument(this.getWorld().equals(start.getWorld()), "Location start cannot be a different world");
        start.checkFinite();
        Preconditions.checkArgument(direction != null, "Vector direction cannot be null");
        direction.checkFinite();
        Preconditions.checkArgument(direction.lengthSquared() > 0.0D, "Direction's magnitude (%s) must be greater than 0", direction.lengthSquared());
        Preconditions.checkArgument(fluidCollisionMode != null, "FluidCollisionMode cannot be null");
        if (maxDistance < 0.0D) {
            return null;
        } else {
            Vector dir = direction.clone().normalize().multiply(maxDistance);
            Vec3 startPos = CraftLocation.toVec3D(start);
            Vec3 endPos = startPos.add(dir.getX(), dir.getY(), dir.getZ());
            BlockHitResult nmsHitResult = this.world.clip(new ClipContext(startPos, endPos, ClipContext.Block.OUTLINE, CraftFluidCollisionMode.toNMS(fluidCollisionMode), (Entity) null), this.position);

            return CraftRayTraceResult.fromNMS(this.getWorld(), nmsHitResult);
        }
    }

    public BoundingBox getBoundingBox() {
        VoxelShape shape = this.getNMS().getShape(this.world, this.position);

        if (shape.isEmpty()) {
            return new BoundingBox();
        } else {
            AABB aabb = shape.bounds();

            return new BoundingBox((double) this.getX() + aabb.minX, (double) this.getY() + aabb.minY, (double) this.getZ() + aabb.minZ, (double) this.getX() + aabb.maxX, (double) this.getY() + aabb.maxY, (double) this.getZ() + aabb.maxZ);
        }
    }

    public org.bukkit.util.VoxelShape getCollisionShape() {
        VoxelShape shape = this.getNMS().getCollisionShape(this.world, this.position);

        return new CraftVoxelShape(shape);
    }

    public boolean canPlace(BlockData data) {
        Preconditions.checkArgument(data != null, "BlockData cannot be null");
        BlockState iblockdata = ((CraftBlockData) data).getState();
        ServerLevel world = this.world.getMinecraftWorld();

        return iblockdata.canSurvive(world, this.position);
    }

    public String getTranslationKey() {
        return this.getNMS().getBlock().getDescriptionId();
    }

    static int[] $SWITCH_TABLE$net$minecraft$core$EnumDirection() {
        int[] aint = CraftBlock.$SWITCH_TABLE$net$minecraft$core$EnumDirection;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Direction.values().length];

            try {
                aint1[Direction.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Direction.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Direction.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Direction.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Direction.UP.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[Direction.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            CraftBlock.$SWITCH_TABLE$net$minecraft$core$EnumDirection = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$BlockFace() {
        int[] aint = CraftBlock.$SWITCH_TABLE$org$bukkit$block$BlockFace;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[BlockFace.values().length];

            try {
                aint1[BlockFace.DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[BlockFace.EAST.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[BlockFace.EAST_NORTH_EAST.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[BlockFace.EAST_SOUTH_EAST.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[BlockFace.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_EAST.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_NORTH_EAST.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_NORTH_WEST.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_WEST.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[BlockFace.SELF.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_EAST.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_SOUTH_EAST.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_SOUTH_WEST.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_WEST.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[BlockFace.UP.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[BlockFace.WEST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[BlockFace.WEST_NORTH_WEST.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[BlockFace.WEST_SOUTH_WEST.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            CraftBlock.$SWITCH_TABLE$org$bukkit$block$BlockFace = aint1;
            return aint1;
        }
    }
}
