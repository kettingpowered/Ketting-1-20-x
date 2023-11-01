package org.bukkit.craftbukkit.v1_20_R2.util.permissions;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;

public final class CraftDefaultPermissions {

    private static final String ROOT = "minecraft";

    private CraftDefaultPermissions() {}

    public static void registerCorePermissions() {
        Permission parent = DefaultPermissions.registerPermission("minecraft", "Gives the user the ability to use all vanilla utilities and commands");

        CommandPermissions.registerPermissions(parent);
        DefaultPermissions.registerPermission("minecraft.nbt.place", "Gives the user the ability to place restricted blocks with NBT in creative", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("minecraft.nbt.copy", "Gives the user the ability to copy NBT in creative", PermissionDefault.TRUE, parent);
        DefaultPermissions.registerPermission("minecraft.debugstick", "Gives the user the ability to use the debug stick in creative", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("minecraft.debugstick.always", "Gives the user the ability to use the debug stick in all game modes", PermissionDefault.FALSE, parent);
        parent.recalculatePermissibles();
    }
}
