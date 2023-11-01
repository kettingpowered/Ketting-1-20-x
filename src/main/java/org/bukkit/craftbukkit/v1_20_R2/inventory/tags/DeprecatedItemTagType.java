package org.bukkit.craftbukkit.v1_20_R2.inventory.tags;

import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public final class DeprecatedItemTagType implements PersistentDataType {

    private final ItemTagType deprecated;

    public DeprecatedItemTagType(ItemTagType deprecated) {
        this.deprecated = deprecated;
    }

    public Class getPrimitiveType() {
        return this.deprecated.getPrimitiveType();
    }

    public Class getComplexType() {
        return this.deprecated.getComplexType();
    }

    public Object toPrimitive(Object complex, PersistentDataAdapterContext context) {
        return this.deprecated.toPrimitive(complex, new DeprecatedItemAdapterContext(context));
    }

    public Object fromPrimitive(Object primitive, PersistentDataAdapterContext context) {
        return this.deprecated.fromPrimitive(primitive, new DeprecatedItemAdapterContext(context));
    }
}
