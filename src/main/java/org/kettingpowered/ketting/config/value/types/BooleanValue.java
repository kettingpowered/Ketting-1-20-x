package org.kettingpowered.ketting.config.value.types;

import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.value.Value;

public class BooleanValue extends Value<Boolean> {

    public BooleanValue(@NotNull String path, boolean defaultValue, @NotNull String description) {
        super(path, defaultValue, description);
    }

    public BooleanValue(@NotNull String path, boolean defaultValue) {
        super(path, defaultValue);
    }

    public void setValue(String value) {
        type = Boolean.parseBoolean(value);
    }
}
