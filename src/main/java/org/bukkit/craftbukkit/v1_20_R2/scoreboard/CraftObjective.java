package org.bukkit.craftbukkit.v1_20_R2.scoreboard;

import com.google.common.base.Preconditions;
import net.minecraft.world.scores.Scoreboard;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;

final class CraftObjective extends CraftScoreboardComponent implements Objective {

    private final net.minecraft.world.scores.Objective objective;
    private final CraftCriteria criteria;

    CraftObjective(CraftScoreboard scoreboard, net.minecraft.world.scores.Objective objective) {
        super(scoreboard);
        this.objective = objective;
        this.criteria = CraftCriteria.getFromNMS(objective);
    }

    net.minecraft.world.scores.Objective getHandle() {
        return this.objective;
    }

    public String getName() {
        this.checkState();
        return this.objective.getName();
    }

    public String getDisplayName() {
        this.checkState();
        return CraftChatMessage.fromComponent(this.objective.getDisplayName());
    }

    public void setDisplayName(String displayName) {
        Preconditions.checkArgument(displayName != null, "Display name cannot be null");
        this.checkState();
        this.objective.setDisplayName(CraftChatMessage.fromString(displayName)[0]);
    }

    public String getCriteria() {
        this.checkState();
        return this.criteria.bukkitName;
    }

    public Criteria getTrackedCriteria() {
        this.checkState();
        return this.criteria;
    }

    public boolean isModifiable() {
        this.checkState();
        return !this.criteria.criteria.isReadOnly();
    }

    public void setDisplaySlot(DisplaySlot slot) {
        CraftScoreboard scoreboard = this.checkState();
        Scoreboard board = scoreboard.board;
        net.minecraft.world.scores.Objective objective = this.objective;
        net.minecraft.world.scores.DisplaySlot[] anet_minecraft_world_scores_displayslot;
        int i = (anet_minecraft_world_scores_displayslot = net.minecraft.world.scores.DisplaySlot.values()).length;

        net.minecraft.world.scores.DisplaySlot slotNumber;

        for (int j = 0; j < i; ++j) {
            slotNumber = anet_minecraft_world_scores_displayslot[j];
            if (board.getDisplayObjective(slotNumber) == objective) {
                board.setDisplayObjective(slotNumber, (net.minecraft.world.scores.Objective) null);
            }
        }

        if (slot != null) {
            slotNumber = CraftScoreboardTranslations.fromBukkitSlot(slot);
            board.setDisplayObjective(slotNumber, this.getHandle());
        }

    }

    public DisplaySlot getDisplaySlot() {
        CraftScoreboard scoreboard = this.checkState();
        Scoreboard board = scoreboard.board;
        net.minecraft.world.scores.Objective objective = this.objective;
        net.minecraft.world.scores.DisplaySlot[] anet_minecraft_world_scores_displayslot;
        int i = (anet_minecraft_world_scores_displayslot = net.minecraft.world.scores.DisplaySlot.values()).length;

        for (int j = 0; j < i; ++j) {
            net.minecraft.world.scores.DisplaySlot i = anet_minecraft_world_scores_displayslot[j];

            if (board.getDisplayObjective(i) == objective) {
                return CraftScoreboardTranslations.toBukkitSlot(i);
            }
        }

        return null;
    }

    public void setRenderType(RenderType renderType) {
        Preconditions.checkArgument(renderType != null, "RenderType cannot be null");
        this.checkState();
        this.objective.setRenderType(CraftScoreboardTranslations.fromBukkitRender(renderType));
    }

    public RenderType getRenderType() {
        this.checkState();
        return CraftScoreboardTranslations.toBukkitRender(this.objective.getRenderType());
    }

    public Score getScore(OfflinePlayer player) {
        Preconditions.checkArgument(player != null, "Player cannot be null");
        this.checkState();
        return new CraftScore(this, player.getName());
    }

    public Score getScore(String entry) {
        Preconditions.checkArgument(entry != null, "Entry cannot be null");
        Preconditions.checkArgument(entry.length() <= 32767, "Score '" + entry + "' is longer than the limit of 32767 characters");
        this.checkState();
        return new CraftScore(this, entry);
    }

    public void unregister() {
        CraftScoreboard scoreboard = this.checkState();

        scoreboard.board.removeObjective(this.objective);
    }

    CraftScoreboard checkState() {
        Preconditions.checkState(this.getScoreboard().board.getObjective(this.objective.getName()) != null, "Unregistered scoreboard component");
        return this.getScoreboard();
    }

    public int hashCode() {
        byte hash = 7;
        int hash = 31 * hash + (this.objective != null ? this.objective.hashCode() : 0);

        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            CraftObjective other = (CraftObjective) obj;

            return this.objective == other.objective || this.objective != null && this.objective.equals(other.objective);
        }
    }
}
