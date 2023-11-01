package org.bukkit.craftbukkit.v1_20_R2.command;

import java.util.Set;
import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandSender.Spigot;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

public class ProxiedNativeCommandSender implements ProxiedCommandSender {

    private final CommandSourceStack orig;
    private final CommandSender caller;
    private final CommandSender callee;

    public ProxiedNativeCommandSender(CommandSourceStack orig, CommandSender caller, CommandSender callee) {
        this.orig = orig;
        this.caller = caller;
        this.callee = callee;
    }

    public CommandSourceStack getHandle() {
        return this.orig;
    }

    public CommandSender getCaller() {
        return this.caller;
    }

    public CommandSender getCallee() {
        return this.callee;
    }

    public void sendMessage(String message) {
        this.getCaller().sendMessage(message);
    }

    public void sendMessage(String... messages) {
        this.getCaller().sendMessage(messages);
    }

    public void sendMessage(UUID sender, String message) {
        this.getCaller().sendMessage(sender, message);
    }

    public void sendMessage(UUID sender, String... messages) {
        this.getCaller().sendMessage(sender, messages);
    }

    public Server getServer() {
        return this.getCallee().getServer();
    }

    public String getName() {
        return this.getCallee().getName();
    }

    public boolean isPermissionSet(String name) {
        return this.getCaller().isPermissionSet(name);
    }

    public boolean isPermissionSet(Permission perm) {
        return this.getCaller().isPermissionSet(perm);
    }

    public boolean hasPermission(String name) {
        return this.getCaller().hasPermission(name);
    }

    public boolean hasPermission(Permission perm) {
        return this.getCaller().hasPermission(perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return this.getCaller().addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return this.getCaller().addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return this.getCaller().addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return this.getCaller().addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        this.getCaller().removeAttachment(attachment);
    }

    public void recalculatePermissions() {
        this.getCaller().recalculatePermissions();
    }

    public Set getEffectivePermissions() {
        return this.getCaller().getEffectivePermissions();
    }

    public boolean isOp() {
        return this.getCaller().isOp();
    }

    public void setOp(boolean value) {
        this.getCaller().setOp(value);
    }

    public Spigot spigot() {
        return this.getCaller().spigot();
    }
}
