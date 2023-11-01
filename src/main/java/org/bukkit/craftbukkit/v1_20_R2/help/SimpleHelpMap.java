package org.bukkit.craftbukkit.v1_20_R2.help;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.MultipleCommandAlias;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.command.VanillaCommandWrapper;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.help.IndexHelpTopic;

public class SimpleHelpMap implements HelpMap {

    private HelpTopic defaultTopic;
    private final Map helpTopics = new TreeMap(HelpTopicComparator.topicNameComparatorInstance());
    private final Map topicFactoryMap = new HashMap();
    private final CraftServer server;
    private HelpYamlReader yaml;

    public SimpleHelpMap(CraftServer server) {
        this.server = server;
        this.yaml = new HelpYamlReader(server);
        Predicate indexFilter = Predicates.not(Predicates.instanceOf(CommandAliasHelpTopic.class));

        if (!this.yaml.commandTopicsInMasterIndex()) {
            indexFilter = Predicates.and(indexFilter, Predicates.not(new SimpleHelpMap.IsCommandTopicPredicate()));
        }

        this.defaultTopic = new IndexHelpTopic("Index", (String) null, (String) null, Collections2.filter(this.helpTopics.values(), indexFilter), "Use /help [n] to get page n of help.");
        this.registerHelpTopicFactory(MultipleCommandAlias.class, new MultipleCommandAliasHelpTopicFactory());
    }

    public synchronized HelpTopic getHelpTopic(String topicName) {
        return topicName.equals("") ? this.defaultTopic : (this.helpTopics.containsKey(topicName) ? (HelpTopic) this.helpTopics.get(topicName) : null);
    }

    public Collection getHelpTopics() {
        return this.helpTopics.values();
    }

    public synchronized void addTopic(HelpTopic topic) {
        if (!this.helpTopics.containsKey(topic.getName())) {
            this.helpTopics.put(topic.getName(), topic);
        }

    }

    public synchronized void clear() {
        this.helpTopics.clear();
    }

    public List getIgnoredPlugins() {
        return this.yaml.getIgnoredPlugins();
    }

    public synchronized void initializeGeneralTopics() {
        this.yaml = new HelpYamlReader(this.server);
        Iterator iterator = this.yaml.getGeneralTopics().iterator();

        HelpTopic topic;

        while (iterator.hasNext()) {
            topic = (HelpTopic) iterator.next();
            this.addTopic(topic);
        }

        iterator = this.yaml.getIndexTopics().iterator();

        while (iterator.hasNext()) {
            topic = (HelpTopic) iterator.next();
            if (topic.getName().equals("Default")) {
                this.defaultTopic = topic;
            } else {
                this.addTopic(topic);
            }
        }

    }

    public synchronized void initializeCommands() {
        HashSet ignoredPlugins = new HashSet(this.yaml.getIgnoredPlugins());

        if (!ignoredPlugins.contains("All")) {
            Iterator iterator = this.server.getCommandMap().getCommands().iterator();

            Command command;
            Iterator iterator1;

            label82:
            while (iterator.hasNext()) {
                command = (Command) iterator.next();
                if (!this.commandInIgnoredPlugin(command, ignoredPlugins)) {
                    iterator1 = this.topicFactoryMap.keySet().iterator();

                    Class c;
                    HelpTopic t;

                    do {
                        if (!iterator1.hasNext()) {
                            this.addTopic(new GenericCommandHelpTopic(command));
                            continue label82;
                        }

                        c = (Class) iterator1.next();
                        if (c.isAssignableFrom(command.getClass())) {
                            t = ((HelpTopicFactory) this.topicFactoryMap.get(c)).createTopic(command);
                            if (t != null) {
                                this.addTopic(t);
                            }
                            continue label82;
                        }
                    } while (!(command instanceof PluginCommand) || !c.isAssignableFrom(((PluginCommand) command).getExecutor().getClass()));

                    t = ((HelpTopicFactory) this.topicFactoryMap.get(c)).createTopic(command);
                    if (t != null) {
                        this.addTopic(t);
                    }
                }
            }

            iterator = this.server.getCommandMap().getCommands().iterator();

            while (iterator.hasNext()) {
                command = (Command) iterator.next();
                if (!this.commandInIgnoredPlugin(command, ignoredPlugins)) {
                    iterator1 = command.getAliases().iterator();

                    while (iterator1.hasNext()) {
                        String alias = (String) iterator1.next();

                        if (this.server.getCommandMap().getCommand(alias) == command) {
                            this.addTopic(new CommandAliasHelpTopic("/" + alias, "/" + command.getLabel(), this));
                        }
                    }
                }
            }

            Collection filteredTopics = Collections2.filter(this.helpTopics.values(), Predicates.instanceOf(CommandAliasHelpTopic.class));

            if (!filteredTopics.isEmpty()) {
                this.addTopic(new IndexHelpTopic("Aliases", "Lists command aliases", (String) null, filteredTopics));
            }

            HashMap pluginIndexes = new HashMap();

            this.fillPluginIndexes(pluginIndexes, this.server.getCommandMap().getCommands());
            iterator1 = pluginIndexes.entrySet().iterator();

            while (iterator1.hasNext()) {
                Entry entry = (Entry) iterator1.next();

                this.addTopic(new IndexHelpTopic((String) entry.getKey(), "All commands for " + (String) entry.getKey(), (String) null, (Collection) entry.getValue(), "Below is a list of all " + (String) entry.getKey() + " commands:"));
            }

            iterator1 = this.yaml.getTopicAmendments().iterator();

            while (iterator1.hasNext()) {
                HelpTopicAmendment amendment = (HelpTopicAmendment) iterator1.next();

                if (this.helpTopics.containsKey(amendment.getTopicName())) {
                    ((HelpTopic) this.helpTopics.get(amendment.getTopicName())).amendTopic(amendment.getShortText(), amendment.getFullText());
                    if (amendment.getPermission() != null) {
                        ((HelpTopic) this.helpTopics.get(amendment.getTopicName())).amendCanSee(amendment.getPermission());
                    }
                }
            }

        }
    }

    private void fillPluginIndexes(Map pluginIndexes, Collection commands) {
        Iterator iterator = commands.iterator();

        while (iterator.hasNext()) {
            Command command = (Command) iterator.next();
            String pluginName = this.getCommandPluginName(command);

            if (pluginName != null) {
                HelpTopic topic = this.getHelpTopic("/" + command.getLabel());

                if (topic != null) {
                    if (!pluginIndexes.containsKey(pluginName)) {
                        pluginIndexes.put(pluginName, new TreeSet(HelpTopicComparator.helpTopicComparatorInstance()));
                    }

                    ((Set) pluginIndexes.get(pluginName)).add(topic);
                }
            }
        }

    }

    private String getCommandPluginName(Command command) {
        return command instanceof VanillaCommandWrapper ? "Minecraft" : (command instanceof BukkitCommand ? "Bukkit" : (command instanceof PluginIdentifiableCommand ? ((PluginIdentifiableCommand) command).getPlugin().getName() : null));
    }

    private boolean commandInIgnoredPlugin(Command command, Set ignoredPlugins) {
        return command instanceof BukkitCommand && ignoredPlugins.contains("Bukkit") ? true : command instanceof PluginIdentifiableCommand && ignoredPlugins.contains(((PluginIdentifiableCommand) command).getPlugin().getName());
    }

    public void registerHelpTopicFactory(Class commandClass, HelpTopicFactory factory) {
        Preconditions.checkArgument(Command.class.isAssignableFrom(commandClass) || CommandExecutor.class.isAssignableFrom(commandClass), "commandClass (%s) must implement either Command or CommandExecutor", commandClass.getName());
        this.topicFactoryMap.put(commandClass, factory);
    }

    private class IsCommandTopicPredicate implements Predicate {

        public boolean apply(HelpTopic topic) {
            return topic.getName().charAt(0) == '/';
        }
    }
}
