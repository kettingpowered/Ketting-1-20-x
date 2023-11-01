package org.bukkit.craftbukkit.v1_20_R2;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import org.bukkit.Color;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Particle.DustTransition;
import org.bukkit.Registry;
import org.bukkit.Vibration;
import org.bukkit.Vibration.Destination.BlockDestination;
import org.bukkit.Vibration.Destination.EntityDestination;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.joml.Vector3f;

public abstract class CraftParticle implements Keyed {

    private static final Registry CRAFT_PARTICLE_REGISTRY = new CraftParticle.CraftParticleRegistry(CraftRegistry.getMinecraftRegistry(Registries.PARTICLE_TYPE));
    private final NamespacedKey key;
    private final ParticleType particle;
    private final Class clazz;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$Particle;

    public static Particle minecraftToBukkit(ParticleType minecraft) {
        Preconditions.checkArgument(minecraft != null);
        net.minecraft.core.Registry registry = CraftRegistry.getMinecraftRegistry(Registries.PARTICLE_TYPE);
        Particle bukkit = (Particle) Registry.PARTICLE_TYPE.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static ParticleType bukkitToMinecraft(Particle bukkit) {
        Preconditions.checkArgument(bukkit != null);
        return (ParticleType) CraftRegistry.getMinecraftRegistry(Registries.PARTICLE_TYPE).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }

    public static ParticleOptions createParticleParam(Particle particle, Object data) {
        Preconditions.checkArgument(particle != null);
        CraftParticle craftParticle = (CraftParticle) CraftParticle.CRAFT_PARTICLE_REGISTRY.get(particle.getKey());

        Preconditions.checkArgument(craftParticle != null);
        return craftParticle.createParticleParam(data);
    }

    public static Object convertLegacy(Object object) {
        MaterialData mat;

        return object instanceof MaterialData && (mat = (MaterialData) object) == (MaterialData) object ? CraftBlockData.fromData(CraftMagicNumbers.getBlock(mat)) : object;
    }

    public static Particle convertLegacy(Particle particle) {
        Particle particle;

        switch ($SWITCH_TABLE$org$bukkit$Particle()[particle.ordinal()]) {
            case 99:
                particle = Particle.BLOCK_CRACK;
                break;
            case 100:
                particle = Particle.BLOCK_DUST;
                break;
            case 101:
                particle = Particle.FALLING_DUST;
                break;
            default:
                particle = particle;
        }

        return particle;
    }

    public CraftParticle(NamespacedKey key, ParticleType particle, Class clazz) {
        this.key = key;
        this.particle = particle;
        this.clazz = clazz;
    }

    public ParticleType getHandle() {
        return this.particle;
    }

    public abstract ParticleOptions createParticleParam(Object object);

    public NamespacedKey getKey() {
        return this.key;
    }

    static int[] $SWITCH_TABLE$org$bukkit$Particle() {
        int[] aint = CraftParticle.$SWITCH_TABLE$org$bukkit$Particle;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Particle.values().length];

            try {
                aint1[Particle.ASH.ordinal()] = 66;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Particle.BLOCK_CRACK.ordinal()] = 36;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Particle.BLOCK_DUST.ordinal()] = 37;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Particle.BLOCK_MARKER.ordinal()] = 98;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[Particle.BUBBLE_COLUMN_UP.ordinal()] = 50;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[Particle.BUBBLE_POP.ordinal()] = 48;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[Particle.CAMPFIRE_COSY_SMOKE.ordinal()] = 54;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[Particle.CAMPFIRE_SIGNAL_SMOKE.ordinal()] = 55;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[Particle.CHERRY_LEAVES.ordinal()] = 96;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[Particle.CLOUD.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[Particle.COMPOSTER.ordinal()] = 56;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[Particle.CRIMSON_SPORE.ordinal()] = 67;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[Particle.CRIT.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[Particle.CRIT_MAGIC.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[Particle.CURRENT_DOWN.ordinal()] = 49;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[Particle.DAMAGE_INDICATOR.ordinal()] = 42;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[Particle.DOLPHIN.ordinal()] = 52;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[Particle.DRAGON_BREATH.ordinal()] = 40;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[Particle.DRIPPING_DRIPSTONE_LAVA.ordinal()] = 81;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                aint1[Particle.DRIPPING_DRIPSTONE_WATER.ordinal()] = 83;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                aint1[Particle.DRIPPING_HONEY.ordinal()] = 61;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                aint1[Particle.DRIPPING_OBSIDIAN_TEAR.ordinal()] = 70;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                aint1[Particle.DRIP_LAVA.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                aint1[Particle.DRIP_WATER.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                aint1[Particle.DUST_COLOR_TRANSITION.ordinal()] = 75;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                aint1[Particle.EGG_CRACK.ordinal()] = 97;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                aint1[Particle.ELECTRIC_SPARK.ordinal()] = 89;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                aint1[Particle.ENCHANTMENT_TABLE.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                aint1[Particle.END_ROD.ordinal()] = 41;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                aint1[Particle.EXPLOSION_HUGE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                aint1[Particle.EXPLOSION_LARGE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                aint1[Particle.EXPLOSION_NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                aint1[Particle.FALLING_DRIPSTONE_LAVA.ordinal()] = 82;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                aint1[Particle.FALLING_DRIPSTONE_WATER.ordinal()] = 84;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                aint1[Particle.FALLING_DUST.ordinal()] = 44;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                aint1[Particle.FALLING_HONEY.ordinal()] = 62;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                aint1[Particle.FALLING_LAVA.ordinal()] = 58;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                aint1[Particle.FALLING_NECTAR.ordinal()] = 64;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                aint1[Particle.FALLING_OBSIDIAN_TEAR.ordinal()] = 71;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                aint1[Particle.FALLING_SPORE_BLOSSOM.ordinal()] = 77;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                aint1[Particle.FALLING_WATER.ordinal()] = 60;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

            try {
                aint1[Particle.FIREWORKS_SPARK.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror41) {
                ;
            }

            try {
                aint1[Particle.FLAME.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror42) {
                ;
            }

            try {
                aint1[Particle.FLASH.ordinal()] = 57;
            } catch (NoSuchFieldError nosuchfielderror43) {
                ;
            }

            try {
                aint1[Particle.GLOW.ordinal()] = 86;
            } catch (NoSuchFieldError nosuchfielderror44) {
                ;
            }

            try {
                aint1[Particle.GLOW_SQUID_INK.ordinal()] = 85;
            } catch (NoSuchFieldError nosuchfielderror45) {
                ;
            }

            try {
                aint1[Particle.HEART.ordinal()] = 34;
            } catch (NoSuchFieldError nosuchfielderror46) {
                ;
            }

            try {
                aint1[Particle.ITEM_CRACK.ordinal()] = 35;
            } catch (NoSuchFieldError nosuchfielderror47) {
                ;
            }

            try {
                aint1[Particle.LANDING_HONEY.ordinal()] = 63;
            } catch (NoSuchFieldError nosuchfielderror48) {
                ;
            }

            try {
                aint1[Particle.LANDING_LAVA.ordinal()] = 59;
            } catch (NoSuchFieldError nosuchfielderror49) {
                ;
            }

            try {
                aint1[Particle.LANDING_OBSIDIAN_TEAR.ordinal()] = 72;
            } catch (NoSuchFieldError nosuchfielderror50) {
                ;
            }

            try {
                aint1[Particle.LAVA.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror51) {
                ;
            }

            try {
                aint1[Particle.LEGACY_BLOCK_CRACK.ordinal()] = 99;
            } catch (NoSuchFieldError nosuchfielderror52) {
                ;
            }

            try {
                aint1[Particle.LEGACY_BLOCK_DUST.ordinal()] = 100;
            } catch (NoSuchFieldError nosuchfielderror53) {
                ;
            }

            try {
                aint1[Particle.LEGACY_FALLING_DUST.ordinal()] = 101;
            } catch (NoSuchFieldError nosuchfielderror54) {
                ;
            }

            try {
                aint1[Particle.MOB_APPEARANCE.ordinal()] = 39;
            } catch (NoSuchFieldError nosuchfielderror55) {
                ;
            }

            try {
                aint1[Particle.NAUTILUS.ordinal()] = 51;
            } catch (NoSuchFieldError nosuchfielderror56) {
                ;
            }

            try {
                aint1[Particle.NOTE.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror57) {
                ;
            }

            try {
                aint1[Particle.PORTAL.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror58) {
                ;
            }

            try {
                aint1[Particle.REDSTONE.ordinal()] = 30;
            } catch (NoSuchFieldError nosuchfielderror59) {
                ;
            }

            try {
                aint1[Particle.REVERSE_PORTAL.ordinal()] = 73;
            } catch (NoSuchFieldError nosuchfielderror60) {
                ;
            }

            try {
                aint1[Particle.SCRAPE.ordinal()] = 90;
            } catch (NoSuchFieldError nosuchfielderror61) {
                ;
            }

            try {
                aint1[Particle.SCULK_CHARGE.ordinal()] = 93;
            } catch (NoSuchFieldError nosuchfielderror62) {
                ;
            }

            try {
                aint1[Particle.SCULK_CHARGE_POP.ordinal()] = 94;
            } catch (NoSuchFieldError nosuchfielderror63) {
                ;
            }

            try {
                aint1[Particle.SCULK_SOUL.ordinal()] = 92;
            } catch (NoSuchFieldError nosuchfielderror64) {
                ;
            }

            try {
                aint1[Particle.SHRIEK.ordinal()] = 95;
            } catch (NoSuchFieldError nosuchfielderror65) {
                ;
            }

            try {
                aint1[Particle.SLIME.ordinal()] = 33;
            } catch (NoSuchFieldError nosuchfielderror66) {
                ;
            }

            try {
                aint1[Particle.SMALL_FLAME.ordinal()] = 79;
            } catch (NoSuchFieldError nosuchfielderror67) {
                ;
            }

            try {
                aint1[Particle.SMOKE_LARGE.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror68) {
                ;
            }

            try {
                aint1[Particle.SMOKE_NORMAL.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror69) {
                ;
            }

            try {
                aint1[Particle.SNEEZE.ordinal()] = 53;
            } catch (NoSuchFieldError nosuchfielderror70) {
                ;
            }

            try {
                aint1[Particle.SNOWBALL.ordinal()] = 31;
            } catch (NoSuchFieldError nosuchfielderror71) {
                ;
            }

            try {
                aint1[Particle.SNOWFLAKE.ordinal()] = 80;
            } catch (NoSuchFieldError nosuchfielderror72) {
                ;
            }

            try {
                aint1[Particle.SNOW_SHOVEL.ordinal()] = 32;
            } catch (NoSuchFieldError nosuchfielderror73) {
                ;
            }

            try {
                aint1[Particle.SONIC_BOOM.ordinal()] = 91;
            } catch (NoSuchFieldError nosuchfielderror74) {
                ;
            }

            try {
                aint1[Particle.SOUL.ordinal()] = 69;
            } catch (NoSuchFieldError nosuchfielderror75) {
                ;
            }

            try {
                aint1[Particle.SOUL_FIRE_FLAME.ordinal()] = 65;
            } catch (NoSuchFieldError nosuchfielderror76) {
                ;
            }

            try {
                aint1[Particle.SPELL.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror77) {
                ;
            }

            try {
                aint1[Particle.SPELL_INSTANT.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror78) {
                ;
            }

            try {
                aint1[Particle.SPELL_MOB.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror79) {
                ;
            }

            try {
                aint1[Particle.SPELL_MOB_AMBIENT.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror80) {
                ;
            }

            try {
                aint1[Particle.SPELL_WITCH.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror81) {
                ;
            }

            try {
                aint1[Particle.SPIT.ordinal()] = 46;
            } catch (NoSuchFieldError nosuchfielderror82) {
                ;
            }

            try {
                aint1[Particle.SPORE_BLOSSOM_AIR.ordinal()] = 78;
            } catch (NoSuchFieldError nosuchfielderror83) {
                ;
            }

            try {
                aint1[Particle.SQUID_INK.ordinal()] = 47;
            } catch (NoSuchFieldError nosuchfielderror84) {
                ;
            }

            try {
                aint1[Particle.SUSPENDED.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror85) {
                ;
            }

            try {
                aint1[Particle.SUSPENDED_DEPTH.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror86) {
                ;
            }

            try {
                aint1[Particle.SWEEP_ATTACK.ordinal()] = 43;
            } catch (NoSuchFieldError nosuchfielderror87) {
                ;
            }

            try {
                aint1[Particle.TOTEM.ordinal()] = 45;
            } catch (NoSuchFieldError nosuchfielderror88) {
                ;
            }

            try {
                aint1[Particle.TOWN_AURA.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror89) {
                ;
            }

            try {
                aint1[Particle.VIBRATION.ordinal()] = 76;
            } catch (NoSuchFieldError nosuchfielderror90) {
                ;
            }

            try {
                aint1[Particle.VILLAGER_ANGRY.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror91) {
                ;
            }

            try {
                aint1[Particle.VILLAGER_HAPPY.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror92) {
                ;
            }

            try {
                aint1[Particle.WARPED_SPORE.ordinal()] = 68;
            } catch (NoSuchFieldError nosuchfielderror93) {
                ;
            }

            try {
                aint1[Particle.WATER_BUBBLE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror94) {
                ;
            }

            try {
                aint1[Particle.WATER_DROP.ordinal()] = 38;
            } catch (NoSuchFieldError nosuchfielderror95) {
                ;
            }

            try {
                aint1[Particle.WATER_SPLASH.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror96) {
                ;
            }

            try {
                aint1[Particle.WATER_WAKE.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror97) {
                ;
            }

            try {
                aint1[Particle.WAX_OFF.ordinal()] = 88;
            } catch (NoSuchFieldError nosuchfielderror98) {
                ;
            }

            try {
                aint1[Particle.WAX_ON.ordinal()] = 87;
            } catch (NoSuchFieldError nosuchfielderror99) {
                ;
            }

            try {
                aint1[Particle.WHITE_ASH.ordinal()] = 74;
            } catch (NoSuchFieldError nosuchfielderror100) {
                ;
            }

            CraftParticle.$SWITCH_TABLE$org$bukkit$Particle = aint1;
            return aint1;
        }
    }

    public static class CraftParticleRegistry extends CraftRegistry {

        private static final Map PARTICLE_MAP = new HashMap();
        private static final BiFunction VOID_FUNCTION = (namex, particlex) -> {
            return new CraftParticle(namex, particlex, Void.class) {
                public ParticleOptions createParticleParam(Void data) {
                    return (SimpleParticleType) this.getHandle();
                }
            };
        };

        static {
            BiFunction dustOptionsFunction = (namex, particlex) -> {
                return new CraftParticle(namex, particlex, DustOptions.class) {
                    public ParticleOptions createParticleParam(DustOptions data) {
                        Color color = data.getColor();

                        return new DustParticleOptions(new Vector3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F), data.getSize());
                    }
                };
            };
            BiFunction itemStackFunction = (namex, particlex) -> {
                return new CraftParticle(namex, particlex, ItemStack.class) {
                    public ParticleOptions createParticleParam(ItemStack data) {
                        return new ItemParticleOption(this.getHandle(), CraftItemStack.asNMSCopy(data));
                    }
                };
            };
            BiFunction blockDataFunction = (namex, particlex) -> {
                return new CraftParticle(namex, particlex, BlockData.class) {
                    public ParticleOptions createParticleParam(BlockData data) {
                        return new BlockParticleOption(this.getHandle(), ((CraftBlockData) data).getState());
                    }
                };
            };
            BiFunction dustTransitionFunction = (namex, particlex) -> {
                return new CraftParticle(namex, particlex, DustTransition.class) {
                    public ParticleOptions createParticleParam(DustTransition data) {
                        Color from = data.getColor();
                        Color to = data.getToColor();

                        return new DustColorTransitionOptions(new Vector3f((float) from.getRed() / 255.0F, (float) from.getGreen() / 255.0F, (float) from.getBlue() / 255.0F), new Vector3f((float) to.getRed() / 255.0F, (float) to.getGreen() / 255.0F, (float) to.getBlue() / 255.0F), data.getSize());
                    }
                };
            };
            BiFunction vibrationFunction = (namex, particlex) -> {
                return new CraftParticle(namex, particlex, Vibration.class) {
                    public ParticleOptions createParticleParam(Vibration data) {
                        Object source;

                        if (data.getDestination() instanceof BlockDestination) {
                            Location destination = ((BlockDestination) data.getDestination()).getLocation();

                            source = new BlockPositionSource(CraftLocation.toBlockPosition(destination));
                        } else {
                            if (!(data.getDestination() instanceof EntityDestination)) {
                                throw new IllegalArgumentException("Unknown vibration destination " + data.getDestination());
                            }

                            Entity destination = ((CraftEntity) ((EntityDestination) data.getDestination()).getEntity()).getHandle();

                            source = new EntityPositionSource(destination, destination.getEyeHeight());
                        }

                        return new VibrationParticleOption((PositionSource) source, data.getArrivalTime());
                    }
                };
            };
            BiFunction floatFunction = (namex, particlex) -> {
                return new CraftParticle(namex, particlex, Float.class) {
                    public ParticleOptions createParticleParam(Float data) {
                        return new SculkChargeParticleOptions(data);
                    }
                };
            };
            BiFunction integerFunction = (namex, particlex) -> {
                return new CraftParticle(namex, particlex, Integer.class) {
                    public ParticleOptions createParticleParam(Integer data) {
                        return new ShriekParticleOption(data);
                    }
                };
            };

            add("dust", dustOptionsFunction);
            add("item", itemStackFunction);
            add("block", blockDataFunction);
            add("falling_dust", blockDataFunction);
            add("dust_color_transition", dustTransitionFunction);
            add("vibration", vibrationFunction);
            add("sculk_charge", floatFunction);
            add("shriek", integerFunction);
            add("block_marker", blockDataFunction);
        }

        private static void add(String name, BiFunction function) {
            CraftParticle.CraftParticleRegistry.PARTICLE_MAP.put(NamespacedKey.fromString(name), function);
        }

        public CraftParticleRegistry(net.minecraft.core.Registry minecraftRegistry) {
            super(CraftParticle.class, minecraftRegistry, (BiFunction) null);
        }

        public CraftParticle createBukkit(NamespacedKey namespacedKey, ParticleType particle) {
            if (particle == null) {
                return null;
            } else {
                BiFunction function = (BiFunction) CraftParticle.CraftParticleRegistry.PARTICLE_MAP.getOrDefault(namespacedKey, CraftParticle.CraftParticleRegistry.VOID_FUNCTION);

                return (CraftParticle) function.apply(namespacedKey, particle);
            }
        }
    }
}
