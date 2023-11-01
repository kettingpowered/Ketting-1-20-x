package org.bukkit.craftbukkit.v1_20_R2;

import com.mojang.authlib.GameProfile;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_20_R2.entity.memory.CraftMemoryMapper;
import org.bukkit.craftbukkit.v1_20_R2.profile.CraftPlayerProfile;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;

@SerializableAs("Player")
public class CraftOfflinePlayer implements OfflinePlayer, ConfigurationSerializable {

    private final GameProfile profile;
    private final CraftServer server;
    private final PlayerDataStorage storage;

    protected CraftOfflinePlayer(CraftServer server, GameProfile profile) {
        this.server = server;
        this.profile = profile;
        this.storage = server.console.playerDataStorage;
    }

    public boolean isOnline() {
        return this.getPlayer() != null;
    }

    public String getName() {
        Player player = this.getPlayer();

        if (player != null) {
            return player.getName();
        } else if (!this.profile.getName().isEmpty()) {
            return this.profile.getName();
        } else {
            CompoundTag data = this.getBukkitData();

            return data != null && data.contains("lastKnownName") ? data.getString("lastKnownName") : null;
        }
    }

    public UUID getUniqueId() {
        return this.profile.getId();
    }

    public PlayerProfile getPlayerProfile() {
        return new CraftPlayerProfile(this.profile);
    }

    public Server getServer() {
        return this.server;
    }

    public boolean isOp() {
        return this.server.getHandle().isOp(this.profile);
    }

    public void setOp(boolean value) {
        if (value != this.isOp()) {
            if (value) {
                this.server.getHandle().op(this.profile);
            } else {
                this.server.getHandle().deop(this.profile);
            }

        }
    }

    public boolean isBanned() {
        return ((ProfileBanList) this.server.getBanList(Type.PROFILE)).isBanned(this.getPlayerProfile());
    }

    public BanEntry ban(String reason, Date expires, String source) {
        return ((ProfileBanList) this.server.getBanList(Type.PROFILE)).addBan(this.getPlayerProfile(), reason, expires, source);
    }

    public BanEntry ban(String reason, Instant expires, String source) {
        return ((ProfileBanList) this.server.getBanList(Type.PROFILE)).addBan(this.getPlayerProfile(), reason, expires, source);
    }

    public BanEntry ban(String reason, Duration duration, String source) {
        return ((ProfileBanList) this.server.getBanList(Type.PROFILE)).addBan(this.getPlayerProfile(), reason, duration, source);
    }

    public void setBanned(boolean value) {
        if (value) {
            ((ProfileBanList) this.server.getBanList(Type.PROFILE)).addBan(this.getPlayerProfile(), (String) null, (Date) null, (String) null);
        } else {
            ((ProfileBanList) this.server.getBanList(Type.PROFILE)).pardon(this.getPlayerProfile());
        }

    }

    public boolean isWhitelisted() {
        return this.server.getHandle().getWhiteList().isWhiteListed(this.profile);
    }

    public void setWhitelisted(boolean value) {
        if (value) {
            this.server.getHandle().getWhiteList().add(new UserWhiteListEntry(this.profile));
        } else {
            this.server.getHandle().getWhiteList().remove((Object) this.profile);
        }

    }

    public Map serialize() {
        LinkedHashMap result = new LinkedHashMap();

        result.put("UUID", this.profile.getId().toString());
        return result;
    }

    public static OfflinePlayer deserialize(Map args) {
        return args.get("name") != null ? Bukkit.getServer().getOfflinePlayer((String) args.get("name")) : Bukkit.getServer().getOfflinePlayer(UUID.fromString((String) args.get("UUID")));
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[UUID=" + this.profile.getId() + "]";
    }

    public Player getPlayer() {
        return this.server.getPlayer(this.getUniqueId());
    }

    public boolean equals(Object obj) {
        OfflinePlayer other;

        return obj instanceof OfflinePlayer && (other = (OfflinePlayer) obj) == (OfflinePlayer) obj ? (this.getUniqueId() != null && other.getUniqueId() != null ? this.getUniqueId().equals(other.getUniqueId()) : false) : false;
    }

    public int hashCode() {
        byte hash = 5;
        int hash = 97 * hash + (this.getUniqueId() != null ? this.getUniqueId().hashCode() : 0);

        return hash;
    }

    private CompoundTag getData() {
        return this.storage.getPlayerData(this.getUniqueId().toString());
    }

    private CompoundTag getBukkitData() {
        CompoundTag result = this.getData();

        if (result != null) {
            if (!result.contains("bukkit")) {
                result.put("bukkit", new CompoundTag());
            }

            result = result.getCompound("bukkit");
        }

        return result;
    }

    private File getDataFile() {
        return new File(this.storage.getPlayerDir(), this.getUniqueId() + ".dat");
    }

    public long getFirstPlayed() {
        Player player = this.getPlayer();

        if (player != null) {
            return player.getFirstPlayed();
        } else {
            CompoundTag data = this.getBukkitData();

            if (data != null) {
                if (data.contains("firstPlayed")) {
                    return data.getLong("firstPlayed");
                } else {
                    File file = this.getDataFile();

                    return file.lastModified();
                }
            } else {
                return 0L;
            }
        }
    }

    public long getLastPlayed() {
        Player player = this.getPlayer();

        if (player != null) {
            return player.getLastPlayed();
        } else {
            CompoundTag data = this.getBukkitData();

            if (data != null) {
                if (data.contains("lastPlayed")) {
                    return data.getLong("lastPlayed");
                } else {
                    File file = this.getDataFile();

                    return file.lastModified();
                }
            } else {
                return 0L;
            }
        }
    }

    public boolean hasPlayedBefore() {
        return this.getData() != null;
    }

    public Location getLastDeathLocation() {
        return this.getData().contains("LastDeathLocation", 10) ? (Location) GlobalPos.CODEC.parse(NbtOps.INSTANCE, this.getData().get("LastDeathLocation")).result().map(CraftMemoryMapper::fromNms).orElse((Object) null) : null;
    }

    public Location getBedSpawnLocation() {
        CompoundTag data = this.getData();

        if (data == null) {
            return null;
        } else if (data.contains("SpawnX") && data.contains("SpawnY") && data.contains("SpawnZ")) {
            String spawnWorld = data.getString("SpawnWorld");

            if (spawnWorld.equals("")) {
                spawnWorld = ((World) this.server.getWorlds().get(0)).getName();
            }

            return new Location(this.server.getWorld(spawnWorld), (double) data.getInt("SpawnX"), (double) data.getInt("SpawnY"), (double) data.getInt("SpawnZ"));
        } else {
            return null;
        }
    }

    public void setMetadata(String metadataKey, MetadataValue metadataValue) {
        this.server.getPlayerMetadata().setMetadata(this, metadataKey, metadataValue);
    }

    public List getMetadata(String metadataKey) {
        return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin plugin) {
        this.server.getPlayerMetadata().removeMetadata(this, metadataKey, plugin);
    }

    private ServerStatsCounter getStatisticManager() {
        return this.server.getHandle().getPlayerStats(this.getUniqueId(), this.getName());
    }

    public void incrementStatistic(Statistic statistic) {
        if (this.isOnline()) {
            this.getPlayer().incrementStatistic(statistic);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.incrementStatistic(manager, statistic, (ServerPlayer) null);
            manager.save();
        }

    }

    public void decrementStatistic(Statistic statistic) {
        if (this.isOnline()) {
            this.getPlayer().decrementStatistic(statistic);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.decrementStatistic(manager, statistic, (ServerPlayer) null);
            manager.save();
        }

    }

    public int getStatistic(Statistic statistic) {
        return this.isOnline() ? this.getPlayer().getStatistic(statistic) : CraftStatistic.getStatistic(this.getStatisticManager(), statistic);
    }

    public void incrementStatistic(Statistic statistic, int amount) {
        if (this.isOnline()) {
            this.getPlayer().incrementStatistic(statistic, amount);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.incrementStatistic(manager, statistic, amount, (ServerPlayer) null);
            manager.save();
        }

    }

    public void decrementStatistic(Statistic statistic, int amount) {
        if (this.isOnline()) {
            this.getPlayer().decrementStatistic(statistic, amount);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.decrementStatistic(manager, statistic, amount, (ServerPlayer) null);
            manager.save();
        }

    }

    public void setStatistic(Statistic statistic, int newValue) {
        if (this.isOnline()) {
            this.getPlayer().setStatistic(statistic, newValue);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.setStatistic(manager, statistic, newValue, (ServerPlayer) null);
            manager.save();
        }

    }

    public void incrementStatistic(Statistic statistic, Material material) {
        if (this.isOnline()) {
            this.getPlayer().incrementStatistic(statistic, material);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.incrementStatistic(manager, statistic, material, (ServerPlayer) null);
            manager.save();
        }

    }

    public void decrementStatistic(Statistic statistic, Material material) {
        if (this.isOnline()) {
            this.getPlayer().decrementStatistic(statistic, material);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.decrementStatistic(manager, statistic, material, (ServerPlayer) null);
            manager.save();
        }

    }

    public int getStatistic(Statistic statistic, Material material) {
        return this.isOnline() ? this.getPlayer().getStatistic(statistic, material) : CraftStatistic.getStatistic(this.getStatisticManager(), statistic, material);
    }

    public void incrementStatistic(Statistic statistic, Material material, int amount) {
        if (this.isOnline()) {
            this.getPlayer().incrementStatistic(statistic, material, amount);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.incrementStatistic(manager, statistic, material, amount, (ServerPlayer) null);
            manager.save();
        }

    }

    public void decrementStatistic(Statistic statistic, Material material, int amount) {
        if (this.isOnline()) {
            this.getPlayer().decrementStatistic(statistic, material, amount);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.decrementStatistic(manager, statistic, material, amount, (ServerPlayer) null);
            manager.save();
        }

    }

    public void setStatistic(Statistic statistic, Material material, int newValue) {
        if (this.isOnline()) {
            this.getPlayer().setStatistic(statistic, material, newValue);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.setStatistic(manager, statistic, material, newValue, (ServerPlayer) null);
            manager.save();
        }

    }

    public void incrementStatistic(Statistic statistic, EntityType entityType) {
        if (this.isOnline()) {
            this.getPlayer().incrementStatistic(statistic, entityType);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.incrementStatistic(manager, statistic, entityType, (ServerPlayer) null);
            manager.save();
        }

    }

    public void decrementStatistic(Statistic statistic, EntityType entityType) {
        if (this.isOnline()) {
            this.getPlayer().decrementStatistic(statistic, entityType);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.decrementStatistic(manager, statistic, entityType, (ServerPlayer) null);
            manager.save();
        }

    }

    public int getStatistic(Statistic statistic, EntityType entityType) {
        return this.isOnline() ? this.getPlayer().getStatistic(statistic, entityType) : CraftStatistic.getStatistic(this.getStatisticManager(), statistic, entityType);
    }

    public void incrementStatistic(Statistic statistic, EntityType entityType, int amount) {
        if (this.isOnline()) {
            this.getPlayer().incrementStatistic(statistic, entityType, amount);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.incrementStatistic(manager, statistic, entityType, amount, (ServerPlayer) null);
            manager.save();
        }

    }

    public void decrementStatistic(Statistic statistic, EntityType entityType, int amount) {
        if (this.isOnline()) {
            this.getPlayer().decrementStatistic(statistic, entityType, amount);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.decrementStatistic(manager, statistic, entityType, amount, (ServerPlayer) null);
            manager.save();
        }

    }

    public void setStatistic(Statistic statistic, EntityType entityType, int newValue) {
        if (this.isOnline()) {
            this.getPlayer().setStatistic(statistic, entityType, newValue);
        } else {
            ServerStatsCounter manager = this.getStatisticManager();

            CraftStatistic.setStatistic(manager, statistic, entityType, newValue, (ServerPlayer) null);
            manager.save();
        }

    }
}
