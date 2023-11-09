package org.bukkit.craftbukkit.v1_20_R2.map;

import org.bukkit.map.MapCursor;

import java.util.ArrayList;

public class RenderData {

    public final byte[] buffer = new byte[128 * 128];
    public final ArrayList<MapCursor> cursors = new ArrayList<>();
}
