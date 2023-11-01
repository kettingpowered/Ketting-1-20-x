package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.entity.memory.CraftMemoryMapper;
import org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryLectern;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftMerchantCustom;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

public class CraftHumanEntity extends CraftLivingEntity implements HumanEntity {

    private CraftInventoryPlayer inventory;
    private final CraftInventory enderChest;
    protected final PermissibleBase perm = new PermissibleBase(this);
    private boolean op;
    private GameMode mode;

    public CraftHumanEntity(CraftServer server, Player entity) {
        super(server, (LivingEntity) entity);
        this.mode = server.getDefaultGameMode();
        this.inventory = new CraftInventoryPlayer(entity.getInventory());
        this.enderChest = new CraftInventory(entity.getEnderChestInventory());
    }

    public PlayerInventory getInventory() {
        return this.inventory;
    }

    public EntityEquipment getEquipment() {
        return this.inventory;
    }

    public Inventory getEnderChest() {
        return this.enderChest;
    }

    public MainHand getMainHand() {
        return this.getHandle().getMainArm() == HumanoidArm.LEFT ? MainHand.LEFT : MainHand.RIGHT;
    }

    public ItemStack getItemInHand() {
        return this.getInventory().getItemInHand();
    }

    public void setItemInHand(ItemStack item) {
        this.getInventory().setItemInHand(item);
    }

    public ItemStack getItemOnCursor() {
        return CraftItemStack.asCraftMirror(this.getHandle().containerMenu.getCarried());
    }

    public void setItemOnCursor(ItemStack item) {
        net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(item);

        this.getHandle().containerMenu.setCarried(stack);
        if (this instanceof CraftPlayer) {
            this.getHandle().containerMenu.broadcastCarriedItem();
        }

    }

    public int getSleepTicks() {
        return this.getHandle().sleepCounter;
    }

    public boolean sleep(Location location, boolean force) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(location.getWorld() != null, "Location needs to be in a world");
        Preconditions.checkArgument(location.getWorld().equals(this.getWorld()), "Cannot sleep across worlds");
        BlockPos blockposition = CraftLocation.toBlockPosition(location);
        BlockState iblockdata = this.getHandle().level().getBlockState(blockposition);

        if (!(iblockdata.getBlock() instanceof BedBlock)) {
            return false;
        } else if (this.getHandle().startSleepInBed(blockposition, force).left().isPresent()) {
            return false;
        } else {
            iblockdata = (BlockState) iblockdata.setValue(BedBlock.OCCUPIED, true);
            this.getHandle().level().setBlock(blockposition, iblockdata, 4);
            return true;
        }
    }

    public void wakeup(boolean setSpawnLocation) {
        Preconditions.checkState(this.isSleeping(), "Cannot wakeup if not sleeping");
        this.getHandle().stopSleepInBed(true, setSpawnLocation);
    }

    public Location getBedLocation() {
        Preconditions.checkState(this.isSleeping(), "Not sleeping");
        BlockPos bed = (BlockPos) this.getHandle().getSleepingPos().get();

        return CraftLocation.toBukkit(bed, this.getWorld());
    }

    public String getName() {
        return this.getHandle().getScoreboardName();
    }

    public boolean isOp() {
        return this.op;
    }

    public boolean isPermissionSet(String name) {
        return this.perm.isPermissionSet(name);
    }

    public boolean isPermissionSet(Permission perm) {
        return this.perm.isPermissionSet(perm);
    }

    public boolean hasPermission(String name) {
        return this.perm.hasPermission(name);
    }

    public boolean hasPermission(Permission perm) {
        return this.perm.hasPermission(perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return this.perm.addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return this.perm.addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return this.perm.addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return this.perm.addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        this.perm.removeAttachment(attachment);
    }

    public void recalculatePermissions() {
        this.perm.recalculatePermissions();
    }

    public void setOp(boolean value) {
        this.op = value;
        this.perm.recalculatePermissions();
    }

    public Set getEffectivePermissions() {
        return this.perm.getEffectivePermissions();
    }

    public GameMode getGameMode() {
        return this.mode;
    }

    public void setGameMode(GameMode mode) {
        Preconditions.checkArgument(mode != null, "GameMode cannot be null");
        this.mode = mode;
    }

    public Player getHandle() {
        return (Player) this.entity;
    }

    public void setHandle(Player entity) {
        super.setHandle((LivingEntity) entity);
        this.inventory = new CraftInventoryPlayer(entity.getInventory());
    }

    public String toString() {
        return "CraftHumanEntity{id=" + this.getEntityId() + "name=" + this.getName() + '}';
    }

    public InventoryView getOpenInventory() {
        return this.getHandle().containerMenu.getBukkitView();
    }

    public InventoryView openInventory(Inventory inventory) {
        if (!(this.getHandle() instanceof ServerPlayer)) {
            return null;
        } else {
            ServerPlayer player = (ServerPlayer) this.getHandle();
            AbstractContainerMenu formerContainer = this.getHandle().containerMenu;
            MenuProvider iinventory = null;

            if (inventory instanceof CraftInventoryDoubleChest) {
                iinventory = ((CraftInventoryDoubleChest) inventory).tile;
            } else if (inventory instanceof CraftInventoryLectern) {
                iinventory = ((CraftInventoryLectern) inventory).tile;
            } else if (inventory instanceof CraftInventory) {
                CraftInventory craft = (CraftInventory) inventory;

                if (craft.getInventory() instanceof MenuProvider) {
                    iinventory = (MenuProvider) craft.getInventory();
                }
            }

            if (iinventory instanceof MenuProvider && iinventory instanceof BlockEntity) {
                BlockEntity te = (BlockEntity) iinventory;

                if (!te.hasLevel()) {
                    te.setLevel(this.getHandle().level());
                }
            }

            MenuType container = CraftContainer.getNotchInventoryType(inventory);

            if (iinventory instanceof MenuProvider) {
                this.getHandle().openMenu(iinventory);
            } else {
                openCustomInventory(inventory, player, container);
            }

            if (this.getHandle().containerMenu == formerContainer) {
                return null;
            } else {
                this.getHandle().containerMenu.checkReachable = false;
                return this.getHandle().containerMenu.getBukkitView();
            }
        }
    }

    private static void openCustomInventory(Inventory inventory, ServerPlayer player, MenuType windowType) {
        if (player.connection != null) {
            Preconditions.checkArgument(windowType != null, "Unknown windowType");
            CraftContainer container = new CraftContainer(inventory, player, player.nextContainerCounter());
            AbstractContainerMenu container = CraftEventFactory.callInventoryOpenEvent(player, container);

            if (container != null) {
                String title = container.getBukkitView().getTitle();

                player.connection.send(new ClientboundOpenScreenPacket(container.containerId, windowType, CraftChatMessage.fromString(title)[0]));
                player.containerMenu = container;
                player.initMenu(container);
            }
        }
    }

    public InventoryView openWorkbench(Location location, boolean force) {
        if (location == null) {
            location = this.getLocation();
        }

        if (!force) {
            Block block = location.getBlock();

            if (block.getType() != Material.CRAFTING_TABLE) {
                return null;
            }
        }

        this.getHandle().openMenu(((CraftingTableBlock) Blocks.CRAFTING_TABLE).getMenuProvider((BlockState) null, this.getHandle().level(), CraftLocation.toBlockPosition(location)));
        if (force) {
            this.getHandle().containerMenu.checkReachable = false;
        }

        return this.getHandle().containerMenu.getBukkitView();
    }

    public InventoryView openEnchanting(Location location, boolean force) {
        if (location == null) {
            location = this.getLocation();
        }

        if (!force) {
            Block block = location.getBlock();

            if (block.getType() != Material.ENCHANTING_TABLE) {
                return null;
            }
        }

        BlockPos pos = CraftLocation.toBlockPosition(location);

        this.getHandle().openMenu(((EnchantmentTableBlock) Blocks.ENCHANTING_TABLE).getMenuProvider((BlockState) null, this.getHandle().level(), pos));
        if (force) {
            this.getHandle().containerMenu.checkReachable = false;
        }

        return this.getHandle().containerMenu.getBukkitView();
    }

    public void openInventory(InventoryView inventory) {
        if (this.getHandle() instanceof ServerPlayer) {
            if (((ServerPlayer) this.getHandle()).connection != null) {
                if (this.getHandle().containerMenu != this.getHandle().inventoryMenu) {
                    ((ServerPlayer) this.getHandle()).connection.handleContainerClose(new ServerboundContainerClosePacket(this.getHandle().containerMenu.containerId));
                }

                ServerPlayer player = (ServerPlayer) this.getHandle();
                Object container;

                if (inventory instanceof CraftInventoryView) {
                    container = ((CraftInventoryView) inventory).getHandle();
                } else {
                    container = new CraftContainer(inventory, this.getHandle(), player.nextContainerCounter());
                }

                AbstractContainerMenu container = CraftEventFactory.callInventoryOpenEvent(player, (AbstractContainerMenu) container);

                if (container != null) {
                    MenuType windowType = CraftContainer.getNotchInventoryType(inventory.getTopInventory());
                    String title = inventory.getTitle();

                    player.connection.send(new ClientboundOpenScreenPacket(container.containerId, windowType, CraftChatMessage.fromString(title)[0]));
                    player.containerMenu = container;
                    player.initMenu(container);
                }
            }
        }
    }

    public InventoryView openMerchant(Villager villager, boolean force) {
        Preconditions.checkNotNull(villager, "villager cannot be null");
        return this.openMerchant((Merchant) villager, force);
    }

    public InventoryView openMerchant(Merchant merchant, boolean force) {
        Preconditions.checkNotNull(merchant, "merchant cannot be null");
        if (!force && merchant.isTrading()) {
            return null;
        } else {
            if (merchant.isTrading()) {
                merchant.getTrader().closeInventory();
            }

            int level = 1;
            Object mcMerchant;
            Component name;

            if (merchant instanceof CraftAbstractVillager) {
                mcMerchant = ((CraftAbstractVillager) merchant).getHandle();
                name = ((CraftAbstractVillager) merchant).getHandle().getDisplayName();
                if (merchant instanceof CraftVillager) {
                    level = ((CraftVillager) merchant).getHandle().getVillagerData().getLevel();
                }
            } else {
                if (!(merchant instanceof CraftMerchantCustom)) {
                    throw new IllegalArgumentException("Can't open merchant " + merchant.toString());
                }

                mcMerchant = ((CraftMerchantCustom) merchant).getMerchant();
                name = ((CraftMerchantCustom) merchant).getMerchant().getScoreboardDisplayName();
            }

            ((net.minecraft.world.item.trading.Merchant) mcMerchant).setTradingPlayer(this.getHandle());
            ((net.minecraft.world.item.trading.Merchant) mcMerchant).openTradingScreen(this.getHandle(), name, level);
            return this.getHandle().containerMenu.getBukkitView();
        }
    }

    public void closeInventory() {
        this.getHandle().closeContainer();
    }

    public boolean isBlocking() {
        return this.getHandle().isBlocking();
    }

    public boolean isHandRaised() {
        return this.getHandle().isUsingItem();
    }

    public ItemStack getItemInUse() {
        net.minecraft.world.item.ItemStack item = this.getHandle().getUseItem();

        return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
    }

    public boolean setWindowProperty(Property prop, int value) {
        return false;
    }

    public int getEnchantmentSeed() {
        return this.getHandle().enchantmentSeed;
    }

    public void setEnchantmentSeed(int i) {
        this.getHandle().enchantmentSeed = i;
    }

    public int getExpToLevel() {
        return this.getHandle().getXpNeededForNextLevel();
    }

    public float getAttackCooldown() {
        return this.getHandle().getAttackStrengthScale(0.5F);
    }

    public boolean hasCooldown(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);
        return this.getHandle().getCooldowns().isOnCooldown(CraftMagicNumbers.getItem(material));
    }

    public int getCooldown(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);
        ItemCooldowns.CooldownInstance cooldown = (ItemCooldowns.CooldownInstance) this.getHandle().getCooldowns().cooldowns.get(CraftMagicNumbers.getItem(material));

        return cooldown == null ? 0 : Math.max(0, cooldown.endTime - this.getHandle().getCooldowns().tickCount);
    }

    public void setCooldown(Material material, int ticks) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);
        Preconditions.checkArgument(ticks >= 0, "Cannot have negative cooldown");
        this.getHandle().getCooldowns().addCooldown(CraftMagicNumbers.getItem(material), ticks);
    }

    public boolean discoverRecipe(NamespacedKey recipe) {
        return this.discoverRecipes(Arrays.asList(recipe)) != 0;
    }

    public int discoverRecipes(Collection recipes) {
        return this.getHandle().awardRecipes(this.bukkitKeysToMinecraftRecipes(recipes));
    }

    public boolean undiscoverRecipe(NamespacedKey recipe) {
        return this.undiscoverRecipes(Arrays.asList(recipe)) != 0;
    }

    public int undiscoverRecipes(Collection recipes) {
        return this.getHandle().resetRecipes(this.bukkitKeysToMinecraftRecipes(recipes));
    }

    public boolean hasDiscoveredRecipe(NamespacedKey recipe) {
        return false;
    }

    public Set getDiscoveredRecipes() {
        return ImmutableSet.of();
    }

    private Collection bukkitKeysToMinecraftRecipes(Collection recipeKeys) {
        ArrayList recipes = new ArrayList();
        RecipeManager manager = this.getHandle().level().getServer().getRecipeManager();
        Iterator iterator = recipeKeys.iterator();

        while (iterator.hasNext()) {
            NamespacedKey recipeKey = (NamespacedKey) iterator.next();
            Optional recipe = manager.byKey(CraftNamespacedKey.toMinecraft(recipeKey));

            if (recipe.isPresent()) {
                recipes.add((RecipeHolder) recipe.get());
            }
        }

        return recipes;
    }

    public Entity getShoulderEntityLeft() {
        if (!this.getHandle().getShoulderEntityLeft().isEmpty()) {
            Optional shoulder = EntityType.create(this.getHandle().getShoulderEntityLeft(), this.getHandle().level());

            return !shoulder.isPresent() ? null : ((net.minecraft.world.entity.Entity) shoulder.get()).getBukkitEntity();
        } else {
            return null;
        }
    }

    public void setShoulderEntityLeft(Entity entity) {
        this.getHandle().setShoulderEntityLeft(entity == null ? new CompoundTag() : ((CraftEntity) entity).save());
        if (entity != null) {
            entity.remove();
        }

    }

    public Entity getShoulderEntityRight() {
        if (!this.getHandle().getShoulderEntityRight().isEmpty()) {
            Optional shoulder = EntityType.create(this.getHandle().getShoulderEntityRight(), this.getHandle().level());

            return !shoulder.isPresent() ? null : ((net.minecraft.world.entity.Entity) shoulder.get()).getBukkitEntity();
        } else {
            return null;
        }
    }

    public void setShoulderEntityRight(Entity entity) {
        this.getHandle().setShoulderEntityRight(entity == null ? new CompoundTag() : ((CraftEntity) entity).save());
        if (entity != null) {
            entity.remove();
        }

    }

    public boolean dropItem(boolean dropAll) {
        return !(this.getHandle() instanceof ServerPlayer) ? false : ((ServerPlayer) this.getHandle()).drop(dropAll);
    }

    public float getExhaustion() {
        return this.getHandle().getFoodData().exhaustionLevel;
    }

    public void setExhaustion(float value) {
        this.getHandle().getFoodData().exhaustionLevel = value;
    }

    public float getSaturation() {
        return this.getHandle().getFoodData().saturationLevel;
    }

    public void setSaturation(float value) {
        this.getHandle().getFoodData().saturationLevel = value;
    }

    public int getFoodLevel() {
        return this.getHandle().getFoodData().foodLevel;
    }

    public void setFoodLevel(int value) {
        this.getHandle().getFoodData().foodLevel = value;
    }

    public int getSaturatedRegenRate() {
        return this.getHandle().getFoodData().saturatedRegenRate;
    }

    public void setSaturatedRegenRate(int i) {
        this.getHandle().getFoodData().saturatedRegenRate = i;
    }

    public int getUnsaturatedRegenRate() {
        return this.getHandle().getFoodData().unsaturatedRegenRate;
    }

    public void setUnsaturatedRegenRate(int i) {
        this.getHandle().getFoodData().unsaturatedRegenRate = i;
    }

    public int getStarvationRate() {
        return this.getHandle().getFoodData().starvationRate;
    }

    public void setStarvationRate(int i) {
        this.getHandle().getFoodData().starvationRate = i;
    }

    public Location getLastDeathLocation() {
        return (Location) this.getHandle().getLastDeathLocation().map(CraftMemoryMapper::fromNms).orElse((Object) null);
    }

    public void setLastDeathLocation(Location location) {
        if (location == null) {
            this.getHandle().setLastDeathLocation(Optional.empty());
        } else {
            this.getHandle().setLastDeathLocation(Optional.of(CraftMemoryMapper.toNms(location)));
        }

    }

    public Firework fireworkBoost(ItemStack fireworkItemStack) {
        Preconditions.checkArgument(fireworkItemStack != null, "fireworkItemStack must not be null");
        Preconditions.checkArgument(fireworkItemStack.getType() == Material.FIREWORK_ROCKET, "fireworkItemStack must be of type %s", Material.FIREWORK_ROCKET);
        FireworkRocketEntity fireworks = new FireworkRocketEntity(this.getHandle().level(), CraftItemStack.asNMSCopy(fireworkItemStack), this.getHandle());
        boolean success = this.getHandle().level().addFreshEntity(fireworks, SpawnReason.CUSTOM);

        return success ? (Firework) fireworks.getBukkitEntity() : null;
    }
}
