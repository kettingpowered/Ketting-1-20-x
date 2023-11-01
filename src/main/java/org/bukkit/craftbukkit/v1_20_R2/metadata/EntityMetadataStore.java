package org.bukkit.craftbukkit.v1_20_R2.metadata;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataStoreBase;

public class EntityMetadataStore extends MetadataStoreBase implements MetadataStore {

    protected String disambiguate(Entity entity, String metadataKey) {
        return entity.getUniqueId().toString() + ":" + metadataKey;
    }
}
