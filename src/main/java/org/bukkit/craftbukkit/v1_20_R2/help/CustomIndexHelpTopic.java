package org.bukkit.craftbukkit.v1_20_R2.help;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;

public class CustomIndexHelpTopic extends IndexHelpTopic {

    private List futureTopics;
    private final HelpMap helpMap;

    public CustomIndexHelpTopic(HelpMap helpMap, String name, String shortText, String permission, List futureTopics, String preamble) {
        super(name, shortText, permission, new HashSet(), preamble);
        this.helpMap = helpMap;
        this.futureTopics = futureTopics;
    }

    public String getFullText(CommandSender sender) {
        if (this.futureTopics != null) {
            LinkedList topics = new LinkedList();
            Iterator iterator = this.futureTopics.iterator();

            while (iterator.hasNext()) {
                String futureTopic = (String) iterator.next();
                HelpTopic topic = this.helpMap.getHelpTopic(futureTopic);

                if (topic != null) {
                    topics.add(topic);
                }
            }

            this.setTopicsCollection(topics);
            this.futureTopics = null;
        }

        return super.getFullText(sender);
    }
}
