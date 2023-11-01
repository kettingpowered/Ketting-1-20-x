package org.bukkit.craftbukkit.v1_20_R2.inventory.tags;

import java.util.Objects;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataContainer;

public final class DeprecatedCustomTagContainer implements CustomItemTagContainer {

    private final PersistentDataContainer wrapped;

    public DeprecatedCustomTagContainer(PersistentDataContainer wrapped) {
        this.wrapped = wrapped;
    }

    public void setCustomTag(NamespacedKey key, ItemTagType type, Object value) {
        if (Objects.equals(CustomItemTagContainer.class, type.getPrimitiveType())) {
            this.wrapped.set(key, new DeprecatedContainerTagType(type), value);
        } else {
            this.wrapped.set(key, new DeprecatedItemTagType(type), value);
        }

    }

    public boolean hasCustomTag(NamespacedKey key, ItemTagType type) {
        return Objects.equals(CustomItemTagContainer.class, type.getPrimitiveType()) ? this.wrapped.has(key, new DeprecatedContainerTagType(type)) : this.wrapped.has(key, new DeprecatedItemTagType(type));
    }

    public Object getCustomTag(NamespacedKey key, ItemTagType type) {
        return Objects.equals(CustomItemTagContainer.class, type.getPrimitiveType()) ? this.wrapped.get(key, new DeprecatedContainerTagType(type)) : this.wrapped.get(key, new DeprecatedItemTagType(type));
    }

    public void removeCustomTag(NamespacedKey key) {
        this.wrapped.remove(key);
    }

    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    public ItemTagAdapterContext getAdapterContext() {
        return new DeprecatedItemAdapterContext(this.wrapped.getAdapterContext());
    }

    public PersistentDataContainer getWrapped() {
        return this.wrapped;
    }
}
