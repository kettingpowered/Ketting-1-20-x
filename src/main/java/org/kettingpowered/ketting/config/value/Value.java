package org.kettingpowered.ketting.config.value;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class Value<C> {

    private String path;
    protected C type;
    private String description;

    public Value(@NotNull String path, @NotNull C defaultValue, @NotNull String description) {
        Validate.notNull(path, "path cannot be null");
        Validate.notBlank(path, "path cannot be blank");
        Validate.notNull(defaultValue, "defaultValue cannot be null");
        Validate.notNull(description, "description cannot be null");

        this.path = path;
        this.type = defaultValue;
        this.description = description;
    }

    public Value(@NotNull String path, @NotNull C defaultValue) {
        this(path, defaultValue, "");
    }

    public C getValue() {
        return type;
    }

    public abstract void setValue(String value);

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasDescription() {
        return !description.isEmpty();
    }
}
