package org.kettingpowered.ketting.utils;

import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R1.command.VanillaCommandWrapper;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.kettingpowered.ketting.command.ForgeCommand;
import org.kettingpowered.ketting.command.ForgeCommandWrapper;
import org.kettingpowered.ketting.config.KettingConfig;
import org.kettingpowered.ketting.inject.ForgeInject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class PermissionHelper {

    public static final Map<String, PermissionNode.PermissionResolver<Boolean>> MODDED_RESOLVERS = new HashMap<>();

    public static final PermissionDynamicContext<?>[] MODDED_CONTEXT_EMPTY = new PermissionDynamicContext[0];
    public static PermissionDynamicContext<?>[] MODDED_CONTEXT = MODDED_CONTEXT_EMPTY;

    public static boolean getModdedValue(String name, ServerPlayer player) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null");

        PermissionNode.PermissionResolver<Boolean> resolver = MODDED_RESOLVERS.get(name);
        return resolver != null && resolver.resolve(player, player.getUUID(), MODDED_CONTEXT);
    }

    public static void injectPermissions(Set<PermissionNode<?>> nodes) {
        nodes.forEach(node -> {
            if (node.getType() != PermissionTypes.BOOLEAN) return;

            String name = node.getNodeName();
            String description = node.getDescription() == null ? "" : node.getDescription().getString();
            Bukkit.getServer().getPluginManager().addPermission(new Permission(name, description, PermissionDefault.MODDED));
            ForgeInject.debug("Registering boolean permission {} to Bukkit", name);
            MODDED_RESOLVERS.put(name, (PermissionNode.PermissionResolver<Boolean>) node.getDefaultResolver());
        });
    }

    public static boolean handlePermission(CraftPlayer player, String permission, Supplier<Boolean> delegate) {
        Permission perm = Bukkit.getServer().getPluginManager().getPermission(permission);
        return perm == null ? delegate.get() : handlePermission(player, perm, delegate);
    }

    public static boolean handlePermission(CraftPlayer player, Permission permission, Supplier<Boolean> delegate) {
        if (!player.isPermissionSet(permission) && permission.getDefault() == PermissionDefault.MODDED)
            return getModdedValue(permission.getName(), player.getHandle());
        return delegate.get();
    }

    public static boolean handleSourceStackPermission(CommandSourceStack stack, CommandNode node, int permissionLevel) {
        if (ForgeCommand.checkIfForgeCommand(node))
            return KettingConfig.getInstance().REROUTE_FORGE_COMMAND_PERMISSIONS.getValue()
                    ? ((stack.getLevel() == null) && stack.permissionLevel >= permissionLevel) || stack.getBukkitSender().hasPermission(ForgeCommandWrapper.getPermission(node))
                    : stack.permissionLevel >= permissionLevel;
        return stack.hasPermission(permissionLevel, VanillaCommandWrapper.getPermission(node));
    }
}
