package org.kettingpowered.ketting.config;

import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.value.types.*;

public class KettingConfig extends AbstractConfig {

    private static final KettingConfig INSTANCE;

    static {
        INSTANCE = new KettingConfig("ketting.yml");
    }

    public static KettingConfig getInstance() {
        return INSTANCE;
    }

    public KettingConfig(@NotNull String fileName) {
        super(new String[] {
                "This is the main configuration file for Ketting.",
                "",
                "Site: https://kettingpowered.org/",
                "Github: https://github.com/kettingpowered/",
                "Discord: https://discord.kettingpowered.org/",
                ""
        }, fileName, 5);
        load();
    }

    //Start of config values
    public final BooleanValue PRINT_INJECTIONS = new BooleanValue("debug.print_injections", false, "Print all values injected into Bukkit to the console.");
    public final BooleanValue WARN_ON_UNKNOWN_ENTITY = new BooleanValue("debug.warn_on_unknown_entity", true, "Print a warning to the console when an entity unknown to Bukkit is spawned.");
    public final BooleanValue WARN_ON_NULL_NBT = new BooleanValue("debug.warn_on_null_nbt", true, "Print a warning to the console when something tries to set a null NBT tag.");
    public final BooleanValue WARN_ON_UNKNOWN_STRUCTURE_TYPE = new BooleanValue("debug.warn_on_unknown_structure_type", true, "Print a warning to the console when a structure type is unknown to Bukkit.");

    public final BooleanValue REROUTE_FORGE_COMMAND_PERMISSIONS = new BooleanValue("ketting.reroute_forge_command_permissions", false, "If true, Ketting will reroute Forge command permissions to Bukkit permissions. This is useful if you want to use a permission plugin like LuckPerms with Forge commands.");
    public final BooleanValue SILENCE_PARSING_ERRORS = new BooleanValue("forge.silence_parsing_errors", true, "If true, this will minimize some general parsing errors from appearing in the console");

    public final BooleanValue MERGE_WORLD_SYSTEMS = new BooleanValue("ketting.merge_world_systems", false, "If true, this will attempt to merge both the Forge and Bukkit world system into one, making dimensions exist in the world folder, and Bukkit worlds in their own folder.");
    public final BooleanValue SPLIT_WORLD_NAMES = new BooleanValue("ketting.split_world_names", false, "If true, this would change dimension names ONLY on the Bukkit side, making Bukkit believe that world/DIM-1 actually is world_nether");

    public final BooleanValue HALT_EXIT = new BooleanValue("ketting.halt.exit", false, "If true, Ketting will call System.exit(255), once it is supposed to regularly shutdown. This is useful, if some plugins or mods keep the server up unintentionally.");
    public final BooleanValue HALT_HALT = new BooleanValue("ketting.halt.halt", true, "If true, Ketting will call Runtime.getRuntime().halt(), once it is supposed to regularly shutdown. Overrides 'ketting.halt.exit'. THIS SKIPS SHUTDOWN HOOKS. SOME STUFF MIGHT BREAK! IF YOU CAN, ENABLE 'ketting.halt.exit' INSTEAD!");
    public final IntValue HALT_THREADDUMP_SLEEP = new IntValue("ketting.halt.thread_dump.sleep_time", 5000, "If true, Ketting will sleep this many seconds before printing the thread-dump.");
    public final BooleanValue HALT_THREADDUMP_ENABLE = new BooleanValue("ketting.halt.thread_dump.enabled", false, "If true, Ketting will print a Stacktrace of all threads once the server is supposed to regularly shutdown. This option is intended to aid debugging for the option 'ketting.force.halt'.");

    public final BooleanValue VELOCITY_SUPPORT_ENABLED = new BooleanValue("ketting.velocity_support.enabled", false, "If true, Ketting will enable support for Velocity proxy forwarding.");
    public final BooleanValue VELOCITY_LEGACY_FORWARDING = new BooleanValue("ketting.velocity_support.legacy_forwarding", false, "If true, Ketting will use the legacy Velocity forwarding method. This is not recommended, as it is less secure.");
    public final StringValue VELOCITY_FORWARDING_SECRET = new StringValue("ketting.velocity_support.forwarding-secret", "", "The secret key used for Velocity proxy forwarding. This should be the same as the contents of the 'forwarding.secret' file in your Velocity server root.");
    //End of config values
}
