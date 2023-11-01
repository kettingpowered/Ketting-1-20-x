package org.bukkit.craftbukkit.v1_20_R2.ban;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.net.InetAddresses;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import net.minecraft.server.players.IpBanListEntry;
import org.bukkit.BanEntry;
import org.bukkit.ban.IpBanList;

public class CraftIpBanList implements IpBanList {

    private final net.minecraft.server.players.IpBanList list;

    public CraftIpBanList(net.minecraft.server.players.IpBanList list) {
        this.list = list;
    }

    public BanEntry getBanEntry(String target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        IpBanListEntry entry = (IpBanListEntry) this.list.get((Object) target);

        return entry == null ? null : new CraftIpBanEntry(target, entry, this.list);
    }

    public BanEntry getBanEntry(InetAddress target) {
        return this.getBanEntry(this.getIpFromAddress(target));
    }

    public BanEntry addBan(String target, String reason, Date expires, String source) {
        Preconditions.checkArgument(target != null, "Ban target cannot be null");
        IpBanListEntry entry = new IpBanListEntry(target, new Date(), source != null && !source.isBlank() ? source : null, expires, reason != null && !reason.isBlank() ? reason : null);

        this.list.add(entry);
        return new CraftIpBanEntry(target, entry, this.list);
    }

    public BanEntry addBan(InetAddress target, String reason, Date expires, String source) {
        return this.addBan(this.getIpFromAddress(target), reason, expires, source);
    }

    public BanEntry addBan(InetAddress target, String reason, Instant expires, String source) {
        Date date = expires != null ? Date.from(expires) : null;

        return this.addBan(target, reason, date, source);
    }

    public BanEntry addBan(InetAddress target, String reason, Duration duration, String source) {
        Instant instant = duration != null ? Instant.now().plus(duration) : null;

        return this.addBan(target, reason, instant, source);
    }

    public Set getBanEntries() {
        Builder builder = ImmutableSet.builder();
        String[] astring;
        int i = (astring = this.list.getUserList()).length;

        for (int j = 0; j < i; ++j) {
            String target = astring[j];
            IpBanListEntry ipBanEntry = (IpBanListEntry) this.list.get((Object) target);

            if (ipBanEntry != null) {
                builder.add(new CraftIpBanEntry(target, ipBanEntry, this.list));
            }
        }

        return builder.build();
    }

    public Set getEntries() {
        Builder builder = ImmutableSet.builder();
        String[] astring;
        int i = (astring = this.list.getUserList()).length;

        for (int j = 0; j < i; ++j) {
            String target = astring[j];
            IpBanListEntry ipBanEntry = (IpBanListEntry) this.list.get((Object) target);

            if (ipBanEntry != null) {
                builder.add(new CraftIpBanEntry(target, ipBanEntry, this.list));
            }
        }

        return builder.build();
    }

    public boolean isBanned(String target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        return this.list.isBanned(target);
    }

    public boolean isBanned(InetAddress target) {
        return this.isBanned(this.getIpFromAddress(target));
    }

    public void pardon(String target) {
        Preconditions.checkArgument(target != null, "Target cannot be null");
        this.list.remove((Object) target);
    }

    public void pardon(InetAddress target) {
        this.pardon(this.getIpFromAddress(target));
    }

    private String getIpFromAddress(InetAddress address) {
        return address == null ? null : InetAddresses.toAddrString(address);
    }
}
