package org.bukkit.craftbukkit.v1_20_R2.persistence;

import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public final class DirtyCraftPersistentDataContainer extends CraftPersistentDataContainer {

    private boolean dirty;

    public DirtyCraftPersistentDataContainer(Map customTags, CraftPersistentDataTypeRegistry registry) {
        super(customTags, registry);
    }

    public DirtyCraftPersistentDataContainer(CraftPersistentDataTypeRegistry registry) {
        super(registry);
    }

    public boolean dirty() {
        return this.dirty;
    }

    public void dirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void set(NamespacedKey key, PersistentDataType type, Object value) {
        super.set(key, type, value);
        this.dirty(true);
    }

    public void remove(NamespacedKey key) {
        super.remove(key);
        this.dirty(true);
    }

    public void put(String key, Tag base) {
        super.put(key, base);
        this.dirty(true);
    }

    public void putAll(CompoundTag compound) {
        super.putAll(compound);
        this.dirty(true);
    }

    public void putAll(Map map) {
        super.putAll(map);
        this.dirty(true);
    }
}
