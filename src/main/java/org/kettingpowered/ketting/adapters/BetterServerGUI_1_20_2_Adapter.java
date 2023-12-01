package org.kettingpowered.ketting.adapters;

import com.mojang.logging.LogQueues;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.server.dedicated.DedicatedServer;
import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.adapter.BetterServerGUIAdapter;
import org.kettingpowered.ketting.common.betterservergui.BetterServerGUI;
import org.kettingpowered.ketting.types.Player;

import javax.swing.*;
import java.util.List;

public class BetterServerGUI_1_20_2_Adapter implements BetterServerGUIAdapter {

    private final DedicatedServer server;

    public BetterServerGUI_1_20_2_Adapter(@NotNull DedicatedServer server) {
        this.server = server;
    }

    public void startAppender(BetterServerGUI instance) {
        Thread logAppenderThread = new Thread(() -> {
            String s;
            while ((s = LogQueues.getNextLogEvent("ServerGuiConsole")) != null) {
                instance.print(s);
            }
        });
        logAppenderThread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(BetterServerGUI.LOGGER));
        logAppenderThread.setDaemon(true);
        logAppenderThread.start();
    }

    public String getMcVersion() {
        return "1.20.2";
    }

    public void onWindowClosing() {}

    public void onStart() {}

    public void onCommand(String command) {
        this.server.handleConsoleInput(command, this.server.createCommandSourceStack());
    }

    public void addComponents(JComponent mainWindow) {}

    public List<Player> getPlayers() {
        return server.getPlayerList()
                .getPlayers()
                .stream()
                .map(sPlayer -> new Player(sPlayer.getDisplayName().getString(), sPlayer.getUUID(), sPlayer.getIpAddress(), () -> server.getPlayerList().isOp(sPlayer.getGameProfile())))
                .toList();
    }

    public void addRightClickMenuItems(JPopupMenu menu, Player selectedPlayer) {}
}
