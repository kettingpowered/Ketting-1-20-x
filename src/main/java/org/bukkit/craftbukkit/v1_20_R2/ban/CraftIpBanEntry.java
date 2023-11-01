package org.bukkit.craftbukkit.v1_20_R2.ban;

import com.google.common.net.InetAddresses;
import java.net.InetAddress;
import java.time.Instant;
import java.util.Date;
import net.minecraft.server.players.IpBanList;
import net.minecraft.server.players.IpBanListEntry;
import org.bukkit.BanEntry;

public final class CraftIpBanEntry implements BanEntry {

    private static final Date minorDate = Date.from(Instant.parse("1899-12-31T04:00:00Z"));
    private final IpBanList list;
    private final String target;
    private Date created;
    private String source;
    private Date expiration;
    private String reason;

    public CraftIpBanEntry(String target, IpBanListEntry entry, IpBanList list) {
        this.list = list;
        this.target = target;
        this.created = entry.getCreated() != null ? new Date(entry.getCreated().getTime()) : null;
        this.source = entry.getSource();
        this.expiration = entry.getExpires() != null ? new Date(entry.getExpires().getTime()) : null;
        this.reason = entry.getReason();
    }

    public String getTarget() {
        return this.target;
    }

    public InetAddress getBanTarget() {
        return InetAddresses.forString(this.target);
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
        if (expiration != null && expiration.getTime() == CraftIpBanEntry.minorDate.getTime()) {
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
        IpBanListEntry entry = new IpBanListEntry(this.target, this.created, this.source, this.expiration, this.reason);

        this.list.add(entry);
    }

    public void remove() {
        this.list.remove((Object) this.target);
    }
}
