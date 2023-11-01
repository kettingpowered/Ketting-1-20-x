package org.bukkit.craftbukkit.v1_20_R2.command;

import java.util.Set;
import java.util.UUID;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandSender.Spigot;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

public abstract class ServerCommandSender implements CommandSender {

    private static PermissibleBase blockPermInst;
    private final PermissibleBase perm;
    private final Spigot spigot = new Spigot() {
        public void sendMessage(BaseComponent component) {
            ServerCommandSender.this.sendMessage(TextComponent.toLegacyText(new BaseComponent[]{component}));
        }

        public void sendMessage(BaseComponent... components) {
            ServerCommandSender.this.sendMessage(TextComponent.toLegacyText(components));
        }

        public void sendMessage(UUID sender, BaseComponent... components) {
            this.sendMessage(components);
        }

        public void sendMessage(UUID sender, BaseComponent component) {
            this.sendMessage(component);
        }
    };

    public ServerCommandSender() {
        if (this instanceof CraftBlockCommandSender) {
            if (ServerCommandSender.blockPermInst == null) {
                ServerCommandSender.blockPermInst = new PermissibleBase(this);
            }

            this.perm = ServerCommandSender.blockPermInst;
        } else {
            this.perm = new PermissibleBase(this);
        }

    }

    public boolean isPermissionSet(String name) {
        return this.perm.isPermissionSet(name);
    }

    public boolean isPermissionSet(Permission perm) {
        return this.perm.isPermissionSet(perm);
    }

    public boolean hasPermission(String name) {
        return this.perm.hasPermission(name);
    }

    public boolean hasPermission(Permission perm) {
        return this.perm.hasPermission(perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return this.perm.addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return this.perm.addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return this.perm.addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return this.perm.addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        this.perm.removeAttachment(attachment);
    }

    public void recalculatePermissions() {
        this.perm.recalculatePermissions();
    }

    public Set getEffectivePermissions() {
        return this.perm.getEffectivePermissions();
    }

    public boolean isPlayer() {
        return false;
    }

    public Server getServer() {
        return Bukkit.getServer();
    }

    public void sendMessage(UUID uuid, String message) {
        this.sendMessage(message);
    }

    public void sendMessage(UUID uuid, String... messages) {
        this.sendMessage(messages);
    }

    public Spigot spigot() {
        return this.spigot;
    }
}
