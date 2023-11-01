package org.bukkit.craftbukkit.v1_20_R2.persistence;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNBTTagConfigSerializer;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CraftPersistentDataContainer implements PersistentDataContainer {

    private final Map customDataTags;
    private final CraftPersistentDataTypeRegistry registry;
    private final CraftPersistentDataAdapterContext adapterContext;

    public CraftPersistentDataContainer(Map customTags, CraftPersistentDataTypeRegistry registry) {
        this(registry);
        this.customDataTags.putAll(customTags);
    }

    public CraftPersistentDataContainer(CraftPersistentDataTypeRegistry registry) {
        this.customDataTags = new HashMap();
        this.registry = registry;
        this.adapterContext = new CraftPersistentDataAdapterContext(this.registry);
    }

    public void set(NamespacedKey key, PersistentDataType type, Object value) {
        Preconditions.checkArgument(key != null, "The NamespacedKey key cannot be null");
        Preconditions.checkArgument(type != null, "The provided type cannot be null");
        Preconditions.checkArgument(value != null, "The provided value cannot be null");
        this.customDataTags.put(key.toString(), this.registry.wrap(type.getPrimitiveType(), type.toPrimitive(value, this.adapterContext)));
    }

    public boolean has(NamespacedKey key, PersistentDataType type) {
        Preconditions.checkArgument(key != null, "The NamespacedKey key cannot be null");
        Preconditions.checkArgument(type != null, "The provided type cannot be null");
        Tag value = (Tag) this.customDataTags.get(key.toString());

        return value == null ? false : this.registry.isInstanceOf(type.getPrimitiveType(), value);
    }

    public Object get(NamespacedKey key, PersistentDataType type) {
        Preconditions.checkArgument(key != null, "The NamespacedKey key cannot be null");
        Preconditions.checkArgument(type != null, "The provided type cannot be null");
        Tag value = (Tag) this.customDataTags.get(key.toString());

        return value == null ? null : type.fromPrimitive(this.registry.extract(type.getPrimitiveType(), value), this.adapterContext);
    }

    public Object getOrDefault(NamespacedKey key, PersistentDataType type, Object defaultValue) {
        Object z = this.get(key, type);

        return z != null ? z : defaultValue;
    }

    public Set getKeys() {
        HashSet keys = new HashSet();

        this.customDataTags.keySet().forEach((keyx) -> {
            String[] keyData = keyx.split(":", 2);

            if (keyData.length == 2) {
                keys.add(new NamespacedKey(keyData[0], keyData[1]));
            }

        });
        return keys;
    }

    public void remove(NamespacedKey key) {
        Preconditions.checkArgument(key != null, "The NamespacedKey key cannot be null");
        this.customDataTags.remove(key.toString());
    }

    public boolean isEmpty() {
        return this.customDataTags.isEmpty();
    }

    public PersistentDataAdapterContext getAdapterContext() {
        return this.adapterContext;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CraftPersistentDataContainer)) {
            return false;
        } else {
            Map myRawMap = this.getRaw();
            Map theirRawMap = ((CraftPersistentDataContainer) obj).getRaw();

            return Objects.equals(myRawMap, theirRawMap);
        }
    }

    public CompoundTag toTagCompound() {
        CompoundTag tag = new CompoundTag();
        Iterator iterator = this.customDataTags.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            tag.put((String) entry.getKey(), (Tag) entry.getValue());
        }

        return tag;
    }

    public void put(String key, Tag base) {
        this.customDataTags.put(key, base);
    }

    public void putAll(Map map) {
        this.customDataTags.putAll(map);
    }

    public void putAll(CompoundTag compound) {
        Iterator iterator = compound.getAllKeys().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            this.customDataTags.put(key, compound.get(key));
        }

    }

    public Map getRaw() {
        return this.customDataTags;
    }

    public CraftPersistentDataTypeRegistry getDataTagTypeRegistry() {
        return this.registry;
    }

    public int hashCode() {
        byte hashCode = 3;
        int hashCode = hashCode + this.customDataTags.hashCode();

        return hashCode;
    }

    public String serialize() {
        return CraftNBTTagConfigSerializer.serialize(this.toTagCompound());
    }
}
