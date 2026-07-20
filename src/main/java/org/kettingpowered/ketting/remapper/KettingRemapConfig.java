package org.kettingpowered.ketting.remapper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public record KettingRemapConfig(boolean remap) {
    public static final KettingRemapConfig PLUGIN = new KettingRemapConfig(true);

    public KettingRemapConfig copy() {
        return new KettingRemapConfig(remap);
    }

    public int write(DataOutput output) throws IOException {
        output.writeBoolean(remap);
        return 1;
    }

    public static KettingRemapConfig read(DataInput input) throws IOException {
        return new KettingRemapConfig(input.readBoolean());
    }
}
