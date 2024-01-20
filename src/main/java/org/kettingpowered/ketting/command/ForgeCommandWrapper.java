package org.kettingpowered.ketting.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.KettingConfig;

import java.util.List;

import static org.bukkit.craftbukkit.v1_20_R1.command.VanillaCommandWrapper.getListener;

public class ForgeCommandWrapper extends BukkitCommand {

    private final Commands dispatcher;
    public final CommandNode<CommandSourceStack> forgeCommand;

    public ForgeCommandWrapper(Commands dispatcher, CommandNode<CommandSourceStack> forgeCommand) {
        super(forgeCommand.getName(), "A Forge modded command.", forgeCommand.getUsageText(), List.of());
        this.dispatcher = dispatcher;
        this.forgeCommand = forgeCommand;
        this.setPermission(getPermission(forgeCommand));
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        final CommandSourceStack listener = getListener(sender);
        if (KettingConfig.getInstance().OVERWRITE_FORGE_PERMISSIONS.getValue()) {
            //If we detect that the CraftPlayer permissible was injected into (ex. LuckPerms), we will use their permissions
            //instead of the Forge permissions defined in 'Commands.literal(...).requires(s -> s.hasPermission(..))'.
            //This will cause a problem though, as the Forge permissions will not be checked.
            //So you will have to manually set the Forge permissions by allowing ex. "forge.command.coolmodcommand" in LuckPerms.
            boolean permissibleInjected = false;
            if (sender instanceof CraftPlayer player)
                permissibleInjected = player.isPermissibleInjected();

            if ((permissibleInjected && !testPermission(sender)) ||
                    (!permissibleInjected && !forgeCommand.getRequirement().test(listener)))
                return true;
        } else {
            if (!forgeCommand.getRequirement().test(listener)) return true;
        }

        dispatcher.setForgeCommand(true);
        dispatcher.performPrefixedCommand(listener, "/"+toDispatcher(args, getName()), "/"+toDispatcher(args, commandLabel));
        dispatcher.setForgeCommand(false);
        return true;
    }

    public static String getPermission(CommandNode<CommandSourceStack> forgeCommand) {
        return "forge.command." + ((forgeCommand.getRedirect() == null) ? forgeCommand.getName() : forgeCommand.getRedirect().getName());
    }

    private String toDispatcher(String[] args, String name) {
        return name + ((args.length > 0) ? " " + Joiner.on(' ').join(args) : "");
    }
}
