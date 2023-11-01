package org.bukkit.craftbukkit.v1_20_R2.scoreboard;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.util.WeakCollection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import org.spigotmc.AsyncCatcher;

public final class CraftScoreboardManager implements ScoreboardManager {

    private final CraftScoreboard mainScoreboard;
    private final MinecraftServer server;
    private final Collection scoreboards = new WeakCollection();
    private final Map playerBoards = new HashMap();

    public CraftScoreboardManager(MinecraftServer minecraftserver, Scoreboard scoreboardServer) {
        this.mainScoreboard = new CraftScoreboard(scoreboardServer);
        this.server = minecraftserver;
        this.scoreboards.add(this.mainScoreboard);
    }

    public CraftScoreboard getMainScoreboard() {
        return this.mainScoreboard;
    }

    public CraftScoreboard getNewScoreboard() {
        AsyncCatcher.catchOp("scoreboard creation");
        CraftScoreboard scoreboard = new CraftScoreboard(new ServerScoreboard(this.server));

        this.scoreboards.add(scoreboard);
        return scoreboard;
    }

    public CraftScoreboard getPlayerBoard(CraftPlayer player) {
        CraftScoreboard board = (CraftScoreboard) this.playerBoards.get(player);

        return board == null ? this.getMainScoreboard() : board;
    }

    public void setPlayerBoard(CraftPlayer player, org.bukkit.scoreboard.Scoreboard bukkitScoreboard) {
        Preconditions.checkArgument(bukkitScoreboard instanceof CraftScoreboard, "Cannot set player scoreboard to an unregistered Scoreboard");
        CraftScoreboard scoreboard = (CraftScoreboard) bukkitScoreboard;
        Scoreboard oldboard = this.getPlayerBoard(player).getHandle();
        Scoreboard newboard = scoreboard.getHandle();
        ServerPlayer entityplayer = player.getHandle();

        if (oldboard != newboard) {
            if (scoreboard == this.mainScoreboard) {
                this.playerBoards.remove(player);
            } else {
                this.playerBoards.put(player, scoreboard);
            }

            HashSet removed = new HashSet();

            for (int i = 0; i < 3; ++i) {
                Objective scoreboardobjective = oldboard.getDisplayObjective((DisplaySlot) DisplaySlot.BY_ID.apply(i));

                if (scoreboardobjective != null && !removed.contains(scoreboardobjective)) {
                    entityplayer.connection.send(new ClientboundSetObjectivePacket(scoreboardobjective, 1));
                    removed.add(scoreboardobjective);
                }
            }

            Iterator iterator = oldboard.getPlayerTeams().iterator();

            while (iterator.hasNext()) {
                PlayerTeam scoreboardteam = (PlayerTeam) iterator.next();

                entityplayer.connection.send(ClientboundSetPlayerTeamPacket.createRemovePacket(scoreboardteam));
            }

            this.server.getPlayerList().updateEntireScoreboard((ServerScoreboard) newboard, player.getHandle());
        }
    }

    public void removePlayer(Player player) {
        this.playerBoards.remove(player);
    }

    public void getScoreboardScores(ObjectiveCriteria criteria, String name, Consumer consumer) {
        Iterator iterator = this.scoreboards.iterator();

        while (iterator.hasNext()) {
            CraftScoreboard scoreboard = (CraftScoreboard) iterator.next();
            Scoreboard board = scoreboard.board;

            board.forAllObjectives(criteria, name, (scorex) -> {
                consumer.accept(scorex);
            });
        }

    }
}
