package org.bukkit.craftbukkit.v1_20_R2.ban;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.mojang.authlib.GameProfile;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import org.bukkit.BanEntry;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.craftbukkit.v1_20_R2.profile.CraftPlayerProfile;
import org.bukkit.profile.PlayerProfile;

public class CraftProfileBanList implements ProfileBanList {

    private final UserBanList list;

    public CraftProfileBanList(UserBanList list) {
        this.list = list;
    }

    public BanEntry getBanEntry(String target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        return this.getBanEntry(getProfile(target));
    }

    public BanEntry getBanEntry(PlayerProfile target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        return this.getBanEntry(((CraftPlayerProfile) target).buildGameProfile());
    }

    public BanEntry addBan(String target, String reason, Date expires, String source) {
        Preconditions.checkArgument(target != null, "Ban target cannot be null");
        return this.addBan(getProfileByName(target), reason, expires, source);
    }

    public BanEntry addBan(PlayerProfile target, String reason, Date expires, String source) {
        Preconditions.checkArgument(target != null, "PlayerProfile cannot be null");
        Preconditions.checkArgument(target.getUniqueId() != null, "The PlayerProfile UUID cannot be null");
        return this.addBan(((CraftPlayerProfile) target).buildGameProfile(), reason, expires, source);
    }

    public BanEntry addBan(PlayerProfile target, String reason, Instant expires, String source) {
        Date date = expires != null ? Date.from(expires) : null;

        return this.addBan(target, reason, date, source);
    }

    public BanEntry addBan(PlayerProfile target, String reason, Duration duration, String source) {
        Instant instant = duration != null ? Instant.now().plus(duration) : null;

        return this.addBan(target, reason, instant, source);
    }

    public Set getBanEntries() {
        Builder builder = ImmutableSet.builder();
        Iterator iterator = this.list.getEntries().iterator();

        while (iterator.hasNext()) {
            UserBanListEntry entry = (UserBanListEntry) iterator.next();
            GameProfile profile = (GameProfile) entry.getUser();

            builder.add(new CraftProfileBanEntry(profile, entry, this.list));
        }

        return builder.build();
    }

    public Set getEntries() {
        Builder builder = ImmutableSet.builder();
        Iterator iterator = this.list.getEntries().iterator();

        while (iterator.hasNext()) {
            UserBanListEntry entry = (UserBanListEntry) iterator.next();
            GameProfile profile = (GameProfile) entry.getUser();

            builder.add(new CraftProfileBanEntry(profile, entry, this.list));
        }

        return builder.build();
    }

    public boolean isBanned(PlayerProfile target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        return this.isBanned(((CraftPlayerProfile) target).buildGameProfile());
    }

    public boolean isBanned(String target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        return this.isBanned(getProfile(target));
    }

    public void pardon(PlayerProfile target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        this.pardon(((CraftPlayerProfile) target).buildGameProfile());
    }

    public void pardon(String target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        this.pardon(getProfile(target));
    }

    public BanEntry getBanEntry(GameProfile profile) {
        if (profile == null) {
            return null;
        } else {
            UserBanListEntry entry = (UserBanListEntry) this.list.get(profile);

            return entry == null ? null : new CraftProfileBanEntry(profile, entry, this.list);
        }
    }

    public BanEntry addBan(GameProfile profile, String reason, Date expires, String source) {
        if (profile == null) {
            return null;
        } else {
            UserBanListEntry entry = new UserBanListEntry(profile, new Date(), source != null && !source.isBlank() ? source : null, expires, reason != null && !reason.isBlank() ? reason : null);

            this.list.add(entry);
            return new CraftProfileBanEntry(profile, entry, this.list);
        }
    }

    private void pardon(GameProfile profile) {
        this.list.remove((Object) profile);
    }

    private boolean isBanned(GameProfile profile) {
        return profile != null && this.list.isBanned(profile);
    }

    static GameProfile getProfile(String target) {
        UUID uuid = null;

        try {
            uuid = UUID.fromString(target);
        } catch (IllegalArgumentException illegalargumentexception) {
            ;
        }

        return uuid != null ? getProfileByUUID(uuid) : getProfileByName(target);
    }

    static GameProfile getProfileByUUID(UUID uuid) {
        return MinecraftServer.getServer() != null ? (GameProfile) MinecraftServer.getServer().getProfileCache().get(uuid).orElse((Object) null) : null;
    }

    static GameProfile getProfileByName(String name) {
        return MinecraftServer.getServer() != null ? (GameProfile) MinecraftServer.getServer().getProfileCache().get(name).orElse((Object) null) : null;
    }
}
