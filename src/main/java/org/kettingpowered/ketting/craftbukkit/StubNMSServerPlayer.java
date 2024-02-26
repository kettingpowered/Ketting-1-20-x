package org.kettingpowered.ketting.craftbukkit;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class StubNMSServerPlayer extends ServerPlayer {
    public static final StubNMSServerPlayer INSTANCE = new StubNMSServerPlayer(
            DedicatedServer.getServer(),
            DedicatedServer.getServer().overworld(),
            new GameProfile(new UUID(0, 0), "test-mock-player")
    );
    private StubNMSServerPlayer(MinecraftServer p_254143_, ServerLevel p_254435_, GameProfile p_253651_){
        super(p_254143_, p_254435_, p_253651_);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
