package org.bukkit.craftbukkit.v1_20_R2.ban;

import com.mojang.authlib.GameProfile;
import java.time.Instant;
import java.util.Date;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import org.bukkit.BanEntry;
import org.bukkit.craftbukkit.v1_20_R2.profile.CraftPlayerProfile;
import org.bukkit.profile.PlayerProfile;

public final class CraftProfileBanEntry implements BanEntry {

    private static final Date minorDate = Date.from(Instant.parse("1899-12-31T04:00:00Z"));
    private final UserBanList list;
    private final GameProfile profile;
    private Date created;
    private String source;
    private Date expiration;
    private String reason;

    public CraftProfileBanEntry(GameProfile profile, UserBanListEntry entry, UserBanList list) {
        this.list = list;
        this.profile = profile;
        this.created = entry.getCreated() != null ? new Date(entry.getCreated().getTime()) : null;
        this.source = entry.getSource();
        this.expiration = entry.getExpires() != null ? new Date(entry.getExpires().getTime()) : null;
        this.reason = entry.getReason();
    }

    public String getTarget() {
        return this.profile.getName();
    }

    public PlayerProfile getBanTarget() {
        return new CraftPlayerProfile(this.profile);
    }

    public Date getCreated() {
        return this.created == null ? null : (Date) this.created.clone();
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getExpiration() {
        return this.expiration == null ? null : (Date) this.expiration.clone();
    }

    public void setExpiration(Date expiration) {
        if (expiration != null && expiration.getTime() == CraftProfileBanEntry.minorDate.getTime()) {
            expiration = null;
        }

        this.expiration = expiration;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void save() {
        UserBanListEntry entry = new UserBanListEntry(this.profile, this.created, this.source, this.expiration, this.reason);

        this.list.add(entry);
    }

    public void remove() {
        this.list.remove((Object) this.profile);
    }
}
