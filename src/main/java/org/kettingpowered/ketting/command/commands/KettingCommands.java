package org.kettingpowered.ketting.command.commands;

import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.command.defaults.BukkitCommand;
import org.kettingpowered.ketting.core.Ketting;
import org.kettingpowered.ketting.internal.KettingConstants;

import java.util.ArrayList;
import java.util.List;

public class KettingCommands {

    private static final List<BukkitCommand> commands = new ArrayList<>();

    static {
        commands.add(new ModsCommand());
    }

    public static void registerCommands(DedicatedServer server) {
        commands.forEach(cmd -> server.server.getCommandMap().register(cmd.getName(), KettingConstants.NAME.toLowerCase(), cmd));
        Ketting.LOGGER.info("Registered {} commands", commands.size());
    }
}
