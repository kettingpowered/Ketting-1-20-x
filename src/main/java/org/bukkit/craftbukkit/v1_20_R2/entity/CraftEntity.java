package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.phys.AABB;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.CraftSound;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_20_R2.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftSpawnCategory;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftVector;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Entity.Spigot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.spigotmc.AsyncCatcher;

public abstract class CraftEntity implements Entity {

    private static PermissibleBase perm;
    private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
    protected final CraftServer server;
    protected net.minecraft.world.entity.Entity entity;
    private final EntityType entityType;
    private EntityDamageEvent lastDamageEvent;
    private final CraftPersistentDataContainer persistentDataContainer;
    private final Spigot spigot;

    public CraftEntity(CraftServer server, net.minecraft.world.entity.Entity entity) {
        this.persistentDataContainer = new CraftPersistentDataContainer(CraftEntity.DATA_TYPE_REGISTRY);
        this.spigot = new Spigot() {
            public void sendMessage(BaseComponent component) {}

            public void sendMessage(BaseComponent... components) {}

            public void sendMessage(UUID sender, BaseComponent... components) {}

            public void sendMessage(UUID sender, BaseComponent component) {}
        };
        this.server = server;
        this.entity = entity;
        this.entityType = CraftEntityType.minecraftToBukkit(entity.getType());
    }

    public static CraftEntity getEntity(CraftServer server, net.minecraft.world.entity.Entity entity) {
        if (entity instanceof LivingEntity) {
            if (entity instanceof Player) {
                if (entity instanceof ServerPlayer) {
                    return new CraftPlayer(server, (ServerPlayer) entity);
                }

                return new CraftHumanEntity(server, (Player) entity);
            }

            if (entity instanceof WaterAnimal) {
                if (entity instanceof Squid) {
                    if (entity instanceof GlowSquid) {
                        return new CraftGlowSquid(server, (GlowSquid) entity);
                    }

                    return new CraftSquid(server, (Squid) entity);
                }

                if (entity instanceof AbstractFish) {
                    if (entity instanceof Cod) {
                        return new CraftCod(server, (Cod) entity);
                    }

                    if (entity instanceof Pufferfish) {
                        return new CraftPufferFish(server, (Pufferfish) entity);
                    }

                    if (entity instanceof Salmon) {
                        return new CraftSalmon(server, (Salmon) entity);
                    }

                    if (entity instanceof TropicalFish) {
                        return new CraftTropicalFish(server, (TropicalFish) entity);
                    }

                    if (entity instanceof Tadpole) {
                        return new CraftTadpole(server, (Tadpole) entity);
                    }

                    return new CraftFish(server, (AbstractFish) entity);
                }

                if (entity instanceof Dolphin) {
                    return new CraftDolphin(server, (Dolphin) entity);
                }

                return new CraftWaterMob(server, (WaterAnimal) entity);
            }

            if (!(entity instanceof PathfinderMob)) {
                if (entity instanceof Slime) {
                    if (entity instanceof MagmaCube) {
                        return new CraftMagmaCube(server, (MagmaCube) entity);
                    }

                    return new CraftSlime(server, (Slime) entity);
                }

                if (entity instanceof FlyingMob) {
                    if (entity instanceof Ghast) {
                        return new CraftGhast(server, (Ghast) entity);
                    }

                    if (entity instanceof Phantom) {
                        return new CraftPhantom(server, (Phantom) entity);
                    }

                    return new CraftFlying(server, (FlyingMob) entity);
                }

                if (entity instanceof EnderDragon) {
                    return new CraftEnderDragon(server, (EnderDragon) entity);
                }

                if (entity instanceof AmbientCreature) {
                    if (entity instanceof Bat) {
                        return new CraftBat(server, (Bat) entity);
                    }

                    return new CraftAmbient(server, (AmbientCreature) entity);
                }

                if (entity instanceof ArmorStand) {
                    return new CraftArmorStand(server, (ArmorStand) entity);
                }

                return new CraftLivingEntity(server, (LivingEntity) entity);
            }

            if (entity instanceof Animal) {
                if (entity instanceof Chicken) {
                    return new CraftChicken(server, (Chicken) entity);
                }

                if (entity instanceof Cow) {
                    if (entity instanceof MushroomCow) {
                        return new CraftMushroomCow(server, (MushroomCow) entity);
                    }

                    return new CraftCow(server, (Cow) entity);
                }

                if (entity instanceof Pig) {
                    return new CraftPig(server, (Pig) entity);
                }

                if (entity instanceof TamableAnimal) {
                    if (entity instanceof Wolf) {
                        return new CraftWolf(server, (Wolf) entity);
                    }

                    if (entity instanceof Cat) {
                        return new CraftCat(server, (Cat) entity);
                    }

                    if (entity instanceof Parrot) {
                        return new CraftParrot(server, (Parrot) entity);
                    }
                } else {
                    if (entity instanceof Sheep) {
                        return new CraftSheep(server, (Sheep) entity);
                    }

                    if (!(entity instanceof AbstractHorse)) {
                        if (entity instanceof Rabbit) {
                            return new CraftRabbit(server, (Rabbit) entity);
                        }

                        if (entity instanceof PolarBear) {
                            return new CraftPolarBear(server, (PolarBear) entity);
                        }

                        if (entity instanceof Turtle) {
                            return new CraftTurtle(server, (Turtle) entity);
                        }

                        if (entity instanceof Ocelot) {
                            return new CraftOcelot(server, (Ocelot) entity);
                        }

                        if (entity instanceof Panda) {
                            return new CraftPanda(server, (Panda) entity);
                        }

                        if (entity instanceof Fox) {
                            return new CraftFox(server, (Fox) entity);
                        }

                        if (entity instanceof Bee) {
                            return new CraftBee(server, (Bee) entity);
                        }

                        if (entity instanceof Hoglin) {
                            return new CraftHoglin(server, (Hoglin) entity);
                        }

                        if (entity instanceof Strider) {
                            return new CraftStrider(server, (Strider) entity);
                        }

                        if (entity instanceof Axolotl) {
                            return new CraftAxolotl(server, (Axolotl) entity);
                        }

                        if (entity instanceof Goat) {
                            return new CraftGoat(server, (Goat) entity);
                        }

                        if (entity instanceof Frog) {
                            return new CraftFrog(server, (Frog) entity);
                        }

                        if (entity instanceof Sniffer) {
                            return new CraftSniffer(server, (Sniffer) entity);
                        }

                        return new CraftAnimals(server, (Animal) entity);
                    }

                    if (entity instanceof AbstractChestedHorse) {
                        if (entity instanceof Donkey) {
                            return new CraftDonkey(server, (Donkey) entity);
                        }

                        if (entity instanceof Mule) {
                            return new CraftMule(server, (Mule) entity);
                        }

                        if (entity instanceof TraderLlama) {
                            return new CraftTraderLlama(server, (TraderLlama) entity);
                        }

                        if (entity instanceof Llama) {
                            return new CraftLlama(server, (Llama) entity);
                        }
                    } else {
                        if (entity instanceof Horse) {
                            return new CraftHorse(server, (Horse) entity);
                        }

                        if (entity instanceof SkeletonHorse) {
                            return new CraftSkeletonHorse(server, (SkeletonHorse) entity);
                        }

                        if (entity instanceof ZombieHorse) {
                            return new CraftZombieHorse(server, (ZombieHorse) entity);
                        }

                        if (entity instanceof Camel) {
                            return new CraftCamel(server, (Camel) entity);
                        }
                    }
                }
            } else if (entity instanceof Monster) {
                if (entity instanceof Zombie) {
                    if (entity instanceof ZombifiedPiglin) {
                        return new CraftPigZombie(server, (ZombifiedPiglin) entity);
                    }

                    if (entity instanceof Husk) {
                        return new CraftHusk(server, (Husk) entity);
                    }

                    if (entity instanceof ZombieVillager) {
                        return new CraftVillagerZombie(server, (ZombieVillager) entity);
                    }

                    if (entity instanceof Drowned) {
                        return new CraftDrowned(server, (Drowned) entity);
                    }

                    return new CraftZombie(server, (Zombie) entity);
                }

                if (entity instanceof Creeper) {
                    return new CraftCreeper(server, (Creeper) entity);
                }

                if (entity instanceof EnderMan) {
                    return new CraftEnderman(server, (EnderMan) entity);
                }

                if (entity instanceof Silverfish) {
                    return new CraftSilverfish(server, (Silverfish) entity);
                }

                if (entity instanceof Giant) {
                    return new CraftGiant(server, (Giant) entity);
                }

                if (!(entity instanceof AbstractSkeleton)) {
                    if (entity instanceof Blaze) {
                        return new CraftBlaze(server, (Blaze) entity);
                    }

                    if (entity instanceof Witch) {
                        return new CraftWitch(server, (Witch) entity);
                    }

                    if (entity instanceof WitherBoss) {
                        return new CraftWither(server, (WitherBoss) entity);
                    }

                    if (entity instanceof Spider) {
                        if (entity instanceof CaveSpider) {
                            return new CraftCaveSpider(server, (CaveSpider) entity);
                        }

                        return new CraftSpider(server, (Spider) entity);
                    }

                    if (entity instanceof Endermite) {
                        return new CraftEndermite(server, (Endermite) entity);
                    }

                    if (entity instanceof Guardian) {
                        if (entity instanceof ElderGuardian) {
                            return new CraftElderGuardian(server, (ElderGuardian) entity);
                        }

                        return new CraftGuardian(server, (Guardian) entity);
                    }

                    if (entity instanceof Vex) {
                        return new CraftVex(server, (Vex) entity);
                    }

                    if (entity instanceof AbstractIllager) {
                        if (entity instanceof SpellcasterIllager) {
                            if (entity instanceof Evoker) {
                                return new CraftEvoker(server, (Evoker) entity);
                            }

                            if (entity instanceof Illusioner) {
                                return new CraftIllusioner(server, (Illusioner) entity);
                            }

                            return new CraftSpellcaster(server, (SpellcasterIllager) entity);
                        }

                        if (entity instanceof Vindicator) {
                            return new CraftVindicator(server, (Vindicator) entity);
                        }

                        if (entity instanceof Pillager) {
                            return new CraftPillager(server, (Pillager) entity);
                        }

                        return new CraftIllager(server, (AbstractIllager) entity);
                    }

                    if (entity instanceof Ravager) {
                        return new CraftRavager(server, (Ravager) entity);
                    }

                    if (entity instanceof AbstractPiglin) {
                        if (entity instanceof Piglin) {
                            return new CraftPiglin(server, (Piglin) entity);
                        }

                        if (entity instanceof PiglinBrute) {
                            return new CraftPiglinBrute(server, (PiglinBrute) entity);
                        }

                        return new CraftPiglinAbstract(server, (AbstractPiglin) entity);
                    }

                    if (entity instanceof Zoglin) {
                        return new CraftZoglin(server, (Zoglin) entity);
                    }

                    if (entity instanceof Warden) {
                        return new CraftWarden(server, (Warden) entity);
                    }

                    return new CraftMonster(server, (Monster) entity);
                }

                if (entity instanceof Stray) {
                    return new CraftStray(server, (Stray) entity);
                }

                if (entity instanceof WitherSkeleton) {
                    return new CraftWitherSkeleton(server, (WitherSkeleton) entity);
                }

                if (entity instanceof Skeleton) {
                    return new CraftSkeleton(server, (Skeleton) entity);
                }
            } else {
                if (!(entity instanceof AbstractGolem)) {
                    if (entity instanceof AbstractVillager) {
                        if (entity instanceof Villager) {
                            return new CraftVillager(server, (Villager) entity);
                        }

                        if (entity instanceof WanderingTrader) {
                            return new CraftWanderingTrader(server, (WanderingTrader) entity);
                        }

                        return new CraftAbstractVillager(server, (AbstractVillager) entity);
                    }

                    if (entity instanceof Allay) {
                        return new CraftAllay(server, (Allay) entity);
                    }

                    return new CraftCreature(server, (PathfinderMob) entity);
                }

                if (entity instanceof SnowGolem) {
                    return new CraftSnowman(server, (SnowGolem) entity);
                }

                if (entity instanceof IronGolem) {
                    return new CraftIronGolem(server, (IronGolem) entity);
                }

                if (entity instanceof Shulker) {
                    return new CraftShulker(server, (Shulker) entity);
                }
            }
        } else {
            if (entity instanceof EnderDragonPart) {
                EnderDragonPart part = (EnderDragonPart) entity;

                if (part.parentMob instanceof EnderDragon) {
                    return new CraftEnderDragonPart(server, (EnderDragonPart) entity);
                }

                return new CraftComplexPart(server, (EnderDragonPart) entity);
            }

            if (entity instanceof ExperienceOrb) {
                return new CraftExperienceOrb(server, (ExperienceOrb) entity);
            }

            if (entity instanceof Arrow) {
                return new CraftTippedArrow(server, (Arrow) entity);
            }

            if (entity instanceof SpectralArrow) {
                return new CraftSpectralArrow(server, (SpectralArrow) entity);
            }

            if (entity instanceof AbstractArrow) {
                if (entity instanceof ThrownTrident) {
                    return new CraftTrident(server, (ThrownTrident) entity);
                }

                return new CraftArrow(server, (AbstractArrow) entity);
            }

            if (entity instanceof Boat) {
                if (entity instanceof ChestBoat) {
                    return new CraftChestBoat(server, (ChestBoat) entity);
                }

                return new CraftBoat(server, (Boat) entity);
            }

            if (entity instanceof ThrowableProjectile) {
                if (entity instanceof ThrownEgg) {
                    return new CraftEgg(server, (ThrownEgg) entity);
                }

                if (entity instanceof Snowball) {
                    return new CraftSnowball(server, (Snowball) entity);
                }

                if (entity instanceof ThrownPotion) {
                    return new CraftThrownPotion(server, (ThrownPotion) entity);
                }

                if (entity instanceof ThrownEnderpearl) {
                    return new CraftEnderPearl(server, (ThrownEnderpearl) entity);
                }

                if (entity instanceof ThrownExperienceBottle) {
                    return new CraftThrownExpBottle(server, (ThrownExperienceBottle) entity);
                }
            } else {
                if (entity instanceof FallingBlockEntity) {
                    return new CraftFallingBlock(server, (FallingBlockEntity) entity);
                }

                if (entity instanceof AbstractHurtingProjectile) {
                    if (entity instanceof SmallFireball) {
                        return new CraftSmallFireball(server, (SmallFireball) entity);
                    }

                    if (entity instanceof LargeFireball) {
                        return new CraftLargeFireball(server, (LargeFireball) entity);
                    }

                    if (entity instanceof WitherSkull) {
                        return new CraftWitherSkull(server, (WitherSkull) entity);
                    }

                    if (entity instanceof DragonFireball) {
                        return new CraftDragonFireball(server, (DragonFireball) entity);
                    }

                    return new CraftFireball(server, (AbstractHurtingProjectile) entity);
                }

                if (entity instanceof EyeOfEnder) {
                    return new CraftEnderSignal(server, (EyeOfEnder) entity);
                }

                if (entity instanceof EndCrystal) {
                    return new CraftEnderCrystal(server, (EndCrystal) entity);
                }

                if (entity instanceof FishingHook) {
                    return new CraftFishHook(server, (FishingHook) entity);
                }

                if (entity instanceof ItemEntity) {
                    return new CraftItem(server, (ItemEntity) entity);
                }

                if (entity instanceof LightningBolt) {
                    return new CraftLightningStrike(server, (LightningBolt) entity);
                }

                if (entity instanceof AbstractMinecart) {
                    if (entity instanceof MinecartFurnace) {
                        return new CraftMinecartFurnace(server, (MinecartFurnace) entity);
                    }

                    if (entity instanceof MinecartChest) {
                        return new CraftMinecartChest(server, (MinecartChest) entity);
                    }

                    if (entity instanceof MinecartTNT) {
                        return new CraftMinecartTNT(server, (MinecartTNT) entity);
                    }

                    if (entity instanceof MinecartHopper) {
                        return new CraftMinecartHopper(server, (MinecartHopper) entity);
                    }

                    if (entity instanceof MinecartSpawner) {
                        return new CraftMinecartMobSpawner(server, (MinecartSpawner) entity);
                    }

                    if (entity instanceof Minecart) {
                        return new CraftMinecartRideable(server, (Minecart) entity);
                    }

                    if (entity instanceof MinecartCommandBlock) {
                        return new CraftMinecartCommand(server, (MinecartCommandBlock) entity);
                    }
                } else {
                    if (entity instanceof HangingEntity) {
                        if (entity instanceof Painting) {
                            return new CraftPainting(server, (Painting) entity);
                        }

                        if (entity instanceof ItemFrame) {
                            if (entity instanceof GlowItemFrame) {
                                return new CraftGlowItemFrame(server, (GlowItemFrame) entity);
                            }

                            return new CraftItemFrame(server, (ItemFrame) entity);
                        }

                        if (entity instanceof LeashFenceKnotEntity) {
                            return new CraftLeash(server, (LeashFenceKnotEntity) entity);
                        }

                        return new CraftHanging(server, (HangingEntity) entity);
                    }

                    if (entity instanceof PrimedTnt) {
                        return new CraftTNTPrimed(server, (PrimedTnt) entity);
                    }

                    if (entity instanceof FireworkRocketEntity) {
                        return new CraftFirework(server, (FireworkRocketEntity) entity);
                    }

                    if (entity instanceof ShulkerBullet) {
                        return new CraftShulkerBullet(server, (ShulkerBullet) entity);
                    }

                    if (entity instanceof AreaEffectCloud) {
                        return new CraftAreaEffectCloud(server, (AreaEffectCloud) entity);
                    }

                    if (entity instanceof EvokerFangs) {
                        return new CraftEvokerFangs(server, (EvokerFangs) entity);
                    }

                    if (entity instanceof LlamaSpit) {
                        return new CraftLlamaSpit(server, (LlamaSpit) entity);
                    }

                    if (entity instanceof Marker) {
                        return new CraftMarker(server, (Marker) entity);
                    }

                    if (entity instanceof Interaction) {
                        return new CraftInteraction(server, (Interaction) entity);
                    }

                    if (entity instanceof Display) {
                        if (entity instanceof Display.BlockDisplay) {
                            return new CraftBlockDisplay(server, (Display.BlockDisplay) entity);
                        }

                        if (entity instanceof Display.ItemDisplay) {
                            return new CraftItemDisplay(server, (Display.ItemDisplay) entity);
                        }

                        if (entity instanceof Display.TextDisplay) {
                            return new CraftTextDisplay(server, (Display.TextDisplay) entity);
                        }

                        return new CraftDisplay(server, (Display) entity);
                    }
                }
            }
        }

        throw new AssertionError("Unknown entity " + (entity == null ? null : entity.getClass()));
    }

    public Location getLocation() {
        return CraftLocation.toBukkit(this.entity.position(), this.getWorld(), this.entity.getBukkitYaw(), this.entity.getXRot());
    }

    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(this.getWorld());
            loc.setX(this.entity.getX());
            loc.setY(this.entity.getY());
            loc.setZ(this.entity.getZ());
            loc.setYaw(this.entity.getBukkitYaw());
            loc.setPitch(this.entity.getXRot());
        }

        return loc;
    }

    public Vector getVelocity() {
        return CraftVector.toBukkit(this.entity.getDeltaMovement());
    }

    public void setVelocity(Vector velocity) {
        Preconditions.checkArgument(velocity != null, "velocity");
        velocity.checkFinite();
        this.entity.setDeltaMovement(CraftVector.toNMS(velocity));
        this.entity.hurtMarked = true;
    }

    public double getHeight() {
        return (double) this.getHandle().getBbHeight();
    }

    public double getWidth() {
        return (double) this.getHandle().getBbWidth();
    }

    public BoundingBox getBoundingBox() {
        AABB bb = this.getHandle().getBoundingBox();

        return new BoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
    }

    public boolean isOnGround() {
        return this.entity instanceof AbstractArrow ? ((AbstractArrow) this.entity).inGround : this.entity.onGround();
    }

    public boolean isInWater() {
        return this.entity.isInWater();
    }

    public World getWorld() {
        return this.entity.level().getWorld();
    }

    public void setRotation(float yaw, float pitch) {
        NumberConversions.checkFinite(pitch, "pitch not finite");
        NumberConversions.checkFinite(yaw, "yaw not finite");
        yaw = Location.normalizeYaw(yaw);
        pitch = Location.normalizePitch(pitch);
        this.entity.setYRot(yaw);
        this.entity.setXRot(pitch);
        this.entity.yRotO = yaw;
        this.entity.xRotO = pitch;
        this.entity.setYHeadRot(yaw);
    }

    public boolean teleport(Location location) {
        return this.teleport(location, TeleportCause.PLUGIN);
    }

    public boolean teleport(Location location, TeleportCause cause) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        location.checkFinite();
        if (!this.entity.isVehicle() && !this.entity.isRemoved()) {
            this.entity.stopRiding();
            if (location.getWorld() != null && !location.getWorld().equals(this.getWorld())) {
                Preconditions.checkState(!this.entity.generation, "Cannot teleport entity to an other world during world generation");
                this.entity.teleportTo(((CraftWorld) location.getWorld()).getHandle(), CraftLocation.toVec3D(location));
                return true;
            } else {
                this.entity.absMoveTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
                this.entity.setYHeadRot(location.getYaw());
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean teleport(Entity destination) {
        return this.teleport(destination.getLocation());
    }

    public boolean teleport(Entity destination, TeleportCause cause) {
        return this.teleport(destination.getLocation(), cause);
    }

    public List getNearbyEntities(double x, double y, double z) {
        Preconditions.checkState(!this.entity.generation, "Cannot get nearby entities during world generation");
        AsyncCatcher.catchOp("getNearbyEntities");
        List notchEntityList = this.entity.level().getEntities(this.entity, this.entity.getBoundingBox().inflate(x, y, z), Predicates.alwaysTrue());
        ArrayList bukkitEntityList = new ArrayList(notchEntityList.size());
        Iterator iterator = notchEntityList.iterator();

        while (iterator.hasNext()) {
            net.minecraft.world.entity.Entity e = (net.minecraft.world.entity.Entity) iterator.next();

            bukkitEntityList.add(e.getBukkitEntity());
        }

        return bukkitEntityList;
    }

    public int getEntityId() {
        return this.entity.getId();
    }

    public int getFireTicks() {
        return this.entity.getRemainingFireTicks();
    }

    public int getMaxFireTicks() {
        return this.entity.getFireImmuneTicks();
    }

    public void setFireTicks(int ticks) {
        this.entity.setRemainingFireTicks(ticks);
    }

    public void setVisualFire(boolean fire) {
        this.getHandle().hasVisualFire = fire;
    }

    public boolean isVisualFire() {
        return this.getHandle().hasVisualFire;
    }

    public int getFreezeTicks() {
        return this.getHandle().getTicksFrozen();
    }

    public int getMaxFreezeTicks() {
        return this.getHandle().getTicksRequiredToFreeze();
    }

    public void setFreezeTicks(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "Ticks (%s) cannot be less than 0", ticks);
        this.getHandle().setTicksFrozen(ticks);
    }

    public boolean isFrozen() {
        return this.getHandle().isFullyFrozen();
    }

    public void remove() {
        this.entity.discard();
    }

    public boolean isDead() {
        return !this.entity.isAlive();
    }

    public boolean isValid() {
        return this.entity.isAlive() && this.entity.valid && this.entity.isChunkLoaded();
    }

    public Server getServer() {
        return this.server;
    }

    public boolean isPersistent() {
        return this.entity.persist;
    }

    public void setPersistent(boolean persistent) {
        this.entity.persist = persistent;
    }

    public Vector getMomentum() {
        return this.getVelocity();
    }

    public void setMomentum(Vector value) {
        this.setVelocity(value);
    }

    public Entity getPassenger() {
        return this.isEmpty() ? null : ((net.minecraft.world.entity.Entity) this.getHandle().passengers.get(0)).getBukkitEntity();
    }

    public boolean setPassenger(Entity passenger) {
        Preconditions.checkArgument(!this.equals(passenger), "Entity cannot ride itself.");
        if (passenger instanceof CraftEntity) {
            this.eject();
            return ((CraftEntity) passenger).getHandle().startRiding(this.getHandle());
        } else {
            return false;
        }
    }

    public List getPassengers() {
        return Lists.newArrayList(Lists.transform(this.getHandle().passengers, (inputx) -> {
            return inputx.getBukkitEntity();
        }));
    }

    public boolean addPassenger(Entity passenger) {
        Preconditions.checkArgument(passenger != null, "Entity passenger cannot be null");
        Preconditions.checkArgument(!this.equals(passenger), "Entity cannot ride itself.");
        return ((CraftEntity) passenger).getHandle().startRiding(this.getHandle(), true);
    }

    public boolean removePassenger(Entity passenger) {
        Preconditions.checkArgument(passenger != null, "Entity passenger cannot be null");
        ((CraftEntity) passenger).getHandle().stopRiding();
        return true;
    }

    public boolean isEmpty() {
        return !this.getHandle().isVehicle();
    }

    public boolean eject() {
        if (this.isEmpty()) {
            return false;
        } else {
            this.getHandle().ejectPassengers();
            return true;
        }
    }

    public float getFallDistance() {
        return this.getHandle().fallDistance;
    }

    public void setFallDistance(float distance) {
        this.getHandle().fallDistance = distance;
    }

    public void setLastDamageCause(EntityDamageEvent event) {
        this.lastDamageEvent = event;
    }

    public EntityDamageEvent getLastDamageCause() {
        return this.lastDamageEvent;
    }

    public UUID getUniqueId() {
        return this.getHandle().getUUID();
    }

    public int getTicksLived() {
        return this.getHandle().tickCount;
    }

    public void setTicksLived(int value) {
        Preconditions.checkArgument(value > 0, "Age value (%s) must be greater than 0", value);
        this.getHandle().tickCount = value;
    }

    public net.minecraft.world.entity.Entity getHandle() {
        return this.entity;
    }

    public final EntityType getType() {
        return this.entityType;
    }

    public void playEffect(EntityEffect type) {
        Preconditions.checkArgument(type != null, "type");
        Preconditions.checkState(!this.entity.generation, "Cannot play effect during world generation");
        if (type.getApplicable().isInstance(this)) {
            this.getHandle().level().broadcastEntityEvent(this.getHandle(), type.getData());
        }

    }

    public Sound getSwimSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().getSwimSound0());
    }

    public Sound getSwimSplashSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().getSwimSplashSound0());
    }

    public Sound getSwimHighSpeedSplashSound() {
        return CraftSound.minecraftToBukkit(this.getHandle().getSwimHighSpeedSplashSound0());
    }

    public void setHandle(net.minecraft.world.entity.Entity entity) {
        this.entity = entity;
    }

    public String toString() {
        return "CraftEntity{id=" + this.getEntityId() + '}';
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            CraftEntity other = (CraftEntity) obj;

            return this.getEntityId() == other.getEntityId();
        }
    }

    public int hashCode() {
        byte hash = 7;
        int hash = 29 * hash + this.getEntityId();

        return hash;
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        this.server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    public List getMetadata(String metadataKey) {
        return this.server.getEntityMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return this.server.getEntityMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        this.server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    public boolean isInsideVehicle() {
        return this.getHandle().isPassenger();
    }

    public boolean leaveVehicle() {
        if (!this.isInsideVehicle()) {
            return false;
        } else {
            this.getHandle().stopRiding();
            return true;
        }
    }

    public Entity getVehicle() {
        return !this.isInsideVehicle() ? null : this.getHandle().getVehicle().getBukkitEntity();
    }

    public void setCustomName(String name) {
        if (name != null && name.length() > 256) {
            name = name.substring(0, 256);
        }

        this.getHandle().setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    public String getCustomName() {
        Component name = this.getHandle().getCustomName();

        return name == null ? null : CraftChatMessage.fromComponent(name);
    }

    public void setCustomNameVisible(boolean flag) {
        this.getHandle().setCustomNameVisible(flag);
    }

    public boolean isCustomNameVisible() {
        return this.getHandle().isCustomNameVisible();
    }

    public void setVisibleByDefault(boolean visible) {
        if (this.getHandle().visibleByDefault != visible) {
            Iterator iterator;
            org.bukkit.entity.Player player;

            if (visible) {
                iterator = this.server.getOnlinePlayers().iterator();

                while (iterator.hasNext()) {
                    player = (org.bukkit.entity.Player) iterator.next();
                    ((CraftPlayer) player).resetAndShowEntity(this);
                }
            } else {
                iterator = this.server.getOnlinePlayers().iterator();

                while (iterator.hasNext()) {
                    player = (org.bukkit.entity.Player) iterator.next();
                    ((CraftPlayer) player).resetAndHideEntity(this);
                }
            }

            this.getHandle().visibleByDefault = visible;
        }

    }

    public boolean isVisibleByDefault() {
        return this.getHandle().visibleByDefault;
    }

    public Set getTrackedBy() {
        Preconditions.checkState(!this.entity.generation, "Cannot get tracking players during world generation");
        Builder players = ImmutableSet.builder();
        ServerLevel world = ((CraftWorld) this.getWorld()).getHandle();
        ChunkMap.TrackedEntity entityTracker = (ChunkMap.TrackedEntity) world.getChunkSource().chunkMap.entityMap.get(this.getEntityId());

        if (entityTracker != null) {
            Iterator iterator = entityTracker.seenBy.iterator();

            while (iterator.hasNext()) {
                ServerPlayerConnection connection = (ServerPlayerConnection) iterator.next();

                players.add(connection.getPlayer().getBukkitEntity());
            }
        }

        return players.build();
    }

    public void sendMessage(String message) {}

    public void sendMessage(String... messages) {}

    public void sendMessage(UUID sender, String message) {
        this.sendMessage(message);
    }

    public void sendMessage(UUID sender, String... messages) {
        this.sendMessage(messages);
    }

    public String getName() {
        return CraftChatMessage.fromComponent(this.getHandle().getName());
    }

    public boolean isPermissionSet(String name) {
        return getPermissibleBase().isPermissionSet(name);
    }

    public boolean isPermissionSet(Permission perm) {
        return getPermissibleBase().isPermissionSet(perm);
    }

    public boolean hasPermission(String name) {
        return getPermissibleBase().hasPermission(name);
    }

    public boolean hasPermission(Permission perm) {
        return getPermissibleBase().hasPermission(perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return getPermissibleBase().addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return getPermissibleBase().addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return getPermissibleBase().addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return getPermissibleBase().addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        getPermissibleBase().removeAttachment(attachment);
    }

    public void recalculatePermissions() {
        getPermissibleBase().recalculatePermissions();
    }

    public Set getEffectivePermissions() {
        return getPermissibleBase().getEffectivePermissions();
    }

    public boolean isOp() {
        return getPermissibleBase().isOp();
    }

    public void setOp(boolean value) {
        getPermissibleBase().setOp(value);
    }

    public void setGlowing(boolean flag) {
        this.getHandle().setGlowingTag(flag);
    }

    public boolean isGlowing() {
        return this.getHandle().isCurrentlyGlowing();
    }

    public void setInvulnerable(boolean flag) {
        this.getHandle().setInvulnerable(flag);
    }

    public boolean isInvulnerable() {
        return this.getHandle().isInvulnerableTo(this.getHandle().damageSources().generic());
    }

    public boolean isSilent() {
        return this.getHandle().isSilent();
    }

    public void setSilent(boolean flag) {
        this.getHandle().setSilent(flag);
    }

    public boolean hasGravity() {
        return !this.getHandle().isNoGravity();
    }

    public void setGravity(boolean gravity) {
        this.getHandle().setNoGravity(!gravity);
    }

    public int getPortalCooldown() {
        return this.getHandle().portalCooldown;
    }

    public void setPortalCooldown(int cooldown) {
        this.getHandle().portalCooldown = cooldown;
    }

    public Set getScoreboardTags() {
        return this.getHandle().getTags();
    }

    public boolean addScoreboardTag(String tag) {
        return this.getHandle().addTag(tag);
    }

    public boolean removeScoreboardTag(String tag) {
        return this.getHandle().removeTag(tag);
    }

    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(this.getHandle().getPistonPushReaction().ordinal());
    }

    public BlockFace getFacing() {
        return CraftBlock.notchToBlockFace(this.getHandle().getMotionDirection());
    }

    public CraftPersistentDataContainer getPersistentDataContainer() {
        return this.persistentDataContainer;
    }

    public Pose getPose() {
        return Pose.values()[this.getHandle().getPose().ordinal()];
    }

    public SpawnCategory getSpawnCategory() {
        return CraftSpawnCategory.toBukkit(this.getHandle().getType().getCategory());
    }

    public void storeBukkitValues(CompoundTag c) {
        if (!this.persistentDataContainer.isEmpty()) {
            c.put("BukkitValues", this.persistentDataContainer.toTagCompound());
        }

    }

    public void readBukkitValues(CompoundTag c) {
        Tag base = c.get("BukkitValues");

        if (base instanceof CompoundTag) {
            this.persistentDataContainer.putAll((CompoundTag) base);
        }

    }

    protected CompoundTag save() {
        CompoundTag nbttagcompound = new CompoundTag();

        nbttagcompound.putString("id", this.getHandle().getEncodeId());
        this.getHandle().saveWithoutId(nbttagcompound);
        return nbttagcompound;
    }

    protected void update() {
        if (this.getHandle().isAlive()) {
            ServerLevel world = ((CraftWorld) this.getWorld()).getHandle();
            ChunkMap.TrackedEntity entityTracker = (ChunkMap.TrackedEntity) world.getChunkSource().chunkMap.entityMap.get(this.getEntityId());

            if (entityTracker != null) {
                entityTracker.broadcast(this.getHandle().getAddEntityPacket());
            }
        }
    }

    private static PermissibleBase getPermissibleBase() {
        if (CraftEntity.perm == null) {
            CraftEntity.perm = new PermissibleBase(new ServerOperator() {
                public boolean isOp() {
                    return false;
                }

                public void setOp(boolean value) {}
            });
        }

        return CraftEntity.perm;
    }

    public Spigot spigot() {
        return this.spigot;
    }
}
