package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Markings;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftInventoryHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.HorseInventory;

public class CraftHorse extends CraftAbstractHorse implements Horse {

    public CraftHorse(CraftServer server, net.minecraft.world.entity.animal.horse.Horse entity) {
        super(server, (AbstractHorse) entity);
    }

    public net.minecraft.world.entity.animal.horse.Horse getHandle() {
        return (net.minecraft.world.entity.animal.horse.Horse) super.getHandle();
    }

    public Variant getVariant() {
        return Variant.HORSE;
    }

    public Color getColor() {
        return Color.values()[this.getHandle().getVariant().getId()];
    }

    public void setColor(Color color) {
        Preconditions.checkArgument(color != null, "Color cannot be null");
        this.getHandle().setVariantAndMarkings(net.minecraft.world.entity.animal.horse.Variant.byId(color.ordinal()), this.getHandle().getMarkings());
    }

    public Style getStyle() {
        return Style.values()[this.getHandle().getMarkings().getId()];
    }

    public void setStyle(Style style) {
        Preconditions.checkArgument(style != null, "Style cannot be null");
        this.getHandle().setVariantAndMarkings(this.getHandle().getVariant(), Markings.byId(style.ordinal()));
    }

    public boolean isCarryingChest() {
        return false;
    }

    public void setCarryingChest(boolean chest) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public HorseInventory getInventory() {
        return new CraftInventoryHorse(this.getHandle().inventory);
    }

    public String toString() {
        return "CraftHorse{variant=" + this.getVariant() + ", owner=" + this.getOwner() + '}';
    }
}
