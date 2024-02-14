package org.kettingpowered.ketting.entity;

import java.util.ArrayList;
import java.util.List;

public final class UnknownEntity {

    private static final List<String> warnedUnknownEntities = new ArrayList<>();

    public static void warned(String entity) {
        if (!warnedUnknownEntities.contains(entity))
            warnedUnknownEntities.add(entity);
    }

    public static boolean isWarned(String entity) {
        return warnedUnknownEntities.contains(entity);
    }
}
