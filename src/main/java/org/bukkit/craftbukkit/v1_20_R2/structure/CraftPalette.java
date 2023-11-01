package org.bukkit.craftbukkit.v1_20_R2.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlockStates;
import org.bukkit.structure.Palette;

public class CraftPalette implements Palette {

    private final StructureTemplate.Palette palette;

    public CraftPalette(StructureTemplate.Palette palette) {
        this.palette = palette;
    }

    public List getBlocks() {
        ArrayList blocks = new ArrayList();
        Iterator iterator = this.palette.blocks().iterator();

        while (iterator.hasNext()) {
            StructureTemplate.StructureBlockInfo blockInfo = (StructureTemplate.StructureBlockInfo) iterator.next();

            blocks.add(CraftBlockStates.getBlockState(blockInfo.pos(), blockInfo.state(), blockInfo.nbt()));
        }

        return blocks;
    }

    public int getBlockCount() {
        return this.palette.blocks().size();
    }
}
