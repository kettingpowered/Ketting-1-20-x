package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.CatVariant;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Cat.Type;

public class CraftCat extends CraftTameableAnimal implements Cat {

    public CraftCat(CraftServer server, net.minecraft.world.entity.animal.Cat entity) {
        super(server, (TamableAnimal) entity);
    }

    public net.minecraft.world.entity.animal.Cat getHandle() {
        return (net.minecraft.world.entity.animal.Cat) super.getHandle();
    }

    public String toString() {
        return "CraftCat";
    }

    public Type getCatType() {
        return CraftCat.CraftType.minecraftToBukkit(this.getHandle().getVariant());
    }

    public void setCatType(Type type) {
        Preconditions.checkArgument(type != null, "Cannot have null Type");
        this.getHandle().setVariant(CraftCat.CraftType.bukkitToMinecraft(type));
    }

    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) this.getHandle().getCollarColor().getId());
    }

    public void setCollarColor(DyeColor color) {
        this.getHandle().setCollarColor(net.minecraft.world.item.DyeColor.byId(color.getWoolData()));
    }

    public static class CraftType {

        public static Type minecraftToBukkit(CatVariant minecraft) {
            Preconditions.checkArgument(minecraft != null);
            Registry registry = CraftRegistry.getMinecraftRegistry(Registries.CAT_VARIANT);

            return Type.values()[registry.getId(minecraft)];
        }

        public static CatVariant bukkitToMinecraft(Type bukkit) {
            Preconditions.checkArgument(bukkit != null);
            return (CatVariant) CraftRegistry.getMinecraftRegistry(Registries.CAT_VARIANT).byId(bukkit.ordinal());
        }
    }
}
