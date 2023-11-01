package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.AABB;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.RegionAccessor;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBiome;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R2.util.RandomSourceWrapper;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Cat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cod;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Display;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Egg;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.GlowSquid;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Illager;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Mule;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Salmon;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Sniffer;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Strider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tadpole;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.Trident;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Turtle;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zoglin;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public abstract class CraftRegionAccessor implements RegionAccessor {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$TreeType;

    public abstract WorldGenLevel getHandle();

    public boolean isNormalWorld() {
        return this.getHandle() instanceof ServerLevel;
    }

    public Biome getBiome(Location location) {
        return this.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Biome getBiome(int x, int y, int z) {
        return CraftBiome.minecraftHolderToBukkit(this.getHandle().getNoiseBiome(x >> 2, y >> 2, z >> 2));
    }

    public void setBiome(Location location, Biome biome) {
        this.setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biome);
    }

    public void setBiome(int x, int y, int z, Biome biome) {
        Preconditions.checkArgument(biome != Biome.CUSTOM, "Cannot set the biome to %s", biome);
        Holder biomeBase = CraftBiome.bukkitToMinecraftHolder(biome);

        this.setBiome(x, y, z, biomeBase);
    }

    public abstract void setBiome(int i, int j, int k, Holder holder);

    public BlockState getBlockState(Location location) {
        return this.getBlockState(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public BlockState getBlockState(int x, int y, int z) {
        return CraftBlock.at(this.getHandle(), new BlockPos(x, y, z)).getState();
    }

    public BlockData getBlockData(Location location) {
        return this.getBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public BlockData getBlockData(int x, int y, int z) {
        return CraftBlockData.fromData(this.getData(x, y, z));
    }

    public Material getType(Location location) {
        return this.getType(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Material getType(int x, int y, int z) {
        return CraftMagicNumbers.getMaterial(this.getData(x, y, z).getBlock());
    }

    private net.minecraft.world.level.block.state.BlockState getData(int x, int y, int z) {
        return this.getHandle().getBlockState(new BlockPos(x, y, z));
    }

    public void setBlockData(Location location, BlockData blockData) {
        this.setBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ(), blockData);
    }

    public void setBlockData(int x, int y, int z, BlockData blockData) {
        WorldGenLevel world = this.getHandle();
        BlockPos pos = new BlockPos(x, y, z);
        net.minecraft.world.level.block.state.BlockState old = this.getHandle().getBlockState(pos);

        CraftBlock.setTypeAndData(world, pos, old, ((CraftBlockData) blockData).getState(), true);
    }

    public void setType(Location location, Material material) {
        this.setType(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
    }

    public void setType(int x, int y, int z, Material material) {
        this.setBlockData(x, y, z, material.createBlockData());
    }

    public int getHighestBlockYAt(int x, int z) {
        return this.getHighestBlockYAt(x, z, HeightMap.MOTION_BLOCKING);
    }

    public int getHighestBlockYAt(Location location) {
        return this.getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    public int getHighestBlockYAt(int x, int z, HeightMap heightMap) {
        return this.getHandle().getHeight(CraftHeightMap.toNMS(heightMap), x, z);
    }

    public int getHighestBlockYAt(Location location, HeightMap heightMap) {
        return this.getHighestBlockYAt(location.getBlockX(), location.getBlockZ(), heightMap);
    }

    public boolean generateTree(Location location, Random random, TreeType treeType) {
        BlockPos pos = CraftLocation.toBlockPosition(location);

        return this.generateTree(this.getHandle(), this.getHandle().getMinecraftWorld().getChunkSource().getGenerator(), pos, new RandomSourceWrapper(random), treeType);
    }

    public boolean generateTree(Location location, Random random, TreeType treeType, Consumer consumer) {
        return this.generateTree(location, random, treeType, consumer == null ? null : (blockx) -> {
            consumer.accept(blockx);
            return true;
        });
    }

    public boolean generateTree(Location location, Random random, TreeType treeType, Predicate predicate) {
        BlockPos pos = CraftLocation.toBlockPosition(location);
        BlockStateListPopulator populator = new BlockStateListPopulator(this.getHandle());
        boolean result = this.generateTree(populator, this.getHandle().getMinecraftWorld().getChunkSource().getGenerator(), pos, new RandomSourceWrapper(random), treeType);

        populator.refreshTiles();
        Iterator iterator = populator.getList().iterator();

        while (iterator.hasNext()) {
            BlockState blockState = (BlockState) iterator.next();

            if (predicate == null || predicate.test(blockState)) {
                blockState.update(true, true);
            }
        }

        return result;
    }

    public boolean generateTree(WorldGenLevel access, ChunkGenerator chunkGenerator, BlockPos pos, RandomSource random, TreeType treeType) {
        ResourceKey gen;

        switch ($SWITCH_TABLE$org$bukkit$TreeType()[treeType.ordinal()]) {
            case 1:
            default:
                gen = TreeFeatures.OAK;
                break;
            case 2:
                gen = TreeFeatures.FANCY_OAK;
                break;
            case 3:
                gen = TreeFeatures.SPRUCE;
                break;
            case 4:
                gen = TreeFeatures.PINE;
                break;
            case 5:
                gen = TreeFeatures.BIRCH;
                break;
            case 6:
                gen = TreeFeatures.MEGA_JUNGLE_TREE;
                break;
            case 7:
                gen = TreeFeatures.JUNGLE_TREE_NO_VINE;
                break;
            case 8:
                gen = TreeFeatures.JUNGLE_TREE;
                break;
            case 9:
                gen = TreeFeatures.JUNGLE_BUSH;
                break;
            case 10:
                gen = TreeFeatures.HUGE_RED_MUSHROOM;
                break;
            case 11:
                gen = TreeFeatures.HUGE_BROWN_MUSHROOM;
                break;
            case 12:
                gen = TreeFeatures.SWAMP_OAK;
                break;
            case 13:
                gen = TreeFeatures.ACACIA;
                break;
            case 14:
                gen = TreeFeatures.DARK_OAK;
                break;
            case 15:
                gen = TreeFeatures.MEGA_PINE;
                break;
            case 16:
                gen = TreeFeatures.SUPER_BIRCH_BEES_0002;
                break;
            case 17:
                ChorusFlowerBlock chorusflowerblock = (ChorusFlowerBlock) Blocks.CHORUS_FLOWER;

                ChorusFlowerBlock.generatePlant(access, pos, random, 8);
                return true;
            case 18:
                gen = TreeFeatures.CRIMSON_FUNGUS_PLANTED;
                break;
            case 19:
                gen = TreeFeatures.WARPED_FUNGUS_PLANTED;
                break;
            case 20:
                gen = TreeFeatures.AZALEA_TREE;
                break;
            case 21:
                gen = TreeFeatures.MANGROVE;
                break;
            case 22:
                gen = TreeFeatures.TALL_MANGROVE;
                break;
            case 23:
                gen = TreeFeatures.CHERRY;
        }

        Holder holder = (Holder) access.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(gen).orElse((Object) null);

        return holder != null ? ((ConfiguredFeature) holder.value()).place(access, chunkGenerator, random, pos) : false;
    }

    public Entity spawnEntity(Location location, EntityType entityType) {
        return this.spawn(location, entityType.getEntityClass());
    }

    public Entity spawnEntity(Location loc, EntityType type, boolean randomizeData) {
        return this.spawn(loc, type.getEntityClass(), (Consumer) null, SpawnReason.CUSTOM, randomizeData);
    }

    public List getEntities() {
        ArrayList list = new ArrayList();

        this.getNMSEntities().forEach((entityx) -> {
            CraftEntity bukkitEntity = entityx.getBukkitEntity();

            if (bukkitEntity != null && (!this.isNormalWorld() || bukkitEntity.isValid())) {
                list.add(bukkitEntity);
            }

        });
        return list;
    }

    public List getLivingEntities() {
        ArrayList list = new ArrayList();

        this.getNMSEntities().forEach((entityx) -> {
            CraftEntity bukkitEntity = entityx.getBukkitEntity();

            if (bukkitEntity != null && bukkitEntity instanceof LivingEntity && (!this.isNormalWorld() || bukkitEntity.isValid())) {
                list.add((LivingEntity) bukkitEntity);
            }

        });
        return list;
    }

    public Collection getEntitiesByClass(Class clazz) {
        ArrayList list = new ArrayList();

        this.getNMSEntities().forEach((entityx) -> {
            CraftEntity bukkitEntity = entityx.getBukkitEntity();

            if (bukkitEntity != null) {
                Class bukkitClass = bukkitEntity.getClass();

                if (clazz.isAssignableFrom(bukkitClass) && (!this.isNormalWorld() || bukkitEntity.isValid())) {
                    list.add(bukkitEntity);
                }

            }
        });
        return list;
    }

    public Collection getEntitiesByClasses(Class... classes) {
        ArrayList list = new ArrayList();

        this.getNMSEntities().forEach((entityx) -> {
            CraftEntity bukkitEntity = entityx.getBukkitEntity();

            if (bukkitEntity != null) {
                Class bukkitClass = bukkitEntity.getClass();
                Class[] aclass = classes;
                int i = classes.length;

                for (int j = 0; j < i; ++j) {
                    Class clazz = aclass[j];

                    if (clazz.isAssignableFrom(bukkitClass)) {
                        if (!this.isNormalWorld() || bukkitEntity.isValid()) {
                            list.add(bukkitEntity);
                        }
                        break;
                    }
                }

            }
        });
        return list;
    }

    public abstract Iterable getNMSEntities();

    public Entity spawn(Location location, Class clazz) throws IllegalArgumentException {
        return this.spawn(location, clazz, (Consumer) null, SpawnReason.CUSTOM);
    }

    public Entity spawn(Location location, Class clazz, Consumer function) throws IllegalArgumentException {
        return this.spawn(location, clazz, function, SpawnReason.CUSTOM);
    }

    public Entity spawn(Location location, Class clazz, boolean randomizeData, Consumer function) throws IllegalArgumentException {
        return this.spawn(location, clazz, function, SpawnReason.CUSTOM, randomizeData);
    }

    public Entity spawn(Location location, Class clazz, Consumer function, SpawnReason reason) throws IllegalArgumentException {
        return this.spawn(location, clazz, function, reason, true);
    }

    public Entity spawn(Location location, Class clazz, Consumer function, SpawnReason reason, boolean randomizeData) throws IllegalArgumentException {
        net.minecraft.world.entity.Entity entity = this.createEntity(location, clazz, randomizeData);

        return this.addEntity(entity, reason, function, randomizeData);
    }

    public Entity addEntity(net.minecraft.world.entity.Entity entity, SpawnReason reason) throws IllegalArgumentException {
        return this.addEntity(entity, reason, (Consumer) null, true);
    }

    public Entity addEntity(net.minecraft.world.entity.Entity entity, SpawnReason reason, Consumer function, boolean randomizeData) throws IllegalArgumentException {
        Preconditions.checkArgument(entity != null, "Cannot spawn null entity");
        if (randomizeData && entity instanceof Mob) {
            ((Mob) entity).finalizeSpawn(this.getHandle(), this.getHandle().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.COMMAND, (SpawnGroupData) null, (CompoundTag) null);
        }

        if (!this.isNormalWorld()) {
            entity.generation = true;
        }

        if (function != null) {
            function.accept(entity.getBukkitEntity());
        }

        this.addEntityToWorld(entity, reason);
        return entity.getBukkitEntity();
    }

    public abstract void addEntityToWorld(net.minecraft.world.entity.Entity net_minecraft_world_entity_entity, SpawnReason spawnreason);

    public net.minecraft.world.entity.Entity createEntity(Location location, Class clazz) throws IllegalArgumentException {
        return this.createEntity(location, clazz, true);
    }

    public net.minecraft.world.entity.Entity createEntity(Location location, Class clazz, boolean randomizeData) throws IllegalArgumentException {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(clazz != null, "Entity class cannot be null");
        Object entity = null;
        ServerLevel world = this.getHandle().getMinecraftWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float pitch = location.getPitch();
        float yaw = location.getYaw();

        if (Boat.class.isAssignableFrom(clazz)) {
            if (ChestBoat.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.CHEST_BOAT.create(world);
            } else {
                entity = net.minecraft.world.entity.EntityType.BOAT.create(world);
            }

            ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, yaw, pitch);
        } else if (FallingBlock.class.isAssignableFrom(clazz)) {
            BlockPos pos = BlockPos.containing(x, y, z);

            entity = FallingBlockEntity.fall(world, pos, this.getHandle().getBlockState(pos));
        } else if (Projectile.class.isAssignableFrom(clazz)) {
            if (Snowball.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.world.entity.projectile.Snowball(world, x, y, z);
            } else if (Egg.class.isAssignableFrom(clazz)) {
                entity = new ThrownEgg(world, x, y, z);
            } else if (AbstractArrow.class.isAssignableFrom(clazz)) {
                if (TippedArrow.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.ARROW.create(world);
                    ((Arrow) ((net.minecraft.world.entity.Entity) entity).getBukkitEntity()).setBasePotionData(new PotionData(PotionType.WATER, false, false));
                } else if (SpectralArrow.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.SPECTRAL_ARROW.create(world);
                } else if (Trident.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.TRIDENT.create(world);
                } else {
                    entity = net.minecraft.world.entity.EntityType.ARROW.create(world);
                }

                ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, 0.0F, 0.0F);
            } else if (ThrownExpBottle.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.EXPERIENCE_BOTTLE.create(world);
                ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, 0.0F, 0.0F);
            } else if (EnderPearl.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.ENDER_PEARL.create(world);
                ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, 0.0F, 0.0F);
            } else if (ThrownPotion.class.isAssignableFrom(clazz)) {
                if (LingeringPotion.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.world.entity.projectile.ThrownPotion(world, x, y, z);
                    ((net.minecraft.world.entity.projectile.ThrownPotion) entity).setItem(CraftItemStack.asNMSCopy(new ItemStack(Material.LINGERING_POTION, 1)));
                } else {
                    entity = new net.minecraft.world.entity.projectile.ThrownPotion(world, x, y, z);
                    ((net.minecraft.world.entity.projectile.ThrownPotion) entity).setItem(CraftItemStack.asNMSCopy(new ItemStack(Material.SPLASH_POTION, 1)));
                }
            } else if (Fireball.class.isAssignableFrom(clazz)) {
                if (SmallFireball.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.SMALL_FIREBALL.create(world);
                } else if (WitherSkull.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.WITHER_SKULL.create(world);
                } else if (DragonFireball.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.DRAGON_FIREBALL.create(world);
                } else {
                    entity = net.minecraft.world.entity.EntityType.FIREBALL.create(world);
                }

                ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, yaw, pitch);
                Vector direction = location.getDirection().multiply(10);

                ((AbstractHurtingProjectile) entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
            } else if (ShulkerBullet.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.SHULKER_BULLET.create(world);
                ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, yaw, pitch);
            } else if (LlamaSpit.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.LLAMA_SPIT.create(world);
                ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, yaw, pitch);
            } else if (Firework.class.isAssignableFrom(clazz)) {
                entity = new FireworkRocketEntity(world, x, y, z, net.minecraft.world.item.ItemStack.EMPTY);
            }
        } else if (Minecart.class.isAssignableFrom(clazz)) {
            if (PoweredMinecart.class.isAssignableFrom(clazz)) {
                entity = new MinecartFurnace(world, x, y, z);
            } else if (StorageMinecart.class.isAssignableFrom(clazz)) {
                entity = new MinecartChest(world, x, y, z);
            } else if (ExplosiveMinecart.class.isAssignableFrom(clazz)) {
                entity = new MinecartTNT(world, x, y, z);
            } else if (HopperMinecart.class.isAssignableFrom(clazz)) {
                entity = new MinecartHopper(world, x, y, z);
            } else if (SpawnerMinecart.class.isAssignableFrom(clazz)) {
                entity = new MinecartSpawner(world, x, y, z);
            } else if (CommandMinecart.class.isAssignableFrom(clazz)) {
                entity = new MinecartCommandBlock(world, x, y, z);
            } else {
                entity = new net.minecraft.world.entity.vehicle.Minecart(world, x, y, z);
            }
        } else if (EnderSignal.class.isAssignableFrom(clazz)) {
            entity = new EyeOfEnder(world, x, y, z);
        } else if (EnderCrystal.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.world.entity.EntityType.END_CRYSTAL.create(world);
            ((net.minecraft.world.entity.Entity) entity).moveTo(x, y, z, 0.0F, 0.0F);
        } else if (LivingEntity.class.isAssignableFrom(clazz)) {
            if (Chicken.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.CHICKEN.create(world);
            } else if (Cow.class.isAssignableFrom(clazz)) {
                if (MushroomCow.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.MOOSHROOM.create(world);
                } else {
                    entity = net.minecraft.world.entity.EntityType.COW.create(world);
                }
            } else if (Golem.class.isAssignableFrom(clazz)) {
                if (Snowman.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.SNOW_GOLEM.create(world);
                } else if (IronGolem.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.IRON_GOLEM.create(world);
                } else if (Shulker.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.SHULKER.create(world);
                }
            } else if (Creeper.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.CREEPER.create(world);
            } else if (Ghast.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.GHAST.create(world);
            } else if (Pig.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.PIG.create(world);
            } else if (!Player.class.isAssignableFrom(clazz)) {
                if (Sheep.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.SHEEP.create(world);
                } else if (AbstractHorse.class.isAssignableFrom(clazz)) {
                    if (ChestedHorse.class.isAssignableFrom(clazz)) {
                        if (Donkey.class.isAssignableFrom(clazz)) {
                            entity = net.minecraft.world.entity.EntityType.DONKEY.create(world);
                        } else if (Mule.class.isAssignableFrom(clazz)) {
                            entity = net.minecraft.world.entity.EntityType.MULE.create(world);
                        } else if (Llama.class.isAssignableFrom(clazz)) {
                            if (TraderLlama.class.isAssignableFrom(clazz)) {
                                entity = net.minecraft.world.entity.EntityType.TRADER_LLAMA.create(world);
                            } else {
                                entity = net.minecraft.world.entity.EntityType.LLAMA.create(world);
                            }
                        }
                    } else if (SkeletonHorse.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.SKELETON_HORSE.create(world);
                    } else if (ZombieHorse.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.ZOMBIE_HORSE.create(world);
                    } else if (Camel.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.CAMEL.create(world);
                    } else {
                        entity = net.minecraft.world.entity.EntityType.HORSE.create(world);
                    }
                } else if (AbstractSkeleton.class.isAssignableFrom(clazz)) {
                    if (Stray.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.STRAY.create(world);
                    } else if (WitherSkeleton.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.WITHER_SKELETON.create(world);
                    } else if (Skeleton.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.SKELETON.create(world);
                    }
                } else if (Slime.class.isAssignableFrom(clazz)) {
                    if (MagmaCube.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.MAGMA_CUBE.create(world);
                    } else {
                        entity = net.minecraft.world.entity.EntityType.SLIME.create(world);
                    }
                } else if (Spider.class.isAssignableFrom(clazz)) {
                    if (CaveSpider.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.CAVE_SPIDER.create(world);
                    } else {
                        entity = net.minecraft.world.entity.EntityType.SPIDER.create(world);
                    }
                } else if (Squid.class.isAssignableFrom(clazz)) {
                    if (GlowSquid.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.GLOW_SQUID.create(world);
                    } else {
                        entity = net.minecraft.world.entity.EntityType.SQUID.create(world);
                    }
                } else if (Tameable.class.isAssignableFrom(clazz)) {
                    if (Wolf.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.WOLF.create(world);
                    } else if (Parrot.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.PARROT.create(world);
                    } else if (Cat.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.CAT.create(world);
                    }
                } else if (PigZombie.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.ZOMBIFIED_PIGLIN.create(world);
                } else if (Zombie.class.isAssignableFrom(clazz)) {
                    if (Husk.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.HUSK.create(world);
                    } else if (ZombieVillager.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.ZOMBIE_VILLAGER.create(world);
                    } else if (Drowned.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.DROWNED.create(world);
                    } else {
                        entity = new net.minecraft.world.entity.monster.Zombie(world);
                    }
                } else if (Giant.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.GIANT.create(world);
                } else if (Silverfish.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.SILVERFISH.create(world);
                } else if (Enderman.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.ENDERMAN.create(world);
                } else if (Blaze.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.BLAZE.create(world);
                } else if (AbstractVillager.class.isAssignableFrom(clazz)) {
                    if (Villager.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.VILLAGER.create(world);
                    } else if (WanderingTrader.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.WANDERING_TRADER.create(world);
                    }
                } else if (Witch.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.WITCH.create(world);
                } else if (Wither.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.WITHER.create(world);
                } else if (ComplexLivingEntity.class.isAssignableFrom(clazz)) {
                    if (EnderDragon.class.isAssignableFrom(clazz)) {
                        Preconditions.checkArgument(this.isNormalWorld(), "Cannot spawn entity %s during world generation", clazz.getName());
                        entity = net.minecraft.world.entity.EntityType.ENDER_DRAGON.create(this.getHandle().getMinecraftWorld());
                    }
                } else if (Ambient.class.isAssignableFrom(clazz)) {
                    if (Bat.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.BAT.create(world);
                    }
                } else if (Rabbit.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.RABBIT.create(world);
                } else if (Endermite.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.ENDERMITE.create(world);
                } else if (Guardian.class.isAssignableFrom(clazz)) {
                    if (ElderGuardian.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.ELDER_GUARDIAN.create(world);
                    } else {
                        entity = net.minecraft.world.entity.EntityType.GUARDIAN.create(world);
                    }
                } else if (ArmorStand.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.world.entity.decoration.ArmorStand(world, x, y, z);
                } else if (PolarBear.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.POLAR_BEAR.create(world);
                } else if (Vex.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.VEX.create(world);
                } else if (Illager.class.isAssignableFrom(clazz)) {
                    if (Spellcaster.class.isAssignableFrom(clazz)) {
                        if (Evoker.class.isAssignableFrom(clazz)) {
                            entity = net.minecraft.world.entity.EntityType.EVOKER.create(world);
                        } else if (Illusioner.class.isAssignableFrom(clazz)) {
                            entity = net.minecraft.world.entity.EntityType.ILLUSIONER.create(world);
                        }
                    } else if (Vindicator.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.VINDICATOR.create(world);
                    } else if (Pillager.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.PILLAGER.create(world);
                    }
                } else if (Turtle.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.TURTLE.create(world);
                } else if (Phantom.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.PHANTOM.create(world);
                } else if (Fish.class.isAssignableFrom(clazz)) {
                    if (Cod.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.COD.create(world);
                    } else if (PufferFish.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.PUFFERFISH.create(world);
                    } else if (Salmon.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.SALMON.create(world);
                    } else if (TropicalFish.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.TROPICAL_FISH.create(world);
                    } else if (Tadpole.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.world.entity.EntityType.TADPOLE.create(world);
                    }
                } else if (Dolphin.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.DOLPHIN.create(world);
                } else if (Ocelot.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.OCELOT.create(world);
                } else if (Ravager.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.RAVAGER.create(world);
                } else if (Panda.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.PANDA.create(world);
                } else if (Fox.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.FOX.create(world);
                } else if (Bee.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.BEE.create(world);
                } else if (Hoglin.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.HOGLIN.create(world);
                } else if (Piglin.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.PIGLIN.create(world);
                } else if (PiglinBrute.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.PIGLIN_BRUTE.create(world);
                } else if (Strider.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.STRIDER.create(world);
                } else if (Zoglin.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.ZOGLIN.create(world);
                } else if (Axolotl.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.AXOLOTL.create(world);
                } else if (Goat.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.GOAT.create(world);
                } else if (Allay.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.ALLAY.create(world);
                } else if (Frog.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.FROG.create(world);
                } else if (Warden.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.WARDEN.create(world);
                } else if (Sniffer.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.world.entity.EntityType.SNIFFER.create(world);
                }
            }

            if (entity != null) {
                ((net.minecraft.world.entity.Entity) entity).absMoveTo(x, y, z, yaw, pitch);
                ((net.minecraft.world.entity.Entity) entity).setYHeadRot(yaw);
            }
        } else if (Hanging.class.isAssignableFrom(clazz)) {
            if (LeashHitch.class.isAssignableFrom(clazz)) {
                entity = new LeashFenceKnotEntity(world, BlockPos.containing(x, y, z));
            } else {
                BlockFace face = BlockFace.SELF;
                BlockFace[] faces = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};
                byte width = 16;
                byte height = 16;

                if (ItemFrame.class.isAssignableFrom(clazz)) {
                    width = 12;
                    height = 12;
                    faces = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN};
                }

                BlockPos pos = BlockPos.containing(x, y, z);
                BlockFace[] ablockface = faces;
                int i = faces.length;

                for (int j = 0; j < i; ++j) {
                    BlockFace dir = ablockface[j];
                    net.minecraft.world.level.block.state.BlockState nmsBlock = this.getHandle().getBlockState(pos.relative(CraftBlock.blockFaceToNotch(dir)));

                    if (nmsBlock.isSolid() || DiodeBlock.isDiode(nmsBlock)) {
                        boolean taken = false;
                        AABB bb = ItemFrame.class.isAssignableFrom(clazz) ? net.minecraft.world.entity.decoration.ItemFrame.calculateBoundingBox((net.minecraft.world.entity.Entity) null, pos, CraftBlock.blockFaceToNotch(dir).getOpposite(), width, height) : HangingEntity.calculateBoundingBox((net.minecraft.world.entity.Entity) null, pos, CraftBlock.blockFaceToNotch(dir).getOpposite(), width, height);
                        List list = this.getHandle().getEntities((net.minecraft.world.entity.Entity) null, bb);
                        Iterator it = list.iterator();

                        while (!taken && it.hasNext()) {
                            net.minecraft.world.entity.Entity e = (net.minecraft.world.entity.Entity) it.next();

                            if (e instanceof HangingEntity) {
                                taken = true;
                            }
                        }

                        if (!taken) {
                            face = dir;
                            break;
                        }
                    }
                }

                if (face == BlockFace.SELF) {
                    face = BlockFace.SOUTH;
                    randomizeData = false;
                }

                Direction dir = CraftBlock.blockFaceToNotch(face).getOpposite();

                if (Painting.class.isAssignableFrom(clazz)) {
                    if (this.isNormalWorld() && randomizeData) {
                        entity = (net.minecraft.world.entity.Entity) net.minecraft.world.entity.decoration.Painting.create(world, pos, dir).orElse((Object) null);
                    } else {
                        entity = new net.minecraft.world.entity.decoration.Painting(net.minecraft.world.entity.EntityType.PAINTING, this.getHandle().getMinecraftWorld());
                        ((net.minecraft.world.entity.Entity) entity).absMoveTo(x, y, z, yaw, pitch);
                        ((net.minecraft.world.entity.decoration.Painting) entity).setDirection(dir);
                    }
                } else if (ItemFrame.class.isAssignableFrom(clazz)) {
                    if (GlowItemFrame.class.isAssignableFrom(clazz)) {
                        entity = new net.minecraft.world.entity.decoration.GlowItemFrame(world, BlockPos.containing(x, y, z), dir);
                    } else {
                        entity = new net.minecraft.world.entity.decoration.ItemFrame(world, BlockPos.containing(x, y, z), dir);
                    }
                }
            }
        } else if (TNTPrimed.class.isAssignableFrom(clazz)) {
            entity = new PrimedTnt(world, x, y, z, (net.minecraft.world.entity.LivingEntity) null);
        } else if (ExperienceOrb.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.world.entity.ExperienceOrb(world, x, y, z, 0);
        } else if (LightningStrike.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(world);
            ((net.minecraft.world.entity.Entity) entity).moveTo(location.getX(), location.getY(), location.getZ());
        } else if (AreaEffectCloud.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.world.entity.AreaEffectCloud(world, x, y, z);
        } else if (EvokerFangs.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.world.entity.projectile.EvokerFangs(world, x, y, z, (float) Math.toRadians((double) yaw), 0, (net.minecraft.world.entity.LivingEntity) null);
        } else if (Marker.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.world.entity.EntityType.MARKER.create(world);
            ((net.minecraft.world.entity.Entity) entity).setPos(x, y, z);
        } else if (Interaction.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.world.entity.EntityType.INTERACTION.create(world);
            ((net.minecraft.world.entity.Entity) entity).setPos(x, y, z);
        } else if (Display.class.isAssignableFrom(clazz)) {
            if (BlockDisplay.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.BLOCK_DISPLAY.create(world);
            } else if (ItemDisplay.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.ITEM_DISPLAY.create(world);
            } else if (TextDisplay.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.world.entity.EntityType.TEXT_DISPLAY.create(world);
            }

            if (entity != null) {
                ((net.minecraft.world.entity.Entity) entity).setPos(x, y, z);
            }
        }

        if (entity != null) {
            return (net.minecraft.world.entity.Entity) entity;
        } else {
            throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$TreeType() {
        int[] aint = CraftRegionAccessor.$SWITCH_TABLE$org$bukkit$TreeType;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[TreeType.values().length];

            try {
                aint1[TreeType.ACACIA.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[TreeType.AZALEA.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[TreeType.BIG_TREE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[TreeType.BIRCH.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[TreeType.BROWN_MUSHROOM.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[TreeType.CHERRY.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[TreeType.CHORUS_PLANT.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[TreeType.COCOA_TREE.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[TreeType.CRIMSON_FUNGUS.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[TreeType.DARK_OAK.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[TreeType.JUNGLE.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[TreeType.JUNGLE_BUSH.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[TreeType.MANGROVE.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[TreeType.MEGA_REDWOOD.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[TreeType.REDWOOD.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[TreeType.RED_MUSHROOM.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[TreeType.SMALL_JUNGLE.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[TreeType.SWAMP.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[TreeType.TALL_BIRCH.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                aint1[TreeType.TALL_MANGROVE.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                aint1[TreeType.TALL_REDWOOD.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                aint1[TreeType.TREE.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                aint1[TreeType.WARPED_FUNGUS.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            CraftRegionAccessor.$SWITCH_TABLE$org$bukkit$TreeType = aint1;
            return aint1;
        }
    }
}
