package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Bamboo.Leaves;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftBamboo extends CraftBlockData implements Bamboo {

    private static final EnumProperty LEAVES = getEnum("leaves");

    public Leaves getLeaves() {
        return (Leaves) this.get(CraftBamboo.LEAVES, Leaves.class);
    }

    public void setLeaves(Leaves leaves) {
        this.set(CraftBamboo.LEAVES, (Enum) leaves);
    }
}
