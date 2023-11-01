package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.FeatureFlag;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameRule;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Raid;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.StructureType;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.World.Spigot;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBiome;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.boss.CraftDragonBattle;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.generator.structure.CraftStructure;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.metadata.BlockMetadataStore;
import org.bukkit.craftbukkit.v1_20_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_20_R2.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftBiomeSearchResult;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftRayTraceResult;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftSpawnCategory;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftStructureSearchResult;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.weather.LightningStrikeEvent.Cause;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BiomeSearchResult;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.StructureSearchResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.AsyncCatcher;

public class CraftWorld extends CraftRegionAccessor implements World {

    public static final int CUSTOM_DIMENSION_OFFSET = 10;
    private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
    private final ServerLevel world;
    private WorldBorder worldBorder;
    private Environment environment;
    private final CraftServer server = (CraftServer) Bukkit.getServer();
    private final ChunkGenerator generator;
    private final BiomeProvider biomeProvider;
    private final List populators = new ArrayList();
    private final BlockMetadataStore blockMetadata = new BlockMetadataStore(this);
    private final Object2IntOpenHashMap spawnCategoryLimit = new Object2IntOpenHashMap();
    private final CraftPersistentDataContainer persistentDataContainer;
    private static final Random rand = new Random();
    private static Map gamerules;
    private static Map gameruleDefinitions;
    private final Spigot spigot;

    public CraftWorld(ServerLevel world, ChunkGenerator gen, BiomeProvider biomeProvider, Environment env) {
        this.persistentDataContainer = new CraftPersistentDataContainer(CraftWorld.DATA_TYPE_REGISTRY);
        this.spigot = new Spigot() {
            public LightningStrike strikeLightning(Location loc, boolean isSilent) {
                LightningBolt lightning = (LightningBolt) EntityType.LIGHTNING_BOLT.create(CraftWorld.this.world);

                lightning.moveTo(loc.getX(), loc.getY(), loc.getZ());
                lightning.isSilent = isSilent;
                CraftWorld.this.world.strikeLightning(lightning, Cause.CUSTOM);
                return (LightningStrike) lightning.getBukkitEntity();
            }

            public LightningStrike strikeLightningEffect(Location loc, boolean isSilent) {
                LightningBolt lightning = (LightningBolt) EntityType.LIGHTNING_BOLT.create(CraftWorld.this.world);

                lightning.moveTo(loc.getX(), loc.getY(), loc.getZ());
                lightning.visualOnly = true;
                lightning.isSilent = isSilent;
                CraftWorld.this.world.strikeLightning(lightning, Cause.CUSTOM);
                return (LightningStrike) lightning.getBukkitEntity();
            }
        };
        this.world = world;
        this.generator = gen;
        this.biomeProvider = biomeProvider;
        this.environment = env;
    }

    public Block getBlockAt(int x, int y, int z) {
        return CraftBlock.at(this.world, new BlockPos(x, y, z));
    }

    public Location getSpawnLocation() {
        BlockPos spawn = this.world.getSharedSpawnPos();
        float yaw = this.world.getSharedSpawnAngle();

        return CraftLocation.toBukkit(spawn, this, yaw, 0.0F);
    }

    public boolean setSpawnLocation(Location location) {
        Preconditions.checkArgument(location != null, "location");
        return this.equals(location.getWorld()) ? this.setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw()) : false;
    }

    public boolean setSpawnLocation(int x, int y, int z, float angle) {
        try {
            Location previousLocation = this.getSpawnLocation();

            this.world.levelData.setSpawn(new BlockPos(x, y, z), angle);
            SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);

            this.server.getPluginManager().callEvent(event);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean setSpawnLocation(int x, int y, int z) {
        return this.setSpawnLocation(x, y, z, 0.0F);
    }

    public Chunk getChunkAt(int x, int z) {
        LevelChunk chunk = (LevelChunk) this.world.getChunk(x, z, ChunkStatus.FULL, true);

        return new CraftChunk(chunk);
    }

    @NotNull
    public Chunk getChunkAt(int x, int z, boolean generate) {
        return (Chunk) (generate ? this.getChunkAt(x, z) : new CraftChunk(this.getHandle(), x, z));
    }

    public Chunk getChunkAt(Block block) {
        Preconditions.checkArgument(block != null, "null block");
        return this.getChunkAt(block.getX() >> 4, block.getZ() >> 4);
    }

    public boolean isChunkLoaded(int x, int z) {
        return this.world.getChunkSource().isChunkLoaded(x, z);
    }

    public boolean isChunkGenerated(int x, int z) {
        try {
            return this.isChunkLoaded(x, z) || ((Optional) this.world.getChunkSource().chunkMap.read(new ChunkPos(x, z)).get()).isPresent();
        } catch (ExecutionException | InterruptedException interruptedexception) {
            throw new RuntimeException(interruptedexception);
        }
    }

    public Chunk[] getLoadedChunks() {
        Long2ObjectLinkedOpenHashMap chunks = this.world.getChunkSource().chunkMap.visibleChunkMap;

        return (Chunk[]) chunks.values().stream().map(ChunkHolder::getFullChunkNow).filter(Objects::nonNull).map(CraftChunk::new).toArray((i) -> {
            return new Chunk[i];
        });
    }

    public void loadChunk(int x, int z) {
        this.loadChunk(x, z, true);
    }

    public boolean unloadChunk(Chunk chunk) {
        return this.unloadChunk(chunk.getX(), chunk.getZ());
    }

    public boolean unloadChunk(int x, int z) {
        return this.unloadChunk(x, z, true);
    }

    public boolean unloadChunk(int x, int z, boolean save) {
        return this.unloadChunk0(x, z, save);
    }

    public boolean unloadChunkRequest(int x, int z) {
        AsyncCatcher.catchOp("chunk unload");
        if (this.isChunkLoaded(x, z)) {
            this.world.getChunkSource().removeRegionTicket(TicketType.PLUGIN, new ChunkPos(x, z), 1, Unit.INSTANCE);
        }

        return true;
    }

    private boolean unloadChunk0(int x, int z, boolean save) {
        AsyncCatcher.catchOp("chunk unload");
        if (!this.isChunkLoaded(x, z)) {
            return true;
        } else {
            LevelChunk chunk = this.world.getChunk(x, z);

            chunk.setUnsaved(!save);
            this.unloadChunkRequest(x, z);
            this.world.getChunkSource().purgeUnload();
            return !this.isChunkLoaded(x, z);
        }
    }

    public boolean regenerateChunk(int x, int z) {
        AsyncCatcher.catchOp("chunk regenerate");
        throw new UnsupportedOperationException("Not supported in this Minecraft version! Unless you can fix it, this is not a bug :)");
    }

    public boolean refreshChunk(int x, int z) {
        ChunkHolder playerChunk = (ChunkHolder) this.world.getChunkSource().chunkMap.visibleChunkMap.get(ChunkPos.asLong(x, z));

        if (playerChunk == null) {
            return false;
        } else {
            playerChunk.getTickingChunkFuture().thenAccept((eitherx) -> {
                eitherx.left().ifPresent((chunkx) -> {
                    List playersInRange = playerChunk.playerProvider.getPlayers(playerChunk.getPos(), false);

                    if (!playersInRange.isEmpty()) {
                        ClientboundLevelChunkWithLightPacket refreshPacket = new ClientboundLevelChunkWithLightPacket(chunkx, this.world.getLightEngine(), (BitSet) null, (BitSet) null);
                        Iterator iterator = playersInRange.iterator();

                        while (iterator.hasNext()) {
                            ServerPlayer player = (ServerPlayer) iterator.next();

                            if (player.connection != null) {
                                player.connection.send(refreshPacket);
                            }
                        }

                    }
                });
            });
            return true;
        }
    }

    public boolean isChunkInUse(int x, int z) {
        return this.isChunkLoaded(x, z);
    }

    public boolean loadChunk(int x, int z, boolean generate) {
        AsyncCatcher.catchOp("chunk load");
        ChunkAccess chunk = this.world.getChunkSource().getChunk(x, z, generate ? ChunkStatus.FULL : ChunkStatus.EMPTY, true);

        if (chunk instanceof ImposterProtoChunk) {
            chunk = this.world.getChunkSource().getChunk(x, z, ChunkStatus.FULL, true);
        }

        if (chunk instanceof LevelChunk) {
            this.world.getChunkSource().addRegionTicket(TicketType.PLUGIN, new ChunkPos(x, z), 1, Unit.INSTANCE);
            return true;
        } else {
            return false;
        }
    }

    public boolean isChunkLoaded(Chunk chunk) {
        Preconditions.checkArgument(chunk != null, "null chunk");
        return this.isChunkLoaded(chunk.getX(), chunk.getZ());
    }

    public void loadChunk(Chunk chunk) {
        Preconditions.checkArgument(chunk != null, "null chunk");
        this.loadChunk(chunk.getX(), chunk.getZ());
    }

    public boolean addPluginChunkTicket(int x, int z, Plugin plugin) {
        Preconditions.checkArgument(plugin != null, "null plugin");
        Preconditions.checkArgument(plugin.isEnabled(), "plugin is not enabled");
        ChunkMap.DistanceManager chunkDistanceManager = this.world.getChunkSource().chunkMap.distanceManager;

        if (chunkDistanceManager.addRegionTicketAtDistance(TicketType.PLUGIN_TICKET, new ChunkPos(x, z), 2, plugin)) {
            this.getChunkAt(x, z);
            return true;
        } else {
            return false;
        }
    }

    public boolean removePluginChunkTicket(int x, int z, Plugin plugin) {
        Preconditions.checkNotNull(plugin, "null plugin");
        ChunkMap.DistanceManager chunkDistanceManager = this.world.getChunkSource().chunkMap.distanceManager;

        return chunkDistanceManager.removeRegionTicketAtDistance(TicketType.PLUGIN_TICKET, new ChunkPos(x, z), 2, plugin);
    }

    public void removePluginChunkTickets(Plugin plugin) {
        Preconditions.checkNotNull(plugin, "null plugin");
        ChunkMap.DistanceManager chunkDistanceManager = this.world.getChunkSource().chunkMap.distanceManager;

        chunkDistanceManager.removeAllTicketsFor(TicketType.PLUGIN_TICKET, 31, plugin);
    }

    public Collection getPluginChunkTickets(int x, int z) {
        ChunkMap.DistanceManager chunkDistanceManager = this.world.getChunkSource().chunkMap.distanceManager;
        SortedArraySet tickets = (SortedArraySet) chunkDistanceManager.tickets.get(ChunkPos.asLong(x, z));

        if (tickets == null) {
            return Collections.emptyList();
        } else {
            Builder ret = ImmutableList.builder();
            Iterator iterator = tickets.iterator();

            while (iterator.hasNext()) {
                Ticket ticket = (Ticket) iterator.next();

                if (ticket.getType() == TicketType.PLUGIN_TICKET) {
                    ret.add((Plugin) ticket.key);
                }
            }

            return ret.build();
        }
    }

    public Map getPluginChunkTickets() {
        HashMap ret = new HashMap();
        ChunkMap.DistanceManager chunkDistanceManager = this.world.getChunkSource().chunkMap.distanceManager;
        Iterator iterator = chunkDistanceManager.tickets.long2ObjectEntrySet().iterator();

        while (iterator.hasNext()) {
            Entry chunkTickets = (Entry) iterator.next();
            long chunkKey = chunkTickets.getLongKey();
            SortedArraySet tickets = (SortedArraySet) chunkTickets.getValue();
            Chunk chunk = null;
            Iterator iterator1 = tickets.iterator();

            while (iterator1.hasNext()) {
                Ticket ticket = (Ticket) iterator1.next();

                if (ticket.getType() == TicketType.PLUGIN_TICKET) {
                    if (chunk == null) {
                        chunk = this.getChunkAt(ChunkPos.getX(chunkKey), ChunkPos.getZ(chunkKey));
                    }

                    ((Builder) ret.computeIfAbsent((Plugin) ticket.key, (keyx) -> {
                        return ImmutableList.builder();
                    })).add(chunk);
                }
            }
        }

        return (Map) ret.entrySet().stream().collect(ImmutableMap.toImmutableMap(java.util.Map.Entry::getKey, (entryx) -> {
            return ((Builder) entryx.getValue()).build();
        }));
    }

    public boolean isChunkForceLoaded(int x, int z) {
        return this.getHandle().getForcedChunks().contains(ChunkPos.asLong(x, z));
    }

    public void setChunkForceLoaded(int x, int z, boolean forced) {
        this.getHandle().setChunkForced(x, z, forced);
    }

    public Collection getForceLoadedChunks() {
        HashSet chunks = new HashSet();
        Iterator iterator = this.getHandle().getForcedChunks().iterator();

        while (iterator.hasNext()) {
            long coord = (Long) iterator.next();

            chunks.add(this.getChunkAt(ChunkPos.getX(coord), ChunkPos.getZ(coord)));
        }

        return Collections.unmodifiableCollection(chunks);
    }

    public ServerLevel getHandle() {
        return this.world;
    }

    public Item dropItem(Location loc, ItemStack item) {
        return this.dropItem(loc, item, (Consumer) null);
    }

    public Item dropItem(Location loc, ItemStack item, Consumer function) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");
        Preconditions.checkArgument(item != null, "ItemStack cannot be null");
        ItemEntity entity = new ItemEntity(this.world, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        Item itemEntity = (Item) entity.getBukkitEntity();

        entity.pickupDelay = 10;
        if (function != null) {
            function.accept(itemEntity);
        }

        this.world.addFreshEntity(entity, SpawnReason.CUSTOM);
        return itemEntity;
    }

    public Item dropItemNaturally(Location loc, ItemStack item) {
        return this.dropItemNaturally(loc, item, (Consumer) null);
    }

    public Item dropItemNaturally(Location loc, ItemStack item, Consumer function) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");
        Preconditions.checkArgument(item != null, "ItemStack cannot be null");
        double xs = (double) (this.world.random.nextFloat() * 0.5F) + 0.25D;
        double ys = (double) (this.world.random.nextFloat() * 0.5F) + 0.25D;
        double zs = (double) (this.world.random.nextFloat() * 0.5F) + 0.25D;

        loc = loc.clone().add(xs, ys, zs);
        return this.dropItem(loc, item, function);
    }

    public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
        return (Arrow) this.spawnArrow(loc, velocity, speed, spread, Arrow.class);
    }

    public AbstractArrow spawnArrow(Location loc, Vector velocity, float speed, float spread, Class clazz) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");
        Preconditions.checkArgument(velocity != null, "Vector cannot be null");
        Preconditions.checkArgument(clazz != null, "clazz Entity for the arrow cannot be null");
        net.minecraft.world.entity.projectile.AbstractArrow arrow;

        if (TippedArrow.class.isAssignableFrom(clazz)) {
            arrow = (net.minecraft.world.entity.projectile.AbstractArrow) EntityType.ARROW.create(this.world);
            ((Arrow) arrow.getBukkitEntity()).setBasePotionData(new PotionData(PotionType.WATER, false, false));
        } else if (SpectralArrow.class.isAssignableFrom(clazz)) {
            arrow = (net.minecraft.world.entity.projectile.AbstractArrow) EntityType.SPECTRAL_ARROW.create(this.world);
        } else if (Trident.class.isAssignableFrom(clazz)) {
            arrow = (net.minecraft.world.entity.projectile.AbstractArrow) EntityType.TRIDENT.create(this.world);
        } else {
            arrow = (net.minecraft.world.entity.projectile.AbstractArrow) EntityType.ARROW.create(this.world);
        }

        arrow.moveTo(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        arrow.shoot(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
        this.world.addFreshEntity(arrow);
        return (AbstractArrow) arrow.getBukkitEntity();
    }

    public LightningStrike strikeLightning(Location loc) {
        return this.strikeLightning0(loc, false);
    }

    public LightningStrike strikeLightningEffect(Location loc) {
        return this.strikeLightning0(loc, true);
    }

    private LightningStrike strikeLightning0(Location loc, boolean isVisual) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");
        LightningBolt lightning = (LightningBolt) EntityType.LIGHTNING_BOLT.create(this.world);

        lightning.moveTo(loc.getX(), loc.getY(), loc.getZ());
        lightning.setVisualOnly(isVisual);
        this.world.strikeLightning(lightning, Cause.CUSTOM);
        return (LightningStrike) lightning.getBukkitEntity();
    }

    public boolean generateTree(Location loc, TreeType type) {
        return this.generateTree(loc, CraftWorld.rand, type);
    }

    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        this.world.captureTreeGeneration = true;
        this.world.captureBlockStates = true;
        boolean grownTree = this.generateTree(loc, type);

        this.world.captureBlockStates = false;
        this.world.captureTreeGeneration = false;
        if (!grownTree) {
            this.world.capturedBlockStates.clear();
            return false;
        } else {
            Iterator iterator = this.world.capturedBlockStates.values().iterator();

            while (iterator.hasNext()) {
                BlockState blockstate = (BlockState) iterator.next();
                BlockPos position = ((CraftBlockState) blockstate).getPosition();
                net.minecraft.world.level.block.state.BlockState oldBlock = this.world.getBlockState(position);
                int flag = ((CraftBlockState) blockstate).getFlag();

                delegate.setBlockData(blockstate.getX(), blockstate.getY(), blockstate.getZ(), blockstate.getBlockData());
                net.minecraft.world.level.block.state.BlockState newBlock = this.world.getBlockState(position);

                this.world.notifyAndUpdatePhysics(position, (LevelChunk) null, oldBlock, newBlock, newBlock, flag, 512);
            }

            this.world.capturedBlockStates.clear();
            return true;
        }
    }

    public String getName() {
        return this.world.K.getLevelName();
    }

    public UUID getUID() {
        return this.world.uuid;
    }

    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(this.world.dimension().location());
    }

    public String toString() {
        return "CraftWorld{name=" + this.getName() + '}';
    }

    public long getTime() {
        long time = this.getFullTime() % 24000L;

        if (time < 0L) {
            time += 24000L;
        }

        return time;
    }

    public void setTime(long time) {
        long margin = (time - this.getFullTime()) % 24000L;

        if (margin < 0L) {
            margin += 24000L;
        }

        this.setFullTime(this.getFullTime() + margin);
    }

    public long getFullTime() {
        return this.world.getDayTime();
    }

    public void setFullTime(long time) {
        TimeSkipEvent event = new TimeSkipEvent(this, SkipReason.CUSTOM, time - this.world.getDayTime());

        this.server.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            this.world.setDayTime(this.world.getDayTime() + event.getSkipAmount());
            Iterator iterator = this.getPlayers().iterator();

            while (iterator.hasNext()) {
                Player p = (Player) iterator.next();
                CraftPlayer cp = (CraftPlayer) p;

                if (cp.getHandle().connection != null) {
                    cp.getHandle().connection.send(new ClientboundSetTimePacket(cp.getHandle().level().getGameTime(), cp.getHandle().getPlayerTime(), cp.getHandle().level().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
                }
            }

        }
    }

    public long getGameTime() {
        return this.world.levelData.getGameTime();
    }

    public boolean createExplosion(double x, double y, double z, float power) {
        return this.createExplosion(x, y, z, power, false, true);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        return this.createExplosion(x, y, z, power, setFire, true);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
        return this.createExplosion(x, y, z, power, setFire, breakBlocks, (Entity) null);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks, Entity source) {
        return !this.world.explode(source == null ? null : ((CraftEntity) source).getHandle(), x, y, z, power, setFire, breakBlocks ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE).wasCanceled;
    }

    public boolean createExplosion(Location loc, float power) {
        return this.createExplosion(loc, power, false);
    }

    public boolean createExplosion(Location loc, float power, boolean setFire) {
        return this.createExplosion(loc, power, setFire, true);
    }

    public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks) {
        return this.createExplosion(loc, power, setFire, breakBlocks, (Entity) null);
    }

    public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks, Entity source) {
        Preconditions.checkArgument(loc != null, "Location is null");
        Preconditions.checkArgument(this.equals(loc.getWorld()), "Location not in world");
        return this.createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire, breakBlocks, source);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public Block getBlockAt(Location location) {
        return this.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Chunk getChunkAt(Location location) {
        return this.getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    public ChunkGenerator getGenerator() {
        return this.generator;
    }

    public BiomeProvider getBiomeProvider() {
        return this.biomeProvider;
    }

    public List getPopulators() {
        return this.populators;
    }

    public Block getHighestBlockAt(int x, int z) {
        return this.getBlockAt(x, this.getHighestBlockYAt(x, z), z);
    }

    public Block getHighestBlockAt(Location location) {
        return this.getHighestBlockAt(location.getBlockX(), location.getBlockZ());
    }

    public int getHighestBlockYAt(int x, int z, HeightMap heightMap) {
        return this.world.getChunk(x >> 4, z >> 4).getHeight(CraftHeightMap.toNMS(heightMap), x, z);
    }

    public Block getHighestBlockAt(int x, int z, HeightMap heightMap) {
        return this.getBlockAt(x, this.getHighestBlockYAt(x, z, heightMap), z);
    }

    public Block getHighestBlockAt(Location location, HeightMap heightMap) {
        return this.getHighestBlockAt(location.getBlockX(), location.getBlockZ(), heightMap);
    }

    public Biome getBiome(int x, int z) {
        return this.getBiome(x, 0, z);
    }

    public void setBiome(int x, int z, Biome bio) {
        for (int y = this.getMinHeight(); y < this.getMaxHeight(); ++y) {
            this.setBiome(x, y, z, bio);
        }

    }

    public void setBiome(int x, int y, int z, Holder bb) {
        BlockPos pos = new BlockPos(x, 0, z);

        if (this.world.hasChunkAt(pos)) {
            LevelChunk chunk = this.world.getChunkAt(pos);

            if (chunk != null) {
                chunk.setBiome(x >> 2, y >> 2, z >> 2, bb);
                chunk.setUnsaved(true);
            }
        }

    }

    public double getTemperature(int x, int z) {
        return this.getTemperature(x, 0, z);
    }

    public double getTemperature(int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        return (double) ((net.minecraft.world.level.biome.Biome) this.world.getNoiseBiome(x >> 2, y >> 2, z >> 2).value()).getTemperature(pos);
    }

    public double getHumidity(int x, int z) {
        return this.getHumidity(x, 0, z);
    }

    public double getHumidity(int x, int y, int z) {
        return (double) ((net.minecraft.world.level.biome.Biome) this.world.getNoiseBiome(x >> 2, y >> 2, z >> 2).value()).climateSettings.downfall();
    }

    /** @deprecated */
    @Deprecated
    public Collection getEntitiesByClass(Class... classes) {
        return this.getEntitiesByClasses(classes);
    }

    public Iterable getNMSEntities() {
        return this.getHandle().getEntities().getAll();
    }

    public void addEntityToWorld(net.minecraft.world.entity.Entity entity, SpawnReason reason) {
        this.getHandle().addFreshEntity(entity, reason);
    }

    public Collection getNearbyEntities(Location location, double x, double y, double z) {
        return this.getNearbyEntities(location, x, y, z, (Predicate) null);
    }

    public Collection getNearbyEntities(Location location, double x, double y, double z, Predicate filter) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(this.equals(location.getWorld()), "Location cannot be in a different world");
        BoundingBox aabb = BoundingBox.of(location, x, y, z);

        return this.getNearbyEntities(aabb, filter);
    }

    public Collection getNearbyEntities(BoundingBox boundingBox) {
        return this.getNearbyEntities(boundingBox, (Predicate) null);
    }

    public Collection getNearbyEntities(BoundingBox boundingBox, Predicate filter) {
        AsyncCatcher.catchOp("getNearbyEntities");
        Preconditions.checkArgument(boundingBox != null, "BoundingBox cannot be null");
        AABB bb = new AABB(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ(), boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMaxZ());
        List entityList = this.getHandle().getEntities((net.minecraft.world.entity.Entity) null, bb, (Predicate) Predicates.alwaysTrue());
        ArrayList bukkitEntityList = new ArrayList(entityList.size());
        Iterator iterator = entityList.iterator();

        while (iterator.hasNext()) {
            net.minecraft.world.entity.Entity entity = (net.minecraft.world.entity.Entity) iterator.next();
            CraftEntity bukkitEntity = entity.getBukkitEntity();

            if (filter == null || filter.test(bukkitEntity)) {
                bukkitEntityList.add(bukkitEntity);
            }
        }

        return bukkitEntityList;
    }

    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance) {
        return this.rayTraceEntities(start, direction, maxDistance, (Predicate) null);
    }

    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize) {
        return this.rayTraceEntities(start, direction, maxDistance, raySize, (Predicate) null);
    }

    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, Predicate filter) {
        return this.rayTraceEntities(start, direction, maxDistance, 0.0D, filter);
    }

    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize, Predicate filter) {
        Preconditions.checkArgument(start != null, "Location start cannot be null");
        Preconditions.checkArgument(this.equals(start.getWorld()), "Location start cannot be in a different world");
        start.checkFinite();
        Preconditions.checkArgument(direction != null, "Vector direction cannot be null");
        direction.checkFinite();
        Preconditions.checkArgument(direction.lengthSquared() > 0.0D, "Direction's magnitude (%s) need to be greater than 0", direction.lengthSquared());
        if (maxDistance < 0.0D) {
            return null;
        } else {
            Vector startPos = start.toVector();
            Vector dir = direction.clone().normalize().multiply(maxDistance);
            BoundingBox aabb = BoundingBox.of(startPos, startPos).expandDirectional(dir).expand(raySize);
            Collection entities = this.getNearbyEntities(aabb, filter);
            Entity nearestHitEntity = null;
            RayTraceResult nearestHitResult = null;
            double nearestDistanceSq = Double.MAX_VALUE;
            Iterator iterator = entities.iterator();

            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();
                BoundingBox boundingBox = entity.getBoundingBox().expand(raySize);
                RayTraceResult hitResult = boundingBox.rayTrace(startPos, direction, maxDistance);

                if (hitResult != null) {
                    double distanceSq = startPos.distanceSquared(hitResult.getHitPosition());

                    if (distanceSq < nearestDistanceSq) {
                        nearestHitEntity = entity;
                        nearestHitResult = hitResult;
                        nearestDistanceSq = distanceSq;
                    }
                }
            }

            return nearestHitEntity == null ? null : new RayTraceResult(nearestHitResult.getHitPosition(), nearestHitEntity, nearestHitResult.getHitBlockFace());
        }
    }

    public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance) {
        return this.rayTraceBlocks(start, direction, maxDistance, FluidCollisionMode.NEVER, false);
    }

    public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        return this.rayTraceBlocks(start, direction, maxDistance, fluidCollisionMode, false);
    }

    public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        Preconditions.checkArgument(start != null, "Location start cannot be null");
        Preconditions.checkArgument(this.equals(start.getWorld()), "Location start cannot be in a different world");
        start.checkFinite();
        Preconditions.checkArgument(direction != null, "Vector direction cannot be null");
        direction.checkFinite();
        Preconditions.checkArgument(direction.lengthSquared() > 0.0D, "Direction's magnitude (%s) need to be greater than 0", direction.lengthSquared());
        Preconditions.checkArgument(fluidCollisionMode != null, "FluidCollisionMode cannot be null");
        if (maxDistance < 0.0D) {
            return null;
        } else {
            Vector dir = direction.clone().normalize().multiply(maxDistance);
            Vec3 startPos = CraftLocation.toVec3D(start);
            Vec3 endPos = startPos.add(dir.getX(), dir.getY(), dir.getZ());
            BlockHitResult nmsHitResult = this.getHandle().clip(new ClipContext(startPos, endPos, ignorePassableBlocks ? ClipContext.Block.COLLIDER : ClipContext.Block.OUTLINE, CraftFluidCollisionMode.toNMS(fluidCollisionMode), (net.minecraft.world.entity.Entity) null));

            return CraftRayTraceResult.fromNMS(this, nmsHitResult);
        }
    }

    public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, double raySize, Predicate filter) {
        RayTraceResult blockHit = this.rayTraceBlocks(start, direction, maxDistance, fluidCollisionMode, ignorePassableBlocks);
        Vector startVec = null;
        double blockHitDistance = maxDistance;

        if (blockHit != null) {
            startVec = start.toVector();
            blockHitDistance = startVec.distance(blockHit.getHitPosition());
        }

        RayTraceResult entityHit = this.rayTraceEntities(start, direction, blockHitDistance, raySize, filter);

        if (blockHit == null) {
            return entityHit;
        } else if (entityHit == null) {
            return blockHit;
        } else {
            double entityHitDistanceSquared = startVec.distanceSquared(entityHit.getHitPosition());

            return entityHitDistanceSquared < blockHitDistance * blockHitDistance ? entityHit : blockHit;
        }
    }

    public List getPlayers() {
        ArrayList list = new ArrayList(this.world.players().size());
        Iterator iterator = this.world.players().iterator();

        while (iterator.hasNext()) {
            net.minecraft.world.entity.player.Player human = (net.minecraft.world.entity.player.Player) iterator.next();
            CraftHumanEntity bukkitEntity = human.getBukkitEntity();

            if (bukkitEntity != null && bukkitEntity instanceof Player) {
                list.add((Player) bukkitEntity);
            }
        }

        return list;
    }

    public void save() {
        AsyncCatcher.catchOp("world save");
        this.server.checkSaveState();
        boolean oldSave = this.world.noSave;

        this.world.noSave = false;
        this.world.save((ProgressListener) null, false, false);
        this.world.noSave = oldSave;
    }

    public boolean isAutoSave() {
        return !this.world.noSave;
    }

    public void setAutoSave(boolean value) {
        this.world.noSave = !value;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.getHandle().K.setDifficulty(net.minecraft.world.Difficulty.byId(difficulty.getValue()));
    }

    public Difficulty getDifficulty() {
        return Difficulty.getByValue(this.getHandle().getDifficulty().ordinal());
    }

    public BlockMetadataStore getBlockMetadata() {
        return this.blockMetadata;
    }

    public boolean hasStorm() {
        return this.world.levelData.isRaining();
    }

    public void setStorm(boolean hasStorm) {
        this.world.levelData.setRaining(hasStorm);
        this.setWeatherDuration(0);
        this.setClearWeatherDuration(0);
    }

    public int getWeatherDuration() {
        return this.world.K.getRainTime();
    }

    public void setWeatherDuration(int duration) {
        this.world.K.setRainTime(duration);
    }

    public boolean isThundering() {
        return this.world.levelData.isThundering();
    }

    public void setThundering(boolean thundering) {
        this.world.K.setThundering(thundering);
        this.setThunderDuration(0);
        this.setClearWeatherDuration(0);
    }

    public int getThunderDuration() {
        return this.world.K.getThunderTime();
    }

    public void setThunderDuration(int duration) {
        this.world.K.setThunderTime(duration);
    }

    public boolean isClearWeather() {
        return !this.hasStorm() && !this.isThundering();
    }

    public void setClearWeatherDuration(int duration) {
        this.world.K.setClearWeatherTime(duration);
    }

    public int getClearWeatherDuration() {
        return this.world.K.getClearWeatherTime();
    }

    public long getSeed() {
        return this.world.getSeed();
    }

    public boolean getPVP() {
        return this.world.pvpMode;
    }

    public void setPVP(boolean pvp) {
        this.world.pvpMode = pvp;
    }

    public void playEffect(Player player, Effect effect, int data) {
        this.playEffect(player.getLocation(), effect, data, 0);
    }

    public void playEffect(Location location, Effect effect, int data) {
        this.playEffect(location, effect, data, 64);
    }

    public void playEffect(Location loc, Effect effect, Object data) {
        this.playEffect(loc, effect, data, 64);
    }

    public void playEffect(Location loc, Effect effect, Object data, int radius) {
        if (data != null) {
            Preconditions.checkArgument(effect.getData() != null, "Effect.%s does not have a valid Data", effect);
            Preconditions.checkArgument(effect.getData().isAssignableFrom(data.getClass()), "%s data cannot be used for the %s effect", data.getClass().getName(), effect);
        } else {
            Preconditions.checkArgument(effect.getData() == null || effect == Effect.ELECTRIC_SPARK, "Wrong kind of data for the %s effect", effect);
        }

        int datavalue = CraftEffect.getDataValue(effect, data);

        this.playEffect(loc, effect, datavalue, radius);
    }

    public void playEffect(Location location, Effect effect, int data, int radius) {
        Preconditions.checkArgument(effect != null, "Effect cannot be null");
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(location.getWorld() != null, "World of Location cannot be null");
        int packetData = effect.getId();
        ClientboundLevelEventPacket packet = new ClientboundLevelEventPacket(packetData, CraftLocation.toBlockPosition(location), data, false);

        radius *= radius;
        Iterator iterator = this.getPlayers().iterator();

        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();

            if (((CraftPlayer) player).getHandle().connection != null && location.getWorld().equals(player.getWorld())) {
                int distance = (int) player.getLocation().distanceSquared(location);

                if (distance <= radius) {
                    ((CraftPlayer) player).getHandle().connection.send(packet);
                }
            }
        }

    }

    public FallingBlock spawnFallingBlock(Location location, MaterialData data) throws IllegalArgumentException {
        Preconditions.checkArgument(data != null, "MaterialData cannot be null");
        return this.spawnFallingBlock(location, data.getItemType(), data.getData());
    }

    public FallingBlock spawnFallingBlock(Location location, Material material, byte data) throws IllegalArgumentException {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(material != null, "Material cannot be null");
        Preconditions.checkArgument(material.isBlock(), "Material.%s must be a block", material);
        FallingBlockEntity entity = FallingBlockEntity.fall(this.world, BlockPos.containing(location.getX(), location.getY(), location.getZ()), CraftMagicNumbers.getBlock(material).defaultBlockState(), SpawnReason.CUSTOM);

        return (FallingBlock) entity.getBukkitEntity();
    }

    public FallingBlock spawnFallingBlock(Location location, BlockData data) throws IllegalArgumentException {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(data != null, "BlockData cannot be null");
        FallingBlockEntity entity = FallingBlockEntity.fall(this.world, BlockPos.containing(location.getX(), location.getY(), location.getZ()), ((CraftBlockData) data).getState(), SpawnReason.CUSTOM);

        return (FallingBlock) entity.getBukkitEntity();
    }

    public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
        return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
    }

    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
        this.world.setSpawnSettings(allowMonsters, allowAnimals);
    }

    public boolean getAllowAnimals() {
        return this.world.getChunkSource().spawnFriendlies;
    }

    public boolean getAllowMonsters() {
        return this.world.getChunkSource().spawnEnemies;
    }

    public int getMinHeight() {
        return this.world.getMinBuildHeight();
    }

    public int getMaxHeight() {
        return this.world.getMaxBuildHeight();
    }

    public int getLogicalHeight() {
        return this.world.dimensionType().logicalHeight();
    }

    public boolean isNatural() {
        return this.world.dimensionType().natural();
    }

    public boolean isBedWorks() {
        return this.world.dimensionType().bedWorks();
    }

    public boolean hasSkyLight() {
        return this.world.dimensionType().hasSkyLight();
    }

    public boolean hasCeiling() {
        return this.world.dimensionType().hasCeiling();
    }

    public boolean isPiglinSafe() {
        return this.world.dimensionType().piglinSafe();
    }

    public boolean isRespawnAnchorWorks() {
        return this.world.dimensionType().respawnAnchorWorks();
    }

    public boolean hasRaids() {
        return this.world.dimensionType().hasRaids();
    }

    public boolean isUltraWarm() {
        return this.world.dimensionType().ultraWarm();
    }

    public int getSeaLevel() {
        return this.world.getSeaLevel();
    }

    public boolean getKeepSpawnInMemory() {
        return this.world.keepSpawnInMemory;
    }

    public void setKeepSpawnInMemory(boolean keepLoaded) {
        this.world.keepSpawnInMemory = keepLoaded;
        BlockPos chunkcoordinates = this.world.getSharedSpawnPos();

        if (keepLoaded) {
            this.world.getChunkSource().addRegionTicket(TicketType.START, new ChunkPos(chunkcoordinates), 11, Unit.INSTANCE);
        } else {
            this.world.getChunkSource().removeRegionTicket(TicketType.START, new ChunkPos(chunkcoordinates), 11, Unit.INSTANCE);
        }

    }

    public int hashCode() {
        return this.getUID().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            CraftWorld other = (CraftWorld) obj;

            return this.getUID() == other.getUID();
        }
    }

    public File getWorldFolder() {
        return this.world.convertable.getLevelPath(LevelResource.ROOT).toFile().getParentFile();
    }

    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
        Iterator iterator = this.getPlayers().iterator();

        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();

            player.sendPluginMessage(source, channel, message);
        }

    }

    public Set getListeningPluginChannels() {
        HashSet result = new HashSet();
        Iterator iterator = this.getPlayers().iterator();

        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();

            result.addAll(player.getListeningPluginChannels());
        }

        return result;
    }

    public WorldType getWorldType() {
        return this.world.isFlat() ? WorldType.FLAT : WorldType.NORMAL;
    }

    public boolean canGenerateStructures() {
        return this.world.K.worldGenOptions().generateStructures();
    }

    public boolean isHardcore() {
        return this.world.getLevelData().isHardcore();
    }

    public void setHardcore(boolean hardcore) {
        this.world.K.settings.hardcore = hardcore;
    }

    /** @deprecated */
    @Deprecated
    public long getTicksPerAnimalSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
        this.setTicksPerSpawns(SpawnCategory.ANIMAL, ticksPerAnimalSpawns);
    }

    /** @deprecated */
    @Deprecated
    public long getTicksPerMonsterSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.MONSTER);
    }

    /** @deprecated */
    @Deprecated
    public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
        this.setTicksPerSpawns(SpawnCategory.MONSTER, ticksPerMonsterSpawns);
    }

    /** @deprecated */
    @Deprecated
    public long getTicksPerWaterSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public void setTicksPerWaterSpawns(int ticksPerWaterSpawns) {
        this.setTicksPerSpawns(SpawnCategory.WATER_ANIMAL, ticksPerWaterSpawns);
    }

    /** @deprecated */
    @Deprecated
    public long getTicksPerWaterAmbientSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT);
    }

    /** @deprecated */
    @Deprecated
    public void setTicksPerWaterAmbientSpawns(int ticksPerWaterAmbientSpawns) {
        this.setTicksPerSpawns(SpawnCategory.WATER_AMBIENT, ticksPerWaterAmbientSpawns);
    }

    /** @deprecated */
    @Deprecated
    public long getTicksPerWaterUndergroundCreatureSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE);
    }

    /** @deprecated */
    @Deprecated
    public void setTicksPerWaterUndergroundCreatureSpawns(int ticksPerWaterUndergroundCreatureSpawns) {
        this.setTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE, ticksPerWaterUndergroundCreatureSpawns);
    }

    /** @deprecated */
    @Deprecated
    public long getTicksPerAmbientSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.AMBIENT);
    }

    /** @deprecated */
    @Deprecated
    public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns) {
        this.setTicksPerSpawns(SpawnCategory.AMBIENT, ticksPerAmbientSpawns);
    }

    public void setTicksPerSpawns(SpawnCategory spawnCategory, int ticksPerCategorySpawn) {
        Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
        Preconditions.checkArgument(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory.%s are not supported", spawnCategory);
        this.world.ticksPerSpawnCategory.put(spawnCategory, (long) ticksPerCategorySpawn);
    }

    public long getTicksPerSpawns(SpawnCategory spawnCategory) {
        Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
        Preconditions.checkArgument(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory.%s are not supported", spawnCategory);
        return this.world.ticksPerSpawnCategory.getLong(spawnCategory);
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        this.server.getWorldMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    public List getMetadata(String metadataKey) {
        return this.server.getWorldMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return this.server.getWorldMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        this.server.getWorldMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    /** @deprecated */
    @Deprecated
    public int getMonsterSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.MONSTER);
    }

    /** @deprecated */
    @Deprecated
    public void setMonsterSpawnLimit(int limit) {
        this.setSpawnLimit(SpawnCategory.MONSTER, limit);
    }

    /** @deprecated */
    @Deprecated
    public int getAnimalSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public void setAnimalSpawnLimit(int limit) {
        this.setSpawnLimit(SpawnCategory.ANIMAL, limit);
    }

    /** @deprecated */
    @Deprecated
    public int getWaterAnimalSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.WATER_ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public void setWaterAnimalSpawnLimit(int limit) {
        this.setSpawnLimit(SpawnCategory.WATER_ANIMAL, limit);
    }

    /** @deprecated */
    @Deprecated
    public int getWaterAmbientSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.WATER_AMBIENT);
    }

    /** @deprecated */
    @Deprecated
    public void setWaterAmbientSpawnLimit(int limit) {
        this.setSpawnLimit(SpawnCategory.WATER_AMBIENT, limit);
    }

    /** @deprecated */
    @Deprecated
    public int getWaterUndergroundCreatureSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE);
    }

    /** @deprecated */
    @Deprecated
    public void setWaterUndergroundCreatureSpawnLimit(int limit) {
        this.setSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE, limit);
    }

    /** @deprecated */
    @Deprecated
    public int getAmbientSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.AMBIENT);
    }

    /** @deprecated */
    @Deprecated
    public void setAmbientSpawnLimit(int limit) {
        this.setSpawnLimit(SpawnCategory.AMBIENT, limit);
    }

    public int getSpawnLimit(SpawnCategory spawnCategory) {
        Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
        Preconditions.checkArgument(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory.%s are not supported", spawnCategory);
        int limit = this.spawnCategoryLimit.getOrDefault(spawnCategory, -1);

        if (limit < 0) {
            limit = this.server.getSpawnLimit(spawnCategory);
        }

        return limit;
    }

    public void setSpawnLimit(SpawnCategory spawnCategory, int limit) {
        Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
        Preconditions.checkArgument(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory.%s are not supported", spawnCategory);
        this.spawnCategoryLimit.put(spawnCategory, limit);
    }

    public void playSound(Location loc, Sound sound, float volume, float pitch) {
        this.playSound(loc, sound, SoundCategory.MASTER, volume, pitch);
    }

    public void playSound(Location loc, String sound, float volume, float pitch) {
        this.playSound(loc, sound, SoundCategory.MASTER, volume, pitch);
    }

    public void playSound(Location loc, Sound sound, SoundCategory category, float volume, float pitch) {
        if (loc != null && sound != null && category != null) {
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();

            this.getHandle().playSound((net.minecraft.world.entity.player.Player) null, x, y, z, CraftSound.bukkitToMinecraft(sound), SoundSource.valueOf(category.name()), volume, pitch);
        }
    }

    public void playSound(Location loc, String sound, SoundCategory category, float volume, float pitch) {
        if (loc != null && sound != null && category != null) {
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            ClientboundSoundPacket packet = new ClientboundSoundPacket(Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation(sound))), SoundSource.valueOf(category.name()), x, y, z, volume, pitch, this.getHandle().getRandom().nextLong());

            this.world.getServer().getPlayerList().broadcast((net.minecraft.world.entity.player.Player) null, x, y, z, volume > 1.0F ? (double) (16.0F * volume) : 16.0D, this.world.dimension(), packet);
        }
    }

    public void playSound(Entity entity, Sound sound, float volume, float pitch) {
        this.playSound(entity, sound, SoundCategory.MASTER, volume, pitch);
    }

    public void playSound(Entity entity, String sound, float volume, float pitch) {
        this.playSound(entity, sound, SoundCategory.MASTER, volume, pitch);
    }

    public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch) {
        CraftEntity craftEntity;

        if (entity instanceof CraftEntity && (craftEntity = (CraftEntity) entity) == (CraftEntity) entity && entity.getWorld() == this && sound != null && category != null) {
            ClientboundSoundEntityPacket packet = new ClientboundSoundEntityPacket(CraftSound.bukkitToMinecraftHolder(sound), SoundSource.valueOf(category.name()), craftEntity.getHandle(), volume, pitch, this.getHandle().getRandom().nextLong());
            ChunkMap.TrackedEntity entityTracker = (ChunkMap.TrackedEntity) this.getHandle().getChunkSource().chunkMap.entityMap.get(entity.getEntityId());

            if (entityTracker != null) {
                entityTracker.broadcastAndSend(packet);
            }

        }
    }

    public void playSound(Entity entity, String sound, SoundCategory category, float volume, float pitch) {
        CraftEntity craftEntity;

        if (entity instanceof CraftEntity && (craftEntity = (CraftEntity) entity) == (CraftEntity) entity && entity.getWorld() == this && sound != null && category != null) {
            ClientboundSoundEntityPacket packet = new ClientboundSoundEntityPacket(Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation(sound))), SoundSource.valueOf(category.name()), craftEntity.getHandle(), volume, pitch, this.getHandle().getRandom().nextLong());
            ChunkMap.TrackedEntity entityTracker = (ChunkMap.TrackedEntity) this.getHandle().getChunkSource().chunkMap.entityMap.get(entity.getEntityId());

            if (entityTracker != null) {
                entityTracker.broadcastAndSend(packet);
            }

        }
    }

    public static synchronized Map getGameRulesNMS() {
        if (CraftWorld.gamerules != null) {
            return CraftWorld.gamerules;
        } else {
            final HashMap gamerules = new HashMap();

            GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
                public void visit(GameRules.Key gamerules_gamerulekey, GameRules.Type gamerules_gameruledefinition) {
                    gamerules.put(gamerules_gamerulekey.getId(), gamerules_gamerulekey);
                }
            });
            CraftWorld.gamerules = gamerules;
            return gamerules;
        }
    }

    public static synchronized Map getGameRuleDefinitions() {
        if (CraftWorld.gameruleDefinitions != null) {
            return CraftWorld.gameruleDefinitions;
        } else {
            final HashMap gameruleDefinitions = new HashMap();

            GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
                public void visit(GameRules.Key gamerules_gamerulekey, GameRules.Type gamerules_gameruledefinition) {
                    gameruleDefinitions.put(gamerules_gamerulekey.getId(), gamerules_gameruledefinition);
                }
            });
            CraftWorld.gameruleDefinitions = gameruleDefinitions;
            return gameruleDefinitions;
        }
    }

    public String getGameRuleValue(String rule) {
        if (rule == null) {
            return null;
        } else {
            GameRules.Value value = this.getHandle().getGameRules().getRule((GameRules.Key) getGameRulesNMS().get(rule));

            return value != null ? value.toString() : "";
        }
    }

    public boolean setGameRuleValue(String rule, String value) {
        if (rule != null && value != null) {
            if (!this.isGameRule(rule)) {
                return false;
            } else {
                GameRules.Value handle = this.getHandle().getGameRules().getRule((GameRules.Key) getGameRulesNMS().get(rule));

                handle.deserialize(value);
                handle.onChanged(this.getHandle().getServer());
                return true;
            }
        } else {
            return false;
        }
    }

    public String[] getGameRules() {
        return (String[]) getGameRulesNMS().keySet().toArray(new String[getGameRulesNMS().size()]);
    }

    public boolean isGameRule(String rule) {
        Preconditions.checkArgument(rule != null, "String rule cannot be null");
        Preconditions.checkArgument(!rule.isEmpty(), "String rule cannot be empty");
        return getGameRulesNMS().containsKey(rule);
    }

    public Object getGameRuleValue(GameRule rule) {
        Preconditions.checkArgument(rule != null, "GameRule cannot be null");
        return this.convert(rule, this.getHandle().getGameRules().getRule((GameRules.Key) getGameRulesNMS().get(rule.getName())));
    }

    public Object getGameRuleDefault(GameRule rule) {
        Preconditions.checkArgument(rule != null, "GameRule cannot be null");
        return this.convert(rule, ((GameRules.Type) getGameRuleDefinitions().get(rule.getName())).createRule());
    }

    public boolean setGameRule(GameRule rule, Object newValue) {
        Preconditions.checkArgument(rule != null, "GameRule cannot be null");
        Preconditions.checkArgument(newValue != null, "GameRule value cannot be null");
        if (!this.isGameRule(rule.getName())) {
            return false;
        } else {
            GameRules.Value handle = this.getHandle().getGameRules().getRule((GameRules.Key) getGameRulesNMS().get(rule.getName()));

            handle.deserialize(newValue.toString());
            handle.onChanged(this.getHandle().getServer());
            return true;
        }
    }

    private Object convert(GameRule rule, GameRules.Value value) {
        if (value == null) {
            return null;
        } else if (value instanceof GameRules.BooleanValue) {
            return rule.getType().cast(((GameRules.BooleanValue) value).get());
        } else if (value instanceof GameRules.IntegerValue) {
            return rule.getType().cast(value.getCommandResult());
        } else {
            throw new IllegalArgumentException("Invalid GameRule type (" + value + ") for GameRule " + rule.getName());
        }
    }

    public WorldBorder getWorldBorder() {
        if (this.worldBorder == null) {
            this.worldBorder = new CraftWorldBorder(this);
        }

        return this.worldBorder;
    }

    public void spawnParticle(Particle particle, Location location, int count) {
        this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count);
    }

    public void spawnParticle(Particle particle, double x, double y, double z, int count) {
        this.spawnParticle(particle, x, y, z, count, (Object) null);
    }

    public void spawnParticle(Particle particle, Location location, int count, Object data) {
        this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, data);
    }

    public void spawnParticle(Particle particle, double x, double y, double z, int count, Object data) {
        this.spawnParticle(particle, x, y, z, count, 0.0D, 0.0D, 0.0D, data);
    }

    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ) {
        this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
    }

    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
        this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, (Object) null);
    }

    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, Object data) {
        this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);
    }

    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, Object data) {
        this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1.0D, data);
    }

    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
    }

    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, (Object) null);
    }

    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, Object data) {
        this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data);
    }

    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, Object data) {
        this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data, false);
    }

    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, Object data, boolean force) {
        this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data, force);
    }

    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, Object data, boolean force) {
        particle = CraftParticle.convertLegacy(particle);
        data = CraftParticle.convertLegacy(data);
        if (data != null) {
            Preconditions.checkArgument(particle.getDataType().isInstance(data), "data (%s) should be %s", data.getClass(), particle.getDataType());
        }

        this.getHandle().sendParticles((ServerPlayer) null, CraftParticle.createParticleParam(particle, data), x, y, z, count, offsetX, offsetY, offsetZ, extra, force);
    }

    /** @deprecated */
    @Deprecated
    public Location locateNearestStructure(Location origin, StructureType structureType, int radius, boolean findUnexplored) {
        StructureSearchResult result = null;

        if (StructureType.MINESHAFT == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.MINESHAFT, radius, findUnexplored);
        } else if (StructureType.VILLAGE == structureType) {
            result = this.locateNearestStructure(origin, List.of(Structure.VILLAGE_DESERT, Structure.VILLAGE_PLAINS, Structure.VILLAGE_SAVANNA, Structure.VILLAGE_SNOWY, Structure.VILLAGE_TAIGA), radius, findUnexplored);
        } else if (StructureType.NETHER_FORTRESS == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.FORTRESS, radius, findUnexplored);
        } else if (StructureType.STRONGHOLD == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.STRONGHOLD, radius, findUnexplored);
        } else if (StructureType.JUNGLE_PYRAMID == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.JUNGLE_TEMPLE, radius, findUnexplored);
        } else if (StructureType.OCEAN_RUIN == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.OCEAN_RUIN, radius, findUnexplored);
        } else if (StructureType.DESERT_PYRAMID == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.DESERT_PYRAMID, radius, findUnexplored);
        } else if (StructureType.IGLOO == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.IGLOO, radius, findUnexplored);
        } else if (StructureType.SWAMP_HUT == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.SWAMP_HUT, radius, findUnexplored);
        } else if (StructureType.OCEAN_MONUMENT == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.OCEAN_MONUMENT, radius, findUnexplored);
        } else if (StructureType.END_CITY == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.END_CITY, radius, findUnexplored);
        } else if (StructureType.WOODLAND_MANSION == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.WOODLAND_MANSION, radius, findUnexplored);
        } else if (StructureType.BURIED_TREASURE == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.BURIED_TREASURE, radius, findUnexplored);
        } else if (StructureType.SHIPWRECK == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.SHIPWRECK, radius, findUnexplored);
        } else if (StructureType.PILLAGER_OUTPOST == structureType) {
            result = this.locateNearestStructure(origin, Structure.PILLAGER_OUTPOST, radius, findUnexplored);
        } else if (StructureType.NETHER_FOSSIL == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.NETHER_FOSSIL, radius, findUnexplored);
        } else if (StructureType.RUINED_PORTAL == structureType) {
            result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.RUINED_PORTAL, radius, findUnexplored);
        } else if (StructureType.BASTION_REMNANT == structureType) {
            result = this.locateNearestStructure(origin, Structure.BASTION_REMNANT, radius, findUnexplored);
        }

        return result == null ? null : result.getLocation();
    }

    public StructureSearchResult locateNearestStructure(Location origin, org.bukkit.generator.structure.StructureType structureType, int radius, boolean findUnexplored) {
        ArrayList structures = new ArrayList();
        Iterator iterator = Registry.STRUCTURE.iterator();

        while (iterator.hasNext()) {
            Structure structure = (Structure) iterator.next();

            if (structure.getStructureType() == structureType) {
                structures.add(structure);
            }
        }

        return this.locateNearestStructure(origin, (List) structures, radius, findUnexplored);
    }

    public StructureSearchResult locateNearestStructure(Location origin, Structure structure, int radius, boolean findUnexplored) {
        return this.locateNearestStructure(origin, List.of(structure), radius, findUnexplored);
    }

    public StructureSearchResult locateNearestStructure(Location origin, List structures, int radius, boolean findUnexplored) {
        BlockPos originPos = BlockPos.containing(origin.getX(), origin.getY(), origin.getZ());
        ArrayList holders = new ArrayList();
        Iterator iterator = structures.iterator();

        while (iterator.hasNext()) {
            Structure structure = (Structure) iterator.next();

            holders.add(Holder.direct(CraftStructure.bukkitToMinecraft(structure)));
        }

        Pair found = this.getHandle().getChunkSource().getGenerator().findNearestMapStructure(this.getHandle(), HolderSet.direct((List) holders), originPos, radius, findUnexplored);

        return found == null ? null : new CraftStructureSearchResult(CraftStructure.minecraftToBukkit((net.minecraft.world.level.levelgen.structure.Structure) ((Holder) found.getSecond()).value(), this.getHandle().registryAccess()), CraftLocation.toBukkit((BlockPos) found.getFirst(), (World) this));
    }

    public BiomeSearchResult locateNearestBiome(Location origin, int radius, Biome... biomes) {
        return this.locateNearestBiome(origin, radius, 32, 64, biomes);
    }

    public BiomeSearchResult locateNearestBiome(Location origin, int radius, int horizontalInterval, int verticalInterval, Biome... biomes) {
        BlockPos originPos = BlockPos.containing(origin.getX(), origin.getY(), origin.getZ());
        HashSet holders = new HashSet();
        Biome[] abiome = biomes;
        int i = biomes.length;

        for (int j = 0; j < i; ++j) {
            Biome biome = abiome[j];

            holders.add(CraftBiome.bukkitToMinecraftHolder(biome));
        }

        Climate.Sampler sampler = this.getHandle().getChunkSource().randomState().sampler();
        Pair found = this.getHandle().getChunkSource().getGenerator().getBiomeSource().findClosestBiome3d(originPos, radius, horizontalInterval, verticalInterval, holders::contains, sampler, this.getHandle());

        return found == null ? null : new CraftBiomeSearchResult(CraftBiome.minecraftHolderToBukkit((Holder) found.getSecond()), new Location(this, (double) ((BlockPos) found.getFirst()).getX(), (double) ((BlockPos) found.getFirst()).getY(), (double) ((BlockPos) found.getFirst()).getZ()));
    }

    public Raid locateNearestRaid(Location location, int radius) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(radius >= 0, "Radius value (%s) cannot be negative", radius);
        Raids persistentRaid = this.world.getRaids();
        net.minecraft.world.entity.raid.Raid raid = persistentRaid.getNearbyRaid(CraftLocation.toBlockPosition(location), radius * radius);

        return raid == null ? null : new CraftRaid(raid);
    }

    public List getRaids() {
        Raids persistentRaid = this.world.getRaids();

        return (List) persistentRaid.raidMap.values().stream().map(CraftRaid::new).collect(Collectors.toList());
    }

    public DragonBattle getEnderDragonBattle() {
        return this.getHandle().getDragonFight() == null ? null : new CraftDragonBattle(this.getHandle().getDragonFight());
    }

    public PersistentDataContainer getPersistentDataContainer() {
        return this.persistentDataContainer;
    }

    public Set getFeatureFlags() {
        Stream stream = CraftFeatureFlag.getFromNMS(this.getHandle().enabledFeatures()).stream();

        FeatureFlag.class.getClass();
        return (Set) stream.map(FeatureFlag.class::cast).collect(Collectors.toUnmodifiableSet());
    }

    public void storeBukkitValues(CompoundTag c) {
        if (!this.persistentDataContainer.isEmpty()) {
            c.put("BukkitValues", this.persistentDataContainer.toTagCompound());
        }

    }

    public void readBukkitValues(Tag c) {
        if (c instanceof CompoundTag) {
            this.persistentDataContainer.putAll((CompoundTag) c);
        }

    }

    public int getViewDistance() {
        return this.world.spigotConfig.viewDistance;
    }

    public int getSimulationDistance() {
        return this.world.spigotConfig.simulationDistance;
    }

    public Spigot spigot() {
        return this.spigot;
    }
}
