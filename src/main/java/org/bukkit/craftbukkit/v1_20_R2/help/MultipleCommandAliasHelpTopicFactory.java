package org.bukkit.craftbukkit.v1_20_R2.help;

import org.bukkit.command.MultipleCommandAlias;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicFactory;

public class MultipleCommandAliasHelpTopicFactory implements HelpTopicFactory {

    public HelpTopic createTopic(MultipleCommandAlias multipleCommandAlias) {
        return new MultipleCommandAliasHelpTopic(multipleCommandAlias);
    }
}
