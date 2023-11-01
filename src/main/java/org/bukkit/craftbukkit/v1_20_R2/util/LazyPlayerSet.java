package org.bukkit.craftbukkit.v1_20_R2.util;

import com.google.common.base.Preconditions;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class LazyPlayerSet extends LazyHashSet {

    private final MinecraftServer server;

    public LazyPlayerSet(MinecraftServer server) {
        this.server = server;
    }

    HashSet makeReference() {
        Preconditions.checkState(this.reference == null, "Reference already created!");
        List players = this.server.getPlayerList().players;
        HashSet reference = new HashSet(players.size());
        Iterator iterator = players.iterator();

        while (iterator.hasNext()) {
            ServerPlayer player = (ServerPlayer) iterator.next();

            reference.add(player.getBukkitEntity());
        }

        return reference;
    }
}
