package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.StringTag;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaMap extends CraftMetaItem implements MapMeta {

    static final CraftMetaItem.ItemMetaKey MAP_SCALING = new CraftMetaItem.ItemMetaKey("map_is_scaling", "scaling");
    /** @deprecated */
    @Deprecated
    static final CraftMetaItem.ItemMetaKey MAP_LOC_NAME = new CraftMetaItem.ItemMetaKey("LocName", "display-loc-name");
    static final CraftMetaItem.ItemMetaKey MAP_COLOR = new CraftMetaItem.ItemMetaKey("MapColor", "display-map-color");
    static final CraftMetaItem.ItemMetaKey MAP_ID = new CraftMetaItem.ItemMetaKey("map", "map-id");
    static final byte SCALING_EMPTY = 0;
    static final byte SCALING_TRUE = 1;
    static final byte SCALING_FALSE = 2;
    private Integer mapId;
    private byte scaling = 0;
    private Color color;

    CraftMetaMap(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaMap) {
            CraftMetaMap map = (CraftMetaMap) meta;

            this.mapId = map.mapId;
            this.scaling = map.scaling;
            this.color = map.color;
        }
    }

    CraftMetaMap(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaMap.MAP_ID.NBT, 99)) {
            this.mapId = tag.getInt(CraftMetaMap.MAP_ID.NBT);
        }

        if (tag.contains(CraftMetaMap.MAP_SCALING.NBT)) {
            this.scaling = (byte) (tag.getBoolean(CraftMetaMap.MAP_SCALING.NBT) ? 1 : 2);
        }

        if (tag.contains(CraftMetaMap.DISPLAY.NBT)) {
            CompoundTag display = tag.getCompound(CraftMetaMap.DISPLAY.NBT);

            if (display.contains(CraftMetaMap.MAP_COLOR.NBT)) {
                try {
                    this.color = Color.fromRGB(display.getInt(CraftMetaMap.MAP_COLOR.NBT));
                } catch (IllegalArgumentException illegalargumentexception) {
                    ;
                }
            }
        }

    }

    CraftMetaMap(Map map) {
        super(map);
        Integer id = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaMap.MAP_ID.BUKKIT, true);

        if (id != null) {
            this.setMapId(id);
        }

        Boolean scaling = (Boolean) CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, CraftMetaMap.MAP_SCALING.BUKKIT, true);

        if (scaling != null) {
            this.setScaling(scaling);
        }

        String locName = CraftMetaItem.SerializableMeta.getString(map, CraftMetaMap.MAP_LOC_NAME.BUKKIT, true);

        if (locName != null) {
            this.setLocationName(locName);
        }

        Color color = (Color) CraftMetaItem.SerializableMeta.getObject(Color.class, map, CraftMetaMap.MAP_COLOR.BUKKIT, true);

        if (color != null) {
            this.setColor(color);
        }

    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.hasMapId()) {
            tag.putInt(CraftMetaMap.MAP_ID.NBT, this.getMapId());
        }

        if (this.hasScaling()) {
            tag.putBoolean(CraftMetaMap.MAP_SCALING.NBT, this.isScaling());
        }

        if (this.hasLocationName()) {
            this.setDisplayTag(tag, CraftMetaMap.MAP_LOC_NAME.NBT, StringTag.valueOf(this.getLocationName()));
        }

        if (this.hasColor()) {
            this.setDisplayTag(tag, CraftMetaMap.MAP_COLOR.NBT, IntTag.valueOf(this.color.asRGB()));
        }

    }

    boolean applicableTo(Material type) {
        return type == Material.FILLED_MAP;
    }

    boolean isEmpty() {
        return super.isEmpty() && this.isMapEmpty();
    }

    boolean isMapEmpty() {
        return !this.hasMapId() && !(this.hasScaling() | this.hasLocationName()) && !this.hasColor();
    }

    public boolean hasMapId() {
        return this.mapId != null;
    }

    public int getMapId() {
        return this.mapId;
    }

    public void setMapId(int id) {
        this.mapId = id;
    }

    public boolean hasMapView() {
        return this.mapId != null;
    }

    public MapView getMapView() {
        Preconditions.checkState(this.hasMapView(), "Item does not have map associated - check hasMapView() first!");
        return Bukkit.getMap(this.mapId);
    }

    public void setMapView(MapView map) {
        this.mapId = map != null ? map.getId() : null;
    }

    boolean hasScaling() {
        return this.scaling != 0;
    }

    public boolean isScaling() {
        return this.scaling == 1;
    }

    public void setScaling(boolean scaling) {
        this.scaling = (byte) (scaling ? 1 : 2);
    }

    public boolean hasLocationName() {
        return this.hasLocalizedName();
    }

    public String getLocationName() {
        return this.getLocalizedName();
    }

    public void setLocationName(String name) {
        this.setLocalizedName(name);
    }

    public boolean hasColor() {
        return this.color != null;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaMap)) {
            return true;
        } else {
            CraftMetaMap that = (CraftMetaMap) meta;

            if (this.scaling == that.scaling) {
                if (this.hasMapId()) {
                    if (!that.hasMapId() || !this.mapId.equals(that.mapId)) {
                        return false;
                    }
                } else if (that.hasMapId()) {
                    return false;
                }

                if (this.hasColor()) {
                    if (!that.hasColor() || !this.color.equals(that.color)) {
                        return false;
                    }
                } else if (that.hasColor()) {
                    return false;
                }

                return true;
            } else {
                return false;
            }
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaMap || this.isMapEmpty());
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasMapId()) {
            hash = 61 * hash + this.mapId.hashCode();
        }

        if (this.hasScaling()) {
            hash ^= 572662306 << (this.isScaling() ? 1 : -1);
        }

        if (this.hasColor()) {
            hash = 61 * hash + this.color.hashCode();
        }

        return original != hash ? CraftMetaMap.class.hashCode() ^ hash : hash;
    }

    public CraftMetaMap clone() {
        return (CraftMetaMap) super.clone();
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasMapId()) {
            builder.put(CraftMetaMap.MAP_ID.BUKKIT, this.getMapId());
        }

        if (this.hasScaling()) {
            builder.put(CraftMetaMap.MAP_SCALING.BUKKIT, this.isScaling());
        }

        if (this.hasColor()) {
            builder.put(CraftMetaMap.MAP_COLOR.BUKKIT, this.getColor());
        }

        return builder;
    }
}
