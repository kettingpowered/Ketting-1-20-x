package org.spigotmc;

import net.minecraft.server.MinecraftServer;

public class AsyncCatcher {

    public static boolean enabled = false; //Ketting: disabled by default

    public static void catchOp(String reason) {
        if (AsyncCatcher.enabled && Thread.currentThread() != MinecraftServer.getServer().serverThread) {
            throw new IllegalStateException("Asynchronous " + reason + "!");
        }
    }
}
