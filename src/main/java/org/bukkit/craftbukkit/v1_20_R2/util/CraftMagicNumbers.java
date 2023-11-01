package org.bukkit.craftbukkit.v1_20_R2.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Dynamic;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelResource;
import org.bukkit.Bukkit;
import org.bukkit.FeatureFlag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.UnsafeValues;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_20_R2.CraftFeatureFlag;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R2.attribute.CraftAttributeInstance;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionType;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.PotionType.InternalPotionData;

public final class CraftMagicNumbers implements UnsafeValues {

    public static final UnsafeValues INSTANCE = new CraftMagicNumbers();
    private static final Map BLOCK_MATERIAL = new HashMap();
    private static final Map ITEM_MATERIAL = new HashMap();
    private static final Map MATERIAL_ITEM = new HashMap();
    private static final Map MATERIAL_BLOCK = new HashMap();
    private static final List SUPPORTED_API;

    static {
        Iterator iterator = BuiltInRegistries.BLOCK.iterator();

        while (iterator.hasNext()) {
            Block block = (Block) iterator.next();

            CraftMagicNumbers.BLOCK_MATERIAL.put(block, Material.getMaterial(BuiltInRegistries.BLOCK.getKey(block).getPath().toUpperCase(Locale.ROOT)));
        }

        iterator = BuiltInRegistries.ITEM.iterator();

        while (iterator.hasNext()) {
            Item item = (Item) iterator.next();

            CraftMagicNumbers.ITEM_MATERIAL.put(item, Material.getMaterial(BuiltInRegistries.ITEM.getKey(item).getPath().toUpperCase(Locale.ROOT)));
        }

        Material[] amaterial;
        int i = (amaterial = Material.values()).length;

        for (int j = 0; j < i; ++j) {
            Material material = amaterial[j];

            if (!material.isLegacy()) {
                ResourceLocation key = key(material);

                BuiltInRegistries.ITEM.getOptional(key).ifPresent((itemx) -> {
                    CraftMagicNumbers.MATERIAL_ITEM.put(material, itemx);
                });
                BuiltInRegistries.BLOCK.getOptional(key).ifPresent((blockx) -> {
                    CraftMagicNumbers.MATERIAL_BLOCK.put(material, blockx);
                });
            }
        }

        SUPPORTED_API = Arrays.asList("1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19", "1.20");
    }

    private CraftMagicNumbers() {}

    public static BlockState getBlock(MaterialData material) {
        return getBlock(material.getItemType(), material.getData());
    }

    public static BlockState getBlock(Material material, byte data) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.fromLegacyData(org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.toLegacy(material), data);
    }

    public static MaterialData getMaterial(BlockState data) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.toLegacy(getMaterial(data.getBlock())).getNewData(toLegacyData(data));
    }

    public static Item getItem(Material material, short data) {
        return material.isLegacy() ? org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.fromLegacyData(org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.toLegacy(material), data) : getItem(material);
    }

    public static MaterialData getMaterialData(Item item) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.toLegacyData(getMaterial(item));
    }

    public static Material getMaterial(Block block) {
        return (Material) CraftMagicNumbers.BLOCK_MATERIAL.get(block);
    }

    public static Material getMaterial(Item item) {
        return (Material) CraftMagicNumbers.ITEM_MATERIAL.getOrDefault(item, Material.AIR);
    }

    public static Item getItem(Material material) {
        if (material != null && material.isLegacy()) {
            material = org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.fromLegacy(material);
        }

        return (Item) CraftMagicNumbers.MATERIAL_ITEM.get(material);
    }

    public static Block getBlock(Material material) {
        if (material != null && material.isLegacy()) {
            material = org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.fromLegacy(material);
        }

        return (Block) CraftMagicNumbers.MATERIAL_BLOCK.get(material);
    }

    public static ResourceLocation key(Material mat) {
        return CraftNamespacedKey.toMinecraft(mat.getKey());
    }

    public static byte toLegacyData(BlockState data) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.toLegacyData(data);
    }

    public Material toLegacy(Material material) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.toLegacy(material);
    }

    public Material fromLegacy(Material material) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.fromLegacy(material);
    }

    public Material fromLegacy(MaterialData material) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.fromLegacy(material);
    }

    public Material fromLegacy(MaterialData material, boolean itemPriority) {
        return org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.fromLegacy(material, itemPriority);
    }

    public BlockData fromLegacy(Material material, byte data) {
        return CraftBlockData.fromData(getBlock(material, data));
    }

    public Material getMaterial(String material, int version) {
        Preconditions.checkArgument(material != null, "material == null");
        Preconditions.checkArgument(version <= this.getDataVersion(), "Newer version! Server downgrades are not supported!");
        if (version == this.getDataVersion()) {
            return Material.getMaterial(material);
        } else {
            Dynamic name = new Dynamic(NbtOps.INSTANCE, StringTag.valueOf("minecraft:" + material.toLowerCase(Locale.ROOT)));
            Dynamic converted = DataFixers.getDataFixer().update(References.ITEM_NAME, name, version, this.getDataVersion());

            if (name.equals(converted)) {
                converted = DataFixers.getDataFixer().update(References.BLOCK_NAME, name, version, this.getDataVersion());
            }

            return Material.matchMaterial(converted.asString(""));
        }
    }

    public String getMappingsVersion() {
        return "3478a65bfd04b15b431fe107b3617dfc";
    }

    public int getDataVersion() {
        return SharedConstants.getCurrentVersion().getDataVersion().getVersion();
    }

    public ItemStack modifyItemStack(ItemStack stack, String arguments) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        try {
            nmsStack.setTag(TagParser.parseTag(arguments));
        } catch (CommandSyntaxException commandsyntaxexception) {
            Logger.getLogger(CraftMagicNumbers.class.getName()).log(Level.SEVERE, (String) null, commandsyntaxexception);
        }

        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        return stack;
    }

    private static File getBukkitDataPackFolder() {
        return new File(MinecraftServer.getServer().getWorldPath(LevelResource.DATAPACK_DIR).toFile(), "bukkit");
    }

    public Advancement loadAdvancement(NamespacedKey key, String advancement) {
        Preconditions.checkArgument(Bukkit.getAdvancement(key) == null, "Advancement %s already exists", key);
        ResourceLocation minecraftkey = CraftNamespacedKey.toMinecraft(key);
        JsonElement jsonelement = (JsonElement) ServerAdvancementManager.GSON.fromJson(advancement, JsonElement.class);
        JsonObject jsonobject = GsonHelper.convertToJsonObject(jsonelement, "advancement");
        net.minecraft.advancements.Advancement nms = net.minecraft.advancements.Advancement.fromJson(jsonobject, new DeserializationContext(minecraftkey, MinecraftServer.getServer().getLootData()));

        if (nms != null) {
            MinecraftServer.getServer().getAdvancements().advancements.put(minecraftkey, new AdvancementHolder(minecraftkey, nms));
            Advancement bukkit = Bukkit.getAdvancement(key);

            if (bukkit != null) {
                File file = new File(getBukkitDataPackFolder(), "data" + File.separator + key.getNamespace() + File.separator + "advancements" + File.separator + key.getKey() + ".json");

                file.getParentFile().mkdirs();

                try {
                    Files.write(advancement, file, Charsets.UTF_8);
                } catch (IOException ioexception) {
                    Bukkit.getLogger().log(Level.SEVERE, "Error saving advancement " + key, ioexception);
                }

                MinecraftServer.getServer().getPlayerList().reloadResources();
                return bukkit;
            }
        }

        return null;
    }

    public boolean removeAdvancement(NamespacedKey key) {
        File file = new File(getBukkitDataPackFolder(), "data" + File.separator + key.getNamespace() + File.separator + "advancements" + File.separator + key.getKey() + ".json");

        return file.delete();
    }

    public void checkSupported(PluginDescriptionFile pdf) throws InvalidPluginException {
        String minimumVersion = MinecraftServer.getServer().server.minimumAPI;
        int minimumIndex = CraftMagicNumbers.SUPPORTED_API.indexOf(minimumVersion);

        if (pdf.getAPIVersion() != null) {
            int pluginIndex = CraftMagicNumbers.SUPPORTED_API.indexOf(pdf.getAPIVersion());

            if (pluginIndex == -1) {
                throw new InvalidPluginException("Unsupported API version " + pdf.getAPIVersion());
            }

            if (pluginIndex < minimumIndex) {
                throw new InvalidPluginException("Plugin API version " + pdf.getAPIVersion() + " is lower than the minimum allowed version. Please update or replace it.");
            }
        } else {
            if (minimumIndex != -1) {
                throw new InvalidPluginException("Plugin API version " + pdf.getAPIVersion() + " is lower than the minimum allowed version. Please update or replace it.");
            }

            org.bukkit.craftbukkit.v1_20_R2.legacy.CraftLegacy.init();
            Bukkit.getLogger().log(Level.WARNING, "Legacy plugin " + pdf.getFullName() + " does not specify an api-version.");
        }

    }

    public static boolean isLegacy(PluginDescriptionFile pdf) {
        return pdf.getAPIVersion() == null;
    }

    public byte[] processClass(PluginDescriptionFile pdf, String path, byte[] clazz) {
        try {
            clazz = Commodore.convert(clazz, !isLegacy(pdf));
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Fatal error trying to convert " + pdf.getFullName() + ":" + path, exception);
        }

        return clazz;
    }

    public Multimap getDefaultAttributeModifiers(Material material, EquipmentSlot slot) {
        Builder defaultAttributes = ImmutableMultimap.builder();
        Multimap nmsDefaultAttributes = getItem(material).getDefaultAttributeModifiers(CraftEquipmentSlot.getNMS(slot));
        Iterator iterator = nmsDefaultAttributes.entries().iterator();

        while (iterator.hasNext()) {
            Entry mapEntry = (Entry) iterator.next();
            Attribute attribute = CraftAttribute.minecraftToBukkit((net.minecraft.world.entity.ai.attributes.Attribute) mapEntry.getKey());

            defaultAttributes.put(attribute, CraftAttributeInstance.convert((AttributeModifier) mapEntry.getValue(), slot));
        }

        return defaultAttributes.build();
    }

    public CreativeCategory getCreativeCategory(Material material) {
        return CreativeCategory.BUILDING_BLOCKS;
    }

    public String getBlockTranslationKey(Material material) {
        Block block = getBlock(material);

        return block != null ? block.getDescriptionId() : null;
    }

    public String getItemTranslationKey(Material material) {
        Item item = getItem(material);

        return item != null ? item.getDescriptionId() : null;
    }

    public String getTranslationKey(EntityType entityType) {
        Preconditions.checkArgument(entityType.getName() != null, "Invalid name of EntityType %s for translation key", entityType);
        return (String) net.minecraft.world.entity.EntityType.byString(entityType.getName()).map(net.minecraft.world.entity.EntityType::getDescriptionId).orElseThrow();
    }

    public String getTranslationKey(ItemStack itemStack) {
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

        return nmsItemStack.getItem().getDescriptionId(nmsItemStack);
    }

    public FeatureFlag getFeatureFlag(NamespacedKey namespacedKey) {
        Preconditions.checkArgument(namespacedKey != null, "NamespaceKey cannot be null");
        return CraftFeatureFlag.getFromNMS(namespacedKey);
    }

    public InternalPotionData getInternalPotionData(NamespacedKey namespacedKey) {
        Potion potionRegistry = (Potion) CraftRegistry.getMinecraftRegistry(Registries.POTION).getOptional(CraftNamespacedKey.toMinecraft(namespacedKey)).orElseThrow();

        return new CraftPotionType(namespacedKey, potionRegistry);
    }

    public static class NBT {

        public static final int TAG_END = 0;
        public static final int TAG_BYTE = 1;
        public static final int TAG_SHORT = 2;
        public static final int TAG_INT = 3;
        public static final int TAG_LONG = 4;
        public static final int TAG_FLOAT = 5;
        public static final int TAG_DOUBLE = 6;
        public static final int TAG_BYTE_ARRAY = 7;
        public static final int TAG_STRING = 8;
        public static final int TAG_LIST = 9;
        public static final int TAG_COMPOUND = 10;
        public static final int TAG_INT_ARRAY = 11;
        public static final int TAG_ANY_NUMBER = 99;
    }
}
