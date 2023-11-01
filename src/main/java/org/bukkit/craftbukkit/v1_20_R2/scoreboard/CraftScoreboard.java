package org.bukkit.craftbukkit.v1_20_R2.scoreboard;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Iterables;
import java.util.Iterator;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class CraftScoreboard implements Scoreboard {

    final net.minecraft.world.scores.Scoreboard board;

    CraftScoreboard(net.minecraft.world.scores.Scoreboard board) {
        this.board = board;
    }

    public CraftObjective registerNewObjective(String name, String criteria) {
        return this.registerNewObjective(name, criteria, name);
    }

    public CraftObjective registerNewObjective(String name, String criteria, String displayName) {
        return this.registerNewObjective(name, (Criteria) CraftCriteria.getFromBukkit(criteria), displayName, RenderType.INTEGER);
    }

    public CraftObjective registerNewObjective(String name, String criteria, String displayName, RenderType renderType) {
        return this.registerNewObjective(name, (Criteria) CraftCriteria.getFromBukkit(criteria), displayName, renderType);
    }

    public CraftObjective registerNewObjective(String name, Criteria criteria, String displayName) {
        return this.registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
    }

    public CraftObjective registerNewObjective(String name, Criteria criteria, String displayName, RenderType renderType) {
        Preconditions.checkArgument(name != null, "Objective name cannot be null");
        Preconditions.checkArgument(criteria != null, "Criteria cannot be null");
        Preconditions.checkArgument(displayName != null, "Display name cannot be null");
        Preconditions.checkArgument(renderType != null, "RenderType cannot be null");
        Preconditions.checkArgument(name.length() <= 32767, "The name '%s' is longer than the limit of 32767 characters (%s)", name, name.length());
        Preconditions.checkArgument(this.board.getObjective(name) == null, "An objective of name '%s' already exists", name);
        Objective objective = this.board.addObjective(name, ((CraftCriteria) criteria).criteria, CraftChatMessage.fromStringOrNull(displayName), CraftScoreboardTranslations.fromBukkitRender(renderType));

        return new CraftObjective(this, objective);
    }

    public org.bukkit.scoreboard.Objective getObjective(String name) {
        Preconditions.checkArgument(name != null, "Objective name cannot be null");
        Objective nms = this.board.getObjective(name);

        return nms == null ? null : new CraftObjective(this, nms);
    }

    public ImmutableSet getObjectivesByCriteria(String criteria) {
        Preconditions.checkArgument(criteria != null, "Criteria name cannot be null");
        Builder objectives = ImmutableSet.builder();
        Iterator iterator = this.board.getObjectives().iterator();

        while (iterator.hasNext()) {
            Objective netObjective = (Objective) iterator.next();
            CraftObjective objective = new CraftObjective(this, netObjective);

            if (objective.getCriteria().equals(criteria)) {
                objectives.add(objective);
            }
        }

        return objectives.build();
    }

    public ImmutableSet getObjectivesByCriteria(Criteria criteria) {
        Preconditions.checkArgument(criteria != null, "Criteria cannot be null");
        Builder objectives = ImmutableSet.builder();
        Iterator iterator = this.board.getObjectives().iterator();

        while (iterator.hasNext()) {
            Objective netObjective = (Objective) iterator.next();
            CraftObjective objective = new CraftObjective(this, netObjective);

            if (objective.getTrackedCriteria().equals(criteria)) {
                objectives.add(objective);
            }
        }

        return objectives.build();
    }

    public ImmutableSet getObjectives() {
        return ImmutableSet.copyOf(Iterables.transform(this.board.getObjectives(), (inputx) -> {
            return new CraftObjective(this, inputx);
        }));
    }

    public org.bukkit.scoreboard.Objective getObjective(DisplaySlot slot) {
        Preconditions.checkArgument(slot != null, "Display slot cannot be null");
        Objective objective = this.board.getDisplayObjective(CraftScoreboardTranslations.fromBukkitSlot(slot));

        return objective == null ? null : new CraftObjective(this, objective);
    }

    public ImmutableSet getScores(OfflinePlayer player) {
        Preconditions.checkArgument(player != null, "OfflinePlayer cannot be null");
        return this.getScores(player.getName());
    }

    public ImmutableSet getScores(String entry) {
        Preconditions.checkArgument(entry != null, "Entry cannot be null");
        Builder scores = ImmutableSet.builder();
        Iterator iterator = this.board.getObjectives().iterator();

        while (iterator.hasNext()) {
            Objective objective = (Objective) iterator.next();

            scores.add(new CraftScore(new CraftObjective(this, objective), entry));
        }

        return scores.build();
    }

    public void resetScores(OfflinePlayer player) {
        Preconditions.checkArgument(player != null, "OfflinePlayer cannot be null");
        this.resetScores(player.getName());
    }

    public void resetScores(String entry) {
        Preconditions.checkArgument(entry != null, "Entry cannot be null");
        Iterator iterator = this.board.getObjectives().iterator();

        while (iterator.hasNext()) {
            Objective objective = (Objective) iterator.next();

            this.board.resetPlayerScore(entry, objective);
        }

    }

    public Team getPlayerTeam(OfflinePlayer player) {
        Preconditions.checkArgument(player != null, "OfflinePlayer cannot be null");
        PlayerTeam team = this.board.getPlayersTeam(player.getName());

        return team == null ? null : new CraftTeam(this, team);
    }

    public Team getEntryTeam(String entry) {
        Preconditions.checkArgument(entry != null, "Entry cannot be null");
        PlayerTeam team = this.board.getPlayersTeam(entry);

        return team == null ? null : new CraftTeam(this, team);
    }

    public Team getTeam(String teamName) {
        Preconditions.checkArgument(teamName != null, "Team name cannot be null");
        PlayerTeam team = this.board.getPlayerTeam(teamName);

        return team == null ? null : new CraftTeam(this, team);
    }

    public ImmutableSet getTeams() {
        return ImmutableSet.copyOf(Iterables.transform(this.board.getPlayerTeams(), (inputx) -> {
            return new CraftTeam(this, inputx);
        }));
    }

    public Team registerNewTeam(String name) {
        Preconditions.checkArgument(name != null, "Team name cannot be null");
        Preconditions.checkArgument(name.length() <= 32767, "Team name '%s' is longer than the limit of 32767 characters (%s)", name, name.length());
        Preconditions.checkArgument(this.board.getPlayerTeam(name) == null, "Team name '%s' is already in use", name);
        return new CraftTeam(this, this.board.addPlayerTeam(name));
    }

    public ImmutableSet getPlayers() {
        Builder players = ImmutableSet.builder();
        Iterator iterator = this.board.getTrackedPlayers().iterator();

        while (iterator.hasNext()) {
            Object playerName = iterator.next();

            players.add(Bukkit.getOfflinePlayer(playerName.toString()));
        }

        return players.build();
    }

    public ImmutableSet getEntries() {
        Builder entries = ImmutableSet.builder();
        Iterator iterator = this.board.getTrackedPlayers().iterator();

        while (iterator.hasNext()) {
            Object entry = iterator.next();

            entries.add(entry.toString());
        }

        return entries.build();
    }

    public void clearSlot(DisplaySlot slot) {
        Preconditions.checkArgument(slot != null, "Slot cannot be null");
        this.board.setDisplayObjective(CraftScoreboardTranslations.fromBukkitSlot(slot), (Objective) null);
    }

    public net.minecraft.world.scores.Scoreboard getHandle() {
        return this.board;
    }
}
