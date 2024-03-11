package org.kettingpowered.ketting.inject;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.izzel.arclight.api.EnumHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R1.CraftStatistic;
import org.bukkit.craftbukkit.v1_20_R1.block.*;
import org.bukkit.craftbukkit.v1_20_R1.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R1.block.impl.CraftFloorSign;
import org.bukkit.craftbukkit.v1_20_R1.block.impl.CraftWallHangingSign;
import org.bukkit.craftbukkit.v1_20_R1.block.impl.CraftWallSign;
import org.bukkit.craftbukkit.v1_20_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_20_R1.potion.CraftPotionEffectType;
import org.bukkit.craftbukkit.v1_20_R1.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.kettingpowered.ketting.config.KettingConfig;
import org.kettingpowered.ketting.core.Ketting;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ForgeInject {

    public static void debug(String message) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.info(message);
        else
            Ketting.LOGGER.debug(message);
    }

    public static void debug(String message, Object... args) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.info(message, args);
        else
            Ketting.LOGGER.debug(message, args);
    }

    public static void debugWarn(String message) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.warn(message);
        else
            Ketting.LOGGER.debug("WARN - " + message);
    }

    public static void debugWarn(String message, Object... args) {
        if (KettingConfig.getInstance().PRINT_INJECTIONS.getValue())
            Ketting.LOGGER.warn(message, args);
        else
            Ketting.LOGGER.debug("WARN - " + message, args);
    }

    public static String standardize(@NotNull ResourceLocation location) {
        Preconditions.checkNotNull(location, "location");
        return (location.getNamespace().equals(NamespacedKey.MINECRAFT) ? location.getPath() : location.toString())
                .replace(':', '_')
                .replaceAll("\\s+", "_")
                .replaceAll("\\W", "")
                .toUpperCase(Locale.ENGLISH);
    }

    public static String standardizeLower(@NotNull ResourceLocation location) {
        return (location.getNamespace().equals(NamespacedKey.MINECRAFT) ? location.getPath() : location.toString())
                .replace(':', '_')
                .replaceAll("\\s+", "_")
                .replaceAll("\\W", "")
                .toLowerCase(Locale.ENGLISH);
    }

    public static final Map<Villager.Profession, ResourceLocation> PROFESSIONS = new HashMap<>();
    public static final Map<net.minecraft.world.entity.EntityType<?>, String> ENTITY_TYPES = new ConcurrentHashMap<>();
    private static final Map<Class<?>, List<Map.Entry<Block, Material>>> MATERIALS = new ConcurrentHashMap<>();

    public static EntityType getBukkitEntityType(Entity entity) {
        EntityType type = EntityType.fromName(ENTITY_TYPES.get(entity.getType()));
        return type == null ? EntityType.UNKNOWN : type;
    }

    //Credit goes to Arclight for this fix
    public static Pose getBukkitEntityPose(net.minecraft.world.entity.Pose nms) {
        if (Pose.values().length <= nms.ordinal()) {
            var newTypes = new ArrayList<Pose>();
            var forgeCount = net.minecraft.world.entity.Pose.values().length;
            for (var id = Pose.values().length; id < forgeCount; id++) {
                var name = net.minecraft.world.entity.Pose.values()[id].name();
                var newPhase = EnumHelper.makeEnum(Pose.class, name, id, List.of(), List.of());
                newTypes.add(newPhase);
                debug("Registering NMS pose {} as pose {}", name, newPhase);
            }
            EnumHelper.addEnums(Pose.class, newTypes);
        }
        return org.bukkit.entity.Pose.values()[nms.ordinal()];
    }

    public static void doInjectingMagic() {
        debug("Injecting Forge Materials into Bukkit");
        addForgeMaterials();
        debug("Registering Forge Materials to Bukkit");
        registerForgeMaterials();
        debug("Injecting Forge Enchantments into Bukkit");
        addForgeEnchantments();
        debug("Injecting Forge Potions into Bukkit");
        addForgePotions();
        debug("Injecting Forge Biomes into Bukkit");
        addForgeBiomes();
        debug("Injecting Forge Entities into Bukkit");
        addForgeEntities();
        debug("Injecting Forge VillagerProfessions into Bukkit");
        addForgeVillagerProfessions();
        debug("Injecting Forge statistics into bukkit");
        addForgeStatistics();
        debug("Injecting Forge into Bukkit: DONE");

        try {
            for (var field : org.bukkit.Registry.class.getFields()) {
                if (Modifier.isStatic(field.getModifiers())
                        && field.get(null) instanceof org.bukkit.Registry.SimpleRegistry<?> registry) {
                    registry.reloader.run();
                }
            }
        } catch (Throwable ignored) {}
    }

    private static void addForgeMaterials() {
        int ordinal = Material.values().length;
        List<Material> values = new ArrayList<>();
        int origin = ordinal;
        int blocks = 0;
        for (var entry : ForgeRegistries.BLOCKS.getEntries()) {
            var location = entry.getKey().location();
            if (location.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                continue;
            }
            // inject block materials into Bukkit for FML
            var enumName = standardize(location);
            var block = entry.getValue();
            var item = ForgeRegistries.ITEMS.getValue(location);
            try {
                Class<?> match = CraftBlockData.getClosestBlockDataClass(block.getClass());
                Class<?> blockDataClass = match == null ? null : match.getInterfaces()[0];
                var material = blockDataClass == null ? Material.addMaterial(enumName, ordinal,
                        CraftNamespacedKey.fromMinecraft(location), true, item != null && item != Items.AIR)
                        : Material.addMaterial(enumName, ordinal, blockDataClass,
                        CraftNamespacedKey.fromMinecraft(location), true, item != null && item != Items.AIR);
                if (material == null) {
                    Ketting.LOGGER.warn("Could not inject block into Bukkit: " + enumName);
                    continue;
                }
                ordinal++;
                values.add(material);
                blocks++;
                CraftMagicNumbers.BLOCK_MATERIAL.put(block, material);
                CraftMagicNumbers.MATERIAL_BLOCK.put(material, block);
                if (match != null)
                    MATERIALS.computeIfAbsent(match, k -> new ArrayList<>()).add(new AbstractMap.SimpleEntry<>(block, material));
                debug("Injecting Forge Blocks into Bukkit: " + material.name());
                if (blockDataClass != null) {
                    debug("Assigning block data " + blockDataClass + " to " + material.name() + " because it extends " + block.getClass());
                }
            } catch (Throwable e) {
                Ketting.LOGGER.error("Could not inject block into Bukkit: " + enumName + ". " + e.getMessage());
            }
        }
        debug("Injecting Forge Blocks into Bukkit: DONE");

        int items = 0;
        for (var entry : ForgeRegistries.ITEMS.getEntries()) {
            var location = entry.getKey().location();
            if (location.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                continue;
            }
            // inject item materials into Bukkit for FML
            var enumName = standardize(location);
            var item = entry.getValue();
            var material = Material.getMaterial(enumName);
            // Material may already be registered by a block
            if (material == null) {
                try {
                    material = Material.addMaterial(enumName, ordinal, CraftNamespacedKey.fromMinecraft(location),
                            false, true);
                    if (material == null) {
                        Ketting.LOGGER.warn("Could not inject item into Bukkit: " + enumName);
                        continue;
                    }
                    values.add(material);
                    ordinal++;
                    items++;
                    debug("Injecting Forge Material into Bukkit: " + material.name());
                } catch (Throwable e) {
                    Ketting.LOGGER.error("Could not inject item into Bukkit: " + enumName + ". " + e.getMessage());
                }
            }
            CraftMagicNumbers.ITEM_MATERIAL.put(item, material);
            CraftMagicNumbers.MATERIAL_ITEM.put(material, item);
        }
        debug("Injecting Forge Material into Bukkit: DONE");
        EnumHelper.addEnums(Material.class, values);
        Ketting.LOGGER.info("Injected {} modded materials ({} blocks, {} items)", ordinal - origin, blocks, items);
    }

    private static void registerForgeMaterials() {
        final Map<Class<?>, List<Map.Entry<Block, Material>>> materials = new ConcurrentHashMap<>(MATERIALS);
        //see CraftBlockStates static {} method

        registerHangingSigns(materials);
        registerSigns(materials);
        registerMaterialsFor(materials, CraftSkull.class, SkullBlockEntity.class, CraftSkull::new);
        registerMaterialsFor(materials, CraftCommandBlock.class, CommandBlockEntity.class, CraftCommandBlock::new);
        registerMaterialsFor(materials, CraftBanner.class, BannerBlockEntity.class, CraftBanner::new);
        registerMaterialsFor(materials, CraftShulkerBox.class, ShulkerBoxBlockEntity.class, CraftShulkerBox::new);
        registerMaterialsFor(materials, CraftBed.class, BedBlockEntity.class, CraftBed::new);
        registerMaterialsFor(materials, CraftBeehive.class, BeehiveBlockEntity.class, CraftBeehive::new);
        registerMaterialsFor(materials, CraftCampfire.class, CampfireBlockEntity.class, CraftCampfire::new);
        registerMaterialsFor(materials, CraftBarrel.class, BarrelBlockEntity.class, CraftBarrel::new);
        registerMaterialsFor(materials, CraftBeacon.class, BeaconBlockEntity.class, CraftBeacon::new);
        registerMaterialsFor(materials, CraftBell.class, BellBlockEntity.class, CraftBell::new);
        registerMaterialsFor(materials, CraftBlastFurnace.class, BlastFurnaceBlockEntity.class, CraftBlastFurnace::new);
        registerMaterialsFor(materials, CraftBrewingStand.class, BrewingStandBlockEntity.class, CraftBrewingStand::new);
        registerMaterialsFor(materials, CraftChiseledBookshelf.class, ChiseledBookShelfBlockEntity.class, CraftChiseledBookshelf::new);
        registerMaterialsFor(materials, CraftComparator.class, ComparatorBlockEntity.class, CraftComparator::new);
        registerMaterialsFor(materials, CraftConduit.class, ConduitBlockEntity.class, CraftConduit::new);
        registerMaterialsFor(materials, CraftDaylightDetector.class, DaylightDetectorBlockEntity.class, CraftDaylightDetector::new);
        registerMaterialsFor(materials, CraftDecoratedPot.class, DecoratedPotBlockEntity.class, CraftDecoratedPot::new);
        registerMaterialsFor(materials, CraftDispenser.class, DispenserBlockEntity.class, CraftDispenser::new);
        registerMaterialsFor(materials, CraftDropper.class, DropperBlockEntity.class, CraftDropper::new);
        registerMaterialsFor(materials, CraftEnchantingTable.class, EnchantmentTableBlockEntity.class, CraftEnchantingTable::new);
        registerMaterialsFor(materials, CraftEnderChest.class, EnderChestBlockEntity.class, CraftEnderChest::new);
        registerMaterialsFor(materials, CraftEndGateway.class, TheEndGatewayBlockEntity.class, CraftEndGateway::new);
        registerMaterialsFor(materials, CraftEndPortal.class, TheEndPortalBlockEntity.class, CraftEndPortal::new);
        registerMaterialsFor(materials, CraftFurnaceFurnace.class, FurnaceBlockEntity.class, CraftFurnaceFurnace::new);
        registerMaterialsFor(materials, CraftHopper.class, HopperBlockEntity.class, CraftHopper::new);
        registerMaterialsFor(materials, CraftJigsaw.class, JigsawBlockEntity.class, CraftJigsaw::new);
        registerMaterialsFor(materials, CraftJukebox.class, JukeboxBlockEntity.class, CraftJukebox::new);
        registerMaterialsFor(materials, CraftLectern.class, LecternBlockEntity.class, CraftLectern::new);
        registerMaterialsFor(materials, CraftMovingPiston.class, PistonMovingBlockEntity.class, CraftMovingPiston::new);
        registerMaterialsFor(materials, CraftSculkCatalyst.class, SculkCatalystBlockEntity.class, CraftSculkCatalyst::new);
        registerMaterialsFor(materials, CraftCalibratedSculkSensor.class, CalibratedSculkSensorBlockEntity.class, CraftCalibratedSculkSensor::new);
        registerMaterialsFor(materials, CraftSculkSensor.class, SculkSensorBlockEntity.class, CraftSculkSensor::new);
        registerMaterialsFor(materials, CraftSculkShrieker.class, SculkShriekerBlockEntity.class, CraftSculkShrieker::new);
        registerMaterialsFor(materials, CraftSmoker.class, SmokerBlockEntity.class, CraftSmoker::new);
        registerMaterialsFor(materials, CraftCreatureSpawner.class, SpawnerBlockEntity.class, CraftCreatureSpawner::new);
        registerMaterialsFor(materials, CraftStructureBlock.class, StructureBlockEntity.class, CraftStructureBlock::new);
        registerMaterialsFor(materials, CraftSuspiciousSand.class, BrushableBlockEntity.class, CraftSuspiciousSand::new);
        registerMaterialsFor(materials, CraftBrushableBlock.class, BrushableBlockEntity.class, CraftBrushableBlock::new);
        registerChests(materials);
        materials.keySet().forEach(craftClass -> {
            debugWarn("Could not find a matching block entity for " + craftClass.getSimpleName());
        });
        debug("Registering Forge Materials: DONE");
    }

    private static <T extends BlockEntity, B extends CraftBlockEntityState<T>> void registerMaterialsFor(@NotNull Map<Class<?>, List<Map.Entry<Block, Material>>> materialsMap, Class<B> craftClass, Class<T> entityClass, BiFunction<World, T, B> blockStateConstructor) {
        final List<Map.Entry<Block, Material>> materials = materialsMap.remove(craftClass);
        if (materials == null || materials.isEmpty())
            return;

        for (Map.Entry<Block, Material> entry : materials) {
            final Block block = entry.getKey();
            final Material material = entry.getValue();

            if (block instanceof EntityBlock entityBlock) {
                debug("Registering " + material.name() + " as " + craftClass.getSimpleName());
                CraftBlockStates.register(material, craftClass, blockStateConstructor, (pos, state) -> {
                    final BlockEntity blockEntity = entityBlock.newBlockEntity(pos, state);
                    try {
                        return entityClass.cast(blockEntity);
                    } catch (ClassCastException cce) {
                        Ketting.LOGGER.error("Could not register " + material.name() + " as " + craftClass.getSimpleName(), cce);
                        return null;
                    }
                });
            }
        }
    }

    private static void registerHangingSigns(@NotNull Map<Class<?>, List<Map.Entry<Block, Material>>> materialsMap) {
        final List<Map.Entry<Block, Material>> materials = new ArrayList<>();
        if (materialsMap.containsKey(CraftHangingSign.class))
            materials.addAll(materialsMap.remove(CraftHangingSign.class));
        if (materialsMap.containsKey(CraftWallHangingSign.class))
            materials.addAll(materialsMap.remove(CraftWallHangingSign.class));

        if (materials.isEmpty())
            return;

        for (Map.Entry<Block, Material> entry : materials) {
            final Block block = entry.getKey();
            final Material material = entry.getValue();

            if (block instanceof EntityBlock entityBlock) {
                debug("Registering " + material.name() + " as " + CraftHangingSign.class.getSimpleName());
                CraftBlockStates.register(material, CraftHangingSign.class, CraftHangingSign::new, (pos, state) -> {
                    final BlockEntity blockEntity = entityBlock.newBlockEntity(pos, state);
                    try {
                        return HangingSignBlockEntity.class.cast(blockEntity);
                    } catch (ClassCastException cce) {
                        Ketting.LOGGER.error("Could not register " + material.name() + " as " + CraftHangingSign.class.getSimpleName(), cce);
                        return null;
                    }
                });
            }
        }
    }

    private static void registerSigns(@NotNull Map<Class<?>, List<Map.Entry<Block, Material>>> materialsMap) {
        final List<Map.Entry<Block, Material>> materials = new ArrayList<>();
        if (materialsMap.containsKey(CraftSign.class))
            materials.addAll(materialsMap.remove(CraftSign.class));
        if (materialsMap.containsKey(CraftWallSign.class))
            materials.addAll(materialsMap.remove(CraftWallSign.class));
        if (materialsMap.containsKey(CraftFloorSign.class))
            materials.addAll(materialsMap.remove(CraftFloorSign.class));

        if (materials.isEmpty())
            return;

        for (Map.Entry<Block, Material> entry : materials) {
            final Block block = entry.getKey();
            final Material material = entry.getValue();

            if (block instanceof EntityBlock entityBlock) {
                debug("Registering " + material.name() + " as " + CraftSign.class.getSimpleName());
                CraftBlockStates.register(material, CraftSign.class, CraftSign::new, (pos, state) -> {
                    final BlockEntity blockEntity = entityBlock.newBlockEntity(pos, state);
                    try {
                        return SignBlockEntity.class.cast(blockEntity);
                    } catch (ClassCastException cce) {
                        Ketting.LOGGER.error("Could not register " + material.name() + " as " + CraftSign.class.getSimpleName(), cce);
                        return null;
                    }
                });
            }
        }
    }

    private static void registerChests(@NotNull Map<Class<?>, List<Map.Entry<Block, Material>>> materialsMap) {
        final List<Map.Entry<Block, Material>> materials = materialsMap.remove(CraftChest.class);
        if (materials == null || materials.isEmpty())
            return;

        for (Map.Entry<Block, Material> entry : materials) {
            final Block block = entry.getKey();
            final Material material = entry.getValue();

            if (block instanceof EntityBlock entityBlock) {
                debug("Registering " + material.name() + " as " + CraftChest.class.getSimpleName());
                CraftBlockStates.register(material, CraftChest.class, CraftChest::new, (pos, state) -> {
                    final BlockEntity blockEntity = entityBlock.newBlockEntity(pos, state);
                    try {
                        return (block instanceof TrappedChestBlock ? TrappedChestBlockEntity.class : ChestBlockEntity.class).cast(blockEntity);
                    } catch (ClassCastException cce) {
                        Ketting.LOGGER.error("Could not register " + material.name() + " as " + CraftChest.class.getSimpleName(), cce);
                        return null;
                    }
                });
            }
        }
    }

    private static void addForgeEnchantments() {
        ForgeRegistries.ENCHANTMENTS.getEntries().forEach(entry -> {
            CraftEnchantment enchantment = new CraftEnchantment(entry.getValue());
            if (!org.bukkit.enchantments.Enchantment.byKey.containsKey(enchantment.getKey())
                    || !org.bukkit.enchantments.Enchantment.byName.containsKey(enchantment.getName())) {
                org.bukkit.enchantments.Enchantment.byKey.put(enchantment.getKey(), enchantment);
                org.bukkit.enchantments.Enchantment.byName.put(enchantment.getName(), enchantment);
                debug("Injecting Forge Enchantments into Bukkit: " + enchantment.getName());
            }
        });
        debug("Injecting Forge Enchantments into Bukkit: DONE");
    }

    private static void addForgePotions() {
        PotionEffectType.startAcceptingRegistrations();
        ForgeRegistries.MOB_EFFECTS.getEntries().forEach(entry -> {
            var pet = new CraftPotionEffectType(entry.getValue());

            if (pet == null)
                return;

            try {
                PotionEffectType.registerPotionEffectType(pet);
                debug("Registering Forge Potion into Bukkit: " + pet.getName());
            } catch (IllegalStateException e) {
                Ketting.LOGGER.error("Could not register potion effect into Bukkit: " + pet.getName() + ". " + e.getMessage());
            }
        });
        PotionEffectType.stopAcceptingRegistrations();
        // Stage 1 complete - now to add the types to bukkit


        int ordinal = EntityType.values().length;
        List<PotionType> values = new ArrayList<>();
        BiMap<PotionType, String> newRegular = HashBiMap.create(CraftPotionUtil.regular);
        for (var entry : ForgeRegistries.POTIONS.getEntries()) {
            var location = entry.getKey().location();
            if (location.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                continue;
            }
            var enumName = standardize(location);
            var potion = entry.getValue();
            var effect = potion.getEffects().isEmpty() ? null : potion.getEffects().get(0);
            PotionEffectType type = null;
            if (effect != null) type = PotionEffectType.getById(MobEffect.getId(effect.getEffect()));
            if (type == null) type = PotionEffectType.NORMAL;
            try {
                var potionType = EnumHelper.makeEnum(PotionType.class, enumName, ordinal,
                        List.of(PotionEffectType.class, boolean.class, boolean.class),
                        List.of(type, false, false));
                if (potionType == null) {
                    Ketting.LOGGER.error("Could not inject potion into Bukkit: " + enumName + ". PotionType is null");
                    continue;
                }
                ordinal++;
                values.add(potionType);
                newRegular.put(potionType, ForgeRegistries.POTIONS.getKey(potion).toString());
                debug("Injecting Forge Potion into Bukkit: " + potionType.name());
            } catch (Throwable e) {
                Ketting.LOGGER.error("Could not inject potion into Bukkit: " + enumName + ". " + e.getMessage());
            }
        }
        CraftPotionUtil.regular = newRegular;
        EnumHelper.addEnums(PotionType.class, values);
        debug("Injecting Forge Potion into Bukkit: DONE");
    }

    private static void addForgeBiomes() {
        int ordinal = EntityType.values().length;
        List<Biome> values = new ArrayList<>();
        for (var entry : ForgeRegistries.BIOMES.getEntries()) {
            var location = entry.getKey().location();
            if (location.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                continue;
            }
            var enumName = standardize(location);
            try {
                var biome = EnumHelper.makeEnum(Biome.class, enumName, ordinal, List.of(), List.of());
                ordinal++;
                values.add(biome);
                debug("Injecting Forge Biome into Bukkit: " + biome.name());
            } catch (Throwable e) {
                Ketting.LOGGER.error("Could not inject biome into Bukkit: " + enumName + ". " + e.getMessage());
            }
        }
        EnumHelper.addEnums(Biome.class, values);
        debug("Injecting Forge Biome into Bukkit: DONE");
    }

    private static void addForgeEntities() {
        int ordinal = EntityType.values().length;
        List<EntityType> values = new ArrayList<>();
        for (var entry : ForgeRegistries.ENTITY_TYPES.getEntries()) {
            var location = ForgeRegistries.ENTITY_TYPES.getKey(entry.getValue());
            var enumName = standardize(location);
            ENTITY_TYPES.put(entry.getValue(), enumName);
            if (location.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                continue;
            }
            int typeId = enumName.hashCode();
            try {
                var bukkitType = EnumHelper.makeEnum(EntityType.class, enumName, ordinal,
                        List.of(String.class, Class.class, Integer.TYPE, Boolean.TYPE, String.class),
                        List.of(location.getPath(), org.bukkit.entity.Entity.class, typeId, false, location.getNamespace()));
                EntityType.NAME_MAP.put(enumName.toLowerCase(), bukkitType);
                EntityType.ID_MAP.put((short) typeId, bukkitType);
                bukkitType.createFactory(entry.getValue());
                ordinal++;
                values.add(bukkitType);
                debug("Injecting Forge Entity into Bukkit: " + enumName);
            } catch (Throwable e) {
                Ketting.LOGGER.error("Could not inject entity into Bukkit: " + enumName + ". " + e.getMessage());
            }
        }
        EnumHelper.addEnums(EntityType.class, values);
        debug("Injecting Forge Entity into Bukkit: DONE");
    }

    private static void addForgeVillagerProfessions() {
        int ordinal = Villager.Profession.values().length;
        List<Villager.Profession> values = new ArrayList<>();
        for (var entry : ForgeRegistries.VILLAGER_PROFESSIONS.getEntries()) {
            var location = entry.getKey().location();
            if (!location.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                var enumName = standardize(location);
                try {
                    var profession = EnumHelper.makeEnum(Villager.Profession.class, enumName, ordinal, List.of(), List.of());
                    values.add(profession);
                    ordinal++;
                    PROFESSIONS.put(profession, location);
                    debug("Injecting Forge VillagerProfession into Bukkit: " + profession.name());
                } catch (Throwable e) {
                    Ketting.LOGGER.error("Could not inject villager profession into Bukkit: " + enumName + ". " + e.getMessage());
                }
            }
        }
        EnumHelper.addEnums(Villager.Profession.class, values);
        debug("Injecting Forge VillagerProfession into Bukkit: DONE");
    }

    public static void addForgeStatistics() {
        List<Statistic> values = new ArrayList<>();
        var statistics = HashBiMap.create(CraftStatistic.statistics);
        int ordinal = Statistic.values().length;
        Stats.CUSTOM.forEach(stat -> {
            ResourceLocation resourceLocation = stat.getValue();
            if (!statistics.containsKey(resourceLocation)) {
                var name = standardize(resourceLocation);
                try {
                    var statistic = EnumHelper.makeEnum(Statistic.class, name, ordinal, List.of(), List.of());
                    statistic.setInjected();
                    values.add(statistic);
                    statistics.put(resourceLocation, statistic);
                    debug("Injected Forge Statistic into Bukkit: " + statistic.name());
                } catch (Throwable e) {
                    Ketting.LOGGER.error("Could not inject statistic into Bukkit: " + name + ". " + e.getMessage());
                }
            }
        });
        EnumHelper.addEnums(Statistic.class, values);
        CraftStatistic.statistics = statistics;
        debug("Injecting Forge Statistic into Bukkit: DONE");
    }
}
