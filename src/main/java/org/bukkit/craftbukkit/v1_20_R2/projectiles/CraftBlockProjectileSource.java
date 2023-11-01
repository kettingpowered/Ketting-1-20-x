package org.bukkit.craftbukkit.v1_20_R2.projectiles;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.util.Vector;

public class CraftBlockProjectileSource implements BlockProjectileSource {

    private final DispenserBlockEntity dispenserBlock;

    public CraftBlockProjectileSource(DispenserBlockEntity dispenserBlock) {
        this.dispenserBlock = dispenserBlock;
    }

    public Block getBlock() {
        return this.dispenserBlock.getLevel().getWorld().getBlockAt(this.dispenserBlock.getBlockPos().getX(), this.dispenserBlock.getBlockPos().getY(), this.dispenserBlock.getBlockPos().getZ());
    }

    public Projectile launchProjectile(Class projectile) {
        return this.launchProjectile(projectile, (Vector) null);
    }

    public Projectile launchProjectile(Class projectile, Vector velocity) {
        Preconditions.checkArgument(this.getBlock().getType() == Material.DISPENSER, "Block is no longer dispenser");
        BlockSource sourceblock = new BlockSource((ServerLevel) this.dispenserBlock.getLevel(), this.dispenserBlock.getBlockPos(), this.dispenserBlock.getBlockState(), this.dispenserBlock);
        Position iposition = DispenserBlock.getDispensePosition(sourceblock);
        Direction enumdirection = (Direction) sourceblock.state().getValue(DispenserBlock.FACING);
        Level world = this.dispenserBlock.getLevel();
        Object launch = null;

        if (Snowball.class.isAssignableFrom(projectile)) {
            launch = new net.minecraft.world.entity.projectile.Snowball(world, iposition.x(), iposition.y(), iposition.z());
        } else if (Egg.class.isAssignableFrom(projectile)) {
            launch = new ThrownEgg(world, iposition.x(), iposition.y(), iposition.z());
        } else if (EnderPearl.class.isAssignableFrom(projectile)) {
            launch = new ThrownEnderpearl(world, (LivingEntity) null);
            ((Entity) launch).setPos(iposition.x(), iposition.y(), iposition.z());
        } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
            launch = new ThrownExperienceBottle(world, iposition.x(), iposition.y(), iposition.z());
        } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
            if (LingeringPotion.class.isAssignableFrom(projectile)) {
                launch = new net.minecraft.world.entity.projectile.ThrownPotion(world, iposition.x(), iposition.y(), iposition.z());
                ((net.minecraft.world.entity.projectile.ThrownPotion) launch).setItem(CraftItemStack.asNMSCopy(new ItemStack(Material.LINGERING_POTION, 1)));
            } else {
                launch = new net.minecraft.world.entity.projectile.ThrownPotion(world, iposition.x(), iposition.y(), iposition.z());
                ((net.minecraft.world.entity.projectile.ThrownPotion) launch).setItem(CraftItemStack.asNMSCopy(new ItemStack(Material.SPLASH_POTION, 1)));
            }
        } else if (AbstractArrow.class.isAssignableFrom(projectile)) {
            if (TippedArrow.class.isAssignableFrom(projectile)) {
                launch = new Arrow(world, iposition.x(), iposition.y(), iposition.z());
                ((org.bukkit.entity.Arrow) ((Entity) launch).getBukkitEntity()).setBasePotionData(new PotionData(PotionType.WATER, false, false));
            } else if (SpectralArrow.class.isAssignableFrom(projectile)) {
                launch = new net.minecraft.world.entity.projectile.SpectralArrow(world, iposition.x(), iposition.y(), iposition.z());
            } else {
                launch = new Arrow(world, iposition.x(), iposition.y(), iposition.z());
            }

            ((net.minecraft.world.entity.projectile.AbstractArrow) launch).pickup = net.minecraft.world.entity.projectile.AbstractArrow.Pickup.ALLOWED;
            ((net.minecraft.world.entity.projectile.AbstractArrow) launch).projectileSource = this;
        } else if (Fireball.class.isAssignableFrom(projectile)) {
            double d0 = iposition.x() + (double) ((float) enumdirection.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) enumdirection.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) enumdirection.getStepZ() * 0.3F);
            RandomSource random = world.random;
            double d3 = random.nextGaussian() * 0.05D + (double) enumdirection.getStepX();
            double d4 = random.nextGaussian() * 0.05D + (double) enumdirection.getStepY();
            double d5 = random.nextGaussian() * 0.05D + (double) enumdirection.getStepZ();

            if (SmallFireball.class.isAssignableFrom(projectile)) {
                launch = new net.minecraft.world.entity.projectile.SmallFireball(world, (LivingEntity) null, d0, d1, d2);
            } else {
                double d6;

                if (WitherSkull.class.isAssignableFrom(projectile)) {
                    launch = EntityType.WITHER_SKULL.create(world);
                    ((Entity) launch).setPos(d0, d1, d2);
                    d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    ((AbstractHurtingProjectile) launch).xPower = d3 / d6 * 0.1D;
                    ((AbstractHurtingProjectile) launch).yPower = d4 / d6 * 0.1D;
                    ((AbstractHurtingProjectile) launch).zPower = d5 / d6 * 0.1D;
                } else {
                    launch = EntityType.FIREBALL.create(world);
                    ((Entity) launch).setPos(d0, d1, d2);
                    d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    ((AbstractHurtingProjectile) launch).xPower = d3 / d6 * 0.1D;
                    ((AbstractHurtingProjectile) launch).yPower = d4 / d6 * 0.1D;
                    ((AbstractHurtingProjectile) launch).zPower = d5 / d6 * 0.1D;
                }
            }

            ((AbstractHurtingProjectile) launch).projectileSource = this;
        }

        Preconditions.checkArgument(launch != null, "Projectile not supported");
        if (launch instanceof net.minecraft.world.entity.projectile.Projectile) {
            if (launch instanceof ThrowableProjectile) {
                ((ThrowableProjectile) launch).projectileSource = this;
            }

            float a = 6.0F;
            float b = 1.1F;

            if (launch instanceof net.minecraft.world.entity.projectile.ThrownPotion || launch instanceof ThrownExpBottle) {
                a *= 0.5F;
                b *= 1.25F;
            }

            ((net.minecraft.world.entity.projectile.Projectile) launch).shoot((double) enumdirection.getStepX(), (double) ((float) enumdirection.getStepY() + 0.1F), (double) enumdirection.getStepZ(), b, a);
        }

        if (velocity != null) {
            ((Projectile) ((Entity) launch).getBukkitEntity()).setVelocity(velocity);
        }

        world.addFreshEntity((Entity) launch);
        return (Projectile) ((Entity) launch).getBukkitEntity();
    }
}
