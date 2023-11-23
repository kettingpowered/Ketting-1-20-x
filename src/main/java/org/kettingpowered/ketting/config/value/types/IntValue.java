package org.kettingpowered.ketting.config.value.types;

import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.value.Value;

public class IntValue extends Value<Integer> {

    public IntValue(@NotNull String path, int defaultValue, @NotNull String description) {
        super(path, defaultValue, description);
    }

    public IntValue(@NotNull String path, int defaultValue) {
        super(path, defaultValue);
    }

    public void setValue(String value) {
        type = Integer.parseInt(value);
    }
}
