package org.bukkit.craftbukkit.v1_20_R2.metadata;

import org.bukkit.OfflinePlayer;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataStoreBase;

public class PlayerMetadataStore extends MetadataStoreBase implements MetadataStore {

    protected String disambiguate(OfflinePlayer player, String metadataKey) {
        return player.getUniqueId() + ":" + metadataKey;
    }
}
