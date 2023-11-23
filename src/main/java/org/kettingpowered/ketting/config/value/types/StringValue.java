package org.kettingpowered.ketting.config.value.types;

import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.value.Value;

public class StringValue extends Value<String> {

    public StringValue(@NotNull String path, @NotNull String defaultValue, @NotNull String description) {
        super(path, defaultValue, description);
    }

    public StringValue(@NotNull String path, @NotNull String defaultValue) {
        super(path, defaultValue);
    }

    public void setValue(String value) {
        type = value;
    }
}
