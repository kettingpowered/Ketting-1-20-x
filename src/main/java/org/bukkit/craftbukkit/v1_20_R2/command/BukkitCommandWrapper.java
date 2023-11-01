package org.bukkit.craftbukkit.v1_20_R2.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.logging.Level;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;

public class BukkitCommandWrapper implements Command, Predicate, SuggestionProvider {

    private final CraftServer server;
    private final org.bukkit.command.Command command;

    public BukkitCommandWrapper(CraftServer server, org.bukkit.command.Command command) {
        this.server = server;
        this.command = command;
    }

    public LiteralCommandNode register(CommandDispatcher dispatcher, String label) {
        return dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder) ((LiteralArgumentBuilder) LiteralArgumentBuilder.literal(label).requires(this)).executes(this)).then(RequiredArgumentBuilder.argument("args", StringArgumentType.greedyString()).suggests(this).executes(this)));
    }

    public boolean test(CommandSourceStack wrapper) {
        return this.command.testPermissionSilent(wrapper.getBukkitSender());
    }

    public int run(CommandContext context) throws CommandSyntaxException {
        CommandSender sender = ((CommandSourceStack) context.getSource()).getBukkitSender();

        try {
            return this.server.dispatchCommand(sender, context.getInput()) ? 1 : 0;
        } catch (CommandException commandexception) {
            sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
            this.server.getLogger().log(Level.SEVERE, (String) null, commandexception);
            return 0;
        }
    }

    public CompletableFuture getSuggestions(CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        List results = this.server.tabComplete(((CommandSourceStack) context.getSource()).getBukkitSender(), builder.getInput(), ((CommandSourceStack) context.getSource()).getLevel(), ((CommandSourceStack) context.getSource()).getPosition(), true);

        builder = builder.createOffset(builder.getInput().lastIndexOf(32) + 1);
        Iterator iterator = results.iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            builder.suggest(s);
        }

        return builder.buildFuture();
    }
}
