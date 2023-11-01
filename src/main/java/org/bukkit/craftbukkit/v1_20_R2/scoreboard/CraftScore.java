package org.bukkit.craftbukkit.v1_20_R2.scoreboard;

import java.util.Map;
import net.minecraft.world.scores.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

final class CraftScore implements Score {

    private final String entry;
    private final CraftObjective objective;

    CraftScore(CraftObjective objective, String entry) {
        this.objective = objective;
        this.entry = entry;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.entry);
    }

    public String getEntry() {
        return this.entry;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public int getScore() {
        Scoreboard board = this.objective.checkState().board;

        if (board.getTrackedPlayers().contains(this.entry)) {
            Map scores = board.getPlayerScores(this.entry);
            net.minecraft.world.scores.Score score = (net.minecraft.world.scores.Score) scores.get(this.objective.getHandle());

            if (score != null) {
                return score.getScore();
            }
        }

        return 0;
    }

    public void setScore(int score) {
        this.objective.checkState().board.getOrCreatePlayerScore(this.entry, this.objective.getHandle()).setScore(score);
    }

    public boolean isScoreSet() {
        Scoreboard board = this.objective.checkState().board;

        return board.getTrackedPlayers().contains(this.entry) && board.getPlayerScores(this.entry).containsKey(this.objective.getHandle());
    }

    public CraftScoreboard getScoreboard() {
        return this.objective.getScoreboard();
    }
}
