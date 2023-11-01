package org.bukkit.craftbukkit.v1_20_R2.help;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.MultipleCommandAlias;
import org.bukkit.help.HelpTopic;

public class MultipleCommandAliasHelpTopic extends HelpTopic {

    private final MultipleCommandAlias alias;

    public MultipleCommandAliasHelpTopic(MultipleCommandAlias alias) {
        this.alias = alias;
        this.name = "/" + alias.getLabel();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < alias.getCommands().length; ++i) {
            if (i != 0) {
                sb.append(ChatColor.GOLD + " > " + ChatColor.WHITE);
            }

            sb.append("/");
            sb.append(alias.getCommands()[i].getLabel());
        }

        this.shortText = sb.toString();
        this.fullText = ChatColor.GOLD + "Alias for: " + ChatColor.WHITE + this.getShortText();
    }

    public boolean canSee(CommandSender sender) {
        if (this.amendedPermission != null) {
            return sender.hasPermission(this.amendedPermission);
        } else if (sender instanceof ConsoleCommandSender) {
            return true;
        } else {
            Command[] acommand;
            int i = (acommand = this.alias.getCommands()).length;

            for (int j = 0; j < i; ++j) {
                Command command = acommand[j];

                if (!command.testPermissionSilent(sender)) {
                    return false;
                }
            }

            return true;
        }
    }
}
