package org.bukkit.craftbukkit.v1_20_R2.help;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.help.HelpTopic;

public class CustomHelpTopic extends HelpTopic {

    private final String permissionNode;

    public CustomHelpTopic(String name, String shortText, String fullText, String permissionNode) {
        this.permissionNode = permissionNode;
        this.name = name;
        this.shortText = shortText;
        this.fullText = shortText + "\n" + fullText;
    }

    public boolean canSee(CommandSender sender) {
        return sender instanceof ConsoleCommandSender ? true : (!this.permissionNode.equals("") ? sender.hasPermission(this.permissionNode) : true);
    }
}
