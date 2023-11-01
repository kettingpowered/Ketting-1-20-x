package org.bukkit.craftbukkit.v1_20_R2.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.ItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;

public class CraftContainer extends AbstractContainerMenu {

    private final InventoryView view;
    private InventoryType cachedType;
    private AbstractContainerMenu delegate;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$event$inventory$InventoryType;

    public CraftContainer(InventoryView view, Player player, int id) {
        super(getNotchInventoryType(view.getTopInventory()), id);
        this.view = view;
        Container top = ((CraftInventory) view.getTopInventory()).getInventory();
        Inventory bottom = (Inventory) ((CraftInventory) view.getBottomInventory()).getInventory();

        this.cachedType = view.getType();
        this.setupSlots(top, bottom, player);
    }

    public CraftContainer(final org.bukkit.inventory.Inventory inventory, final Player player, int id) {
        this(new InventoryView() {
            private final String originalTitle = inventory instanceof CraftInventoryCustom ? ((CraftInventoryCustom.MinecraftInventory) ((CraftInventory) inventory).getInventory()).getTitle() : inventory.getType().getDefaultTitle();
            private String title;

            {
                this.title = this.originalTitle;
            }

            public org.bukkit.inventory.Inventory getTopInventory() {
                return inventory;
            }

            public org.bukkit.inventory.Inventory getBottomInventory() {
                return this.getPlayer().getInventory();
            }

            public HumanEntity getPlayer() {
                return player.getBukkitEntity();
            }

            public InventoryType getType() {
                return inventory.getType();
            }

            public String getTitle() {
                return this.title;
            }

            public String getOriginalTitle() {
                return this.originalTitle;
            }

            public void setTitle(String title) {
                CraftInventoryView.sendInventoryTitleChange(this, title);
                this.title = title;
            }
        }, player, id);
    }

    public InventoryView getBukkitView() {
        return this.view;
    }

    public static MenuType getNotchInventoryType(org.bukkit.inventory.Inventory inventory) {
        switch ($SWITCH_TABLE$org$bukkit$event$inventory$InventoryType()[inventory.getType().ordinal()]) {
            case 1:
            case 9:
            case 12:
            case 18:
                switch (inventory.getSize()) {
                    case 9:
                        return MenuType.GENERIC_9x1;
                    case 18:
                        return MenuType.GENERIC_9x2;
                    case 27:
                        return MenuType.GENERIC_9x3;
                    case 36:
                    case 41:
                        return MenuType.GENERIC_9x4;
                    case 45:
                        return MenuType.GENERIC_9x5;
                    case 54:
                        return MenuType.GENERIC_9x6;
                    default:
                        throw new IllegalArgumentException("Unsupported custom inventory size " + inventory.getSize());
                }
            case 2:
                return MenuType.GENERIC_3x3;
            case 3:
                return MenuType.GENERIC_3x3;
            case 4:
                return MenuType.FURNACE;
            case 5:
                return MenuType.CRAFTING;
            case 6:
            case 10:
            case 11:
                throw new IllegalArgumentException("Can't open a " + inventory.getType() + " inventory!");
            case 7:
                return MenuType.ENCHANTMENT;
            case 8:
                return MenuType.BREWING_STAND;
            case 13:
                return MenuType.ANVIL;
            case 14:
            case 29:
                return MenuType.SMITHING;
            case 15:
                return MenuType.BEACON;
            case 16:
                return MenuType.HOPPER;
            case 17:
                return MenuType.SHULKER_BOX;
            case 19:
                return MenuType.BLAST_FURNACE;
            case 20:
                return MenuType.LECTERN;
            case 21:
                return MenuType.SMOKER;
            case 22:
                return MenuType.LOOM;
            case 23:
                return MenuType.CARTOGRAPHY_TABLE;
            case 24:
                return MenuType.GRINDSTONE;
            case 25:
                return MenuType.STONECUTTER;
            case 26:
            case 27:
            case 28:
            default:
                return MenuType.GENERIC_9x3;
        }
    }

    private void setupSlots(Container top, Inventory bottom, Player entityhuman) {
        byte windowId = -1;

        switch ($SWITCH_TABLE$org$bukkit$event$inventory$InventoryType()[this.cachedType.ordinal()]) {
            case 1:
            case 9:
            case 12:
            case 18:
                this.delegate = new ChestMenu(MenuType.GENERIC_9x3, windowId, bottom, top, top.getContainerSize() / 9);
                break;
            case 2:
            case 3:
                this.delegate = new DispenserMenu(windowId, bottom, top);
                break;
            case 4:
                this.delegate = new FurnaceMenu(windowId, bottom, top, new SimpleContainerData(4));
                break;
            case 5:
            case 6:
                this.setupWorkbench(top, bottom);
                break;
            case 7:
                this.delegate = new EnchantmentMenu(windowId, bottom);
                break;
            case 8:
                this.delegate = new BrewingStandMenu(windowId, bottom, top, new SimpleContainerData(2));
            case 10:
            case 26:
            case 27:
            case 28:
            default:
                break;
            case 11:
                this.delegate = new MerchantMenu(windowId, bottom);
                break;
            case 13:
                this.setupAnvil(top, bottom);
                break;
            case 14:
            case 29:
                this.setupSmithing(top, bottom);
                break;
            case 15:
                this.delegate = new BeaconMenu(windowId, bottom);
                break;
            case 16:
                this.delegate = new HopperMenu(windowId, bottom, top);
                break;
            case 17:
                this.delegate = new ShulkerBoxMenu(windowId, bottom, top);
                break;
            case 19:
                this.delegate = new BlastFurnaceMenu(windowId, bottom, top, new SimpleContainerData(4));
                break;
            case 20:
                this.delegate = new LecternMenu(windowId, top, new SimpleContainerData(1), bottom);
                break;
            case 21:
                this.delegate = new SmokerMenu(windowId, bottom, top, new SimpleContainerData(4));
                break;
            case 22:
                this.delegate = new LoomMenu(windowId, bottom);
                break;
            case 23:
                this.delegate = new CartographyTableMenu(windowId, bottom);
                break;
            case 24:
                this.delegate = new GrindstoneMenu(windowId, bottom);
                break;
            case 25:
                this.delegate = new StonecutterMenu(windowId, bottom);
        }

        if (this.delegate != null) {
            this.lastSlots = this.delegate.lastSlots;
            this.slots = this.delegate.slots;
            this.remoteSlots = this.delegate.remoteSlots;
        }

        switch ($SWITCH_TABLE$org$bukkit$event$inventory$InventoryType()[this.cachedType.ordinal()]) {
            case 5:
                this.delegate = new CraftingMenu(windowId, bottom);
                break;
            case 13:
                this.delegate = new AnvilMenu(windowId, bottom);
        }

    }

    private void setupWorkbench(Container top, Container bottom) {
        this.addSlot(new Slot(top, 0, 124, 35));

        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 3; ++col) {
                this.addSlot(new Slot(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.addSlot(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (col = 0; col < 9; ++col) {
            this.addSlot(new Slot(bottom, col, 8 + col * 18, 142));
        }

    }

    private void setupAnvil(Container top, Container bottom) {
        this.addSlot(new Slot(top, 0, 27, 47));
        this.addSlot(new Slot(top, 1, 76, 47));
        this.addSlot(new Slot(top, 2, 134, 47));

        int row;

        for (row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            this.addSlot(new Slot(bottom, row, 8 + row * 18, 142));
        }

    }

    private void setupSmithing(Container top, Container bottom) {
        this.addSlot(new Slot(top, 0, 8, 48));
        this.addSlot(new Slot(top, 1, 26, 48));
        this.addSlot(new Slot(top, 2, 44, 48));
        this.addSlot(new Slot(top, 3, 98, 48));

        int row;

        for (row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            this.addSlot(new Slot(bottom, row, 8 + row * 18, 142));
        }

    }

    public ItemStack quickMoveStack(Player entityhuman, int i) {
        return this.delegate != null ? this.delegate.quickMoveStack(entityhuman, i) : ItemStack.EMPTY;
    }

    public boolean stillValid(Player entity) {
        return true;
    }

    public MenuType getType() {
        return getNotchInventoryType(this.view.getTopInventory());
    }

    static int[] $SWITCH_TABLE$org$bukkit$event$inventory$InventoryType() {
        int[] aint = CraftContainer.$SWITCH_TABLE$org$bukkit$event$inventory$InventoryType;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[InventoryType.values().length];

            try {
                aint1[InventoryType.ANVIL.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[InventoryType.BARREL.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[InventoryType.BEACON.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[InventoryType.BLAST_FURNACE.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                aint1[InventoryType.BREWING.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                aint1[InventoryType.CARTOGRAPHY.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                aint1[InventoryType.CHEST.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                aint1[InventoryType.CHISELED_BOOKSHELF.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                aint1[InventoryType.COMPOSTER.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                aint1[InventoryType.CRAFTING.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                aint1[InventoryType.CREATIVE.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                aint1[InventoryType.DISPENSER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                aint1[InventoryType.DROPPER.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                aint1[InventoryType.ENCHANTING.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                aint1[InventoryType.ENDER_CHEST.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                aint1[InventoryType.FURNACE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                aint1[InventoryType.GRINDSTONE.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                aint1[InventoryType.HOPPER.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                aint1[InventoryType.JUKEBOX.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                aint1[InventoryType.LECTERN.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                aint1[InventoryType.LOOM.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                aint1[InventoryType.MERCHANT.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                aint1[InventoryType.PLAYER.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                aint1[InventoryType.SHULKER_BOX.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                aint1[InventoryType.SMITHING.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                aint1[InventoryType.SMITHING_NEW.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                aint1[InventoryType.SMOKER.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                aint1[InventoryType.STONECUTTER.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                aint1[InventoryType.WORKBENCH.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            CraftContainer.$SWITCH_TABLE$org$bukkit$event$inventory$InventoryType = aint1;
            return aint1;
        }
    }
}
