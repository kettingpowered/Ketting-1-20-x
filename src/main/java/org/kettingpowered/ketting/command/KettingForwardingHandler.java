package org.kettingpowered.ketting.command;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.handler.IPermissionHandler;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import org.bukkit.Bukkit;
import org.kettingpowered.ketting.internal.KettingConstants;

import java.util.Set;
import java.util.UUID;

public class KettingForwardingHandler implements IPermissionHandler {

    private final IPermissionHandler delegate;

    public KettingForwardingHandler(IPermissionHandler delegate) {
        this.delegate = delegate;
    }

    public ResourceLocation getIdentifier() {
        return new ResourceLocation(KettingConstants.NAME.toLowerCase(), "permission");
    }

    public Set<PermissionNode<?>> getRegisteredNodes() {
        return delegate.getRegisteredNodes();
    }

    public <T> T getPermission(ServerPlayer player, PermissionNode<T> node, PermissionDynamicContext<?>... context) {
        if (node.getType() == PermissionTypes.BOOLEAN) {
            return (T) (Object) player.getBukkitEntity().hasPermission(node.getNodeName());
        } else {
            return delegate.getPermission(player, node, context);
        }
    }

    public <T> T getOfflinePermission(UUID uuid, PermissionNode<T> node, PermissionDynamicContext<?>... context) {
        var player = Bukkit.getPlayer(uuid);
        if (player != null && node.getType() == PermissionTypes.BOOLEAN) {
            return (T) (Object) player.hasPermission(node.getNodeName());
        } else {
            return delegate.getOfflinePermission(uuid, node, context);
        }
    }
}
