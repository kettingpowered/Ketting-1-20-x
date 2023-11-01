package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_20_R2.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_20_R2.Overridden;
import org.bukkit.craftbukkit.v1_20_R2.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R2.attribute.CraftAttributeInstance;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.inventory.tags.DeprecatedCustomTagContainer;
import org.bukkit.craftbukkit.v1_20_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_20_R2.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNBTTagConfigSerializer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.spigotmc.ValidateUtils;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaItem implements ItemMeta, Damageable, Repairable, BlockDataMeta {

    static final CraftMetaItem.ItemMetaKey NAME = new CraftMetaItem.ItemMetaKey("Name", "display-name");
    static final CraftMetaItem.ItemMetaKey LOCNAME = new CraftMetaItem.ItemMetaKey("LocName", "loc-name");
    static final CraftMetaItem.ItemMetaKey DISPLAY = new CraftMetaItem.ItemMetaKey("display");
    static final CraftMetaItem.ItemMetaKey LORE = new CraftMetaItem.ItemMetaKey("Lore", "lore");
    static final CraftMetaItem.ItemMetaKey CUSTOM_MODEL_DATA = new CraftMetaItem.ItemMetaKey("CustomModelData", "custom-model-data");
    static final CraftMetaItem.ItemMetaKey ENCHANTMENTS = new CraftMetaItem.ItemMetaKey("Enchantments", "enchants");
    static final CraftMetaItem.ItemMetaKey ENCHANTMENTS_ID = new CraftMetaItem.ItemMetaKey("id");
    static final CraftMetaItem.ItemMetaKey ENCHANTMENTS_LVL = new CraftMetaItem.ItemMetaKey("lvl");
    static final CraftMetaItem.ItemMetaKey REPAIR = new CraftMetaItem.ItemMetaKey("RepairCost", "repair-cost");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES = new CraftMetaItem.ItemMetaKey("AttributeModifiers", "attribute-modifiers");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES_IDENTIFIER = new CraftMetaItem.ItemMetaKey("AttributeName");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES_NAME = new CraftMetaItem.ItemMetaKey("Name");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES_VALUE = new CraftMetaItem.ItemMetaKey("Amount");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES_TYPE = new CraftMetaItem.ItemMetaKey("Operation");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES_UUID_HIGH = new CraftMetaItem.ItemMetaKey("UUIDMost");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES_UUID_LOW = new CraftMetaItem.ItemMetaKey("UUIDLeast");
    static final CraftMetaItem.ItemMetaKey ATTRIBUTES_SLOT = new CraftMetaItem.ItemMetaKey("Slot");
    static final CraftMetaItem.ItemMetaKey HIDEFLAGS = new CraftMetaItem.ItemMetaKey("HideFlags", "ItemFlags");
    static final CraftMetaItem.ItemMetaKey UNBREAKABLE = new CraftMetaItem.ItemMetaKey("Unbreakable");
    static final CraftMetaItem.ItemMetaKey DAMAGE = new CraftMetaItem.ItemMetaKey("Damage");
    static final CraftMetaItem.ItemMetaKey BLOCK_DATA = new CraftMetaItem.ItemMetaKey("BlockStateTag");
    static final CraftMetaItem.ItemMetaKey BUKKIT_CUSTOM_TAG = new CraftMetaItem.ItemMetaKey("PublicBukkitValues");
    private String displayName;
    private String locName;
    private List lore;
    private Integer customModelData;
    private CompoundTag blockData;
    private Map enchantments;
    private Multimap attributeModifiers;
    private int repairCost;
    private int hideFlag;
    private boolean unbreakable;
    private int damage;
    private static final Set HANDLED_TAGS = Sets.newHashSet();
    private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
    private CompoundTag internalTag;
    final Map unhandledTags = new HashMap();
    private CraftPersistentDataContainer persistentDataContainer;
    private int version;

    CraftMetaItem(CraftMetaItem meta) {
        this.persistentDataContainer = new CraftPersistentDataContainer(CraftMetaItem.DATA_TYPE_REGISTRY);
        this.version = CraftMagicNumbers.INSTANCE.getDataVersion();
        if (meta != null) {
            this.displayName = meta.displayName;
            this.locName = meta.locName;
            if (meta.lore != null) {
                this.lore = new ArrayList(meta.lore);
            }

            this.customModelData = meta.customModelData;
            this.blockData = meta.blockData;
            if (meta.enchantments != null) {
                this.enchantments = new LinkedHashMap(meta.enchantments);
            }

            if (meta.hasAttributeModifiers()) {
                this.attributeModifiers = LinkedHashMultimap.create(meta.attributeModifiers);
            }

            this.repairCost = meta.repairCost;
            this.hideFlag = meta.hideFlag;
            this.unbreakable = meta.unbreakable;
            this.damage = meta.damage;
            this.unhandledTags.putAll(meta.unhandledTags);
            this.persistentDataContainer.putAll(meta.persistentDataContainer.getRaw());
            this.internalTag = meta.internalTag;
            if (this.internalTag != null) {
                this.deserializeInternal(this.internalTag, meta);
            }

            this.version = meta.version;
        }
    }

    CraftMetaItem(CompoundTag tag) {
        this.persistentDataContainer = new CraftPersistentDataContainer(CraftMetaItem.DATA_TYPE_REGISTRY);
        this.version = CraftMagicNumbers.INSTANCE.getDataVersion();
        CompoundTag compound;

        if (tag.contains(CraftMetaItem.DISPLAY.NBT)) {
            compound = tag.getCompound(CraftMetaItem.DISPLAY.NBT);
            if (compound.contains(CraftMetaItem.NAME.NBT)) {
                this.displayName = ValidateUtils.limit(compound.getString(CraftMetaItem.NAME.NBT), 8192);
            }

            if (compound.contains(CraftMetaItem.LOCNAME.NBT)) {
                this.locName = ValidateUtils.limit(compound.getString(CraftMetaItem.LOCNAME.NBT), 8192);
            }

            if (compound.contains(CraftMetaItem.LORE.NBT)) {
                ListTag list = compound.getList(CraftMetaItem.LORE.NBT, 8);

                this.lore = new ArrayList(list.size());

                for (int index = 0; index < list.size(); ++index) {
                    String line = ValidateUtils.limit(list.getString(index), 8192);

                    this.lore.add(line);
                }
            }
        }

        if (tag.contains(CraftMetaItem.CUSTOM_MODEL_DATA.NBT, 3)) {
            this.customModelData = tag.getInt(CraftMetaItem.CUSTOM_MODEL_DATA.NBT);
        }

        if (tag.contains(CraftMetaItem.BLOCK_DATA.NBT, 10)) {
            this.blockData = tag.getCompound(CraftMetaItem.BLOCK_DATA.NBT).copy();
        }

        this.enchantments = buildEnchantments(tag, CraftMetaItem.ENCHANTMENTS);
        this.attributeModifiers = buildModifiers(tag, CraftMetaItem.ATTRIBUTES);
        if (tag.contains(CraftMetaItem.REPAIR.NBT)) {
            this.repairCost = tag.getInt(CraftMetaItem.REPAIR.NBT);
        }

        if (tag.contains(CraftMetaItem.HIDEFLAGS.NBT)) {
            this.hideFlag = tag.getInt(CraftMetaItem.HIDEFLAGS.NBT);
        }

        if (tag.contains(CraftMetaItem.UNBREAKABLE.NBT)) {
            this.unbreakable = tag.getBoolean(CraftMetaItem.UNBREAKABLE.NBT);
        }

        if (tag.contains(CraftMetaItem.DAMAGE.NBT)) {
            this.damage = tag.getInt(CraftMetaItem.DAMAGE.NBT);
        }

        if (tag.contains(CraftMetaItem.BUKKIT_CUSTOM_TAG.NBT)) {
            compound = tag.getCompound(CraftMetaItem.BUKKIT_CUSTOM_TAG.NBT);
            Set keys = compound.getAllKeys();
            Iterator iterator = keys.iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                this.persistentDataContainer.put(key, compound.get(key).copy());
            }
        }

        Set keys = tag.getAllKeys();
        Iterator iterator1 = keys.iterator();

        while (iterator1.hasNext()) {
            String key = (String) iterator1.next();

            if (!getHandledTags().contains(key)) {
                this.unhandledTags.put(key, tag.get(key).copy());
            }
        }

    }

    static Map buildEnchantments(CompoundTag tag, CraftMetaItem.ItemMetaKey key) {
        if (!tag.contains(key.NBT)) {
            return null;
        } else {
            ListTag ench = tag.getList(key.NBT, 10);
            LinkedHashMap enchantments = new LinkedHashMap(ench.size());

            for (int i = 0; i < ench.size(); ++i) {
                String id = ((CompoundTag) ench.get(i)).getString(CraftMetaItem.ENCHANTMENTS_ID.NBT);
                int level = '\uffff' & ((CompoundTag) ench.get(i)).getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
                Enchantment enchant = Enchantment.getByKey(CraftNamespacedKey.fromStringOrNull(id));

                if (enchant != null) {
                    enchantments.put(enchant, level);
                }
            }

            return enchantments;
        }
    }

    static Multimap buildModifiers(CompoundTag tag, CraftMetaItem.ItemMetaKey key) {
        LinkedHashMultimap modifiers = LinkedHashMultimap.create();

        if (!tag.contains(key.NBT, 9)) {
            return modifiers;
        } else {
            ListTag mods = tag.getList(key.NBT, 10);
            int size = mods.size();

            for (int i = 0; i < size; ++i) {
                CompoundTag entry = mods.getCompound(i);

                if (!entry.isEmpty()) {
                    AttributeModifier nmsModifier = AttributeModifier.load(entry);

                    if (nmsModifier != null) {
                        org.bukkit.attribute.AttributeModifier attribMod = CraftAttributeInstance.convert(nmsModifier);
                        String attributeName = entry.getString(CraftMetaItem.ATTRIBUTES_IDENTIFIER.NBT);

                        if (attributeName != null && !attributeName.isEmpty()) {
                            Attribute attribute = CraftAttribute.stringToBukkit(attributeName);

                            if (attribute != null) {
                                if (entry.contains(CraftMetaItem.ATTRIBUTES_SLOT.NBT, 8)) {
                                    String slotName = entry.getString(CraftMetaItem.ATTRIBUTES_SLOT.NBT);

                                    if (slotName == null || slotName.isEmpty()) {
                                        modifiers.put(attribute, attribMod);
                                        continue;
                                    }

                                    EquipmentSlot slot = null;

                                    try {
                                        slot = CraftEquipmentSlot.getSlot(net.minecraft.world.entity.EquipmentSlot.byName(slotName.toLowerCase(Locale.ROOT)));
                                    } catch (IllegalArgumentException illegalargumentexception) {
                                        ;
                                    }

                                    if (slot == null) {
                                        modifiers.put(attribute, attribMod);
                                        continue;
                                    }

                                    attribMod = new org.bukkit.attribute.AttributeModifier(attribMod.getUniqueId(), attribMod.getName(), attribMod.getAmount(), attribMod.getOperation(), slot);
                                }

                                modifiers.put(attribute, attribMod);
                            }
                        }
                    }
                }
            }

            return modifiers;
        }
    }

    CraftMetaItem(Map map) {
        this.persistentDataContainer = new CraftPersistentDataContainer(CraftMetaItem.DATA_TYPE_REGISTRY);
        this.version = CraftMagicNumbers.INSTANCE.getDataVersion();
        this.displayName = CraftChatMessage.fromJSONOrStringOrNullToJSON(CraftMetaItem.SerializableMeta.getString(map, CraftMetaItem.NAME.BUKKIT, true));
        this.locName = CraftChatMessage.fromJSONOrStringOrNullToJSON(CraftMetaItem.SerializableMeta.getString(map, CraftMetaItem.LOCNAME.BUKKIT, true));
        Iterable lore = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaItem.LORE.BUKKIT, true);

        if (lore != null) {
            safelyAdd(lore, this.lore = new ArrayList(), true);
        }

        Integer customModelData = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaItem.CUSTOM_MODEL_DATA.BUKKIT, true);

        if (customModelData != null) {
            this.setCustomModelData(customModelData);
        }

        Object blockData = CraftMetaItem.SerializableMeta.getObject(Object.class, map, CraftMetaItem.BLOCK_DATA.BUKKIT, true);

        if (blockData != null) {
            this.blockData = (CompoundTag) CraftNBTTagConfigSerializer.deserialize(blockData);
        }

        this.enchantments = buildEnchantments(map, CraftMetaItem.ENCHANTMENTS);
        this.attributeModifiers = buildModifiers(map, CraftMetaItem.ATTRIBUTES);
        Integer repairCost = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaItem.REPAIR.BUKKIT, true);

        if (repairCost != null) {
            this.setRepairCost(repairCost);
        }

        Iterable hideFlags = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaItem.HIDEFLAGS.BUKKIT, true);
        String internal;

        if (hideFlags != null) {
            Iterator iterator = hideFlags.iterator();

            while (iterator.hasNext()) {
                Object hideFlagObject = iterator.next();

                internal = (String) hideFlagObject;

                try {
                    ItemFlag hideFlatEnum = ItemFlag.valueOf(internal);

                    this.addItemFlags(hideFlatEnum);
                } catch (IllegalArgumentException illegalargumentexception) {
                    ;
                }
            }
        }

        Boolean unbreakable = (Boolean) CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, CraftMetaItem.UNBREAKABLE.BUKKIT, true);

        if (unbreakable != null) {
            this.setUnbreakable(unbreakable);
        }

        Integer damage = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaItem.DAMAGE.BUKKIT, true);

        if (damage != null) {
            this.setDamage(damage);
        }

        internal = CraftMetaItem.SerializableMeta.getString(map, "internal", true);
        if (internal != null) {
            ByteArrayInputStream buf = new ByteArrayInputStream(Base64.getDecoder().decode(internal));

            try {
                this.internalTag = NbtIo.readCompressed((InputStream) buf);
                this.deserializeInternal(this.internalTag, map);
                Set keys = this.internalTag.getAllKeys();
                Iterator iterator1 = keys.iterator();

                while (iterator1.hasNext()) {
                    String key = (String) iterator1.next();

                    if (!getHandledTags().contains(key)) {
                        this.unhandledTags.put(key, this.internalTag.get(key));
                    }
                }
            } catch (IOException ioexception) {
                Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, (String) null, ioexception);
            }
        }

        Object nbtMap = CraftMetaItem.SerializableMeta.getObject(Object.class, map, CraftMetaItem.BUKKIT_CUSTOM_TAG.BUKKIT, true);

        if (nbtMap != null) {
            this.persistentDataContainer.putAll((CompoundTag) CraftNBTTagConfigSerializer.deserialize(nbtMap));
        }

    }

    void deserializeInternal(CompoundTag tag, Object context) {
        if (tag.contains(CraftMetaItem.ATTRIBUTES.NBT, 9)) {
            this.attributeModifiers = buildModifiers(tag, CraftMetaItem.ATTRIBUTES);
        }

    }

    static Map buildEnchantments(Map map, CraftMetaItem.ItemMetaKey key) {
        Map ench = (Map) CraftMetaItem.SerializableMeta.getObject(Map.class, map, key.BUKKIT, true);

        if (ench == null) {
            return null;
        } else {
            LinkedHashMap enchantments = new LinkedHashMap(ench.size());
            Iterator iterator = ench.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();
                String enchantKey = entry.getKey().toString();

                if (enchantKey.equals("SWEEPING")) {
                    enchantKey = "SWEEPING_EDGE";
                }

                Enchantment enchantment = Enchantment.getByName(enchantKey);

                if (enchantment != null && entry.getValue() instanceof Integer) {
                    enchantments.put(enchantment, (Integer) entry.getValue());
                }
            }

            return enchantments;
        }
    }

    static Multimap buildModifiers(Map map, CraftMetaItem.ItemMetaKey key) {
        Map mods = (Map) CraftMetaItem.SerializableMeta.getObject(Map.class, map, key.BUKKIT, true);
        LinkedHashMultimap result = LinkedHashMultimap.create();

        if (mods == null) {
            return result;
        } else {
            Iterator iterator = mods.keySet().iterator();

            while (true) {
                String attributeName;

                do {
                    Object obj;

                    do {
                        if (!iterator.hasNext()) {
                            return result;
                        }

                        obj = iterator.next();
                    } while (!(obj instanceof String));

                    attributeName = (String) obj;
                } while (Strings.isNullOrEmpty(attributeName));

                List list = (List) CraftMetaItem.SerializableMeta.getObject(List.class, mods, attributeName, true);

                if (list == null || list.isEmpty()) {
                    return result;
                }

                Iterator iterator1 = list.iterator();

                while (iterator1.hasNext()) {
                    Object o = iterator1.next();

                    if (o instanceof org.bukkit.attribute.AttributeModifier) {
                        org.bukkit.attribute.AttributeModifier modifier = (org.bukkit.attribute.AttributeModifier) o;
                        Attribute attribute = (Attribute) EnumUtils.getEnum(Attribute.class, attributeName.toUpperCase(Locale.ROOT));

                        if (attribute != null) {
                            result.put(attribute, modifier);
                        }
                    }
                }
            }
        }
    }

    @Overridden
    void applyToItem(CompoundTag itemTag) {
        if (this.hasDisplayName()) {
            this.setDisplayTag(itemTag, CraftMetaItem.NAME.NBT, StringTag.valueOf(this.displayName));
        }

        if (this.hasLocalizedName()) {
            this.setDisplayTag(itemTag, CraftMetaItem.LOCNAME.NBT, StringTag.valueOf(this.locName));
        }

        if (this.lore != null) {
            this.setDisplayTag(itemTag, CraftMetaItem.LORE.NBT, this.createStringList(this.lore));
        }

        if (this.hasCustomModelData()) {
            itemTag.putInt(CraftMetaItem.CUSTOM_MODEL_DATA.NBT, this.customModelData);
        }

        if (this.hasBlockData()) {
            itemTag.put(CraftMetaItem.BLOCK_DATA.NBT, this.blockData);
        }

        if (this.hideFlag != 0) {
            itemTag.putInt(CraftMetaItem.HIDEFLAGS.NBT, this.hideFlag);
        }

        applyEnchantments(this.enchantments, itemTag, CraftMetaItem.ENCHANTMENTS);
        applyModifiers(this.attributeModifiers, itemTag, CraftMetaItem.ATTRIBUTES);
        if (this.hasRepairCost()) {
            itemTag.putInt(CraftMetaItem.REPAIR.NBT, this.repairCost);
        }

        if (this.isUnbreakable()) {
            itemTag.putBoolean(CraftMetaItem.UNBREAKABLE.NBT, this.unbreakable);
        }

        if (this.hasDamage()) {
            itemTag.putInt(CraftMetaItem.DAMAGE.NBT, this.damage);
        }

        Iterator iterator = this.unhandledTags.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry e = (Entry) iterator.next();

            itemTag.put((String) e.getKey(), (Tag) e.getValue());
        }

        if (!this.persistentDataContainer.isEmpty()) {
            CompoundTag bukkitCustomCompound = new CompoundTag();
            Map rawPublicMap = this.persistentDataContainer.getRaw();
            Iterator iterator1 = rawPublicMap.entrySet().iterator();

            while (iterator1.hasNext()) {
                Entry nbtBaseEntry = (Entry) iterator1.next();

                bukkitCustomCompound.put((String) nbtBaseEntry.getKey(), (Tag) nbtBaseEntry.getValue());
            }

            itemTag.put(CraftMetaItem.BUKKIT_CUSTOM_TAG.NBT, bukkitCustomCompound);
        }

    }

    ListTag createStringList(List list) {
        if (list == null) {
            return null;
        } else {
            ListTag tagList = new ListTag();
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                String value = (String) iterator.next();

                tagList.add(StringTag.valueOf(this.version > 0 && this.version < 1803 ? CraftChatMessage.fromJSONComponent(value) : value));
            }

            return tagList;
        }
    }

    static void applyEnchantments(Map enchantments, CompoundTag tag, CraftMetaItem.ItemMetaKey key) {
        if (enchantments != null) {
            ListTag list = new ListTag();
            Iterator iterator = enchantments.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();
                CompoundTag subtag = new CompoundTag();

                subtag.putString(CraftMetaItem.ENCHANTMENTS_ID.NBT, ((Enchantment) entry.getKey()).getKey().toString());
                subtag.putShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, ((Integer) entry.getValue()).shortValue());
                list.add(subtag);
            }

            tag.put(key.NBT, list);
        }
    }

    static void applyModifiers(Multimap modifiers, CompoundTag tag, CraftMetaItem.ItemMetaKey key) {
        if (modifiers != null && !modifiers.isEmpty()) {
            ListTag list = new ListTag();
            Iterator iterator = modifiers.entries().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                if (entry.getKey() != null && entry.getValue() != null) {
                    AttributeModifier nmsModifier = CraftAttributeInstance.convert((org.bukkit.attribute.AttributeModifier) entry.getValue());
                    CompoundTag sub = nmsModifier.save();

                    if (!sub.isEmpty()) {
                        String name = ((Attribute) entry.getKey()).getKey().toString();

                        if (name != null && !name.isEmpty()) {
                            sub.putString(CraftMetaItem.ATTRIBUTES_IDENTIFIER.NBT, name);
                            if (((org.bukkit.attribute.AttributeModifier) entry.getValue()).getSlot() != null) {
                                net.minecraft.world.entity.EquipmentSlot slot = CraftEquipmentSlot.getNMS(((org.bukkit.attribute.AttributeModifier) entry.getValue()).getSlot());

                                if (slot != null) {
                                    sub.putString(CraftMetaItem.ATTRIBUTES_SLOT.NBT, slot.getName());
                                }
                            }

                            list.add(sub);
                        }
                    }
                }
            }

            tag.put(key.NBT, list);
        }
    }

    void setDisplayTag(CompoundTag tag, String key, Tag value) {
        CompoundTag display = tag.getCompound(CraftMetaItem.DISPLAY.NBT);

        if (!tag.contains(CraftMetaItem.DISPLAY.NBT)) {
            tag.put(CraftMetaItem.DISPLAY.NBT, display);
        }

        display.put(key, value);
    }

    @Overridden
    boolean applicableTo(Material type) {
        return type != Material.AIR;
    }

    @Overridden
    boolean isEmpty() {
        return !this.hasDisplayName() && !this.hasLocalizedName() && !this.hasEnchants() && this.lore == null && !this.hasCustomModelData() && !this.hasBlockData() && !this.hasRepairCost() && this.unhandledTags.isEmpty() && this.persistentDataContainer.isEmpty() && this.hideFlag == 0 && !this.isUnbreakable() && !this.hasDamage() && !this.hasAttributeModifiers();
    }

    public String getDisplayName() {
        return CraftChatMessage.fromJSONComponent(this.displayName);
    }

    public final void setDisplayName(String name) {
        this.displayName = CraftChatMessage.fromStringOrNullToJSON(name);
    }

    public boolean hasDisplayName() {
        return this.displayName != null;
    }

    public String getLocalizedName() {
        return CraftChatMessage.fromJSONComponent(this.locName);
    }

    public void setLocalizedName(String name) {
        this.locName = CraftChatMessage.fromStringOrNullToJSON(name);
    }

    public boolean hasLocalizedName() {
        return this.locName != null;
    }

    public boolean hasLore() {
        return this.lore != null && !this.lore.isEmpty();
    }

    public boolean hasRepairCost() {
        return this.repairCost > 0;
    }

    public boolean hasEnchant(Enchantment ench) {
        Preconditions.checkArgument(ench != null, "Enchantment cannot be null");
        return this.hasEnchants() && this.enchantments.containsKey(ench);
    }

    public int getEnchantLevel(Enchantment ench) {
        Preconditions.checkArgument(ench != null, "Enchantment cannot be null");
        Integer level = this.hasEnchants() ? (Integer) this.enchantments.get(ench) : null;

        return level == null ? 0 : level;
    }

    public Map getEnchants() {
        return this.hasEnchants() ? ImmutableMap.copyOf(this.enchantments) : ImmutableMap.of();
    }

    public boolean addEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
        Preconditions.checkArgument(ench != null, "Enchantment cannot be null");
        if (this.enchantments == null) {
            this.enchantments = new LinkedHashMap(4);
        }

        if (!ignoreRestrictions && (level < ench.getStartLevel() || level > ench.getMaxLevel())) {
            return false;
        } else {
            Integer old = (Integer) this.enchantments.put(ench, level);

            return old == null || old != level;
        }
    }

    public boolean removeEnchant(Enchantment ench) {
        Preconditions.checkArgument(ench != null, "Enchantment cannot be null");
        boolean b = this.hasEnchants() && this.enchantments.remove(ench) != null;

        if (this.enchantments != null && this.enchantments.isEmpty()) {
            this.enchantments = null;
        }

        return b;
    }

    public boolean hasEnchants() {
        return this.enchantments != null && !this.enchantments.isEmpty();
    }

    public boolean hasConflictingEnchant(Enchantment ench) {
        return checkConflictingEnchants(this.enchantments, ench);
    }

    public void addItemFlags(ItemFlag... hideFlags) {
        ItemFlag[] aitemflag = hideFlags;
        int i = hideFlags.length;

        for (int j = 0; j < i; ++j) {
            ItemFlag f = aitemflag[j];

            this.hideFlag |= this.getBitModifier(f);
        }

    }

    public void removeItemFlags(ItemFlag... hideFlags) {
        ItemFlag[] aitemflag = hideFlags;
        int i = hideFlags.length;

        for (int j = 0; j < i; ++j) {
            ItemFlag f = aitemflag[j];

            this.hideFlag &= ~this.getBitModifier(f);
        }

    }

    public Set getItemFlags() {
        EnumSet currentFlags = EnumSet.noneOf(ItemFlag.class);
        ItemFlag[] aitemflag;
        int i = (aitemflag = ItemFlag.values()).length;

        for (int j = 0; j < i; ++j) {
            ItemFlag f = aitemflag[j];

            if (this.hasItemFlag(f)) {
                currentFlags.add(f);
            }
        }

        return currentFlags;
    }

    public boolean hasItemFlag(ItemFlag flag) {
        byte bitModifier = this.getBitModifier(flag);

        return (this.hideFlag & bitModifier) == bitModifier;
    }

    private byte getBitModifier(ItemFlag hideFlag) {
        return (byte) (1 << hideFlag.ordinal());
    }

    public List getLore() {
        return this.lore == null ? null : new ArrayList(Lists.transform(this.lore, CraftChatMessage::fromJSONComponent));
    }

    public void setLore(List lore) {
        if (lore != null && !lore.isEmpty()) {
            if (this.lore == null) {
                this.lore = new ArrayList(lore.size());
            } else {
                this.lore.clear();
            }

            safelyAdd(lore, this.lore, false);
        } else {
            this.lore = null;
        }

    }

    public boolean hasCustomModelData() {
        return this.customModelData != null;
    }

    public int getCustomModelData() {
        Preconditions.checkState(this.hasCustomModelData(), "We don't have CustomModelData! Check hasCustomModelData first!");
        return this.customModelData;
    }

    public void setCustomModelData(Integer data) {
        this.customModelData = data;
    }

    public boolean hasBlockData() {
        return this.blockData != null;
    }

    public BlockData getBlockData(Material material) {
        BlockState defaultData = CraftMagicNumbers.getBlock(material).defaultBlockState();

        return CraftBlockData.fromData(this.hasBlockData() ? BlockItem.getBlockState(defaultData, this.blockData) : defaultData);
    }

    public void setBlockData(BlockData blockData) {
        this.blockData = blockData == null ? null : ((CraftBlockData) blockData).toStates();
    }

    public int getRepairCost() {
        return this.repairCost;
    }

    public void setRepairCost(int cost) {
        this.repairCost = cost;
    }

    public boolean isUnbreakable() {
        return this.unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public boolean hasAttributeModifiers() {
        return this.attributeModifiers != null && !this.attributeModifiers.isEmpty();
    }

    public Multimap getAttributeModifiers() {
        return this.hasAttributeModifiers() ? ImmutableMultimap.copyOf(this.attributeModifiers) : null;
    }

    private void checkAttributeList() {
        if (this.attributeModifiers == null) {
            this.attributeModifiers = LinkedHashMultimap.create();
        }

    }

    public Multimap getAttributeModifiers(@Nullable EquipmentSlot slot) {
        this.checkAttributeList();
        LinkedHashMultimap result = LinkedHashMultimap.create();
        Iterator iterator = this.attributeModifiers.entries().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            if (((org.bukkit.attribute.AttributeModifier) entry.getValue()).getSlot() == null || ((org.bukkit.attribute.AttributeModifier) entry.getValue()).getSlot() == slot) {
                result.put((Attribute) entry.getKey(), (org.bukkit.attribute.AttributeModifier) entry.getValue());
            }
        }

        return result;
    }

    public Collection getAttributeModifiers(@Nonnull Attribute attribute) {
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        return this.attributeModifiers.containsKey(attribute) ? ImmutableList.copyOf(this.attributeModifiers.get(attribute)) : null;
    }

    public boolean addAttributeModifier(@Nonnull Attribute attribute, @Nonnull org.bukkit.attribute.AttributeModifier modifier) {
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");
        this.checkAttributeList();
        Iterator iterator = this.attributeModifiers.entries().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            Preconditions.checkArgument(!((org.bukkit.attribute.AttributeModifier) entry.getValue()).getUniqueId().equals(modifier.getUniqueId()), "Cannot register AttributeModifier. Modifier is already applied! %s", modifier);
        }

        return this.attributeModifiers.put(attribute, modifier);
    }

    public void setAttributeModifiers(@Nullable Multimap attributeModifiers) {
        if (attributeModifiers != null && !attributeModifiers.isEmpty()) {
            this.checkAttributeList();
            this.attributeModifiers.clear();
            Iterator iterator = attributeModifiers.entries().iterator();

            while (iterator.hasNext()) {
                Entry next = (Entry) iterator.next();

                if (next.getKey() != null && next.getValue() != null) {
                    this.attributeModifiers.put((Attribute) next.getKey(), (org.bukkit.attribute.AttributeModifier) next.getValue());
                } else {
                    iterator.remove();
                }
            }

        } else {
            this.attributeModifiers = LinkedHashMultimap.create();
        }
    }

    public boolean removeAttributeModifier(@Nonnull Attribute attribute) {
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        this.checkAttributeList();
        return !this.attributeModifiers.removeAll(attribute).isEmpty();
    }

    public boolean removeAttributeModifier(@Nullable EquipmentSlot slot) {
        this.checkAttributeList();
        int removed = 0;
        Iterator iter = this.attributeModifiers.entries().iterator();

        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();

            if (((org.bukkit.attribute.AttributeModifier) entry.getValue()).getSlot() == null || ((org.bukkit.attribute.AttributeModifier) entry.getValue()).getSlot() == slot) {
                iter.remove();
                ++removed;
            }
        }

        if (removed > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean removeAttributeModifier(@Nonnull Attribute attribute, @Nonnull org.bukkit.attribute.AttributeModifier modifier) {
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");
        this.checkAttributeList();
        int removed = 0;
        Iterator iter = this.attributeModifiers.entries().iterator();

        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();

            if (entry.getKey() != null && entry.getValue() != null) {
                if (entry.getKey() == attribute && ((org.bukkit.attribute.AttributeModifier) entry.getValue()).getUniqueId().equals(modifier.getUniqueId())) {
                    iter.remove();
                    ++removed;
                }
            } else {
                iter.remove();
                ++removed;
            }
        }

        if (removed > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getAsString() {
        CompoundTag tag = new CompoundTag();

        this.applyToItem(tag);
        return tag.toString();
    }

    public CustomItemTagContainer getCustomTagContainer() {
        return new DeprecatedCustomTagContainer(this.getPersistentDataContainer());
    }

    public PersistentDataContainer getPersistentDataContainer() {
        return this.persistentDataContainer;
    }

    private static boolean compareModifiers(Multimap first, Multimap second) {
        if (first != null && second != null) {
            Iterator iterator = first.entries().iterator();

            Entry entry;

            while (iterator.hasNext()) {
                entry = (Entry) iterator.next();
                if (!second.containsEntry(entry.getKey(), entry.getValue())) {
                    return false;
                }
            }

            iterator = second.entries().iterator();

            while (iterator.hasNext()) {
                entry = (Entry) iterator.next();
                if (!first.containsEntry(entry.getKey(), entry.getValue())) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean hasDamage() {
        return this.damage > 0;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public final boolean equals(Object object) {
        return object == null ? false : (object == this ? true : (!(object instanceof CraftMetaItem) ? false : CraftItemFactory.instance().equals((ItemMeta) this, (ItemMeta) object)));
    }

    @Overridden
    boolean equalsCommon(CraftMetaItem that) {
        if (this.hasDisplayName()) {
            if (!that.hasDisplayName() || !this.displayName.equals(that.displayName)) {
                return false;
            }
        } else if (that.hasDisplayName()) {
            return false;
        }

        if (this.hasLocalizedName()) {
            if (!that.hasLocalizedName() || !this.locName.equals(that.locName)) {
                return false;
            }
        } else if (that.hasLocalizedName()) {
            return false;
        }

        if (this.hasEnchants()) {
            if (!that.hasEnchants() || !this.enchantments.equals(that.enchantments)) {
                return false;
            }
        } else if (that.hasEnchants()) {
            return false;
        }

        if (Objects.equals(this.lore, that.lore)) {
            if (this.hasCustomModelData()) {
                if (!that.hasCustomModelData() || !this.customModelData.equals(that.customModelData)) {
                    return false;
                }
            } else if (that.hasCustomModelData()) {
                return false;
            }

            if (this.hasBlockData()) {
                if (!that.hasBlockData() || !this.blockData.equals(that.blockData)) {
                    return false;
                }
            } else if (that.hasBlockData()) {
                return false;
            }

            if (this.hasRepairCost()) {
                if (!that.hasRepairCost() || this.repairCost != that.repairCost) {
                    return false;
                }
            } else if (that.hasRepairCost()) {
                return false;
            }

            if (this.hasAttributeModifiers()) {
                if (!that.hasAttributeModifiers() || !compareModifiers(this.attributeModifiers, that.attributeModifiers)) {
                    return false;
                }
            } else if (that.hasAttributeModifiers()) {
                return false;
            }

            if (this.unhandledTags.equals(that.unhandledTags) && this.persistentDataContainer.equals(that.persistentDataContainer) && this.hideFlag == that.hideFlag && this.isUnbreakable() == that.isUnbreakable()) {
                if (this.hasDamage()) {
                    if (!that.hasDamage() || this.damage != that.damage) {
                        return false;
                    }
                } else if (that.hasDamage()) {
                    return false;
                }

                if (this.version == that.version) {
                    return true;
                }
            }
        }

        return false;
    }

    @Overridden
    boolean notUncommon(CraftMetaItem meta) {
        return true;
    }

    public final int hashCode() {
        return this.applyHash();
    }

    @Overridden
    int applyHash() {
        byte hash = 3;
        int hash = 61 * hash + (this.hasDisplayName() ? this.displayName.hashCode() : 0);

        hash = 61 * hash + (this.hasLocalizedName() ? this.locName.hashCode() : 0);
        hash = 61 * hash + (this.lore != null ? this.lore.hashCode() : 0);
        hash = 61 * hash + (this.hasCustomModelData() ? this.customModelData.hashCode() : 0);
        hash = 61 * hash + (this.hasBlockData() ? this.blockData.hashCode() : 0);
        hash = 61 * hash + (this.hasEnchants() ? this.enchantments.hashCode() : 0);
        hash = 61 * hash + (this.hasRepairCost() ? this.repairCost : 0);
        hash = 61 * hash + this.unhandledTags.hashCode();
        hash = 61 * hash + (!this.persistentDataContainer.isEmpty() ? this.persistentDataContainer.hashCode() : 0);
        hash = 61 * hash + this.hideFlag;
        hash = 61 * hash + (this.isUnbreakable() ? 1231 : 1237);
        hash = 61 * hash + (this.hasDamage() ? this.damage : 0);
        hash = 61 * hash + (this.hasAttributeModifiers() ? this.attributeModifiers.hashCode() : 0);
        hash = 61 * hash + this.version;
        return hash;
    }

    @Overridden
    public CraftMetaItem clone() {
        try {
            CraftMetaItem clone = (CraftMetaItem) super.clone();

            if (this.lore != null) {
                clone.lore = new ArrayList(this.lore);
            }

            clone.customModelData = this.customModelData;
            clone.blockData = this.blockData;
            if (this.enchantments != null) {
                clone.enchantments = new LinkedHashMap(this.enchantments);
            }

            if (this.hasAttributeModifiers()) {
                clone.attributeModifiers = LinkedHashMultimap.create(this.attributeModifiers);
            }

            clone.persistentDataContainer = new CraftPersistentDataContainer(this.persistentDataContainer.getRaw(), CraftMetaItem.DATA_TYPE_REGISTRY);
            clone.hideFlag = this.hideFlag;
            clone.unbreakable = this.unbreakable;
            clone.damage = this.damage;
            clone.version = this.version;
            return clone;
        } catch (CloneNotSupportedException clonenotsupportedexception) {
            throw new Error(clonenotsupportedexception);
        }
    }

    public final Map serialize() {
        Builder map = ImmutableMap.builder();

        map.put("meta-type", CraftMetaItem.SerializableMeta.classMap.get(this.getClass()));
        this.serialize(map);
        return map.build();
    }

    @Overridden
    Builder serialize(Builder builder) {
        if (this.hasDisplayName()) {
            builder.put(CraftMetaItem.NAME.BUKKIT, this.displayName);
        }

        if (this.hasLocalizedName()) {
            builder.put(CraftMetaItem.LOCNAME.BUKKIT, this.locName);
        }

        if (this.lore != null) {
            builder.put(CraftMetaItem.LORE.BUKKIT, ImmutableList.copyOf(this.lore));
        }

        if (this.hasCustomModelData()) {
            builder.put(CraftMetaItem.CUSTOM_MODEL_DATA.BUKKIT, this.customModelData);
        }

        if (this.hasBlockData()) {
            builder.put(CraftMetaItem.BLOCK_DATA.BUKKIT, CraftNBTTagConfigSerializer.serialize(this.blockData));
        }

        serializeEnchantments(this.enchantments, builder, CraftMetaItem.ENCHANTMENTS);
        serializeModifiers(this.attributeModifiers, builder, CraftMetaItem.ATTRIBUTES);
        if (this.hasRepairCost()) {
            builder.put(CraftMetaItem.REPAIR.BUKKIT, this.repairCost);
        }

        ArrayList hideFlags = new ArrayList();
        Iterator iterator = this.getItemFlags().iterator();

        while (iterator.hasNext()) {
            ItemFlag hideFlagEnum = (ItemFlag) iterator.next();

            hideFlags.add(hideFlagEnum.name());
        }

        if (!hideFlags.isEmpty()) {
            builder.put(CraftMetaItem.HIDEFLAGS.BUKKIT, hideFlags);
        }

        if (this.isUnbreakable()) {
            builder.put(CraftMetaItem.UNBREAKABLE.BUKKIT, this.unbreakable);
        }

        if (this.hasDamage()) {
            builder.put(CraftMetaItem.DAMAGE.BUKKIT, this.damage);
        }

        HashMap internalTags = new HashMap(this.unhandledTags);

        this.serializeInternal(internalTags);
        if (!internalTags.isEmpty()) {
            CompoundTag internal = new CompoundTag();
            Iterator iterator1 = internalTags.entrySet().iterator();

            while (iterator1.hasNext()) {
                Entry e = (Entry) iterator1.next();

                internal.put((String) e.getKey(), (Tag) e.getValue());
            }

            try {
                ByteArrayOutputStream buf = new ByteArrayOutputStream();

                NbtIo.writeCompressed(internal, (OutputStream) buf);
                builder.put("internal", Base64.getEncoder().encodeToString(buf.toByteArray()));
            } catch (IOException ioexception) {
                Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, (String) null, ioexception);
            }
        }

        if (!this.persistentDataContainer.isEmpty()) {
            builder.put(CraftMetaItem.BUKKIT_CUSTOM_TAG.BUKKIT, this.persistentDataContainer.serialize());
        }

        return builder;
    }

    void serializeInternal(Map unhandledTags) {}

    Material updateMaterial(Material material) {
        return material;
    }

    static void serializeEnchantments(Map enchantments, Builder builder, CraftMetaItem.ItemMetaKey key) {
        if (enchantments != null && !enchantments.isEmpty()) {
            Builder enchants = ImmutableMap.builder();
            Iterator iterator = enchantments.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry enchant = (Entry) iterator.next();

                enchants.put(((Enchantment) enchant.getKey()).getName(), (Integer) enchant.getValue());
            }

            builder.put(key.BUKKIT, enchants.build());
        }
    }

    static void serializeModifiers(Multimap modifiers, Builder builder, CraftMetaItem.ItemMetaKey key) {
        if (modifiers != null && !modifiers.isEmpty()) {
            LinkedHashMap mods = new LinkedHashMap();
            Iterator iterator = modifiers.entries().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                if (entry.getKey() != null) {
                    Collection modCollection = modifiers.get((Attribute) entry.getKey());

                    if (modCollection != null && !modCollection.isEmpty()) {
                        mods.put(((Attribute) entry.getKey()).name(), new ArrayList(modCollection));
                    }
                }
            }

            builder.put(key.BUKKIT, mods);
        }
    }

    static void safelyAdd(Iterable addFrom, Collection addTo, boolean possiblyJsonInput) {
        if (addFrom != null) {
            Iterator iterator = addFrom.iterator();

            while (iterator.hasNext()) {
                Object object = iterator.next();

                if (!(object instanceof String)) {
                    if (object != null) {
                        throw new IllegalArgumentException(addFrom + " cannot contain non-string " + object.getClass().getName());
                    }

                    addTo.add(CraftChatMessage.toJSON(Component.empty()));
                } else {
                    String entry = object.toString();

                    if (possiblyJsonInput) {
                        addTo.add(CraftChatMessage.fromJSONOrStringToJSON(entry));
                    } else {
                        addTo.add(CraftChatMessage.fromStringToJSON(entry));
                    }
                }
            }

        }
    }

    static boolean checkConflictingEnchants(Map enchantments, Enchantment ench) {
        if (enchantments != null && !enchantments.isEmpty()) {
            Iterator iterator = enchantments.keySet().iterator();

            while (iterator.hasNext()) {
                Enchantment enchant = (Enchantment) iterator.next();

                if (enchant.conflictsWith(ench)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public final String toString() {
        return (String) CraftMetaItem.SerializableMeta.classMap.get(this.getClass()) + "_META:" + this.serialize();
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static Set getHandledTags() {
        synchronized (CraftMetaItem.HANDLED_TAGS) {
            if (CraftMetaItem.HANDLED_TAGS.isEmpty()) {
                CraftMetaItem.HANDLED_TAGS.addAll(Arrays.asList(CraftMetaItem.DISPLAY.NBT, CraftMetaItem.CUSTOM_MODEL_DATA.NBT, CraftMetaItem.BLOCK_DATA.NBT, CraftMetaItem.REPAIR.NBT, CraftMetaItem.ENCHANTMENTS.NBT, CraftMetaItem.HIDEFLAGS.NBT, CraftMetaItem.UNBREAKABLE.NBT, CraftMetaItem.DAMAGE.NBT, CraftMetaItem.BUKKIT_CUSTOM_TAG.NBT, CraftMetaItem.ATTRIBUTES.NBT, CraftMetaItem.ATTRIBUTES_IDENTIFIER.NBT, CraftMetaItem.ATTRIBUTES_NAME.NBT, CraftMetaItem.ATTRIBUTES_VALUE.NBT, CraftMetaItem.ATTRIBUTES_UUID_HIGH.NBT, CraftMetaItem.ATTRIBUTES_UUID_LOW.NBT, CraftMetaItem.ATTRIBUTES_SLOT.NBT, CraftMetaArmor.TRIM.NBT, CraftMetaArmor.TRIM_MATERIAL.NBT, CraftMetaArmor.TRIM_PATTERN.NBT, CraftMetaMap.MAP_SCALING.NBT, CraftMetaMap.MAP_COLOR.NBT, CraftMetaMap.MAP_ID.NBT, CraftMetaPotion.POTION_EFFECTS.NBT, CraftMetaPotion.DEFAULT_POTION.NBT, CraftMetaPotion.POTION_COLOR.NBT, CraftMetaSkull.SKULL_OWNER.NBT, CraftMetaSkull.SKULL_PROFILE.NBT, CraftMetaSpawnEgg.ENTITY_TAG.NBT, CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT, CraftMetaBook.BOOK_TITLE.NBT, CraftMetaBook.BOOK_AUTHOR.NBT, CraftMetaBook.BOOK_PAGES.NBT, CraftMetaBook.RESOLVED.NBT, CraftMetaBook.GENERATION.NBT, CraftMetaFirework.FIREWORKS.NBT, CraftMetaEnchantedBook.STORED_ENCHANTMENTS.NBT, CraftMetaCharge.EXPLOSION.NBT, CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT, CraftMetaKnowledgeBook.BOOK_RECIPES.NBT, CraftMetaTropicalFishBucket.VARIANT.NBT, CraftMetaAxolotlBucket.VARIANT.NBT, CraftMetaCrossbow.CHARGED.NBT, CraftMetaCrossbow.CHARGED_PROJECTILES.NBT, CraftMetaSuspiciousStew.EFFECTS.NBT, CraftMetaCompass.LODESTONE_DIMENSION.NBT, CraftMetaCompass.LODESTONE_POS.NBT, CraftMetaCompass.LODESTONE_TRACKED.NBT, CraftMetaBundle.ITEMS.NBT, CraftMetaMusicInstrument.GOAT_HORN_INSTRUMENT.NBT));
            }

            return CraftMetaItem.HANDLED_TAGS;
        }
    }

    static class ItemMetaKey {

        final String BUKKIT;
        final String NBT;

        ItemMetaKey(String both) {
            this(both, both);
        }

        ItemMetaKey(String nbt, String bukkit) {
            this.NBT = nbt;
            this.BUKKIT = bukkit;
        }

        @Retention(RetentionPolicy.SOURCE)
        @Target({ElementType.FIELD})
        @interface Specific {

            CraftMetaItem.ItemMetaKey.Specific.To value();

            public static enum To {

                BUKKIT, NBT;
            }
        }
    }

    @SerializableAs("ItemMeta")
    public static final class SerializableMeta implements ConfigurationSerializable {

        static final String TYPE_FIELD = "meta-type";
        static final ImmutableMap classMap = ImmutableMap.builder().put(CraftMetaArmor.class, "ARMOR").put(CraftMetaArmorStand.class, "ARMOR_STAND").put(CraftMetaBanner.class, "BANNER").put(CraftMetaBlockState.class, "TILE_ENTITY").put(CraftMetaBook.class, "BOOK").put(CraftMetaBookSigned.class, "BOOK_SIGNED").put(CraftMetaSkull.class, "SKULL").put(CraftMetaLeatherArmor.class, "LEATHER_ARMOR").put(CraftMetaColorableArmor.class, "COLORABLE_ARMOR").put(CraftMetaMap.class, "MAP").put(CraftMetaPotion.class, "POTION").put(CraftMetaSpawnEgg.class, "SPAWN_EGG").put(CraftMetaEnchantedBook.class, "ENCHANTED").put(CraftMetaFirework.class, "FIREWORK").put(CraftMetaCharge.class, "FIREWORK_EFFECT").put(CraftMetaKnowledgeBook.class, "KNOWLEDGE_BOOK").put(CraftMetaTropicalFishBucket.class, "TROPICAL_FISH_BUCKET").put(CraftMetaAxolotlBucket.class, "AXOLOTL_BUCKET").put(CraftMetaCrossbow.class, "CROSSBOW").put(CraftMetaSuspiciousStew.class, "SUSPICIOUS_STEW").put(CraftMetaEntityTag.class, "ENTITY_TAG").put(CraftMetaCompass.class, "COMPASS").put(CraftMetaBundle.class, "BUNDLE").put(CraftMetaMusicInstrument.class, "MUSIC_INSTRUMENT").put(CraftMetaItem.class, "UNSPECIFIC").build();
        static final ImmutableMap constructorMap;

        static {
            Builder classConstructorBuilder = ImmutableMap.builder();
            Iterator iterator = CraftMetaItem.SerializableMeta.classMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry mapping = (Entry) iterator.next();

                try {
                    classConstructorBuilder.put((String) mapping.getValue(), ((Class) mapping.getKey()).getDeclaredConstructor(Map.class));
                } catch (NoSuchMethodException nosuchmethodexception) {
                    throw new AssertionError(nosuchmethodexception);
                }
            }

            constructorMap = classConstructorBuilder.build();
        }

        private SerializableMeta() {}

        public static ItemMeta deserialize(Map map) throws Throwable {
            Preconditions.checkArgument(map != null, "Cannot deserialize null map");
            String type = getString(map, "meta-type", false);
            Constructor constructor = (Constructor) CraftMetaItem.SerializableMeta.constructorMap.get(type);

            if (constructor == null) {
                throw new IllegalArgumentException(type + " is not a valid " + "meta-type");
            } else {
                try {
                    return (ItemMeta) constructor.newInstance(map);
                } catch (InstantiationException instantiationexception) {
                    throw new AssertionError(instantiationexception);
                } catch (IllegalAccessException illegalaccessexception) {
                    throw new AssertionError(illegalaccessexception);
                } catch (InvocationTargetException invocationtargetexception) {
                    throw invocationtargetexception.getCause();
                }
            }
        }

        public Map serialize() {
            throw new AssertionError();
        }

        static String getString(Map map, Object field, boolean nullable) {
            return (String) getObject(String.class, map, field, nullable);
        }

        static boolean getBoolean(Map map, Object field) {
            Boolean value = (Boolean) getObject(Boolean.class, map, field, true);

            return value != null && value;
        }

        static Object getObject(Class clazz, Map map, Object field, boolean nullable) {
            Object object = map.get(field);

            if (clazz.isInstance(object)) {
                return clazz.cast(object);
            } else if (object == null) {
                if (!nullable) {
                    throw new NoSuchElementException(map + " does not contain " + field);
                } else {
                    return null;
                }
            } else {
                throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
            }
        }
    }
}
