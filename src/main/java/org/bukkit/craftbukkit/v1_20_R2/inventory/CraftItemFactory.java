package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntityType;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLegacy;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class CraftItemFactory implements ItemFactory {

    static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(10511680);
    private static final CraftItemFactory instance = new CraftItemFactory();
    private static volatile int[] $SWITCH_TABLE$org$bukkit$Material;

    static {
        ConfigurationSerialization.registerClass(CraftMetaItem.SerializableMeta.class);
    }

    private CraftItemFactory() {}

    public boolean isApplicable(ItemMeta meta, ItemStack itemstack) {
        return itemstack == null ? false : this.isApplicable(meta, itemstack.getType());
    }

    public boolean isApplicable(ItemMeta meta, Material type) {
        type = CraftLegacy.fromLegacy(type);
        if (type != null && meta != null) {
            Preconditions.checkArgument(meta instanceof CraftMetaItem, "Meta of %s not created by %s", meta.getClass().toString(), CraftItemFactory.class.getName());
            return ((CraftMetaItem) meta).applicableTo(type);
        } else {
            return false;
        }
    }

    public ItemMeta getItemMeta(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        return this.getItemMeta(material, (CraftMetaItem) null);
    }

    private ItemMeta getItemMeta(Material material, CraftMetaItem meta) {
        material = CraftLegacy.fromLegacy(material);
        switch ($SWITCH_TABLE$org$bukkit$Material()[material.ordinal()]) {
            case 1:
                return null;
            case 46:
            case 47:
            case 266:
            case 267:
            case 277:
            case 278:
            case 281:
            case 289:
            case 352:
            case 353:
            case 354:
            case 360:
            case 374:
            case 375:
            case 493:
            case 494:
            case 501:
            case 502:
            case 503:
            case 504:
            case 505:
            case 506:
            case 507:
            case 508:
            case 509:
            case 510:
            case 511:
            case 512:
            case 513:
            case 514:
            case 515:
            case 516:
            case 517:
            case 640:
            case 646:
            case 647:
            case 648:
            case 649:
            case 653:
            case 654:
            case 655:
            case 657:
            case 755:
            case 756:
            case 847:
            case 848:
            case 849:
            case 850:
            case 851:
            case 852:
            case 853:
            case 854:
            case 855:
            case 856:
            case 857:
            case 858:
            case 859:
            case 860:
            case 861:
            case 862:
            case 863:
            case 864:
            case 865:
            case 866:
            case 867:
            case 868:
            case 964:
            case 1117:
            case 1155:
            case 1156:
            case 1157:
            case 1163:
            case 1168:
            case 1169:
            case 1172:
            case 1173:
            case 1265:
            case 1266:
            case 1267:
            case 1268:
            case 1269:
            case 1270:
            case 1271:
            case 1272:
            case 1273:
            case 1274:
            case 1275:
            case 1276:
            case 1277:
            case 1278:
            case 1279:
            case 1280:
            case 1281:
            case 1282:
            case 1283:
            case 1284:
            case 1374:
            case 1375:
                return new CraftMetaBlockState(meta, material);
            case 757:
            case 821:
            case 822:
            case 823:
            case 824:
            case 825:
            case 826:
            case 827:
            case 828:
            case 829:
            case 830:
            case 831:
            case 832:
            case 833:
            case 834:
            case 835:
            case 836:
            case 837:
            case 838:
            case 839:
            case 840:
                return (ItemMeta) (meta != null && meta.getClass().equals(CraftMetaArmor.class) ? meta : new CraftMetaArmor(meta));
            case 817:
            case 818:
            case 819:
            case 820:
                return (ItemMeta) (meta instanceof CraftMetaColorableArmor ? meta : new CraftMetaColorableArmor(meta));
            case 844:
            case 876:
            case 877:
            case 878:
            case 1049:
            case 1050:
                return (ItemMeta) (meta instanceof CraftMetaEntityTag ? meta : new CraftMetaEntityTag(meta));
            case 879:
                return (ItemMeta) (meta instanceof CraftMetaTropicalFishBucket ? meta : new CraftMetaTropicalFishBucket(meta));
            case 880:
                return (ItemMeta) (meta instanceof CraftMetaAxolotlBucket ? meta : new CraftMetaAxolotlBucket(meta));
            case 889:
                return (ItemMeta) (meta instanceof CraftMetaCompass ? meta : new CraftMetaCompass(meta));
            case 891:
                return (ItemMeta) (meta instanceof CraftMetaBundle ? meta : new CraftMetaBundle(meta));
            case 942:
                return (ItemMeta) (meta instanceof CraftMetaMap ? meta : new CraftMetaMap(meta));
            case 958:
            case 1113:
            case 1115:
            case 1116:
                return (ItemMeta) (meta instanceof CraftMetaPotion ? meta : new CraftMetaPotion(meta));
            case 968:
            case 969:
            case 970:
            case 971:
            case 972:
            case 973:
            case 974:
            case 975:
            case 976:
            case 977:
            case 978:
            case 979:
            case 980:
            case 981:
            case 982:
            case 983:
            case 984:
            case 985:
            case 986:
            case 987:
            case 988:
            case 989:
            case 990:
            case 991:
            case 992:
            case 993:
            case 994:
            case 995:
            case 996:
            case 997:
            case 998:
            case 999:
            case 1000:
            case 1001:
            case 1002:
            case 1003:
            case 1004:
            case 1005:
            case 1006:
            case 1007:
            case 1008:
            case 1009:
            case 1010:
            case 1011:
            case 1012:
            case 1013:
            case 1014:
            case 1015:
            case 1016:
            case 1017:
            case 1018:
            case 1019:
            case 1020:
            case 1021:
            case 1022:
            case 1023:
            case 1024:
            case 1025:
            case 1026:
            case 1027:
            case 1028:
            case 1029:
            case 1030:
            case 1031:
            case 1032:
            case 1033:
            case 1034:
            case 1035:
            case 1036:
            case 1037:
            case 1038:
            case 1039:
            case 1040:
            case 1041:
            case 1042:
            case 1043:
            case 1044:
                return (ItemMeta) (meta instanceof CraftMetaSpawnEgg ? meta : new CraftMetaSpawnEgg(meta));
            case 1047:
                return (ItemMeta) (meta != null && meta.getClass().equals(CraftMetaBook.class) ? meta : new CraftMetaBook(meta));
            case 1048:
                return (ItemMeta) (meta instanceof CraftMetaBookSigned ? meta : new CraftMetaBookSigned(meta));
            case 1058:
            case 1059:
            case 1060:
            case 1061:
            case 1062:
            case 1063:
            case 1064:
            case 1327:
            case 1328:
            case 1329:
            case 1330:
            case 1331:
            case 1332:
            case 1333:
                return (ItemMeta) (meta instanceof CraftMetaSkull ? meta : new CraftMetaSkull(meta));
            case 1067:
                return (ItemMeta) (meta instanceof CraftMetaFirework ? meta : new CraftMetaFirework(meta));
            case 1068:
                return (ItemMeta) (meta instanceof CraftMetaCharge ? meta : new CraftMetaCharge(meta));
            case 1069:
                return (ItemMeta) (meta instanceof CraftMetaEnchantedBook ? meta : new CraftMetaEnchantedBook(meta));
            case 1078:
                return (ItemMeta) (meta instanceof CraftMetaArmorStand ? meta : new CraftMetaArmorStand(meta));
            case 1082:
                return (ItemMeta) (meta instanceof CraftMetaLeatherArmor ? meta : new CraftMetaLeatherArmor(meta));
            case 1088:
            case 1089:
            case 1090:
            case 1091:
            case 1092:
            case 1093:
            case 1094:
            case 1095:
            case 1096:
            case 1097:
            case 1098:
            case 1099:
            case 1100:
            case 1101:
            case 1102:
            case 1103:
            case 1334:
            case 1335:
            case 1336:
            case 1337:
            case 1338:
            case 1339:
            case 1340:
            case 1341:
            case 1342:
            case 1343:
            case 1344:
            case 1345:
            case 1346:
            case 1347:
            case 1348:
            case 1349:
                return (ItemMeta) (meta instanceof CraftMetaBanner ? meta : new CraftMetaBanner(meta));
            case 1121:
                return (ItemMeta) (meta instanceof CraftMetaKnowledgeBook ? meta : new CraftMetaKnowledgeBook(meta));
            case 1144:
                return (ItemMeta) (meta instanceof CraftMetaCrossbow ? meta : new CraftMetaCrossbow(meta));
            case 1145:
                return (ItemMeta) (meta instanceof CraftMetaSuspiciousStew ? meta : new CraftMetaSuspiciousStew(meta));
            case 1153:
                return (ItemMeta) (meta instanceof CraftMetaMusicInstrument ? meta : new CraftMetaMusicInstrument(meta));
            default:
                return new CraftMetaItem(meta);
        }
    }

    public boolean equals(ItemMeta meta1, ItemMeta meta2) {
        if (meta1 == meta2) {
            return true;
        } else if (meta1 != null) {
            Preconditions.checkArgument(meta1 instanceof CraftMetaItem, "First meta of %s does not belong to %s", meta1.getClass().getName(), CraftItemFactory.class.getName());
            if (meta2 != null) {
                Preconditions.checkArgument(meta2 instanceof CraftMetaItem, "Second meta of %s does not belong to %s", meta2.getClass().getName(), CraftItemFactory.class.getName());
                return this.equals((CraftMetaItem) meta1, (CraftMetaItem) meta2);
            } else {
                return ((CraftMetaItem) meta1).isEmpty();
            }
        } else {
            return ((CraftMetaItem) meta2).isEmpty();
        }
    }

    boolean equals(CraftMetaItem meta1, CraftMetaItem meta2) {
        return meta1.equalsCommon(meta2) && meta1.notUncommon(meta2) && meta2.notUncommon(meta1);
    }

    public static CraftItemFactory instance() {
        return CraftItemFactory.instance;
    }

    public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) {
        Preconditions.checkArgument(stack != null, "ItemStack stack cannot be null");
        return this.asMetaFor(meta, stack.getType());
    }

    public ItemMeta asMetaFor(ItemMeta meta, Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        Preconditions.checkArgument(meta instanceof CraftMetaItem, "ItemMeta of %s not created by %s", meta != null ? meta.getClass().toString() : "null", CraftItemFactory.class.getName());
        return this.getItemMeta(material, (CraftMetaItem) meta);
    }

    public Color getDefaultLeatherColor() {
        return CraftItemFactory.DEFAULT_LEATHER_COLOR;
    }

    public ItemStack createItemStack(String input) throws IllegalArgumentException {
        try {
            ItemParser.ItemResult arg = ItemParser.parseForItem(BuiltInRegistries.ITEM.asLookup(), new StringReader(input));
            Item item = (Item) arg.item().value();
            net.minecraft.world.item.ItemStack nmsItemStack = new net.minecraft.world.item.ItemStack(item);
            CompoundTag nbt = arg.nbt();

            if (nbt != null) {
                nmsItemStack.setTag(nbt);
            }

            return CraftItemStack.asCraftMirror(nmsItemStack);
        } catch (CommandSyntaxException commandsyntaxexception) {
            throw new IllegalArgumentException("Could not parse ItemStack: " + input, commandsyntaxexception);
        }
    }

    public Material updateMaterial(ItemMeta meta, Material material) throws IllegalArgumentException {
        return ((CraftMetaItem) meta).updateMaterial(material);
    }

    public Material getSpawnEgg(EntityType type) {
        if (type == EntityType.UNKNOWN) {
            return null;
        } else {
            net.minecraft.world.entity.EntityType nmsType = CraftEntityType.bukkitToMinecraft(type);
            SpawnEggItem nmsItem = SpawnEggItem.byId(nmsType);

            return nmsItem == null ? null : CraftMagicNumbers.getMaterial((Item) nmsItem);
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$Material() {
        int[] aint = CraftItemFactory.$SWITCH_TABLE$org$bukkit$Material;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Material.values().length];

            try {
                aint1[Material.ACACIA_BOAT.ordinal()] = 745;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Material.ACACIA_BUTTON.ordinal()] = 667;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Material.ACACIA_CHEST_BOAT.ordinal()] = 746;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Material.ACACIA_DOOR.ordinal()] = 694;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Material.ACACIA_FENCE.ordinal()] = 294;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[Material.ACACIA_FENCE_GATE.ordinal()] = 717;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[Material.ACACIA_HANGING_SIGN.ordinal()] = 862;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[Material.ACACIA_LEAVES.ordinal()] = 159;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[Material.ACACIA_LOG.ordinal()] = 115;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[Material.ACACIA_PLANKS.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[Material.ACACIA_PRESSURE_PLATE.ordinal()] = 682;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[Material.ACACIA_SAPLING.ordinal()] = 40;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[Material.ACACIA_SIGN.ordinal()] = 851;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[Material.ACACIA_SLAB.ordinal()] = 235;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[Material.ACACIA_STAIRS.ordinal()] = 366;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[Material.ACACIA_TRAPDOOR.ordinal()] = 706;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[Material.ACACIA_WALL_HANGING_SIGN.ordinal()] = 1277;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[Material.ACACIA_WALL_SIGN.ordinal()] = 1268;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[Material.ACACIA_WOOD.ordinal()] = 149;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                aint1[Material.ACTIVATOR_RAIL.ordinal()] = 727;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                aint1[Material.AIR.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                aint1[Material.ALLAY_SPAWN_EGG.ordinal()] = 968;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                aint1[Material.ALLIUM.ordinal()] = 200;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                aint1[Material.AMETHYST_BLOCK.ordinal()] = 73;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                aint1[Material.AMETHYST_CLUSTER.ordinal()] = 1211;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                aint1[Material.AMETHYST_SHARD.ordinal()] = 769;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                aint1[Material.ANCIENT_DEBRIS.ordinal()] = 68;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                aint1[Material.ANDESITE.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                aint1[Material.ANDESITE_SLAB.ordinal()] = 627;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                aint1[Material.ANDESITE_STAIRS.ordinal()] = 610;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                aint1[Material.ANDESITE_WALL.ordinal()] = 386;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                aint1[Material.ANGLER_POTTERY_SHERD.ordinal()] = 1236;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                aint1[Material.ANVIL.ordinal()] = 398;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                aint1[Material.APPLE.ordinal()] = 760;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                aint1[Material.ARCHER_POTTERY_SHERD.ordinal()] = 1237;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                aint1[Material.ARMOR_STAND.ordinal()] = 1078;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                aint1[Material.ARMS_UP_POTTERY_SHERD.ordinal()] = 1238;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                aint1[Material.ARROW.ordinal()] = 762;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                aint1[Material.ATTACHED_MELON_STEM.ordinal()] = 1289;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                aint1[Material.ATTACHED_PUMPKIN_STEM.ordinal()] = 1288;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                aint1[Material.AXOLOTL_BUCKET.ordinal()] = 880;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

            try {
                aint1[Material.AXOLOTL_SPAWN_EGG.ordinal()] = 969;
            } catch (NoSuchFieldError nosuchfielderror41) {
                ;
            }

            try {
                aint1[Material.AZALEA.ordinal()] = 176;
            } catch (NoSuchFieldError nosuchfielderror42) {
                ;
            }

            try {
                aint1[Material.AZALEA_LEAVES.ordinal()] = 163;
            } catch (NoSuchFieldError nosuchfielderror43) {
                ;
            }

            try {
                aint1[Material.AZURE_BLUET.ordinal()] = 201;
            } catch (NoSuchFieldError nosuchfielderror44) {
                ;
            }

            try {
                aint1[Material.BAKED_POTATO.ordinal()] = 1054;
            } catch (NoSuchFieldError nosuchfielderror45) {
                ;
            }

            try {
                aint1[Material.BAMBOO.ordinal()] = 230;
            } catch (NoSuchFieldError nosuchfielderror46) {
                ;
            }

            try {
                aint1[Material.BAMBOO_BLOCK.ordinal()] = 123;
            } catch (NoSuchFieldError nosuchfielderror47) {
                ;
            }

            try {
                aint1[Material.BAMBOO_BUTTON.ordinal()] = 671;
            } catch (NoSuchFieldError nosuchfielderror48) {
                ;
            }

            try {
                aint1[Material.BAMBOO_CHEST_RAFT.ordinal()] = 754;
            } catch (NoSuchFieldError nosuchfielderror49) {
                ;
            }

            try {
                aint1[Material.BAMBOO_DOOR.ordinal()] = 698;
            } catch (NoSuchFieldError nosuchfielderror50) {
                ;
            }

            try {
                aint1[Material.BAMBOO_FENCE.ordinal()] = 298;
            } catch (NoSuchFieldError nosuchfielderror51) {
                ;
            }

            try {
                aint1[Material.BAMBOO_FENCE_GATE.ordinal()] = 721;
            } catch (NoSuchFieldError nosuchfielderror52) {
                ;
            }

            try {
                aint1[Material.BAMBOO_HANGING_SIGN.ordinal()] = 866;
            } catch (NoSuchFieldError nosuchfielderror53) {
                ;
            }

            try {
                aint1[Material.BAMBOO_MOSAIC.ordinal()] = 35;
            } catch (NoSuchFieldError nosuchfielderror54) {
                ;
            }

            try {
                aint1[Material.BAMBOO_MOSAIC_SLAB.ordinal()] = 240;
            } catch (NoSuchFieldError nosuchfielderror55) {
                ;
            }

            try {
                aint1[Material.BAMBOO_MOSAIC_STAIRS.ordinal()] = 371;
            } catch (NoSuchFieldError nosuchfielderror56) {
                ;
            }

            try {
                aint1[Material.BAMBOO_PLANKS.ordinal()] = 32;
            } catch (NoSuchFieldError nosuchfielderror57) {
                ;
            }

            try {
                aint1[Material.BAMBOO_PRESSURE_PLATE.ordinal()] = 686;
            } catch (NoSuchFieldError nosuchfielderror58) {
                ;
            }

            try {
                aint1[Material.BAMBOO_RAFT.ordinal()] = 753;
            } catch (NoSuchFieldError nosuchfielderror59) {
                ;
            }

            try {
                aint1[Material.BAMBOO_SAPLING.ordinal()] = 1366;
            } catch (NoSuchFieldError nosuchfielderror60) {
                ;
            }

            try {
                aint1[Material.BAMBOO_SIGN.ordinal()] = 855;
            } catch (NoSuchFieldError nosuchfielderror61) {
                ;
            }

            try {
                aint1[Material.BAMBOO_SLAB.ordinal()] = 239;
            } catch (NoSuchFieldError nosuchfielderror62) {
                ;
            }

            try {
                aint1[Material.BAMBOO_STAIRS.ordinal()] = 370;
            } catch (NoSuchFieldError nosuchfielderror63) {
                ;
            }

            try {
                aint1[Material.BAMBOO_TRAPDOOR.ordinal()] = 710;
            } catch (NoSuchFieldError nosuchfielderror64) {
                ;
            }

            try {
                aint1[Material.BAMBOO_WALL_HANGING_SIGN.ordinal()] = 1284;
            } catch (NoSuchFieldError nosuchfielderror65) {
                ;
            }

            try {
                aint1[Material.BAMBOO_WALL_SIGN.ordinal()] = 1273;
            } catch (NoSuchFieldError nosuchfielderror66) {
                ;
            }

            try {
                aint1[Material.BARREL.ordinal()] = 1155;
            } catch (NoSuchFieldError nosuchfielderror67) {
                ;
            }

            try {
                aint1[Material.BARRIER.ordinal()] = 422;
            } catch (NoSuchFieldError nosuchfielderror68) {
                ;
            }

            try {
                aint1[Material.BASALT.ordinal()] = 307;
            } catch (NoSuchFieldError nosuchfielderror69) {
                ;
            }

            try {
                aint1[Material.BAT_SPAWN_EGG.ordinal()] = 970;
            } catch (NoSuchFieldError nosuchfielderror70) {
                ;
            }

            try {
                aint1[Material.BEACON.ordinal()] = 375;
            } catch (NoSuchFieldError nosuchfielderror71) {
                ;
            }

            try {
                aint1[Material.BEDROCK.ordinal()] = 44;
            } catch (NoSuchFieldError nosuchfielderror72) {
                ;
            }

            try {
                aint1[Material.BEEF.ordinal()] = 948;
            } catch (NoSuchFieldError nosuchfielderror73) {
                ;
            }

            try {
                aint1[Material.BEEHIVE.ordinal()] = 1173;
            } catch (NoSuchFieldError nosuchfielderror74) {
                ;
            }

            try {
                aint1[Material.BEETROOT.ordinal()] = 1109;
            } catch (NoSuchFieldError nosuchfielderror75) {
                ;
            }

            try {
                aint1[Material.BEETROOTS.ordinal()] = 1352;
            } catch (NoSuchFieldError nosuchfielderror76) {
                ;
            }

            try {
                aint1[Material.BEETROOT_SEEDS.ordinal()] = 1110;
            } catch (NoSuchFieldError nosuchfielderror77) {
                ;
            }

            try {
                aint1[Material.BEETROOT_SOUP.ordinal()] = 1111;
            } catch (NoSuchFieldError nosuchfielderror78) {
                ;
            }

            try {
                aint1[Material.BEE_NEST.ordinal()] = 1172;
            } catch (NoSuchFieldError nosuchfielderror79) {
                ;
            }

            try {
                aint1[Material.BEE_SPAWN_EGG.ordinal()] = 971;
            } catch (NoSuchFieldError nosuchfielderror80) {
                ;
            }

            try {
                aint1[Material.BELL.ordinal()] = 1163;
            } catch (NoSuchFieldError nosuchfielderror81) {
                ;
            }

            try {
                aint1[Material.BIG_DRIPLEAF.ordinal()] = 228;
            } catch (NoSuchFieldError nosuchfielderror82) {
                ;
            }

            try {
                aint1[Material.BIG_DRIPLEAF_STEM.ordinal()] = 1400;
            } catch (NoSuchFieldError nosuchfielderror83) {
                ;
            }

            try {
                aint1[Material.BIRCH_BOAT.ordinal()] = 741;
            } catch (NoSuchFieldError nosuchfielderror84) {
                ;
            }

            try {
                aint1[Material.BIRCH_BUTTON.ordinal()] = 665;
            } catch (NoSuchFieldError nosuchfielderror85) {
                ;
            }

            try {
                aint1[Material.BIRCH_CHEST_BOAT.ordinal()] = 742;
            } catch (NoSuchFieldError nosuchfielderror86) {
                ;
            }

            try {
                aint1[Material.BIRCH_DOOR.ordinal()] = 692;
            } catch (NoSuchFieldError nosuchfielderror87) {
                ;
            }

            try {
                aint1[Material.BIRCH_FENCE.ordinal()] = 292;
            } catch (NoSuchFieldError nosuchfielderror88) {
                ;
            }

            try {
                aint1[Material.BIRCH_FENCE_GATE.ordinal()] = 715;
            } catch (NoSuchFieldError nosuchfielderror89) {
                ;
            }

            try {
                aint1[Material.BIRCH_HANGING_SIGN.ordinal()] = 860;
            } catch (NoSuchFieldError nosuchfielderror90) {
                ;
            }

            try {
                aint1[Material.BIRCH_LEAVES.ordinal()] = 157;
            } catch (NoSuchFieldError nosuchfielderror91) {
                ;
            }

            try {
                aint1[Material.BIRCH_LOG.ordinal()] = 113;
            } catch (NoSuchFieldError nosuchfielderror92) {
                ;
            }

            try {
                aint1[Material.BIRCH_PLANKS.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror93) {
                ;
            }

            try {
                aint1[Material.BIRCH_PRESSURE_PLATE.ordinal()] = 680;
            } catch (NoSuchFieldError nosuchfielderror94) {
                ;
            }

            try {
                aint1[Material.BIRCH_SAPLING.ordinal()] = 38;
            } catch (NoSuchFieldError nosuchfielderror95) {
                ;
            }

            try {
                aint1[Material.BIRCH_SIGN.ordinal()] = 849;
            } catch (NoSuchFieldError nosuchfielderror96) {
                ;
            }

            try {
                aint1[Material.BIRCH_SLAB.ordinal()] = 233;
            } catch (NoSuchFieldError nosuchfielderror97) {
                ;
            }

            try {
                aint1[Material.BIRCH_STAIRS.ordinal()] = 364;
            } catch (NoSuchFieldError nosuchfielderror98) {
                ;
            }

            try {
                aint1[Material.BIRCH_TRAPDOOR.ordinal()] = 704;
            } catch (NoSuchFieldError nosuchfielderror99) {
                ;
            }

            try {
                aint1[Material.BIRCH_WALL_HANGING_SIGN.ordinal()] = 1276;
            } catch (NoSuchFieldError nosuchfielderror100) {
                ;
            }

            try {
                aint1[Material.BIRCH_WALL_SIGN.ordinal()] = 1267;
            } catch (NoSuchFieldError nosuchfielderror101) {
                ;
            }

            try {
                aint1[Material.BIRCH_WOOD.ordinal()] = 147;
            } catch (NoSuchFieldError nosuchfielderror102) {
                ;
            }

            try {
                aint1[Material.BLACKSTONE.ordinal()] = 1178;
            } catch (NoSuchFieldError nosuchfielderror103) {
                ;
            }

            try {
                aint1[Material.BLACKSTONE_SLAB.ordinal()] = 1179;
            } catch (NoSuchFieldError nosuchfielderror104) {
                ;
            }

            try {
                aint1[Material.BLACKSTONE_STAIRS.ordinal()] = 1180;
            } catch (NoSuchFieldError nosuchfielderror105) {
                ;
            }

            try {
                aint1[Material.BLACKSTONE_WALL.ordinal()] = 391;
            } catch (NoSuchFieldError nosuchfielderror106) {
                ;
            }

            try {
                aint1[Material.BLACK_BANNER.ordinal()] = 1103;
            } catch (NoSuchFieldError nosuchfielderror107) {
                ;
            }

            try {
                aint1[Material.BLACK_BED.ordinal()] = 940;
            } catch (NoSuchFieldError nosuchfielderror108) {
                ;
            }

            try {
                aint1[Material.BLACK_CANDLE.ordinal()] = 1207;
            } catch (NoSuchFieldError nosuchfielderror109) {
                ;
            }

            try {
                aint1[Material.BLACK_CANDLE_CAKE.ordinal()] = 1396;
            } catch (NoSuchFieldError nosuchfielderror110) {
                ;
            }

            try {
                aint1[Material.BLACK_CARPET.ordinal()] = 440;
            } catch (NoSuchFieldError nosuchfielderror111) {
                ;
            }

            try {
                aint1[Material.BLACK_CONCRETE.ordinal()] = 549;
            } catch (NoSuchFieldError nosuchfielderror112) {
                ;
            }

            try {
                aint1[Material.BLACK_CONCRETE_POWDER.ordinal()] = 565;
            } catch (NoSuchFieldError nosuchfielderror113) {
                ;
            }

            try {
                aint1[Material.BLACK_DYE.ordinal()] = 920;
            } catch (NoSuchFieldError nosuchfielderror114) {
                ;
            }

            try {
                aint1[Material.BLACK_GLAZED_TERRACOTTA.ordinal()] = 533;
            } catch (NoSuchFieldError nosuchfielderror115) {
                ;
            }

            try {
                aint1[Material.BLACK_SHULKER_BOX.ordinal()] = 517;
            } catch (NoSuchFieldError nosuchfielderror116) {
                ;
            }

            try {
                aint1[Material.BLACK_STAINED_GLASS.ordinal()] = 465;
            } catch (NoSuchFieldError nosuchfielderror117) {
                ;
            }

            try {
                aint1[Material.BLACK_STAINED_GLASS_PANE.ordinal()] = 481;
            } catch (NoSuchFieldError nosuchfielderror118) {
                ;
            }

            try {
                aint1[Material.BLACK_TERRACOTTA.ordinal()] = 421;
            } catch (NoSuchFieldError nosuchfielderror119) {
                ;
            }

            try {
                aint1[Material.BLACK_WALL_BANNER.ordinal()] = 1349;
            } catch (NoSuchFieldError nosuchfielderror120) {
                ;
            }

            try {
                aint1[Material.BLACK_WOOL.ordinal()] = 196;
            } catch (NoSuchFieldError nosuchfielderror121) {
                ;
            }

            try {
                aint1[Material.BLADE_POTTERY_SHERD.ordinal()] = 1239;
            } catch (NoSuchFieldError nosuchfielderror122) {
                ;
            }

            try {
                aint1[Material.BLAST_FURNACE.ordinal()] = 1157;
            } catch (NoSuchFieldError nosuchfielderror123) {
                ;
            }

            try {
                aint1[Material.BLAZE_POWDER.ordinal()] = 962;
            } catch (NoSuchFieldError nosuchfielderror124) {
                ;
            }

            try {
                aint1[Material.BLAZE_ROD.ordinal()] = 954;
            } catch (NoSuchFieldError nosuchfielderror125) {
                ;
            }

            try {
                aint1[Material.BLAZE_SPAWN_EGG.ordinal()] = 972;
            } catch (NoSuchFieldError nosuchfielderror126) {
                ;
            }

            try {
                aint1[Material.BLUE_BANNER.ordinal()] = 1099;
            } catch (NoSuchFieldError nosuchfielderror127) {
                ;
            }

            try {
                aint1[Material.BLUE_BED.ordinal()] = 936;
            } catch (NoSuchFieldError nosuchfielderror128) {
                ;
            }

            try {
                aint1[Material.BLUE_CANDLE.ordinal()] = 1203;
            } catch (NoSuchFieldError nosuchfielderror129) {
                ;
            }

            try {
                aint1[Material.BLUE_CANDLE_CAKE.ordinal()] = 1392;
            } catch (NoSuchFieldError nosuchfielderror130) {
                ;
            }

            try {
                aint1[Material.BLUE_CARPET.ordinal()] = 436;
            } catch (NoSuchFieldError nosuchfielderror131) {
                ;
            }

            try {
                aint1[Material.BLUE_CONCRETE.ordinal()] = 545;
            } catch (NoSuchFieldError nosuchfielderror132) {
                ;
            }

            try {
                aint1[Material.BLUE_CONCRETE_POWDER.ordinal()] = 561;
            } catch (NoSuchFieldError nosuchfielderror133) {
                ;
            }

            try {
                aint1[Material.BLUE_DYE.ordinal()] = 916;
            } catch (NoSuchFieldError nosuchfielderror134) {
                ;
            }

            try {
                aint1[Material.BLUE_GLAZED_TERRACOTTA.ordinal()] = 529;
            } catch (NoSuchFieldError nosuchfielderror135) {
                ;
            }

            try {
                aint1[Material.BLUE_ICE.ordinal()] = 598;
            } catch (NoSuchFieldError nosuchfielderror136) {
                ;
            }

            try {
                aint1[Material.BLUE_ORCHID.ordinal()] = 199;
            } catch (NoSuchFieldError nosuchfielderror137) {
                ;
            }

            try {
                aint1[Material.BLUE_SHULKER_BOX.ordinal()] = 513;
            } catch (NoSuchFieldError nosuchfielderror138) {
                ;
            }

            try {
                aint1[Material.BLUE_STAINED_GLASS.ordinal()] = 461;
            } catch (NoSuchFieldError nosuchfielderror139) {
                ;
            }

            try {
                aint1[Material.BLUE_STAINED_GLASS_PANE.ordinal()] = 477;
            } catch (NoSuchFieldError nosuchfielderror140) {
                ;
            }

            try {
                aint1[Material.BLUE_TERRACOTTA.ordinal()] = 417;
            } catch (NoSuchFieldError nosuchfielderror141) {
                ;
            }

            try {
                aint1[Material.BLUE_WALL_BANNER.ordinal()] = 1345;
            } catch (NoSuchFieldError nosuchfielderror142) {
                ;
            }

            try {
                aint1[Material.BLUE_WOOL.ordinal()] = 192;
            } catch (NoSuchFieldError nosuchfielderror143) {
                ;
            }

            try {
                aint1[Material.BONE.ordinal()] = 922;
            } catch (NoSuchFieldError nosuchfielderror144) {
                ;
            }

            try {
                aint1[Material.BONE_BLOCK.ordinal()] = 499;
            } catch (NoSuchFieldError nosuchfielderror145) {
                ;
            }

            try {
                aint1[Material.BONE_MEAL.ordinal()] = 921;
            } catch (NoSuchFieldError nosuchfielderror146) {
                ;
            }

            try {
                aint1[Material.BOOK.ordinal()] = 886;
            } catch (NoSuchFieldError nosuchfielderror147) {
                ;
            }

            try {
                aint1[Material.BOOKSHELF.ordinal()] = 265;
            } catch (NoSuchFieldError nosuchfielderror148) {
                ;
            }

            try {
                aint1[Material.BOW.ordinal()] = 761;
            } catch (NoSuchFieldError nosuchfielderror149) {
                ;
            }

            try {
                aint1[Material.BOWL.ordinal()] = 809;
            } catch (NoSuchFieldError nosuchfielderror150) {
                ;
            }

            try {
                aint1[Material.BRAIN_CORAL.ordinal()] = 579;
            } catch (NoSuchFieldError nosuchfielderror151) {
                ;
            }

            try {
                aint1[Material.BRAIN_CORAL_BLOCK.ordinal()] = 574;
            } catch (NoSuchFieldError nosuchfielderror152) {
                ;
            }

            try {
                aint1[Material.BRAIN_CORAL_FAN.ordinal()] = 589;
            } catch (NoSuchFieldError nosuchfielderror153) {
                ;
            }

            try {
                aint1[Material.BRAIN_CORAL_WALL_FAN.ordinal()] = 1362;
            } catch (NoSuchFieldError nosuchfielderror154) {
                ;
            }

            try {
                aint1[Material.BREAD.ordinal()] = 816;
            } catch (NoSuchFieldError nosuchfielderror155) {
                ;
            }

            try {
                aint1[Material.BREWER_POTTERY_SHERD.ordinal()] = 1240;
            } catch (NoSuchFieldError nosuchfielderror156) {
                ;
            }

            try {
                aint1[Material.BREWING_STAND.ordinal()] = 964;
            } catch (NoSuchFieldError nosuchfielderror157) {
                ;
            }

            try {
                aint1[Material.BRICK.ordinal()] = 882;
            } catch (NoSuchFieldError nosuchfielderror158) {
                ;
            }

            try {
                aint1[Material.BRICKS.ordinal()] = 264;
            } catch (NoSuchFieldError nosuchfielderror159) {
                ;
            }

            try {
                aint1[Material.BRICK_SLAB.ordinal()] = 249;
            } catch (NoSuchFieldError nosuchfielderror160) {
                ;
            }

            try {
                aint1[Material.BRICK_STAIRS.ordinal()] = 340;
            } catch (NoSuchFieldError nosuchfielderror161) {
                ;
            }

            try {
                aint1[Material.BRICK_WALL.ordinal()] = 378;
            } catch (NoSuchFieldError nosuchfielderror162) {
                ;
            }

            try {
                aint1[Material.BROWN_BANNER.ordinal()] = 1100;
            } catch (NoSuchFieldError nosuchfielderror163) {
                ;
            }

            try {
                aint1[Material.BROWN_BED.ordinal()] = 937;
            } catch (NoSuchFieldError nosuchfielderror164) {
                ;
            }

            try {
                aint1[Material.BROWN_CANDLE.ordinal()] = 1204;
            } catch (NoSuchFieldError nosuchfielderror165) {
                ;
            }

            try {
                aint1[Material.BROWN_CANDLE_CAKE.ordinal()] = 1393;
            } catch (NoSuchFieldError nosuchfielderror166) {
                ;
            }

            try {
                aint1[Material.BROWN_CARPET.ordinal()] = 437;
            } catch (NoSuchFieldError nosuchfielderror167) {
                ;
            }

            try {
                aint1[Material.BROWN_CONCRETE.ordinal()] = 546;
            } catch (NoSuchFieldError nosuchfielderror168) {
                ;
            }

            try {
                aint1[Material.BROWN_CONCRETE_POWDER.ordinal()] = 562;
            } catch (NoSuchFieldError nosuchfielderror169) {
                ;
            }

            try {
                aint1[Material.BROWN_DYE.ordinal()] = 917;
            } catch (NoSuchFieldError nosuchfielderror170) {
                ;
            }

            try {
                aint1[Material.BROWN_GLAZED_TERRACOTTA.ordinal()] = 530;
            } catch (NoSuchFieldError nosuchfielderror171) {
                ;
            }

            try {
                aint1[Material.BROWN_MUSHROOM.ordinal()] = 213;
            } catch (NoSuchFieldError nosuchfielderror172) {
                ;
            }

            try {
                aint1[Material.BROWN_MUSHROOM_BLOCK.ordinal()] = 331;
            } catch (NoSuchFieldError nosuchfielderror173) {
                ;
            }

            try {
                aint1[Material.BROWN_SHULKER_BOX.ordinal()] = 514;
            } catch (NoSuchFieldError nosuchfielderror174) {
                ;
            }

            try {
                aint1[Material.BROWN_STAINED_GLASS.ordinal()] = 462;
            } catch (NoSuchFieldError nosuchfielderror175) {
                ;
            }

            try {
                aint1[Material.BROWN_STAINED_GLASS_PANE.ordinal()] = 478;
            } catch (NoSuchFieldError nosuchfielderror176) {
                ;
            }

            try {
                aint1[Material.BROWN_TERRACOTTA.ordinal()] = 418;
            } catch (NoSuchFieldError nosuchfielderror177) {
                ;
            }

            try {
                aint1[Material.BROWN_WALL_BANNER.ordinal()] = 1346;
            } catch (NoSuchFieldError nosuchfielderror178) {
                ;
            }

            try {
                aint1[Material.BROWN_WOOL.ordinal()] = 193;
            } catch (NoSuchFieldError nosuchfielderror179) {
                ;
            }

            try {
                aint1[Material.BRUSH.ordinal()] = 1218;
            } catch (NoSuchFieldError nosuchfielderror180) {
                ;
            }

            try {
                aint1[Material.BUBBLE_COLUMN.ordinal()] = 1370;
            } catch (NoSuchFieldError nosuchfielderror181) {
                ;
            }

            try {
                aint1[Material.BUBBLE_CORAL.ordinal()] = 580;
            } catch (NoSuchFieldError nosuchfielderror182) {
                ;
            }

            try {
                aint1[Material.BUBBLE_CORAL_BLOCK.ordinal()] = 575;
            } catch (NoSuchFieldError nosuchfielderror183) {
                ;
            }

            try {
                aint1[Material.BUBBLE_CORAL_FAN.ordinal()] = 590;
            } catch (NoSuchFieldError nosuchfielderror184) {
                ;
            }

            try {
                aint1[Material.BUBBLE_CORAL_WALL_FAN.ordinal()] = 1363;
            } catch (NoSuchFieldError nosuchfielderror185) {
                ;
            }

            try {
                aint1[Material.BUCKET.ordinal()] = 869;
            } catch (NoSuchFieldError nosuchfielderror186) {
                ;
            }

            try {
                aint1[Material.BUDDING_AMETHYST.ordinal()] = 74;
            } catch (NoSuchFieldError nosuchfielderror187) {
                ;
            }

            try {
                aint1[Material.BUNDLE.ordinal()] = 891;
            } catch (NoSuchFieldError nosuchfielderror188) {
                ;
            }

            try {
                aint1[Material.BURN_POTTERY_SHERD.ordinal()] = 1241;
            } catch (NoSuchFieldError nosuchfielderror189) {
                ;
            }

            try {
                aint1[Material.CACTUS.ordinal()] = 287;
            } catch (NoSuchFieldError nosuchfielderror190) {
                ;
            }

            try {
                aint1[Material.CAKE.ordinal()] = 924;
            } catch (NoSuchFieldError nosuchfielderror191) {
                ;
            }

            try {
                aint1[Material.CALCITE.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror192) {
                ;
            }

            try {
                aint1[Material.CALIBRATED_SCULK_SENSOR.ordinal()] = 655;
            } catch (NoSuchFieldError nosuchfielderror193) {
                ;
            }

            try {
                aint1[Material.CAMEL_SPAWN_EGG.ordinal()] = 974;
            } catch (NoSuchFieldError nosuchfielderror194) {
                ;
            }

            try {
                aint1[Material.CAMPFIRE.ordinal()] = 1168;
            } catch (NoSuchFieldError nosuchfielderror195) {
                ;
            }

            try {
                aint1[Material.CANDLE.ordinal()] = 1191;
            } catch (NoSuchFieldError nosuchfielderror196) {
                ;
            }

            try {
                aint1[Material.CANDLE_CAKE.ordinal()] = 1380;
            } catch (NoSuchFieldError nosuchfielderror197) {
                ;
            }

            try {
                aint1[Material.CARROT.ordinal()] = 1052;
            } catch (NoSuchFieldError nosuchfielderror198) {
                ;
            }

            try {
                aint1[Material.CARROTS.ordinal()] = 1325;
            } catch (NoSuchFieldError nosuchfielderror199) {
                ;
            }

            try {
                aint1[Material.CARROT_ON_A_STICK.ordinal()] = 734;
            } catch (NoSuchFieldError nosuchfielderror200) {
                ;
            }

            try {
                aint1[Material.CARTOGRAPHY_TABLE.ordinal()] = 1158;
            } catch (NoSuchFieldError nosuchfielderror201) {
                ;
            }

            try {
                aint1[Material.CARVED_PUMPKIN.ordinal()] = 302;
            } catch (NoSuchFieldError nosuchfielderror202) {
                ;
            }

            try {
                aint1[Material.CAT_SPAWN_EGG.ordinal()] = 973;
            } catch (NoSuchFieldError nosuchfielderror203) {
                ;
            }

            try {
                aint1[Material.CAULDRON.ordinal()] = 965;
            } catch (NoSuchFieldError nosuchfielderror204) {
                ;
            }

            try {
                aint1[Material.CAVE_AIR.ordinal()] = 1369;
            } catch (NoSuchFieldError nosuchfielderror205) {
                ;
            }

            try {
                aint1[Material.CAVE_SPIDER_SPAWN_EGG.ordinal()] = 975;
            } catch (NoSuchFieldError nosuchfielderror206) {
                ;
            }

            try {
                aint1[Material.CAVE_VINES.ordinal()] = 1398;
            } catch (NoSuchFieldError nosuchfielderror207) {
                ;
            }

            try {
                aint1[Material.CAVE_VINES_PLANT.ordinal()] = 1399;
            } catch (NoSuchFieldError nosuchfielderror208) {
                ;
            }

            try {
                aint1[Material.CHAIN.ordinal()] = 335;
            } catch (NoSuchFieldError nosuchfielderror209) {
                ;
            }

            try {
                aint1[Material.CHAINMAIL_BOOTS.ordinal()] = 824;
            } catch (NoSuchFieldError nosuchfielderror210) {
                ;
            }

            try {
                aint1[Material.CHAINMAIL_CHESTPLATE.ordinal()] = 822;
            } catch (NoSuchFieldError nosuchfielderror211) {
                ;
            }

            try {
                aint1[Material.CHAINMAIL_HELMET.ordinal()] = 821;
            } catch (NoSuchFieldError nosuchfielderror212) {
                ;
            }

            try {
                aint1[Material.CHAINMAIL_LEGGINGS.ordinal()] = 823;
            } catch (NoSuchFieldError nosuchfielderror213) {
                ;
            }

            try {
                aint1[Material.CHAIN_COMMAND_BLOCK.ordinal()] = 494;
            } catch (NoSuchFieldError nosuchfielderror214) {
                ;
            }

            try {
                aint1[Material.CHARCOAL.ordinal()] = 764;
            } catch (NoSuchFieldError nosuchfielderror215) {
                ;
            }

            try {
                aint1[Material.CHERRY_BOAT.ordinal()] = 747;
            } catch (NoSuchFieldError nosuchfielderror216) {
                ;
            }

            try {
                aint1[Material.CHERRY_BUTTON.ordinal()] = 668;
            } catch (NoSuchFieldError nosuchfielderror217) {
                ;
            }

            try {
                aint1[Material.CHERRY_CHEST_BOAT.ordinal()] = 748;
            } catch (NoSuchFieldError nosuchfielderror218) {
                ;
            }

            try {
                aint1[Material.CHERRY_DOOR.ordinal()] = 695;
            } catch (NoSuchFieldError nosuchfielderror219) {
                ;
            }

            try {
                aint1[Material.CHERRY_FENCE.ordinal()] = 295;
            } catch (NoSuchFieldError nosuchfielderror220) {
                ;
            }

            try {
                aint1[Material.CHERRY_FENCE_GATE.ordinal()] = 718;
            } catch (NoSuchFieldError nosuchfielderror221) {
                ;
            }

            try {
                aint1[Material.CHERRY_HANGING_SIGN.ordinal()] = 863;
            } catch (NoSuchFieldError nosuchfielderror222) {
                ;
            }

            try {
                aint1[Material.CHERRY_LEAVES.ordinal()] = 160;
            } catch (NoSuchFieldError nosuchfielderror223) {
                ;
            }

            try {
                aint1[Material.CHERRY_LOG.ordinal()] = 116;
            } catch (NoSuchFieldError nosuchfielderror224) {
                ;
            }

            try {
                aint1[Material.CHERRY_PLANKS.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror225) {
                ;
            }

            try {
                aint1[Material.CHERRY_PRESSURE_PLATE.ordinal()] = 683;
            } catch (NoSuchFieldError nosuchfielderror226) {
                ;
            }

            try {
                aint1[Material.CHERRY_SAPLING.ordinal()] = 41;
            } catch (NoSuchFieldError nosuchfielderror227) {
                ;
            }

            try {
                aint1[Material.CHERRY_SIGN.ordinal()] = 852;
            } catch (NoSuchFieldError nosuchfielderror228) {
                ;
            }

            try {
                aint1[Material.CHERRY_SLAB.ordinal()] = 236;
            } catch (NoSuchFieldError nosuchfielderror229) {
                ;
            }

            try {
                aint1[Material.CHERRY_STAIRS.ordinal()] = 367;
            } catch (NoSuchFieldError nosuchfielderror230) {
                ;
            }

            try {
                aint1[Material.CHERRY_TRAPDOOR.ordinal()] = 707;
            } catch (NoSuchFieldError nosuchfielderror231) {
                ;
            }

            try {
                aint1[Material.CHERRY_WALL_HANGING_SIGN.ordinal()] = 1278;
            } catch (NoSuchFieldError nosuchfielderror232) {
                ;
            }

            try {
                aint1[Material.CHERRY_WALL_SIGN.ordinal()] = 1269;
            } catch (NoSuchFieldError nosuchfielderror233) {
                ;
            }

            try {
                aint1[Material.CHERRY_WOOD.ordinal()] = 150;
            } catch (NoSuchFieldError nosuchfielderror234) {
                ;
            }

            try {
                aint1[Material.CHEST.ordinal()] = 278;
            } catch (NoSuchFieldError nosuchfielderror235) {
                ;
            }

            try {
                aint1[Material.CHEST_MINECART.ordinal()] = 730;
            } catch (NoSuchFieldError nosuchfielderror236) {
                ;
            }

            try {
                aint1[Material.CHICKEN.ordinal()] = 950;
            } catch (NoSuchFieldError nosuchfielderror237) {
                ;
            }

            try {
                aint1[Material.CHICKEN_SPAWN_EGG.ordinal()] = 976;
            } catch (NoSuchFieldError nosuchfielderror238) {
                ;
            }

            try {
                aint1[Material.CHIPPED_ANVIL.ordinal()] = 399;
            } catch (NoSuchFieldError nosuchfielderror239) {
                ;
            }

            try {
                aint1[Material.CHISELED_BOOKSHELF.ordinal()] = 266;
            } catch (NoSuchFieldError nosuchfielderror240) {
                ;
            }

            try {
                aint1[Material.CHISELED_DEEPSLATE.ordinal()] = 329;
            } catch (NoSuchFieldError nosuchfielderror241) {
                ;
            }

            try {
                aint1[Material.CHISELED_NETHER_BRICKS.ordinal()] = 347;
            } catch (NoSuchFieldError nosuchfielderror242) {
                ;
            }

            try {
                aint1[Material.CHISELED_POLISHED_BLACKSTONE.ordinal()] = 1185;
            } catch (NoSuchFieldError nosuchfielderror243) {
                ;
            }

            try {
                aint1[Material.CHISELED_QUARTZ_BLOCK.ordinal()] = 401;
            } catch (NoSuchFieldError nosuchfielderror244) {
                ;
            }

            try {
                aint1[Material.CHISELED_RED_SANDSTONE.ordinal()] = 490;
            } catch (NoSuchFieldError nosuchfielderror245) {
                ;
            }

            try {
                aint1[Material.CHISELED_SANDSTONE.ordinal()] = 171;
            } catch (NoSuchFieldError nosuchfielderror246) {
                ;
            }

            try {
                aint1[Material.CHISELED_STONE_BRICKS.ordinal()] = 322;
            } catch (NoSuchFieldError nosuchfielderror247) {
                ;
            }

            try {
                aint1[Material.CHORUS_FLOWER.ordinal()] = 273;
            } catch (NoSuchFieldError nosuchfielderror248) {
                ;
            }

            try {
                aint1[Material.CHORUS_FRUIT.ordinal()] = 1105;
            } catch (NoSuchFieldError nosuchfielderror249) {
                ;
            }

            try {
                aint1[Material.CHORUS_PLANT.ordinal()] = 272;
            } catch (NoSuchFieldError nosuchfielderror250) {
                ;
            }

            try {
                aint1[Material.CLAY.ordinal()] = 288;
            } catch (NoSuchFieldError nosuchfielderror251) {
                ;
            }

            try {
                aint1[Material.CLAY_BALL.ordinal()] = 883;
            } catch (NoSuchFieldError nosuchfielderror252) {
                ;
            }

            try {
                aint1[Material.CLOCK.ordinal()] = 893;
            } catch (NoSuchFieldError nosuchfielderror253) {
                ;
            }

            try {
                aint1[Material.COAL.ordinal()] = 763;
            } catch (NoSuchFieldError nosuchfielderror254) {
                ;
            }

            try {
                aint1[Material.COAL_BLOCK.ordinal()] = 69;
            } catch (NoSuchFieldError nosuchfielderror255) {
                ;
            }

            try {
                aint1[Material.COAL_ORE.ordinal()] = 50;
            } catch (NoSuchFieldError nosuchfielderror256) {
                ;
            }

            try {
                aint1[Material.COARSE_DIRT.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror257) {
                ;
            }

            try {
                aint1[Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1222;
            } catch (NoSuchFieldError nosuchfielderror258) {
                ;
            }

            try {
                aint1[Material.COBBLED_DEEPSLATE.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror259) {
                ;
            }

            try {
                aint1[Material.COBBLED_DEEPSLATE_SLAB.ordinal()] = 631;
            } catch (NoSuchFieldError nosuchfielderror260) {
                ;
            }

            try {
                aint1[Material.COBBLED_DEEPSLATE_STAIRS.ordinal()] = 614;
            } catch (NoSuchFieldError nosuchfielderror261) {
                ;
            }

            try {
                aint1[Material.COBBLED_DEEPSLATE_WALL.ordinal()] = 394;
            } catch (NoSuchFieldError nosuchfielderror262) {
                ;
            }

            try {
                aint1[Material.COBBLESTONE.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror263) {
                ;
            }

            try {
                aint1[Material.COBBLESTONE_SLAB.ordinal()] = 248;
            } catch (NoSuchFieldError nosuchfielderror264) {
                ;
            }

            try {
                aint1[Material.COBBLESTONE_STAIRS.ordinal()] = 283;
            } catch (NoSuchFieldError nosuchfielderror265) {
                ;
            }

            try {
                aint1[Material.COBBLESTONE_WALL.ordinal()] = 376;
            } catch (NoSuchFieldError nosuchfielderror266) {
                ;
            }

            try {
                aint1[Material.COBWEB.ordinal()] = 173;
            } catch (NoSuchFieldError nosuchfielderror267) {
                ;
            }

            try {
                aint1[Material.COCOA.ordinal()] = 1296;
            } catch (NoSuchFieldError nosuchfielderror268) {
                ;
            }

            try {
                aint1[Material.COCOA_BEANS.ordinal()] = 904;
            } catch (NoSuchFieldError nosuchfielderror269) {
                ;
            }

            try {
                aint1[Material.COD.ordinal()] = 896;
            } catch (NoSuchFieldError nosuchfielderror270) {
                ;
            }

            try {
                aint1[Material.COD_BUCKET.ordinal()] = 878;
            } catch (NoSuchFieldError nosuchfielderror271) {
                ;
            }

            try {
                aint1[Material.COD_SPAWN_EGG.ordinal()] = 977;
            } catch (NoSuchFieldError nosuchfielderror272) {
                ;
            }

            try {
                aint1[Material.COMMAND_BLOCK.ordinal()] = 374;
            } catch (NoSuchFieldError nosuchfielderror273) {
                ;
            }

            try {
                aint1[Material.COMMAND_BLOCK_MINECART.ordinal()] = 1085;
            } catch (NoSuchFieldError nosuchfielderror274) {
                ;
            }

            try {
                aint1[Material.COMPARATOR.ordinal()] = 640;
            } catch (NoSuchFieldError nosuchfielderror275) {
                ;
            }

            try {
                aint1[Material.COMPASS.ordinal()] = 889;
            } catch (NoSuchFieldError nosuchfielderror276) {
                ;
            }

            try {
                aint1[Material.COMPOSTER.ordinal()] = 1154;
            } catch (NoSuchFieldError nosuchfielderror277) {
                ;
            }

            try {
                aint1[Material.CONDUIT.ordinal()] = 599;
            } catch (NoSuchFieldError nosuchfielderror278) {
                ;
            }

            try {
                aint1[Material.COOKED_BEEF.ordinal()] = 949;
            } catch (NoSuchFieldError nosuchfielderror279) {
                ;
            }

            try {
                aint1[Material.COOKED_CHICKEN.ordinal()] = 951;
            } catch (NoSuchFieldError nosuchfielderror280) {
                ;
            }

            try {
                aint1[Material.COOKED_COD.ordinal()] = 900;
            } catch (NoSuchFieldError nosuchfielderror281) {
                ;
            }

            try {
                aint1[Material.COOKED_MUTTON.ordinal()] = 1087;
            } catch (NoSuchFieldError nosuchfielderror282) {
                ;
            }

            try {
                aint1[Material.COOKED_PORKCHOP.ordinal()] = 843;
            } catch (NoSuchFieldError nosuchfielderror283) {
                ;
            }

            try {
                aint1[Material.COOKED_RABBIT.ordinal()] = 1074;
            } catch (NoSuchFieldError nosuchfielderror284) {
                ;
            }

            try {
                aint1[Material.COOKED_SALMON.ordinal()] = 901;
            } catch (NoSuchFieldError nosuchfielderror285) {
                ;
            }

            try {
                aint1[Material.COOKIE.ordinal()] = 941;
            } catch (NoSuchFieldError nosuchfielderror286) {
                ;
            }

            try {
                aint1[Material.COPPER_BLOCK.ordinal()] = 76;
            } catch (NoSuchFieldError nosuchfielderror287) {
                ;
            }

            try {
                aint1[Material.COPPER_INGOT.ordinal()] = 773;
            } catch (NoSuchFieldError nosuchfielderror288) {
                ;
            }

            try {
                aint1[Material.COPPER_ORE.ordinal()] = 54;
            } catch (NoSuchFieldError nosuchfielderror289) {
                ;
            }

            try {
                aint1[Material.CORNFLOWER.ordinal()] = 207;
            } catch (NoSuchFieldError nosuchfielderror290) {
                ;
            }

            try {
                aint1[Material.COW_SPAWN_EGG.ordinal()] = 978;
            } catch (NoSuchFieldError nosuchfielderror291) {
                ;
            }

            try {
                aint1[Material.CRACKED_DEEPSLATE_BRICKS.ordinal()] = 326;
            } catch (NoSuchFieldError nosuchfielderror292) {
                ;
            }

            try {
                aint1[Material.CRACKED_DEEPSLATE_TILES.ordinal()] = 328;
            } catch (NoSuchFieldError nosuchfielderror293) {
                ;
            }

            try {
                aint1[Material.CRACKED_NETHER_BRICKS.ordinal()] = 346;
            } catch (NoSuchFieldError nosuchfielderror294) {
                ;
            }

            try {
                aint1[Material.CRACKED_POLISHED_BLACKSTONE_BRICKS.ordinal()] = 1189;
            } catch (NoSuchFieldError nosuchfielderror295) {
                ;
            }

            try {
                aint1[Material.CRACKED_STONE_BRICKS.ordinal()] = 321;
            } catch (NoSuchFieldError nosuchfielderror296) {
                ;
            }

            try {
                aint1[Material.CRAFTING_TABLE.ordinal()] = 279;
            } catch (NoSuchFieldError nosuchfielderror297) {
                ;
            }

            try {
                aint1[Material.CREEPER_BANNER_PATTERN.ordinal()] = 1148;
            } catch (NoSuchFieldError nosuchfielderror298) {
                ;
            }

            try {
                aint1[Material.CREEPER_HEAD.ordinal()] = 1062;
            } catch (NoSuchFieldError nosuchfielderror299) {
                ;
            }

            try {
                aint1[Material.CREEPER_SPAWN_EGG.ordinal()] = 979;
            } catch (NoSuchFieldError nosuchfielderror300) {
                ;
            }

            try {
                aint1[Material.CREEPER_WALL_HEAD.ordinal()] = 1331;
            } catch (NoSuchFieldError nosuchfielderror301) {
                ;
            }

            try {
                aint1[Material.CRIMSON_BUTTON.ordinal()] = 672;
            } catch (NoSuchFieldError nosuchfielderror302) {
                ;
            }

            try {
                aint1[Material.CRIMSON_DOOR.ordinal()] = 699;
            } catch (NoSuchFieldError nosuchfielderror303) {
                ;
            }

            try {
                aint1[Material.CRIMSON_FENCE.ordinal()] = 299;
            } catch (NoSuchFieldError nosuchfielderror304) {
                ;
            }

            try {
                aint1[Material.CRIMSON_FENCE_GATE.ordinal()] = 722;
            } catch (NoSuchFieldError nosuchfielderror305) {
                ;
            }

            try {
                aint1[Material.CRIMSON_FUNGUS.ordinal()] = 215;
            } catch (NoSuchFieldError nosuchfielderror306) {
                ;
            }

            try {
                aint1[Material.CRIMSON_HANGING_SIGN.ordinal()] = 867;
            } catch (NoSuchFieldError nosuchfielderror307) {
                ;
            }

            try {
                aint1[Material.CRIMSON_HYPHAE.ordinal()] = 153;
            } catch (NoSuchFieldError nosuchfielderror308) {
                ;
            }

            try {
                aint1[Material.CRIMSON_NYLIUM.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror309) {
                ;
            }

            try {
                aint1[Material.CRIMSON_PLANKS.ordinal()] = 33;
            } catch (NoSuchFieldError nosuchfielderror310) {
                ;
            }

            try {
                aint1[Material.CRIMSON_PRESSURE_PLATE.ordinal()] = 687;
            } catch (NoSuchFieldError nosuchfielderror311) {
                ;
            }

            try {
                aint1[Material.CRIMSON_ROOTS.ordinal()] = 217;
            } catch (NoSuchFieldError nosuchfielderror312) {
                ;
            }

            try {
                aint1[Material.CRIMSON_SIGN.ordinal()] = 856;
            } catch (NoSuchFieldError nosuchfielderror313) {
                ;
            }

            try {
                aint1[Material.CRIMSON_SLAB.ordinal()] = 241;
            } catch (NoSuchFieldError nosuchfielderror314) {
                ;
            }

            try {
                aint1[Material.CRIMSON_STAIRS.ordinal()] = 372;
            } catch (NoSuchFieldError nosuchfielderror315) {
                ;
            }

            try {
                aint1[Material.CRIMSON_STEM.ordinal()] = 121;
            } catch (NoSuchFieldError nosuchfielderror316) {
                ;
            }

            try {
                aint1[Material.CRIMSON_TRAPDOOR.ordinal()] = 711;
            } catch (NoSuchFieldError nosuchfielderror317) {
                ;
            }

            try {
                aint1[Material.CRIMSON_WALL_HANGING_SIGN.ordinal()] = 1282;
            } catch (NoSuchFieldError nosuchfielderror318) {
                ;
            }

            try {
                aint1[Material.CRIMSON_WALL_SIGN.ordinal()] = 1374;
            } catch (NoSuchFieldError nosuchfielderror319) {
                ;
            }

            try {
                aint1[Material.CROSSBOW.ordinal()] = 1144;
            } catch (NoSuchFieldError nosuchfielderror320) {
                ;
            }

            try {
                aint1[Material.CRYING_OBSIDIAN.ordinal()] = 1177;
            } catch (NoSuchFieldError nosuchfielderror321) {
                ;
            }

            try {
                aint1[Material.CUT_COPPER.ordinal()] = 83;
            } catch (NoSuchFieldError nosuchfielderror322) {
                ;
            }

            try {
                aint1[Material.CUT_COPPER_SLAB.ordinal()] = 91;
            } catch (NoSuchFieldError nosuchfielderror323) {
                ;
            }

            try {
                aint1[Material.CUT_COPPER_STAIRS.ordinal()] = 87;
            } catch (NoSuchFieldError nosuchfielderror324) {
                ;
            }

            try {
                aint1[Material.CUT_RED_SANDSTONE.ordinal()] = 491;
            } catch (NoSuchFieldError nosuchfielderror325) {
                ;
            }

            try {
                aint1[Material.CUT_RED_SANDSTONE_SLAB.ordinal()] = 255;
            } catch (NoSuchFieldError nosuchfielderror326) {
                ;
            }

            try {
                aint1[Material.CUT_SANDSTONE.ordinal()] = 172;
            } catch (NoSuchFieldError nosuchfielderror327) {
                ;
            }

            try {
                aint1[Material.CUT_SANDSTONE_SLAB.ordinal()] = 246;
            } catch (NoSuchFieldError nosuchfielderror328) {
                ;
            }

            try {
                aint1[Material.CYAN_BANNER.ordinal()] = 1097;
            } catch (NoSuchFieldError nosuchfielderror329) {
                ;
            }

            try {
                aint1[Material.CYAN_BED.ordinal()] = 934;
            } catch (NoSuchFieldError nosuchfielderror330) {
                ;
            }

            try {
                aint1[Material.CYAN_CANDLE.ordinal()] = 1201;
            } catch (NoSuchFieldError nosuchfielderror331) {
                ;
            }

            try {
                aint1[Material.CYAN_CANDLE_CAKE.ordinal()] = 1390;
            } catch (NoSuchFieldError nosuchfielderror332) {
                ;
            }

            try {
                aint1[Material.CYAN_CARPET.ordinal()] = 434;
            } catch (NoSuchFieldError nosuchfielderror333) {
                ;
            }

            try {
                aint1[Material.CYAN_CONCRETE.ordinal()] = 543;
            } catch (NoSuchFieldError nosuchfielderror334) {
                ;
            }

            try {
                aint1[Material.CYAN_CONCRETE_POWDER.ordinal()] = 559;
            } catch (NoSuchFieldError nosuchfielderror335) {
                ;
            }

            try {
                aint1[Material.CYAN_DYE.ordinal()] = 914;
            } catch (NoSuchFieldError nosuchfielderror336) {
                ;
            }

            try {
                aint1[Material.CYAN_GLAZED_TERRACOTTA.ordinal()] = 527;
            } catch (NoSuchFieldError nosuchfielderror337) {
                ;
            }

            try {
                aint1[Material.CYAN_SHULKER_BOX.ordinal()] = 511;
            } catch (NoSuchFieldError nosuchfielderror338) {
                ;
            }

            try {
                aint1[Material.CYAN_STAINED_GLASS.ordinal()] = 459;
            } catch (NoSuchFieldError nosuchfielderror339) {
                ;
            }

            try {
                aint1[Material.CYAN_STAINED_GLASS_PANE.ordinal()] = 475;
            } catch (NoSuchFieldError nosuchfielderror340) {
                ;
            }

            try {
                aint1[Material.CYAN_TERRACOTTA.ordinal()] = 415;
            } catch (NoSuchFieldError nosuchfielderror341) {
                ;
            }

            try {
                aint1[Material.CYAN_WALL_BANNER.ordinal()] = 1343;
            } catch (NoSuchFieldError nosuchfielderror342) {
                ;
            }

            try {
                aint1[Material.CYAN_WOOL.ordinal()] = 190;
            } catch (NoSuchFieldError nosuchfielderror343) {
                ;
            }

            try {
                aint1[Material.DAMAGED_ANVIL.ordinal()] = 400;
            } catch (NoSuchFieldError nosuchfielderror344) {
                ;
            }

            try {
                aint1[Material.DANDELION.ordinal()] = 197;
            } catch (NoSuchFieldError nosuchfielderror345) {
                ;
            }

            try {
                aint1[Material.DANGER_POTTERY_SHERD.ordinal()] = 1242;
            } catch (NoSuchFieldError nosuchfielderror346) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_BOAT.ordinal()] = 749;
            } catch (NoSuchFieldError nosuchfielderror347) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_BUTTON.ordinal()] = 669;
            } catch (NoSuchFieldError nosuchfielderror348) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_CHEST_BOAT.ordinal()] = 750;
            } catch (NoSuchFieldError nosuchfielderror349) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_DOOR.ordinal()] = 696;
            } catch (NoSuchFieldError nosuchfielderror350) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_FENCE.ordinal()] = 296;
            } catch (NoSuchFieldError nosuchfielderror351) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_FENCE_GATE.ordinal()] = 719;
            } catch (NoSuchFieldError nosuchfielderror352) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_HANGING_SIGN.ordinal()] = 864;
            } catch (NoSuchFieldError nosuchfielderror353) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_LEAVES.ordinal()] = 161;
            } catch (NoSuchFieldError nosuchfielderror354) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_LOG.ordinal()] = 117;
            } catch (NoSuchFieldError nosuchfielderror355) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_PLANKS.ordinal()] = 30;
            } catch (NoSuchFieldError nosuchfielderror356) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_PRESSURE_PLATE.ordinal()] = 684;
            } catch (NoSuchFieldError nosuchfielderror357) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_SAPLING.ordinal()] = 42;
            } catch (NoSuchFieldError nosuchfielderror358) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_SIGN.ordinal()] = 853;
            } catch (NoSuchFieldError nosuchfielderror359) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_SLAB.ordinal()] = 237;
            } catch (NoSuchFieldError nosuchfielderror360) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_STAIRS.ordinal()] = 368;
            } catch (NoSuchFieldError nosuchfielderror361) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_TRAPDOOR.ordinal()] = 708;
            } catch (NoSuchFieldError nosuchfielderror362) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_WALL_HANGING_SIGN.ordinal()] = 1280;
            } catch (NoSuchFieldError nosuchfielderror363) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_WALL_SIGN.ordinal()] = 1271;
            } catch (NoSuchFieldError nosuchfielderror364) {
                ;
            }

            try {
                aint1[Material.DARK_OAK_WOOD.ordinal()] = 151;
            } catch (NoSuchFieldError nosuchfielderror365) {
                ;
            }

            try {
                aint1[Material.DARK_PRISMARINE.ordinal()] = 484;
            } catch (NoSuchFieldError nosuchfielderror366) {
                ;
            }

            try {
                aint1[Material.DARK_PRISMARINE_SLAB.ordinal()] = 259;
            } catch (NoSuchFieldError nosuchfielderror367) {
                ;
            }

            try {
                aint1[Material.DARK_PRISMARINE_STAIRS.ordinal()] = 487;
            } catch (NoSuchFieldError nosuchfielderror368) {
                ;
            }

            try {
                aint1[Material.DAYLIGHT_DETECTOR.ordinal()] = 653;
            } catch (NoSuchFieldError nosuchfielderror369) {
                ;
            }

            try {
                aint1[Material.DEAD_BRAIN_CORAL.ordinal()] = 583;
            } catch (NoSuchFieldError nosuchfielderror370) {
                ;
            }

            try {
                aint1[Material.DEAD_BRAIN_CORAL_BLOCK.ordinal()] = 569;
            } catch (NoSuchFieldError nosuchfielderror371) {
                ;
            }

            try {
                aint1[Material.DEAD_BRAIN_CORAL_FAN.ordinal()] = 594;
            } catch (NoSuchFieldError nosuchfielderror372) {
                ;
            }

            try {
                aint1[Material.DEAD_BRAIN_CORAL_WALL_FAN.ordinal()] = 1357;
            } catch (NoSuchFieldError nosuchfielderror373) {
                ;
            }

            try {
                aint1[Material.DEAD_BUBBLE_CORAL.ordinal()] = 584;
            } catch (NoSuchFieldError nosuchfielderror374) {
                ;
            }

            try {
                aint1[Material.DEAD_BUBBLE_CORAL_BLOCK.ordinal()] = 570;
            } catch (NoSuchFieldError nosuchfielderror375) {
                ;
            }

            try {
                aint1[Material.DEAD_BUBBLE_CORAL_FAN.ordinal()] = 595;
            } catch (NoSuchFieldError nosuchfielderror376) {
                ;
            }

            try {
                aint1[Material.DEAD_BUBBLE_CORAL_WALL_FAN.ordinal()] = 1358;
            } catch (NoSuchFieldError nosuchfielderror377) {
                ;
            }

            try {
                aint1[Material.DEAD_BUSH.ordinal()] = 178;
            } catch (NoSuchFieldError nosuchfielderror378) {
                ;
            }

            try {
                aint1[Material.DEAD_FIRE_CORAL.ordinal()] = 585;
            } catch (NoSuchFieldError nosuchfielderror379) {
                ;
            }

            try {
                aint1[Material.DEAD_FIRE_CORAL_BLOCK.ordinal()] = 571;
            } catch (NoSuchFieldError nosuchfielderror380) {
                ;
            }

            try {
                aint1[Material.DEAD_FIRE_CORAL_FAN.ordinal()] = 596;
            } catch (NoSuchFieldError nosuchfielderror381) {
                ;
            }

            try {
                aint1[Material.DEAD_FIRE_CORAL_WALL_FAN.ordinal()] = 1359;
            } catch (NoSuchFieldError nosuchfielderror382) {
                ;
            }

            try {
                aint1[Material.DEAD_HORN_CORAL.ordinal()] = 586;
            } catch (NoSuchFieldError nosuchfielderror383) {
                ;
            }

            try {
                aint1[Material.DEAD_HORN_CORAL_BLOCK.ordinal()] = 572;
            } catch (NoSuchFieldError nosuchfielderror384) {
                ;
            }

            try {
                aint1[Material.DEAD_HORN_CORAL_FAN.ordinal()] = 597;
            } catch (NoSuchFieldError nosuchfielderror385) {
                ;
            }

            try {
                aint1[Material.DEAD_HORN_CORAL_WALL_FAN.ordinal()] = 1360;
            } catch (NoSuchFieldError nosuchfielderror386) {
                ;
            }

            try {
                aint1[Material.DEAD_TUBE_CORAL.ordinal()] = 587;
            } catch (NoSuchFieldError nosuchfielderror387) {
                ;
            }

            try {
                aint1[Material.DEAD_TUBE_CORAL_BLOCK.ordinal()] = 568;
            } catch (NoSuchFieldError nosuchfielderror388) {
                ;
            }

            try {
                aint1[Material.DEAD_TUBE_CORAL_FAN.ordinal()] = 593;
            } catch (NoSuchFieldError nosuchfielderror389) {
                ;
            }

            try {
                aint1[Material.DEAD_TUBE_CORAL_WALL_FAN.ordinal()] = 1356;
            } catch (NoSuchFieldError nosuchfielderror390) {
                ;
            }

            try {
                aint1[Material.DEBUG_STICK.ordinal()] = 1122;
            } catch (NoSuchFieldError nosuchfielderror391) {
                ;
            }

            try {
                aint1[Material.DECORATED_POT.ordinal()] = 267;
            } catch (NoSuchFieldError nosuchfielderror392) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror393) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_BRICKS.ordinal()] = 325;
            } catch (NoSuchFieldError nosuchfielderror394) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_BRICK_SLAB.ordinal()] = 633;
            } catch (NoSuchFieldError nosuchfielderror395) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_BRICK_STAIRS.ordinal()] = 616;
            } catch (NoSuchFieldError nosuchfielderror396) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_BRICK_WALL.ordinal()] = 396;
            } catch (NoSuchFieldError nosuchfielderror397) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_COAL_ORE.ordinal()] = 51;
            } catch (NoSuchFieldError nosuchfielderror398) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_COPPER_ORE.ordinal()] = 55;
            } catch (NoSuchFieldError nosuchfielderror399) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_DIAMOND_ORE.ordinal()] = 65;
            } catch (NoSuchFieldError nosuchfielderror400) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_EMERALD_ORE.ordinal()] = 61;
            } catch (NoSuchFieldError nosuchfielderror401) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_GOLD_ORE.ordinal()] = 57;
            } catch (NoSuchFieldError nosuchfielderror402) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_IRON_ORE.ordinal()] = 53;
            } catch (NoSuchFieldError nosuchfielderror403) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_LAPIS_ORE.ordinal()] = 63;
            } catch (NoSuchFieldError nosuchfielderror404) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_REDSTONE_ORE.ordinal()] = 59;
            } catch (NoSuchFieldError nosuchfielderror405) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_TILES.ordinal()] = 327;
            } catch (NoSuchFieldError nosuchfielderror406) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_TILE_SLAB.ordinal()] = 634;
            } catch (NoSuchFieldError nosuchfielderror407) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_TILE_STAIRS.ordinal()] = 617;
            } catch (NoSuchFieldError nosuchfielderror408) {
                ;
            }

            try {
                aint1[Material.DEEPSLATE_TILE_WALL.ordinal()] = 397;
            } catch (NoSuchFieldError nosuchfielderror409) {
                ;
            }

            try {
                aint1[Material.DETECTOR_RAIL.ordinal()] = 725;
            } catch (NoSuchFieldError nosuchfielderror410) {
                ;
            }

            try {
                aint1[Material.DIAMOND.ordinal()] = 765;
            } catch (NoSuchFieldError nosuchfielderror411) {
                ;
            }

            try {
                aint1[Material.DIAMOND_AXE.ordinal()] = 801;
            } catch (NoSuchFieldError nosuchfielderror412) {
                ;
            }

            try {
                aint1[Material.DIAMOND_BLOCK.ordinal()] = 78;
            } catch (NoSuchFieldError nosuchfielderror413) {
                ;
            }

            try {
                aint1[Material.DIAMOND_BOOTS.ordinal()] = 832;
            } catch (NoSuchFieldError nosuchfielderror414) {
                ;
            }

            try {
                aint1[Material.DIAMOND_CHESTPLATE.ordinal()] = 830;
            } catch (NoSuchFieldError nosuchfielderror415) {
                ;
            }

            try {
                aint1[Material.DIAMOND_HELMET.ordinal()] = 829;
            } catch (NoSuchFieldError nosuchfielderror416) {
                ;
            }

            try {
                aint1[Material.DIAMOND_HOE.ordinal()] = 802;
            } catch (NoSuchFieldError nosuchfielderror417) {
                ;
            }

            try {
                aint1[Material.DIAMOND_HORSE_ARMOR.ordinal()] = 1081;
            } catch (NoSuchFieldError nosuchfielderror418) {
                ;
            }

            try {
                aint1[Material.DIAMOND_LEGGINGS.ordinal()] = 831;
            } catch (NoSuchFieldError nosuchfielderror419) {
                ;
            }

            try {
                aint1[Material.DIAMOND_ORE.ordinal()] = 64;
            } catch (NoSuchFieldError nosuchfielderror420) {
                ;
            }

            try {
                aint1[Material.DIAMOND_PICKAXE.ordinal()] = 800;
            } catch (NoSuchFieldError nosuchfielderror421) {
                ;
            }

            try {
                aint1[Material.DIAMOND_SHOVEL.ordinal()] = 799;
            } catch (NoSuchFieldError nosuchfielderror422) {
                ;
            }

            try {
                aint1[Material.DIAMOND_SWORD.ordinal()] = 798;
            } catch (NoSuchFieldError nosuchfielderror423) {
                ;
            }

            try {
                aint1[Material.DIORITE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror424) {
                ;
            }

            try {
                aint1[Material.DIORITE_SLAB.ordinal()] = 630;
            } catch (NoSuchFieldError nosuchfielderror425) {
                ;
            }

            try {
                aint1[Material.DIORITE_STAIRS.ordinal()] = 613;
            } catch (NoSuchFieldError nosuchfielderror426) {
                ;
            }

            try {
                aint1[Material.DIORITE_WALL.ordinal()] = 390;
            } catch (NoSuchFieldError nosuchfielderror427) {
                ;
            }

            try {
                aint1[Material.DIRT.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror428) {
                ;
            }

            try {
                aint1[Material.DIRT_PATH.ordinal()] = 443;
            } catch (NoSuchFieldError nosuchfielderror429) {
                ;
            }

            try {
                aint1[Material.DISC_FRAGMENT_5.ordinal()] = 1139;
            } catch (NoSuchFieldError nosuchfielderror430) {
                ;
            }

            try {
                aint1[Material.DISPENSER.ordinal()] = 647;
            } catch (NoSuchFieldError nosuchfielderror431) {
                ;
            }

            try {
                aint1[Material.DOLPHIN_SPAWN_EGG.ordinal()] = 980;
            } catch (NoSuchFieldError nosuchfielderror432) {
                ;
            }

            try {
                aint1[Material.DONKEY_SPAWN_EGG.ordinal()] = 981;
            } catch (NoSuchFieldError nosuchfielderror433) {
                ;
            }

            try {
                aint1[Material.DRAGON_BREATH.ordinal()] = 1112;
            } catch (NoSuchFieldError nosuchfielderror434) {
                ;
            }

            try {
                aint1[Material.DRAGON_EGG.ordinal()] = 358;
            } catch (NoSuchFieldError nosuchfielderror435) {
                ;
            }

            try {
                aint1[Material.DRAGON_HEAD.ordinal()] = 1063;
            } catch (NoSuchFieldError nosuchfielderror436) {
                ;
            }

            try {
                aint1[Material.DRAGON_WALL_HEAD.ordinal()] = 1332;
            } catch (NoSuchFieldError nosuchfielderror437) {
                ;
            }

            try {
                aint1[Material.DRIED_KELP.ordinal()] = 945;
            } catch (NoSuchFieldError nosuchfielderror438) {
                ;
            }

            try {
                aint1[Material.DRIED_KELP_BLOCK.ordinal()] = 884;
            } catch (NoSuchFieldError nosuchfielderror439) {
                ;
            }

            try {
                aint1[Material.DRIPSTONE_BLOCK.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror440) {
                ;
            }

            try {
                aint1[Material.DROPPER.ordinal()] = 648;
            } catch (NoSuchFieldError nosuchfielderror441) {
                ;
            }

            try {
                aint1[Material.DROWNED_SPAWN_EGG.ordinal()] = 982;
            } catch (NoSuchFieldError nosuchfielderror442) {
                ;
            }

            try {
                aint1[Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1221;
            } catch (NoSuchFieldError nosuchfielderror443) {
                ;
            }

            try {
                aint1[Material.ECHO_SHARD.ordinal()] = 1217;
            } catch (NoSuchFieldError nosuchfielderror444) {
                ;
            }

            try {
                aint1[Material.EGG.ordinal()] = 888;
            } catch (NoSuchFieldError nosuchfielderror445) {
                ;
            }

            try {
                aint1[Material.ELDER_GUARDIAN_SPAWN_EGG.ordinal()] = 983;
            } catch (NoSuchFieldError nosuchfielderror446) {
                ;
            }

            try {
                aint1[Material.ELYTRA.ordinal()] = 736;
            } catch (NoSuchFieldError nosuchfielderror447) {
                ;
            }

            try {
                aint1[Material.EMERALD.ordinal()] = 766;
            } catch (NoSuchFieldError nosuchfielderror448) {
                ;
            }

            try {
                aint1[Material.EMERALD_BLOCK.ordinal()] = 361;
            } catch (NoSuchFieldError nosuchfielderror449) {
                ;
            }

            try {
                aint1[Material.EMERALD_ORE.ordinal()] = 60;
            } catch (NoSuchFieldError nosuchfielderror450) {
                ;
            }

            try {
                aint1[Material.ENCHANTED_BOOK.ordinal()] = 1069;
            } catch (NoSuchFieldError nosuchfielderror451) {
                ;
            }

            try {
                aint1[Material.ENCHANTED_GOLDEN_APPLE.ordinal()] = 846;
            } catch (NoSuchFieldError nosuchfielderror452) {
                ;
            }

            try {
                aint1[Material.ENCHANTING_TABLE.ordinal()] = 354;
            } catch (NoSuchFieldError nosuchfielderror453) {
                ;
            }

            try {
                aint1[Material.ENDERMAN_SPAWN_EGG.ordinal()] = 985;
            } catch (NoSuchFieldError nosuchfielderror454) {
                ;
            }

            try {
                aint1[Material.ENDERMITE_SPAWN_EGG.ordinal()] = 986;
            } catch (NoSuchFieldError nosuchfielderror455) {
                ;
            }

            try {
                aint1[Material.ENDER_CHEST.ordinal()] = 360;
            } catch (NoSuchFieldError nosuchfielderror456) {
                ;
            }

            try {
                aint1[Material.ENDER_DRAGON_SPAWN_EGG.ordinal()] = 984;
            } catch (NoSuchFieldError nosuchfielderror457) {
                ;
            }

            try {
                aint1[Material.ENDER_EYE.ordinal()] = 966;
            } catch (NoSuchFieldError nosuchfielderror458) {
                ;
            }

            try {
                aint1[Material.ENDER_PEARL.ordinal()] = 953;
            } catch (NoSuchFieldError nosuchfielderror459) {
                ;
            }

            try {
                aint1[Material.END_CRYSTAL.ordinal()] = 1104;
            } catch (NoSuchFieldError nosuchfielderror460) {
                ;
            }

            try {
                aint1[Material.END_GATEWAY.ordinal()] = 1353;
            } catch (NoSuchFieldError nosuchfielderror461) {
                ;
            }

            try {
                aint1[Material.END_PORTAL.ordinal()] = 1295;
            } catch (NoSuchFieldError nosuchfielderror462) {
                ;
            }

            try {
                aint1[Material.END_PORTAL_FRAME.ordinal()] = 355;
            } catch (NoSuchFieldError nosuchfielderror463) {
                ;
            }

            try {
                aint1[Material.END_ROD.ordinal()] = 271;
            } catch (NoSuchFieldError nosuchfielderror464) {
                ;
            }

            try {
                aint1[Material.END_STONE.ordinal()] = 356;
            } catch (NoSuchFieldError nosuchfielderror465) {
                ;
            }

            try {
                aint1[Material.END_STONE_BRICKS.ordinal()] = 357;
            } catch (NoSuchFieldError nosuchfielderror466) {
                ;
            }

            try {
                aint1[Material.END_STONE_BRICK_SLAB.ordinal()] = 623;
            } catch (NoSuchFieldError nosuchfielderror467) {
                ;
            }

            try {
                aint1[Material.END_STONE_BRICK_STAIRS.ordinal()] = 605;
            } catch (NoSuchFieldError nosuchfielderror468) {
                ;
            }

            try {
                aint1[Material.END_STONE_BRICK_WALL.ordinal()] = 389;
            } catch (NoSuchFieldError nosuchfielderror469) {
                ;
            }

            try {
                aint1[Material.EVOKER_SPAWN_EGG.ordinal()] = 987;
            } catch (NoSuchFieldError nosuchfielderror470) {
                ;
            }

            try {
                aint1[Material.EXPERIENCE_BOTTLE.ordinal()] = 1045;
            } catch (NoSuchFieldError nosuchfielderror471) {
                ;
            }

            try {
                aint1[Material.EXPLORER_POTTERY_SHERD.ordinal()] = 1243;
            } catch (NoSuchFieldError nosuchfielderror472) {
                ;
            }

            try {
                aint1[Material.EXPOSED_COPPER.ordinal()] = 80;
            } catch (NoSuchFieldError nosuchfielderror473) {
                ;
            }

            try {
                aint1[Material.EXPOSED_CUT_COPPER.ordinal()] = 84;
            } catch (NoSuchFieldError nosuchfielderror474) {
                ;
            }

            try {
                aint1[Material.EXPOSED_CUT_COPPER_SLAB.ordinal()] = 92;
            } catch (NoSuchFieldError nosuchfielderror475) {
                ;
            }

            try {
                aint1[Material.EXPOSED_CUT_COPPER_STAIRS.ordinal()] = 88;
            } catch (NoSuchFieldError nosuchfielderror476) {
                ;
            }

            try {
                aint1[Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1225;
            } catch (NoSuchFieldError nosuchfielderror477) {
                ;
            }

            try {
                aint1[Material.FARMLAND.ordinal()] = 280;
            } catch (NoSuchFieldError nosuchfielderror478) {
                ;
            }

            try {
                aint1[Material.FEATHER.ordinal()] = 812;
            } catch (NoSuchFieldError nosuchfielderror479) {
                ;
            }

            try {
                aint1[Material.FERMENTED_SPIDER_EYE.ordinal()] = 961;
            } catch (NoSuchFieldError nosuchfielderror480) {
                ;
            }

            try {
                aint1[Material.FERN.ordinal()] = 175;
            } catch (NoSuchFieldError nosuchfielderror481) {
                ;
            }

            try {
                aint1[Material.FILLED_MAP.ordinal()] = 942;
            } catch (NoSuchFieldError nosuchfielderror482) {
                ;
            }

            try {
                aint1[Material.FIRE.ordinal()] = 1262;
            } catch (NoSuchFieldError nosuchfielderror483) {
                ;
            }

            try {
                aint1[Material.FIREWORK_ROCKET.ordinal()] = 1067;
            } catch (NoSuchFieldError nosuchfielderror484) {
                ;
            }

            try {
                aint1[Material.FIREWORK_STAR.ordinal()] = 1068;
            } catch (NoSuchFieldError nosuchfielderror485) {
                ;
            }

            try {
                aint1[Material.FIRE_CHARGE.ordinal()] = 1046;
            } catch (NoSuchFieldError nosuchfielderror486) {
                ;
            }

            try {
                aint1[Material.FIRE_CORAL.ordinal()] = 581;
            } catch (NoSuchFieldError nosuchfielderror487) {
                ;
            }

            try {
                aint1[Material.FIRE_CORAL_BLOCK.ordinal()] = 576;
            } catch (NoSuchFieldError nosuchfielderror488) {
                ;
            }

            try {
                aint1[Material.FIRE_CORAL_FAN.ordinal()] = 591;
            } catch (NoSuchFieldError nosuchfielderror489) {
                ;
            }

            try {
                aint1[Material.FIRE_CORAL_WALL_FAN.ordinal()] = 1364;
            } catch (NoSuchFieldError nosuchfielderror490) {
                ;
            }

            try {
                aint1[Material.FISHING_ROD.ordinal()] = 892;
            } catch (NoSuchFieldError nosuchfielderror491) {
                ;
            }

            try {
                aint1[Material.FLETCHING_TABLE.ordinal()] = 1159;
            } catch (NoSuchFieldError nosuchfielderror492) {
                ;
            }

            try {
                aint1[Material.FLINT.ordinal()] = 841;
            } catch (NoSuchFieldError nosuchfielderror493) {
                ;
            }

            try {
                aint1[Material.FLINT_AND_STEEL.ordinal()] = 759;
            } catch (NoSuchFieldError nosuchfielderror494) {
                ;
            }

            try {
                aint1[Material.FLOWERING_AZALEA.ordinal()] = 177;
            } catch (NoSuchFieldError nosuchfielderror495) {
                ;
            }

            try {
                aint1[Material.FLOWERING_AZALEA_LEAVES.ordinal()] = 164;
            } catch (NoSuchFieldError nosuchfielderror496) {
                ;
            }

            try {
                aint1[Material.FLOWER_BANNER_PATTERN.ordinal()] = 1147;
            } catch (NoSuchFieldError nosuchfielderror497) {
                ;
            }

            try {
                aint1[Material.FLOWER_POT.ordinal()] = 1051;
            } catch (NoSuchFieldError nosuchfielderror498) {
                ;
            }

            try {
                aint1[Material.FOX_SPAWN_EGG.ordinal()] = 988;
            } catch (NoSuchFieldError nosuchfielderror499) {
                ;
            }

            try {
                aint1[Material.FRIEND_POTTERY_SHERD.ordinal()] = 1244;
            } catch (NoSuchFieldError nosuchfielderror500) {
                ;
            }

            try {
                aint1[Material.FROGSPAWN.ordinal()] = 1216;
            } catch (NoSuchFieldError nosuchfielderror501) {
                ;
            }

            try {
                aint1[Material.FROG_SPAWN_EGG.ordinal()] = 989;
            } catch (NoSuchFieldError nosuchfielderror502) {
                ;
            }

            try {
                aint1[Material.FROSTED_ICE.ordinal()] = 1354;
            } catch (NoSuchFieldError nosuchfielderror503) {
                ;
            }

            try {
                aint1[Material.FURNACE.ordinal()] = 281;
            } catch (NoSuchFieldError nosuchfielderror504) {
                ;
            }

            try {
                aint1[Material.FURNACE_MINECART.ordinal()] = 731;
            } catch (NoSuchFieldError nosuchfielderror505) {
                ;
            }

            try {
                aint1[Material.GHAST_SPAWN_EGG.ordinal()] = 990;
            } catch (NoSuchFieldError nosuchfielderror506) {
                ;
            }

            try {
                aint1[Material.GHAST_TEAR.ordinal()] = 955;
            } catch (NoSuchFieldError nosuchfielderror507) {
                ;
            }

            try {
                aint1[Material.GILDED_BLACKSTONE.ordinal()] = 1181;
            } catch (NoSuchFieldError nosuchfielderror508) {
                ;
            }

            try {
                aint1[Material.GLASS.ordinal()] = 167;
            } catch (NoSuchFieldError nosuchfielderror509) {
                ;
            }

            try {
                aint1[Material.GLASS_BOTTLE.ordinal()] = 959;
            } catch (NoSuchFieldError nosuchfielderror510) {
                ;
            }

            try {
                aint1[Material.GLASS_PANE.ordinal()] = 336;
            } catch (NoSuchFieldError nosuchfielderror511) {
                ;
            }

            try {
                aint1[Material.GLISTERING_MELON_SLICE.ordinal()] = 967;
            } catch (NoSuchFieldError nosuchfielderror512) {
                ;
            }

            try {
                aint1[Material.GLOBE_BANNER_PATTERN.ordinal()] = 1151;
            } catch (NoSuchFieldError nosuchfielderror513) {
                ;
            }

            try {
                aint1[Material.GLOWSTONE.ordinal()] = 311;
            } catch (NoSuchFieldError nosuchfielderror514) {
                ;
            }

            try {
                aint1[Material.GLOWSTONE_DUST.ordinal()] = 895;
            } catch (NoSuchFieldError nosuchfielderror515) {
                ;
            }

            try {
                aint1[Material.GLOW_BERRIES.ordinal()] = 1167;
            } catch (NoSuchFieldError nosuchfielderror516) {
                ;
            }

            try {
                aint1[Material.GLOW_INK_SAC.ordinal()] = 903;
            } catch (NoSuchFieldError nosuchfielderror517) {
                ;
            }

            try {
                aint1[Material.GLOW_ITEM_FRAME.ordinal()] = 1050;
            } catch (NoSuchFieldError nosuchfielderror518) {
                ;
            }

            try {
                aint1[Material.GLOW_LICHEN.ordinal()] = 339;
            } catch (NoSuchFieldError nosuchfielderror519) {
                ;
            }

            try {
                aint1[Material.GLOW_SQUID_SPAWN_EGG.ordinal()] = 991;
            } catch (NoSuchFieldError nosuchfielderror520) {
                ;
            }

            try {
                aint1[Material.GOAT_HORN.ordinal()] = 1153;
            } catch (NoSuchFieldError nosuchfielderror521) {
                ;
            }

            try {
                aint1[Material.GOAT_SPAWN_EGG.ordinal()] = 992;
            } catch (NoSuchFieldError nosuchfielderror522) {
                ;
            }

            try {
                aint1[Material.GOLDEN_APPLE.ordinal()] = 845;
            } catch (NoSuchFieldError nosuchfielderror523) {
                ;
            }

            try {
                aint1[Material.GOLDEN_AXE.ordinal()] = 791;
            } catch (NoSuchFieldError nosuchfielderror524) {
                ;
            }

            try {
                aint1[Material.GOLDEN_BOOTS.ordinal()] = 836;
            } catch (NoSuchFieldError nosuchfielderror525) {
                ;
            }

            try {
                aint1[Material.GOLDEN_CARROT.ordinal()] = 1057;
            } catch (NoSuchFieldError nosuchfielderror526) {
                ;
            }

            try {
                aint1[Material.GOLDEN_CHESTPLATE.ordinal()] = 834;
            } catch (NoSuchFieldError nosuchfielderror527) {
                ;
            }

            try {
                aint1[Material.GOLDEN_HELMET.ordinal()] = 833;
            } catch (NoSuchFieldError nosuchfielderror528) {
                ;
            }

            try {
                aint1[Material.GOLDEN_HOE.ordinal()] = 792;
            } catch (NoSuchFieldError nosuchfielderror529) {
                ;
            }

            try {
                aint1[Material.GOLDEN_HORSE_ARMOR.ordinal()] = 1080;
            } catch (NoSuchFieldError nosuchfielderror530) {
                ;
            }

            try {
                aint1[Material.GOLDEN_LEGGINGS.ordinal()] = 835;
            } catch (NoSuchFieldError nosuchfielderror531) {
                ;
            }

            try {
                aint1[Material.GOLDEN_PICKAXE.ordinal()] = 790;
            } catch (NoSuchFieldError nosuchfielderror532) {
                ;
            }

            try {
                aint1[Material.GOLDEN_SHOVEL.ordinal()] = 789;
            } catch (NoSuchFieldError nosuchfielderror533) {
                ;
            }

            try {
                aint1[Material.GOLDEN_SWORD.ordinal()] = 788;
            } catch (NoSuchFieldError nosuchfielderror534) {
                ;
            }

            try {
                aint1[Material.GOLD_BLOCK.ordinal()] = 77;
            } catch (NoSuchFieldError nosuchfielderror535) {
                ;
            }

            try {
                aint1[Material.GOLD_INGOT.ordinal()] = 775;
            } catch (NoSuchFieldError nosuchfielderror536) {
                ;
            }

            try {
                aint1[Material.GOLD_NUGGET.ordinal()] = 956;
            } catch (NoSuchFieldError nosuchfielderror537) {
                ;
            }

            try {
                aint1[Material.GOLD_ORE.ordinal()] = 56;
            } catch (NoSuchFieldError nosuchfielderror538) {
                ;
            }

            try {
                aint1[Material.GRANITE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror539) {
                ;
            }

            try {
                aint1[Material.GRANITE_SLAB.ordinal()] = 626;
            } catch (NoSuchFieldError nosuchfielderror540) {
                ;
            }

            try {
                aint1[Material.GRANITE_STAIRS.ordinal()] = 609;
            } catch (NoSuchFieldError nosuchfielderror541) {
                ;
            }

            try {
                aint1[Material.GRANITE_WALL.ordinal()] = 382;
            } catch (NoSuchFieldError nosuchfielderror542) {
                ;
            }

            try {
                aint1[Material.GRASS.ordinal()] = 174;
            } catch (NoSuchFieldError nosuchfielderror543) {
                ;
            }

            try {
                aint1[Material.GRASS_BLOCK.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror544) {
                ;
            }

            try {
                aint1[Material.GRAVEL.ordinal()] = 49;
            } catch (NoSuchFieldError nosuchfielderror545) {
                ;
            }

            try {
                aint1[Material.GRAY_BANNER.ordinal()] = 1095;
            } catch (NoSuchFieldError nosuchfielderror546) {
                ;
            }

            try {
                aint1[Material.GRAY_BED.ordinal()] = 932;
            } catch (NoSuchFieldError nosuchfielderror547) {
                ;
            }

            try {
                aint1[Material.GRAY_CANDLE.ordinal()] = 1199;
            } catch (NoSuchFieldError nosuchfielderror548) {
                ;
            }

            try {
                aint1[Material.GRAY_CANDLE_CAKE.ordinal()] = 1388;
            } catch (NoSuchFieldError nosuchfielderror549) {
                ;
            }

            try {
                aint1[Material.GRAY_CARPET.ordinal()] = 432;
            } catch (NoSuchFieldError nosuchfielderror550) {
                ;
            }

            try {
                aint1[Material.GRAY_CONCRETE.ordinal()] = 541;
            } catch (NoSuchFieldError nosuchfielderror551) {
                ;
            }

            try {
                aint1[Material.GRAY_CONCRETE_POWDER.ordinal()] = 557;
            } catch (NoSuchFieldError nosuchfielderror552) {
                ;
            }

            try {
                aint1[Material.GRAY_DYE.ordinal()] = 912;
            } catch (NoSuchFieldError nosuchfielderror553) {
                ;
            }

            try {
                aint1[Material.GRAY_GLAZED_TERRACOTTA.ordinal()] = 525;
            } catch (NoSuchFieldError nosuchfielderror554) {
                ;
            }

            try {
                aint1[Material.GRAY_SHULKER_BOX.ordinal()] = 509;
            } catch (NoSuchFieldError nosuchfielderror555) {
                ;
            }

            try {
                aint1[Material.GRAY_STAINED_GLASS.ordinal()] = 457;
            } catch (NoSuchFieldError nosuchfielderror556) {
                ;
            }

            try {
                aint1[Material.GRAY_STAINED_GLASS_PANE.ordinal()] = 473;
            } catch (NoSuchFieldError nosuchfielderror557) {
                ;
            }

            try {
                aint1[Material.GRAY_TERRACOTTA.ordinal()] = 413;
            } catch (NoSuchFieldError nosuchfielderror558) {
                ;
            }

            try {
                aint1[Material.GRAY_WALL_BANNER.ordinal()] = 1341;
            } catch (NoSuchFieldError nosuchfielderror559) {
                ;
            }

            try {
                aint1[Material.GRAY_WOOL.ordinal()] = 188;
            } catch (NoSuchFieldError nosuchfielderror560) {
                ;
            }

            try {
                aint1[Material.GREEN_BANNER.ordinal()] = 1101;
            } catch (NoSuchFieldError nosuchfielderror561) {
                ;
            }

            try {
                aint1[Material.GREEN_BED.ordinal()] = 938;
            } catch (NoSuchFieldError nosuchfielderror562) {
                ;
            }

            try {
                aint1[Material.GREEN_CANDLE.ordinal()] = 1205;
            } catch (NoSuchFieldError nosuchfielderror563) {
                ;
            }

            try {
                aint1[Material.GREEN_CANDLE_CAKE.ordinal()] = 1394;
            } catch (NoSuchFieldError nosuchfielderror564) {
                ;
            }

            try {
                aint1[Material.GREEN_CARPET.ordinal()] = 438;
            } catch (NoSuchFieldError nosuchfielderror565) {
                ;
            }

            try {
                aint1[Material.GREEN_CONCRETE.ordinal()] = 547;
            } catch (NoSuchFieldError nosuchfielderror566) {
                ;
            }

            try {
                aint1[Material.GREEN_CONCRETE_POWDER.ordinal()] = 563;
            } catch (NoSuchFieldError nosuchfielderror567) {
                ;
            }

            try {
                aint1[Material.GREEN_DYE.ordinal()] = 918;
            } catch (NoSuchFieldError nosuchfielderror568) {
                ;
            }

            try {
                aint1[Material.GREEN_GLAZED_TERRACOTTA.ordinal()] = 531;
            } catch (NoSuchFieldError nosuchfielderror569) {
                ;
            }

            try {
                aint1[Material.GREEN_SHULKER_BOX.ordinal()] = 515;
            } catch (NoSuchFieldError nosuchfielderror570) {
                ;
            }

            try {
                aint1[Material.GREEN_STAINED_GLASS.ordinal()] = 463;
            } catch (NoSuchFieldError nosuchfielderror571) {
                ;
            }

            try {
                aint1[Material.GREEN_STAINED_GLASS_PANE.ordinal()] = 479;
            } catch (NoSuchFieldError nosuchfielderror572) {
                ;
            }

            try {
                aint1[Material.GREEN_TERRACOTTA.ordinal()] = 419;
            } catch (NoSuchFieldError nosuchfielderror573) {
                ;
            }

            try {
                aint1[Material.GREEN_WALL_BANNER.ordinal()] = 1347;
            } catch (NoSuchFieldError nosuchfielderror574) {
                ;
            }

            try {
                aint1[Material.GREEN_WOOL.ordinal()] = 194;
            } catch (NoSuchFieldError nosuchfielderror575) {
                ;
            }

            try {
                aint1[Material.GRINDSTONE.ordinal()] = 1160;
            } catch (NoSuchFieldError nosuchfielderror576) {
                ;
            }

            try {
                aint1[Material.GUARDIAN_SPAWN_EGG.ordinal()] = 993;
            } catch (NoSuchFieldError nosuchfielderror577) {
                ;
            }

            try {
                aint1[Material.GUNPOWDER.ordinal()] = 813;
            } catch (NoSuchFieldError nosuchfielderror578) {
                ;
            }

            try {
                aint1[Material.HANGING_ROOTS.ordinal()] = 227;
            } catch (NoSuchFieldError nosuchfielderror579) {
                ;
            }

            try {
                aint1[Material.HAY_BLOCK.ordinal()] = 424;
            } catch (NoSuchFieldError nosuchfielderror580) {
                ;
            }

            try {
                aint1[Material.HEARTBREAK_POTTERY_SHERD.ordinal()] = 1246;
            } catch (NoSuchFieldError nosuchfielderror581) {
                ;
            }

            try {
                aint1[Material.HEART_OF_THE_SEA.ordinal()] = 1143;
            } catch (NoSuchFieldError nosuchfielderror582) {
                ;
            }

            try {
                aint1[Material.HEART_POTTERY_SHERD.ordinal()] = 1245;
            } catch (NoSuchFieldError nosuchfielderror583) {
                ;
            }

            try {
                aint1[Material.HEAVY_WEIGHTED_PRESSURE_PLATE.ordinal()] = 677;
            } catch (NoSuchFieldError nosuchfielderror584) {
                ;
            }

            try {
                aint1[Material.HOGLIN_SPAWN_EGG.ordinal()] = 994;
            } catch (NoSuchFieldError nosuchfielderror585) {
                ;
            }

            try {
                aint1[Material.HONEYCOMB.ordinal()] = 1171;
            } catch (NoSuchFieldError nosuchfielderror586) {
                ;
            }

            try {
                aint1[Material.HONEYCOMB_BLOCK.ordinal()] = 1175;
            } catch (NoSuchFieldError nosuchfielderror587) {
                ;
            }

            try {
                aint1[Material.HONEY_BLOCK.ordinal()] = 644;
            } catch (NoSuchFieldError nosuchfielderror588) {
                ;
            }

            try {
                aint1[Material.HONEY_BOTTLE.ordinal()] = 1174;
            } catch (NoSuchFieldError nosuchfielderror589) {
                ;
            }

            try {
                aint1[Material.HOPPER.ordinal()] = 646;
            } catch (NoSuchFieldError nosuchfielderror590) {
                ;
            }

            try {
                aint1[Material.HOPPER_MINECART.ordinal()] = 733;
            } catch (NoSuchFieldError nosuchfielderror591) {
                ;
            }

            try {
                aint1[Material.HORN_CORAL.ordinal()] = 582;
            } catch (NoSuchFieldError nosuchfielderror592) {
                ;
            }

            try {
                aint1[Material.HORN_CORAL_BLOCK.ordinal()] = 577;
            } catch (NoSuchFieldError nosuchfielderror593) {
                ;
            }

            try {
                aint1[Material.HORN_CORAL_FAN.ordinal()] = 592;
            } catch (NoSuchFieldError nosuchfielderror594) {
                ;
            }

            try {
                aint1[Material.HORN_CORAL_WALL_FAN.ordinal()] = 1365;
            } catch (NoSuchFieldError nosuchfielderror595) {
                ;
            }

            try {
                aint1[Material.HORSE_SPAWN_EGG.ordinal()] = 995;
            } catch (NoSuchFieldError nosuchfielderror596) {
                ;
            }

            try {
                aint1[Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1235;
            } catch (NoSuchFieldError nosuchfielderror597) {
                ;
            }

            try {
                aint1[Material.HOWL_POTTERY_SHERD.ordinal()] = 1247;
            } catch (NoSuchFieldError nosuchfielderror598) {
                ;
            }

            try {
                aint1[Material.HUSK_SPAWN_EGG.ordinal()] = 996;
            } catch (NoSuchFieldError nosuchfielderror599) {
                ;
            }

            try {
                aint1[Material.ICE.ordinal()] = 285;
            } catch (NoSuchFieldError nosuchfielderror600) {
                ;
            }

            try {
                aint1[Material.INFESTED_CHISELED_STONE_BRICKS.ordinal()] = 317;
            } catch (NoSuchFieldError nosuchfielderror601) {
                ;
            }

            try {
                aint1[Material.INFESTED_COBBLESTONE.ordinal()] = 313;
            } catch (NoSuchFieldError nosuchfielderror602) {
                ;
            }

            try {
                aint1[Material.INFESTED_CRACKED_STONE_BRICKS.ordinal()] = 316;
            } catch (NoSuchFieldError nosuchfielderror603) {
                ;
            }

            try {
                aint1[Material.INFESTED_DEEPSLATE.ordinal()] = 318;
            } catch (NoSuchFieldError nosuchfielderror604) {
                ;
            }

            try {
                aint1[Material.INFESTED_MOSSY_STONE_BRICKS.ordinal()] = 315;
            } catch (NoSuchFieldError nosuchfielderror605) {
                ;
            }

            try {
                aint1[Material.INFESTED_STONE.ordinal()] = 312;
            } catch (NoSuchFieldError nosuchfielderror606) {
                ;
            }

            try {
                aint1[Material.INFESTED_STONE_BRICKS.ordinal()] = 314;
            } catch (NoSuchFieldError nosuchfielderror607) {
                ;
            }

            try {
                aint1[Material.INK_SAC.ordinal()] = 902;
            } catch (NoSuchFieldError nosuchfielderror608) {
                ;
            }

            try {
                aint1[Material.IRON_AXE.ordinal()] = 796;
            } catch (NoSuchFieldError nosuchfielderror609) {
                ;
            }

            try {
                aint1[Material.IRON_BARS.ordinal()] = 334;
            } catch (NoSuchFieldError nosuchfielderror610) {
                ;
            }

            try {
                aint1[Material.IRON_BLOCK.ordinal()] = 75;
            } catch (NoSuchFieldError nosuchfielderror611) {
                ;
            }

            try {
                aint1[Material.IRON_BOOTS.ordinal()] = 828;
            } catch (NoSuchFieldError nosuchfielderror612) {
                ;
            }

            try {
                aint1[Material.IRON_CHESTPLATE.ordinal()] = 826;
            } catch (NoSuchFieldError nosuchfielderror613) {
                ;
            }

            try {
                aint1[Material.IRON_DOOR.ordinal()] = 689;
            } catch (NoSuchFieldError nosuchfielderror614) {
                ;
            }

            try {
                aint1[Material.IRON_GOLEM_SPAWN_EGG.ordinal()] = 997;
            } catch (NoSuchFieldError nosuchfielderror615) {
                ;
            }

            try {
                aint1[Material.IRON_HELMET.ordinal()] = 825;
            } catch (NoSuchFieldError nosuchfielderror616) {
                ;
            }

            try {
                aint1[Material.IRON_HOE.ordinal()] = 797;
            } catch (NoSuchFieldError nosuchfielderror617) {
                ;
            }

            try {
                aint1[Material.IRON_HORSE_ARMOR.ordinal()] = 1079;
            } catch (NoSuchFieldError nosuchfielderror618) {
                ;
            }

            try {
                aint1[Material.IRON_INGOT.ordinal()] = 771;
            } catch (NoSuchFieldError nosuchfielderror619) {
                ;
            }

            try {
                aint1[Material.IRON_LEGGINGS.ordinal()] = 827;
            } catch (NoSuchFieldError nosuchfielderror620) {
                ;
            }

            try {
                aint1[Material.IRON_NUGGET.ordinal()] = 1120;
            } catch (NoSuchFieldError nosuchfielderror621) {
                ;
            }

            try {
                aint1[Material.IRON_ORE.ordinal()] = 52;
            } catch (NoSuchFieldError nosuchfielderror622) {
                ;
            }

            try {
                aint1[Material.IRON_PICKAXE.ordinal()] = 795;
            } catch (NoSuchFieldError nosuchfielderror623) {
                ;
            }

            try {
                aint1[Material.IRON_SHOVEL.ordinal()] = 794;
            } catch (NoSuchFieldError nosuchfielderror624) {
                ;
            }

            try {
                aint1[Material.IRON_SWORD.ordinal()] = 793;
            } catch (NoSuchFieldError nosuchfielderror625) {
                ;
            }

            try {
                aint1[Material.IRON_TRAPDOOR.ordinal()] = 701;
            } catch (NoSuchFieldError nosuchfielderror626) {
                ;
            }

            try {
                aint1[Material.ITEM_FRAME.ordinal()] = 1049;
            } catch (NoSuchFieldError nosuchfielderror627) {
                ;
            }

            try {
                aint1[Material.JACK_O_LANTERN.ordinal()] = 303;
            } catch (NoSuchFieldError nosuchfielderror628) {
                ;
            }

            try {
                aint1[Material.JIGSAW.ordinal()] = 756;
            } catch (NoSuchFieldError nosuchfielderror629) {
                ;
            }

            try {
                aint1[Material.JUKEBOX.ordinal()] = 289;
            } catch (NoSuchFieldError nosuchfielderror630) {
                ;
            }

            try {
                aint1[Material.JUNGLE_BOAT.ordinal()] = 743;
            } catch (NoSuchFieldError nosuchfielderror631) {
                ;
            }

            try {
                aint1[Material.JUNGLE_BUTTON.ordinal()] = 666;
            } catch (NoSuchFieldError nosuchfielderror632) {
                ;
            }

            try {
                aint1[Material.JUNGLE_CHEST_BOAT.ordinal()] = 744;
            } catch (NoSuchFieldError nosuchfielderror633) {
                ;
            }

            try {
                aint1[Material.JUNGLE_DOOR.ordinal()] = 693;
            } catch (NoSuchFieldError nosuchfielderror634) {
                ;
            }

            try {
                aint1[Material.JUNGLE_FENCE.ordinal()] = 293;
            } catch (NoSuchFieldError nosuchfielderror635) {
                ;
            }

            try {
                aint1[Material.JUNGLE_FENCE_GATE.ordinal()] = 716;
            } catch (NoSuchFieldError nosuchfielderror636) {
                ;
            }

            try {
                aint1[Material.JUNGLE_HANGING_SIGN.ordinal()] = 861;
            } catch (NoSuchFieldError nosuchfielderror637) {
                ;
            }

            try {
                aint1[Material.JUNGLE_LEAVES.ordinal()] = 158;
            } catch (NoSuchFieldError nosuchfielderror638) {
                ;
            }

            try {
                aint1[Material.JUNGLE_LOG.ordinal()] = 114;
            } catch (NoSuchFieldError nosuchfielderror639) {
                ;
            }

            try {
                aint1[Material.JUNGLE_PLANKS.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror640) {
                ;
            }

            try {
                aint1[Material.JUNGLE_PRESSURE_PLATE.ordinal()] = 681;
            } catch (NoSuchFieldError nosuchfielderror641) {
                ;
            }

            try {
                aint1[Material.JUNGLE_SAPLING.ordinal()] = 39;
            } catch (NoSuchFieldError nosuchfielderror642) {
                ;
            }

            try {
                aint1[Material.JUNGLE_SIGN.ordinal()] = 850;
            } catch (NoSuchFieldError nosuchfielderror643) {
                ;
            }

            try {
                aint1[Material.JUNGLE_SLAB.ordinal()] = 234;
            } catch (NoSuchFieldError nosuchfielderror644) {
                ;
            }

            try {
                aint1[Material.JUNGLE_STAIRS.ordinal()] = 365;
            } catch (NoSuchFieldError nosuchfielderror645) {
                ;
            }

            try {
                aint1[Material.JUNGLE_TRAPDOOR.ordinal()] = 705;
            } catch (NoSuchFieldError nosuchfielderror646) {
                ;
            }

            try {
                aint1[Material.JUNGLE_WALL_HANGING_SIGN.ordinal()] = 1279;
            } catch (NoSuchFieldError nosuchfielderror647) {
                ;
            }

            try {
                aint1[Material.JUNGLE_WALL_SIGN.ordinal()] = 1270;
            } catch (NoSuchFieldError nosuchfielderror648) {
                ;
            }

            try {
                aint1[Material.JUNGLE_WOOD.ordinal()] = 148;
            } catch (NoSuchFieldError nosuchfielderror649) {
                ;
            }

            try {
                aint1[Material.KELP.ordinal()] = 223;
            } catch (NoSuchFieldError nosuchfielderror650) {
                ;
            }

            try {
                aint1[Material.KELP_PLANT.ordinal()] = 1355;
            } catch (NoSuchFieldError nosuchfielderror651) {
                ;
            }

            try {
                aint1[Material.KNOWLEDGE_BOOK.ordinal()] = 1121;
            } catch (NoSuchFieldError nosuchfielderror652) {
                ;
            }

            try {
                aint1[Material.LADDER.ordinal()] = 282;
            } catch (NoSuchFieldError nosuchfielderror653) {
                ;
            }

            try {
                aint1[Material.LANTERN.ordinal()] = 1164;
            } catch (NoSuchFieldError nosuchfielderror654) {
                ;
            }

            try {
                aint1[Material.LAPIS_BLOCK.ordinal()] = 169;
            } catch (NoSuchFieldError nosuchfielderror655) {
                ;
            }

            try {
                aint1[Material.LAPIS_LAZULI.ordinal()] = 767;
            } catch (NoSuchFieldError nosuchfielderror656) {
                ;
            }

            try {
                aint1[Material.LAPIS_ORE.ordinal()] = 62;
            } catch (NoSuchFieldError nosuchfielderror657) {
                ;
            }

            try {
                aint1[Material.LARGE_AMETHYST_BUD.ordinal()] = 1210;
            } catch (NoSuchFieldError nosuchfielderror658) {
                ;
            }

            try {
                aint1[Material.LARGE_FERN.ordinal()] = 449;
            } catch (NoSuchFieldError nosuchfielderror659) {
                ;
            }

            try {
                aint1[Material.LAVA.ordinal()] = 1257;
            } catch (NoSuchFieldError nosuchfielderror660) {
                ;
            }

            try {
                aint1[Material.LAVA_BUCKET.ordinal()] = 871;
            } catch (NoSuchFieldError nosuchfielderror661) {
                ;
            }

            try {
                aint1[Material.LAVA_CAULDRON.ordinal()] = 1293;
            } catch (NoSuchFieldError nosuchfielderror662) {
                ;
            }

            try {
                aint1[Material.LEAD.ordinal()] = 1083;
            } catch (NoSuchFieldError nosuchfielderror663) {
                ;
            }

            try {
                aint1[Material.LEATHER.ordinal()] = 874;
            } catch (NoSuchFieldError nosuchfielderror664) {
                ;
            }

            try {
                aint1[Material.LEATHER_BOOTS.ordinal()] = 820;
            } catch (NoSuchFieldError nosuchfielderror665) {
                ;
            }

            try {
                aint1[Material.LEATHER_CHESTPLATE.ordinal()] = 818;
            } catch (NoSuchFieldError nosuchfielderror666) {
                ;
            }

            try {
                aint1[Material.LEATHER_HELMET.ordinal()] = 817;
            } catch (NoSuchFieldError nosuchfielderror667) {
                ;
            }

            try {
                aint1[Material.LEATHER_HORSE_ARMOR.ordinal()] = 1082;
            } catch (NoSuchFieldError nosuchfielderror668) {
                ;
            }

            try {
                aint1[Material.LEATHER_LEGGINGS.ordinal()] = 819;
            } catch (NoSuchFieldError nosuchfielderror669) {
                ;
            }

            try {
                aint1[Material.LECTERN.ordinal()] = 649;
            } catch (NoSuchFieldError nosuchfielderror670) {
                ;
            }

            try {
                aint1[Material.LEGACY_ACACIA_DOOR.ordinal()] = 1599;
            } catch (NoSuchFieldError nosuchfielderror671) {
                ;
            }

            try {
                aint1[Material.LEGACY_ACACIA_DOOR_ITEM.ordinal()] = 1831;
            } catch (NoSuchFieldError nosuchfielderror672) {
                ;
            }

            try {
                aint1[Material.LEGACY_ACACIA_FENCE.ordinal()] = 1595;
            } catch (NoSuchFieldError nosuchfielderror673) {
                ;
            }

            try {
                aint1[Material.LEGACY_ACACIA_FENCE_GATE.ordinal()] = 1590;
            } catch (NoSuchFieldError nosuchfielderror674) {
                ;
            }

            try {
                aint1[Material.LEGACY_ACACIA_STAIRS.ordinal()] = 1566;
            } catch (NoSuchFieldError nosuchfielderror675) {
                ;
            }

            try {
                aint1[Material.LEGACY_ACTIVATOR_RAIL.ordinal()] = 1560;
            } catch (NoSuchFieldError nosuchfielderror676) {
                ;
            }

            try {
                aint1[Material.LEGACY_AIR.ordinal()] = 1403;
            } catch (NoSuchFieldError nosuchfielderror677) {
                ;
            }

            try {
                aint1[Material.LEGACY_ANVIL.ordinal()] = 1548;
            } catch (NoSuchFieldError nosuchfielderror678) {
                ;
            }

            try {
                aint1[Material.LEGACY_APPLE.ordinal()] = 1661;
            } catch (NoSuchFieldError nosuchfielderror679) {
                ;
            }

            try {
                aint1[Material.LEGACY_ARMOR_STAND.ordinal()] = 1817;
            } catch (NoSuchFieldError nosuchfielderror680) {
                ;
            }

            try {
                aint1[Material.LEGACY_ARROW.ordinal()] = 1663;
            } catch (NoSuchFieldError nosuchfielderror681) {
                ;
            }

            try {
                aint1[Material.LEGACY_BAKED_POTATO.ordinal()] = 1794;
            } catch (NoSuchFieldError nosuchfielderror682) {
                ;
            }

            try {
                aint1[Material.LEGACY_BANNER.ordinal()] = 1826;
            } catch (NoSuchFieldError nosuchfielderror683) {
                ;
            }

            try {
                aint1[Material.LEGACY_BARRIER.ordinal()] = 1569;
            } catch (NoSuchFieldError nosuchfielderror684) {
                ;
            }

            try {
                aint1[Material.LEGACY_BEACON.ordinal()] = 1541;
            } catch (NoSuchFieldError nosuchfielderror685) {
                ;
            }

            try {
                aint1[Material.LEGACY_BED.ordinal()] = 1756;
            } catch (NoSuchFieldError nosuchfielderror686) {
                ;
            }

            try {
                aint1[Material.LEGACY_BEDROCK.ordinal()] = 1410;
            } catch (NoSuchFieldError nosuchfielderror687) {
                ;
            }

            try {
                aint1[Material.LEGACY_BED_BLOCK.ordinal()] = 1429;
            } catch (NoSuchFieldError nosuchfielderror688) {
                ;
            }

            try {
                aint1[Material.LEGACY_BEETROOT.ordinal()] = 1835;
            } catch (NoSuchFieldError nosuchfielderror689) {
                ;
            }

            try {
                aint1[Material.LEGACY_BEETROOT_BLOCK.ordinal()] = 1610;
            } catch (NoSuchFieldError nosuchfielderror690) {
                ;
            }

            try {
                aint1[Material.LEGACY_BEETROOT_SEEDS.ordinal()] = 1836;
            } catch (NoSuchFieldError nosuchfielderror691) {
                ;
            }

            try {
                aint1[Material.LEGACY_BEETROOT_SOUP.ordinal()] = 1837;
            } catch (NoSuchFieldError nosuchfielderror692) {
                ;
            }

            try {
                aint1[Material.LEGACY_BIRCH_DOOR.ordinal()] = 1597;
            } catch (NoSuchFieldError nosuchfielderror693) {
                ;
            }

            try {
                aint1[Material.LEGACY_BIRCH_DOOR_ITEM.ordinal()] = 1829;
            } catch (NoSuchFieldError nosuchfielderror694) {
                ;
            }

            try {
                aint1[Material.LEGACY_BIRCH_FENCE.ordinal()] = 1592;
            } catch (NoSuchFieldError nosuchfielderror695) {
                ;
            }

            try {
                aint1[Material.LEGACY_BIRCH_FENCE_GATE.ordinal()] = 1587;
            } catch (NoSuchFieldError nosuchfielderror696) {
                ;
            }

            try {
                aint1[Material.LEGACY_BIRCH_WOOD_STAIRS.ordinal()] = 1538;
            } catch (NoSuchFieldError nosuchfielderror697) {
                ;
            }

            try {
                aint1[Material.LEGACY_BLACK_GLAZED_TERRACOTTA.ordinal()] = 1653;
            } catch (NoSuchFieldError nosuchfielderror698) {
                ;
            }

            try {
                aint1[Material.LEGACY_BLACK_SHULKER_BOX.ordinal()] = 1637;
            } catch (NoSuchFieldError nosuchfielderror699) {
                ;
            }

            try {
                aint1[Material.LEGACY_BLAZE_POWDER.ordinal()] = 1778;
            } catch (NoSuchFieldError nosuchfielderror700) {
                ;
            }

            try {
                aint1[Material.LEGACY_BLAZE_ROD.ordinal()] = 1770;
            } catch (NoSuchFieldError nosuchfielderror701) {
                ;
            }

            try {
                aint1[Material.LEGACY_BLUE_GLAZED_TERRACOTTA.ordinal()] = 1649;
            } catch (NoSuchFieldError nosuchfielderror702) {
                ;
            }

            try {
                aint1[Material.LEGACY_BLUE_SHULKER_BOX.ordinal()] = 1633;
            } catch (NoSuchFieldError nosuchfielderror703) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOAT.ordinal()] = 1734;
            } catch (NoSuchFieldError nosuchfielderror704) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOAT_ACACIA.ordinal()] = 1848;
            } catch (NoSuchFieldError nosuchfielderror705) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOAT_BIRCH.ordinal()] = 1846;
            } catch (NoSuchFieldError nosuchfielderror706) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOAT_DARK_OAK.ordinal()] = 1849;
            } catch (NoSuchFieldError nosuchfielderror707) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOAT_JUNGLE.ordinal()] = 1847;
            } catch (NoSuchFieldError nosuchfielderror708) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOAT_SPRUCE.ordinal()] = 1845;
            } catch (NoSuchFieldError nosuchfielderror709) {
                ;
            }

            try {
                aint1[Material.LEGACY_BONE.ordinal()] = 1753;
            } catch (NoSuchFieldError nosuchfielderror710) {
                ;
            }

            try {
                aint1[Material.LEGACY_BONE_BLOCK.ordinal()] = 1619;
            } catch (NoSuchFieldError nosuchfielderror711) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOOK.ordinal()] = 1741;
            } catch (NoSuchFieldError nosuchfielderror712) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOOKSHELF.ordinal()] = 1450;
            } catch (NoSuchFieldError nosuchfielderror713) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOOK_AND_QUILL.ordinal()] = 1787;
            } catch (NoSuchFieldError nosuchfielderror714) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOW.ordinal()] = 1662;
            } catch (NoSuchFieldError nosuchfielderror715) {
                ;
            }

            try {
                aint1[Material.LEGACY_BOWL.ordinal()] = 1682;
            } catch (NoSuchFieldError nosuchfielderror716) {
                ;
            }

            try {
                aint1[Material.LEGACY_BREAD.ordinal()] = 1698;
            } catch (NoSuchFieldError nosuchfielderror717) {
                ;
            }

            try {
                aint1[Material.LEGACY_BREWING_STAND.ordinal()] = 1520;
            } catch (NoSuchFieldError nosuchfielderror718) {
                ;
            }

            try {
                aint1[Material.LEGACY_BREWING_STAND_ITEM.ordinal()] = 1780;
            } catch (NoSuchFieldError nosuchfielderror719) {
                ;
            }

            try {
                aint1[Material.LEGACY_BRICK.ordinal()] = 1448;
            } catch (NoSuchFieldError nosuchfielderror720) {
                ;
            }

            try {
                aint1[Material.LEGACY_BRICK_STAIRS.ordinal()] = 1511;
            } catch (NoSuchFieldError nosuchfielderror721) {
                ;
            }

            try {
                aint1[Material.LEGACY_BROWN_GLAZED_TERRACOTTA.ordinal()] = 1650;
            } catch (NoSuchFieldError nosuchfielderror722) {
                ;
            }

            try {
                aint1[Material.LEGACY_BROWN_MUSHROOM.ordinal()] = 1442;
            } catch (NoSuchFieldError nosuchfielderror723) {
                ;
            }

            try {
                aint1[Material.LEGACY_BROWN_SHULKER_BOX.ordinal()] = 1634;
            } catch (NoSuchFieldError nosuchfielderror724) {
                ;
            }

            try {
                aint1[Material.LEGACY_BUCKET.ordinal()] = 1726;
            } catch (NoSuchFieldError nosuchfielderror725) {
                ;
            }

            try {
                aint1[Material.LEGACY_BURNING_FURNACE.ordinal()] = 1465;
            } catch (NoSuchFieldError nosuchfielderror726) {
                ;
            }

            try {
                aint1[Material.LEGACY_CACTUS.ordinal()] = 1484;
            } catch (NoSuchFieldError nosuchfielderror727) {
                ;
            }

            try {
                aint1[Material.LEGACY_CAKE.ordinal()] = 1755;
            } catch (NoSuchFieldError nosuchfielderror728) {
                ;
            }

            try {
                aint1[Material.LEGACY_CAKE_BLOCK.ordinal()] = 1495;
            } catch (NoSuchFieldError nosuchfielderror729) {
                ;
            }

            try {
                aint1[Material.LEGACY_CARPET.ordinal()] = 1574;
            } catch (NoSuchFieldError nosuchfielderror730) {
                ;
            }

            try {
                aint1[Material.LEGACY_CARROT.ordinal()] = 1544;
            } catch (NoSuchFieldError nosuchfielderror731) {
                ;
            }

            try {
                aint1[Material.LEGACY_CARROT_ITEM.ordinal()] = 1792;
            } catch (NoSuchFieldError nosuchfielderror732) {
                ;
            }

            try {
                aint1[Material.LEGACY_CARROT_STICK.ordinal()] = 1799;
            } catch (NoSuchFieldError nosuchfielderror733) {
                ;
            }

            try {
                aint1[Material.LEGACY_CAULDRON.ordinal()] = 1521;
            } catch (NoSuchFieldError nosuchfielderror734) {
                ;
            }

            try {
                aint1[Material.LEGACY_CAULDRON_ITEM.ordinal()] = 1781;
            } catch (NoSuchFieldError nosuchfielderror735) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHAINMAIL_BOOTS.ordinal()] = 1706;
            } catch (NoSuchFieldError nosuchfielderror736) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHAINMAIL_CHESTPLATE.ordinal()] = 1704;
            } catch (NoSuchFieldError nosuchfielderror737) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHAINMAIL_HELMET.ordinal()] = 1703;
            } catch (NoSuchFieldError nosuchfielderror738) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHAINMAIL_LEGGINGS.ordinal()] = 1705;
            } catch (NoSuchFieldError nosuchfielderror739) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHEST.ordinal()] = 1457;
            } catch (NoSuchFieldError nosuchfielderror740) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHORUS_FLOWER.ordinal()] = 1603;
            } catch (NoSuchFieldError nosuchfielderror741) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHORUS_FRUIT.ordinal()] = 1833;
            } catch (NoSuchFieldError nosuchfielderror742) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHORUS_FRUIT_POPPED.ordinal()] = 1834;
            } catch (NoSuchFieldError nosuchfielderror743) {
                ;
            }

            try {
                aint1[Material.LEGACY_CHORUS_PLANT.ordinal()] = 1602;
            } catch (NoSuchFieldError nosuchfielderror744) {
                ;
            }

            try {
                aint1[Material.LEGACY_CLAY.ordinal()] = 1485;
            } catch (NoSuchFieldError nosuchfielderror745) {
                ;
            }

            try {
                aint1[Material.LEGACY_CLAY_BALL.ordinal()] = 1738;
            } catch (NoSuchFieldError nosuchfielderror746) {
                ;
            }

            try {
                aint1[Material.LEGACY_CLAY_BRICK.ordinal()] = 1737;
            } catch (NoSuchFieldError nosuchfielderror747) {
                ;
            }

            try {
                aint1[Material.LEGACY_COAL.ordinal()] = 1664;
            } catch (NoSuchFieldError nosuchfielderror748) {
                ;
            }

            try {
                aint1[Material.LEGACY_COAL_BLOCK.ordinal()] = 1576;
            } catch (NoSuchFieldError nosuchfielderror749) {
                ;
            }

            try {
                aint1[Material.LEGACY_COAL_ORE.ordinal()] = 1419;
            } catch (NoSuchFieldError nosuchfielderror750) {
                ;
            }

            try {
                aint1[Material.LEGACY_COBBLESTONE.ordinal()] = 1407;
            } catch (NoSuchFieldError nosuchfielderror751) {
                ;
            }

            try {
                aint1[Material.LEGACY_COBBLESTONE_STAIRS.ordinal()] = 1470;
            } catch (NoSuchFieldError nosuchfielderror752) {
                ;
            }

            try {
                aint1[Material.LEGACY_COBBLE_WALL.ordinal()] = 1542;
            } catch (NoSuchFieldError nosuchfielderror753) {
                ;
            }

            try {
                aint1[Material.LEGACY_COCOA.ordinal()] = 1530;
            } catch (NoSuchFieldError nosuchfielderror754) {
                ;
            }

            try {
                aint1[Material.LEGACY_COMMAND.ordinal()] = 1540;
            } catch (NoSuchFieldError nosuchfielderror755) {
                ;
            }

            try {
                aint1[Material.LEGACY_COMMAND_CHAIN.ordinal()] = 1614;
            } catch (NoSuchFieldError nosuchfielderror756) {
                ;
            }

            try {
                aint1[Material.LEGACY_COMMAND_MINECART.ordinal()] = 1823;
            } catch (NoSuchFieldError nosuchfielderror757) {
                ;
            }

            try {
                aint1[Material.LEGACY_COMMAND_REPEATING.ordinal()] = 1613;
            } catch (NoSuchFieldError nosuchfielderror758) {
                ;
            }

            try {
                aint1[Material.LEGACY_COMPASS.ordinal()] = 1746;
            } catch (NoSuchFieldError nosuchfielderror759) {
                ;
            }

            try {
                aint1[Material.LEGACY_CONCRETE.ordinal()] = 1654;
            } catch (NoSuchFieldError nosuchfielderror760) {
                ;
            }

            try {
                aint1[Material.LEGACY_CONCRETE_POWDER.ordinal()] = 1655;
            } catch (NoSuchFieldError nosuchfielderror761) {
                ;
            }

            try {
                aint1[Material.LEGACY_COOKED_BEEF.ordinal()] = 1765;
            } catch (NoSuchFieldError nosuchfielderror762) {
                ;
            }

            try {
                aint1[Material.LEGACY_COOKED_CHICKEN.ordinal()] = 1767;
            } catch (NoSuchFieldError nosuchfielderror763) {
                ;
            }

            try {
                aint1[Material.LEGACY_COOKED_FISH.ordinal()] = 1751;
            } catch (NoSuchFieldError nosuchfielderror764) {
                ;
            }

            try {
                aint1[Material.LEGACY_COOKED_MUTTON.ordinal()] = 1825;
            } catch (NoSuchFieldError nosuchfielderror765) {
                ;
            }

            try {
                aint1[Material.LEGACY_COOKED_RABBIT.ordinal()] = 1813;
            } catch (NoSuchFieldError nosuchfielderror766) {
                ;
            }

            try {
                aint1[Material.LEGACY_COOKIE.ordinal()] = 1758;
            } catch (NoSuchFieldError nosuchfielderror767) {
                ;
            }

            try {
                aint1[Material.LEGACY_CROPS.ordinal()] = 1462;
            } catch (NoSuchFieldError nosuchfielderror768) {
                ;
            }

            try {
                aint1[Material.LEGACY_CYAN_GLAZED_TERRACOTTA.ordinal()] = 1647;
            } catch (NoSuchFieldError nosuchfielderror769) {
                ;
            }

            try {
                aint1[Material.LEGACY_CYAN_SHULKER_BOX.ordinal()] = 1631;
            } catch (NoSuchFieldError nosuchfielderror770) {
                ;
            }

            try {
                aint1[Material.LEGACY_DARK_OAK_DOOR.ordinal()] = 1600;
            } catch (NoSuchFieldError nosuchfielderror771) {
                ;
            }

            try {
                aint1[Material.LEGACY_DARK_OAK_DOOR_ITEM.ordinal()] = 1832;
            } catch (NoSuchFieldError nosuchfielderror772) {
                ;
            }

            try {
                aint1[Material.LEGACY_DARK_OAK_FENCE.ordinal()] = 1594;
            } catch (NoSuchFieldError nosuchfielderror773) {
                ;
            }

            try {
                aint1[Material.LEGACY_DARK_OAK_FENCE_GATE.ordinal()] = 1589;
            } catch (NoSuchFieldError nosuchfielderror774) {
                ;
            }

            try {
                aint1[Material.LEGACY_DARK_OAK_STAIRS.ordinal()] = 1567;
            } catch (NoSuchFieldError nosuchfielderror775) {
                ;
            }

            try {
                aint1[Material.LEGACY_DAYLIGHT_DETECTOR.ordinal()] = 1554;
            } catch (NoSuchFieldError nosuchfielderror776) {
                ;
            }

            try {
                aint1[Material.LEGACY_DAYLIGHT_DETECTOR_INVERTED.ordinal()] = 1581;
            } catch (NoSuchFieldError nosuchfielderror777) {
                ;
            }

            try {
                aint1[Material.LEGACY_DEAD_BUSH.ordinal()] = 1435;
            } catch (NoSuchFieldError nosuchfielderror778) {
                ;
            }

            try {
                aint1[Material.LEGACY_DETECTOR_RAIL.ordinal()] = 1431;
            } catch (NoSuchFieldError nosuchfielderror779) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND.ordinal()] = 1665;
            } catch (NoSuchFieldError nosuchfielderror780) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_AXE.ordinal()] = 1680;
            } catch (NoSuchFieldError nosuchfielderror781) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_BARDING.ordinal()] = 1820;
            } catch (NoSuchFieldError nosuchfielderror782) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_BLOCK.ordinal()] = 1460;
            } catch (NoSuchFieldError nosuchfielderror783) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_BOOTS.ordinal()] = 1714;
            } catch (NoSuchFieldError nosuchfielderror784) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_CHESTPLATE.ordinal()] = 1712;
            } catch (NoSuchFieldError nosuchfielderror785) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_HELMET.ordinal()] = 1711;
            } catch (NoSuchFieldError nosuchfielderror786) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_HOE.ordinal()] = 1694;
            } catch (NoSuchFieldError nosuchfielderror787) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_LEGGINGS.ordinal()] = 1713;
            } catch (NoSuchFieldError nosuchfielderror788) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_ORE.ordinal()] = 1459;
            } catch (NoSuchFieldError nosuchfielderror789) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_PICKAXE.ordinal()] = 1679;
            } catch (NoSuchFieldError nosuchfielderror790) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_SPADE.ordinal()] = 1678;
            } catch (NoSuchFieldError nosuchfielderror791) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIAMOND_SWORD.ordinal()] = 1677;
            } catch (NoSuchFieldError nosuchfielderror792) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIODE.ordinal()] = 1757;
            } catch (NoSuchFieldError nosuchfielderror793) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIODE_BLOCK_OFF.ordinal()] = 1496;
            } catch (NoSuchFieldError nosuchfielderror794) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIODE_BLOCK_ON.ordinal()] = 1497;
            } catch (NoSuchFieldError nosuchfielderror795) {
                ;
            }

            try {
                aint1[Material.LEGACY_DIRT.ordinal()] = 1406;
            } catch (NoSuchFieldError nosuchfielderror796) {
                ;
            }

            try {
                aint1[Material.LEGACY_DISPENSER.ordinal()] = 1426;
            } catch (NoSuchFieldError nosuchfielderror797) {
                ;
            }

            try {
                aint1[Material.LEGACY_DOUBLE_PLANT.ordinal()] = 1578;
            } catch (NoSuchFieldError nosuchfielderror798) {
                ;
            }

            try {
                aint1[Material.LEGACY_DOUBLE_STEP.ordinal()] = 1446;
            } catch (NoSuchFieldError nosuchfielderror799) {
                ;
            }

            try {
                aint1[Material.LEGACY_DOUBLE_STONE_SLAB2.ordinal()] = 1584;
            } catch (NoSuchFieldError nosuchfielderror800) {
                ;
            }

            try {
                aint1[Material.LEGACY_DRAGONS_BREATH.ordinal()] = 1838;
            } catch (NoSuchFieldError nosuchfielderror801) {
                ;
            }

            try {
                aint1[Material.LEGACY_DRAGON_EGG.ordinal()] = 1525;
            } catch (NoSuchFieldError nosuchfielderror802) {
                ;
            }

            try {
                aint1[Material.LEGACY_DROPPER.ordinal()] = 1561;
            } catch (NoSuchFieldError nosuchfielderror803) {
                ;
            }

            try {
                aint1[Material.LEGACY_EGG.ordinal()] = 1745;
            } catch (NoSuchFieldError nosuchfielderror804) {
                ;
            }

            try {
                aint1[Material.LEGACY_ELYTRA.ordinal()] = 1844;
            } catch (NoSuchFieldError nosuchfielderror805) {
                ;
            }

            try {
                aint1[Material.LEGACY_EMERALD.ordinal()] = 1789;
            } catch (NoSuchFieldError nosuchfielderror806) {
                ;
            }

            try {
                aint1[Material.LEGACY_EMERALD_BLOCK.ordinal()] = 1536;
            } catch (NoSuchFieldError nosuchfielderror807) {
                ;
            }

            try {
                aint1[Material.LEGACY_EMERALD_ORE.ordinal()] = 1532;
            } catch (NoSuchFieldError nosuchfielderror808) {
                ;
            }

            try {
                aint1[Material.LEGACY_EMPTY_MAP.ordinal()] = 1796;
            } catch (NoSuchFieldError nosuchfielderror809) {
                ;
            }

            try {
                aint1[Material.LEGACY_ENCHANTED_BOOK.ordinal()] = 1804;
            } catch (NoSuchFieldError nosuchfielderror810) {
                ;
            }

            try {
                aint1[Material.LEGACY_ENCHANTMENT_TABLE.ordinal()] = 1519;
            } catch (NoSuchFieldError nosuchfielderror811) {
                ;
            }

            try {
                aint1[Material.LEGACY_ENDER_CHEST.ordinal()] = 1533;
            } catch (NoSuchFieldError nosuchfielderror812) {
                ;
            }

            try {
                aint1[Material.LEGACY_ENDER_PEARL.ordinal()] = 1769;
            } catch (NoSuchFieldError nosuchfielderror813) {
                ;
            }

            try {
                aint1[Material.LEGACY_ENDER_PORTAL.ordinal()] = 1522;
            } catch (NoSuchFieldError nosuchfielderror814) {
                ;
            }

            try {
                aint1[Material.LEGACY_ENDER_PORTAL_FRAME.ordinal()] = 1523;
            } catch (NoSuchFieldError nosuchfielderror815) {
                ;
            }

            try {
                aint1[Material.LEGACY_ENDER_STONE.ordinal()] = 1524;
            } catch (NoSuchFieldError nosuchfielderror816) {
                ;
            }

            try {
                aint1[Material.LEGACY_END_BRICKS.ordinal()] = 1609;
            } catch (NoSuchFieldError nosuchfielderror817) {
                ;
            }

            try {
                aint1[Material.LEGACY_END_CRYSTAL.ordinal()] = 1827;
            } catch (NoSuchFieldError nosuchfielderror818) {
                ;
            }

            try {
                aint1[Material.LEGACY_END_GATEWAY.ordinal()] = 1612;
            } catch (NoSuchFieldError nosuchfielderror819) {
                ;
            }

            try {
                aint1[Material.LEGACY_END_ROD.ordinal()] = 1601;
            } catch (NoSuchFieldError nosuchfielderror820) {
                ;
            }

            try {
                aint1[Material.LEGACY_EXPLOSIVE_MINECART.ordinal()] = 1808;
            } catch (NoSuchFieldError nosuchfielderror821) {
                ;
            }

            try {
                aint1[Material.LEGACY_EXP_BOTTLE.ordinal()] = 1785;
            } catch (NoSuchFieldError nosuchfielderror822) {
                ;
            }

            try {
                aint1[Material.LEGACY_EYE_OF_ENDER.ordinal()] = 1782;
            } catch (NoSuchFieldError nosuchfielderror823) {
                ;
            }

            try {
                aint1[Material.LEGACY_FEATHER.ordinal()] = 1689;
            } catch (NoSuchFieldError nosuchfielderror824) {
                ;
            }

            try {
                aint1[Material.LEGACY_FENCE.ordinal()] = 1488;
            } catch (NoSuchFieldError nosuchfielderror825) {
                ;
            }

            try {
                aint1[Material.LEGACY_FENCE_GATE.ordinal()] = 1510;
            } catch (NoSuchFieldError nosuchfielderror826) {
                ;
            }

            try {
                aint1[Material.LEGACY_FERMENTED_SPIDER_EYE.ordinal()] = 1777;
            } catch (NoSuchFieldError nosuchfielderror827) {
                ;
            }

            try {
                aint1[Material.LEGACY_FIRE.ordinal()] = 1454;
            } catch (NoSuchFieldError nosuchfielderror828) {
                ;
            }

            try {
                aint1[Material.LEGACY_FIREBALL.ordinal()] = 1786;
            } catch (NoSuchFieldError nosuchfielderror829) {
                ;
            }

            try {
                aint1[Material.LEGACY_FIREWORK.ordinal()] = 1802;
            } catch (NoSuchFieldError nosuchfielderror830) {
                ;
            }

            try {
                aint1[Material.LEGACY_FIREWORK_CHARGE.ordinal()] = 1803;
            } catch (NoSuchFieldError nosuchfielderror831) {
                ;
            }

            try {
                aint1[Material.LEGACY_FISHING_ROD.ordinal()] = 1747;
            } catch (NoSuchFieldError nosuchfielderror832) {
                ;
            }

            try {
                aint1[Material.LEGACY_FLINT.ordinal()] = 1719;
            } catch (NoSuchFieldError nosuchfielderror833) {
                ;
            }

            try {
                aint1[Material.LEGACY_FLINT_AND_STEEL.ordinal()] = 1660;
            } catch (NoSuchFieldError nosuchfielderror834) {
                ;
            }

            try {
                aint1[Material.LEGACY_FLOWER_POT.ordinal()] = 1543;
            } catch (NoSuchFieldError nosuchfielderror835) {
                ;
            }

            try {
                aint1[Material.LEGACY_FLOWER_POT_ITEM.ordinal()] = 1791;
            } catch (NoSuchFieldError nosuchfielderror836) {
                ;
            }

            try {
                aint1[Material.LEGACY_FROSTED_ICE.ordinal()] = 1615;
            } catch (NoSuchFieldError nosuchfielderror837) {
                ;
            }

            try {
                aint1[Material.LEGACY_FURNACE.ordinal()] = 1464;
            } catch (NoSuchFieldError nosuchfielderror838) {
                ;
            }

            try {
                aint1[Material.LEGACY_GHAST_TEAR.ordinal()] = 1771;
            } catch (NoSuchFieldError nosuchfielderror839) {
                ;
            }

            try {
                aint1[Material.LEGACY_GLASS.ordinal()] = 1423;
            } catch (NoSuchFieldError nosuchfielderror840) {
                ;
            }

            try {
                aint1[Material.LEGACY_GLASS_BOTTLE.ordinal()] = 1775;
            } catch (NoSuchFieldError nosuchfielderror841) {
                ;
            }

            try {
                aint1[Material.LEGACY_GLOWING_REDSTONE_ORE.ordinal()] = 1477;
            } catch (NoSuchFieldError nosuchfielderror842) {
                ;
            }

            try {
                aint1[Material.LEGACY_GLOWSTONE.ordinal()] = 1492;
            } catch (NoSuchFieldError nosuchfielderror843) {
                ;
            }

            try {
                aint1[Material.LEGACY_GLOWSTONE_DUST.ordinal()] = 1749;
            } catch (NoSuchFieldError nosuchfielderror844) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLDEN_APPLE.ordinal()] = 1723;
            } catch (NoSuchFieldError nosuchfielderror845) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLDEN_CARROT.ordinal()] = 1797;
            } catch (NoSuchFieldError nosuchfielderror846) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_AXE.ordinal()] = 1687;
            } catch (NoSuchFieldError nosuchfielderror847) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_BARDING.ordinal()] = 1819;
            } catch (NoSuchFieldError nosuchfielderror848) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_BLOCK.ordinal()] = 1444;
            } catch (NoSuchFieldError nosuchfielderror849) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_BOOTS.ordinal()] = 1718;
            } catch (NoSuchFieldError nosuchfielderror850) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_CHESTPLATE.ordinal()] = 1716;
            } catch (NoSuchFieldError nosuchfielderror851) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_HELMET.ordinal()] = 1715;
            } catch (NoSuchFieldError nosuchfielderror852) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_HOE.ordinal()] = 1695;
            } catch (NoSuchFieldError nosuchfielderror853) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_INGOT.ordinal()] = 1667;
            } catch (NoSuchFieldError nosuchfielderror854) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_LEGGINGS.ordinal()] = 1717;
            } catch (NoSuchFieldError nosuchfielderror855) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_NUGGET.ordinal()] = 1772;
            } catch (NoSuchFieldError nosuchfielderror856) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_ORE.ordinal()] = 1417;
            } catch (NoSuchFieldError nosuchfielderror857) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_PICKAXE.ordinal()] = 1686;
            } catch (NoSuchFieldError nosuchfielderror858) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_PLATE.ordinal()] = 1550;
            } catch (NoSuchFieldError nosuchfielderror859) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_RECORD.ordinal()] = 1854;
            } catch (NoSuchFieldError nosuchfielderror860) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_SPADE.ordinal()] = 1685;
            } catch (NoSuchFieldError nosuchfielderror861) {
                ;
            }

            try {
                aint1[Material.LEGACY_GOLD_SWORD.ordinal()] = 1684;
            } catch (NoSuchFieldError nosuchfielderror862) {
                ;
            }

            try {
                aint1[Material.LEGACY_GRASS.ordinal()] = 1405;
            } catch (NoSuchFieldError nosuchfielderror863) {
                ;
            }

            try {
                aint1[Material.LEGACY_GRASS_PATH.ordinal()] = 1611;
            } catch (NoSuchFieldError nosuchfielderror864) {
                ;
            }

            try {
                aint1[Material.LEGACY_GRAVEL.ordinal()] = 1416;
            } catch (NoSuchFieldError nosuchfielderror865) {
                ;
            }

            try {
                aint1[Material.LEGACY_GRAY_GLAZED_TERRACOTTA.ordinal()] = 1645;
            } catch (NoSuchFieldError nosuchfielderror866) {
                ;
            }

            try {
                aint1[Material.LEGACY_GRAY_SHULKER_BOX.ordinal()] = 1629;
            } catch (NoSuchFieldError nosuchfielderror867) {
                ;
            }

            try {
                aint1[Material.LEGACY_GREEN_GLAZED_TERRACOTTA.ordinal()] = 1651;
            } catch (NoSuchFieldError nosuchfielderror868) {
                ;
            }

            try {
                aint1[Material.LEGACY_GREEN_RECORD.ordinal()] = 1855;
            } catch (NoSuchFieldError nosuchfielderror869) {
                ;
            }

            try {
                aint1[Material.LEGACY_GREEN_SHULKER_BOX.ordinal()] = 1635;
            } catch (NoSuchFieldError nosuchfielderror870) {
                ;
            }

            try {
                aint1[Material.LEGACY_GRILLED_PORK.ordinal()] = 1721;
            } catch (NoSuchFieldError nosuchfielderror871) {
                ;
            }

            try {
                aint1[Material.LEGACY_HARD_CLAY.ordinal()] = 1575;
            } catch (NoSuchFieldError nosuchfielderror872) {
                ;
            }

            try {
                aint1[Material.LEGACY_HAY_BLOCK.ordinal()] = 1573;
            } catch (NoSuchFieldError nosuchfielderror873) {
                ;
            }

            try {
                aint1[Material.LEGACY_HOPPER.ordinal()] = 1557;
            } catch (NoSuchFieldError nosuchfielderror874) {
                ;
            }

            try {
                aint1[Material.LEGACY_HOPPER_MINECART.ordinal()] = 1809;
            } catch (NoSuchFieldError nosuchfielderror875) {
                ;
            }

            try {
                aint1[Material.LEGACY_HUGE_MUSHROOM_1.ordinal()] = 1502;
            } catch (NoSuchFieldError nosuchfielderror876) {
                ;
            }

            try {
                aint1[Material.LEGACY_HUGE_MUSHROOM_2.ordinal()] = 1503;
            } catch (NoSuchFieldError nosuchfielderror877) {
                ;
            }

            try {
                aint1[Material.LEGACY_ICE.ordinal()] = 1482;
            } catch (NoSuchFieldError nosuchfielderror878) {
                ;
            }

            try {
                aint1[Material.LEGACY_INK_SACK.ordinal()] = 1752;
            } catch (NoSuchFieldError nosuchfielderror879) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_AXE.ordinal()] = 1659;
            } catch (NoSuchFieldError nosuchfielderror880) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_BARDING.ordinal()] = 1818;
            } catch (NoSuchFieldError nosuchfielderror881) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_BLOCK.ordinal()] = 1445;
            } catch (NoSuchFieldError nosuchfielderror882) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_BOOTS.ordinal()] = 1710;
            } catch (NoSuchFieldError nosuchfielderror883) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_CHESTPLATE.ordinal()] = 1708;
            } catch (NoSuchFieldError nosuchfielderror884) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_DOOR.ordinal()] = 1731;
            } catch (NoSuchFieldError nosuchfielderror885) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_DOOR_BLOCK.ordinal()] = 1474;
            } catch (NoSuchFieldError nosuchfielderror886) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_FENCE.ordinal()] = 1504;
            } catch (NoSuchFieldError nosuchfielderror887) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_HELMET.ordinal()] = 1707;
            } catch (NoSuchFieldError nosuchfielderror888) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_HOE.ordinal()] = 1693;
            } catch (NoSuchFieldError nosuchfielderror889) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_INGOT.ordinal()] = 1666;
            } catch (NoSuchFieldError nosuchfielderror890) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_LEGGINGS.ordinal()] = 1709;
            } catch (NoSuchFieldError nosuchfielderror891) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_NUGGET.ordinal()] = 1852;
            } catch (NoSuchFieldError nosuchfielderror892) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_ORE.ordinal()] = 1418;
            } catch (NoSuchFieldError nosuchfielderror893) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_PICKAXE.ordinal()] = 1658;
            } catch (NoSuchFieldError nosuchfielderror894) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_PLATE.ordinal()] = 1551;
            } catch (NoSuchFieldError nosuchfielderror895) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_SPADE.ordinal()] = 1657;
            } catch (NoSuchFieldError nosuchfielderror896) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_SWORD.ordinal()] = 1668;
            } catch (NoSuchFieldError nosuchfielderror897) {
                ;
            }

            try {
                aint1[Material.LEGACY_IRON_TRAPDOOR.ordinal()] = 1570;
            } catch (NoSuchFieldError nosuchfielderror898) {
                ;
            }

            try {
                aint1[Material.LEGACY_ITEM_FRAME.ordinal()] = 1790;
            } catch (NoSuchFieldError nosuchfielderror899) {
                ;
            }

            try {
                aint1[Material.LEGACY_JACK_O_LANTERN.ordinal()] = 1494;
            } catch (NoSuchFieldError nosuchfielderror900) {
                ;
            }

            try {
                aint1[Material.LEGACY_JUKEBOX.ordinal()] = 1487;
            } catch (NoSuchFieldError nosuchfielderror901) {
                ;
            }

            try {
                aint1[Material.LEGACY_JUNGLE_DOOR.ordinal()] = 1598;
            } catch (NoSuchFieldError nosuchfielderror902) {
                ;
            }

            try {
                aint1[Material.LEGACY_JUNGLE_DOOR_ITEM.ordinal()] = 1830;
            } catch (NoSuchFieldError nosuchfielderror903) {
                ;
            }

            try {
                aint1[Material.LEGACY_JUNGLE_FENCE.ordinal()] = 1593;
            } catch (NoSuchFieldError nosuchfielderror904) {
                ;
            }

            try {
                aint1[Material.LEGACY_JUNGLE_FENCE_GATE.ordinal()] = 1588;
            } catch (NoSuchFieldError nosuchfielderror905) {
                ;
            }

            try {
                aint1[Material.LEGACY_JUNGLE_WOOD_STAIRS.ordinal()] = 1539;
            } catch (NoSuchFieldError nosuchfielderror906) {
                ;
            }

            try {
                aint1[Material.LEGACY_KNOWLEDGE_BOOK.ordinal()] = 1853;
            } catch (NoSuchFieldError nosuchfielderror907) {
                ;
            }

            try {
                aint1[Material.LEGACY_LADDER.ordinal()] = 1468;
            } catch (NoSuchFieldError nosuchfielderror908) {
                ;
            }

            try {
                aint1[Material.LEGACY_LAPIS_BLOCK.ordinal()] = 1425;
            } catch (NoSuchFieldError nosuchfielderror909) {
                ;
            }

            try {
                aint1[Material.LEGACY_LAPIS_ORE.ordinal()] = 1424;
            } catch (NoSuchFieldError nosuchfielderror910) {
                ;
            }

            try {
                aint1[Material.LEGACY_LAVA.ordinal()] = 1413;
            } catch (NoSuchFieldError nosuchfielderror911) {
                ;
            }

            try {
                aint1[Material.LEGACY_LAVA_BUCKET.ordinal()] = 1728;
            } catch (NoSuchFieldError nosuchfielderror912) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEASH.ordinal()] = 1821;
            } catch (NoSuchFieldError nosuchfielderror913) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEATHER.ordinal()] = 1735;
            } catch (NoSuchFieldError nosuchfielderror914) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEATHER_BOOTS.ordinal()] = 1702;
            } catch (NoSuchFieldError nosuchfielderror915) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEATHER_CHESTPLATE.ordinal()] = 1700;
            } catch (NoSuchFieldError nosuchfielderror916) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEATHER_HELMET.ordinal()] = 1699;
            } catch (NoSuchFieldError nosuchfielderror917) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEATHER_LEGGINGS.ordinal()] = 1701;
            } catch (NoSuchFieldError nosuchfielderror918) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEAVES.ordinal()] = 1421;
            } catch (NoSuchFieldError nosuchfielderror919) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEAVES_2.ordinal()] = 1564;
            } catch (NoSuchFieldError nosuchfielderror920) {
                ;
            }

            try {
                aint1[Material.LEGACY_LEVER.ordinal()] = 1472;
            } catch (NoSuchFieldError nosuchfielderror921) {
                ;
            }

            try {
                aint1[Material.LEGACY_LIGHT_BLUE_GLAZED_TERRACOTTA.ordinal()] = 1641;
            } catch (NoSuchFieldError nosuchfielderror922) {
                ;
            }

            try {
                aint1[Material.LEGACY_LIGHT_BLUE_SHULKER_BOX.ordinal()] = 1625;
            } catch (NoSuchFieldError nosuchfielderror923) {
                ;
            }

            try {
                aint1[Material.LEGACY_LIME_GLAZED_TERRACOTTA.ordinal()] = 1643;
            } catch (NoSuchFieldError nosuchfielderror924) {
                ;
            }

            try {
                aint1[Material.LEGACY_LIME_SHULKER_BOX.ordinal()] = 1627;
            } catch (NoSuchFieldError nosuchfielderror925) {
                ;
            }

            try {
                aint1[Material.LEGACY_LINGERING_POTION.ordinal()] = 1842;
            } catch (NoSuchFieldError nosuchfielderror926) {
                ;
            }

            try {
                aint1[Material.LEGACY_LOG.ordinal()] = 1420;
            } catch (NoSuchFieldError nosuchfielderror927) {
                ;
            }

            try {
                aint1[Material.LEGACY_LOG_2.ordinal()] = 1565;
            } catch (NoSuchFieldError nosuchfielderror928) {
                ;
            }

            try {
                aint1[Material.LEGACY_LONG_GRASS.ordinal()] = 1434;
            } catch (NoSuchFieldError nosuchfielderror929) {
                ;
            }

            try {
                aint1[Material.LEGACY_MAGENTA_GLAZED_TERRACOTTA.ordinal()] = 1640;
            } catch (NoSuchFieldError nosuchfielderror930) {
                ;
            }

            try {
                aint1[Material.LEGACY_MAGENTA_SHULKER_BOX.ordinal()] = 1624;
            } catch (NoSuchFieldError nosuchfielderror931) {
                ;
            }

            try {
                aint1[Material.LEGACY_MAGMA.ordinal()] = 1616;
            } catch (NoSuchFieldError nosuchfielderror932) {
                ;
            }

            try {
                aint1[Material.LEGACY_MAGMA_CREAM.ordinal()] = 1779;
            } catch (NoSuchFieldError nosuchfielderror933) {
                ;
            }

            try {
                aint1[Material.LEGACY_MAP.ordinal()] = 1759;
            } catch (NoSuchFieldError nosuchfielderror934) {
                ;
            }

            try {
                aint1[Material.LEGACY_MELON.ordinal()] = 1761;
            } catch (NoSuchFieldError nosuchfielderror935) {
                ;
            }

            try {
                aint1[Material.LEGACY_MELON_BLOCK.ordinal()] = 1506;
            } catch (NoSuchFieldError nosuchfielderror936) {
                ;
            }

            try {
                aint1[Material.LEGACY_MELON_SEEDS.ordinal()] = 1763;
            } catch (NoSuchFieldError nosuchfielderror937) {
                ;
            }

            try {
                aint1[Material.LEGACY_MELON_STEM.ordinal()] = 1508;
            } catch (NoSuchFieldError nosuchfielderror938) {
                ;
            }

            try {
                aint1[Material.LEGACY_MILK_BUCKET.ordinal()] = 1736;
            } catch (NoSuchFieldError nosuchfielderror939) {
                ;
            }

            try {
                aint1[Material.LEGACY_MINECART.ordinal()] = 1729;
            } catch (NoSuchFieldError nosuchfielderror940) {
                ;
            }

            try {
                aint1[Material.LEGACY_MOB_SPAWNER.ordinal()] = 1455;
            } catch (NoSuchFieldError nosuchfielderror941) {
                ;
            }

            try {
                aint1[Material.LEGACY_MONSTER_EGG.ordinal()] = 1784;
            } catch (NoSuchFieldError nosuchfielderror942) {
                ;
            }

            try {
                aint1[Material.LEGACY_MONSTER_EGGS.ordinal()] = 1500;
            } catch (NoSuchFieldError nosuchfielderror943) {
                ;
            }

            try {
                aint1[Material.LEGACY_MOSSY_COBBLESTONE.ordinal()] = 1451;
            } catch (NoSuchFieldError nosuchfielderror944) {
                ;
            }

            try {
                aint1[Material.LEGACY_MUSHROOM_SOUP.ordinal()] = 1683;
            } catch (NoSuchFieldError nosuchfielderror945) {
                ;
            }

            try {
                aint1[Material.LEGACY_MUTTON.ordinal()] = 1824;
            } catch (NoSuchFieldError nosuchfielderror946) {
                ;
            }

            try {
                aint1[Material.LEGACY_MYCEL.ordinal()] = 1513;
            } catch (NoSuchFieldError nosuchfielderror947) {
                ;
            }

            try {
                aint1[Material.LEGACY_NAME_TAG.ordinal()] = 1822;
            } catch (NoSuchFieldError nosuchfielderror948) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHERRACK.ordinal()] = 1490;
            } catch (NoSuchFieldError nosuchfielderror949) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_BRICK.ordinal()] = 1515;
            } catch (NoSuchFieldError nosuchfielderror950) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_BRICK_ITEM.ordinal()] = 1806;
            } catch (NoSuchFieldError nosuchfielderror951) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_BRICK_STAIRS.ordinal()] = 1517;
            } catch (NoSuchFieldError nosuchfielderror952) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_FENCE.ordinal()] = 1516;
            } catch (NoSuchFieldError nosuchfielderror953) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_STALK.ordinal()] = 1773;
            } catch (NoSuchFieldError nosuchfielderror954) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_STAR.ordinal()] = 1800;
            } catch (NoSuchFieldError nosuchfielderror955) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_WARTS.ordinal()] = 1518;
            } catch (NoSuchFieldError nosuchfielderror956) {
                ;
            }

            try {
                aint1[Material.LEGACY_NETHER_WART_BLOCK.ordinal()] = 1617;
            } catch (NoSuchFieldError nosuchfielderror957) {
                ;
            }

            try {
                aint1[Material.LEGACY_NOTE_BLOCK.ordinal()] = 1428;
            } catch (NoSuchFieldError nosuchfielderror958) {
                ;
            }

            try {
                aint1[Material.LEGACY_OBSERVER.ordinal()] = 1621;
            } catch (NoSuchFieldError nosuchfielderror959) {
                ;
            }

            try {
                aint1[Material.LEGACY_OBSIDIAN.ordinal()] = 1452;
            } catch (NoSuchFieldError nosuchfielderror960) {
                ;
            }

            try {
                aint1[Material.LEGACY_ORANGE_GLAZED_TERRACOTTA.ordinal()] = 1639;
            } catch (NoSuchFieldError nosuchfielderror961) {
                ;
            }

            try {
                aint1[Material.LEGACY_ORANGE_SHULKER_BOX.ordinal()] = 1623;
            } catch (NoSuchFieldError nosuchfielderror962) {
                ;
            }

            try {
                aint1[Material.LEGACY_PACKED_ICE.ordinal()] = 1577;
            } catch (NoSuchFieldError nosuchfielderror963) {
                ;
            }

            try {
                aint1[Material.LEGACY_PAINTING.ordinal()] = 1722;
            } catch (NoSuchFieldError nosuchfielderror964) {
                ;
            }

            try {
                aint1[Material.LEGACY_PAPER.ordinal()] = 1740;
            } catch (NoSuchFieldError nosuchfielderror965) {
                ;
            }

            try {
                aint1[Material.LEGACY_PINK_GLAZED_TERRACOTTA.ordinal()] = 1644;
            } catch (NoSuchFieldError nosuchfielderror966) {
                ;
            }

            try {
                aint1[Material.LEGACY_PINK_SHULKER_BOX.ordinal()] = 1628;
            } catch (NoSuchFieldError nosuchfielderror967) {
                ;
            }

            try {
                aint1[Material.LEGACY_PISTON_BASE.ordinal()] = 1436;
            } catch (NoSuchFieldError nosuchfielderror968) {
                ;
            }

            try {
                aint1[Material.LEGACY_PISTON_EXTENSION.ordinal()] = 1437;
            } catch (NoSuchFieldError nosuchfielderror969) {
                ;
            }

            try {
                aint1[Material.LEGACY_PISTON_MOVING_PIECE.ordinal()] = 1439;
            } catch (NoSuchFieldError nosuchfielderror970) {
                ;
            }

            try {
                aint1[Material.LEGACY_PISTON_STICKY_BASE.ordinal()] = 1432;
            } catch (NoSuchFieldError nosuchfielderror971) {
                ;
            }

            try {
                aint1[Material.LEGACY_POISONOUS_POTATO.ordinal()] = 1795;
            } catch (NoSuchFieldError nosuchfielderror972) {
                ;
            }

            try {
                aint1[Material.LEGACY_PORK.ordinal()] = 1720;
            } catch (NoSuchFieldError nosuchfielderror973) {
                ;
            }

            try {
                aint1[Material.LEGACY_PORTAL.ordinal()] = 1493;
            } catch (NoSuchFieldError nosuchfielderror974) {
                ;
            }

            try {
                aint1[Material.LEGACY_POTATO.ordinal()] = 1545;
            } catch (NoSuchFieldError nosuchfielderror975) {
                ;
            }

            try {
                aint1[Material.LEGACY_POTATO_ITEM.ordinal()] = 1793;
            } catch (NoSuchFieldError nosuchfielderror976) {
                ;
            }

            try {
                aint1[Material.LEGACY_POTION.ordinal()] = 1774;
            } catch (NoSuchFieldError nosuchfielderror977) {
                ;
            }

            try {
                aint1[Material.LEGACY_POWERED_MINECART.ordinal()] = 1744;
            } catch (NoSuchFieldError nosuchfielderror978) {
                ;
            }

            try {
                aint1[Material.LEGACY_POWERED_RAIL.ordinal()] = 1430;
            } catch (NoSuchFieldError nosuchfielderror979) {
                ;
            }

            try {
                aint1[Material.LEGACY_PRISMARINE.ordinal()] = 1571;
            } catch (NoSuchFieldError nosuchfielderror980) {
                ;
            }

            try {
                aint1[Material.LEGACY_PRISMARINE_CRYSTALS.ordinal()] = 1811;
            } catch (NoSuchFieldError nosuchfielderror981) {
                ;
            }

            try {
                aint1[Material.LEGACY_PRISMARINE_SHARD.ordinal()] = 1810;
            } catch (NoSuchFieldError nosuchfielderror982) {
                ;
            }

            try {
                aint1[Material.LEGACY_PUMPKIN.ordinal()] = 1489;
            } catch (NoSuchFieldError nosuchfielderror983) {
                ;
            }

            try {
                aint1[Material.LEGACY_PUMPKIN_PIE.ordinal()] = 1801;
            } catch (NoSuchFieldError nosuchfielderror984) {
                ;
            }

            try {
                aint1[Material.LEGACY_PUMPKIN_SEEDS.ordinal()] = 1762;
            } catch (NoSuchFieldError nosuchfielderror985) {
                ;
            }

            try {
                aint1[Material.LEGACY_PUMPKIN_STEM.ordinal()] = 1507;
            } catch (NoSuchFieldError nosuchfielderror986) {
                ;
            }

            try {
                aint1[Material.LEGACY_PURPLE_GLAZED_TERRACOTTA.ordinal()] = 1648;
            } catch (NoSuchFieldError nosuchfielderror987) {
                ;
            }

            try {
                aint1[Material.LEGACY_PURPLE_SHULKER_BOX.ordinal()] = 1632;
            } catch (NoSuchFieldError nosuchfielderror988) {
                ;
            }

            try {
                aint1[Material.LEGACY_PURPUR_BLOCK.ordinal()] = 1604;
            } catch (NoSuchFieldError nosuchfielderror989) {
                ;
            }

            try {
                aint1[Material.LEGACY_PURPUR_DOUBLE_SLAB.ordinal()] = 1607;
            } catch (NoSuchFieldError nosuchfielderror990) {
                ;
            }

            try {
                aint1[Material.LEGACY_PURPUR_PILLAR.ordinal()] = 1605;
            } catch (NoSuchFieldError nosuchfielderror991) {
                ;
            }

            try {
                aint1[Material.LEGACY_PURPUR_SLAB.ordinal()] = 1608;
            } catch (NoSuchFieldError nosuchfielderror992) {
                ;
            }

            try {
                aint1[Material.LEGACY_PURPUR_STAIRS.ordinal()] = 1606;
            } catch (NoSuchFieldError nosuchfielderror993) {
                ;
            }

            try {
                aint1[Material.LEGACY_QUARTZ.ordinal()] = 1807;
            } catch (NoSuchFieldError nosuchfielderror994) {
                ;
            }

            try {
                aint1[Material.LEGACY_QUARTZ_BLOCK.ordinal()] = 1558;
            } catch (NoSuchFieldError nosuchfielderror995) {
                ;
            }

            try {
                aint1[Material.LEGACY_QUARTZ_ORE.ordinal()] = 1556;
            } catch (NoSuchFieldError nosuchfielderror996) {
                ;
            }

            try {
                aint1[Material.LEGACY_QUARTZ_STAIRS.ordinal()] = 1559;
            } catch (NoSuchFieldError nosuchfielderror997) {
                ;
            }

            try {
                aint1[Material.LEGACY_RABBIT.ordinal()] = 1812;
            } catch (NoSuchFieldError nosuchfielderror998) {
                ;
            }

            try {
                aint1[Material.LEGACY_RABBIT_FOOT.ordinal()] = 1815;
            } catch (NoSuchFieldError nosuchfielderror999) {
                ;
            }

            try {
                aint1[Material.LEGACY_RABBIT_HIDE.ordinal()] = 1816;
            } catch (NoSuchFieldError nosuchfielderror1000) {
                ;
            }

            try {
                aint1[Material.LEGACY_RABBIT_STEW.ordinal()] = 1814;
            } catch (NoSuchFieldError nosuchfielderror1001) {
                ;
            }

            try {
                aint1[Material.LEGACY_RAILS.ordinal()] = 1469;
            } catch (NoSuchFieldError nosuchfielderror1002) {
                ;
            }

            try {
                aint1[Material.LEGACY_RAW_BEEF.ordinal()] = 1764;
            } catch (NoSuchFieldError nosuchfielderror1003) {
                ;
            }

            try {
                aint1[Material.LEGACY_RAW_CHICKEN.ordinal()] = 1766;
            } catch (NoSuchFieldError nosuchfielderror1004) {
                ;
            }

            try {
                aint1[Material.LEGACY_RAW_FISH.ordinal()] = 1750;
            } catch (NoSuchFieldError nosuchfielderror1005) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_10.ordinal()] = 1863;
            } catch (NoSuchFieldError nosuchfielderror1006) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_11.ordinal()] = 1864;
            } catch (NoSuchFieldError nosuchfielderror1007) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_12.ordinal()] = 1865;
            } catch (NoSuchFieldError nosuchfielderror1008) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_3.ordinal()] = 1856;
            } catch (NoSuchFieldError nosuchfielderror1009) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_4.ordinal()] = 1857;
            } catch (NoSuchFieldError nosuchfielderror1010) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_5.ordinal()] = 1858;
            } catch (NoSuchFieldError nosuchfielderror1011) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_6.ordinal()] = 1859;
            } catch (NoSuchFieldError nosuchfielderror1012) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_7.ordinal()] = 1860;
            } catch (NoSuchFieldError nosuchfielderror1013) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_8.ordinal()] = 1861;
            } catch (NoSuchFieldError nosuchfielderror1014) {
                ;
            }

            try {
                aint1[Material.LEGACY_RECORD_9.ordinal()] = 1862;
            } catch (NoSuchFieldError nosuchfielderror1015) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE.ordinal()] = 1732;
            } catch (NoSuchFieldError nosuchfielderror1016) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_BLOCK.ordinal()] = 1555;
            } catch (NoSuchFieldError nosuchfielderror1017) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_COMPARATOR.ordinal()] = 1805;
            } catch (NoSuchFieldError nosuchfielderror1018) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_COMPARATOR_OFF.ordinal()] = 1552;
            } catch (NoSuchFieldError nosuchfielderror1019) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_COMPARATOR_ON.ordinal()] = 1553;
            } catch (NoSuchFieldError nosuchfielderror1020) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_LAMP_OFF.ordinal()] = 1526;
            } catch (NoSuchFieldError nosuchfielderror1021) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_LAMP_ON.ordinal()] = 1527;
            } catch (NoSuchFieldError nosuchfielderror1022) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_ORE.ordinal()] = 1476;
            } catch (NoSuchFieldError nosuchfielderror1023) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_TORCH_OFF.ordinal()] = 1478;
            } catch (NoSuchFieldError nosuchfielderror1024) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_TORCH_ON.ordinal()] = 1479;
            } catch (NoSuchFieldError nosuchfielderror1025) {
                ;
            }

            try {
                aint1[Material.LEGACY_REDSTONE_WIRE.ordinal()] = 1458;
            } catch (NoSuchFieldError nosuchfielderror1026) {
                ;
            }

            try {
                aint1[Material.LEGACY_RED_GLAZED_TERRACOTTA.ordinal()] = 1652;
            } catch (NoSuchFieldError nosuchfielderror1027) {
                ;
            }

            try {
                aint1[Material.LEGACY_RED_MUSHROOM.ordinal()] = 1443;
            } catch (NoSuchFieldError nosuchfielderror1028) {
                ;
            }

            try {
                aint1[Material.LEGACY_RED_NETHER_BRICK.ordinal()] = 1618;
            } catch (NoSuchFieldError nosuchfielderror1029) {
                ;
            }

            try {
                aint1[Material.LEGACY_RED_ROSE.ordinal()] = 1441;
            } catch (NoSuchFieldError nosuchfielderror1030) {
                ;
            }

            try {
                aint1[Material.LEGACY_RED_SANDSTONE.ordinal()] = 1582;
            } catch (NoSuchFieldError nosuchfielderror1031) {
                ;
            }

            try {
                aint1[Material.LEGACY_RED_SANDSTONE_STAIRS.ordinal()] = 1583;
            } catch (NoSuchFieldError nosuchfielderror1032) {
                ;
            }

            try {
                aint1[Material.LEGACY_RED_SHULKER_BOX.ordinal()] = 1636;
            } catch (NoSuchFieldError nosuchfielderror1033) {
                ;
            }

            try {
                aint1[Material.LEGACY_ROTTEN_FLESH.ordinal()] = 1768;
            } catch (NoSuchFieldError nosuchfielderror1034) {
                ;
            }

            try {
                aint1[Material.LEGACY_SADDLE.ordinal()] = 1730;
            } catch (NoSuchFieldError nosuchfielderror1035) {
                ;
            }

            try {
                aint1[Material.LEGACY_SAND.ordinal()] = 1415;
            } catch (NoSuchFieldError nosuchfielderror1036) {
                ;
            }

            try {
                aint1[Material.LEGACY_SANDSTONE.ordinal()] = 1427;
            } catch (NoSuchFieldError nosuchfielderror1037) {
                ;
            }

            try {
                aint1[Material.LEGACY_SANDSTONE_STAIRS.ordinal()] = 1531;
            } catch (NoSuchFieldError nosuchfielderror1038) {
                ;
            }

            try {
                aint1[Material.LEGACY_SAPLING.ordinal()] = 1409;
            } catch (NoSuchFieldError nosuchfielderror1039) {
                ;
            }

            try {
                aint1[Material.LEGACY_SEA_LANTERN.ordinal()] = 1572;
            } catch (NoSuchFieldError nosuchfielderror1040) {
                ;
            }

            try {
                aint1[Material.LEGACY_SEEDS.ordinal()] = 1696;
            } catch (NoSuchFieldError nosuchfielderror1041) {
                ;
            }

            try {
                aint1[Material.LEGACY_SHEARS.ordinal()] = 1760;
            } catch (NoSuchFieldError nosuchfielderror1042) {
                ;
            }

            try {
                aint1[Material.LEGACY_SHIELD.ordinal()] = 1843;
            } catch (NoSuchFieldError nosuchfielderror1043) {
                ;
            }

            try {
                aint1[Material.LEGACY_SHULKER_SHELL.ordinal()] = 1851;
            } catch (NoSuchFieldError nosuchfielderror1044) {
                ;
            }

            try {
                aint1[Material.LEGACY_SIGN.ordinal()] = 1724;
            } catch (NoSuchFieldError nosuchfielderror1045) {
                ;
            }

            try {
                aint1[Material.LEGACY_SIGN_POST.ordinal()] = 1466;
            } catch (NoSuchFieldError nosuchfielderror1046) {
                ;
            }

            try {
                aint1[Material.LEGACY_SILVER_GLAZED_TERRACOTTA.ordinal()] = 1646;
            } catch (NoSuchFieldError nosuchfielderror1047) {
                ;
            }

            try {
                aint1[Material.LEGACY_SILVER_SHULKER_BOX.ordinal()] = 1630;
            } catch (NoSuchFieldError nosuchfielderror1048) {
                ;
            }

            try {
                aint1[Material.LEGACY_SKULL.ordinal()] = 1547;
            } catch (NoSuchFieldError nosuchfielderror1049) {
                ;
            }

            try {
                aint1[Material.LEGACY_SKULL_ITEM.ordinal()] = 1798;
            } catch (NoSuchFieldError nosuchfielderror1050) {
                ;
            }

            try {
                aint1[Material.LEGACY_SLIME_BALL.ordinal()] = 1742;
            } catch (NoSuchFieldError nosuchfielderror1051) {
                ;
            }

            try {
                aint1[Material.LEGACY_SLIME_BLOCK.ordinal()] = 1568;
            } catch (NoSuchFieldError nosuchfielderror1052) {
                ;
            }

            try {
                aint1[Material.LEGACY_SMOOTH_BRICK.ordinal()] = 1501;
            } catch (NoSuchFieldError nosuchfielderror1053) {
                ;
            }

            try {
                aint1[Material.LEGACY_SMOOTH_STAIRS.ordinal()] = 1512;
            } catch (NoSuchFieldError nosuchfielderror1054) {
                ;
            }

            try {
                aint1[Material.LEGACY_SNOW.ordinal()] = 1481;
            } catch (NoSuchFieldError nosuchfielderror1055) {
                ;
            }

            try {
                aint1[Material.LEGACY_SNOW_BALL.ordinal()] = 1733;
            } catch (NoSuchFieldError nosuchfielderror1056) {
                ;
            }

            try {
                aint1[Material.LEGACY_SNOW_BLOCK.ordinal()] = 1483;
            } catch (NoSuchFieldError nosuchfielderror1057) {
                ;
            }

            try {
                aint1[Material.LEGACY_SOIL.ordinal()] = 1463;
            } catch (NoSuchFieldError nosuchfielderror1058) {
                ;
            }

            try {
                aint1[Material.LEGACY_SOUL_SAND.ordinal()] = 1491;
            } catch (NoSuchFieldError nosuchfielderror1059) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPECKLED_MELON.ordinal()] = 1783;
            } catch (NoSuchFieldError nosuchfielderror1060) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPECTRAL_ARROW.ordinal()] = 1840;
            } catch (NoSuchFieldError nosuchfielderror1061) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPIDER_EYE.ordinal()] = 1776;
            } catch (NoSuchFieldError nosuchfielderror1062) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPLASH_POTION.ordinal()] = 1839;
            } catch (NoSuchFieldError nosuchfielderror1063) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPONGE.ordinal()] = 1422;
            } catch (NoSuchFieldError nosuchfielderror1064) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPRUCE_DOOR.ordinal()] = 1596;
            } catch (NoSuchFieldError nosuchfielderror1065) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPRUCE_DOOR_ITEM.ordinal()] = 1828;
            } catch (NoSuchFieldError nosuchfielderror1066) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPRUCE_FENCE.ordinal()] = 1591;
            } catch (NoSuchFieldError nosuchfielderror1067) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPRUCE_FENCE_GATE.ordinal()] = 1586;
            } catch (NoSuchFieldError nosuchfielderror1068) {
                ;
            }

            try {
                aint1[Material.LEGACY_SPRUCE_WOOD_STAIRS.ordinal()] = 1537;
            } catch (NoSuchFieldError nosuchfielderror1069) {
                ;
            }

            try {
                aint1[Material.LEGACY_STAINED_CLAY.ordinal()] = 1562;
            } catch (NoSuchFieldError nosuchfielderror1070) {
                ;
            }

            try {
                aint1[Material.LEGACY_STAINED_GLASS.ordinal()] = 1498;
            } catch (NoSuchFieldError nosuchfielderror1071) {
                ;
            }

            try {
                aint1[Material.LEGACY_STAINED_GLASS_PANE.ordinal()] = 1563;
            } catch (NoSuchFieldError nosuchfielderror1072) {
                ;
            }

            try {
                aint1[Material.LEGACY_STANDING_BANNER.ordinal()] = 1579;
            } catch (NoSuchFieldError nosuchfielderror1073) {
                ;
            }

            try {
                aint1[Material.LEGACY_STATIONARY_LAVA.ordinal()] = 1414;
            } catch (NoSuchFieldError nosuchfielderror1074) {
                ;
            }

            try {
                aint1[Material.LEGACY_STATIONARY_WATER.ordinal()] = 1412;
            } catch (NoSuchFieldError nosuchfielderror1075) {
                ;
            }

            try {
                aint1[Material.LEGACY_STEP.ordinal()] = 1447;
            } catch (NoSuchFieldError nosuchfielderror1076) {
                ;
            }

            try {
                aint1[Material.LEGACY_STICK.ordinal()] = 1681;
            } catch (NoSuchFieldError nosuchfielderror1077) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE.ordinal()] = 1404;
            } catch (NoSuchFieldError nosuchfielderror1078) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_AXE.ordinal()] = 1676;
            } catch (NoSuchFieldError nosuchfielderror1079) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_BUTTON.ordinal()] = 1480;
            } catch (NoSuchFieldError nosuchfielderror1080) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_HOE.ordinal()] = 1692;
            } catch (NoSuchFieldError nosuchfielderror1081) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_PICKAXE.ordinal()] = 1675;
            } catch (NoSuchFieldError nosuchfielderror1082) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_PLATE.ordinal()] = 1473;
            } catch (NoSuchFieldError nosuchfielderror1083) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_SLAB2.ordinal()] = 1585;
            } catch (NoSuchFieldError nosuchfielderror1084) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_SPADE.ordinal()] = 1674;
            } catch (NoSuchFieldError nosuchfielderror1085) {
                ;
            }

            try {
                aint1[Material.LEGACY_STONE_SWORD.ordinal()] = 1673;
            } catch (NoSuchFieldError nosuchfielderror1086) {
                ;
            }

            try {
                aint1[Material.LEGACY_STORAGE_MINECART.ordinal()] = 1743;
            } catch (NoSuchFieldError nosuchfielderror1087) {
                ;
            }

            try {
                aint1[Material.LEGACY_STRING.ordinal()] = 1688;
            } catch (NoSuchFieldError nosuchfielderror1088) {
                ;
            }

            try {
                aint1[Material.LEGACY_STRUCTURE_BLOCK.ordinal()] = 1656;
            } catch (NoSuchFieldError nosuchfielderror1089) {
                ;
            }

            try {
                aint1[Material.LEGACY_STRUCTURE_VOID.ordinal()] = 1620;
            } catch (NoSuchFieldError nosuchfielderror1090) {
                ;
            }

            try {
                aint1[Material.LEGACY_SUGAR.ordinal()] = 1754;
            } catch (NoSuchFieldError nosuchfielderror1091) {
                ;
            }

            try {
                aint1[Material.LEGACY_SUGAR_CANE.ordinal()] = 1739;
            } catch (NoSuchFieldError nosuchfielderror1092) {
                ;
            }

            try {
                aint1[Material.LEGACY_SUGAR_CANE_BLOCK.ordinal()] = 1486;
            } catch (NoSuchFieldError nosuchfielderror1093) {
                ;
            }

            try {
                aint1[Material.LEGACY_SULPHUR.ordinal()] = 1690;
            } catch (NoSuchFieldError nosuchfielderror1094) {
                ;
            }

            try {
                aint1[Material.LEGACY_THIN_GLASS.ordinal()] = 1505;
            } catch (NoSuchFieldError nosuchfielderror1095) {
                ;
            }

            try {
                aint1[Material.LEGACY_TIPPED_ARROW.ordinal()] = 1841;
            } catch (NoSuchFieldError nosuchfielderror1096) {
                ;
            }

            try {
                aint1[Material.LEGACY_TNT.ordinal()] = 1449;
            } catch (NoSuchFieldError nosuchfielderror1097) {
                ;
            }

            try {
                aint1[Material.LEGACY_TORCH.ordinal()] = 1453;
            } catch (NoSuchFieldError nosuchfielderror1098) {
                ;
            }

            try {
                aint1[Material.LEGACY_TOTEM.ordinal()] = 1850;
            } catch (NoSuchFieldError nosuchfielderror1099) {
                ;
            }

            try {
                aint1[Material.LEGACY_TRAPPED_CHEST.ordinal()] = 1549;
            } catch (NoSuchFieldError nosuchfielderror1100) {
                ;
            }

            try {
                aint1[Material.LEGACY_TRAP_DOOR.ordinal()] = 1499;
            } catch (NoSuchFieldError nosuchfielderror1101) {
                ;
            }

            try {
                aint1[Material.LEGACY_TRIPWIRE.ordinal()] = 1535;
            } catch (NoSuchFieldError nosuchfielderror1102) {
                ;
            }

            try {
                aint1[Material.LEGACY_TRIPWIRE_HOOK.ordinal()] = 1534;
            } catch (NoSuchFieldError nosuchfielderror1103) {
                ;
            }

            try {
                aint1[Material.LEGACY_VINE.ordinal()] = 1509;
            } catch (NoSuchFieldError nosuchfielderror1104) {
                ;
            }

            try {
                aint1[Material.LEGACY_WALL_BANNER.ordinal()] = 1580;
            } catch (NoSuchFieldError nosuchfielderror1105) {
                ;
            }

            try {
                aint1[Material.LEGACY_WALL_SIGN.ordinal()] = 1471;
            } catch (NoSuchFieldError nosuchfielderror1106) {
                ;
            }

            try {
                aint1[Material.LEGACY_WATCH.ordinal()] = 1748;
            } catch (NoSuchFieldError nosuchfielderror1107) {
                ;
            }

            try {
                aint1[Material.LEGACY_WATER.ordinal()] = 1411;
            } catch (NoSuchFieldError nosuchfielderror1108) {
                ;
            }

            try {
                aint1[Material.LEGACY_WATER_BUCKET.ordinal()] = 1727;
            } catch (NoSuchFieldError nosuchfielderror1109) {
                ;
            }

            try {
                aint1[Material.LEGACY_WATER_LILY.ordinal()] = 1514;
            } catch (NoSuchFieldError nosuchfielderror1110) {
                ;
            }

            try {
                aint1[Material.LEGACY_WEB.ordinal()] = 1433;
            } catch (NoSuchFieldError nosuchfielderror1111) {
                ;
            }

            try {
                aint1[Material.LEGACY_WHEAT.ordinal()] = 1697;
            } catch (NoSuchFieldError nosuchfielderror1112) {
                ;
            }

            try {
                aint1[Material.LEGACY_WHITE_GLAZED_TERRACOTTA.ordinal()] = 1638;
            } catch (NoSuchFieldError nosuchfielderror1113) {
                ;
            }

            try {
                aint1[Material.LEGACY_WHITE_SHULKER_BOX.ordinal()] = 1622;
            } catch (NoSuchFieldError nosuchfielderror1114) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD.ordinal()] = 1408;
            } catch (NoSuchFieldError nosuchfielderror1115) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOODEN_DOOR.ordinal()] = 1467;
            } catch (NoSuchFieldError nosuchfielderror1116) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_AXE.ordinal()] = 1672;
            } catch (NoSuchFieldError nosuchfielderror1117) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_BUTTON.ordinal()] = 1546;
            } catch (NoSuchFieldError nosuchfielderror1118) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_DOOR.ordinal()] = 1725;
            } catch (NoSuchFieldError nosuchfielderror1119) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_DOUBLE_STEP.ordinal()] = 1528;
            } catch (NoSuchFieldError nosuchfielderror1120) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_HOE.ordinal()] = 1691;
            } catch (NoSuchFieldError nosuchfielderror1121) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_PICKAXE.ordinal()] = 1671;
            } catch (NoSuchFieldError nosuchfielderror1122) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_PLATE.ordinal()] = 1475;
            } catch (NoSuchFieldError nosuchfielderror1123) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_SPADE.ordinal()] = 1670;
            } catch (NoSuchFieldError nosuchfielderror1124) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_STAIRS.ordinal()] = 1456;
            } catch (NoSuchFieldError nosuchfielderror1125) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_STEP.ordinal()] = 1529;
            } catch (NoSuchFieldError nosuchfielderror1126) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOD_SWORD.ordinal()] = 1669;
            } catch (NoSuchFieldError nosuchfielderror1127) {
                ;
            }

            try {
                aint1[Material.LEGACY_WOOL.ordinal()] = 1438;
            } catch (NoSuchFieldError nosuchfielderror1128) {
                ;
            }

            try {
                aint1[Material.LEGACY_WORKBENCH.ordinal()] = 1461;
            } catch (NoSuchFieldError nosuchfielderror1129) {
                ;
            }

            try {
                aint1[Material.LEGACY_WRITTEN_BOOK.ordinal()] = 1788;
            } catch (NoSuchFieldError nosuchfielderror1130) {
                ;
            }

            try {
                aint1[Material.LEGACY_YELLOW_FLOWER.ordinal()] = 1440;
            } catch (NoSuchFieldError nosuchfielderror1131) {
                ;
            }

            try {
                aint1[Material.LEGACY_YELLOW_GLAZED_TERRACOTTA.ordinal()] = 1642;
            } catch (NoSuchFieldError nosuchfielderror1132) {
                ;
            }

            try {
                aint1[Material.LEGACY_YELLOW_SHULKER_BOX.ordinal()] = 1626;
            } catch (NoSuchFieldError nosuchfielderror1133) {
                ;
            }

            try {
                aint1[Material.LEVER.ordinal()] = 651;
            } catch (NoSuchFieldError nosuchfielderror1134) {
                ;
            }

            try {
                aint1[Material.LIGHT.ordinal()] = 423;
            } catch (NoSuchFieldError nosuchfielderror1135) {
                ;
            }

            try {
                aint1[Material.LIGHTNING_ROD.ordinal()] = 652;
            } catch (NoSuchFieldError nosuchfielderror1136) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_BANNER.ordinal()] = 1091;
            } catch (NoSuchFieldError nosuchfielderror1137) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_BED.ordinal()] = 928;
            } catch (NoSuchFieldError nosuchfielderror1138) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_CANDLE.ordinal()] = 1195;
            } catch (NoSuchFieldError nosuchfielderror1139) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_CANDLE_CAKE.ordinal()] = 1384;
            } catch (NoSuchFieldError nosuchfielderror1140) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_CARPET.ordinal()] = 428;
            } catch (NoSuchFieldError nosuchfielderror1141) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_CONCRETE.ordinal()] = 537;
            } catch (NoSuchFieldError nosuchfielderror1142) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_CONCRETE_POWDER.ordinal()] = 553;
            } catch (NoSuchFieldError nosuchfielderror1143) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_DYE.ordinal()] = 908;
            } catch (NoSuchFieldError nosuchfielderror1144) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_GLAZED_TERRACOTTA.ordinal()] = 521;
            } catch (NoSuchFieldError nosuchfielderror1145) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_SHULKER_BOX.ordinal()] = 505;
            } catch (NoSuchFieldError nosuchfielderror1146) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_STAINED_GLASS.ordinal()] = 453;
            } catch (NoSuchFieldError nosuchfielderror1147) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_STAINED_GLASS_PANE.ordinal()] = 469;
            } catch (NoSuchFieldError nosuchfielderror1148) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_TERRACOTTA.ordinal()] = 409;
            } catch (NoSuchFieldError nosuchfielderror1149) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_WALL_BANNER.ordinal()] = 1337;
            } catch (NoSuchFieldError nosuchfielderror1150) {
                ;
            }

            try {
                aint1[Material.LIGHT_BLUE_WOOL.ordinal()] = 184;
            } catch (NoSuchFieldError nosuchfielderror1151) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_BANNER.ordinal()] = 1096;
            } catch (NoSuchFieldError nosuchfielderror1152) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_BED.ordinal()] = 933;
            } catch (NoSuchFieldError nosuchfielderror1153) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_CANDLE.ordinal()] = 1200;
            } catch (NoSuchFieldError nosuchfielderror1154) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_CANDLE_CAKE.ordinal()] = 1389;
            } catch (NoSuchFieldError nosuchfielderror1155) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_CARPET.ordinal()] = 433;
            } catch (NoSuchFieldError nosuchfielderror1156) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_CONCRETE.ordinal()] = 542;
            } catch (NoSuchFieldError nosuchfielderror1157) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_CONCRETE_POWDER.ordinal()] = 558;
            } catch (NoSuchFieldError nosuchfielderror1158) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_DYE.ordinal()] = 913;
            } catch (NoSuchFieldError nosuchfielderror1159) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_GLAZED_TERRACOTTA.ordinal()] = 526;
            } catch (NoSuchFieldError nosuchfielderror1160) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_SHULKER_BOX.ordinal()] = 510;
            } catch (NoSuchFieldError nosuchfielderror1161) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_STAINED_GLASS.ordinal()] = 458;
            } catch (NoSuchFieldError nosuchfielderror1162) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_STAINED_GLASS_PANE.ordinal()] = 474;
            } catch (NoSuchFieldError nosuchfielderror1163) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_TERRACOTTA.ordinal()] = 414;
            } catch (NoSuchFieldError nosuchfielderror1164) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_WALL_BANNER.ordinal()] = 1342;
            } catch (NoSuchFieldError nosuchfielderror1165) {
                ;
            }

            try {
                aint1[Material.LIGHT_GRAY_WOOL.ordinal()] = 189;
            } catch (NoSuchFieldError nosuchfielderror1166) {
                ;
            }

            try {
                aint1[Material.LIGHT_WEIGHTED_PRESSURE_PLATE.ordinal()] = 676;
            } catch (NoSuchFieldError nosuchfielderror1167) {
                ;
            }

            try {
                aint1[Material.LILAC.ordinal()] = 445;
            } catch (NoSuchFieldError nosuchfielderror1168) {
                ;
            }

            try {
                aint1[Material.LILY_OF_THE_VALLEY.ordinal()] = 208;
            } catch (NoSuchFieldError nosuchfielderror1169) {
                ;
            }

            try {
                aint1[Material.LILY_PAD.ordinal()] = 344;
            } catch (NoSuchFieldError nosuchfielderror1170) {
                ;
            }

            try {
                aint1[Material.LIME_BANNER.ordinal()] = 1093;
            } catch (NoSuchFieldError nosuchfielderror1171) {
                ;
            }

            try {
                aint1[Material.LIME_BED.ordinal()] = 930;
            } catch (NoSuchFieldError nosuchfielderror1172) {
                ;
            }

            try {
                aint1[Material.LIME_CANDLE.ordinal()] = 1197;
            } catch (NoSuchFieldError nosuchfielderror1173) {
                ;
            }

            try {
                aint1[Material.LIME_CANDLE_CAKE.ordinal()] = 1386;
            } catch (NoSuchFieldError nosuchfielderror1174) {
                ;
            }

            try {
                aint1[Material.LIME_CARPET.ordinal()] = 430;
            } catch (NoSuchFieldError nosuchfielderror1175) {
                ;
            }

            try {
                aint1[Material.LIME_CONCRETE.ordinal()] = 539;
            } catch (NoSuchFieldError nosuchfielderror1176) {
                ;
            }

            try {
                aint1[Material.LIME_CONCRETE_POWDER.ordinal()] = 555;
            } catch (NoSuchFieldError nosuchfielderror1177) {
                ;
            }

            try {
                aint1[Material.LIME_DYE.ordinal()] = 910;
            } catch (NoSuchFieldError nosuchfielderror1178) {
                ;
            }

            try {
                aint1[Material.LIME_GLAZED_TERRACOTTA.ordinal()] = 523;
            } catch (NoSuchFieldError nosuchfielderror1179) {
                ;
            }

            try {
                aint1[Material.LIME_SHULKER_BOX.ordinal()] = 507;
            } catch (NoSuchFieldError nosuchfielderror1180) {
                ;
            }

            try {
                aint1[Material.LIME_STAINED_GLASS.ordinal()] = 455;
            } catch (NoSuchFieldError nosuchfielderror1181) {
                ;
            }

            try {
                aint1[Material.LIME_STAINED_GLASS_PANE.ordinal()] = 471;
            } catch (NoSuchFieldError nosuchfielderror1182) {
                ;
            }

            try {
                aint1[Material.LIME_TERRACOTTA.ordinal()] = 411;
            } catch (NoSuchFieldError nosuchfielderror1183) {
                ;
            }

            try {
                aint1[Material.LIME_WALL_BANNER.ordinal()] = 1339;
            } catch (NoSuchFieldError nosuchfielderror1184) {
                ;
            }

            try {
                aint1[Material.LIME_WOOL.ordinal()] = 186;
            } catch (NoSuchFieldError nosuchfielderror1185) {
                ;
            }

            try {
                aint1[Material.LINGERING_POTION.ordinal()] = 1116;
            } catch (NoSuchFieldError nosuchfielderror1186) {
                ;
            }

            try {
                aint1[Material.LLAMA_SPAWN_EGG.ordinal()] = 998;
            } catch (NoSuchFieldError nosuchfielderror1187) {
                ;
            }

            try {
                aint1[Material.LODESTONE.ordinal()] = 1176;
            } catch (NoSuchFieldError nosuchfielderror1188) {
                ;
            }

            try {
                aint1[Material.LOOM.ordinal()] = 1146;
            } catch (NoSuchFieldError nosuchfielderror1189) {
                ;
            }

            try {
                aint1[Material.MAGENTA_BANNER.ordinal()] = 1090;
            } catch (NoSuchFieldError nosuchfielderror1190) {
                ;
            }

            try {
                aint1[Material.MAGENTA_BED.ordinal()] = 927;
            } catch (NoSuchFieldError nosuchfielderror1191) {
                ;
            }

            try {
                aint1[Material.MAGENTA_CANDLE.ordinal()] = 1194;
            } catch (NoSuchFieldError nosuchfielderror1192) {
                ;
            }

            try {
                aint1[Material.MAGENTA_CANDLE_CAKE.ordinal()] = 1383;
            } catch (NoSuchFieldError nosuchfielderror1193) {
                ;
            }

            try {
                aint1[Material.MAGENTA_CARPET.ordinal()] = 427;
            } catch (NoSuchFieldError nosuchfielderror1194) {
                ;
            }

            try {
                aint1[Material.MAGENTA_CONCRETE.ordinal()] = 536;
            } catch (NoSuchFieldError nosuchfielderror1195) {
                ;
            }

            try {
                aint1[Material.MAGENTA_CONCRETE_POWDER.ordinal()] = 552;
            } catch (NoSuchFieldError nosuchfielderror1196) {
                ;
            }

            try {
                aint1[Material.MAGENTA_DYE.ordinal()] = 907;
            } catch (NoSuchFieldError nosuchfielderror1197) {
                ;
            }

            try {
                aint1[Material.MAGENTA_GLAZED_TERRACOTTA.ordinal()] = 520;
            } catch (NoSuchFieldError nosuchfielderror1198) {
                ;
            }

            try {
                aint1[Material.MAGENTA_SHULKER_BOX.ordinal()] = 504;
            } catch (NoSuchFieldError nosuchfielderror1199) {
                ;
            }

            try {
                aint1[Material.MAGENTA_STAINED_GLASS.ordinal()] = 452;
            } catch (NoSuchFieldError nosuchfielderror1200) {
                ;
            }

            try {
                aint1[Material.MAGENTA_STAINED_GLASS_PANE.ordinal()] = 468;
            } catch (NoSuchFieldError nosuchfielderror1201) {
                ;
            }

            try {
                aint1[Material.MAGENTA_TERRACOTTA.ordinal()] = 408;
            } catch (NoSuchFieldError nosuchfielderror1202) {
                ;
            }

            try {
                aint1[Material.MAGENTA_WALL_BANNER.ordinal()] = 1336;
            } catch (NoSuchFieldError nosuchfielderror1203) {
                ;
            }

            try {
                aint1[Material.MAGENTA_WOOL.ordinal()] = 183;
            } catch (NoSuchFieldError nosuchfielderror1204) {
                ;
            }

            try {
                aint1[Material.MAGMA_BLOCK.ordinal()] = 495;
            } catch (NoSuchFieldError nosuchfielderror1205) {
                ;
            }

            try {
                aint1[Material.MAGMA_CREAM.ordinal()] = 963;
            } catch (NoSuchFieldError nosuchfielderror1206) {
                ;
            }

            try {
                aint1[Material.MAGMA_CUBE_SPAWN_EGG.ordinal()] = 999;
            } catch (NoSuchFieldError nosuchfielderror1207) {
                ;
            }

            try {
                aint1[Material.MANGROVE_BOAT.ordinal()] = 751;
            } catch (NoSuchFieldError nosuchfielderror1208) {
                ;
            }

            try {
                aint1[Material.MANGROVE_BUTTON.ordinal()] = 670;
            } catch (NoSuchFieldError nosuchfielderror1209) {
                ;
            }

            try {
                aint1[Material.MANGROVE_CHEST_BOAT.ordinal()] = 752;
            } catch (NoSuchFieldError nosuchfielderror1210) {
                ;
            }

            try {
                aint1[Material.MANGROVE_DOOR.ordinal()] = 697;
            } catch (NoSuchFieldError nosuchfielderror1211) {
                ;
            }

            try {
                aint1[Material.MANGROVE_FENCE.ordinal()] = 297;
            } catch (NoSuchFieldError nosuchfielderror1212) {
                ;
            }

            try {
                aint1[Material.MANGROVE_FENCE_GATE.ordinal()] = 720;
            } catch (NoSuchFieldError nosuchfielderror1213) {
                ;
            }

            try {
                aint1[Material.MANGROVE_HANGING_SIGN.ordinal()] = 865;
            } catch (NoSuchFieldError nosuchfielderror1214) {
                ;
            }

            try {
                aint1[Material.MANGROVE_LEAVES.ordinal()] = 162;
            } catch (NoSuchFieldError nosuchfielderror1215) {
                ;
            }

            try {
                aint1[Material.MANGROVE_LOG.ordinal()] = 118;
            } catch (NoSuchFieldError nosuchfielderror1216) {
                ;
            }

            try {
                aint1[Material.MANGROVE_PLANKS.ordinal()] = 31;
            } catch (NoSuchFieldError nosuchfielderror1217) {
                ;
            }

            try {
                aint1[Material.MANGROVE_PRESSURE_PLATE.ordinal()] = 685;
            } catch (NoSuchFieldError nosuchfielderror1218) {
                ;
            }

            try {
                aint1[Material.MANGROVE_PROPAGULE.ordinal()] = 43;
            } catch (NoSuchFieldError nosuchfielderror1219) {
                ;
            }

            try {
                aint1[Material.MANGROVE_ROOTS.ordinal()] = 119;
            } catch (NoSuchFieldError nosuchfielderror1220) {
                ;
            }

            try {
                aint1[Material.MANGROVE_SIGN.ordinal()] = 854;
            } catch (NoSuchFieldError nosuchfielderror1221) {
                ;
            }

            try {
                aint1[Material.MANGROVE_SLAB.ordinal()] = 238;
            } catch (NoSuchFieldError nosuchfielderror1222) {
                ;
            }

            try {
                aint1[Material.MANGROVE_STAIRS.ordinal()] = 369;
            } catch (NoSuchFieldError nosuchfielderror1223) {
                ;
            }

            try {
                aint1[Material.MANGROVE_TRAPDOOR.ordinal()] = 709;
            } catch (NoSuchFieldError nosuchfielderror1224) {
                ;
            }

            try {
                aint1[Material.MANGROVE_WALL_HANGING_SIGN.ordinal()] = 1281;
            } catch (NoSuchFieldError nosuchfielderror1225) {
                ;
            }

            try {
                aint1[Material.MANGROVE_WALL_SIGN.ordinal()] = 1272;
            } catch (NoSuchFieldError nosuchfielderror1226) {
                ;
            }

            try {
                aint1[Material.MANGROVE_WOOD.ordinal()] = 152;
            } catch (NoSuchFieldError nosuchfielderror1227) {
                ;
            }

            try {
                aint1[Material.MAP.ordinal()] = 1056;
            } catch (NoSuchFieldError nosuchfielderror1228) {
                ;
            }

            try {
                aint1[Material.MEDIUM_AMETHYST_BUD.ordinal()] = 1209;
            } catch (NoSuchFieldError nosuchfielderror1229) {
                ;
            }

            try {
                aint1[Material.MELON.ordinal()] = 337;
            } catch (NoSuchFieldError nosuchfielderror1230) {
                ;
            }

            try {
                aint1[Material.MELON_SEEDS.ordinal()] = 947;
            } catch (NoSuchFieldError nosuchfielderror1231) {
                ;
            }

            try {
                aint1[Material.MELON_SLICE.ordinal()] = 944;
            } catch (NoSuchFieldError nosuchfielderror1232) {
                ;
            }

            try {
                aint1[Material.MELON_STEM.ordinal()] = 1291;
            } catch (NoSuchFieldError nosuchfielderror1233) {
                ;
            }

            try {
                aint1[Material.MILK_BUCKET.ordinal()] = 875;
            } catch (NoSuchFieldError nosuchfielderror1234) {
                ;
            }

            try {
                aint1[Material.MINECART.ordinal()] = 729;
            } catch (NoSuchFieldError nosuchfielderror1235) {
                ;
            }

            try {
                aint1[Material.MINER_POTTERY_SHERD.ordinal()] = 1248;
            } catch (NoSuchFieldError nosuchfielderror1236) {
                ;
            }

            try {
                aint1[Material.MOJANG_BANNER_PATTERN.ordinal()] = 1150;
            } catch (NoSuchFieldError nosuchfielderror1237) {
                ;
            }

            try {
                aint1[Material.MOOSHROOM_SPAWN_EGG.ordinal()] = 1000;
            } catch (NoSuchFieldError nosuchfielderror1238) {
                ;
            }

            try {
                aint1[Material.MOSSY_COBBLESTONE.ordinal()] = 268;
            } catch (NoSuchFieldError nosuchfielderror1239) {
                ;
            }

            try {
                aint1[Material.MOSSY_COBBLESTONE_SLAB.ordinal()] = 622;
            } catch (NoSuchFieldError nosuchfielderror1240) {
                ;
            }

            try {
                aint1[Material.MOSSY_COBBLESTONE_STAIRS.ordinal()] = 604;
            } catch (NoSuchFieldError nosuchfielderror1241) {
                ;
            }

            try {
                aint1[Material.MOSSY_COBBLESTONE_WALL.ordinal()] = 377;
            } catch (NoSuchFieldError nosuchfielderror1242) {
                ;
            }

            try {
                aint1[Material.MOSSY_STONE_BRICKS.ordinal()] = 320;
            } catch (NoSuchFieldError nosuchfielderror1243) {
                ;
            }

            try {
                aint1[Material.MOSSY_STONE_BRICK_SLAB.ordinal()] = 620;
            } catch (NoSuchFieldError nosuchfielderror1244) {
                ;
            }

            try {
                aint1[Material.MOSSY_STONE_BRICK_STAIRS.ordinal()] = 602;
            } catch (NoSuchFieldError nosuchfielderror1245) {
                ;
            }

            try {
                aint1[Material.MOSSY_STONE_BRICK_WALL.ordinal()] = 381;
            } catch (NoSuchFieldError nosuchfielderror1246) {
                ;
            }

            try {
                aint1[Material.MOSS_BLOCK.ordinal()] = 226;
            } catch (NoSuchFieldError nosuchfielderror1247) {
                ;
            }

            try {
                aint1[Material.MOSS_CARPET.ordinal()] = 224;
            } catch (NoSuchFieldError nosuchfielderror1248) {
                ;
            }

            try {
                aint1[Material.MOURNER_POTTERY_SHERD.ordinal()] = 1249;
            } catch (NoSuchFieldError nosuchfielderror1249) {
                ;
            }

            try {
                aint1[Material.MOVING_PISTON.ordinal()] = 1260;
            } catch (NoSuchFieldError nosuchfielderror1250) {
                ;
            }

            try {
                aint1[Material.MUD.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror1251) {
                ;
            }

            try {
                aint1[Material.MUDDY_MANGROVE_ROOTS.ordinal()] = 120;
            } catch (NoSuchFieldError nosuchfielderror1252) {
                ;
            }

            try {
                aint1[Material.MUD_BRICKS.ordinal()] = 324;
            } catch (NoSuchFieldError nosuchfielderror1253) {
                ;
            }

            try {
                aint1[Material.MUD_BRICK_SLAB.ordinal()] = 251;
            } catch (NoSuchFieldError nosuchfielderror1254) {
                ;
            }

            try {
                aint1[Material.MUD_BRICK_STAIRS.ordinal()] = 342;
            } catch (NoSuchFieldError nosuchfielderror1255) {
                ;
            }

            try {
                aint1[Material.MUD_BRICK_WALL.ordinal()] = 384;
            } catch (NoSuchFieldError nosuchfielderror1256) {
                ;
            }

            try {
                aint1[Material.MULE_SPAWN_EGG.ordinal()] = 1001;
            } catch (NoSuchFieldError nosuchfielderror1257) {
                ;
            }

            try {
                aint1[Material.MUSHROOM_STEM.ordinal()] = 333;
            } catch (NoSuchFieldError nosuchfielderror1258) {
                ;
            }

            try {
                aint1[Material.MUSHROOM_STEW.ordinal()] = 810;
            } catch (NoSuchFieldError nosuchfielderror1259) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_11.ordinal()] = 1133;
            } catch (NoSuchFieldError nosuchfielderror1260) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_13.ordinal()] = 1123;
            } catch (NoSuchFieldError nosuchfielderror1261) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_5.ordinal()] = 1137;
            } catch (NoSuchFieldError nosuchfielderror1262) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_BLOCKS.ordinal()] = 1125;
            } catch (NoSuchFieldError nosuchfielderror1263) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_CAT.ordinal()] = 1124;
            } catch (NoSuchFieldError nosuchfielderror1264) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_CHIRP.ordinal()] = 1126;
            } catch (NoSuchFieldError nosuchfielderror1265) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_FAR.ordinal()] = 1127;
            } catch (NoSuchFieldError nosuchfielderror1266) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_MALL.ordinal()] = 1128;
            } catch (NoSuchFieldError nosuchfielderror1267) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_MELLOHI.ordinal()] = 1129;
            } catch (NoSuchFieldError nosuchfielderror1268) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_OTHERSIDE.ordinal()] = 1135;
            } catch (NoSuchFieldError nosuchfielderror1269) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_PIGSTEP.ordinal()] = 1138;
            } catch (NoSuchFieldError nosuchfielderror1270) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_RELIC.ordinal()] = 1136;
            } catch (NoSuchFieldError nosuchfielderror1271) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_STAL.ordinal()] = 1130;
            } catch (NoSuchFieldError nosuchfielderror1272) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_STRAD.ordinal()] = 1131;
            } catch (NoSuchFieldError nosuchfielderror1273) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_WAIT.ordinal()] = 1134;
            } catch (NoSuchFieldError nosuchfielderror1274) {
                ;
            }

            try {
                aint1[Material.MUSIC_DISC_WARD.ordinal()] = 1132;
            } catch (NoSuchFieldError nosuchfielderror1275) {
                ;
            }

            try {
                aint1[Material.MUTTON.ordinal()] = 1086;
            } catch (NoSuchFieldError nosuchfielderror1276) {
                ;
            }

            try {
                aint1[Material.MYCELIUM.ordinal()] = 343;
            } catch (NoSuchFieldError nosuchfielderror1277) {
                ;
            }

            try {
                aint1[Material.NAME_TAG.ordinal()] = 1084;
            } catch (NoSuchFieldError nosuchfielderror1278) {
                ;
            }

            try {
                aint1[Material.NAUTILUS_SHELL.ordinal()] = 1142;
            } catch (NoSuchFieldError nosuchfielderror1279) {
                ;
            }

            try {
                aint1[Material.NETHERITE_AXE.ordinal()] = 806;
            } catch (NoSuchFieldError nosuchfielderror1280) {
                ;
            }

            try {
                aint1[Material.NETHERITE_BLOCK.ordinal()] = 79;
            } catch (NoSuchFieldError nosuchfielderror1281) {
                ;
            }

            try {
                aint1[Material.NETHERITE_BOOTS.ordinal()] = 840;
            } catch (NoSuchFieldError nosuchfielderror1282) {
                ;
            }

            try {
                aint1[Material.NETHERITE_CHESTPLATE.ordinal()] = 838;
            } catch (NoSuchFieldError nosuchfielderror1283) {
                ;
            }

            try {
                aint1[Material.NETHERITE_HELMET.ordinal()] = 837;
            } catch (NoSuchFieldError nosuchfielderror1284) {
                ;
            }

            try {
                aint1[Material.NETHERITE_HOE.ordinal()] = 807;
            } catch (NoSuchFieldError nosuchfielderror1285) {
                ;
            }

            try {
                aint1[Material.NETHERITE_INGOT.ordinal()] = 776;
            } catch (NoSuchFieldError nosuchfielderror1286) {
                ;
            }

            try {
                aint1[Material.NETHERITE_LEGGINGS.ordinal()] = 839;
            } catch (NoSuchFieldError nosuchfielderror1287) {
                ;
            }

            try {
                aint1[Material.NETHERITE_PICKAXE.ordinal()] = 805;
            } catch (NoSuchFieldError nosuchfielderror1288) {
                ;
            }

            try {
                aint1[Material.NETHERITE_SCRAP.ordinal()] = 777;
            } catch (NoSuchFieldError nosuchfielderror1289) {
                ;
            }

            try {
                aint1[Material.NETHERITE_SHOVEL.ordinal()] = 804;
            } catch (NoSuchFieldError nosuchfielderror1290) {
                ;
            }

            try {
                aint1[Material.NETHERITE_SWORD.ordinal()] = 803;
            } catch (NoSuchFieldError nosuchfielderror1291) {
                ;
            }

            try {
                aint1[Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE.ordinal()] = 1219;
            } catch (NoSuchFieldError nosuchfielderror1292) {
                ;
            }

            try {
                aint1[Material.NETHERRACK.ordinal()] = 304;
            } catch (NoSuchFieldError nosuchfielderror1293) {
                ;
            }

            try {
                aint1[Material.NETHER_BRICK.ordinal()] = 1070;
            } catch (NoSuchFieldError nosuchfielderror1294) {
                ;
            }

            try {
                aint1[Material.NETHER_BRICKS.ordinal()] = 345;
            } catch (NoSuchFieldError nosuchfielderror1295) {
                ;
            }

            try {
                aint1[Material.NETHER_BRICK_FENCE.ordinal()] = 348;
            } catch (NoSuchFieldError nosuchfielderror1296) {
                ;
            }

            try {
                aint1[Material.NETHER_BRICK_SLAB.ordinal()] = 252;
            } catch (NoSuchFieldError nosuchfielderror1297) {
                ;
            }

            try {
                aint1[Material.NETHER_BRICK_STAIRS.ordinal()] = 349;
            } catch (NoSuchFieldError nosuchfielderror1298) {
                ;
            }

            try {
                aint1[Material.NETHER_BRICK_WALL.ordinal()] = 385;
            } catch (NoSuchFieldError nosuchfielderror1299) {
                ;
            }

            try {
                aint1[Material.NETHER_GOLD_ORE.ordinal()] = 66;
            } catch (NoSuchFieldError nosuchfielderror1300) {
                ;
            }

            try {
                aint1[Material.NETHER_PORTAL.ordinal()] = 1287;
            } catch (NoSuchFieldError nosuchfielderror1301) {
                ;
            }

            try {
                aint1[Material.NETHER_QUARTZ_ORE.ordinal()] = 67;
            } catch (NoSuchFieldError nosuchfielderror1302) {
                ;
            }

            try {
                aint1[Material.NETHER_SPROUTS.ordinal()] = 219;
            } catch (NoSuchFieldError nosuchfielderror1303) {
                ;
            }

            try {
                aint1[Material.NETHER_STAR.ordinal()] = 1065;
            } catch (NoSuchFieldError nosuchfielderror1304) {
                ;
            }

            try {
                aint1[Material.NETHER_WART.ordinal()] = 957;
            } catch (NoSuchFieldError nosuchfielderror1305) {
                ;
            }

            try {
                aint1[Material.NETHER_WART_BLOCK.ordinal()] = 496;
            } catch (NoSuchFieldError nosuchfielderror1306) {
                ;
            }

            try {
                aint1[Material.NOTE_BLOCK.ordinal()] = 660;
            } catch (NoSuchFieldError nosuchfielderror1307) {
                ;
            }

            try {
                aint1[Material.OAK_BOAT.ordinal()] = 737;
            } catch (NoSuchFieldError nosuchfielderror1308) {
                ;
            }

            try {
                aint1[Material.OAK_BUTTON.ordinal()] = 663;
            } catch (NoSuchFieldError nosuchfielderror1309) {
                ;
            }

            try {
                aint1[Material.OAK_CHEST_BOAT.ordinal()] = 738;
            } catch (NoSuchFieldError nosuchfielderror1310) {
                ;
            }

            try {
                aint1[Material.OAK_DOOR.ordinal()] = 690;
            } catch (NoSuchFieldError nosuchfielderror1311) {
                ;
            }

            try {
                aint1[Material.OAK_FENCE.ordinal()] = 290;
            } catch (NoSuchFieldError nosuchfielderror1312) {
                ;
            }

            try {
                aint1[Material.OAK_FENCE_GATE.ordinal()] = 713;
            } catch (NoSuchFieldError nosuchfielderror1313) {
                ;
            }

            try {
                aint1[Material.OAK_HANGING_SIGN.ordinal()] = 858;
            } catch (NoSuchFieldError nosuchfielderror1314) {
                ;
            }

            try {
                aint1[Material.OAK_LEAVES.ordinal()] = 155;
            } catch (NoSuchFieldError nosuchfielderror1315) {
                ;
            }

            try {
                aint1[Material.OAK_LOG.ordinal()] = 111;
            } catch (NoSuchFieldError nosuchfielderror1316) {
                ;
            }

            try {
                aint1[Material.OAK_PLANKS.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror1317) {
                ;
            }

            try {
                aint1[Material.OAK_PRESSURE_PLATE.ordinal()] = 678;
            } catch (NoSuchFieldError nosuchfielderror1318) {
                ;
            }

            try {
                aint1[Material.OAK_SAPLING.ordinal()] = 36;
            } catch (NoSuchFieldError nosuchfielderror1319) {
                ;
            }

            try {
                aint1[Material.OAK_SIGN.ordinal()] = 847;
            } catch (NoSuchFieldError nosuchfielderror1320) {
                ;
            }

            try {
                aint1[Material.OAK_SLAB.ordinal()] = 231;
            } catch (NoSuchFieldError nosuchfielderror1321) {
                ;
            }

            try {
                aint1[Material.OAK_STAIRS.ordinal()] = 362;
            } catch (NoSuchFieldError nosuchfielderror1322) {
                ;
            }

            try {
                aint1[Material.OAK_TRAPDOOR.ordinal()] = 702;
            } catch (NoSuchFieldError nosuchfielderror1323) {
                ;
            }

            try {
                aint1[Material.OAK_WALL_HANGING_SIGN.ordinal()] = 1274;
            } catch (NoSuchFieldError nosuchfielderror1324) {
                ;
            }

            try {
                aint1[Material.OAK_WALL_SIGN.ordinal()] = 1265;
            } catch (NoSuchFieldError nosuchfielderror1325) {
                ;
            }

            try {
                aint1[Material.OAK_WOOD.ordinal()] = 145;
            } catch (NoSuchFieldError nosuchfielderror1326) {
                ;
            }

            try {
                aint1[Material.OBSERVER.ordinal()] = 645;
            } catch (NoSuchFieldError nosuchfielderror1327) {
                ;
            }

            try {
                aint1[Material.OBSIDIAN.ordinal()] = 269;
            } catch (NoSuchFieldError nosuchfielderror1328) {
                ;
            }

            try {
                aint1[Material.OCELOT_SPAWN_EGG.ordinal()] = 1002;
            } catch (NoSuchFieldError nosuchfielderror1329) {
                ;
            }

            try {
                aint1[Material.OCHRE_FROGLIGHT.ordinal()] = 1213;
            } catch (NoSuchFieldError nosuchfielderror1330) {
                ;
            }

            try {
                aint1[Material.ORANGE_BANNER.ordinal()] = 1089;
            } catch (NoSuchFieldError nosuchfielderror1331) {
                ;
            }

            try {
                aint1[Material.ORANGE_BED.ordinal()] = 926;
            } catch (NoSuchFieldError nosuchfielderror1332) {
                ;
            }

            try {
                aint1[Material.ORANGE_CANDLE.ordinal()] = 1193;
            } catch (NoSuchFieldError nosuchfielderror1333) {
                ;
            }

            try {
                aint1[Material.ORANGE_CANDLE_CAKE.ordinal()] = 1382;
            } catch (NoSuchFieldError nosuchfielderror1334) {
                ;
            }

            try {
                aint1[Material.ORANGE_CARPET.ordinal()] = 426;
            } catch (NoSuchFieldError nosuchfielderror1335) {
                ;
            }

            try {
                aint1[Material.ORANGE_CONCRETE.ordinal()] = 535;
            } catch (NoSuchFieldError nosuchfielderror1336) {
                ;
            }

            try {
                aint1[Material.ORANGE_CONCRETE_POWDER.ordinal()] = 551;
            } catch (NoSuchFieldError nosuchfielderror1337) {
                ;
            }

            try {
                aint1[Material.ORANGE_DYE.ordinal()] = 906;
            } catch (NoSuchFieldError nosuchfielderror1338) {
                ;
            }

            try {
                aint1[Material.ORANGE_GLAZED_TERRACOTTA.ordinal()] = 519;
            } catch (NoSuchFieldError nosuchfielderror1339) {
                ;
            }

            try {
                aint1[Material.ORANGE_SHULKER_BOX.ordinal()] = 503;
            } catch (NoSuchFieldError nosuchfielderror1340) {
                ;
            }

            try {
                aint1[Material.ORANGE_STAINED_GLASS.ordinal()] = 451;
            } catch (NoSuchFieldError nosuchfielderror1341) {
                ;
            }

            try {
                aint1[Material.ORANGE_STAINED_GLASS_PANE.ordinal()] = 467;
            } catch (NoSuchFieldError nosuchfielderror1342) {
                ;
            }

            try {
                aint1[Material.ORANGE_TERRACOTTA.ordinal()] = 407;
            } catch (NoSuchFieldError nosuchfielderror1343) {
                ;
            }

            try {
                aint1[Material.ORANGE_TULIP.ordinal()] = 203;
            } catch (NoSuchFieldError nosuchfielderror1344) {
                ;
            }

            try {
                aint1[Material.ORANGE_WALL_BANNER.ordinal()] = 1335;
            } catch (NoSuchFieldError nosuchfielderror1345) {
                ;
            }

            try {
                aint1[Material.ORANGE_WOOL.ordinal()] = 182;
            } catch (NoSuchFieldError nosuchfielderror1346) {
                ;
            }

            try {
                aint1[Material.OXEYE_DAISY.ordinal()] = 206;
            } catch (NoSuchFieldError nosuchfielderror1347) {
                ;
            }

            try {
                aint1[Material.OXIDIZED_COPPER.ordinal()] = 82;
            } catch (NoSuchFieldError nosuchfielderror1348) {
                ;
            }

            try {
                aint1[Material.OXIDIZED_CUT_COPPER.ordinal()] = 86;
            } catch (NoSuchFieldError nosuchfielderror1349) {
                ;
            }

            try {
                aint1[Material.OXIDIZED_CUT_COPPER_SLAB.ordinal()] = 94;
            } catch (NoSuchFieldError nosuchfielderror1350) {
                ;
            }

            try {
                aint1[Material.OXIDIZED_CUT_COPPER_STAIRS.ordinal()] = 90;
            } catch (NoSuchFieldError nosuchfielderror1351) {
                ;
            }

            try {
                aint1[Material.PACKED_ICE.ordinal()] = 442;
            } catch (NoSuchFieldError nosuchfielderror1352) {
                ;
            }

            try {
                aint1[Material.PACKED_MUD.ordinal()] = 323;
            } catch (NoSuchFieldError nosuchfielderror1353) {
                ;
            }

            try {
                aint1[Material.PAINTING.ordinal()] = 844;
            } catch (NoSuchFieldError nosuchfielderror1354) {
                ;
            }

            try {
                aint1[Material.PANDA_SPAWN_EGG.ordinal()] = 1003;
            } catch (NoSuchFieldError nosuchfielderror1355) {
                ;
            }

            try {
                aint1[Material.PAPER.ordinal()] = 885;
            } catch (NoSuchFieldError nosuchfielderror1356) {
                ;
            }

            try {
                aint1[Material.PARROT_SPAWN_EGG.ordinal()] = 1004;
            } catch (NoSuchFieldError nosuchfielderror1357) {
                ;
            }

            try {
                aint1[Material.PEARLESCENT_FROGLIGHT.ordinal()] = 1215;
            } catch (NoSuchFieldError nosuchfielderror1358) {
                ;
            }

            try {
                aint1[Material.PEONY.ordinal()] = 447;
            } catch (NoSuchFieldError nosuchfielderror1359) {
                ;
            }

            try {
                aint1[Material.PETRIFIED_OAK_SLAB.ordinal()] = 247;
            } catch (NoSuchFieldError nosuchfielderror1360) {
                ;
            }

            try {
                aint1[Material.PHANTOM_MEMBRANE.ordinal()] = 1141;
            } catch (NoSuchFieldError nosuchfielderror1361) {
                ;
            }

            try {
                aint1[Material.PHANTOM_SPAWN_EGG.ordinal()] = 1005;
            } catch (NoSuchFieldError nosuchfielderror1362) {
                ;
            }

            try {
                aint1[Material.PIGLIN_BANNER_PATTERN.ordinal()] = 1152;
            } catch (NoSuchFieldError nosuchfielderror1363) {
                ;
            }

            try {
                aint1[Material.PIGLIN_BRUTE_SPAWN_EGG.ordinal()] = 1008;
            } catch (NoSuchFieldError nosuchfielderror1364) {
                ;
            }

            try {
                aint1[Material.PIGLIN_HEAD.ordinal()] = 1064;
            } catch (NoSuchFieldError nosuchfielderror1365) {
                ;
            }

            try {
                aint1[Material.PIGLIN_SPAWN_EGG.ordinal()] = 1007;
            } catch (NoSuchFieldError nosuchfielderror1366) {
                ;
            }

            try {
                aint1[Material.PIGLIN_WALL_HEAD.ordinal()] = 1333;
            } catch (NoSuchFieldError nosuchfielderror1367) {
                ;
            }

            try {
                aint1[Material.PIG_SPAWN_EGG.ordinal()] = 1006;
            } catch (NoSuchFieldError nosuchfielderror1368) {
                ;
            }

            try {
                aint1[Material.PILLAGER_SPAWN_EGG.ordinal()] = 1009;
            } catch (NoSuchFieldError nosuchfielderror1369) {
                ;
            }

            try {
                aint1[Material.PINK_BANNER.ordinal()] = 1094;
            } catch (NoSuchFieldError nosuchfielderror1370) {
                ;
            }

            try {
                aint1[Material.PINK_BED.ordinal()] = 931;
            } catch (NoSuchFieldError nosuchfielderror1371) {
                ;
            }

            try {
                aint1[Material.PINK_CANDLE.ordinal()] = 1198;
            } catch (NoSuchFieldError nosuchfielderror1372) {
                ;
            }

            try {
                aint1[Material.PINK_CANDLE_CAKE.ordinal()] = 1387;
            } catch (NoSuchFieldError nosuchfielderror1373) {
                ;
            }

            try {
                aint1[Material.PINK_CARPET.ordinal()] = 431;
            } catch (NoSuchFieldError nosuchfielderror1374) {
                ;
            }

            try {
                aint1[Material.PINK_CONCRETE.ordinal()] = 540;
            } catch (NoSuchFieldError nosuchfielderror1375) {
                ;
            }

            try {
                aint1[Material.PINK_CONCRETE_POWDER.ordinal()] = 556;
            } catch (NoSuchFieldError nosuchfielderror1376) {
                ;
            }

            try {
                aint1[Material.PINK_DYE.ordinal()] = 911;
            } catch (NoSuchFieldError nosuchfielderror1377) {
                ;
            }

            try {
                aint1[Material.PINK_GLAZED_TERRACOTTA.ordinal()] = 524;
            } catch (NoSuchFieldError nosuchfielderror1378) {
                ;
            }

            try {
                aint1[Material.PINK_PETALS.ordinal()] = 225;
            } catch (NoSuchFieldError nosuchfielderror1379) {
                ;
            }

            try {
                aint1[Material.PINK_SHULKER_BOX.ordinal()] = 508;
            } catch (NoSuchFieldError nosuchfielderror1380) {
                ;
            }

            try {
                aint1[Material.PINK_STAINED_GLASS.ordinal()] = 456;
            } catch (NoSuchFieldError nosuchfielderror1381) {
                ;
            }

            try {
                aint1[Material.PINK_STAINED_GLASS_PANE.ordinal()] = 472;
            } catch (NoSuchFieldError nosuchfielderror1382) {
                ;
            }

            try {
                aint1[Material.PINK_TERRACOTTA.ordinal()] = 412;
            } catch (NoSuchFieldError nosuchfielderror1383) {
                ;
            }

            try {
                aint1[Material.PINK_TULIP.ordinal()] = 205;
            } catch (NoSuchFieldError nosuchfielderror1384) {
                ;
            }

            try {
                aint1[Material.PINK_WALL_BANNER.ordinal()] = 1340;
            } catch (NoSuchFieldError nosuchfielderror1385) {
                ;
            }

            try {
                aint1[Material.PINK_WOOL.ordinal()] = 187;
            } catch (NoSuchFieldError nosuchfielderror1386) {
                ;
            }

            try {
                aint1[Material.PISTON.ordinal()] = 641;
            } catch (NoSuchFieldError nosuchfielderror1387) {
                ;
            }

            try {
                aint1[Material.PISTON_HEAD.ordinal()] = 1259;
            } catch (NoSuchFieldError nosuchfielderror1388) {
                ;
            }

            try {
                aint1[Material.PITCHER_CROP.ordinal()] = 1351;
            } catch (NoSuchFieldError nosuchfielderror1389) {
                ;
            }

            try {
                aint1[Material.PITCHER_PLANT.ordinal()] = 211;
            } catch (NoSuchFieldError nosuchfielderror1390) {
                ;
            }

            try {
                aint1[Material.PITCHER_POD.ordinal()] = 1108;
            } catch (NoSuchFieldError nosuchfielderror1391) {
                ;
            }

            try {
                aint1[Material.PLAYER_HEAD.ordinal()] = 1060;
            } catch (NoSuchFieldError nosuchfielderror1392) {
                ;
            }

            try {
                aint1[Material.PLAYER_WALL_HEAD.ordinal()] = 1330;
            } catch (NoSuchFieldError nosuchfielderror1393) {
                ;
            }

            try {
                aint1[Material.PLENTY_POTTERY_SHERD.ordinal()] = 1250;
            } catch (NoSuchFieldError nosuchfielderror1394) {
                ;
            }

            try {
                aint1[Material.PODZOL.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror1395) {
                ;
            }

            try {
                aint1[Material.POINTED_DRIPSTONE.ordinal()] = 1212;
            } catch (NoSuchFieldError nosuchfielderror1396) {
                ;
            }

            try {
                aint1[Material.POISONOUS_POTATO.ordinal()] = 1055;
            } catch (NoSuchFieldError nosuchfielderror1397) {
                ;
            }

            try {
                aint1[Material.POLAR_BEAR_SPAWN_EGG.ordinal()] = 1010;
            } catch (NoSuchFieldError nosuchfielderror1398) {
                ;
            }

            try {
                aint1[Material.POLISHED_ANDESITE.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror1399) {
                ;
            }

            try {
                aint1[Material.POLISHED_ANDESITE_SLAB.ordinal()] = 629;
            } catch (NoSuchFieldError nosuchfielderror1400) {
                ;
            }

            try {
                aint1[Material.POLISHED_ANDESITE_STAIRS.ordinal()] = 612;
            } catch (NoSuchFieldError nosuchfielderror1401) {
                ;
            }

            try {
                aint1[Material.POLISHED_BASALT.ordinal()] = 308;
            } catch (NoSuchFieldError nosuchfielderror1402) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE.ordinal()] = 1182;
            } catch (NoSuchFieldError nosuchfielderror1403) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_BRICKS.ordinal()] = 1186;
            } catch (NoSuchFieldError nosuchfielderror1404) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_BRICK_SLAB.ordinal()] = 1187;
            } catch (NoSuchFieldError nosuchfielderror1405) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_BRICK_STAIRS.ordinal()] = 1188;
            } catch (NoSuchFieldError nosuchfielderror1406) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_BRICK_WALL.ordinal()] = 393;
            } catch (NoSuchFieldError nosuchfielderror1407) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_BUTTON.ordinal()] = 662;
            } catch (NoSuchFieldError nosuchfielderror1408) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_PRESSURE_PLATE.ordinal()] = 675;
            } catch (NoSuchFieldError nosuchfielderror1409) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_SLAB.ordinal()] = 1183;
            } catch (NoSuchFieldError nosuchfielderror1410) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_STAIRS.ordinal()] = 1184;
            } catch (NoSuchFieldError nosuchfielderror1411) {
                ;
            }

            try {
                aint1[Material.POLISHED_BLACKSTONE_WALL.ordinal()] = 392;
            } catch (NoSuchFieldError nosuchfielderror1412) {
                ;
            }

            try {
                aint1[Material.POLISHED_DEEPSLATE.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror1413) {
                ;
            }

            try {
                aint1[Material.POLISHED_DEEPSLATE_SLAB.ordinal()] = 632;
            } catch (NoSuchFieldError nosuchfielderror1414) {
                ;
            }

            try {
                aint1[Material.POLISHED_DEEPSLATE_STAIRS.ordinal()] = 615;
            } catch (NoSuchFieldError nosuchfielderror1415) {
                ;
            }

            try {
                aint1[Material.POLISHED_DEEPSLATE_WALL.ordinal()] = 395;
            } catch (NoSuchFieldError nosuchfielderror1416) {
                ;
            }

            try {
                aint1[Material.POLISHED_DIORITE.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror1417) {
                ;
            }

            try {
                aint1[Material.POLISHED_DIORITE_SLAB.ordinal()] = 621;
            } catch (NoSuchFieldError nosuchfielderror1418) {
                ;
            }

            try {
                aint1[Material.POLISHED_DIORITE_STAIRS.ordinal()] = 603;
            } catch (NoSuchFieldError nosuchfielderror1419) {
                ;
            }

            try {
                aint1[Material.POLISHED_GRANITE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror1420) {
                ;
            }

            try {
                aint1[Material.POLISHED_GRANITE_SLAB.ordinal()] = 618;
            } catch (NoSuchFieldError nosuchfielderror1421) {
                ;
            }

            try {
                aint1[Material.POLISHED_GRANITE_STAIRS.ordinal()] = 600;
            } catch (NoSuchFieldError nosuchfielderror1422) {
                ;
            }

            try {
                aint1[Material.POPPED_CHORUS_FRUIT.ordinal()] = 1106;
            } catch (NoSuchFieldError nosuchfielderror1423) {
                ;
            }

            try {
                aint1[Material.POPPY.ordinal()] = 198;
            } catch (NoSuchFieldError nosuchfielderror1424) {
                ;
            }

            try {
                aint1[Material.PORKCHOP.ordinal()] = 842;
            } catch (NoSuchFieldError nosuchfielderror1425) {
                ;
            }

            try {
                aint1[Material.POTATO.ordinal()] = 1053;
            } catch (NoSuchFieldError nosuchfielderror1426) {
                ;
            }

            try {
                aint1[Material.POTATOES.ordinal()] = 1326;
            } catch (NoSuchFieldError nosuchfielderror1427) {
                ;
            }

            try {
                aint1[Material.POTION.ordinal()] = 958;
            } catch (NoSuchFieldError nosuchfielderror1428) {
                ;
            }

            try {
                aint1[Material.POTTED_ACACIA_SAPLING.ordinal()] = 1303;
            } catch (NoSuchFieldError nosuchfielderror1429) {
                ;
            }

            try {
                aint1[Material.POTTED_ALLIUM.ordinal()] = 1311;
            } catch (NoSuchFieldError nosuchfielderror1430) {
                ;
            }

            try {
                aint1[Material.POTTED_AZALEA_BUSH.ordinal()] = 1401;
            } catch (NoSuchFieldError nosuchfielderror1431) {
                ;
            }

            try {
                aint1[Material.POTTED_AZURE_BLUET.ordinal()] = 1312;
            } catch (NoSuchFieldError nosuchfielderror1432) {
                ;
            }

            try {
                aint1[Material.POTTED_BAMBOO.ordinal()] = 1367;
            } catch (NoSuchFieldError nosuchfielderror1433) {
                ;
            }

            try {
                aint1[Material.POTTED_BIRCH_SAPLING.ordinal()] = 1301;
            } catch (NoSuchFieldError nosuchfielderror1434) {
                ;
            }

            try {
                aint1[Material.POTTED_BLUE_ORCHID.ordinal()] = 1310;
            } catch (NoSuchFieldError nosuchfielderror1435) {
                ;
            }

            try {
                aint1[Material.POTTED_BROWN_MUSHROOM.ordinal()] = 1322;
            } catch (NoSuchFieldError nosuchfielderror1436) {
                ;
            }

            try {
                aint1[Material.POTTED_CACTUS.ordinal()] = 1324;
            } catch (NoSuchFieldError nosuchfielderror1437) {
                ;
            }

            try {
                aint1[Material.POTTED_CHERRY_SAPLING.ordinal()] = 1304;
            } catch (NoSuchFieldError nosuchfielderror1438) {
                ;
            }

            try {
                aint1[Material.POTTED_CORNFLOWER.ordinal()] = 1318;
            } catch (NoSuchFieldError nosuchfielderror1439) {
                ;
            }

            try {
                aint1[Material.POTTED_CRIMSON_FUNGUS.ordinal()] = 1376;
            } catch (NoSuchFieldError nosuchfielderror1440) {
                ;
            }

            try {
                aint1[Material.POTTED_CRIMSON_ROOTS.ordinal()] = 1378;
            } catch (NoSuchFieldError nosuchfielderror1441) {
                ;
            }

            try {
                aint1[Material.POTTED_DANDELION.ordinal()] = 1308;
            } catch (NoSuchFieldError nosuchfielderror1442) {
                ;
            }

            try {
                aint1[Material.POTTED_DARK_OAK_SAPLING.ordinal()] = 1305;
            } catch (NoSuchFieldError nosuchfielderror1443) {
                ;
            }

            try {
                aint1[Material.POTTED_DEAD_BUSH.ordinal()] = 1323;
            } catch (NoSuchFieldError nosuchfielderror1444) {
                ;
            }

            try {
                aint1[Material.POTTED_FERN.ordinal()] = 1307;
            } catch (NoSuchFieldError nosuchfielderror1445) {
                ;
            }

            try {
                aint1[Material.POTTED_FLOWERING_AZALEA_BUSH.ordinal()] = 1402;
            } catch (NoSuchFieldError nosuchfielderror1446) {
                ;
            }

            try {
                aint1[Material.POTTED_JUNGLE_SAPLING.ordinal()] = 1302;
            } catch (NoSuchFieldError nosuchfielderror1447) {
                ;
            }

            try {
                aint1[Material.POTTED_LILY_OF_THE_VALLEY.ordinal()] = 1319;
            } catch (NoSuchFieldError nosuchfielderror1448) {
                ;
            }

            try {
                aint1[Material.POTTED_MANGROVE_PROPAGULE.ordinal()] = 1306;
            } catch (NoSuchFieldError nosuchfielderror1449) {
                ;
            }

            try {
                aint1[Material.POTTED_OAK_SAPLING.ordinal()] = 1299;
            } catch (NoSuchFieldError nosuchfielderror1450) {
                ;
            }

            try {
                aint1[Material.POTTED_ORANGE_TULIP.ordinal()] = 1314;
            } catch (NoSuchFieldError nosuchfielderror1451) {
                ;
            }

            try {
                aint1[Material.POTTED_OXEYE_DAISY.ordinal()] = 1317;
            } catch (NoSuchFieldError nosuchfielderror1452) {
                ;
            }

            try {
                aint1[Material.POTTED_PINK_TULIP.ordinal()] = 1316;
            } catch (NoSuchFieldError nosuchfielderror1453) {
                ;
            }

            try {
                aint1[Material.POTTED_POPPY.ordinal()] = 1309;
            } catch (NoSuchFieldError nosuchfielderror1454) {
                ;
            }

            try {
                aint1[Material.POTTED_RED_MUSHROOM.ordinal()] = 1321;
            } catch (NoSuchFieldError nosuchfielderror1455) {
                ;
            }

            try {
                aint1[Material.POTTED_RED_TULIP.ordinal()] = 1313;
            } catch (NoSuchFieldError nosuchfielderror1456) {
                ;
            }

            try {
                aint1[Material.POTTED_SPRUCE_SAPLING.ordinal()] = 1300;
            } catch (NoSuchFieldError nosuchfielderror1457) {
                ;
            }

            try {
                aint1[Material.POTTED_TORCHFLOWER.ordinal()] = 1298;
            } catch (NoSuchFieldError nosuchfielderror1458) {
                ;
            }

            try {
                aint1[Material.POTTED_WARPED_FUNGUS.ordinal()] = 1377;
            } catch (NoSuchFieldError nosuchfielderror1459) {
                ;
            }

            try {
                aint1[Material.POTTED_WARPED_ROOTS.ordinal()] = 1379;
            } catch (NoSuchFieldError nosuchfielderror1460) {
                ;
            }

            try {
                aint1[Material.POTTED_WHITE_TULIP.ordinal()] = 1315;
            } catch (NoSuchFieldError nosuchfielderror1461) {
                ;
            }

            try {
                aint1[Material.POTTED_WITHER_ROSE.ordinal()] = 1320;
            } catch (NoSuchFieldError nosuchfielderror1462) {
                ;
            }

            try {
                aint1[Material.POWDER_SNOW.ordinal()] = 1397;
            } catch (NoSuchFieldError nosuchfielderror1463) {
                ;
            }

            try {
                aint1[Material.POWDER_SNOW_BUCKET.ordinal()] = 872;
            } catch (NoSuchFieldError nosuchfielderror1464) {
                ;
            }

            try {
                aint1[Material.POWDER_SNOW_CAULDRON.ordinal()] = 1294;
            } catch (NoSuchFieldError nosuchfielderror1465) {
                ;
            }

            try {
                aint1[Material.POWERED_RAIL.ordinal()] = 724;
            } catch (NoSuchFieldError nosuchfielderror1466) {
                ;
            }

            try {
                aint1[Material.PRISMARINE.ordinal()] = 482;
            } catch (NoSuchFieldError nosuchfielderror1467) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_BRICKS.ordinal()] = 483;
            } catch (NoSuchFieldError nosuchfielderror1468) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_BRICK_SLAB.ordinal()] = 258;
            } catch (NoSuchFieldError nosuchfielderror1469) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_BRICK_STAIRS.ordinal()] = 486;
            } catch (NoSuchFieldError nosuchfielderror1470) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_CRYSTALS.ordinal()] = 1072;
            } catch (NoSuchFieldError nosuchfielderror1471) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_SHARD.ordinal()] = 1071;
            } catch (NoSuchFieldError nosuchfielderror1472) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_SLAB.ordinal()] = 257;
            } catch (NoSuchFieldError nosuchfielderror1473) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_STAIRS.ordinal()] = 485;
            } catch (NoSuchFieldError nosuchfielderror1474) {
                ;
            }

            try {
                aint1[Material.PRISMARINE_WALL.ordinal()] = 379;
            } catch (NoSuchFieldError nosuchfielderror1475) {
                ;
            }

            try {
                aint1[Material.PRIZE_POTTERY_SHERD.ordinal()] = 1251;
            } catch (NoSuchFieldError nosuchfielderror1476) {
                ;
            }

            try {
                aint1[Material.PUFFERFISH.ordinal()] = 899;
            } catch (NoSuchFieldError nosuchfielderror1477) {
                ;
            }

            try {
                aint1[Material.PUFFERFISH_BUCKET.ordinal()] = 876;
            } catch (NoSuchFieldError nosuchfielderror1478) {
                ;
            }

            try {
                aint1[Material.PUFFERFISH_SPAWN_EGG.ordinal()] = 1011;
            } catch (NoSuchFieldError nosuchfielderror1479) {
                ;
            }

            try {
                aint1[Material.PUMPKIN.ordinal()] = 301;
            } catch (NoSuchFieldError nosuchfielderror1480) {
                ;
            }

            try {
                aint1[Material.PUMPKIN_PIE.ordinal()] = 1066;
            } catch (NoSuchFieldError nosuchfielderror1481) {
                ;
            }

            try {
                aint1[Material.PUMPKIN_SEEDS.ordinal()] = 946;
            } catch (NoSuchFieldError nosuchfielderror1482) {
                ;
            }

            try {
                aint1[Material.PUMPKIN_STEM.ordinal()] = 1290;
            } catch (NoSuchFieldError nosuchfielderror1483) {
                ;
            }

            try {
                aint1[Material.PURPLE_BANNER.ordinal()] = 1098;
            } catch (NoSuchFieldError nosuchfielderror1484) {
                ;
            }

            try {
                aint1[Material.PURPLE_BED.ordinal()] = 935;
            } catch (NoSuchFieldError nosuchfielderror1485) {
                ;
            }

            try {
                aint1[Material.PURPLE_CANDLE.ordinal()] = 1202;
            } catch (NoSuchFieldError nosuchfielderror1486) {
                ;
            }

            try {
                aint1[Material.PURPLE_CANDLE_CAKE.ordinal()] = 1391;
            } catch (NoSuchFieldError nosuchfielderror1487) {
                ;
            }

            try {
                aint1[Material.PURPLE_CARPET.ordinal()] = 435;
            } catch (NoSuchFieldError nosuchfielderror1488) {
                ;
            }

            try {
                aint1[Material.PURPLE_CONCRETE.ordinal()] = 544;
            } catch (NoSuchFieldError nosuchfielderror1489) {
                ;
            }

            try {
                aint1[Material.PURPLE_CONCRETE_POWDER.ordinal()] = 560;
            } catch (NoSuchFieldError nosuchfielderror1490) {
                ;
            }

            try {
                aint1[Material.PURPLE_DYE.ordinal()] = 915;
            } catch (NoSuchFieldError nosuchfielderror1491) {
                ;
            }

            try {
                aint1[Material.PURPLE_GLAZED_TERRACOTTA.ordinal()] = 528;
            } catch (NoSuchFieldError nosuchfielderror1492) {
                ;
            }

            try {
                aint1[Material.PURPLE_SHULKER_BOX.ordinal()] = 512;
            } catch (NoSuchFieldError nosuchfielderror1493) {
                ;
            }

            try {
                aint1[Material.PURPLE_STAINED_GLASS.ordinal()] = 460;
            } catch (NoSuchFieldError nosuchfielderror1494) {
                ;
            }

            try {
                aint1[Material.PURPLE_STAINED_GLASS_PANE.ordinal()] = 476;
            } catch (NoSuchFieldError nosuchfielderror1495) {
                ;
            }

            try {
                aint1[Material.PURPLE_TERRACOTTA.ordinal()] = 416;
            } catch (NoSuchFieldError nosuchfielderror1496) {
                ;
            }

            try {
                aint1[Material.PURPLE_WALL_BANNER.ordinal()] = 1344;
            } catch (NoSuchFieldError nosuchfielderror1497) {
                ;
            }

            try {
                aint1[Material.PURPLE_WOOL.ordinal()] = 191;
            } catch (NoSuchFieldError nosuchfielderror1498) {
                ;
            }

            try {
                aint1[Material.PURPUR_BLOCK.ordinal()] = 274;
            } catch (NoSuchFieldError nosuchfielderror1499) {
                ;
            }

            try {
                aint1[Material.PURPUR_PILLAR.ordinal()] = 275;
            } catch (NoSuchFieldError nosuchfielderror1500) {
                ;
            }

            try {
                aint1[Material.PURPUR_SLAB.ordinal()] = 256;
            } catch (NoSuchFieldError nosuchfielderror1501) {
                ;
            }

            try {
                aint1[Material.PURPUR_STAIRS.ordinal()] = 276;
            } catch (NoSuchFieldError nosuchfielderror1502) {
                ;
            }

            try {
                aint1[Material.QUARTZ.ordinal()] = 768;
            } catch (NoSuchFieldError nosuchfielderror1503) {
                ;
            }

            try {
                aint1[Material.QUARTZ_BLOCK.ordinal()] = 402;
            } catch (NoSuchFieldError nosuchfielderror1504) {
                ;
            }

            try {
                aint1[Material.QUARTZ_BRICKS.ordinal()] = 403;
            } catch (NoSuchFieldError nosuchfielderror1505) {
                ;
            }

            try {
                aint1[Material.QUARTZ_PILLAR.ordinal()] = 404;
            } catch (NoSuchFieldError nosuchfielderror1506) {
                ;
            }

            try {
                aint1[Material.QUARTZ_SLAB.ordinal()] = 253;
            } catch (NoSuchFieldError nosuchfielderror1507) {
                ;
            }

            try {
                aint1[Material.QUARTZ_STAIRS.ordinal()] = 405;
            } catch (NoSuchFieldError nosuchfielderror1508) {
                ;
            }

            try {
                aint1[Material.RABBIT.ordinal()] = 1073;
            } catch (NoSuchFieldError nosuchfielderror1509) {
                ;
            }

            try {
                aint1[Material.RABBIT_FOOT.ordinal()] = 1076;
            } catch (NoSuchFieldError nosuchfielderror1510) {
                ;
            }

            try {
                aint1[Material.RABBIT_HIDE.ordinal()] = 1077;
            } catch (NoSuchFieldError nosuchfielderror1511) {
                ;
            }

            try {
                aint1[Material.RABBIT_SPAWN_EGG.ordinal()] = 1012;
            } catch (NoSuchFieldError nosuchfielderror1512) {
                ;
            }

            try {
                aint1[Material.RABBIT_STEW.ordinal()] = 1075;
            } catch (NoSuchFieldError nosuchfielderror1513) {
                ;
            }

            try {
                aint1[Material.RAIL.ordinal()] = 726;
            } catch (NoSuchFieldError nosuchfielderror1514) {
                ;
            }

            try {
                aint1[Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1234;
            } catch (NoSuchFieldError nosuchfielderror1515) {
                ;
            }

            try {
                aint1[Material.RAVAGER_SPAWN_EGG.ordinal()] = 1013;
            } catch (NoSuchFieldError nosuchfielderror1516) {
                ;
            }

            try {
                aint1[Material.RAW_COPPER.ordinal()] = 772;
            } catch (NoSuchFieldError nosuchfielderror1517) {
                ;
            }

            try {
                aint1[Material.RAW_COPPER_BLOCK.ordinal()] = 71;
            } catch (NoSuchFieldError nosuchfielderror1518) {
                ;
            }

            try {
                aint1[Material.RAW_GOLD.ordinal()] = 774;
            } catch (NoSuchFieldError nosuchfielderror1519) {
                ;
            }

            try {
                aint1[Material.RAW_GOLD_BLOCK.ordinal()] = 72;
            } catch (NoSuchFieldError nosuchfielderror1520) {
                ;
            }

            try {
                aint1[Material.RAW_IRON.ordinal()] = 770;
            } catch (NoSuchFieldError nosuchfielderror1521) {
                ;
            }

            try {
                aint1[Material.RAW_IRON_BLOCK.ordinal()] = 70;
            } catch (NoSuchFieldError nosuchfielderror1522) {
                ;
            }

            try {
                aint1[Material.RECOVERY_COMPASS.ordinal()] = 890;
            } catch (NoSuchFieldError nosuchfielderror1523) {
                ;
            }

            try {
                aint1[Material.REDSTONE.ordinal()] = 636;
            } catch (NoSuchFieldError nosuchfielderror1524) {
                ;
            }

            try {
                aint1[Material.REDSTONE_BLOCK.ordinal()] = 638;
            } catch (NoSuchFieldError nosuchfielderror1525) {
                ;
            }

            try {
                aint1[Material.REDSTONE_LAMP.ordinal()] = 659;
            } catch (NoSuchFieldError nosuchfielderror1526) {
                ;
            }

            try {
                aint1[Material.REDSTONE_ORE.ordinal()] = 58;
            } catch (NoSuchFieldError nosuchfielderror1527) {
                ;
            }

            try {
                aint1[Material.REDSTONE_TORCH.ordinal()] = 637;
            } catch (NoSuchFieldError nosuchfielderror1528) {
                ;
            }

            try {
                aint1[Material.REDSTONE_WALL_TORCH.ordinal()] = 1285;
            } catch (NoSuchFieldError nosuchfielderror1529) {
                ;
            }

            try {
                aint1[Material.REDSTONE_WIRE.ordinal()] = 1264;
            } catch (NoSuchFieldError nosuchfielderror1530) {
                ;
            }

            try {
                aint1[Material.RED_BANNER.ordinal()] = 1102;
            } catch (NoSuchFieldError nosuchfielderror1531) {
                ;
            }

            try {
                aint1[Material.RED_BED.ordinal()] = 939;
            } catch (NoSuchFieldError nosuchfielderror1532) {
                ;
            }

            try {
                aint1[Material.RED_CANDLE.ordinal()] = 1206;
            } catch (NoSuchFieldError nosuchfielderror1533) {
                ;
            }

            try {
                aint1[Material.RED_CANDLE_CAKE.ordinal()] = 1395;
            } catch (NoSuchFieldError nosuchfielderror1534) {
                ;
            }

            try {
                aint1[Material.RED_CARPET.ordinal()] = 439;
            } catch (NoSuchFieldError nosuchfielderror1535) {
                ;
            }

            try {
                aint1[Material.RED_CONCRETE.ordinal()] = 548;
            } catch (NoSuchFieldError nosuchfielderror1536) {
                ;
            }

            try {
                aint1[Material.RED_CONCRETE_POWDER.ordinal()] = 564;
            } catch (NoSuchFieldError nosuchfielderror1537) {
                ;
            }

            try {
                aint1[Material.RED_DYE.ordinal()] = 919;
            } catch (NoSuchFieldError nosuchfielderror1538) {
                ;
            }

            try {
                aint1[Material.RED_GLAZED_TERRACOTTA.ordinal()] = 532;
            } catch (NoSuchFieldError nosuchfielderror1539) {
                ;
            }

            try {
                aint1[Material.RED_MUSHROOM.ordinal()] = 214;
            } catch (NoSuchFieldError nosuchfielderror1540) {
                ;
            }

            try {
                aint1[Material.RED_MUSHROOM_BLOCK.ordinal()] = 332;
            } catch (NoSuchFieldError nosuchfielderror1541) {
                ;
            }

            try {
                aint1[Material.RED_NETHER_BRICKS.ordinal()] = 498;
            } catch (NoSuchFieldError nosuchfielderror1542) {
                ;
            }

            try {
                aint1[Material.RED_NETHER_BRICK_SLAB.ordinal()] = 628;
            } catch (NoSuchFieldError nosuchfielderror1543) {
                ;
            }

            try {
                aint1[Material.RED_NETHER_BRICK_STAIRS.ordinal()] = 611;
            } catch (NoSuchFieldError nosuchfielderror1544) {
                ;
            }

            try {
                aint1[Material.RED_NETHER_BRICK_WALL.ordinal()] = 387;
            } catch (NoSuchFieldError nosuchfielderror1545) {
                ;
            }

            try {
                aint1[Material.RED_SAND.ordinal()] = 48;
            } catch (NoSuchFieldError nosuchfielderror1546) {
                ;
            }

            try {
                aint1[Material.RED_SANDSTONE.ordinal()] = 489;
            } catch (NoSuchFieldError nosuchfielderror1547) {
                ;
            }

            try {
                aint1[Material.RED_SANDSTONE_SLAB.ordinal()] = 254;
            } catch (NoSuchFieldError nosuchfielderror1548) {
                ;
            }

            try {
                aint1[Material.RED_SANDSTONE_STAIRS.ordinal()] = 492;
            } catch (NoSuchFieldError nosuchfielderror1549) {
                ;
            }

            try {
                aint1[Material.RED_SANDSTONE_WALL.ordinal()] = 380;
            } catch (NoSuchFieldError nosuchfielderror1550) {
                ;
            }

            try {
                aint1[Material.RED_SHULKER_BOX.ordinal()] = 516;
            } catch (NoSuchFieldError nosuchfielderror1551) {
                ;
            }

            try {
                aint1[Material.RED_STAINED_GLASS.ordinal()] = 464;
            } catch (NoSuchFieldError nosuchfielderror1552) {
                ;
            }

            try {
                aint1[Material.RED_STAINED_GLASS_PANE.ordinal()] = 480;
            } catch (NoSuchFieldError nosuchfielderror1553) {
                ;
            }

            try {
                aint1[Material.RED_TERRACOTTA.ordinal()] = 420;
            } catch (NoSuchFieldError nosuchfielderror1554) {
                ;
            }

            try {
                aint1[Material.RED_TULIP.ordinal()] = 202;
            } catch (NoSuchFieldError nosuchfielderror1555) {
                ;
            }

            try {
                aint1[Material.RED_WALL_BANNER.ordinal()] = 1348;
            } catch (NoSuchFieldError nosuchfielderror1556) {
                ;
            }

            try {
                aint1[Material.RED_WOOL.ordinal()] = 195;
            } catch (NoSuchFieldError nosuchfielderror1557) {
                ;
            }

            try {
                aint1[Material.REINFORCED_DEEPSLATE.ordinal()] = 330;
            } catch (NoSuchFieldError nosuchfielderror1558) {
                ;
            }

            try {
                aint1[Material.REPEATER.ordinal()] = 639;
            } catch (NoSuchFieldError nosuchfielderror1559) {
                ;
            }

            try {
                aint1[Material.REPEATING_COMMAND_BLOCK.ordinal()] = 493;
            } catch (NoSuchFieldError nosuchfielderror1560) {
                ;
            }

            try {
                aint1[Material.RESPAWN_ANCHOR.ordinal()] = 1190;
            } catch (NoSuchFieldError nosuchfielderror1561) {
                ;
            }

            try {
                aint1[Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1229;
            } catch (NoSuchFieldError nosuchfielderror1562) {
                ;
            }

            try {
                aint1[Material.ROOTED_DIRT.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror1563) {
                ;
            }

            try {
                aint1[Material.ROSE_BUSH.ordinal()] = 446;
            } catch (NoSuchFieldError nosuchfielderror1564) {
                ;
            }

            try {
                aint1[Material.ROTTEN_FLESH.ordinal()] = 952;
            } catch (NoSuchFieldError nosuchfielderror1565) {
                ;
            }

            try {
                aint1[Material.SADDLE.ordinal()] = 728;
            } catch (NoSuchFieldError nosuchfielderror1566) {
                ;
            }

            try {
                aint1[Material.SALMON.ordinal()] = 897;
            } catch (NoSuchFieldError nosuchfielderror1567) {
                ;
            }

            try {
                aint1[Material.SALMON_BUCKET.ordinal()] = 877;
            } catch (NoSuchFieldError nosuchfielderror1568) {
                ;
            }

            try {
                aint1[Material.SALMON_SPAWN_EGG.ordinal()] = 1014;
            } catch (NoSuchFieldError nosuchfielderror1569) {
                ;
            }

            try {
                aint1[Material.SAND.ordinal()] = 45;
            } catch (NoSuchFieldError nosuchfielderror1570) {
                ;
            }

            try {
                aint1[Material.SANDSTONE.ordinal()] = 170;
            } catch (NoSuchFieldError nosuchfielderror1571) {
                ;
            }

            try {
                aint1[Material.SANDSTONE_SLAB.ordinal()] = 245;
            } catch (NoSuchFieldError nosuchfielderror1572) {
                ;
            }

            try {
                aint1[Material.SANDSTONE_STAIRS.ordinal()] = 359;
            } catch (NoSuchFieldError nosuchfielderror1573) {
                ;
            }

            try {
                aint1[Material.SANDSTONE_WALL.ordinal()] = 388;
            } catch (NoSuchFieldError nosuchfielderror1574) {
                ;
            }

            try {
                aint1[Material.SCAFFOLDING.ordinal()] = 635;
            } catch (NoSuchFieldError nosuchfielderror1575) {
                ;
            }

            try {
                aint1[Material.SCULK.ordinal()] = 350;
            } catch (NoSuchFieldError nosuchfielderror1576) {
                ;
            }

            try {
                aint1[Material.SCULK_CATALYST.ordinal()] = 352;
            } catch (NoSuchFieldError nosuchfielderror1577) {
                ;
            }

            try {
                aint1[Material.SCULK_SENSOR.ordinal()] = 654;
            } catch (NoSuchFieldError nosuchfielderror1578) {
                ;
            }

            try {
                aint1[Material.SCULK_SHRIEKER.ordinal()] = 353;
            } catch (NoSuchFieldError nosuchfielderror1579) {
                ;
            }

            try {
                aint1[Material.SCULK_VEIN.ordinal()] = 351;
            } catch (NoSuchFieldError nosuchfielderror1580) {
                ;
            }

            try {
                aint1[Material.SCUTE.ordinal()] = 758;
            } catch (NoSuchFieldError nosuchfielderror1581) {
                ;
            }

            try {
                aint1[Material.SEAGRASS.ordinal()] = 179;
            } catch (NoSuchFieldError nosuchfielderror1582) {
                ;
            }

            try {
                aint1[Material.SEA_LANTERN.ordinal()] = 488;
            } catch (NoSuchFieldError nosuchfielderror1583) {
                ;
            }

            try {
                aint1[Material.SEA_PICKLE.ordinal()] = 180;
            } catch (NoSuchFieldError nosuchfielderror1584) {
                ;
            }

            try {
                aint1[Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1220;
            } catch (NoSuchFieldError nosuchfielderror1585) {
                ;
            }

            try {
                aint1[Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1232;
            } catch (NoSuchFieldError nosuchfielderror1586) {
                ;
            }

            try {
                aint1[Material.SHEAF_POTTERY_SHERD.ordinal()] = 1252;
            } catch (NoSuchFieldError nosuchfielderror1587) {
                ;
            }

            try {
                aint1[Material.SHEARS.ordinal()] = 943;
            } catch (NoSuchFieldError nosuchfielderror1588) {
                ;
            }

            try {
                aint1[Material.SHEEP_SPAWN_EGG.ordinal()] = 1015;
            } catch (NoSuchFieldError nosuchfielderror1589) {
                ;
            }

            try {
                aint1[Material.SHELTER_POTTERY_SHERD.ordinal()] = 1253;
            } catch (NoSuchFieldError nosuchfielderror1590) {
                ;
            }

            try {
                aint1[Material.SHIELD.ordinal()] = 1117;
            } catch (NoSuchFieldError nosuchfielderror1591) {
                ;
            }

            try {
                aint1[Material.SHROOMLIGHT.ordinal()] = 1170;
            } catch (NoSuchFieldError nosuchfielderror1592) {
                ;
            }

            try {
                aint1[Material.SHULKER_BOX.ordinal()] = 501;
            } catch (NoSuchFieldError nosuchfielderror1593) {
                ;
            }

            try {
                aint1[Material.SHULKER_SHELL.ordinal()] = 1119;
            } catch (NoSuchFieldError nosuchfielderror1594) {
                ;
            }

            try {
                aint1[Material.SHULKER_SPAWN_EGG.ordinal()] = 1016;
            } catch (NoSuchFieldError nosuchfielderror1595) {
                ;
            }

            try {
                aint1[Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1233;
            } catch (NoSuchFieldError nosuchfielderror1596) {
                ;
            }

            try {
                aint1[Material.SILVERFISH_SPAWN_EGG.ordinal()] = 1017;
            } catch (NoSuchFieldError nosuchfielderror1597) {
                ;
            }

            try {
                aint1[Material.SKELETON_HORSE_SPAWN_EGG.ordinal()] = 1019;
            } catch (NoSuchFieldError nosuchfielderror1598) {
                ;
            }

            try {
                aint1[Material.SKELETON_SKULL.ordinal()] = 1058;
            } catch (NoSuchFieldError nosuchfielderror1599) {
                ;
            }

            try {
                aint1[Material.SKELETON_SPAWN_EGG.ordinal()] = 1018;
            } catch (NoSuchFieldError nosuchfielderror1600) {
                ;
            }

            try {
                aint1[Material.SKELETON_WALL_SKULL.ordinal()] = 1327;
            } catch (NoSuchFieldError nosuchfielderror1601) {
                ;
            }

            try {
                aint1[Material.SKULL_BANNER_PATTERN.ordinal()] = 1149;
            } catch (NoSuchFieldError nosuchfielderror1602) {
                ;
            }

            try {
                aint1[Material.SKULL_POTTERY_SHERD.ordinal()] = 1254;
            } catch (NoSuchFieldError nosuchfielderror1603) {
                ;
            }

            try {
                aint1[Material.SLIME_BALL.ordinal()] = 887;
            } catch (NoSuchFieldError nosuchfielderror1604) {
                ;
            }

            try {
                aint1[Material.SLIME_BLOCK.ordinal()] = 643;
            } catch (NoSuchFieldError nosuchfielderror1605) {
                ;
            }

            try {
                aint1[Material.SLIME_SPAWN_EGG.ordinal()] = 1020;
            } catch (NoSuchFieldError nosuchfielderror1606) {
                ;
            }

            try {
                aint1[Material.SMALL_AMETHYST_BUD.ordinal()] = 1208;
            } catch (NoSuchFieldError nosuchfielderror1607) {
                ;
            }

            try {
                aint1[Material.SMALL_DRIPLEAF.ordinal()] = 229;
            } catch (NoSuchFieldError nosuchfielderror1608) {
                ;
            }

            try {
                aint1[Material.SMITHING_TABLE.ordinal()] = 1161;
            } catch (NoSuchFieldError nosuchfielderror1609) {
                ;
            }

            try {
                aint1[Material.SMOKER.ordinal()] = 1156;
            } catch (NoSuchFieldError nosuchfielderror1610) {
                ;
            }

            try {
                aint1[Material.SMOOTH_BASALT.ordinal()] = 309;
            } catch (NoSuchFieldError nosuchfielderror1611) {
                ;
            }

            try {
                aint1[Material.SMOOTH_QUARTZ.ordinal()] = 260;
            } catch (NoSuchFieldError nosuchfielderror1612) {
                ;
            }

            try {
                aint1[Material.SMOOTH_QUARTZ_SLAB.ordinal()] = 625;
            } catch (NoSuchFieldError nosuchfielderror1613) {
                ;
            }

            try {
                aint1[Material.SMOOTH_QUARTZ_STAIRS.ordinal()] = 608;
            } catch (NoSuchFieldError nosuchfielderror1614) {
                ;
            }

            try {
                aint1[Material.SMOOTH_RED_SANDSTONE.ordinal()] = 261;
            } catch (NoSuchFieldError nosuchfielderror1615) {
                ;
            }

            try {
                aint1[Material.SMOOTH_RED_SANDSTONE_SLAB.ordinal()] = 619;
            } catch (NoSuchFieldError nosuchfielderror1616) {
                ;
            }

            try {
                aint1[Material.SMOOTH_RED_SANDSTONE_STAIRS.ordinal()] = 601;
            } catch (NoSuchFieldError nosuchfielderror1617) {
                ;
            }

            try {
                aint1[Material.SMOOTH_SANDSTONE.ordinal()] = 262;
            } catch (NoSuchFieldError nosuchfielderror1618) {
                ;
            }

            try {
                aint1[Material.SMOOTH_SANDSTONE_SLAB.ordinal()] = 624;
            } catch (NoSuchFieldError nosuchfielderror1619) {
                ;
            }

            try {
                aint1[Material.SMOOTH_SANDSTONE_STAIRS.ordinal()] = 607;
            } catch (NoSuchFieldError nosuchfielderror1620) {
                ;
            }

            try {
                aint1[Material.SMOOTH_STONE.ordinal()] = 263;
            } catch (NoSuchFieldError nosuchfielderror1621) {
                ;
            }

            try {
                aint1[Material.SMOOTH_STONE_SLAB.ordinal()] = 244;
            } catch (NoSuchFieldError nosuchfielderror1622) {
                ;
            }

            try {
                aint1[Material.SNIFFER_EGG.ordinal()] = 567;
            } catch (NoSuchFieldError nosuchfielderror1623) {
                ;
            }

            try {
                aint1[Material.SNIFFER_SPAWN_EGG.ordinal()] = 1021;
            } catch (NoSuchFieldError nosuchfielderror1624) {
                ;
            }

            try {
                aint1[Material.SNORT_POTTERY_SHERD.ordinal()] = 1255;
            } catch (NoSuchFieldError nosuchfielderror1625) {
                ;
            }

            try {
                aint1[Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1228;
            } catch (NoSuchFieldError nosuchfielderror1626) {
                ;
            }

            try {
                aint1[Material.SNOW.ordinal()] = 284;
            } catch (NoSuchFieldError nosuchfielderror1627) {
                ;
            }

            try {
                aint1[Material.SNOWBALL.ordinal()] = 873;
            } catch (NoSuchFieldError nosuchfielderror1628) {
                ;
            }

            try {
                aint1[Material.SNOW_BLOCK.ordinal()] = 286;
            } catch (NoSuchFieldError nosuchfielderror1629) {
                ;
            }

            try {
                aint1[Material.SNOW_GOLEM_SPAWN_EGG.ordinal()] = 1022;
            } catch (NoSuchFieldError nosuchfielderror1630) {
                ;
            }

            try {
                aint1[Material.SOUL_CAMPFIRE.ordinal()] = 1169;
            } catch (NoSuchFieldError nosuchfielderror1631) {
                ;
            }

            try {
                aint1[Material.SOUL_FIRE.ordinal()] = 1263;
            } catch (NoSuchFieldError nosuchfielderror1632) {
                ;
            }

            try {
                aint1[Material.SOUL_LANTERN.ordinal()] = 1165;
            } catch (NoSuchFieldError nosuchfielderror1633) {
                ;
            }

            try {
                aint1[Material.SOUL_SAND.ordinal()] = 305;
            } catch (NoSuchFieldError nosuchfielderror1634) {
                ;
            }

            try {
                aint1[Material.SOUL_SOIL.ordinal()] = 306;
            } catch (NoSuchFieldError nosuchfielderror1635) {
                ;
            }

            try {
                aint1[Material.SOUL_TORCH.ordinal()] = 310;
            } catch (NoSuchFieldError nosuchfielderror1636) {
                ;
            }

            try {
                aint1[Material.SOUL_WALL_TORCH.ordinal()] = 1286;
            } catch (NoSuchFieldError nosuchfielderror1637) {
                ;
            }

            try {
                aint1[Material.SPAWNER.ordinal()] = 277;
            } catch (NoSuchFieldError nosuchfielderror1638) {
                ;
            }

            try {
                aint1[Material.SPECTRAL_ARROW.ordinal()] = 1114;
            } catch (NoSuchFieldError nosuchfielderror1639) {
                ;
            }

            try {
                aint1[Material.SPIDER_EYE.ordinal()] = 960;
            } catch (NoSuchFieldError nosuchfielderror1640) {
                ;
            }

            try {
                aint1[Material.SPIDER_SPAWN_EGG.ordinal()] = 1023;
            } catch (NoSuchFieldError nosuchfielderror1641) {
                ;
            }

            try {
                aint1[Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1230;
            } catch (NoSuchFieldError nosuchfielderror1642) {
                ;
            }

            try {
                aint1[Material.SPLASH_POTION.ordinal()] = 1113;
            } catch (NoSuchFieldError nosuchfielderror1643) {
                ;
            }

            try {
                aint1[Material.SPONGE.ordinal()] = 165;
            } catch (NoSuchFieldError nosuchfielderror1644) {
                ;
            }

            try {
                aint1[Material.SPORE_BLOSSOM.ordinal()] = 212;
            } catch (NoSuchFieldError nosuchfielderror1645) {
                ;
            }

            try {
                aint1[Material.SPRUCE_BOAT.ordinal()] = 739;
            } catch (NoSuchFieldError nosuchfielderror1646) {
                ;
            }

            try {
                aint1[Material.SPRUCE_BUTTON.ordinal()] = 664;
            } catch (NoSuchFieldError nosuchfielderror1647) {
                ;
            }

            try {
                aint1[Material.SPRUCE_CHEST_BOAT.ordinal()] = 740;
            } catch (NoSuchFieldError nosuchfielderror1648) {
                ;
            }

            try {
                aint1[Material.SPRUCE_DOOR.ordinal()] = 691;
            } catch (NoSuchFieldError nosuchfielderror1649) {
                ;
            }

            try {
                aint1[Material.SPRUCE_FENCE.ordinal()] = 291;
            } catch (NoSuchFieldError nosuchfielderror1650) {
                ;
            }

            try {
                aint1[Material.SPRUCE_FENCE_GATE.ordinal()] = 714;
            } catch (NoSuchFieldError nosuchfielderror1651) {
                ;
            }

            try {
                aint1[Material.SPRUCE_HANGING_SIGN.ordinal()] = 859;
            } catch (NoSuchFieldError nosuchfielderror1652) {
                ;
            }

            try {
                aint1[Material.SPRUCE_LEAVES.ordinal()] = 156;
            } catch (NoSuchFieldError nosuchfielderror1653) {
                ;
            }

            try {
                aint1[Material.SPRUCE_LOG.ordinal()] = 112;
            } catch (NoSuchFieldError nosuchfielderror1654) {
                ;
            }

            try {
                aint1[Material.SPRUCE_PLANKS.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror1655) {
                ;
            }

            try {
                aint1[Material.SPRUCE_PRESSURE_PLATE.ordinal()] = 679;
            } catch (NoSuchFieldError nosuchfielderror1656) {
                ;
            }

            try {
                aint1[Material.SPRUCE_SAPLING.ordinal()] = 37;
            } catch (NoSuchFieldError nosuchfielderror1657) {
                ;
            }

            try {
                aint1[Material.SPRUCE_SIGN.ordinal()] = 848;
            } catch (NoSuchFieldError nosuchfielderror1658) {
                ;
            }

            try {
                aint1[Material.SPRUCE_SLAB.ordinal()] = 232;
            } catch (NoSuchFieldError nosuchfielderror1659) {
                ;
            }

            try {
                aint1[Material.SPRUCE_STAIRS.ordinal()] = 363;
            } catch (NoSuchFieldError nosuchfielderror1660) {
                ;
            }

            try {
                aint1[Material.SPRUCE_TRAPDOOR.ordinal()] = 703;
            } catch (NoSuchFieldError nosuchfielderror1661) {
                ;
            }

            try {
                aint1[Material.SPRUCE_WALL_HANGING_SIGN.ordinal()] = 1275;
            } catch (NoSuchFieldError nosuchfielderror1662) {
                ;
            }

            try {
                aint1[Material.SPRUCE_WALL_SIGN.ordinal()] = 1266;
            } catch (NoSuchFieldError nosuchfielderror1663) {
                ;
            }

            try {
                aint1[Material.SPRUCE_WOOD.ordinal()] = 146;
            } catch (NoSuchFieldError nosuchfielderror1664) {
                ;
            }

            try {
                aint1[Material.SPYGLASS.ordinal()] = 894;
            } catch (NoSuchFieldError nosuchfielderror1665) {
                ;
            }

            try {
                aint1[Material.SQUID_SPAWN_EGG.ordinal()] = 1024;
            } catch (NoSuchFieldError nosuchfielderror1666) {
                ;
            }

            try {
                aint1[Material.STICK.ordinal()] = 808;
            } catch (NoSuchFieldError nosuchfielderror1667) {
                ;
            }

            try {
                aint1[Material.STICKY_PISTON.ordinal()] = 642;
            } catch (NoSuchFieldError nosuchfielderror1668) {
                ;
            }

            try {
                aint1[Material.STONE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1669) {
                ;
            }

            try {
                aint1[Material.STONECUTTER.ordinal()] = 1162;
            } catch (NoSuchFieldError nosuchfielderror1670) {
                ;
            }

            try {
                aint1[Material.STONE_AXE.ordinal()] = 786;
            } catch (NoSuchFieldError nosuchfielderror1671) {
                ;
            }

            try {
                aint1[Material.STONE_BRICKS.ordinal()] = 319;
            } catch (NoSuchFieldError nosuchfielderror1672) {
                ;
            }

            try {
                aint1[Material.STONE_BRICK_SLAB.ordinal()] = 250;
            } catch (NoSuchFieldError nosuchfielderror1673) {
                ;
            }

            try {
                aint1[Material.STONE_BRICK_STAIRS.ordinal()] = 341;
            } catch (NoSuchFieldError nosuchfielderror1674) {
                ;
            }

            try {
                aint1[Material.STONE_BRICK_WALL.ordinal()] = 383;
            } catch (NoSuchFieldError nosuchfielderror1675) {
                ;
            }

            try {
                aint1[Material.STONE_BUTTON.ordinal()] = 661;
            } catch (NoSuchFieldError nosuchfielderror1676) {
                ;
            }

            try {
                aint1[Material.STONE_HOE.ordinal()] = 787;
            } catch (NoSuchFieldError nosuchfielderror1677) {
                ;
            }

            try {
                aint1[Material.STONE_PICKAXE.ordinal()] = 785;
            } catch (NoSuchFieldError nosuchfielderror1678) {
                ;
            }

            try {
                aint1[Material.STONE_PRESSURE_PLATE.ordinal()] = 674;
            } catch (NoSuchFieldError nosuchfielderror1679) {
                ;
            }

            try {
                aint1[Material.STONE_SHOVEL.ordinal()] = 784;
            } catch (NoSuchFieldError nosuchfielderror1680) {
                ;
            }

            try {
                aint1[Material.STONE_SLAB.ordinal()] = 243;
            } catch (NoSuchFieldError nosuchfielderror1681) {
                ;
            }

            try {
                aint1[Material.STONE_STAIRS.ordinal()] = 606;
            } catch (NoSuchFieldError nosuchfielderror1682) {
                ;
            }

            try {
                aint1[Material.STONE_SWORD.ordinal()] = 783;
            } catch (NoSuchFieldError nosuchfielderror1683) {
                ;
            }

            try {
                aint1[Material.STRAY_SPAWN_EGG.ordinal()] = 1025;
            } catch (NoSuchFieldError nosuchfielderror1684) {
                ;
            }

            try {
                aint1[Material.STRIDER_SPAWN_EGG.ordinal()] = 1026;
            } catch (NoSuchFieldError nosuchfielderror1685) {
                ;
            }

            try {
                aint1[Material.STRING.ordinal()] = 811;
            } catch (NoSuchFieldError nosuchfielderror1686) {
                ;
            }

            try {
                aint1[Material.STRIPPED_ACACIA_LOG.ordinal()] = 128;
            } catch (NoSuchFieldError nosuchfielderror1687) {
                ;
            }

            try {
                aint1[Material.STRIPPED_ACACIA_WOOD.ordinal()] = 138;
            } catch (NoSuchFieldError nosuchfielderror1688) {
                ;
            }

            try {
                aint1[Material.STRIPPED_BAMBOO_BLOCK.ordinal()] = 144;
            } catch (NoSuchFieldError nosuchfielderror1689) {
                ;
            }

            try {
                aint1[Material.STRIPPED_BIRCH_LOG.ordinal()] = 126;
            } catch (NoSuchFieldError nosuchfielderror1690) {
                ;
            }

            try {
                aint1[Material.STRIPPED_BIRCH_WOOD.ordinal()] = 136;
            } catch (NoSuchFieldError nosuchfielderror1691) {
                ;
            }

            try {
                aint1[Material.STRIPPED_CHERRY_LOG.ordinal()] = 129;
            } catch (NoSuchFieldError nosuchfielderror1692) {
                ;
            }

            try {
                aint1[Material.STRIPPED_CHERRY_WOOD.ordinal()] = 139;
            } catch (NoSuchFieldError nosuchfielderror1693) {
                ;
            }

            try {
                aint1[Material.STRIPPED_CRIMSON_HYPHAE.ordinal()] = 142;
            } catch (NoSuchFieldError nosuchfielderror1694) {
                ;
            }

            try {
                aint1[Material.STRIPPED_CRIMSON_STEM.ordinal()] = 132;
            } catch (NoSuchFieldError nosuchfielderror1695) {
                ;
            }

            try {
                aint1[Material.STRIPPED_DARK_OAK_LOG.ordinal()] = 130;
            } catch (NoSuchFieldError nosuchfielderror1696) {
                ;
            }

            try {
                aint1[Material.STRIPPED_DARK_OAK_WOOD.ordinal()] = 140;
            } catch (NoSuchFieldError nosuchfielderror1697) {
                ;
            }

            try {
                aint1[Material.STRIPPED_JUNGLE_LOG.ordinal()] = 127;
            } catch (NoSuchFieldError nosuchfielderror1698) {
                ;
            }

            try {
                aint1[Material.STRIPPED_JUNGLE_WOOD.ordinal()] = 137;
            } catch (NoSuchFieldError nosuchfielderror1699) {
                ;
            }

            try {
                aint1[Material.STRIPPED_MANGROVE_LOG.ordinal()] = 131;
            } catch (NoSuchFieldError nosuchfielderror1700) {
                ;
            }

            try {
                aint1[Material.STRIPPED_MANGROVE_WOOD.ordinal()] = 141;
            } catch (NoSuchFieldError nosuchfielderror1701) {
                ;
            }

            try {
                aint1[Material.STRIPPED_OAK_LOG.ordinal()] = 124;
            } catch (NoSuchFieldError nosuchfielderror1702) {
                ;
            }

            try {
                aint1[Material.STRIPPED_OAK_WOOD.ordinal()] = 134;
            } catch (NoSuchFieldError nosuchfielderror1703) {
                ;
            }

            try {
                aint1[Material.STRIPPED_SPRUCE_LOG.ordinal()] = 125;
            } catch (NoSuchFieldError nosuchfielderror1704) {
                ;
            }

            try {
                aint1[Material.STRIPPED_SPRUCE_WOOD.ordinal()] = 135;
            } catch (NoSuchFieldError nosuchfielderror1705) {
                ;
            }

            try {
                aint1[Material.STRIPPED_WARPED_HYPHAE.ordinal()] = 143;
            } catch (NoSuchFieldError nosuchfielderror1706) {
                ;
            }

            try {
                aint1[Material.STRIPPED_WARPED_STEM.ordinal()] = 133;
            } catch (NoSuchFieldError nosuchfielderror1707) {
                ;
            }

            try {
                aint1[Material.STRUCTURE_BLOCK.ordinal()] = 755;
            } catch (NoSuchFieldError nosuchfielderror1708) {
                ;
            }

            try {
                aint1[Material.STRUCTURE_VOID.ordinal()] = 500;
            } catch (NoSuchFieldError nosuchfielderror1709) {
                ;
            }

            try {
                aint1[Material.SUGAR.ordinal()] = 923;
            } catch (NoSuchFieldError nosuchfielderror1710) {
                ;
            }

            try {
                aint1[Material.SUGAR_CANE.ordinal()] = 222;
            } catch (NoSuchFieldError nosuchfielderror1711) {
                ;
            }

            try {
                aint1[Material.SUNFLOWER.ordinal()] = 444;
            } catch (NoSuchFieldError nosuchfielderror1712) {
                ;
            }

            try {
                aint1[Material.SUSPICIOUS_GRAVEL.ordinal()] = 47;
            } catch (NoSuchFieldError nosuchfielderror1713) {
                ;
            }

            try {
                aint1[Material.SUSPICIOUS_SAND.ordinal()] = 46;
            } catch (NoSuchFieldError nosuchfielderror1714) {
                ;
            }

            try {
                aint1[Material.SUSPICIOUS_STEW.ordinal()] = 1145;
            } catch (NoSuchFieldError nosuchfielderror1715) {
                ;
            }

            try {
                aint1[Material.SWEET_BERRIES.ordinal()] = 1166;
            } catch (NoSuchFieldError nosuchfielderror1716) {
                ;
            }

            try {
                aint1[Material.SWEET_BERRY_BUSH.ordinal()] = 1371;
            } catch (NoSuchFieldError nosuchfielderror1717) {
                ;
            }

            try {
                aint1[Material.TADPOLE_BUCKET.ordinal()] = 881;
            } catch (NoSuchFieldError nosuchfielderror1718) {
                ;
            }

            try {
                aint1[Material.TADPOLE_SPAWN_EGG.ordinal()] = 1027;
            } catch (NoSuchFieldError nosuchfielderror1719) {
                ;
            }

            try {
                aint1[Material.TALL_GRASS.ordinal()] = 448;
            } catch (NoSuchFieldError nosuchfielderror1720) {
                ;
            }

            try {
                aint1[Material.TALL_SEAGRASS.ordinal()] = 1258;
            } catch (NoSuchFieldError nosuchfielderror1721) {
                ;
            }

            try {
                aint1[Material.TARGET.ordinal()] = 650;
            } catch (NoSuchFieldError nosuchfielderror1722) {
                ;
            }

            try {
                aint1[Material.TERRACOTTA.ordinal()] = 441;
            } catch (NoSuchFieldError nosuchfielderror1723) {
                ;
            }

            try {
                aint1[Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1227;
            } catch (NoSuchFieldError nosuchfielderror1724) {
                ;
            }

            try {
                aint1[Material.TINTED_GLASS.ordinal()] = 168;
            } catch (NoSuchFieldError nosuchfielderror1725) {
                ;
            }

            try {
                aint1[Material.TIPPED_ARROW.ordinal()] = 1115;
            } catch (NoSuchFieldError nosuchfielderror1726) {
                ;
            }

            try {
                aint1[Material.TNT.ordinal()] = 658;
            } catch (NoSuchFieldError nosuchfielderror1727) {
                ;
            }

            try {
                aint1[Material.TNT_MINECART.ordinal()] = 732;
            } catch (NoSuchFieldError nosuchfielderror1728) {
                ;
            }

            try {
                aint1[Material.TORCH.ordinal()] = 270;
            } catch (NoSuchFieldError nosuchfielderror1729) {
                ;
            }

            try {
                aint1[Material.TORCHFLOWER.ordinal()] = 210;
            } catch (NoSuchFieldError nosuchfielderror1730) {
                ;
            }

            try {
                aint1[Material.TORCHFLOWER_CROP.ordinal()] = 1350;
            } catch (NoSuchFieldError nosuchfielderror1731) {
                ;
            }

            try {
                aint1[Material.TORCHFLOWER_SEEDS.ordinal()] = 1107;
            } catch (NoSuchFieldError nosuchfielderror1732) {
                ;
            }

            try {
                aint1[Material.TOTEM_OF_UNDYING.ordinal()] = 1118;
            } catch (NoSuchFieldError nosuchfielderror1733) {
                ;
            }

            try {
                aint1[Material.TRADER_LLAMA_SPAWN_EGG.ordinal()] = 1028;
            } catch (NoSuchFieldError nosuchfielderror1734) {
                ;
            }

            try {
                aint1[Material.TRAPPED_CHEST.ordinal()] = 657;
            } catch (NoSuchFieldError nosuchfielderror1735) {
                ;
            }

            try {
                aint1[Material.TRIDENT.ordinal()] = 1140;
            } catch (NoSuchFieldError nosuchfielderror1736) {
                ;
            }

            try {
                aint1[Material.TRIPWIRE.ordinal()] = 1297;
            } catch (NoSuchFieldError nosuchfielderror1737) {
                ;
            }

            try {
                aint1[Material.TRIPWIRE_HOOK.ordinal()] = 656;
            } catch (NoSuchFieldError nosuchfielderror1738) {
                ;
            }

            try {
                aint1[Material.TROPICAL_FISH.ordinal()] = 898;
            } catch (NoSuchFieldError nosuchfielderror1739) {
                ;
            }

            try {
                aint1[Material.TROPICAL_FISH_BUCKET.ordinal()] = 879;
            } catch (NoSuchFieldError nosuchfielderror1740) {
                ;
            }

            try {
                aint1[Material.TROPICAL_FISH_SPAWN_EGG.ordinal()] = 1029;
            } catch (NoSuchFieldError nosuchfielderror1741) {
                ;
            }

            try {
                aint1[Material.TUBE_CORAL.ordinal()] = 578;
            } catch (NoSuchFieldError nosuchfielderror1742) {
                ;
            }

            try {
                aint1[Material.TUBE_CORAL_BLOCK.ordinal()] = 573;
            } catch (NoSuchFieldError nosuchfielderror1743) {
                ;
            }

            try {
                aint1[Material.TUBE_CORAL_FAN.ordinal()] = 588;
            } catch (NoSuchFieldError nosuchfielderror1744) {
                ;
            }

            try {
                aint1[Material.TUBE_CORAL_WALL_FAN.ordinal()] = 1361;
            } catch (NoSuchFieldError nosuchfielderror1745) {
                ;
            }

            try {
                aint1[Material.TUFF.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror1746) {
                ;
            }

            try {
                aint1[Material.TURTLE_EGG.ordinal()] = 566;
            } catch (NoSuchFieldError nosuchfielderror1747) {
                ;
            }

            try {
                aint1[Material.TURTLE_HELMET.ordinal()] = 757;
            } catch (NoSuchFieldError nosuchfielderror1748) {
                ;
            }

            try {
                aint1[Material.TURTLE_SPAWN_EGG.ordinal()] = 1030;
            } catch (NoSuchFieldError nosuchfielderror1749) {
                ;
            }

            try {
                aint1[Material.TWISTING_VINES.ordinal()] = 221;
            } catch (NoSuchFieldError nosuchfielderror1750) {
                ;
            }

            try {
                aint1[Material.TWISTING_VINES_PLANT.ordinal()] = 1373;
            } catch (NoSuchFieldError nosuchfielderror1751) {
                ;
            }

            try {
                aint1[Material.VERDANT_FROGLIGHT.ordinal()] = 1214;
            } catch (NoSuchFieldError nosuchfielderror1752) {
                ;
            }

            try {
                aint1[Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1226;
            } catch (NoSuchFieldError nosuchfielderror1753) {
                ;
            }

            try {
                aint1[Material.VEX_SPAWN_EGG.ordinal()] = 1031;
            } catch (NoSuchFieldError nosuchfielderror1754) {
                ;
            }

            try {
                aint1[Material.VILLAGER_SPAWN_EGG.ordinal()] = 1032;
            } catch (NoSuchFieldError nosuchfielderror1755) {
                ;
            }

            try {
                aint1[Material.VINDICATOR_SPAWN_EGG.ordinal()] = 1033;
            } catch (NoSuchFieldError nosuchfielderror1756) {
                ;
            }

            try {
                aint1[Material.VINE.ordinal()] = 338;
            } catch (NoSuchFieldError nosuchfielderror1757) {
                ;
            }

            try {
                aint1[Material.VOID_AIR.ordinal()] = 1368;
            } catch (NoSuchFieldError nosuchfielderror1758) {
                ;
            }

            try {
                aint1[Material.WALL_TORCH.ordinal()] = 1261;
            } catch (NoSuchFieldError nosuchfielderror1759) {
                ;
            }

            try {
                aint1[Material.WANDERING_TRADER_SPAWN_EGG.ordinal()] = 1034;
            } catch (NoSuchFieldError nosuchfielderror1760) {
                ;
            }

            try {
                aint1[Material.WARDEN_SPAWN_EGG.ordinal()] = 1035;
            } catch (NoSuchFieldError nosuchfielderror1761) {
                ;
            }

            try {
                aint1[Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1224;
            } catch (NoSuchFieldError nosuchfielderror1762) {
                ;
            }

            try {
                aint1[Material.WARPED_BUTTON.ordinal()] = 673;
            } catch (NoSuchFieldError nosuchfielderror1763) {
                ;
            }

            try {
                aint1[Material.WARPED_DOOR.ordinal()] = 700;
            } catch (NoSuchFieldError nosuchfielderror1764) {
                ;
            }

            try {
                aint1[Material.WARPED_FENCE.ordinal()] = 300;
            } catch (NoSuchFieldError nosuchfielderror1765) {
                ;
            }

            try {
                aint1[Material.WARPED_FENCE_GATE.ordinal()] = 723;
            } catch (NoSuchFieldError nosuchfielderror1766) {
                ;
            }

            try {
                aint1[Material.WARPED_FUNGUS.ordinal()] = 216;
            } catch (NoSuchFieldError nosuchfielderror1767) {
                ;
            }

            try {
                aint1[Material.WARPED_FUNGUS_ON_A_STICK.ordinal()] = 735;
            } catch (NoSuchFieldError nosuchfielderror1768) {
                ;
            }

            try {
                aint1[Material.WARPED_HANGING_SIGN.ordinal()] = 868;
            } catch (NoSuchFieldError nosuchfielderror1769) {
                ;
            }

            try {
                aint1[Material.WARPED_HYPHAE.ordinal()] = 154;
            } catch (NoSuchFieldError nosuchfielderror1770) {
                ;
            }

            try {
                aint1[Material.WARPED_NYLIUM.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror1771) {
                ;
            }

            try {
                aint1[Material.WARPED_PLANKS.ordinal()] = 34;
            } catch (NoSuchFieldError nosuchfielderror1772) {
                ;
            }

            try {
                aint1[Material.WARPED_PRESSURE_PLATE.ordinal()] = 688;
            } catch (NoSuchFieldError nosuchfielderror1773) {
                ;
            }

            try {
                aint1[Material.WARPED_ROOTS.ordinal()] = 218;
            } catch (NoSuchFieldError nosuchfielderror1774) {
                ;
            }

            try {
                aint1[Material.WARPED_SIGN.ordinal()] = 857;
            } catch (NoSuchFieldError nosuchfielderror1775) {
                ;
            }

            try {
                aint1[Material.WARPED_SLAB.ordinal()] = 242;
            } catch (NoSuchFieldError nosuchfielderror1776) {
                ;
            }

            try {
                aint1[Material.WARPED_STAIRS.ordinal()] = 373;
            } catch (NoSuchFieldError nosuchfielderror1777) {
                ;
            }

            try {
                aint1[Material.WARPED_STEM.ordinal()] = 122;
            } catch (NoSuchFieldError nosuchfielderror1778) {
                ;
            }

            try {
                aint1[Material.WARPED_TRAPDOOR.ordinal()] = 712;
            } catch (NoSuchFieldError nosuchfielderror1779) {
                ;
            }

            try {
                aint1[Material.WARPED_WALL_HANGING_SIGN.ordinal()] = 1283;
            } catch (NoSuchFieldError nosuchfielderror1780) {
                ;
            }

            try {
                aint1[Material.WARPED_WALL_SIGN.ordinal()] = 1375;
            } catch (NoSuchFieldError nosuchfielderror1781) {
                ;
            }

            try {
                aint1[Material.WARPED_WART_BLOCK.ordinal()] = 497;
            } catch (NoSuchFieldError nosuchfielderror1782) {
                ;
            }

            try {
                aint1[Material.WATER.ordinal()] = 1256;
            } catch (NoSuchFieldError nosuchfielderror1783) {
                ;
            }

            try {
                aint1[Material.WATER_BUCKET.ordinal()] = 870;
            } catch (NoSuchFieldError nosuchfielderror1784) {
                ;
            }

            try {
                aint1[Material.WATER_CAULDRON.ordinal()] = 1292;
            } catch (NoSuchFieldError nosuchfielderror1785) {
                ;
            }

            try {
                aint1[Material.WAXED_COPPER_BLOCK.ordinal()] = 95;
            } catch (NoSuchFieldError nosuchfielderror1786) {
                ;
            }

            try {
                aint1[Material.WAXED_CUT_COPPER.ordinal()] = 99;
            } catch (NoSuchFieldError nosuchfielderror1787) {
                ;
            }

            try {
                aint1[Material.WAXED_CUT_COPPER_SLAB.ordinal()] = 107;
            } catch (NoSuchFieldError nosuchfielderror1788) {
                ;
            }

            try {
                aint1[Material.WAXED_CUT_COPPER_STAIRS.ordinal()] = 103;
            } catch (NoSuchFieldError nosuchfielderror1789) {
                ;
            }

            try {
                aint1[Material.WAXED_EXPOSED_COPPER.ordinal()] = 96;
            } catch (NoSuchFieldError nosuchfielderror1790) {
                ;
            }

            try {
                aint1[Material.WAXED_EXPOSED_CUT_COPPER.ordinal()] = 100;
            } catch (NoSuchFieldError nosuchfielderror1791) {
                ;
            }

            try {
                aint1[Material.WAXED_EXPOSED_CUT_COPPER_SLAB.ordinal()] = 108;
            } catch (NoSuchFieldError nosuchfielderror1792) {
                ;
            }

            try {
                aint1[Material.WAXED_EXPOSED_CUT_COPPER_STAIRS.ordinal()] = 104;
            } catch (NoSuchFieldError nosuchfielderror1793) {
                ;
            }

            try {
                aint1[Material.WAXED_OXIDIZED_COPPER.ordinal()] = 98;
            } catch (NoSuchFieldError nosuchfielderror1794) {
                ;
            }

            try {
                aint1[Material.WAXED_OXIDIZED_CUT_COPPER.ordinal()] = 102;
            } catch (NoSuchFieldError nosuchfielderror1795) {
                ;
            }

            try {
                aint1[Material.WAXED_OXIDIZED_CUT_COPPER_SLAB.ordinal()] = 110;
            } catch (NoSuchFieldError nosuchfielderror1796) {
                ;
            }

            try {
                aint1[Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS.ordinal()] = 106;
            } catch (NoSuchFieldError nosuchfielderror1797) {
                ;
            }

            try {
                aint1[Material.WAXED_WEATHERED_COPPER.ordinal()] = 97;
            } catch (NoSuchFieldError nosuchfielderror1798) {
                ;
            }

            try {
                aint1[Material.WAXED_WEATHERED_CUT_COPPER.ordinal()] = 101;
            } catch (NoSuchFieldError nosuchfielderror1799) {
                ;
            }

            try {
                aint1[Material.WAXED_WEATHERED_CUT_COPPER_SLAB.ordinal()] = 109;
            } catch (NoSuchFieldError nosuchfielderror1800) {
                ;
            }

            try {
                aint1[Material.WAXED_WEATHERED_CUT_COPPER_STAIRS.ordinal()] = 105;
            } catch (NoSuchFieldError nosuchfielderror1801) {
                ;
            }

            try {
                aint1[Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1231;
            } catch (NoSuchFieldError nosuchfielderror1802) {
                ;
            }

            try {
                aint1[Material.WEATHERED_COPPER.ordinal()] = 81;
            } catch (NoSuchFieldError nosuchfielderror1803) {
                ;
            }

            try {
                aint1[Material.WEATHERED_CUT_COPPER.ordinal()] = 85;
            } catch (NoSuchFieldError nosuchfielderror1804) {
                ;
            }

            try {
                aint1[Material.WEATHERED_CUT_COPPER_SLAB.ordinal()] = 93;
            } catch (NoSuchFieldError nosuchfielderror1805) {
                ;
            }

            try {
                aint1[Material.WEATHERED_CUT_COPPER_STAIRS.ordinal()] = 89;
            } catch (NoSuchFieldError nosuchfielderror1806) {
                ;
            }

            try {
                aint1[Material.WEEPING_VINES.ordinal()] = 220;
            } catch (NoSuchFieldError nosuchfielderror1807) {
                ;
            }

            try {
                aint1[Material.WEEPING_VINES_PLANT.ordinal()] = 1372;
            } catch (NoSuchFieldError nosuchfielderror1808) {
                ;
            }

            try {
                aint1[Material.WET_SPONGE.ordinal()] = 166;
            } catch (NoSuchFieldError nosuchfielderror1809) {
                ;
            }

            try {
                aint1[Material.WHEAT.ordinal()] = 815;
            } catch (NoSuchFieldError nosuchfielderror1810) {
                ;
            }

            try {
                aint1[Material.WHEAT_SEEDS.ordinal()] = 814;
            } catch (NoSuchFieldError nosuchfielderror1811) {
                ;
            }

            try {
                aint1[Material.WHITE_BANNER.ordinal()] = 1088;
            } catch (NoSuchFieldError nosuchfielderror1812) {
                ;
            }

            try {
                aint1[Material.WHITE_BED.ordinal()] = 925;
            } catch (NoSuchFieldError nosuchfielderror1813) {
                ;
            }

            try {
                aint1[Material.WHITE_CANDLE.ordinal()] = 1192;
            } catch (NoSuchFieldError nosuchfielderror1814) {
                ;
            }

            try {
                aint1[Material.WHITE_CANDLE_CAKE.ordinal()] = 1381;
            } catch (NoSuchFieldError nosuchfielderror1815) {
                ;
            }

            try {
                aint1[Material.WHITE_CARPET.ordinal()] = 425;
            } catch (NoSuchFieldError nosuchfielderror1816) {
                ;
            }

            try {
                aint1[Material.WHITE_CONCRETE.ordinal()] = 534;
            } catch (NoSuchFieldError nosuchfielderror1817) {
                ;
            }

            try {
                aint1[Material.WHITE_CONCRETE_POWDER.ordinal()] = 550;
            } catch (NoSuchFieldError nosuchfielderror1818) {
                ;
            }

            try {
                aint1[Material.WHITE_DYE.ordinal()] = 905;
            } catch (NoSuchFieldError nosuchfielderror1819) {
                ;
            }

            try {
                aint1[Material.WHITE_GLAZED_TERRACOTTA.ordinal()] = 518;
            } catch (NoSuchFieldError nosuchfielderror1820) {
                ;
            }

            try {
                aint1[Material.WHITE_SHULKER_BOX.ordinal()] = 502;
            } catch (NoSuchFieldError nosuchfielderror1821) {
                ;
            }

            try {
                aint1[Material.WHITE_STAINED_GLASS.ordinal()] = 450;
            } catch (NoSuchFieldError nosuchfielderror1822) {
                ;
            }

            try {
                aint1[Material.WHITE_STAINED_GLASS_PANE.ordinal()] = 466;
            } catch (NoSuchFieldError nosuchfielderror1823) {
                ;
            }

            try {
                aint1[Material.WHITE_TERRACOTTA.ordinal()] = 406;
            } catch (NoSuchFieldError nosuchfielderror1824) {
                ;
            }

            try {
                aint1[Material.WHITE_TULIP.ordinal()] = 204;
            } catch (NoSuchFieldError nosuchfielderror1825) {
                ;
            }

            try {
                aint1[Material.WHITE_WALL_BANNER.ordinal()] = 1334;
            } catch (NoSuchFieldError nosuchfielderror1826) {
                ;
            }

            try {
                aint1[Material.WHITE_WOOL.ordinal()] = 181;
            } catch (NoSuchFieldError nosuchfielderror1827) {
                ;
            }

            try {
                aint1[Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE.ordinal()] = 1223;
            } catch (NoSuchFieldError nosuchfielderror1828) {
                ;
            }

            try {
                aint1[Material.WITCH_SPAWN_EGG.ordinal()] = 1036;
            } catch (NoSuchFieldError nosuchfielderror1829) {
                ;
            }

            try {
                aint1[Material.WITHER_ROSE.ordinal()] = 209;
            } catch (NoSuchFieldError nosuchfielderror1830) {
                ;
            }

            try {
                aint1[Material.WITHER_SKELETON_SKULL.ordinal()] = 1059;
            } catch (NoSuchFieldError nosuchfielderror1831) {
                ;
            }

            try {
                aint1[Material.WITHER_SKELETON_SPAWN_EGG.ordinal()] = 1038;
            } catch (NoSuchFieldError nosuchfielderror1832) {
                ;
            }

            try {
                aint1[Material.WITHER_SKELETON_WALL_SKULL.ordinal()] = 1328;
            } catch (NoSuchFieldError nosuchfielderror1833) {
                ;
            }

            try {
                aint1[Material.WITHER_SPAWN_EGG.ordinal()] = 1037;
            } catch (NoSuchFieldError nosuchfielderror1834) {
                ;
            }

            try {
                aint1[Material.WOLF_SPAWN_EGG.ordinal()] = 1039;
            } catch (NoSuchFieldError nosuchfielderror1835) {
                ;
            }

            try {
                aint1[Material.WOODEN_AXE.ordinal()] = 781;
            } catch (NoSuchFieldError nosuchfielderror1836) {
                ;
            }

            try {
                aint1[Material.WOODEN_HOE.ordinal()] = 782;
            } catch (NoSuchFieldError nosuchfielderror1837) {
                ;
            }

            try {
                aint1[Material.WOODEN_PICKAXE.ordinal()] = 780;
            } catch (NoSuchFieldError nosuchfielderror1838) {
                ;
            }

            try {
                aint1[Material.WOODEN_SHOVEL.ordinal()] = 779;
            } catch (NoSuchFieldError nosuchfielderror1839) {
                ;
            }

            try {
                aint1[Material.WOODEN_SWORD.ordinal()] = 778;
            } catch (NoSuchFieldError nosuchfielderror1840) {
                ;
            }

            try {
                aint1[Material.WRITABLE_BOOK.ordinal()] = 1047;
            } catch (NoSuchFieldError nosuchfielderror1841) {
                ;
            }

            try {
                aint1[Material.WRITTEN_BOOK.ordinal()] = 1048;
            } catch (NoSuchFieldError nosuchfielderror1842) {
                ;
            }

            try {
                aint1[Material.YELLOW_BANNER.ordinal()] = 1092;
            } catch (NoSuchFieldError nosuchfielderror1843) {
                ;
            }

            try {
                aint1[Material.YELLOW_BED.ordinal()] = 929;
            } catch (NoSuchFieldError nosuchfielderror1844) {
                ;
            }

            try {
                aint1[Material.YELLOW_CANDLE.ordinal()] = 1196;
            } catch (NoSuchFieldError nosuchfielderror1845) {
                ;
            }

            try {
                aint1[Material.YELLOW_CANDLE_CAKE.ordinal()] = 1385;
            } catch (NoSuchFieldError nosuchfielderror1846) {
                ;
            }

            try {
                aint1[Material.YELLOW_CARPET.ordinal()] = 429;
            } catch (NoSuchFieldError nosuchfielderror1847) {
                ;
            }

            try {
                aint1[Material.YELLOW_CONCRETE.ordinal()] = 538;
            } catch (NoSuchFieldError nosuchfielderror1848) {
                ;
            }

            try {
                aint1[Material.YELLOW_CONCRETE_POWDER.ordinal()] = 554;
            } catch (NoSuchFieldError nosuchfielderror1849) {
                ;
            }

            try {
                aint1[Material.YELLOW_DYE.ordinal()] = 909;
            } catch (NoSuchFieldError nosuchfielderror1850) {
                ;
            }

            try {
                aint1[Material.YELLOW_GLAZED_TERRACOTTA.ordinal()] = 522;
            } catch (NoSuchFieldError nosuchfielderror1851) {
                ;
            }

            try {
                aint1[Material.YELLOW_SHULKER_BOX.ordinal()] = 506;
            } catch (NoSuchFieldError nosuchfielderror1852) {
                ;
            }

            try {
                aint1[Material.YELLOW_STAINED_GLASS.ordinal()] = 454;
            } catch (NoSuchFieldError nosuchfielderror1853) {
                ;
            }

            try {
                aint1[Material.YELLOW_STAINED_GLASS_PANE.ordinal()] = 470;
            } catch (NoSuchFieldError nosuchfielderror1854) {
                ;
            }

            try {
                aint1[Material.YELLOW_TERRACOTTA.ordinal()] = 410;
            } catch (NoSuchFieldError nosuchfielderror1855) {
                ;
            }

            try {
                aint1[Material.YELLOW_WALL_BANNER.ordinal()] = 1338;
            } catch (NoSuchFieldError nosuchfielderror1856) {
                ;
            }

            try {
                aint1[Material.YELLOW_WOOL.ordinal()] = 185;
            } catch (NoSuchFieldError nosuchfielderror1857) {
                ;
            }

            try {
                aint1[Material.ZOGLIN_SPAWN_EGG.ordinal()] = 1040;
            } catch (NoSuchFieldError nosuchfielderror1858) {
                ;
            }

            try {
                aint1[Material.ZOMBIE_HEAD.ordinal()] = 1061;
            } catch (NoSuchFieldError nosuchfielderror1859) {
                ;
            }

            try {
                aint1[Material.ZOMBIE_HORSE_SPAWN_EGG.ordinal()] = 1042;
            } catch (NoSuchFieldError nosuchfielderror1860) {
                ;
            }

            try {
                aint1[Material.ZOMBIE_SPAWN_EGG.ordinal()] = 1041;
            } catch (NoSuchFieldError nosuchfielderror1861) {
                ;
            }

            try {
                aint1[Material.ZOMBIE_VILLAGER_SPAWN_EGG.ordinal()] = 1043;
            } catch (NoSuchFieldError nosuchfielderror1862) {
                ;
            }

            try {
                aint1[Material.ZOMBIE_WALL_HEAD.ordinal()] = 1329;
            } catch (NoSuchFieldError nosuchfielderror1863) {
                ;
            }

            try {
                aint1[Material.ZOMBIFIED_PIGLIN_SPAWN_EGG.ordinal()] = 1044;
            } catch (NoSuchFieldError nosuchfielderror1864) {
                ;
            }

            CraftItemFactory.$SWITCH_TABLE$org$bukkit$Material = aint1;
            return aint1;
        }
    }
}
