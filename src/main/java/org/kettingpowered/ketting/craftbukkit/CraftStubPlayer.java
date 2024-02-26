package org.kettingpowered.ketting.craftbukkit;

import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;

public class CraftStubPlayer extends CraftPlayer {
    public final static CraftStubPlayer INSTANCE = new CraftStubPlayer(DedicatedServer.getServer().server, StubNMSServerPlayer.INSTANCE);
    private CraftStubPlayer(CraftServer server, ServerPlayer player){
        super(server, player);
    }
}
