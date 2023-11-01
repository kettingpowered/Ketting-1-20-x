package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import java.util.Random;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.FireworkMeta;

public class CraftFirework extends CraftProjectile implements Firework {

    private final Random random = new Random();
    private final CraftItemStack item;

    public CraftFirework(CraftServer server, FireworkRocketEntity entity) {
        super(server, (Projectile) entity);
        ItemStack item = (ItemStack) this.getHandle().getEntityData().get(FireworkRocketEntity.DATA_ID_FIREWORKS_ITEM);

        if (item.isEmpty()) {
            item = new ItemStack(Items.FIREWORK_ROCKET);
            this.getHandle().getEntityData().set(FireworkRocketEntity.DATA_ID_FIREWORKS_ITEM, item);
        }

        this.item = CraftItemStack.asCraftMirror(item);
        if (this.item.getType() != Material.FIREWORK_ROCKET) {
            this.item.setType(Material.FIREWORK_ROCKET);
        }

    }

    public FireworkRocketEntity getHandle() {
        return (FireworkRocketEntity) this.entity;
    }

    public String toString() {
        return "CraftFirework";
    }

    public FireworkMeta getFireworkMeta() {
        return (FireworkMeta) this.item.getItemMeta();
    }

    public void setFireworkMeta(FireworkMeta meta) {
        this.item.setItemMeta(meta);
        this.getHandle().lifetime = 10 * (1 + meta.getPower()) + this.random.nextInt(6) + this.random.nextInt(7);
        this.getHandle().getEntityData().markDirty(FireworkRocketEntity.DATA_ID_FIREWORKS_ITEM);
    }

    public boolean setAttachedTo(LivingEntity entity) {
        if (this.isDetonated()) {
            return false;
        } else {
            this.getHandle().attachedToEntity = entity != null ? ((CraftLivingEntity) entity).getHandle() : null;
            return true;
        }
    }

    public LivingEntity getAttachedTo() {
        net.minecraft.world.entity.LivingEntity entity = this.getHandle().attachedToEntity;

        return entity != null ? (LivingEntity) entity.getBukkitEntity() : null;
    }

    public boolean setLife(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks must be greater than or equal to 0");
        if (this.isDetonated()) {
            return false;
        } else {
            this.getHandle().life = ticks;
            return true;
        }
    }

    public int getLife() {
        return this.getHandle().life;
    }

    public boolean setMaxLife(int ticks) {
        Preconditions.checkArgument(ticks > 0, "ticks must be greater than 0");
        if (this.isDetonated()) {
            return false;
        } else {
            this.getHandle().lifetime = ticks;
            return true;
        }
    }

    public int getMaxLife() {
        return this.getHandle().lifetime;
    }

    public void detonate() {
        this.setLife(this.getMaxLife() + 1);
    }

    public boolean isDetonated() {
        return this.getHandle().life > this.getHandle().lifetime;
    }

    public boolean isShotAtAngle() {
        return this.getHandle().isShotAtAngle();
    }

    public void setShotAtAngle(boolean shotAtAngle) {
        this.getHandle().getEntityData().set(FireworkRocketEntity.DATA_SHOT_AT_ANGLE, shotAtAngle);
    }
}
