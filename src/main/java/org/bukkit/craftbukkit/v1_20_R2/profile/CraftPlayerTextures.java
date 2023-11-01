package org.bukkit.craftbukkit.v1_20_R2.profile;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.properties.Property;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.craftbukkit.v1_20_R2.util.JsonHelper;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.profile.PlayerTextures.SkinModel;

final class CraftPlayerTextures implements PlayerTextures {

    static final String PROPERTY_NAME = "textures";
    private static final String MINECRAFT_HOST = "textures.minecraft.net";
    private static final String MINECRAFT_PATH = "/texture/";
    private final CraftPlayerProfile profile;
    private boolean loaded = false;
    private JsonObject data;
    private long timestamp;
    private URL skin;
    private SkinModel skinModel;
    private URL cape;
    private boolean dirty;

    private static void validateTextureUrl(@Nullable URL url) {
        if (url != null) {
            Preconditions.checkArgument(url.getHost().equals("textures.minecraft.net"), "Expected host '%s' but got '%s'", "textures.minecraft.net", url.getHost());
            Preconditions.checkArgument(url.getPath().startsWith("/texture/"), "Expected path starting with '%s' but got '%s", "/texture/", url.getPath());
        }
    }

    @Nullable
    private static URL parseUrl(@Nullable String urlString) {
        if (urlString == null) {
            return null;
        } else {
            try {
                return new URL(urlString);
            } catch (MalformedURLException malformedurlexception) {
                return null;
            }
        }
    }

    @Nullable
    private static SkinModel parseSkinModel(@Nullable String skinModelName) {
        if (skinModelName == null) {
            return null;
        } else {
            try {
                return SkinModel.valueOf(skinModelName.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException illegalargumentexception) {
                return null;
            }
        }
    }

    CraftPlayerTextures(@Nonnull CraftPlayerProfile profile) {
        this.skinModel = SkinModel.CLASSIC;
        this.dirty = false;
        this.profile = profile;
    }

    void copyFrom(@Nonnull PlayerTextures other) {
        if (other != this) {
            Preconditions.checkArgument(other instanceof CraftPlayerTextures, "Expecting CraftPlayerTextures, got %s", other.getClass().getName());
            CraftPlayerTextures otherTextures = (CraftPlayerTextures) other;

            this.clear();
            Property texturesProperty = otherTextures.getProperty();

            this.profile.setProperty("textures", texturesProperty);
            if (texturesProperty != null && (!Objects.equals(this.profile.getUniqueId(), otherTextures.profile.getUniqueId()) || !Objects.equals(this.profile.getName(), otherTextures.profile.getName()))) {
                this.ensureLoaded();
                this.markDirty();
                this.rebuildPropertyIfDirty();
            }

        }
    }

    private void ensureLoaded() {
        if (!this.loaded) {
            this.loaded = true;
            Property property = this.getProperty();

            if (property != null) {
                this.data = CraftProfileProperty.decodePropertyValue(property.value());
                if (this.data != null) {
                    JsonObject texturesMap = JsonHelper.getObjectOrNull(this.data, "textures");

                    this.loadSkin(texturesMap);
                    this.loadCape(texturesMap);
                    this.loadTimestamp();
                }

            }
        }
    }

    private void loadSkin(@Nullable JsonObject texturesMap) {
        if (texturesMap != null) {
            JsonObject texture = JsonHelper.getObjectOrNull(texturesMap, Type.SKIN.name());

            if (texture != null) {
                String skinUrlString = JsonHelper.getStringOrNull(texture, "url");

                this.skin = parseUrl(skinUrlString);
                this.skinModel = loadSkinModel(texture);
                if (this.skinModel == null && this.skin != null) {
                    this.skinModel = SkinModel.CLASSIC;
                }

            }
        }
    }

    @Nullable
    private static SkinModel loadSkinModel(@Nullable JsonObject texture) {
        if (texture == null) {
            return null;
        } else {
            JsonObject metadata = JsonHelper.getObjectOrNull(texture, "metadata");

            if (metadata == null) {
                return null;
            } else {
                String skinModelName = JsonHelper.getStringOrNull(metadata, "model");

                return parseSkinModel(skinModelName);
            }
        }
    }

    private void loadCape(@Nullable JsonObject texturesMap) {
        if (texturesMap != null) {
            JsonObject texture = JsonHelper.getObjectOrNull(texturesMap, Type.CAPE.name());

            if (texture != null) {
                String skinUrlString = JsonHelper.getStringOrNull(texture, "url");

                this.cape = parseUrl(skinUrlString);
            }
        }
    }

    private void loadTimestamp() {
        if (this.data != null) {
            JsonPrimitive timestamp = JsonHelper.getPrimitiveOrNull(this.data, "timestamp");

            if (timestamp != null) {
                try {
                    this.timestamp = timestamp.getAsLong();
                } catch (NumberFormatException numberformatexception) {
                    ;
                }

            }
        }
    }

    private void markDirty() {
        this.dirty = true;
        this.data = null;
        this.timestamp = 0L;
    }

    public boolean isEmpty() {
        this.ensureLoaded();
        return this.skin == null && this.cape == null;
    }

    public void clear() {
        this.profile.removeProperty("textures");
        this.loaded = false;
        this.data = null;
        this.timestamp = 0L;
        this.skin = null;
        this.skinModel = SkinModel.CLASSIC;
        this.cape = null;
        this.dirty = false;
    }

    public URL getSkin() {
        this.ensureLoaded();
        return this.skin;
    }

    public void setSkin(URL skinUrl) {
        this.setSkin(skinUrl, SkinModel.CLASSIC);
    }

    public void setSkin(URL skinUrl, SkinModel skinModel) {
        validateTextureUrl(skinUrl);
        if (skinModel == null) {
            skinModel = SkinModel.CLASSIC;
        }

        if (!Objects.equals(this.getSkin(), skinUrl) || !Objects.equals(this.getSkinModel(), skinModel)) {
            this.skin = skinUrl;
            this.skinModel = skinUrl != null ? skinModel : SkinModel.CLASSIC;
            this.markDirty();
        }
    }

    public SkinModel getSkinModel() {
        this.ensureLoaded();
        return this.skinModel;
    }

    public URL getCape() {
        this.ensureLoaded();
        return this.cape;
    }

    public void setCape(URL capeUrl) {
        validateTextureUrl(capeUrl);
        if (!Objects.equals(this.getCape(), capeUrl)) {
            this.cape = capeUrl;
            this.markDirty();
        }
    }

    public long getTimestamp() {
        this.ensureLoaded();
        return this.timestamp;
    }

    public boolean isSigned() {
        if (this.dirty) {
            return false;
        } else {
            Property property = this.getProperty();

            return property != null && CraftProfileProperty.hasValidSignature(property);
        }
    }

    @Nullable
    Property getProperty() {
        this.rebuildPropertyIfDirty();
        return this.profile.getProperty("textures");
    }

    void rebuildPropertyIfDirty() {
        if (this.dirty) {
            this.dirty = false;
            if (this.isEmpty()) {
                this.profile.removeProperty("textures");
            } else {
                JsonObject propertyData = new JsonObject();
                JsonObject texturesMap;
                JsonObject skinTexture;

                if (this.skin != null) {
                    texturesMap = JsonHelper.getOrCreateObject(propertyData, "textures");
                    skinTexture = JsonHelper.getOrCreateObject(texturesMap, Type.SKIN.name());
                    skinTexture.addProperty("url", this.skin.toExternalForm());
                    if (this.skinModel != SkinModel.CLASSIC) {
                        JsonObject metadata = JsonHelper.getOrCreateObject(skinTexture, "metadata");

                        metadata.addProperty("model", this.skinModel.name().toLowerCase(Locale.ROOT));
                    }
                }

                if (this.cape != null) {
                    texturesMap = JsonHelper.getOrCreateObject(propertyData, "textures");
                    skinTexture = JsonHelper.getOrCreateObject(texturesMap, Type.CAPE.name());
                    skinTexture.addProperty("url", this.cape.toExternalForm());
                }

                this.data = propertyData;
                String encodedTexturesData = CraftProfileProperty.encodePropertyValue(propertyData, CraftProfileProperty.JsonFormatter.COMPACT);
                Property property = new Property("textures", encodedTexturesData);

                this.profile.setProperty("textures", property);
            }
        }
    }

    private JsonObject getData() {
        this.ensureLoaded();
        this.rebuildPropertyIfDirty();
        return this.data;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("CraftPlayerTextures [data=");
        builder.append(this.getData());
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        Property property = this.getProperty();

        return property == null ? 0 : CraftProfileProperty.hashCode(property);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof CraftPlayerTextures)) {
            return false;
        } else {
            CraftPlayerTextures other = (CraftPlayerTextures) obj;
            Property property = this.getProperty();
            Property otherProperty = other.getProperty();

            return CraftProfileProperty.equals(property, otherProperty);
        }
    }
}
