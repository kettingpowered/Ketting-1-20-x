package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.DataResult;
import java.util.Map;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.inventory.meta.CompassMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaCompass extends CraftMetaItem implements CompassMeta {

    static final CraftMetaItem.ItemMetaKey LODESTONE_DIMENSION = new CraftMetaItem.ItemMetaKey("LodestoneDimension");
    static final CraftMetaItem.ItemMetaKey LODESTONE_POS = new CraftMetaItem.ItemMetaKey("LodestonePos", "lodestone");
    static final CraftMetaItem.ItemMetaKey LODESTONE_POS_WORLD = new CraftMetaItem.ItemMetaKey("LodestonePosWorld");
    static final CraftMetaItem.ItemMetaKey LODESTONE_POS_X = new CraftMetaItem.ItemMetaKey("LodestonePosX");
    static final CraftMetaItem.ItemMetaKey LODESTONE_POS_Y = new CraftMetaItem.ItemMetaKey("LodestonePosY");
    static final CraftMetaItem.ItemMetaKey LODESTONE_POS_Z = new CraftMetaItem.ItemMetaKey("LodestonePosZ");
    static final CraftMetaItem.ItemMetaKey LODESTONE_TRACKED = new CraftMetaItem.ItemMetaKey("LodestoneTracked");
    private StringTag lodestoneWorld;
    private int lodestoneX;
    private int lodestoneY;
    private int lodestoneZ;
    private Boolean tracked;

    CraftMetaCompass(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaCompass) {
            CraftMetaCompass compassMeta = (CraftMetaCompass) meta;

            this.lodestoneWorld = compassMeta.lodestoneWorld;
            this.lodestoneX = compassMeta.lodestoneX;
            this.lodestoneY = compassMeta.lodestoneY;
            this.lodestoneZ = compassMeta.lodestoneZ;
            this.tracked = compassMeta.tracked;
        }
    }

    CraftMetaCompass(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaCompass.LODESTONE_DIMENSION.NBT) && tag.contains(CraftMetaCompass.LODESTONE_POS.NBT)) {
            this.lodestoneWorld = (StringTag) tag.get(CraftMetaCompass.LODESTONE_DIMENSION.NBT);
            CompoundTag pos = tag.getCompound(CraftMetaCompass.LODESTONE_POS.NBT);

            this.lodestoneX = pos.getInt("X");
            this.lodestoneY = pos.getInt("Y");
            this.lodestoneZ = pos.getInt("Z");
        }

        if (tag.contains(CraftMetaCompass.LODESTONE_TRACKED.NBT)) {
            this.tracked = tag.getBoolean(CraftMetaCompass.LODESTONE_TRACKED.NBT);
        }

    }

    CraftMetaCompass(Map map) {
        super(map);
        String lodestoneWorldString = CraftMetaItem.SerializableMeta.getString(map, CraftMetaCompass.LODESTONE_POS_WORLD.BUKKIT, true);

        if (lodestoneWorldString != null) {
            this.lodestoneWorld = StringTag.valueOf(lodestoneWorldString);
            this.lodestoneX = (Integer) map.get(CraftMetaCompass.LODESTONE_POS_X.BUKKIT);
            this.lodestoneY = (Integer) map.get(CraftMetaCompass.LODESTONE_POS_Y.BUKKIT);
            this.lodestoneZ = (Integer) map.get(CraftMetaCompass.LODESTONE_POS_Z.BUKKIT);
        } else {
            Location lodestone = (Location) CraftMetaItem.SerializableMeta.getObject(Location.class, map, CraftMetaCompass.LODESTONE_POS.BUKKIT, true);

            if (lodestone != null && lodestone.getWorld() != null) {
                this.setLodestone(lodestone);
            }
        }

        this.tracked = CraftMetaItem.SerializableMeta.getBoolean(map, CraftMetaCompass.LODESTONE_TRACKED.BUKKIT);
    }

    void applyToItem(CompoundTag tag) {
        super.applyToItem(tag);
        if (this.lodestoneWorld != null) {
            CompoundTag pos = new CompoundTag();

            pos.putInt("X", this.lodestoneX);
            pos.putInt("Y", this.lodestoneY);
            pos.putInt("Z", this.lodestoneZ);
            tag.put(CraftMetaCompass.LODESTONE_POS.NBT, pos);
            tag.put(CraftMetaCompass.LODESTONE_DIMENSION.NBT, this.lodestoneWorld);
        }

        if (this.tracked != null) {
            tag.putBoolean(CraftMetaCompass.LODESTONE_TRACKED.NBT, this.tracked);
        }

    }

    boolean isEmpty() {
        return super.isEmpty() && this.isCompassEmpty();
    }

    boolean isCompassEmpty() {
        return !this.hasLodestone() && !this.hasLodestoneTracked();
    }

    boolean applicableTo(Material type) {
        return type == Material.COMPASS;
    }

    public CraftMetaCompass clone() {
        CraftMetaCompass clone = (CraftMetaCompass) super.clone();

        return clone;
    }

    public boolean hasLodestone() {
        return this.lodestoneWorld != null;
    }

    public Location getLodestone() {
        if (this.lodestoneWorld == null) {
            return null;
        } else {
            Optional key = Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, this.lodestoneWorld).result();
            ServerLevel worldServer = key.isPresent() ? MinecraftServer.getServer().getLevel((ResourceKey) key.get()) : null;
            CraftWorld world = worldServer != null ? worldServer.getWorld() : null;

            return new Location(world, (double) this.lodestoneX, (double) this.lodestoneY, (double) this.lodestoneZ);
        }
    }

    public void setLodestone(Location lodestone) {
        Preconditions.checkArgument(lodestone == null || lodestone.getWorld() != null, "world is null");
        if (lodestone == null) {
            this.lodestoneWorld = null;
        } else {
            ResourceKey key = ((CraftWorld) lodestone.getWorld()).getHandle().dimension();
            DataResult dataresult = Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, key);

            this.lodestoneWorld = (StringTag) dataresult.get().orThrow();
            this.lodestoneX = lodestone.getBlockX();
            this.lodestoneY = lodestone.getBlockY();
            this.lodestoneZ = lodestone.getBlockZ();
        }

    }

    boolean hasLodestoneTracked() {
        return this.tracked != null;
    }

    public boolean isLodestoneTracked() {
        return this.hasLodestoneTracked() && this.tracked;
    }

    public void setLodestoneTracked(boolean tracked) {
        this.tracked = tracked;
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasLodestone()) {
            hash = 73 * hash + this.lodestoneWorld.hashCode();
            hash = 73 * hash + this.lodestoneX;
            hash = 73 * hash + this.lodestoneY;
            hash = 73 * hash + this.lodestoneZ;
        }

        if (this.hasLodestoneTracked()) {
            hash = 73 * hash + (this.isLodestoneTracked() ? 1231 : 1237);
        }

        return original != hash ? CraftMetaCompass.class.hashCode() ^ hash : hash;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else if (!(meta instanceof CraftMetaCompass)) {
            return true;
        } else {
            CraftMetaCompass that = (CraftMetaCompass) meta;

            if (this.hasLodestone()) {
                if (!that.hasLodestone() || !this.lodestoneWorld.getAsString().equals(that.lodestoneWorld.getAsString()) || this.lodestoneX != that.lodestoneX || this.lodestoneY != that.lodestoneY || this.lodestoneZ != that.lodestoneZ) {
                    return false;
                }
            } else if (that.hasLodestone()) {
                return false;
            }

            if (this.tracked == that.tracked) {
                return true;
            } else {
                return false;
            }
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaCompass || this.isCompassEmpty());
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasLodestone()) {
            builder.put(CraftMetaCompass.LODESTONE_POS_WORLD.BUKKIT, this.lodestoneWorld.getAsString());
            builder.put(CraftMetaCompass.LODESTONE_POS_X.BUKKIT, this.lodestoneX);
            builder.put(CraftMetaCompass.LODESTONE_POS_Y.BUKKIT, this.lodestoneY);
            builder.put(CraftMetaCompass.LODESTONE_POS_Z.BUKKIT, this.lodestoneZ);
        }

        if (this.hasLodestoneTracked()) {
            builder.put(CraftMetaCompass.LODESTONE_TRACKED.BUKKIT, this.tracked);
        }

        return builder;
    }
}
