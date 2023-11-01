package org.bukkit.craftbukkit.v1_20_R2.scoreboard;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.world.scores.PlayerTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

final class CraftTeam extends CraftScoreboardComponent implements Team {

    private final PlayerTeam team;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$scoreboard$Team$Option;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$scoreboard$NameTagVisibility;
    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$scores$ScoreboardTeamBase$EnumNameTagVisibility;

    CraftTeam(CraftScoreboard scoreboard, PlayerTeam team) {
        super(scoreboard);
        this.team = team;
    }

    public String getName() {
        this.checkState();
        return this.team.getName();
    }

    public String getDisplayName() {
        this.checkState();
        return CraftChatMessage.fromComponent(this.team.getDisplayName());
    }

    public void setDisplayName(String displayName) {
        Preconditions.checkArgument(displayName != null, "Display name cannot be null");
        this.checkState();
        this.team.setDisplayName(CraftChatMessage.fromString(displayName)[0]);
    }

    public String getPrefix() {
        this.checkState();
        return CraftChatMessage.fromComponent(this.team.getPlayerPrefix());
    }

    public void setPrefix(String prefix) {
        Preconditions.checkArgument(prefix != null, "Prefix cannot be null");
        this.checkState();
        this.team.setPlayerPrefix(CraftChatMessage.fromStringOrNull(prefix));
    }

    public String getSuffix() {
        this.checkState();
        return CraftChatMessage.fromComponent(this.team.getPlayerSuffix());
    }

    public void setSuffix(String suffix) {
        Preconditions.checkArgument(suffix != null, "Suffix cannot be null");
        this.checkState();
        this.team.setPlayerSuffix(CraftChatMessage.fromStringOrNull(suffix));
    }

    public ChatColor getColor() {
        this.checkState();
        return CraftChatMessage.getColor(this.team.getColor());
    }

    public void setColor(ChatColor color) {
        Preconditions.checkArgument(color != null, "Color cannot be null");
        this.checkState();
        this.team.setColor(CraftChatMessage.getColor(color));
    }

    public boolean allowFriendlyFire() {
        this.checkState();
        return this.team.isAllowFriendlyFire();
    }

    public void setAllowFriendlyFire(boolean enabled) {
        this.checkState();
        this.team.setAllowFriendlyFire(enabled);
    }

    public boolean canSeeFriendlyInvisibles() {
        this.checkState();
        return this.team.canSeeFriendlyInvisibles();
    }

    public void setCanSeeFriendlyInvisibles(boolean enabled) {
        this.checkState();
        this.team.setSeeFriendlyInvisibles(enabled);
    }

    public NameTagVisibility getNameTagVisibility() throws IllegalArgumentException {
        this.checkState();
        return notchToBukkit(this.team.getNameTagVisibility());
    }

    public void setNameTagVisibility(NameTagVisibility visibility) throws IllegalArgumentException {
        this.checkState();
        this.team.setNameTagVisibility(bukkitToNotch(visibility));
    }

    public Set getPlayers() {
        this.checkState();
        Builder players = ImmutableSet.builder();
        Iterator iterator = this.team.getPlayers().iterator();

        while (iterator.hasNext()) {
            String playerName = (String) iterator.next();

            players.add(Bukkit.getOfflinePlayer(playerName));
        }

        return players.build();
    }

    public Set getEntries() {
        this.checkState();
        Builder entries = ImmutableSet.builder();
        Iterator iterator = this.team.getPlayers().iterator();

        while (iterator.hasNext()) {
            String playerName = (String) iterator.next();

            entries.add(playerName);
        }

        return entries.build();
    }

    public int getSize() {
        this.checkState();
        return this.team.getPlayers().size();
    }

    public void addPlayer(OfflinePlayer player) {
        Preconditions.checkArgument(player != null, "OfflinePlayer cannot be null");
        this.addEntry(player.getName());
    }

    public void addEntry(String entry) {
        Preconditions.checkArgument(entry != null, "Entry cannot be null");
        CraftScoreboard scoreboard = this.checkState();

        scoreboard.board.addPlayerToTeam(entry, this.team);
    }

    public boolean removePlayer(OfflinePlayer player) {
        Preconditions.checkArgument(player != null, "OfflinePlayer cannot be null");
        return this.removeEntry(player.getName());
    }

    public boolean removeEntry(String entry) {
        Preconditions.checkArgument(entry != null, "Entry cannot be null");
        CraftScoreboard scoreboard = this.checkState();

        if (!this.team.getPlayers().contains(entry)) {
            return false;
        } else {
            scoreboard.board.removePlayerFromTeam(entry, this.team);
            return true;
        }
    }

    public boolean hasPlayer(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
        Preconditions.checkArgument(player != null, "OfflinePlayer cannot be null");
        return this.hasEntry(player.getName());
    }

    public boolean hasEntry(String entry) throws IllegalArgumentException, IllegalStateException {
        Preconditions.checkArgument(entry != null, "Entry cannot be null");
        this.checkState();
        return this.team.getPlayers().contains(entry);
    }

    public void unregister() {
        CraftScoreboard scoreboard = this.checkState();

        scoreboard.board.removePlayerTeam(this.team);
    }

    public OptionStatus getOption(Option option) {
        this.checkState();
        switch ($SWITCH_TABLE$org$bukkit$scoreboard$Team$Option()[option.ordinal()]) {
            case 1:
                return OptionStatus.values()[this.team.getNameTagVisibility().ordinal()];
            case 2:
                return OptionStatus.values()[this.team.getDeathMessageVisibility().ordinal()];
            case 3:
                return OptionStatus.values()[this.team.getCollisionRule().ordinal()];
            default:
                throw new IllegalArgumentException("Unrecognised option " + option);
        }
    }

    public void setOption(Option option, OptionStatus status) {
        this.checkState();
        switch ($SWITCH_TABLE$org$bukkit$scoreboard$Team$Option()[option.ordinal()]) {
            case 1:
                this.team.setNameTagVisibility(net.minecraft.world.scores.Team.Visibility.values()[status.ordinal()]);
                break;
            case 2:
                this.team.setDeathMessageVisibility(net.minecraft.world.scores.Team.Visibility.values()[status.ordinal()]);
                break;
            case 3:
                this.team.setCollisionRule(net.minecraft.world.scores.Team.CollisionRule.values()[status.ordinal()]);
                break;
            default:
                throw new IllegalArgumentException("Unrecognised option " + option);
        }

    }

    public static net.minecraft.world.scores.Team.Visibility bukkitToNotch(NameTagVisibility visibility) {
        switch ($SWITCH_TABLE$org$bukkit$scoreboard$NameTagVisibility()[visibility.ordinal()]) {
            case 1:
                return net.minecraft.world.scores.Team.Visibility.ALWAYS;
            case 2:
                return net.minecraft.world.scores.Team.Visibility.NEVER;
            case 3:
                return net.minecraft.world.scores.Team.Visibility.HIDE_FOR_OTHER_TEAMS;
            case 4:
                return net.minecraft.world.scores.Team.Visibility.HIDE_FOR_OWN_TEAM;
            default:
                throw new IllegalArgumentException("Unknown visibility level " + visibility);
        }
    }

    public static NameTagVisibility notchToBukkit(net.minecraft.world.scores.Team.Visibility visibility) {
        switch ($SWITCH_TABLE$net$minecraft$world$scores$ScoreboardTeamBase$EnumNameTagVisibility()[visibility.ordinal()]) {
            case 1:
                return NameTagVisibility.ALWAYS;
            case 2:
                return NameTagVisibility.NEVER;
            case 3:
                return NameTagVisibility.HIDE_FOR_OTHER_TEAMS;
            case 4:
                return NameTagVisibility.HIDE_FOR_OWN_TEAM;
            default:
                throw new IllegalArgumentException("Unknown visibility level " + visibility);
        }
    }

    CraftScoreboard checkState() {
        Preconditions.checkState(this.getScoreboard().board.getPlayerTeam(this.team.getName()) != null, "Unregistered scoreboard component");
        return this.getScoreboard();
    }

    public int hashCode() {
        byte hash = 7;
        int hash = 43 * hash + (this.team != null ? this.team.hashCode() : 0);

        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            CraftTeam other = (CraftTeam) obj;

            return this.team == other.team || this.team != null && this.team.equals(other.team);
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$scoreboard$Team$Option() {
        int[] aint = CraftTeam.$SWITCH_TABLE$org$bukkit$scoreboard$Team$Option;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Option.values().length];

            try {
                aint1[Option.COLLISION_RULE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Option.DEATH_MESSAGE_VISIBILITY.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Option.NAME_TAG_VISIBILITY.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftTeam.$SWITCH_TABLE$org$bukkit$scoreboard$Team$Option = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$scoreboard$NameTagVisibility() {
        int[] aint = CraftTeam.$SWITCH_TABLE$org$bukkit$scoreboard$NameTagVisibility;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[NameTagVisibility.values().length];

            try {
                aint1[NameTagVisibility.ALWAYS.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[NameTagVisibility.HIDE_FOR_OTHER_TEAMS.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[NameTagVisibility.HIDE_FOR_OWN_TEAM.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[NameTagVisibility.NEVER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            CraftTeam.$SWITCH_TABLE$org$bukkit$scoreboard$NameTagVisibility = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$scores$ScoreboardTeamBase$EnumNameTagVisibility() {
        int[] aint = CraftTeam.$SWITCH_TABLE$net$minecraft$world$scores$ScoreboardTeamBase$EnumNameTagVisibility;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[net.minecraft.world.scores.Team.Visibility.values().length];

            try {
                aint1[net.minecraft.world.scores.Team.Visibility.ALWAYS.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[net.minecraft.world.scores.Team.Visibility.HIDE_FOR_OTHER_TEAMS.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[net.minecraft.world.scores.Team.Visibility.HIDE_FOR_OWN_TEAM.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[net.minecraft.world.scores.Team.Visibility.NEVER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            CraftTeam.$SWITCH_TABLE$net$minecraft$world$scores$ScoreboardTeamBase$EnumNameTagVisibility = aint1;
            return aint1;
        }
    }
}
