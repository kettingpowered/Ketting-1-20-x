package org.bukkit.craftbukkit.v1_20_R2.command;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftMinecartCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.CommandMinecart;

public final class VanillaCommandWrapper extends BukkitCommand {

    private final Commands dispatcher;
    public final CommandNode vanillaCommand;

    public VanillaCommandWrapper(Commands dispatcher, CommandNode vanillaCommand) {
        super(vanillaCommand.getName(), "A Mojang provided command.", vanillaCommand.getUsageText(), Collections.EMPTY_LIST);
        this.dispatcher = dispatcher;
        this.vanillaCommand = vanillaCommand;
        this.setPermission(getPermission(vanillaCommand));
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        } else {
            CommandSourceStack icommandlistener = getListener(sender);

            this.dispatcher.performPrefixedCommand(icommandlistener, this.toDispatcher(args, this.getName()), this.toDispatcher(args, commandLabel));
            return true;
        }
    }

    public List tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
        Preconditions.checkArgument(sender != null, "Sender cannot be null");
        Preconditions.checkArgument(args != null, "Arguments cannot be null");
        Preconditions.checkArgument(alias != null, "Alias cannot be null");
        CommandSourceStack icommandlistener = getListener(sender);
        ParseResults parsed = this.dispatcher.getDispatcher().parse(this.toDispatcher(args, this.getName()), icommandlistener);
        ArrayList results = new ArrayList();

        this.dispatcher.getDispatcher().getCompletionSuggestions(parsed).thenAccept((suggestionsx) -> {
            suggestionsx.getList().forEach((sx) -> {
                results.add(sx.getText());
            });
        });
        return results;
    }

    public static CommandSourceStack getListener(CommandSender sender) {
        if (sender instanceof Entity) {
            return sender instanceof CommandMinecart ? ((CraftMinecartCommand) sender).getHandle().getCommandBlock().createCommandSourceStack() : ((CraftEntity) sender).getHandle().createCommandSourceStack();
        } else if (sender instanceof BlockCommandSender) {
            return ((CraftBlockCommandSender) sender).getWrapper();
        } else if (sender instanceof RemoteConsoleCommandSender) {
            return ((CraftRemoteConsoleCommandSender) sender).getListener().createCommandSourceStack();
        } else if (sender instanceof ConsoleCommandSender) {
            return ((CraftServer) sender.getServer()).getServer().createCommandSourceStack();
        } else if (sender instanceof ProxiedCommandSender) {
            return ((ProxiedNativeCommandSender) sender).getHandle();
        } else {
            throw new IllegalArgumentException("Cannot make " + sender + " a vanilla command listener");
        }
    }

    public static String getPermission(CommandNode vanillaCommand) {
        return "minecraft.command." + (vanillaCommand.getRedirect() == null ? vanillaCommand.getName() : vanillaCommand.getRedirect().getName());
    }

    private String toDispatcher(String[] args, String name) {
        return name + (args.length > 0 ? " " + Joiner.on(' ').join(args) : "");
    }
}
