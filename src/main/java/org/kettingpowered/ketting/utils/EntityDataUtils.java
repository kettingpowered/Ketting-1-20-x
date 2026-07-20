package org.kettingpowered.ketting.utils;

import net.minecraft.network.syncher.SynchedEntityData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityDataUtils {

    public static <T> boolean classesMatch(SynchedEntityData.DataItem<T> dataitem, T value) {
        if (dataitem.getValue() == null || value == null) return true; //Just skip if one of them is null
        if (exceptionCheck(dataitem.getValue(), value)) return true;
        return dataitem.getValue().getClass().isAssignableFrom(value.getClass());
    }

    private static <T> boolean exceptionCheck(T data, T value) {
        var className = data.getClass().getName();
        if (className.equals("kotlin.collections.EmptySet") && value instanceof Set<?>) return true;
        if (className.equals("java.util.Collections$EmptyMap") && value instanceof Map<?, ?>) return true;
        if (className.equals("java.util.Collections$EmptyList") && value instanceof List<?>) return true;
        return false;
    }
}
