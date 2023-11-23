package org.kettingpowered.ketting.config;

import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kettingpowered.ketting.config.value.Value;
import org.kettingpowered.ketting.core.Ketting;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractConfig {

    private final String[] header;
    public final File file;
    private final int configVersion;

    protected YamlConfiguration config;

    private List<Value<?>> configValues;

    public AbstractConfig(@Nullable String[] header, @NotNull String fileName, int configVersion) {
        Validate.notNull(fileName, "fileName cannot be null");
        Validate.notBlank(fileName, "fileName cannot be blank");

        this.header = header;
        this.file = new File(fileName);
        this.configVersion = configVersion;
    }

    public AbstractConfig(String fileName, int configVersion) {
        this(null, fileName, configVersion);
    }

    public void load() {
        if (config != null) return;
        config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);

        if (config.getInt("config-version", 0) != configVersion) {
            config = new YamlConfiguration();
            config.options().copyDefaults(true);
            collectConfigValues().forEach(v -> config.addDefault(v.getPath(), v.getValue()));
            config.set("config-version", configVersion);
            save();
            return;
        }

        collectConfigValues().forEach(v -> v.setValue(config.getString(v.getPath())));
        save();
    }

    public void save() {
        try {
            if (header != null) {
                List<String> header = new ArrayList<>();
                Collections.addAll(header, this.header);
                collectConfigValues()
                        .stream()
                        .filter(Value::hasDescription)
                        .forEach(v -> header.add("Value: " + v.getPath() + " Default: " + v.getValue() + "   # " + v.getDescription()));
                config.options().setHeader(header);
            }
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Value<?>> collectConfigValues() {
        if (configValues != null)
            return configValues;

        configValues = new ArrayList<>();

        for (Field field : getClass().getDeclaredFields()) {
            if (Value.class.isAssignableFrom(field.getType())) {
                try {
                    if (!field.trySetAccessible()) {
                        Ketting.LOGGER.warn("Could not set '" + field.getName() + "' accessible");
                        continue;
                    }
                    final Value<?> v = (Value<?>) field.get(this);
                    if (v == null) continue;
                    configValues.add(v);
                    config.addDefault(v.getPath(), v.getValue());
                } catch (IllegalAccessException e) {
                    Ketting.LOGGER.warn("Could not read '" + field.getName() + "'", e);
                }
            }
        }

        Ketting.LOGGER.info("Loaded {} config values", configValues.size());
        return configValues;
    }
}
