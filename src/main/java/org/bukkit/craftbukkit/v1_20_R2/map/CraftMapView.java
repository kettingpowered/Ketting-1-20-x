package org.bukkit.craftbukkit.v1_20_R2.map;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public final class CraftMapView implements MapView {

    private final Map renderCache = new HashMap();
    private final List renderers = new ArrayList();
    private final Map canvases = new HashMap();
    protected final MapItemSavedData worldMap;

    public CraftMapView(MapItemSavedData worldMap) {
        this.worldMap = worldMap;
        this.addRenderer(new CraftMapRenderer(this, worldMap));
    }

    public int getId() {
        String text = this.worldMap.id;

        Preconditions.checkState(text.startsWith("map_"), "Map has a invalid ID");

        try {
            return Integer.parseInt(text.substring("map_".length()));
        } catch (NumberFormatException numberformatexception) {
            throw new IllegalStateException("Map has non-numeric ID");
        }
    }

    public boolean isVirtual() {
        return this.renderers.size() > 0 && !(this.renderers.get(0) instanceof CraftMapRenderer);
    }

    public Scale getScale() {
        return Scale.valueOf(this.worldMap.scale);
    }

    public void setScale(Scale scale) {
        this.worldMap.scale = scale.getValue();
    }

    public World getWorld() {
        ResourceKey dimension = this.worldMap.dimension;
        ServerLevel world = MinecraftServer.getServer().getLevel(dimension);

        return (World) (world != null ? world.getWorld() : (this.worldMap.uniqueId != null ? Bukkit.getServer().getWorld(this.worldMap.uniqueId) : null));
    }

    public void setWorld(World world) {
        this.worldMap.dimension = ((CraftWorld) world).getHandle().dimension();
        this.worldMap.uniqueId = world.getUID();
    }

    public int getCenterX() {
        return this.worldMap.centerX;
    }

    public int getCenterZ() {
        return this.worldMap.centerZ;
    }

    public void setCenterX(int x) {
        this.worldMap.centerX = x;
    }

    public void setCenterZ(int z) {
        this.worldMap.centerZ = z;
    }

    public List getRenderers() {
        return new ArrayList(this.renderers);
    }

    public void addRenderer(MapRenderer renderer) {
        if (!this.renderers.contains(renderer)) {
            this.renderers.add(renderer);
            this.canvases.put(renderer, new HashMap());
            renderer.initialize(this);
        }

    }

    public boolean removeRenderer(MapRenderer renderer) {
        if (!this.renderers.contains(renderer)) {
            return false;
        } else {
            this.renderers.remove(renderer);
            Iterator iterator = ((Map) this.canvases.get(renderer)).entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                for (int x = 0; x < 128; ++x) {
                    for (int y = 0; y < 128; ++y) {
                        ((CraftMapCanvas) entry.getValue()).setPixel(x, y, (byte) -1);
                    }
                }
            }

            this.canvases.remove(renderer);
            return true;
        }
    }

    private boolean isContextual() {
        Iterator iterator = this.renderers.iterator();

        while (iterator.hasNext()) {
            MapRenderer renderer = (MapRenderer) iterator.next();

            if (renderer.isContextual()) {
                return true;
            }
        }

        return false;
    }

    public RenderData render(CraftPlayer player) {
        boolean context = this.isContextual();
        RenderData render = (RenderData) this.renderCache.get(context ? player : null);

        if (render == null) {
            render = new RenderData();
            this.renderCache.put(context ? player : null, render);
        }

        if (context && this.renderCache.containsKey((Object) null)) {
            this.renderCache.remove((Object) null);
        }

        Arrays.fill(render.buffer, (byte) 0);
        render.cursors.clear();
        Iterator iterator = this.renderers.iterator();

        while (iterator.hasNext()) {
            MapRenderer renderer = (MapRenderer) iterator.next();
            CraftMapCanvas canvas = (CraftMapCanvas) ((Map) this.canvases.get(renderer)).get(renderer.isContextual() ? player : null);

            if (canvas == null) {
                canvas = new CraftMapCanvas(this);
                ((Map) this.canvases.get(renderer)).put(renderer.isContextual() ? player : null, canvas);
            }

            canvas.setBase(render.buffer);

            try {
                renderer.render(this, canvas, player);
            } catch (Throwable throwable) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not render map using renderer " + renderer.getClass().getName(), throwable);
            }

            byte[] buf = canvas.getBuffer();

            int i;

            for (i = 0; i < buf.length; ++i) {
                byte color = buf[i];

                if (color >= 0 || color <= -9) {
                    render.buffer[i] = color;
                }
            }

            for (i = 0; i < canvas.getCursors().size(); ++i) {
                render.cursors.add(canvas.getCursors().getCursor(i));
            }
        }

        return render;
    }

    public boolean isTrackingPosition() {
        return this.worldMap.trackingPosition;
    }

    public void setTrackingPosition(boolean trackingPosition) {
        this.worldMap.trackingPosition = trackingPosition;
    }

    public boolean isUnlimitedTracking() {
        return this.worldMap.unlimitedTracking;
    }

    public void setUnlimitedTracking(boolean unlimited) {
        this.worldMap.unlimitedTracking = unlimited;
    }

    public boolean isLocked() {
        return this.worldMap.locked;
    }

    public void setLocked(boolean locked) {
        this.worldMap.locked = locked;
    }
}
