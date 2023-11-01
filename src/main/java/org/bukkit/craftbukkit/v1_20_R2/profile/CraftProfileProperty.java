package org.bukkit.craftbukkit.v1_20_R2.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.ServicesKeySet;
import com.mojang.authlib.yggdrasil.ServicesKeyType;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.craftbukkit.v1_20_R2.configuration.ConfigSerializationUtil;

final class CraftProfileProperty {

    private static final ServicesKeySet PUBLIC_KEYS;

    static {
        try {
            PUBLIC_KEYS = (new YggdrasilAuthenticationService(Proxy.NO_PROXY)).getServicesKeySet();
        } catch (Exception exception) {
            throw new Error("Could not load yggdrasil_session_pubkey.der! This indicates a bug.");
        }
    }

    public static boolean hasValidSignature(@Nonnull Property property) {
        return property.hasSignature() && CraftProfileProperty.PUBLIC_KEYS.keys(ServicesKeyType.PROFILE_PROPERTY).stream().anyMatch((keyx) -> {
            return keyx.validateProperty(property);
        });
    }

    @Nullable
    private static String decodeBase64(@Nonnull String encoded) {
        try {
            return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    @Nullable
    public static JsonObject decodePropertyValue(@Nonnull String encodedPropertyValue) {
        String json = decodeBase64(encodedPropertyValue);

        if (json == null) {
            return null;
        } else {
            try {
                JsonElement jsonElement = JsonParser.parseString(json);

                return !jsonElement.isJsonObject() ? null : jsonElement.getAsJsonObject();
            } catch (JsonParseException jsonparseexception) {
                return null;
            }
        }
    }

    @Nonnull
    public static String encodePropertyValue(@Nonnull JsonObject propertyValue, @Nonnull CraftProfileProperty.JsonFormatter formatter) {
        String json = formatter.format(propertyValue);

        return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    @Nonnull
    public static String toString(@Nonnull Property property) {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("name=");
        builder.append(property.name());
        builder.append(", value=");
        builder.append(property.value());
        builder.append(", signature=");
        builder.append(property.signature());
        builder.append("}");
        return builder.toString();
    }

    public static int hashCode(@Nonnull Property property) {
        byte result = 1;
        int result = 31 * result + Objects.hashCode(property.name());

        result = 31 * result + Objects.hashCode(property.value());
        result = 31 * result + Objects.hashCode(property.signature());
        return result;
    }

    public static boolean equals(@Nullable Property property, @Nullable Property other) {
        return property != null && other != null ? (!Objects.equals(property.value(), other.value()) ? false : (!Objects.equals(property.name(), other.name()) ? false : Objects.equals(property.signature(), other.signature()))) : property == other;
    }

    public static Map serialize(@Nonnull Property property) {
        LinkedHashMap map = new LinkedHashMap();

        map.put("name", property.name());
        map.put("value", property.value());
        if (property.hasSignature()) {
            map.put("signature", property.signature());
        }

        return map;
    }

    public static Property deserialize(@Nonnull Map map) {
        String name = ConfigSerializationUtil.getString(map, "name", false);
        String value = ConfigSerializationUtil.getString(map, "value", false);
        String signature = ConfigSerializationUtil.getString(map, "signature", true);

        return new Property(name, value, signature);
    }

    private CraftProfileProperty() {}

    public interface JsonFormatter {

        CraftProfileProperty.JsonFormatter COMPACT = new CraftProfileProperty.JsonFormatter() {
            private final Gson gson = (new GsonBuilder()).create();

            public String format(JsonElement jsonElement) {
                return this.gson.toJson(jsonElement);
            }
        };

        String format(JsonElement jsonelement);
    }
}
