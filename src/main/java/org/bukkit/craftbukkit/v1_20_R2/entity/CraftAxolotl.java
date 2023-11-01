package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Axolotl.Variant;

public class CraftAxolotl extends CraftAnimals implements Axolotl {

    public CraftAxolotl(CraftServer server, net.minecraft.world.entity.animal.axolotl.Axolotl entity) {
        super(server, (Animal) entity);
    }

    public net.minecraft.world.entity.animal.axolotl.Axolotl getHandle() {
        return (net.minecraft.world.entity.animal.axolotl.Axolotl) super.getHandle();
    }

    public String toString() {
        return "CraftAxolotl";
    }

    public boolean isPlayingDead() {
        return this.getHandle().isPlayingDead();
    }

    public void setPlayingDead(boolean playingDead) {
        this.getHandle().setPlayingDead(playingDead);
    }

    public Variant getVariant() {
        return Variant.values()[this.getHandle().getVariant().ordinal()];
    }

    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant1 != null, "variant");
        this.getHandle().setVariant(net.minecraft.world.entity.animal.axolotl.Axolotl.Variant.byId(variant2.ordinal()));
    }
}
