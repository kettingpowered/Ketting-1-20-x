package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import jline.console.ConsoleReader;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ConsoleInput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.validation.ContentValidationException;
import net.minecraft.world.phys.Vec3;
import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Fluid;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Server.Spigot;
import org.bukkit.StructureType;
import org.bukkit.Tag;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.Conversable;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_20_R2.ban.CraftIpBanList;
import org.bukkit.craftbukkit.v1_20_R2.ban.CraftProfileBanList;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.boss.CraftBossBar;
import org.bukkit.craftbukkit.v1_20_R2.boss.CraftKeyedBossbar;
import org.bukkit.craftbukkit.v1_20_R2.command.BukkitCommandWrapper;
import org.bukkit.craftbukkit.v1_20_R2.command.CraftCommandMap;
import org.bukkit.craftbukkit.v1_20_R2.command.VanillaCommandWrapper;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_20_R2.generator.CraftWorldInfo;
import org.bukkit.craftbukkit.v1_20_R2.generator.OldCraftChunkData;
import org.bukkit.craftbukkit.v1_20_R2.help.SimpleHelpMap;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftBlastingRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftCampfireRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftMerchantCustom;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftSmithingTransformRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftSmithingTrimRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftSmokingRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftStonecuttingRecipe;
import org.bukkit.craftbukkit.v1_20_R2.inventory.RecipeIterator;
import org.bukkit.craftbukkit.v1_20_R2.inventory.util.CraftInventoryCreator;
import org.bukkit.craftbukkit.v1_20_R2.map.CraftMapColorCache;
import org.bukkit.craftbukkit.v1_20_R2.map.CraftMapView;
import org.bukkit.craftbukkit.v1_20_R2.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.v1_20_R2.metadata.PlayerMetadataStore;
import org.bukkit.craftbukkit.v1_20_R2.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.v1_20_R2.packs.CraftDataPackManager;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionBrewer;
import org.bukkit.craftbukkit.v1_20_R2.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.v1_20_R2.scheduler.CraftScheduler;
import org.bukkit.craftbukkit.v1_20_R2.scoreboard.CraftCriteria;
import org.bukkit.craftbukkit.v1_20_R2.scoreboard.CraftScoreboardManager;
import org.bukkit.craftbukkit.v1_20_R2.structure.CraftStructureManager;
import org.bukkit.craftbukkit.v1_20_R2.tag.CraftBlockTag;
import org.bukkit.craftbukkit.v1_20_R2.tag.CraftEntityTag;
import org.bukkit.craftbukkit.v1_20_R2.tag.CraftFluidTag;
import org.bukkit.craftbukkit.v1_20_R2.tag.CraftItemTag;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftIconCache;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftSpawnCategory;
import org.bukkit.craftbukkit.v1_20_R2.util.DatFileFilter;
import org.bukkit.craftbukkit.v1_20_R2.util.Versioning;
import org.bukkit.craftbukkit.v1_20_R2.util.permissions.CraftDefaultPermissions;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.server.ServerLoadEvent.LoadType;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.inventory.SmithingTrimRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.loot.LootTable;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView.Scale;
import org.bukkit.packs.DataPackManager;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitWorker;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.StringUtil;
import org.bukkit.util.permissions.DefaultPermissions;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.RestartCommand;
import org.spigotmc.SpigotConfig;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public final class CraftServer implements Server {

    private final String serverName = "CraftBukkit";
    private final String serverVersion;
    private final String bukkitVersion = Versioning.getBukkitVersion();
    private final Logger logger = Logger.getLogger("Minecraft");
    private final ServicesManager servicesManager = new SimpleServicesManager();
    private final CraftScheduler scheduler = new CraftScheduler();
    private final CraftCommandMap commandMap = new CraftCommandMap(this);
    private final SimpleHelpMap helpMap = new SimpleHelpMap(this);
    private final StandardMessenger messenger = new StandardMessenger();
    private final SimplePluginManager pluginManager;
    private final StructureManager structureManager;
    protected final DedicatedServer console;
    protected final DedicatedPlayerList playerList;
    private final Map worlds;
    private final Map registries;
    private YamlConfiguration configuration;
    private YamlConfiguration commandsConfiguration;
    private final Yaml yaml;
    private final Map offlinePlayers;
    private final EntityMetadataStore entityMetadata;
    private final PlayerMetadataStore playerMetadata;
    private final WorldMetadataStore worldMetadata;
    private final Object2IntOpenHashMap spawnCategoryLimit;
    private File container;
    private WarningState warningState;
    public String minimumAPI;
    public CraftScoreboardManager scoreboardManager;
    public CraftDataPackManager dataPackManager;
    public boolean playerCommandState;
    private boolean printSaveWarning;
    private CraftIconCache icon;
    private boolean overrideAllCommandBlockCommands;
    public boolean ignoreVanillaPermissions;
    private final List playerView;
    public int reloadCount;
    private final Spigot spigot;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$World$Environment;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$BanList$Type;

    static {
        ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
        ConfigurationSerialization.registerClass(CraftPlayerProfile.class);
        CraftItemFactory.instance();
    }

    public CraftServer(DedicatedServer console, PlayerList playerList) {
        this.pluginManager = new SimplePluginManager(this, this.commandMap);
        this.worlds = new LinkedHashMap();
        this.registries = new HashMap();
        this.yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
        this.offlinePlayers = (new MapMaker()).weakValues().makeMap();
        this.entityMetadata = new EntityMetadataStore();
        this.playerMetadata = new PlayerMetadataStore();
        this.worldMetadata = new WorldMetadataStore();
        this.spawnCategoryLimit = new Object2IntOpenHashMap();
        this.warningState = WarningState.DEFAULT;
        this.overrideAllCommandBlockCommands = false;
        this.ignoreVanillaPermissions = false;
        this.spigot = new Spigot() {
            public YamlConfiguration getConfig() {
                return SpigotConfig.config;
            }

            public void restart() {
                RestartCommand.restart();
            }

            public void broadcast(BaseComponent component) {
                Iterator iterator = CraftServer.this.getOnlinePlayers().iterator();

                while (iterator.hasNext()) {
                    Player player = (Player) iterator.next();

                    player.spigot().sendMessage(component);
                }

            }

            public void broadcast(BaseComponent... components) {
                Iterator iterator = CraftServer.this.getOnlinePlayers().iterator();

                while (iterator.hasNext()) {
                    Player player = (Player) iterator.next();

                    player.spigot().sendMessage(components);
                }

            }
        };
        this.console = console;
        this.playerList = (DedicatedPlayerList) playerList;
        this.playerView = Collections.unmodifiableList(Lists.transform(playerList.players, new Function() {
            public CraftPlayer apply(ServerPlayer player) {
                return player.getBukkitEntity();
            }
        }));
        this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
        this.structureManager = new CraftStructureManager(console.getStructureManager());
        this.dataPackManager = new CraftDataPackManager(this.getServer().getPackRepository());
        Bukkit.setServer(this);
        CraftRegistry.setMinecraftRegistry(console.registryAccess());
        Enchantments.SHARPNESS.getClass();
        Enchantment.stopAcceptingRegistrations();
        Potion.setPotionBrewer(new CraftPotionBrewer());
        MobEffects.BLINDNESS.getClass();
        PotionEffectType.stopAcceptingRegistrations();
        if (!Main.useConsole) {
            this.getLogger().info("Console input is disabled due to --noconsole command argument");
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.getConfigFile());
        this.configuration.options().copyDefaults(true);
        this.configuration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml"), Charsets.UTF_8)));
        ConfigurationSection legacyAlias = null;

        if (!this.configuration.isString("aliases")) {
            legacyAlias = this.configuration.getConfigurationSection("aliases");
            this.configuration.set("aliases", "now-in-commands.yml");
        }

        this.saveConfig();
        if (this.getCommandsConfigFile().isFile()) {
            legacyAlias = null;
        }

        this.commandsConfiguration = YamlConfiguration.loadConfiguration(this.getCommandsConfigFile());
        this.commandsConfiguration.options().copyDefaults(true);
        this.commandsConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("configurations/commands.yml"), Charsets.UTF_8)));
        this.saveCommandsConfig();
        if (legacyAlias != null) {
            ConfigurationSection aliases = this.commandsConfiguration.createSection("aliases");

            String key;
            ArrayList commands;

            for (Iterator iterator = legacyAlias.getKeys(false).iterator(); iterator.hasNext(); aliases.set(key, commands)) {
                key = (String) iterator.next();
                commands = new ArrayList();
                if (legacyAlias.isList(key)) {
                    Iterator iterator1 = legacyAlias.getStringList(key).iterator();

                    while (iterator1.hasNext()) {
                        String command = (String) iterator1.next();

                        commands.add(command + " $1-");
                    }
                } else {
                    commands.add(legacyAlias.getString(key) + " $1-");
                }
            }
        }

        this.saveCommandsConfig();
        this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*");
        this.ignoreVanillaPermissions = this.commandsConfiguration.getBoolean("ignore-vanilla-permissions");
        this.pluginManager.useTimings(this.configuration.getBoolean("settings.plugin-profiling"));
        this.overrideSpawnLimits();
        console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
        this.warningState = WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
        TicketType.PLUGIN.timeout = (long) this.configuration.getInt("chunk-gc.period-in-ticks");
        this.minimumAPI = this.configuration.getString("settings.minimum-api");
        this.loadIcon();
        if (this.configuration.getBoolean("settings.use-map-color-cache")) {
            MapPalette.setMapColorCache(new CraftMapColorCache(this.logger));
        }

    }

    public boolean getCommandBlockOverride(String command) {
        return this.overrideAllCommandBlockCommands || this.commandsConfiguration.getStringList("command-block-overrides").contains(command);
    }

    private File getConfigFile() {
        return (File) this.console.options.valueOf("bukkit-settings");
    }

    private File getCommandsConfigFile() {
        return (File) this.console.options.valueOf("commands-settings");
    }

    private void overrideSpawnLimits() {
        SpawnCategory[] aspawncategory;
        int i = (aspawncategory = SpawnCategory.values()).length;

        for (int j = 0; j < i; ++j) {
            SpawnCategory spawnCategory = aspawncategory[j];

            if (CraftSpawnCategory.isValidForLimits(spawnCategory)) {
                this.spawnCategoryLimit.put(spawnCategory, this.configuration.getInt(CraftSpawnCategory.getConfigNameSpawnLimit(spawnCategory)));
            }
        }

    }

    private void saveConfig() {
        try {
            this.configuration.save(this.getConfigFile());
        } catch (IOException ioexception) {
            Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + this.getConfigFile(), ioexception);
        }

    }

    private void saveCommandsConfig() {
        try {
            this.commandsConfiguration.save(this.getCommandsConfigFile());
        } catch (IOException ioexception) {
            Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + this.getCommandsConfigFile(), ioexception);
        }

    }

    public void loadPlugins() {
        this.pluginManager.registerInterface(JavaPluginLoader.class);
        File pluginFolder = (File) this.console.options.valueOf("plugins");

        if (pluginFolder.exists()) {
            Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);
            Plugin[] aplugin = plugins;
            int i = plugins.length;

            for (int j = 0; j < i; ++j) {
                Plugin plugin = aplugin[j];

                try {
                    String message = String.format("Loading %s", plugin.getDescription().getFullName());

                    plugin.getLogger().info(message);
                    plugin.onLoad();
                } catch (Throwable throwable) {
                    Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, throwable.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", throwable);
                }
            }
        } else {
            pluginFolder.mkdir();
        }

    }

    public void enablePlugins(PluginLoadOrder type) {
        if (type == PluginLoadOrder.STARTUP) {
            this.helpMap.clear();
            this.helpMap.initializeGeneralTopics();
        }

        Plugin[] plugins = this.pluginManager.getPlugins();
        Plugin[] aplugin = plugins;
        int i = plugins.length;

        for (int j = 0; j < i; ++j) {
            Plugin plugin = aplugin[j];

            if (!plugin.isEnabled() && plugin.getDescription().getLoad() == type) {
                this.enablePlugin(plugin);
            }
        }

        if (type == PluginLoadOrder.POSTWORLD) {
            this.setVanillaCommands(true);
            this.commandMap.setFallbackCommands();
            this.setVanillaCommands(false);
            this.commandMap.registerServerAliases();
            DefaultPermissions.registerCorePermissions();
            CraftDefaultPermissions.registerCorePermissions();
            this.loadCustomPermissions();
            this.helpMap.initializeCommands();
            this.syncCommands();
        }

    }

    public void disablePlugins() {
        this.pluginManager.disablePlugins();
    }

    private void setVanillaCommands(boolean first) {
        Commands dispatcher = this.console.vanillaCommandDispatcher;
        Iterator iterator = dispatcher.getDispatcher().getRoot().getChildren().iterator();

        while (iterator.hasNext()) {
            CommandNode cmd = (CommandNode) iterator.next();
            VanillaCommandWrapper wrapper = new VanillaCommandWrapper(dispatcher, cmd);

            if (SpigotConfig.replaceCommands.contains(wrapper.getName())) {
                if (first) {
                    this.commandMap.register("minecraft", wrapper);
                }
            } else if (!first) {
                this.commandMap.register("minecraft", wrapper);
            }
        }

    }

    public void syncCommands() {
        Commands dispatcher = this.console.resources.managers().commands = new Commands();
        Iterator iterator = this.commandMap.getKnownCommands().entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            String label = (String) entry.getKey();
            Command command = (Command) entry.getValue();

            if (command instanceof VanillaCommandWrapper) {
                LiteralCommandNode node = (LiteralCommandNode) ((VanillaCommandWrapper) command).vanillaCommand;

                if (!node.getLiteral().equals(label)) {
                    LiteralCommandNode clone = new LiteralCommandNode(label, node.getCommand(), node.getRequirement(), node.getRedirect(), node.getRedirectModifier(), node.isFork());
                    Iterator iterator1 = node.getChildren().iterator();

                    while (iterator1.hasNext()) {
                        CommandNode child = (CommandNode) iterator1.next();

                        clone.addChild(child);
                    }

                    node = clone;
                }

                dispatcher.getDispatcher().getRoot().addChild(node);
            } else {
                (new BukkitCommandWrapper(this, (Command) entry.getValue())).register(dispatcher.getDispatcher(), label);
            }
        }

        iterator = this.getHandle().players.iterator();

        while (iterator.hasNext()) {
            ServerPlayer player = (ServerPlayer) iterator.next();

            dispatcher.sendCommands(player);
        }

    }

    private void enablePlugin(Plugin plugin) {
        try {
            List perms = plugin.getDescription().getPermissions();
            Iterator iterator = perms.iterator();

            while (iterator.hasNext()) {
                Permission perm = (Permission) iterator.next();

                try {
                    this.pluginManager.addPermission(perm, false);
                } catch (IllegalArgumentException illegalargumentexception) {
                    this.getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", illegalargumentexception);
                }
            }

            this.pluginManager.dirtyPermissibles();
            this.pluginManager.enablePlugin(plugin);
        } catch (Throwable throwable) {
            Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, throwable.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", throwable);
        }

    }

    public String getName() {
        return "CraftBukkit";
    }

    public String getVersion() {
        return this.serverVersion + " (MC: " + this.console.getServerVersion() + ")";
    }

    public String getBukkitVersion() {
        return this.bukkitVersion;
    }

    public List getOnlinePlayers() {
        return this.playerView;
    }

    /** @deprecated */
    @Deprecated
    public Player getPlayer(String name) {
        Preconditions.checkArgument(name != null, "name cannot be null");
        Player found = this.getPlayerExact(name);

        if (found != null) {
            return found;
        } else {
            String lowerName = name.toLowerCase(Locale.ENGLISH);
            int delta = Integer.MAX_VALUE;
            Iterator iterator = this.getOnlinePlayers().iterator();

            while (iterator.hasNext()) {
                Player player = (Player) iterator.next();

                if (player.getName().toLowerCase(Locale.ENGLISH).startsWith(lowerName)) {
                    int curDelta = Math.abs(player.getName().length() - lowerName.length());

                    if (curDelta < delta) {
                        found = player;
                        delta = curDelta;
                    }

                    if (curDelta == 0) {
                        break;
                    }
                }
            }

            return found;
        }
    }

    /** @deprecated */
    @Deprecated
    public Player getPlayerExact(String name) {
        Preconditions.checkArgument(name != null, "name cannot be null");
        ServerPlayer player = this.playerList.getPlayerByName(name);

        return player != null ? player.getBukkitEntity() : null;
    }

    public Player getPlayer(UUID id) {
        Preconditions.checkArgument(id != null, "UUID id cannot be null");
        ServerPlayer player = this.playerList.getPlayer(id);

        return player != null ? player.getBukkitEntity() : null;
    }

    public int broadcastMessage(String message) {
        return this.broadcast(message, "bukkit.broadcast.user");
    }

    /** @deprecated */
    @Deprecated
    public List matchPlayer(String partialName) {
        Preconditions.checkArgument(partialName != null, "partialName cannot be null");
        ArrayList matchedPlayers = new ArrayList();
        Iterator iterator = this.getOnlinePlayers().iterator();

        while (iterator.hasNext()) {
            Player iterPlayer = (Player) iterator.next();
            String iterPlayerName = iterPlayer.getName();

            if (partialName.equalsIgnoreCase(iterPlayerName)) {
                matchedPlayers.clear();
                matchedPlayers.add(iterPlayer);
                break;
            }

            if (iterPlayerName.toLowerCase(Locale.ENGLISH).contains(partialName.toLowerCase(Locale.ENGLISH))) {
                matchedPlayers.add(iterPlayer);
            }
        }

        return matchedPlayers;
    }

    public int getMaxPlayers() {
        return this.playerList.getMaxPlayers();
    }

    public void setMaxPlayers(int maxPlayers) {
        Preconditions.checkArgument(maxPlayers >= 0, "maxPlayers must be >= 0");
        this.playerList.maxPlayers = maxPlayers;
    }

    public int getPort() {
        return this.getServer().getPort();
    }

    public int getViewDistance() {
        return this.getProperties().viewDistance;
    }

    public int getSimulationDistance() {
        return this.getProperties().simulationDistance;
    }

    public String getIp() {
        return this.getServer().getLocalIp();
    }

    public String getWorldType() {
        return this.getProperties().properties.getProperty("level-type");
    }

    public boolean getGenerateStructures() {
        return this.getServer().getWorldData().worldGenOptions().generateStructures();
    }

    public int getMaxWorldSize() {
        return this.getProperties().maxWorldSize;
    }

    public boolean getAllowEnd() {
        return this.configuration.getBoolean("settings.allow-end");
    }

    public boolean getAllowNether() {
        return this.getServer().isNetherEnabled();
    }

    public boolean getWarnOnOverload() {
        return this.configuration.getBoolean("settings.warn-on-overload");
    }

    public boolean getQueryPlugins() {
        return this.configuration.getBoolean("settings.query-plugins");
    }

    public List getInitialEnabledPacks() {
        return Collections.unmodifiableList(this.getProperties().initialDataPackConfiguration.getEnabled());
    }

    public List getInitialDisabledPacks() {
        return Collections.unmodifiableList(this.getProperties().initialDataPackConfiguration.getDisabled());
    }

    public DataPackManager getDataPackManager() {
        return this.dataPackManager;
    }

    public String getResourcePack() {
        return (String) this.getServer().getServerResourcePack().map(MinecraftServer.ServerResourcePackInfo::url).orElse("");
    }

    public String getResourcePackHash() {
        return ((String) this.getServer().getServerResourcePack().map(MinecraftServer.ServerResourcePackInfo::hash).orElse("")).toUpperCase(Locale.ROOT);
    }

    public String getResourcePackPrompt() {
        return (String) this.getServer().getServerResourcePack().map(MinecraftServer.ServerResourcePackInfo::prompt).map(CraftChatMessage::fromComponent).orElse("");
    }

    public boolean isResourcePackRequired() {
        return this.getServer().isResourcePackRequired();
    }

    public boolean hasWhitelist() {
        return (Boolean) this.getProperties().whiteList.get();
    }

    private DedicatedServerProperties getProperties() {
        return this.console.getProperties();
    }

    public String getUpdateFolder() {
        return this.configuration.getString("settings.update-folder", "update");
    }

    public File getUpdateFolderFile() {
        return new File((File) this.console.options.valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
    }

    public long getConnectionThrottle() {
        return SpigotConfig.bungee ? -1L : (long) this.configuration.getInt("settings.connection-throttle");
    }

    /** @deprecated */
    @Deprecated
    public int getTicksPerAnimalSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public int getTicksPerMonsterSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.MONSTER);
    }

    /** @deprecated */
    @Deprecated
    public int getTicksPerWaterSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public int getTicksPerWaterAmbientSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT);
    }

    /** @deprecated */
    @Deprecated
    public int getTicksPerWaterUndergroundCreatureSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE);
    }

    /** @deprecated */
    @Deprecated
    public int getTicksPerAmbientSpawns() {
        return this.getTicksPerSpawns(SpawnCategory.AMBIENT);
    }

    public int getTicksPerSpawns(SpawnCategory spawnCategory) {
        Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
        Preconditions.checkArgument(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory.%s are not supported", spawnCategory);
        return this.configuration.getInt(CraftSpawnCategory.getConfigNameTicksPerSpawn(spawnCategory));
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public CraftScheduler getScheduler() {
        return this.scheduler;
    }

    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    public List getWorlds() {
        return new ArrayList(this.worlds.values());
    }

    public DedicatedPlayerList getHandle() {
        return this.playerList;
    }

    public boolean dispatchServerCommand(CommandSender sender, ConsoleInput serverCommand) {
        if (sender instanceof Conversable) {
            Conversable conversable = (Conversable) sender;

            if (conversable.isConversing()) {
                conversable.acceptConversationInput(serverCommand.msg);
                return true;
            }
        }

        try {
            this.playerCommandState = true;
            boolean flag = this.dispatchCommand(sender, serverCommand.msg);

            return flag;
        } catch (Exception exception) {
            this.getLogger().log(Level.WARNING, "Unexpected exception while parsing console command \"" + serverCommand.msg + '"', exception);
        } finally {
            this.playerCommandState = false;
        }

        return false;
    }

    public boolean dispatchCommand(CommandSender sender, String commandLine) {
        Preconditions.checkArgument(sender != null, "sender cannot be null");
        Preconditions.checkArgument(commandLine != null, "commandLine cannot be null");
        AsyncCatcher.catchOp("command dispatch");
        if (this.commandMap.dispatch(sender, commandLine)) {
            return true;
        } else {
            if (!SpigotConfig.unknownCommandMessage.isEmpty()) {
                sender.sendMessage(SpigotConfig.unknownCommandMessage);
            }

            return false;
        }
    }

    public void reload() {
        ++this.reloadCount;
        this.configuration = YamlConfiguration.loadConfiguration(this.getConfigFile());
        this.commandsConfiguration = YamlConfiguration.loadConfiguration(this.getCommandsConfigFile());
        this.console.settings = new DedicatedServerSettings(this.console.options);
        DedicatedServerProperties config = this.console.settings.getProperties();

        this.console.setPvpAllowed(config.pvp);
        this.console.setFlightAllowed(config.allowFlight);
        this.console.setMotd(config.motd);
        this.overrideSpawnLimits();
        this.warningState = WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
        TicketType.PLUGIN.timeout = (long) this.configuration.getInt("chunk-gc.period-in-ticks");
        this.minimumAPI = this.configuration.getString("settings.minimum-api");
        this.printSaveWarning = false;
        this.console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
        this.loadIcon();

        try {
            this.playerList.getIpBans().load();
        } catch (IOException ioexception) {
            this.logger.log(Level.WARNING, "Failed to load banned-ips.json, " + ioexception.getMessage());
        }

        try {
            this.playerList.getBans().load();
        } catch (IOException ioexception1) {
            this.logger.log(Level.WARNING, "Failed to load banned-players.json, " + ioexception1.getMessage());
        }

        SpigotConfig.init((File) this.console.options.valueOf("spigot-settings"));
        Iterator iterator = this.console.getAllLevels().iterator();

        while (iterator.hasNext()) {
            ServerLevel world = (ServerLevel) iterator.next();

            world.K.setDifficulty(config.difficulty);
            world.setSpawnSettings(config.spawnMonsters, config.spawnAnimals);
            SpawnCategory[] aspawncategory;
            int i = (aspawncategory = SpawnCategory.values()).length;

            for (int j = 0; j < i; ++j) {
                SpawnCategory spawnCategory = aspawncategory[j];

                if (CraftSpawnCategory.isValidForLimits(spawnCategory)) {
                    long ticksPerCategorySpawn = (long) this.getTicksPerSpawns(spawnCategory);

                    if (ticksPerCategorySpawn < 0L) {
                        world.ticksPerSpawnCategory.put(spawnCategory, CraftSpawnCategory.getDefaultTicksPerSpawn(spawnCategory));
                    } else {
                        world.ticksPerSpawnCategory.put(spawnCategory, ticksPerCategorySpawn);
                    }
                }
            }

            world.spigotConfig.init();
        }

        this.pluginManager.clearPlugins();
        this.commandMap.clearCommands();
        this.reloadData();
        SpigotConfig.registerCommands();
        this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*");
        this.ignoreVanillaPermissions = this.commandsConfiguration.getBoolean("ignore-vanilla-permissions");

        for (int pollCount = 0; pollCount < 50 && this.getScheduler().getActiveWorkers().size() > 0; ++pollCount) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException interruptedexception) {
                ;
            }
        }

        List overdueWorkers = this.getScheduler().getActiveWorkers();
        Iterator iterator1 = overdueWorkers.iterator();

        while (iterator1.hasNext()) {
            BukkitWorker worker = (BukkitWorker) iterator1.next();
            Plugin plugin = worker.getOwner();

            this.getLogger().log(Level.SEVERE, String.format("Nag author(s): '%s' of '%s' about the following: %s", plugin.getDescription().getAuthors(), plugin.getDescription().getFullName(), "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin"));
        }

        this.loadPlugins();
        this.enablePlugins(PluginLoadOrder.STARTUP);
        this.enablePlugins(PluginLoadOrder.POSTWORLD);
        this.getPluginManager().callEvent(new ServerLoadEvent(LoadType.RELOAD));
    }

    public void reloadData() {
        ReloadCommand.reload(this.console);
    }

    private void loadIcon() {
        this.icon = new CraftIconCache((byte[]) null);

        try {
            File file = new File(new File("."), "server-icon.png");

            if (file.isFile()) {
                this.icon = loadServerIcon0(file);
            }
        } catch (Exception exception) {
            this.getLogger().log(Level.WARNING, "Couldn't load server icon", exception);
        }

    }

    private void loadCustomPermissions() {
        File file = new File(this.configuration.getString("settings.permissions-file"));

        FileInputStream stream;

        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException filenotfoundexception) {
            try {
                file.createNewFile();
            } finally {
                ;
            }

            return;
        }

        Map perms;
        label211:
        {
            try {
                perms = (Map) this.yaml.load(stream);
                break label211;
            } catch (MarkedYAMLException markedyamlexception) {
                this.getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + markedyamlexception.toString());
                return;
            } catch (Throwable throwable) {
                this.getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", throwable);
            } finally {
                try {
                    stream.close();
                } catch (IOException ioexception) {
                    ;
                }

            }

            return;
        }

        if (perms == null) {
            this.getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
        } else {
            List permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid", Permission.DEFAULT_PERMISSION);
            Iterator iterator = permsList.iterator();

            while (iterator.hasNext()) {
                Permission perm = (Permission) iterator.next();

                try {
                    this.pluginManager.addPermission(perm);
                } catch (IllegalArgumentException illegalargumentexception) {
                    this.getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", illegalargumentexception);
                }
            }

        }
    }

    public String toString() {
        return "CraftServer{serverName=CraftBukkit,serverVersion=" + this.serverVersion + ",minecraftVersion=" + this.console.getServerVersion() + '}';
    }

    public World createWorld(String name, Environment environment) {
        return WorldCreator.name(name).environment(environment).createWorld();
    }

    public World createWorld(String name, Environment environment, long seed) {
        return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
    }

    public World createWorld(String name, Environment environment, ChunkGenerator generator) {
        return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
    }

    public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {
        return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
    }

    public World createWorld(WorldCreator creator) {
        Preconditions.checkState(this.console.getAllLevels().iterator().hasNext(), "Cannot create additional worlds on STARTUP");
        Preconditions.checkArgument(creator != null, "WorldCreator cannot be null");
        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        BiomeProvider biomeProvider = creator.biomeProvider();
        File folder = new File(this.getWorldContainer(), name);
        World world = this.getWorld(name);

        if (world != null) {
            return world;
        } else {
            if (folder.exists()) {
                Preconditions.checkArgument(folder.isDirectory(), "File (%s) exists and isn't a folder", name);
            }

            if (generator == null) {
                generator = this.getGenerator(name);
            }

            if (biomeProvider == null) {
                biomeProvider = this.getBiomeProvider(name);
            }

            ResourceKey actualDimension;

            switch ($SWITCH_TABLE$org$bukkit$World$Environment()[creator.environment().ordinal()]) {
                case 1:
                    actualDimension = LevelStem.OVERWORLD;
                    break;
                case 2:
                    actualDimension = LevelStem.NETHER;
                    break;
                case 3:
                    actualDimension = LevelStem.END;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal dimension (" + creator.environment() + ")");
            }

            LevelStorageSource.LevelStorageAccess worldSession;

            try {
                worldSession = LevelStorageSource.createDefault(this.getWorldContainer().toPath()).validateAndCreateAccess(name, actualDimension);
            } catch (ContentValidationException | IOException ioexception) {
                throw new RuntimeException(ioexception);
            }

            boolean hardcore = creator.hardcore();
            WorldLoader.DataLoadContext worldloader_a = this.console.worldLoader;
            Registry iregistry = worldloader_a.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM);
            RegistryOps dynamicops = RegistryOps.create(NbtOps.INSTANCE, (HolderLookup.Provider) worldloader_a.datapackWorldgen());
            Pair pair = worldSession.getDataTag(dynamicops, worldloader_a.dataConfiguration(), iregistry, worldloader_a.datapackWorldgen().allRegistriesLifecycle());
            PrimaryLevelData worlddata;

            if (pair != null) {
                worlddata = (PrimaryLevelData) pair.getFirst();
                iregistry = ((WorldDimensions.Complete) pair.getSecond()).dimensions();
            } else {
                WorldOptions worldoptions = new WorldOptions(creator.seed(), creator.generateStructures(), false);
                DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(GsonHelper.parse(creator.generatorSettings().isEmpty() ? "{}" : creator.generatorSettings()), creator.type().name().toLowerCase(Locale.ROOT));
                LevelSettings worldsettings = new LevelSettings(name, GameType.byId(this.getDefaultGameMode().getValue()), hardcore, Difficulty.EASY, false, new GameRules(), worldloader_a.dataConfiguration());
                WorldDimensions worlddimensions = properties.create(worldloader_a.datapackWorldgen());
                WorldDimensions.Complete worlddimensions_b = worlddimensions.bake(iregistry);
                Lifecycle lifecycle = worlddimensions_b.lifecycle().add(worldloader_a.datapackWorldgen().allRegistriesLifecycle());

                worlddata = new PrimaryLevelData(worldsettings, worldoptions, worlddimensions_b.specialWorldProperty(), lifecycle);
                iregistry = worlddimensions_b.dimensions();
            }

            worlddata.customDimensions = iregistry;
            worlddata.checkName(name);
            worlddata.setModdedInfo(this.console.getServerModName(), this.console.getModdedStatus().shouldReportAsModified());
            if (this.console.options.has("forceUpgrade")) {
                net.minecraft.server.Main.forceUpgrade(worldSession, DataFixers.getDataFixer(), this.console.options.has("eraseCache"), () -> {
                    return true;
                }, iregistry);
            }

            long j = BiomeManager.obfuscateSeed(creator.seed());
            ImmutableList list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));
            LevelStem worlddimension = (LevelStem) iregistry.get(actualDimension);
            CraftWorldInfo worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), (DimensionType) worlddimension.type().value());

            if (biomeProvider == null && generator != null) {
                biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
            }

            String levelName = this.getServer().getProperties().levelName;
            ResourceKey worldKey;

            if (name.equals(levelName + "_nether")) {
                worldKey = net.minecraft.world.level.Level.NETHER;
            } else if (name.equals(levelName + "_the_end")) {
                worldKey = net.minecraft.world.level.Level.END;
            } else {
                worldKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(name.toLowerCase(Locale.ENGLISH)));
            }

            ServerLevel internal = new ServerLevel(this.console, this.console.executor, worldSession, worlddata, worldKey, worlddimension, this.getServer().progressListenerFactory.create(11), worlddata.isDebugWorld(), j, creator.environment() == Environment.NORMAL ? list : ImmutableList.of(), true, this.console.overworld().getRandomSequences(), creator.environment(), generator, biomeProvider);

            internal.keepSpawnInMemory = creator.keepSpawnInMemory();
            if (!this.worlds.containsKey(name.toLowerCase(Locale.ENGLISH))) {
                return null;
            } else {
                this.console.initWorld(internal, worlddata, worlddata, worlddata.worldGenOptions());
                internal.setSpawnSettings(true, true);
                this.console.addLevel(internal);
                this.getServer().prepareLevels(internal.getChunkSource().chunkMap.progressListener, internal);
                internal.entityManager.tick();
                this.pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
                return internal.getWorld();
            }
        }
    }

    public boolean unloadWorld(String name, boolean save) {
        return this.unloadWorld(this.getWorld(name), save);
    }

    public boolean unloadWorld(World world, boolean save) {
        if (world == null) {
            return false;
        } else {
            ServerLevel handle = ((CraftWorld) world).getHandle();

            if (this.console.getLevel(handle.dimension()) == null) {
                return false;
            } else if (handle.dimension() == net.minecraft.world.level.Level.OVERWORLD) {
                return false;
            } else if (handle.players().size() > 0) {
                return false;
            } else {
                WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());

                this.pluginManager.callEvent(e);
                if (e.isCancelled()) {
                    return false;
                } else {
                    try {
                        if (save) {
                            handle.save((ProgressListener) null, true, true);
                        }

                        handle.getChunkSource().close(save);
                        handle.entityManager.close(save);
                        handle.convertable.close();
                    } catch (Exception exception) {
                        this.getLogger().log(Level.SEVERE, (String) null, exception);
                    }

                    this.worlds.remove(world.getName().toLowerCase(Locale.ENGLISH));
                    this.console.removeLevel(handle);
                    return true;
                }
            }
        }
    }

    public DedicatedServer getServer() {
        return this.console;
    }

    public World getWorld(String name) {
        Preconditions.checkArgument(name != null, "name cannot be null");
        return (World) this.worlds.get(name.toLowerCase(Locale.ENGLISH));
    }

    public World getWorld(UUID uid) {
        Iterator iterator = this.worlds.values().iterator();

        while (iterator.hasNext()) {
            World world = (World) iterator.next();

            if (world.getUID().equals(uid)) {
                return world;
            }
        }

        return null;
    }

    public void addWorld(World world) {
        if (this.getWorld(world.getUID()) != null) {
            System.out.println("World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName() + "'s world directory if you want to be able to load the duplicate world.");
        } else {
            this.worlds.put(world.getName().toLowerCase(Locale.ENGLISH), world);
        }
    }

    public WorldBorder createWorldBorder() {
        return new CraftWorldBorder(new net.minecraft.world.level.border.WorldBorder());
    }

    public Logger getLogger() {
        return this.logger;
    }

    public ConsoleReader getReader() {
        return this.console.reader;
    }

    public PluginCommand getPluginCommand(String name) {
        Command command = this.commandMap.getCommand(name);

        return command instanceof PluginCommand ? (PluginCommand) command : null;
    }

    public void savePlayers() {
        this.checkSaveState();
        this.playerList.saveAll();
    }

    public boolean addRecipe(Recipe recipe) {
        Object toAdd;

        if (recipe instanceof CraftRecipe) {
            toAdd = (CraftRecipe) recipe;
        } else if (recipe instanceof ShapedRecipe) {
            toAdd = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe) recipe);
        } else if (recipe instanceof ShapelessRecipe) {
            toAdd = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe) recipe);
        } else if (recipe instanceof FurnaceRecipe) {
            toAdd = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe) recipe);
        } else if (recipe instanceof BlastingRecipe) {
            toAdd = CraftBlastingRecipe.fromBukkitRecipe((BlastingRecipe) recipe);
        } else if (recipe instanceof CampfireRecipe) {
            toAdd = CraftCampfireRecipe.fromBukkitRecipe((CampfireRecipe) recipe);
        } else if (recipe instanceof SmokingRecipe) {
            toAdd = CraftSmokingRecipe.fromBukkitRecipe((SmokingRecipe) recipe);
        } else if (recipe instanceof StonecuttingRecipe) {
            toAdd = CraftStonecuttingRecipe.fromBukkitRecipe((StonecuttingRecipe) recipe);
        } else if (recipe instanceof SmithingTransformRecipe) {
            toAdd = CraftSmithingTransformRecipe.fromBukkitRecipe((SmithingTransformRecipe) recipe);
        } else {
            if (!(recipe instanceof SmithingTrimRecipe)) {
                if (recipe instanceof ComplexRecipe) {
                    throw new UnsupportedOperationException("Cannot add custom complex recipe");
                }

                return false;
            }

            toAdd = CraftSmithingTrimRecipe.fromBukkitRecipe((SmithingTrimRecipe) recipe);
        }

        ((CraftRecipe) toAdd).addToCraftingManager();
        return true;
    }

    public List getRecipesFor(ItemStack result) {
        Preconditions.checkArgument(result != null, "ItemStack cannot be null");
        ArrayList results = new ArrayList();
        Iterator iter = this.recipeIterator();

        while (iter.hasNext()) {
            Recipe recipe = (Recipe) iter.next();
            ItemStack stack = recipe.getResult();

            if (stack.getType() == result.getType() && (result.getDurability() == -1 || result.getDurability() == stack.getDurability())) {
                results.add(recipe);
            }
        }

        return results;
    }

    public Recipe getRecipe(NamespacedKey recipeKey) {
        Preconditions.checkArgument(recipeKey != null, "NamespacedKey recipeKey cannot be null");
        return (Recipe) this.getServer().getRecipeManager().byKey(CraftNamespacedKey.toMinecraft(recipeKey)).map(RecipeHolder::toBukkitRecipe).orElse((Object) null);
    }

    public Recipe getCraftingRecipe(ItemStack[] craftingMatrix, World world) {
        AbstractContainerMenu container = new AbstractContainerMenu((MenuType) null, -1) {
            public InventoryView getBukkitView() {
                return null;
            }

            public boolean stillValid(net.minecraft.world.entity.player.Player entityhuman) {
                return false;
            }

            public net.minecraft.world.item.ItemStack quickMoveStack(net.minecraft.world.entity.player.Player entityhuman, int i) {
                return net.minecraft.world.item.ItemStack.EMPTY;
            }
        };
        TransientCraftingContainer inventoryCrafting = new TransientCraftingContainer(container, 3, 3);

        return (Recipe) this.getNMSRecipe(craftingMatrix, inventoryCrafting, (CraftWorld) world).map(RecipeHolder::toBukkitRecipe).orElse((Object) null);
    }

    public ItemStack craftItem(ItemStack[] craftingMatrix, World world, Player player) {
        Preconditions.checkArgument(world != null, "world cannot be null");
        Preconditions.checkArgument(player != null, "player cannot be null");
        CraftWorld craftWorld = (CraftWorld) world;
        CraftPlayer craftPlayer = (CraftPlayer) player;
        CraftingMenu container = new CraftingMenu(-1, craftPlayer.getHandle().getInventory());
        TransientCraftingContainer inventoryCrafting = container.r;
        ResultContainer craftResult = container.resultSlots;
        Optional recipe = this.getNMSRecipe(craftingMatrix, inventoryCrafting, craftWorld);
        net.minecraft.world.item.ItemStack itemstack = net.minecraft.world.item.ItemStack.EMPTY;

        if (recipe.isPresent()) {
            RecipeHolder recipeCrafting = (RecipeHolder) recipe.get();

            if (craftResult.setRecipeUsed(craftWorld.getHandle(), craftPlayer.getHandle(), recipeCrafting)) {
                itemstack = ((CraftingRecipe) recipeCrafting.value()).assemble(inventoryCrafting, craftWorld.getHandle().registryAccess());
            }
        }

        net.minecraft.world.item.ItemStack result = CraftEventFactory.callPreCraftEvent(inventoryCrafting, craftResult, itemstack, container.getBukkitView(), recipe.map(RecipeHolder::toBukkitRecipe).orElse((Object) null) instanceof RepairItemRecipe);

        for (int i = 0; i < craftingMatrix.length; ++i) {
            Item remaining = ((net.minecraft.world.item.ItemStack) inventoryCrafting.getContents().get(i)).getItem().getCraftingRemainingItem();

            craftingMatrix[i] = remaining != null ? CraftItemStack.asBukkitCopy(remaining.getDefaultInstance()) : null;
        }

        return CraftItemStack.asBukkitCopy(result);
    }

    private Optional getNMSRecipe(ItemStack[] craftingMatrix, CraftingContainer inventoryCrafting, CraftWorld world) {
        Preconditions.checkArgument(craftingMatrix != null, "craftingMatrix must not be null");
        Preconditions.checkArgument(craftingMatrix.length == 9, "craftingMatrix must be an array of length 9");
        Preconditions.checkArgument(world != null, "world must not be null");

        for (int i = 0; i < craftingMatrix.length; ++i) {
            inventoryCrafting.setItem(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        return this.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, inventoryCrafting, world.getHandle());
    }

    public Iterator recipeIterator() {
        return new RecipeIterator();
    }

    public void clearRecipes() {
        this.console.getRecipeManager().clearRecipes();
    }

    public void resetRecipes() {
        this.reloadData();
    }

    public boolean removeRecipe(NamespacedKey recipeKey) {
        Preconditions.checkArgument(recipeKey != null, "recipeKey == null");
        ResourceLocation mcKey = CraftNamespacedKey.toMinecraft(recipeKey);

        return this.getServer().getRecipeManager().removeRecipe(mcKey);
    }

    public Map getCommandAliases() {
        ConfigurationSection section = this.commandsConfiguration.getConfigurationSection("aliases");
        LinkedHashMap result = new LinkedHashMap();
        String key;
        Object commands;

        if (section != null) {
            for (Iterator iterator = section.getKeys(false).iterator(); iterator.hasNext(); result.put(key, (String[]) ((List) commands).toArray(new String[((List) commands).size()]))) {
                key = (String) iterator.next();
                if (section.isList(key)) {
                    commands = section.getStringList(key);
                } else {
                    commands = ImmutableList.of(section.getString(key));
                }
            }
        }

        return result;
    }

    public void removeBukkitSpawnRadius() {
        this.configuration.set("settings.spawn-radius", (Object) null);
        this.saveConfig();
    }

    public int getBukkitSpawnRadius() {
        return this.configuration.getInt("settings.spawn-radius", -1);
    }

    public String getShutdownMessage() {
        return this.configuration.getString("settings.shutdown-message");
    }

    public int getSpawnRadius() {
        return this.getServer().getSpawnProtectionRadius();
    }

    public void setSpawnRadius(int value) {
        this.configuration.set("settings.spawn-radius", value);
        this.saveConfig();
    }

    public boolean shouldSendChatPreviews() {
        return false;
    }

    public boolean isEnforcingSecureProfiles() {
        return this.getServer().enforceSecureProfile();
    }

    public boolean getHideOnlinePlayers() {
        return this.console.hidesOnlinePlayers();
    }

    public boolean getOnlineMode() {
        return this.console.usesAuthentication();
    }

    public boolean getAllowFlight() {
        return this.console.isFlightAllowed();
    }

    public boolean isHardcore() {
        return this.console.isHardcore();
    }

    public ChunkGenerator getGenerator(String world) {
        ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
        ChunkGenerator result = null;

        if (section != null) {
            section = section.getConfigurationSection(world);
            if (section != null) {
                String name = section.getString("generator");

                if (name != null && !name.equals("")) {
                    String[] split = name.split(":", 2);
                    String id = split.length > 1 ? split[1] : null;
                    Plugin plugin = this.pluginManager.getPlugin(split[0]);

                    if (plugin == null) {
                        this.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
                    } else if (!plugin.isEnabled()) {
                        this.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
                    } else {
                        try {
                            result = plugin.getDefaultWorldGenerator(world, id);
                            if (result == null) {
                                this.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' lacks a default world generator");
                            }
                        } catch (Throwable throwable) {
                            plugin.getLogger().log(Level.SEVERE, "Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), throwable);
                        }
                    }
                }
            }
        }

        return result;
    }

    public BiomeProvider getBiomeProvider(String world) {
        ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
        BiomeProvider result = null;

        if (section != null) {
            section = section.getConfigurationSection(world);
            if (section != null) {
                String name = section.getString("biome-provider");

                if (name != null && !name.equals("")) {
                    String[] split = name.split(":", 2);
                    String id = split.length > 1 ? split[1] : null;
                    Plugin plugin = this.pluginManager.getPlugin(split[0]);

                    if (plugin == null) {
                        this.getLogger().severe("Could not set biome provider for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
                    } else if (!plugin.isEnabled()) {
                        this.getLogger().severe("Could not set biome provider for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
                    } else {
                        try {
                            result = plugin.getDefaultBiomeProvider(world, id);
                            if (result == null) {
                                this.getLogger().severe("Could not set biome provider for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' lacks a default world biome provider");
                            }
                        } catch (Throwable throwable) {
                            plugin.getLogger().log(Level.SEVERE, "Could not set biome provider for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), throwable);
                        }
                    }
                }
            }
        }

        return result;
    }

    /** @deprecated */
    @Deprecated
    public CraftMapView getMap(int id) {
        MapItemSavedData worldmap = this.console.getLevel(net.minecraft.world.level.Level.OVERWORLD).getMapData("map_" + id);

        return worldmap == null ? null : worldmap.mapView;
    }

    public CraftMapView createMap(World world) {
        Preconditions.checkArgument(world != null, "World cannot be null");
        ServerLevel minecraftWorld = ((CraftWorld) world).getHandle();
        int newId = MapItem.createNewSavedData(minecraftWorld, minecraftWorld.getLevelData().getXSpawn(), minecraftWorld.getLevelData().getZSpawn(), 3, false, false, minecraftWorld.dimension());

        return minecraftWorld.getMapData(MapItem.makeKey(newId)).mapView;
    }

    public ItemStack createExplorerMap(World world, Location location, StructureType structureType) {
        return this.createExplorerMap(world, location, structureType, 100, true);
    }

    public ItemStack createExplorerMap(World world, Location location, StructureType structureType, int radius, boolean findUnexplored) {
        Preconditions.checkArgument(world != null, "World cannot be null");
        Preconditions.checkArgument(structureType != null, "StructureType cannot be null");
        Preconditions.checkArgument(structureType.getMapIcon() != null, "Cannot create explorer maps for StructureType %s", structureType.getName());
        ServerLevel worldServer = ((CraftWorld) world).getHandle();
        Location structureLocation = world.locateNearestStructure(location, structureType, radius, findUnexplored);
        BlockPos structurePosition = CraftLocation.toBlockPosition(structureLocation);
        net.minecraft.world.item.ItemStack stack = MapItem.create(worldServer, structurePosition.getX(), structurePosition.getZ(), Scale.NORMAL.getValue(), true, true);

        MapItem.renderBiomePreviewMap(worldServer, stack);
        MapItem.getSavedData(stack, worldServer);
        MapItemSavedData.addTargetDecoration(stack, structurePosition, "+", MapDecoration.Type.byIcon(structureType.getMapIcon().getValue()));
        return CraftItemStack.asBukkitCopy(stack);
    }

    public void shutdown() {
        this.console.halt(false);
    }

    public int broadcast(String message, String permission) {
        HashSet recipients = new HashSet();
        Iterator iterator = this.getPluginManager().getPermissionSubscriptions(permission).iterator();

        while (iterator.hasNext()) {
            Permissible permissible = (Permissible) iterator.next();

            if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                recipients.add((CommandSender) permissible);
            }
        }

        BroadcastMessageEvent broadcastMessageEvent = new BroadcastMessageEvent(!Bukkit.isPrimaryThread(), message, recipients);

        this.getPluginManager().callEvent(broadcastMessageEvent);
        if (broadcastMessageEvent.isCancelled()) {
            return 0;
        } else {
            message = broadcastMessageEvent.getMessage();
            Iterator iterator1 = recipients.iterator();

            while (iterator1.hasNext()) {
                CommandSender recipient = (CommandSender) iterator1.next();

                recipient.sendMessage(message);
            }

            return recipients.size();
        }
    }

    /** @deprecated */
    @Deprecated
    public OfflinePlayer getOfflinePlayer(String name) {
        Preconditions.checkArgument(name != null, "name cannot be null");
        Preconditions.checkArgument(!name.isBlank(), "name cannot be empty");
        Object result = this.getPlayerExact(name);

        if (result == null) {
            GameProfile profile = null;

            if (this.getOnlineMode() || SpigotConfig.bungee) {
                profile = (GameProfile) this.console.getProfileCache().get(name).orElse((Object) null);
            }

            if (profile == null) {
                result = this.getOfflinePlayer(new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8)), name));
            } else {
                result = this.getOfflinePlayer(profile);
            }
        } else {
            this.offlinePlayers.remove(((OfflinePlayer) result).getUniqueId());
        }

        return (OfflinePlayer) result;
    }

    public OfflinePlayer getOfflinePlayer(UUID id) {
        Preconditions.checkArgument(id != null, "UUID id cannot be null");
        Object result = this.getPlayer(id);

        if (result == null) {
            result = (OfflinePlayer) this.offlinePlayers.get(id);
            if (result == null) {
                result = new CraftOfflinePlayer(this, new GameProfile(id, ""));
                this.offlinePlayers.put(id, result);
            }
        } else {
            this.offlinePlayers.remove(id);
        }

        return (OfflinePlayer) result;
    }

    public PlayerProfile createPlayerProfile(UUID uniqueId, String name) {
        return new CraftPlayerProfile(uniqueId, name);
    }

    public PlayerProfile createPlayerProfile(UUID uniqueId) {
        return new CraftPlayerProfile(uniqueId, (String) null);
    }

    public PlayerProfile createPlayerProfile(String name) {
        return new CraftPlayerProfile((UUID) null, name);
    }

    public OfflinePlayer getOfflinePlayer(GameProfile profile) {
        CraftOfflinePlayer player = new CraftOfflinePlayer(this, profile);

        this.offlinePlayers.put(profile.getId(), player);
        return player;
    }

    public Set getIPBans() {
        return (Set) this.playerList.getIpBans().getEntries().stream().map(StoredUserEntry::getUser).collect(Collectors.toSet());
    }

    public void banIP(String address) {
        Preconditions.checkArgument(address != null && !address.isBlank(), "Address cannot be null or blank.");
        this.getBanList(Type.IP).addBan(address, (String) null, (Date) null, (String) null);
    }

    public void unbanIP(String address) {
        Preconditions.checkArgument(address != null && !address.isBlank(), "Address cannot be null or blank.");
        this.getBanList(Type.IP).pardon(address);
    }

    public void banIP(InetAddress address) {
        Preconditions.checkArgument(address != null, "Address cannot be null.");
        ((CraftIpBanList) this.getBanList(Type.IP)).addBan(address, (String) null, (Date) null, (String) null);
    }

    public void unbanIP(InetAddress address) {
        Preconditions.checkArgument(address != null, "Address cannot be null.");
        ((CraftIpBanList) this.getBanList(Type.IP)).pardon(address);
    }

    public Set getBannedPlayers() {
        HashSet result = new HashSet();
        Iterator iterator = this.playerList.getBans().getEntries().iterator();

        while (iterator.hasNext()) {
            UserBanListEntry entry = (UserBanListEntry) iterator.next();

            result.add(this.getOfflinePlayer((GameProfile) entry.getUser()));
        }

        return result;
    }

    public BanList getBanList(Type type) {
        Preconditions.checkArgument(type != null, "BanList.Type cannot be null");
        Object object;

        switch ($SWITCH_TABLE$org$bukkit$BanList$Type()[type.ordinal()]) {
            case 1:
            case 3:
                object = new CraftProfileBanList(this.playerList.getBans());
                break;
            case 2:
                object = new CraftIpBanList(this.playerList.getIpBans());
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return (BanList) object;
    }

    public void setWhitelist(boolean value) {
        this.playerList.setUsingWhiteList(value);
        this.console.storeUsingWhiteList(value);
    }

    public boolean isWhitelistEnforced() {
        return this.console.isEnforceWhitelist();
    }

    public void setWhitelistEnforced(boolean value) {
        this.console.setEnforceWhitelist(value);
    }

    public Set getWhitelistedPlayers() {
        LinkedHashSet result = new LinkedHashSet();
        Iterator iterator = this.playerList.getWhiteList().getEntries().iterator();

        while (iterator.hasNext()) {
            UserWhiteListEntry entry = (UserWhiteListEntry) iterator.next();

            result.add(this.getOfflinePlayer((GameProfile) entry.getUser()));
        }

        return result;
    }

    public Set getOperators() {
        HashSet result = new HashSet();
        Iterator iterator = this.playerList.getOps().getEntries().iterator();

        while (iterator.hasNext()) {
            ServerOpListEntry entry = (ServerOpListEntry) iterator.next();

            result.add(this.getOfflinePlayer((GameProfile) entry.getUser()));
        }

        return result;
    }

    public void reloadWhitelist() {
        this.playerList.reloadWhiteList();
    }

    public GameMode getDefaultGameMode() {
        return GameMode.getByValue(this.console.getLevel(net.minecraft.world.level.Level.OVERWORLD).K.getGameType().getId());
    }

    public void setDefaultGameMode(GameMode mode) {
        Preconditions.checkArgument(mode != null, "GameMode cannot be null");
        Iterator iterator = this.getWorlds().iterator();

        while (iterator.hasNext()) {
            World world = (World) iterator.next();

            ((CraftWorld) world).getHandle().K.setGameType(GameType.byId(mode.getValue()));
        }

    }

    public ConsoleCommandSender getConsoleSender() {
        return this.console.console;
    }

    public EntityMetadataStore getEntityMetadata() {
        return this.entityMetadata;
    }

    public PlayerMetadataStore getPlayerMetadata() {
        return this.playerMetadata;
    }

    public WorldMetadataStore getWorldMetadata() {
        return this.worldMetadata;
    }

    public File getWorldContainer() {
        return this.getServer().storageSource.getDimensionPath(net.minecraft.world.level.Level.OVERWORLD).getParent().toFile();
    }

    public OfflinePlayer[] getOfflinePlayers() {
        PlayerDataStorage storage = this.console.playerDataStorage;
        String[] files = storage.getPlayerDir().list(new DatFileFilter());
        HashSet players = new HashSet();
        String[] astring = files;
        int i = files.length;

        for (int j = 0; j < i; ++j) {
            String file = astring[j];

            try {
                players.add(this.getOfflinePlayer(UUID.fromString(file.substring(0, file.length() - 4))));
            } catch (IllegalArgumentException illegalargumentexception) {
                ;
            }
        }

        players.addAll(this.getOnlinePlayers());
        return (OfflinePlayer[]) players.toArray(new OfflinePlayer[players.size()]);
    }

    public Messenger getMessenger() {
        return this.messenger;
    }

    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        StandardMessenger.validatePluginMessage(this.getMessenger(), source, channel, message);
        Iterator iterator = this.getOnlinePlayers().iterator();

        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();

            player.sendPluginMessage(source, channel, message);
        }

    }

    public Set getListeningPluginChannels() {
        HashSet result = new HashSet();
        Iterator iterator = this.getOnlinePlayers().iterator();

        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();

            result.addAll(player.getListeningPluginChannels());
        }

        return result;
    }

    public Inventory createInventory(InventoryHolder owner, InventoryType type) {
        Preconditions.checkArgument(type != null, "InventoryType cannot be null");
        Preconditions.checkArgument(type.isCreatable(), "InventoryType.%s cannot be used to create a inventory", type);
        return CraftInventoryCreator.INSTANCE.createInventory(owner, type);
    }

    public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
        Preconditions.checkArgument(type != null, "InventoryType cannot be null");
        Preconditions.checkArgument(type.isCreatable(), "InventoryType.%s cannot be used to create a inventory", type);
        Preconditions.checkArgument(title != null, "title cannot be null");
        return CraftInventoryCreator.INSTANCE.createInventory(owner, type, title);
    }

    public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
        Preconditions.checkArgument(9 <= size && size <= 54 && size % 9 == 0, "Size for custom inventory must be a multiple of 9 between 9 and 54 slots (got %s)", size);
        return CraftInventoryCreator.INSTANCE.createInventory(owner, size);
    }

    public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
        Preconditions.checkArgument(9 <= size && size <= 54 && size % 9 == 0, "Size for custom inventory must be a multiple of 9 between 9 and 54 slots (got %s)", size);
        return CraftInventoryCreator.INSTANCE.createInventory(owner, size, title);
    }

    public Merchant createMerchant(String title) {
        return new CraftMerchantCustom(title == null ? InventoryType.MERCHANT.getDefaultTitle() : title);
    }

    public int getMaxChainedNeighborUpdates() {
        return this.getServer().getMaxChainedNeighborUpdates();
    }

    public HelpMap getHelpMap() {
        return this.helpMap;
    }

    public SimpleCommandMap getCommandMap() {
        return this.commandMap;
    }

    /** @deprecated */
    @Deprecated
    public int getMonsterSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.MONSTER);
    }

    /** @deprecated */
    @Deprecated
    public int getAnimalSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public int getWaterAnimalSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.WATER_ANIMAL);
    }

    /** @deprecated */
    @Deprecated
    public int getWaterAmbientSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.WATER_AMBIENT);
    }

    /** @deprecated */
    @Deprecated
    public int getWaterUndergroundCreatureSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE);
    }

    /** @deprecated */
    @Deprecated
    public int getAmbientSpawnLimit() {
        return this.getSpawnLimit(SpawnCategory.AMBIENT);
    }

    public int getSpawnLimit(SpawnCategory spawnCategory) {
        return this.spawnCategoryLimit.getOrDefault(spawnCategory, -1);
    }

    public boolean isPrimaryThread() {
        return Thread.currentThread().equals(this.console.serverThread) || this.console.hasStopped() || !AsyncCatcher.enabled;
    }

    public String getMotd() {
        return this.console.getMotd();
    }

    public void setMotd(String motd) {
        this.console.setMotd(motd);
    }

    public WarningState getWarningState() {
        return this.warningState;
    }

    public List tabComplete(CommandSender sender, String message, ServerLevel world, Vec3 pos, boolean forceCommand) {
        if (!(sender instanceof Player)) {
            return ImmutableList.of();
        } else {
            Player player = (Player) sender;
            List offers;

            if (!message.startsWith("/") && !forceCommand) {
                offers = this.tabCompleteChat(player, message);
            } else {
                offers = this.tabCompleteCommand(player, message, world, pos);
            }

            TabCompleteEvent tabEvent = new TabCompleteEvent(player, message, offers);

            this.getPluginManager().callEvent(tabEvent);
            return tabEvent.isCancelled() ? Collections.EMPTY_LIST : tabEvent.getCompletions();
        }
    }

    public List tabCompleteCommand(Player player, String message, ServerLevel world, Vec3 pos) {
        if ((SpigotConfig.tabComplete < 0 || message.length() <= SpigotConfig.tabComplete) && !message.contains(" ")) {
            return ImmutableList.of();
        } else {
            List completions = null;

            try {
                if (message.startsWith("/")) {
                    message = message.substring(1);
                }

                if (pos == null) {
                    completions = this.getCommandMap().tabComplete(player, message);
                } else {
                    completions = this.getCommandMap().tabComplete(player, message, CraftLocation.toBukkit(pos, (World) world.getWorld()));
                }
            } catch (CommandException commandexception) {
                player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to tab-complete this command");
                this.getLogger().log(Level.SEVERE, "Exception when " + player.getName() + " attempted to tab complete " + message, commandexception);
            }

            return (List) (completions == null ? ImmutableList.of() : completions);
        }
    }

    public List tabCompleteChat(Player player, String message) {
        ArrayList completions = new ArrayList();
        PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);
        String token = event.getLastToken();
        Iterator iterator = this.getOnlinePlayers().iterator();

        while (iterator.hasNext()) {
            Player p = (Player) iterator.next();

            if (player.canSee(p) && StringUtil.startsWithIgnoreCase(p.getName(), token)) {
                completions.add(p.getName());
            }
        }

        this.pluginManager.callEvent(event);
        Iterator it = completions.iterator();

        while (it.hasNext()) {
            Object current = it.next();

            if (!(current instanceof String)) {
                it.remove();
            }
        }

        Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
        return completions;
    }

    public CraftItemFactory getItemFactory() {
        return CraftItemFactory.instance();
    }

    public CraftScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public Criteria getScoreboardCriteria(String name) {
        return CraftCriteria.getFromBukkit(name);
    }

    public void checkSaveState() {
        if (!this.playerCommandState && !this.printSaveWarning && this.console.autosavePeriod > 0) {
            this.printSaveWarning = true;
            this.getLogger().log(Level.WARNING, "A manual (plugin-induced) save has been detected while server is configured to auto-save. This may affect performance.", this.warningState == WarningState.ON ? new Throwable() : null);
        }
    }

    public CraftIconCache getServerIcon() {
        return this.icon;
    }

    public CraftIconCache loadServerIcon(File file) throws Exception {
        Preconditions.checkArgument(file != null, "File cannot be null");
        Preconditions.checkArgument(file.isFile(), "File (%s) is not a valid file", file);
        return loadServerIcon0(file);
    }

    static CraftIconCache loadServerIcon0(File file) throws Exception {
        return loadServerIcon0(ImageIO.read(file));
    }

    public CraftIconCache loadServerIcon(BufferedImage image) throws Exception {
        Preconditions.checkArgument(image != null, "BufferedImage image cannot be null");
        return loadServerIcon0(image);
    }

    static CraftIconCache loadServerIcon0(BufferedImage image) throws Exception {
        Preconditions.checkArgument(image.getWidth() == 64, "BufferedImage must be 64 pixels wide (%s)", image.getWidth());
        Preconditions.checkArgument(image.getHeight() == 64, "BufferedImage must be 64 pixels high (%s)", image.getHeight());
        ByteArrayOutputStream bytebuf = new ByteArrayOutputStream();

        ImageIO.write(image, "PNG", bytebuf);
        return new CraftIconCache(bytebuf.toByteArray());
    }

    public void setIdleTimeout(int threshold) {
        this.console.setPlayerIdleTimeout(threshold);
    }

    public int getIdleTimeout() {
        return this.console.getPlayerIdleTimeout();
    }

    public ChunkData createChunkData(World world) {
        Preconditions.checkArgument(world != null, "World cannot be null");
        ServerLevel handle = ((CraftWorld) world).getHandle();

        return new OldCraftChunkData(world.getMinHeight(), world.getMaxHeight(), handle.registryAccess().registryOrThrow(Registries.BIOME));
    }

    public BossBar createBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
        return new CraftBossBar(title, color, style, flags);
    }

    public KeyedBossBar createBossBar(NamespacedKey key, String title, BarColor barColor, BarStyle barStyle, BarFlag... barFlags) {
        Preconditions.checkArgument(key != null, "NamespacedKey key cannot be null");
        Preconditions.checkArgument(barColor != null, "BarColor key cannot be null");
        Preconditions.checkArgument(barStyle != null, "BarStyle key cannot be null");
        CustomBossEvent bossBattleCustom = this.getServer().getCustomBossEvents().create(CraftNamespacedKey.toMinecraft(key), CraftChatMessage.fromString(title, true)[0]);
        CraftKeyedBossbar craftKeyedBossbar = new CraftKeyedBossbar(bossBattleCustom);

        craftKeyedBossbar.setColor(barColor);
        craftKeyedBossbar.setStyle(barStyle);
        BarFlag[] abarflag = barFlags;
        int i = barFlags.length;

        for (int j = 0; j < i; ++j) {
            BarFlag flag = abarflag[j];

            if (flag != null) {
                craftKeyedBossbar.addFlag(flag);
            }
        }

        return craftKeyedBossbar;
    }

    public Iterator getBossBars() {
        return Iterators.unmodifiableIterator(Iterators.transform(this.getServer().getCustomBossEvents().getEvents().iterator(), new Function() {
            public KeyedBossBar apply(CustomBossEvent bossBattleCustom) {
                return bossBattleCustom.getBukkitEntity();
            }
        }));
    }

    public KeyedBossBar getBossBar(NamespacedKey key) {
        Preconditions.checkArgument(key != null, "key");
        CustomBossEvent bossBattleCustom = this.getServer().getCustomBossEvents().get(CraftNamespacedKey.toMinecraft(key));

        return bossBattleCustom == null ? null : bossBattleCustom.getBukkitEntity();
    }

    public boolean removeBossBar(NamespacedKey key) {
        Preconditions.checkArgument(key != null, "key");
        CustomBossEvents bossBattleCustomData = this.getServer().getCustomBossEvents();
        CustomBossEvent bossBattleCustom = bossBattleCustomData.get(CraftNamespacedKey.toMinecraft(key));

        if (bossBattleCustom != null) {
            bossBattleCustomData.remove(bossBattleCustom);
            return true;
        } else {
            return false;
        }
    }

    public Entity getEntity(UUID uuid) {
        Preconditions.checkArgument(uuid != null, "UUID id cannot be null");
        Iterator iterator = this.getServer().getAllLevels().iterator();

        while (iterator.hasNext()) {
            ServerLevel world = (ServerLevel) iterator.next();
            net.minecraft.world.entity.Entity entity = world.getEntity(uuid);

            if (entity != null) {
                return entity.getBukkitEntity();
            }
        }

        return null;
    }

    public Advancement getAdvancement(NamespacedKey key) {
        Preconditions.checkArgument(key != null, "NamespacedKey key cannot be null");
        AdvancementHolder advancement = this.console.getAdvancements().get(CraftNamespacedKey.toMinecraft(key));

        return advancement == null ? null : advancement.toBukkit();
    }

    public Iterator advancementIterator() {
        return Iterators.unmodifiableIterator(Iterators.transform(this.console.getAdvancements().getAllAdvancements().iterator(), new Function() {
            public Advancement apply(AdvancementHolder advancement) {
                return advancement.toBukkit();
            }
        }));
    }

    public BlockData createBlockData(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        return this.createBlockData(material, (String) null);
    }

    public BlockData createBlockData(Material material, Consumer consumer) {
        BlockData data = this.createBlockData(material);

        if (consumer != null) {
            consumer.accept(data);
        }

        return data;
    }

    public BlockData createBlockData(String data) throws IllegalArgumentException {
        Preconditions.checkArgument(data != null, "data cannot be null");
        return this.createBlockData((Material) null, data);
    }

    public BlockData createBlockData(Material material, String data) {
        Preconditions.checkArgument(material != null || data != null, "Must provide one of material or data");
        return CraftBlockData.newData(material, data);
    }

    public Tag getTag(String registry, NamespacedKey tag, Class clazz) {
        Preconditions.checkArgument(registry != null, "registry cannot be null");
        Preconditions.checkArgument(tag != null, "NamespacedKey tag cannot be null");
        Preconditions.checkArgument(clazz != null, "Class clazz cannot be null");
        ResourceLocation key = CraftNamespacedKey.toMinecraft(tag);
        TagKey entityTagKey;

        switch (registry.hashCode()) {
            case -1386164858:
                if (!registry.equals("blocks")) {
                    throw new IllegalArgumentException();
                }

                Preconditions.checkArgument(clazz == Material.class, "Block namespace (%s) must have material type", clazz.getName());
                entityTagKey = TagKey.create(Registries.BLOCK, key);
                if (BuiltInRegistries.BLOCK.getTag(entityTagKey).isPresent()) {
                    return new CraftBlockTag(BuiltInRegistries.BLOCK, entityTagKey);
                }
                break;
            case -1271463959:
                if (!registry.equals("fluids")) {
                    throw new IllegalArgumentException();
                }

                Preconditions.checkArgument(clazz == Fluid.class, "Fluid namespace (%s) must have fluid type", clazz.getName());
                entityTagKey = TagKey.create(Registries.FLUID, key);
                if (BuiltInRegistries.FLUID.getTag(entityTagKey).isPresent()) {
                    return new CraftFluidTag(BuiltInRegistries.FLUID, entityTagKey);
                }
                break;
            case 100526016:
                if (!registry.equals("items")) {
                    throw new IllegalArgumentException();
                }

                Preconditions.checkArgument(clazz == Material.class, "Item namespace (%s) must have material type", clazz.getName());
                entityTagKey = TagKey.create(Registries.ITEM, key);
                if (BuiltInRegistries.ITEM.getTag(entityTagKey).isPresent()) {
                    return new CraftItemTag(BuiltInRegistries.ITEM, entityTagKey);
                }
                break;
            case 1078323485:
                if (registry.equals("entity_types")) {
                    Preconditions.checkArgument(clazz == EntityType.class, "Entity type namespace (%s) must have entity type", clazz.getName());
                    entityTagKey = TagKey.create(Registries.ENTITY_TYPE, key);
                    if (BuiltInRegistries.ENTITY_TYPE.getTag(entityTagKey).isPresent()) {
                        return new CraftEntityTag(BuiltInRegistries.ENTITY_TYPE, entityTagKey);
                    }
                    break;
                }

                throw new IllegalArgumentException();
            default:
                throw new IllegalArgumentException();
        }

        return null;
    }

    public Iterable getTags(String registry, Class clazz) {
        Preconditions.checkArgument(registry != null, "registry cannot be null");
        Preconditions.checkArgument(clazz != null, "Class clazz cannot be null");
        DefaultedRegistry entityTags;

        switch (registry.hashCode()) {
            case -1386164858:
                if (registry.equals("blocks")) {
                    Preconditions.checkArgument(clazz == Material.class, "Block namespace (%s) must have material type", clazz.getName());
                    entityTags = BuiltInRegistries.BLOCK;
                    return (Iterable) entityTags.getTags().map((pairx) -> {
                        return new CraftBlockTag(entityTags, (TagKey) pairx.getFirst());
                    }).collect(ImmutableList.toImmutableList());
                }
                break;
            case -1271463959:
                if (registry.equals("fluids")) {
                    Preconditions.checkArgument(clazz == Material.class, "Fluid namespace (%s) must have fluid type", clazz.getName());
                    entityTags = BuiltInRegistries.FLUID;
                    return (Iterable) entityTags.getTags().map((pairx) -> {
                        return new CraftFluidTag(entityTags, (TagKey) pairx.getFirst());
                    }).collect(ImmutableList.toImmutableList());
                }
                break;
            case 100526016:
                if (registry.equals("items")) {
                    Preconditions.checkArgument(clazz == Material.class, "Item namespace (%s) must have material type", clazz.getName());
                    entityTags = BuiltInRegistries.ITEM;
                    return (Iterable) entityTags.getTags().map((pairx) -> {
                        return new CraftItemTag(entityTags, (TagKey) pairx.getFirst());
                    }).collect(ImmutableList.toImmutableList());
                }
                break;
            case 1078323485:
                if (registry.equals("entity_types")) {
                    Preconditions.checkArgument(clazz == EntityType.class, "Entity type namespace (%s) must have entity type", clazz.getName());
                    entityTags = BuiltInRegistries.ENTITY_TYPE;
                    return (Iterable) entityTags.getTags().map((pairx) -> {
                        return new CraftEntityTag(entityTags, (TagKey) pairx.getFirst());
                    }).collect(ImmutableList.toImmutableList());
                }
        }

        throw new IllegalArgumentException();
    }

    public LootTable getLootTable(NamespacedKey key) {
        Preconditions.checkArgument(key != null, "NamespacedKey key cannot be null");
        LootDataManager registry = this.getServer().getLootData();

        return new CraftLootTable(key, registry.getLootTable(CraftNamespacedKey.toMinecraft(key)));
    }

    public List selectEntities(CommandSender sender, String selector) {
        Preconditions.checkArgument(selector != null, "selector cannot be null");
        Preconditions.checkArgument(sender != null, "CommandSender sender cannot be null");
        EntityArgument arg = EntityArgument.entities();

        List nms;

        try {
            StringReader reader = new StringReader(selector);

            nms = arg.parse(reader, true).findEntities(VanillaCommandWrapper.getListener(sender));
            Preconditions.checkArgument(!reader.canRead(), "Spurious trailing data in selector: %s", selector);
        } catch (CommandSyntaxException commandsyntaxexception) {
            throw new IllegalArgumentException("Could not parse selector: " + selector, commandsyntaxexception);
        }

        return new ArrayList(Lists.transform(nms, (entityx) -> {
            return entityx.getBukkitEntity();
        }));
    }

    public StructureManager getStructureManager() {
        return this.structureManager;
    }

    public org.bukkit.Registry getRegistry(Class aClass) {
        return (org.bukkit.Registry) this.registries.computeIfAbsent(aClass, (keyx) -> {
            return CraftRegistry.createRegistry(aClass, this.console.registryAccess());
        });
    }

    /** @deprecated */
    @Deprecated
    public UnsafeValues getUnsafe() {
        return CraftMagicNumbers.INSTANCE;
    }

    public Spigot spigot() {
        return this.spigot;
    }

    static int[] $SWITCH_TABLE$org$bukkit$World$Environment() {
        int[] aint = CraftServer.$SWITCH_TABLE$org$bukkit$World$Environment;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Environment.values().length];

            try {
                aint1[Environment.CUSTOM.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Environment.NETHER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Environment.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Environment.THE_END.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            CraftServer.$SWITCH_TABLE$org$bukkit$World$Environment = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$BanList$Type() {
        int[] aint = CraftServer.$SWITCH_TABLE$org$bukkit$BanList$Type;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Type.values().length];

            try {
                aint1[Type.IP.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Type.NAME.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Type.PROFILE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftServer.$SWITCH_TABLE$org$bukkit$BanList$Type = aint1;
            return aint1;
        }
    }
}
