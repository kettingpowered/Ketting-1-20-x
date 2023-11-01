package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.bukkit.Axis;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.potion.Potion;

public class CraftEffect {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$BlockFace;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$Axis;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$Effect;

    public static int getDataValue(Effect effect, Object data) {
        int datavalue;

        switch ($SWITCH_TABLE$org$bukkit$Effect()[effect.ordinal()]) {
            case 15:
                Preconditions.checkArgument(data == Material.AIR || ((Material) data).isRecord(), "Invalid record type for Material %s!", data);
                datavalue = Item.getId(CraftMagicNumbers.getItem((Material) data));
                break;
            case 22:
                switch ($SWITCH_TABLE$org$bukkit$block$BlockFace()[((BlockFace) data).ordinal()]) {
                    case 1:
                        datavalue = 2;
                        return datavalue;
                    case 2:
                        datavalue = 5;
                        return datavalue;
                    case 3:
                        datavalue = 3;
                        return datavalue;
                    case 4:
                        datavalue = 4;
                        return datavalue;
                    case 5:
                        datavalue = 1;
                        return datavalue;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 19:
                        datavalue = 0;
                        return datavalue;
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    default:
                        throw new IllegalArgumentException("Bad smoke direction!");
                }
            case 23:
                Preconditions.checkArgument(((Material) data).isBlock(), "Material %s is not a block!", data);
                datavalue = Block.getId(CraftMagicNumbers.getBlock((Material) data).defaultBlockState());
                break;
            case 24:
                datavalue = ((Potion) data).toDamageValue() & 63;
                break;
            case 25:
                datavalue = ((Color) data).asRGB();
                break;
            case 34:
                datavalue = (Integer) data;
                break;
            case 57:
                datavalue = (Boolean) data ? 1 : 0;
                break;
            case 62:
                datavalue = (Integer) data;
                break;
            case 65:
                if (data == null) {
                    datavalue = -1;
                    break;
                } else {
                    switch ($SWITCH_TABLE$org$bukkit$Axis()[((Axis) data).ordinal()]) {
                        case 1:
                            datavalue = 0;
                            return datavalue;
                        case 2:
                            datavalue = 1;
                            return datavalue;
                        case 3:
                            datavalue = 2;
                            return datavalue;
                        default:
                            throw new IllegalArgumentException("Bad electric spark axis!");
                    }
                }
            default:
                datavalue = 0;
        }

        return datavalue;
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$BlockFace() {
        int[] aint = CraftEffect.$SWITCH_TABLE$org$bukkit$block$BlockFace;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[BlockFace.values().length];

            try {
                aint1[BlockFace.DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[BlockFace.EAST.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[BlockFace.EAST_NORTH_EAST.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[BlockFace.EAST_SOUTH_EAST.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[BlockFace.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_EAST.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_NORTH_EAST.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_NORTH_WEST.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[BlockFace.NORTH_WEST.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[BlockFace.SELF.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_EAST.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_SOUTH_EAST.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_SOUTH_WEST.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[BlockFace.SOUTH_WEST.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[BlockFace.UP.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[BlockFace.WEST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[BlockFace.WEST_NORTH_WEST.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[BlockFace.WEST_SOUTH_WEST.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            CraftEffect.$SWITCH_TABLE$org$bukkit$block$BlockFace = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$Axis() {
        int[] aint = CraftEffect.$SWITCH_TABLE$org$bukkit$Axis;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Axis.values().length];

            try {
                aint1[Axis.X.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Axis.Y.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Axis.Z.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftEffect.$SWITCH_TABLE$org$bukkit$Axis = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$Effect() {
        int[] aint = CraftEffect.$SWITCH_TABLE$org$bukkit$Effect;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Effect.values().length];

            try {
                aint1[Effect.ANVIL_BREAK.ordinal()] = 36;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Effect.ANVIL_LAND.ordinal()] = 38;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Effect.ANVIL_USE.ordinal()] = 37;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Effect.BAT_TAKEOFF.ordinal()] = 44;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Effect.BLAZE_SHOOT.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[Effect.BONE_MEAL_USE.ordinal()] = 62;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[Effect.BOOK_PAGE_TURN.ordinal()] = 51;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[Effect.BOW_FIRE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[Effect.BREWING_STAND_BREW.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[Effect.CHORUS_FLOWER_DEATH.ordinal()] = 30;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[Effect.CHORUS_FLOWER_GROW.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[Effect.CLICK1.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[Effect.CLICK2.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[Effect.COMPOSTER_FILL_ATTEMPT.ordinal()] = 57;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[Effect.COPPER_WAX_OFF.ordinal()] = 67;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[Effect.COPPER_WAX_ON.ordinal()] = 66;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[Effect.DOOR_CLOSE.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[Effect.DOOR_TOGGLE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[Effect.DRAGON_BREATH.ordinal()] = 35;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                aint1[Effect.DRIPPING_DRIPSTONE.ordinal()] = 61;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                aint1[Effect.ELECTRIC_SPARK.ordinal()] = 65;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                aint1[Effect.ENDERDRAGON_GROWL.ordinal()] = 46;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                aint1[Effect.ENDERDRAGON_SHOOT.ordinal()] = 39;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                aint1[Effect.ENDEREYE_LAUNCH.ordinal()] = 32;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                aint1[Effect.ENDER_DRAGON_DESTROY_BLOCK.ordinal()] = 63;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                aint1[Effect.ENDER_SIGNAL.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                aint1[Effect.END_GATEWAY_SPAWN.ordinal()] = 45;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                aint1[Effect.END_PORTAL_FRAME_FILL.ordinal()] = 60;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                aint1[Effect.EXTINGUISH.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                aint1[Effect.FENCE_GATE_CLOSE.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                aint1[Effect.FENCE_GATE_TOGGLE.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                aint1[Effect.FIREWORK_SHOOT.ordinal()] = 33;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                aint1[Effect.GHAST_SHOOT.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                aint1[Effect.GHAST_SHRIEK.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                aint1[Effect.GRINDSTONE_USE.ordinal()] = 50;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                aint1[Effect.HUSK_CONVERTED_TO_ZOMBIE.ordinal()] = 49;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                aint1[Effect.INSTANT_POTION_BREAK.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                aint1[Effect.IRON_DOOR_CLOSE.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                aint1[Effect.IRON_DOOR_TOGGLE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                aint1[Effect.IRON_TRAPDOOR_CLOSE.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                aint1[Effect.IRON_TRAPDOOR_TOGGLE.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

            try {
                aint1[Effect.LAVA_INTERACT.ordinal()] = 58;
            } catch (NoSuchFieldError nosuchfielderror41) {
                ;
            }

            try {
                aint1[Effect.MOBSPAWNER_FLAMES.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror42) {
                ;
            }

            try {
                aint1[Effect.OXIDISED_COPPER_SCRAPE.ordinal()] = 68;
            } catch (NoSuchFieldError nosuchfielderror43) {
                ;
            }

            try {
                aint1[Effect.PHANTOM_BITE.ordinal()] = 47;
            } catch (NoSuchFieldError nosuchfielderror44) {
                ;
            }

            try {
                aint1[Effect.POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON.ordinal()] = 54;
            } catch (NoSuchFieldError nosuchfielderror45) {
                ;
            }

            try {
                aint1[Effect.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON.ordinal()] = 55;
            } catch (NoSuchFieldError nosuchfielderror46) {
                ;
            }

            try {
                aint1[Effect.POINTED_DRIPSTONE_LAND.ordinal()] = 53;
            } catch (NoSuchFieldError nosuchfielderror47) {
                ;
            }

            try {
                aint1[Effect.PORTAL_TRAVEL.ordinal()] = 31;
            } catch (NoSuchFieldError nosuchfielderror48) {
                ;
            }

            try {
                aint1[Effect.POTION_BREAK.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror49) {
                ;
            }

            try {
                aint1[Effect.RECORD_PLAY.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror50) {
                ;
            }

            try {
                aint1[Effect.REDSTONE_TORCH_BURNOUT.ordinal()] = 59;
            } catch (NoSuchFieldError nosuchfielderror51) {
                ;
            }

            try {
                aint1[Effect.SKELETON_CONVERTED_TO_STRAY.ordinal()] = 56;
            } catch (NoSuchFieldError nosuchfielderror52) {
                ;
            }

            try {
                aint1[Effect.SMITHING_TABLE_USE.ordinal()] = 52;
            } catch (NoSuchFieldError nosuchfielderror53) {
                ;
            }

            try {
                aint1[Effect.SMOKE.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror54) {
                ;
            }

            try {
                aint1[Effect.SPONGE_DRY.ordinal()] = 64;
            } catch (NoSuchFieldError nosuchfielderror55) {
                ;
            }

            try {
                aint1[Effect.STEP_SOUND.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror56) {
                ;
            }

            try {
                aint1[Effect.TRAPDOOR_CLOSE.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror57) {
                ;
            }

            try {
                aint1[Effect.TRAPDOOR_TOGGLE.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror58) {
                ;
            }

            try {
                aint1[Effect.VILLAGER_PLANT_GROW.ordinal()] = 34;
            } catch (NoSuchFieldError nosuchfielderror59) {
                ;
            }

            try {
                aint1[Effect.WITHER_BREAK_BLOCK.ordinal()] = 40;
            } catch (NoSuchFieldError nosuchfielderror60) {
                ;
            }

            try {
                aint1[Effect.WITHER_SHOOT.ordinal()] = 41;
            } catch (NoSuchFieldError nosuchfielderror61) {
                ;
            }

            try {
                aint1[Effect.ZOMBIE_CHEW_IRON_DOOR.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror62) {
                ;
            }

            try {
                aint1[Effect.ZOMBIE_CHEW_WOODEN_DOOR.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror63) {
                ;
            }

            try {
                aint1[Effect.ZOMBIE_CONVERTED_TO_DROWNED.ordinal()] = 48;
            } catch (NoSuchFieldError nosuchfielderror64) {
                ;
            }

            try {
                aint1[Effect.ZOMBIE_CONVERTED_VILLAGER.ordinal()] = 43;
            } catch (NoSuchFieldError nosuchfielderror65) {
                ;
            }

            try {
                aint1[Effect.ZOMBIE_DESTROY_DOOR.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror66) {
                ;
            }

            try {
                aint1[Effect.ZOMBIE_INFECT.ordinal()] = 42;
            } catch (NoSuchFieldError nosuchfielderror67) {
                ;
            }

            CraftEffect.$SWITCH_TABLE$org$bukkit$Effect = aint1;
            return aint1;
        }
    }
}
