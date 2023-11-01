package org.bukkit.craftbukkit.v1_20_R2.metadata;

import org.bukkit.World;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataStoreBase;

public class WorldMetadataStore extends MetadataStoreBase implements MetadataStore {

    protected String disambiguate(World world, String metadataKey) {
        return world.getUID().toString() + ":" + metadataKey;
    }
}
