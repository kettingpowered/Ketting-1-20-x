package org.bukkit.craftbukkit.v1_20_R2.util;

import net.minecraft.server.MinecraftServer;
import org.spigotmc.AsyncCatcher;

public class ServerShutdownThread extends Thread {

    private final MinecraftServer server;

    public ServerShutdownThread(MinecraftServer server) {
        this.server = server;
    }

    public void run() {
        try {
            AsyncCatcher.enabled = false;
            this.server.close();
        } finally {
            try {
                this.server.reader.getTerminal().restore();
            } catch (Exception exception) {
                ;
            }

        }

    }
}
