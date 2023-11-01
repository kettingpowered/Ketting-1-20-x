package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.inventory.meta.BookMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaBookSigned extends CraftMetaBook implements BookMeta {

    CraftMetaBookSigned(CraftMetaItem meta) {
        super(meta);
    }

    CraftMetaBookSigned(CompoundTag tag) {
        super(tag);
    }

    CraftMetaBookSigned(Map map) {
        super(map);
    }

    protected String deserializePage(String pageData) {
        return CraftChatMessage.fromJSONOrStringToJSON(pageData, false, true, 320, false);
    }

    protected String convertPlainPageToData(String page) {
        return CraftChatMessage.fromStringToJSON(page, true);
    }

    protected String convertDataToPlainPage(String pageData) {
        return CraftChatMessage.fromJSONComponent(pageData);
    }

    void applyToItem(CompoundTag itemData) {
        super.applyToItem(itemData);
    }

    boolean isEmpty() {
        return super.isEmpty();
    }

    boolean applicableTo(Material type) {
        return type == Material.WRITTEN_BOOK || type == Material.WRITABLE_BOOK;
    }

    public CraftMetaBookSigned clone() {
        CraftMetaBookSigned meta = (CraftMetaBookSigned) super.clone();

        return meta;
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        return original != hash ? CraftMetaBookSigned.class.hashCode() ^ hash : hash;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        return super.equalsCommon(meta);
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaBookSigned || this.isBookEmpty());
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        return builder;
    }
}
