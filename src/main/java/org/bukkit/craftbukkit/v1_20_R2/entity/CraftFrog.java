package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FrogVariant;
import org.bukkit.craftbukkit.v1_20_R2.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Frog.Variant;

public class CraftFrog extends CraftAnimals implements Frog {

    public CraftFrog(CraftServer server, net.minecraft.world.entity.animal.frog.Frog entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.frog.Frog getHandle() {
        return (net.minecraft.world.entity.animal.frog.Frog) this.entity;
    }

    public String toString() {
        return "CraftFrog";
    }

    public Entity getTongueTarget() {
        return (Entity) this.getHandle().getTongueTarget().map(net.minecraft.world.entity.Entity::getBukkitEntity).orElse((Object) null);
    }

    public void setTongueTarget(Entity target) {
        if (target == null) {
            this.getHandle().eraseTongueTarget();
        } else {
            this.getHandle().setTongueTarget(((CraftEntity) target).getHandle());
        }

    }

    public Variant getVariant() {
        return CraftFrog.CraftVariant.minecraftToBukkit(this.getHandle().getVariant());
    }

    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant1 != null, "variant");
        this.getHandle().setVariant(CraftFrog.CraftVariant.bukkitToMinecraft(variant2));
    }

    public static class CraftVariant {

        public static Variant minecraftToBukkit(FrogVariant minecraft) {
            Preconditions.checkArgument(minecraft != null);
            Registry registry = CraftRegistry.getMinecraftRegistry(Registries.FROG_VARIANT);
            Variant bukkit = (Variant) org.bukkit.Registry.FROG_VARIANT.get(CraftNamespacedKey.fromMinecraft(((ResourceKey) registry.getResourceKey(minecraft).orElseThrow()).location()));

            Preconditions.checkArgument(bukkit != null);
            return bukkit;
        }

        public static FrogVariant bukkitToMinecraft(Variant bukkit) {
            Preconditions.checkArgument(bukkit != null);
            return (FrogVariant) CraftRegistry.getMinecraftRegistry(Registries.FROG_VARIANT).getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
        }
    }
}
