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

    public final BooleanValue CHECK_FOR_UPDATES = new BooleanValue("updates.check_for_updates", true, "Check for updates on startup.");
    //End of config values
}
