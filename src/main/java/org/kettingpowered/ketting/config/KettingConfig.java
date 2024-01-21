package org.kettingpowered.ketting.config;

import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.value.Value;
import org.kettingpowered.ketting.config.value.types.BooleanValue;
import org.kettingpowered.ketting.core.Ketting;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

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
        }, fileName, 1);
        load();
    }

    //Start of config values
    public final BooleanValue PRINT_INJECTIONS = new BooleanValue("debug.print_injections", false, "Print all values injected into Bukkit to the console.");
    public final BooleanValue WARN_ON_UNKNOWN_ENTITY = new BooleanValue("debug.warn_on_unknown_entity", true, "Print a warning to the console when an entity unknown to Bukkit is spawned.");

    public final BooleanValue OVERWRITE_FORGE_PERMISSIONS = new BooleanValue("forge.overwrite_forge_permissions", false, "--- WARNING - THIS WILL COMPLETELY DISABLE FORGE PERMISSION CHECKS ---  Overwrite Forge permissions with Bukkit permissions, makes it possible to use a permission manager plugin for modded commands. If true, Forge permissions will be set to 'forge.command.MODDEDCOMMAND' where MODDEDCOMMAND is the name of the modded command.");
    //End of config values
}
