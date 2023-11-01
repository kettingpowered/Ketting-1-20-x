package org.bukkit.craftbukkit.v1_20_R2.profile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.configuration.ConfigSerializationUtil;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

@SerializableAs("PlayerProfile")
public final class CraftPlayerProfile implements PlayerProfile {

    private final UUID uniqueId;
    private final String name;
    private final PropertyMap properties;
    private final CraftPlayerTextures textures;

    @Nonnull
    public static GameProfile validateSkullProfile(@Nonnull GameProfile gameProfile) {
        boolean isValidSkullProfile = gameProfile.getName() != null || gameProfile.getProperties().containsKey("textures");

        Preconditions.checkArgument(isValidSkullProfile, "The skull profile is missing a name or textures!");
        return gameProfile;
    }

    @Nullable
    public static Property getProperty(@Nonnull GameProfile profile, String propertyName) {
        return (Property) Iterables.getFirst(profile.getProperties().get(propertyName), (Object) null);
    }

    public CraftPlayerProfile(UUID uniqueId, String name) {
        this.properties = new PropertyMap();
        this.textures = new CraftPlayerTextures(this);
        Preconditions.checkArgument(uniqueId != null || !StringUtils.isBlank(name), "uniqueId is null or name is blank");
        this.uniqueId = uniqueId == null ? Util.NIL_UUID : uniqueId;
        this.name = name == null ? "" : name;
    }

    public CraftPlayerProfile(@Nonnull GameProfile gameProfile) {
        this(gameProfile.getId(), gameProfile.getName());
        this.properties.putAll(gameProfile.getProperties());
    }

    private CraftPlayerProfile(@Nonnull CraftPlayerProfile other) {
        this(other.uniqueId, other.name);
        this.properties.putAll(other.properties);
        this.textures.copyFrom(other.textures);
    }

    public UUID getUniqueId() {
        return this.uniqueId.equals(Util.NIL_UUID) ? null : this.uniqueId;
    }

    public String getName() {
        return this.name.isEmpty() ? null : this.name;
    }

    @Nullable
    Property getProperty(String propertyName) {
        return (Property) Iterables.getFirst(this.properties.get(propertyName), (Object) null);
    }

    void setProperty(String propertyName, @Nullable Property property) {
        this.removeProperty(propertyName);
        if (property != null) {
            this.properties.put(property.name(), property);
        }

    }

    void removeProperty(String propertyName) {
        this.properties.removeAll(propertyName);
    }

    void rebuildDirtyProperties() {
        this.textures.rebuildPropertyIfDirty();
    }

    public CraftPlayerTextures getTextures() {
        return this.textures;
    }

    public void setTextures(@Nullable PlayerTextures textures) {
        if (textures == null) {
            this.textures.clear();
        } else {
            this.textures.copyFrom(textures);
        }

    }

    public boolean isComplete() {
        return this.getUniqueId() != null && this.getName() != null && !this.textures.isEmpty();
    }

    public CompletableFuture update() {
        return CompletableFuture.supplyAsync(this::getUpdatedProfile, Util.backgroundExecutor());
    }

    private CraftPlayerProfile getUpdatedProfile() {
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        GameProfile profile = this.buildGameProfile();

        if (profile.getId().equals(Util.NIL_UUID)) {
            profile = (GameProfile) server.getProfileCache().get(profile.getName()).orElse(profile);
        }

        if (!profile.getId().equals(Util.NIL_UUID)) {
            GameProfile newProfile;

            try {
                newProfile = (GameProfile) ((Optional) SkullBlockEntity.fillProfileTextures(profile).get()).orElse((Object) null);
            } catch (ExecutionException | InterruptedException interruptedexception) {
                throw new RuntimeException("Exception filling profile textures", interruptedexception);
            }

            if (newProfile != null) {
                profile = newProfile;
            }
        }

        return new CraftPlayerProfile(profile);
    }

    @Nonnull
    public GameProfile buildGameProfile() {
        this.rebuildDirtyProperties();
        GameProfile profile = new GameProfile(this.uniqueId, this.name);

        profile.getProperties().putAll(this.properties);
        return profile;
    }

    public String toString() {
        this.rebuildDirtyProperties();
        StringBuilder builder = new StringBuilder();

        builder.append("CraftPlayerProfile [uniqueId=");
        builder.append(this.uniqueId);
        builder.append(", name=");
        builder.append(this.name);
        builder.append(", properties=");
        builder.append(toString(this.properties));
        builder.append("]");
        return builder.toString();
    }

    private static String toString(@Nonnull PropertyMap propertyMap) {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        propertyMap.asMap().forEach((propertyNamex, propertiesx) -> {
            builder.append(propertyNamex);
            builder.append("=");
            builder.append((String) propertiesx.stream().map(CraftProfileProperty::toString).collect(Collectors.joining(",", "[", "]")));
        });
        builder.append("}");
        return builder.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof CraftPlayerProfile)) {
            return false;
        } else {
            CraftPlayerProfile other = (CraftPlayerProfile) obj;

            if (!Objects.equals(this.uniqueId, other.uniqueId)) {
                return false;
            } else if (!Objects.equals(this.name, other.name)) {
                return false;
            } else {
                this.rebuildDirtyProperties();
                other.rebuildDirtyProperties();
                return equals(this.properties, other.properties);
            }
        }
    }

    private static boolean equals(@Nonnull PropertyMap propertyMap, @Nonnull PropertyMap other) {
        if (propertyMap.size() != other.size()) {
            return false;
        } else {
            Iterator iterator1 = propertyMap.values().iterator();
            Iterator iterator2 = other.values().iterator();

            while (iterator1.hasNext()) {
                if (!iterator2.hasNext()) {
                    return false;
                }

                Property property1 = (Property) iterator1.next();
                Property property2 = (Property) iterator2.next();

                if (!CraftProfileProperty.equals(property1, property2)) {
                    return false;
                }
            }

            return !iterator2.hasNext();
        }
    }

    public int hashCode() {
        this.rebuildDirtyProperties();
        byte result = 1;
        int result = 31 * result + Objects.hashCode(this.uniqueId);

        result = 31 * result + Objects.hashCode(this.name);
        result = 31 * result + hashCode(this.properties);
        return result;
    }

    private static int hashCode(PropertyMap propertyMap) {
        int result = 1;

        Property property;

        for (Iterator iterator = propertyMap.values().iterator(); iterator.hasNext(); result = 31 * result + CraftProfileProperty.hashCode(property)) {
            property = (Property) iterator.next();
        }

        return result;
    }

    public CraftPlayerProfile clone() {
        return new CraftPlayerProfile(this);
    }

    public Map serialize() {
        LinkedHashMap map = new LinkedHashMap();

        if (this.getUniqueId() != null) {
            map.put("uniqueId", this.getUniqueId().toString());
        }

        if (this.getName() != null) {
            map.put("name", this.getName());
        }

        this.rebuildDirtyProperties();
        if (!this.properties.isEmpty()) {
            ArrayList propertiesData = new ArrayList();

            this.properties.forEach((propertyNamex, propertyx) -> {
                propertiesData.add(CraftProfileProperty.serialize(propertyx));
            });
            map.put("properties", propertiesData);
        }

        return map;
    }

    public static CraftPlayerProfile deserialize(Map map) {
        UUID uniqueId = ConfigSerializationUtil.getUuid(map, "uniqueId", true);
        String name = ConfigSerializationUtil.getString(map, "name", true);
        CraftPlayerProfile profile = new CraftPlayerProfile(uniqueId, name);

        if (map.containsKey("properties")) {
            Iterator iterator = ((List) map.get("properties")).iterator();

            while (iterator.hasNext()) {
                Object propertyData = iterator.next();

                Preconditions.checkArgument(propertyData instanceof Map, "Propertu data (%s) is not a valid Map", propertyData);
                Property property = CraftProfileProperty.deserialize((Map) propertyData);

                profile.properties.put(property.name(), property);
            }
        }

        return profile;
    }
}
