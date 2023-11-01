package org.bukkit.craftbukkit.v1_20_R2.inventory.tags;

import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.persistence.PersistentDataAdapterContext;

public final class DeprecatedItemAdapterContext implements ItemTagAdapterContext {

    private final PersistentDataAdapterContext context;

    public DeprecatedItemAdapterContext(PersistentDataAdapterContext context) {
        this.context = context;
    }

    public CustomItemTagContainer newTagContainer() {
        return new DeprecatedCustomTagContainer(this.context.newPersistentDataContainer());
    }
}
