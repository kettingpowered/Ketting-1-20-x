package org.bukkit.craftbukkit.v1_20_R2.inventory.tags;

import com.google.common.base.Preconditions;
import org.bukkit.craftbukkit.v1_20_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class DeprecatedContainerTagType implements PersistentDataType {

    private final ItemTagType deprecated;

    DeprecatedContainerTagType(ItemTagType deprecated) {
        this.deprecated = deprecated;
    }

    public Class getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    public Class getComplexType() {
        return this.deprecated.getComplexType();
    }

    public PersistentDataContainer toPrimitive(Object complex, PersistentDataAdapterContext context) {
        CustomItemTagContainer deprecated = (CustomItemTagContainer) this.deprecated.toPrimitive(complex, new DeprecatedItemAdapterContext(context));

        Preconditions.checkArgument(deprecated instanceof DeprecatedCustomTagContainer, "Could not wrap deprecated API due to foreign CustomItemTagContainer implementation %s", deprecated.getClass().getSimpleName());
        DeprecatedCustomTagContainer tagContainer = (DeprecatedCustomTagContainer) deprecated;
        PersistentDataContainer wrapped = tagContainer.getWrapped();

        Preconditions.checkArgument(wrapped instanceof CraftPersistentDataContainer, "Could not wrap deprecated API due to wrong deprecation wrapper %s", deprecated.getClass().getSimpleName());
        CraftPersistentDataContainer craftTagContainer = (CraftPersistentDataContainer) wrapped;

        return new CraftPersistentDataContainer(craftTagContainer.getRaw(), craftTagContainer.getDataTagTypeRegistry());
    }

    public Object fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        Preconditions.checkArgument(primitive instanceof CraftPersistentDataContainer, "Could not wrap deprecated API due to foreign PersistentMetadataContainer implementation %s", primitive.getClass().getSimpleName());
        return this.deprecated.fromPrimitive(new DeprecatedCustomTagContainer(primitive), new DeprecatedItemAdapterContext(context));
    }
}
