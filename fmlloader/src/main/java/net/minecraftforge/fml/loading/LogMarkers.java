/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.fml.loading;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogMarkers {
    public static final Marker CORE = MarkerFactory.getMarker("CORE");
    public static final Marker LOADING = MarkerFactory.getMarker("LOADING");
    public static final Marker SCAN = MarkerFactory.getMarker("SCAN");
    public static final Marker SPLASH = MarkerFactory.getMarker("SPLASH");
    public static final Marker DYNAMIC_EXCLUDE_ERRORS = MarkerFactory.getMarker("DYNAMIC_EXCLUDE_ERRORS");
    public static final Marker DYNAMIC_EXCLUDE_EXCLUDES = MarkerFactory.getMarker("DYNAMIC_EXCLUDE_EXCLUDES");
}
