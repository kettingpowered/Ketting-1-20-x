package org.bukkit.craftbukkit.v1_20_R2.legacy;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.util.datafix.fixes.ItemIdFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

/** @deprecated */
@Deprecated
public final class CraftLegacy {

    private static final Map SPAWN_EGGS = new HashMap();
    private static final Set whitelistedStates = new HashSet(Arrays.asList("explode", "check_decay", "decayable", "facing"));
    private static final Map materialToItem = new HashMap(16384);
    private static final Map itemToMaterial = new HashMap(1024);
    private static final Map materialToData = new HashMap(4096);
    private static final Map dataToMaterial = new HashMap(4096);
    private static final Map materialToBlock = new HashMap(4096);
    private static final Map blockToMaterial = new HashMap(1024);

    static {
        System.err.println("Initializing Legacy Material Support. Unless you have legacy plugins and/or data this is a bug!");
        if (MinecraftServer.getServer() != null && MinecraftServer.getServer().isDebugging()) {
            (new Exception()).printStackTrace();
        }

        CraftLegacy.SPAWN_EGGS.put((byte) 0, Material.PIG_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.BAT.getTypeId(), Material.BAT_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.BLAZE.getTypeId(), Material.BLAZE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.CAVE_SPIDER.getTypeId(), Material.CAVE_SPIDER_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.CHICKEN.getTypeId(), Material.CHICKEN_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.COD.getTypeId(), Material.COD_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.COW.getTypeId(), Material.COW_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.CREEPER.getTypeId(), Material.CREEPER_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.DOLPHIN.getTypeId(), Material.DOLPHIN_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.DONKEY.getTypeId(), Material.DONKEY_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.ELDER_GUARDIAN.getTypeId(), Material.ELDER_GUARDIAN_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.ENDERMAN.getTypeId(), Material.ENDERMAN_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.ENDERMITE.getTypeId(), Material.ENDERMITE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.EVOKER.getTypeId(), Material.EVOKER_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.GHAST.getTypeId(), Material.GHAST_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.GUARDIAN.getTypeId(), Material.GUARDIAN_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.HORSE.getTypeId(), Material.HORSE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.HUSK.getTypeId(), Material.HUSK_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.LLAMA.getTypeId(), Material.LLAMA_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.MAGMA_CUBE.getTypeId(), Material.MAGMA_CUBE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.MUSHROOM_COW.getTypeId(), Material.MOOSHROOM_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.MULE.getTypeId(), Material.MULE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.OCELOT.getTypeId(), Material.OCELOT_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.PARROT.getTypeId(), Material.PARROT_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.PIG.getTypeId(), Material.PIG_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.PHANTOM.getTypeId(), Material.PHANTOM_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.POLAR_BEAR.getTypeId(), Material.POLAR_BEAR_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.PUFFERFISH.getTypeId(), Material.PUFFERFISH_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.RABBIT.getTypeId(), Material.RABBIT_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SALMON.getTypeId(), Material.SALMON_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SHEEP.getTypeId(), Material.SHEEP_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SHULKER.getTypeId(), Material.SHULKER_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SILVERFISH.getTypeId(), Material.SILVERFISH_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SKELETON.getTypeId(), Material.SKELETON_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SKELETON_HORSE.getTypeId(), Material.SKELETON_HORSE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SLIME.getTypeId(), Material.SLIME_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SPIDER.getTypeId(), Material.SPIDER_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.SQUID.getTypeId(), Material.SQUID_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.STRAY.getTypeId(), Material.STRAY_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.TROPICAL_FISH.getTypeId(), Material.TROPICAL_FISH_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.TURTLE.getTypeId(), Material.TURTLE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.VEX.getTypeId(), Material.VEX_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.VILLAGER.getTypeId(), Material.VILLAGER_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.VINDICATOR.getTypeId(), Material.VINDICATOR_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.WITCH.getTypeId(), Material.WITCH_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.WITHER_SKELETON.getTypeId(), Material.WITHER_SKELETON_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.WOLF.getTypeId(), Material.WOLF_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.ZOMBIE.getTypeId(), Material.ZOMBIE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.ZOMBIE_HORSE.getTypeId(), Material.ZOMBIE_HORSE_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.ZOMBIFIED_PIGLIN.getTypeId(), Material.ZOMBIFIED_PIGLIN_SPAWN_EGG);
        CraftLegacy.SPAWN_EGGS.put((byte) EntityType.ZOMBIE_VILLAGER.getTypeId(), Material.ZOMBIE_VILLAGER_SPAWN_EGG);
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Material[] amaterial;
        int i = (amaterial = Material.values()).length;

        for (int j = 0; j < i; ++j) {
            Material material = amaterial[j];

            if (material.isLegacy()) {
                if (material.isBlock()) {
                    for (byte data = 0; data < 16; ++data) {
                        MaterialData matData = new MaterialData(material, data);
                        Dynamic blockTag = BlockStateData.getTag(material.getId() << 4 | data);

                        blockTag = DataFixers.getDataFixer().update(References.BLOCK_STATE, blockTag, 100, CraftMagicNumbers.INSTANCE.getDataVersion());
                        if (!blockTag.get("Name").asString("").contains("%%FILTER_ME%%")) {
                            String name = blockTag.get("Name").asString("");
                            Block block = (Block) BuiltInRegistries.BLOCK.get(new ResourceLocation(name));

                            if (block != null) {
                                BlockState blockData = block.defaultBlockState();
                                StateDefinition states = block.getStateDefinition();
                                Optional propMap = blockTag.getElement("Properties").result();

                                if (propMap.isPresent()) {
                                    CompoundTag properties = (CompoundTag) propMap.get();
                                    Iterator iterator = properties.getAllKeys().iterator();

                                    while (iterator.hasNext()) {
                                        String dataKey = (String) iterator.next();
                                        Property state = states.getProperty(dataKey);

                                        if (state == null) {
                                            Preconditions.checkArgument(CraftLegacy.whitelistedStates.contains(dataKey), "No state for %s", dataKey);
                                        } else {
                                            Preconditions.checkState(!properties.getString(dataKey).isEmpty(), "Empty data string");
                                            Optional opt = state.getValue(properties.getString(dataKey));

                                            Preconditions.checkArgument(opt.isPresent(), "No state value %s for %s", properties.getString(dataKey), dataKey);
                                            blockData = (BlockState) blockData.setValue(state, (Comparable) opt.get());
                                        }
                                    }
                                }

                                if (block != Blocks.AIR) {
                                    CraftLegacy.materialToData.put(matData, blockData);
                                    if (!CraftLegacy.dataToMaterial.containsKey(blockData)) {
                                        CraftLegacy.dataToMaterial.put(blockData, matData);
                                    }

                                    CraftLegacy.materialToBlock.put(matData, block);
                                    if (!CraftLegacy.blockToMaterial.containsKey(block)) {
                                        CraftLegacy.blockToMaterial.put(block, matData);
                                    }
                                }
                            }
                        }
                    }
                }

                int maxData = material.getMaxDurability() == 0 ? 16 : 1;

                if (material == Material.LEGACY_MONSTER_EGG) {
                    maxData = 121;
                }

                for (byte data = 0; data < maxData; ++data) {
                    if (material != Material.LEGACY_MONSTER_EGG && ItemIdFix.getItem(material.getId()) != null) {
                        MaterialData matData = new MaterialData(material, data);
                        CompoundTag stack = new CompoundTag();

                        stack.putInt("id", material.getId());
                        stack.putShort("Damage", data);
                        Dynamic converted = DataFixers.getDataFixer().update(References.ITEM_STACK, new Dynamic(NbtOps.INSTANCE, stack), -1, CraftMagicNumbers.INSTANCE.getDataVersion());
                        String newId = converted.get("id").asString("");

                        if (newId.equals("minecraft:spawn_egg")) {
                            newId = "minecraft:pig_spawn_egg";
                        }

                        Item newMaterial = (Item) BuiltInRegistries.ITEM.get(new ResourceLocation(newId));

                        if (newMaterial != Items.AIR) {
                            CraftLegacy.materialToItem.put(matData, newMaterial);
                            if (!CraftLegacy.itemToMaterial.containsKey(newMaterial)) {
                                CraftLegacy.itemToMaterial.put(newMaterial, matData);
                            }
                        }
                    }
                }

                Iterator iterator1 = CraftLegacy.SPAWN_EGGS.entrySet().iterator();

                while (iterator1.hasNext()) {
                    Entry entry = (Entry) iterator1.next();
                    MaterialData matData = new MaterialData(Material.LEGACY_MONSTER_EGG, (Byte) entry.getKey());
                    Item newMaterial = CraftMagicNumbers.getItem((Material) entry.getValue());

                    CraftLegacy.materialToItem.put(matData, newMaterial);
                    CraftLegacy.itemToMaterial.put(newMaterial, matData);
                }
            }
        }

    }

    private CraftLegacy() {}

    public static Material toLegacy(Material material) {
        return material != null && !material.isLegacy() ? toLegacyData(material).getItemType() : material;
    }

    public static MaterialData toLegacyData(Material material) {
        Preconditions.checkArgument(!material.isLegacy(), "toLegacy on legacy Material");
        MaterialData mappedData;

        if (material.isBlock()) {
            Block block = CraftMagicNumbers.getBlock(material);
            BlockState blockData = block.defaultBlockState();

            mappedData = (MaterialData) CraftLegacy.dataToMaterial.get(blockData);
            if (mappedData == null) {
                mappedData = (MaterialData) CraftLegacy.blockToMaterial.get(block);
                if (mappedData == null) {
                    mappedData = (MaterialData) CraftLegacy.itemToMaterial.get(block.asItem());
                }
            }
        } else {
            Item item = CraftMagicNumbers.getItem(material);

            mappedData = (MaterialData) CraftLegacy.itemToMaterial.get(item);
        }

        return mappedData == null ? new MaterialData(Material.LEGACY_AIR) : mappedData;
    }

    public static BlockState fromLegacyData(Material material, byte data) {
        Preconditions.checkArgument(material.isLegacy(), "fromLegacyData on modern Material");
        MaterialData materialData = new MaterialData(material, data);
        BlockState converted = (BlockState) CraftLegacy.materialToData.get(materialData);

        if (converted != null) {
            return converted;
        } else {
            Block convertedBlock = (Block) CraftLegacy.materialToBlock.get(materialData);

            return convertedBlock != null ? convertedBlock.defaultBlockState() : Blocks.AIR.defaultBlockState();
        }
    }

    public static Item fromLegacyData(Material material, short data) {
        Preconditions.checkArgument(material.isLegacy(), "fromLegacyData on modern Material. Did you forget to define a modern (1.13+) api-version in your plugin.yml?");
        MaterialData materialData = new MaterialData(material, (byte) data);
        Item convertedItem = (Item) CraftLegacy.materialToItem.get(materialData);

        if (convertedItem != null) {
            return convertedItem;
        } else {
            if (material.isBlock()) {
                BlockState converted = (BlockState) CraftLegacy.materialToData.get(materialData);

                if (converted != null) {
                    return converted.getBlock().asItem();
                }

                Block convertedBlock = (Block) CraftLegacy.materialToBlock.get(materialData);

                if (convertedBlock != null) {
                    return convertedBlock.asItem();
                }
            }

            return Items.AIR;
        }
    }

    public static byte toLegacyData(BlockState blockData) {
        return toLegacy(blockData).getData();
    }

    public static Material toLegacyMaterial(BlockState blockData) {
        return toLegacy(blockData).getItemType();
    }

    public static MaterialData toLegacy(BlockState blockData) {
        MaterialData mappedData = (MaterialData) CraftLegacy.dataToMaterial.get(blockData);

        if (mappedData == null) {
            mappedData = (MaterialData) CraftLegacy.blockToMaterial.get(blockData.getBlock());
        }

        return mappedData == null ? new MaterialData(Material.LEGACY_AIR) : mappedData;
    }

    public static Material fromLegacy(Material material) {
        return material != null && material.isLegacy() ? fromLegacy(new MaterialData(material)) : material;
    }

    public static Material fromLegacy(MaterialData materialData) {
        return fromLegacy(materialData, false);
    }

    public static Material fromLegacy(MaterialData materialData, boolean itemPriority) {
        Material material = materialData.getItemType();

        if (material != null && material.isLegacy()) {
            Material mappedData = null;
            Item item;

            if (itemPriority) {
                item = (Item) CraftLegacy.materialToItem.get(materialData);
                if (item != null) {
                    mappedData = CraftMagicNumbers.getMaterial(item);
                }
            }

            if (mappedData == null && material.isBlock()) {
                BlockState iblock = (BlockState) CraftLegacy.materialToData.get(materialData);

                if (iblock != null) {
                    mappedData = CraftMagicNumbers.getMaterial(iblock.getBlock());
                }

                if (mappedData == null) {
                    Block block = (Block) CraftLegacy.materialToBlock.get(materialData);

                    if (block != null) {
                        mappedData = CraftMagicNumbers.getMaterial(block);
                    }
                }
            }

            if (!itemPriority && mappedData == null) {
                item = (Item) CraftLegacy.materialToItem.get(materialData);
                if (item != null) {
                    mappedData = CraftMagicNumbers.getMaterial(item);
                }
            }

            return mappedData == null ? Material.AIR : mappedData;
        } else {
            return material;
        }
    }

    public static Material[] values() {
        Material[] values = Material.values();

        return (Material[]) Arrays.copyOfRange(values, Material.LEGACY_AIR.ordinal(), values.length);
    }

    public static Material valueOf(String name) {
        return name.startsWith("LEGACY_") ? Material.valueOf(name) : Material.valueOf("LEGACY_" + name);
    }

    public static Material getMaterial(String name) {
        return name.startsWith("LEGACY_") ? Material.getMaterial(name) : Material.getMaterial("LEGACY_" + name);
    }

    public static Material matchMaterial(String name) {
        return name.startsWith("LEGACY_") ? Material.matchMaterial(name) : Material.matchMaterial("LEGACY_" + name);
    }

    public static int ordinal(Material material) {
        Preconditions.checkArgument(material.isLegacy(), "ordinal on modern Material");
        return material.ordinal() - Material.LEGACY_AIR.ordinal();
    }

    public static String name(Material material) {
        return material.name().substring("LEGACY_".length());
    }

    public static String toString(Material material) {
        return name(material);
    }

    public static void init() {}

    public static void main(String[] args) {
        System.err.println("");
    }
}
