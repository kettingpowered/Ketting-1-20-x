package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.DecoratedPot;
import org.bukkit.block.DecoratedPot.Side;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;

public class CraftDecoratedPot extends CraftBlockEntityState implements DecoratedPot {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$DecoratedPot$Side;

    public CraftDecoratedPot(World world, DecoratedPotBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftDecoratedPot(CraftDecoratedPot state) {
        super((CraftBlockEntityState) state);
    }

    public void setSherd(Side face, Material sherd) {
        Preconditions.checkArgument(face != null, "face must not be null");
        Preconditions.checkArgument(sherd == null || sherd == Material.BRICK || Tag.ITEMS_DECORATED_POT_SHERDS.isTagged(sherd), "sherd is not a valid sherd material: %s", sherd);
        Item sherdItem = sherd != null ? CraftMagicNumbers.getItem(sherd) : Items.BRICK;
        DecoratedPotBlockEntity.Decorations decorations = ((DecoratedPotBlockEntity) this.getSnapshot()).getDecorations();

        switch ($SWITCH_TABLE$org$bukkit$block$DecoratedPot$Side()[face.ordinal()]) {
            case 1:
                ((DecoratedPotBlockEntity) this.getSnapshot()).decorations = new DecoratedPotBlockEntity.Decorations(sherdItem, decorations.left(), decorations.right(), decorations.front());
                break;
            case 2:
                ((DecoratedPotBlockEntity) this.getSnapshot()).decorations = new DecoratedPotBlockEntity.Decorations(decorations.back(), sherdItem, decorations.right(), decorations.front());
                break;
            case 3:
                ((DecoratedPotBlockEntity) this.getSnapshot()).decorations = new DecoratedPotBlockEntity.Decorations(decorations.back(), decorations.left(), sherdItem, decorations.front());
                break;
            case 4:
                ((DecoratedPotBlockEntity) this.getSnapshot()).decorations = new DecoratedPotBlockEntity.Decorations(decorations.back(), decorations.left(), decorations.right(), sherdItem);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + face);
        }

    }

    public Material getSherd(Side face) {
        Preconditions.checkArgument(face != null, "face must not be null");
        DecoratedPotBlockEntity.Decorations decorations = ((DecoratedPotBlockEntity) this.getSnapshot()).getDecorations();
        Item item;

        switch ($SWITCH_TABLE$org$bukkit$block$DecoratedPot$Side()[face.ordinal()]) {
            case 1:
                item = decorations.back();
                break;
            case 2:
                item = decorations.left();
                break;
            case 3:
                item = decorations.right();
                break;
            case 4:
                item = decorations.front();
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + face);
        }

        Item sherdItem = item;

        return CraftMagicNumbers.getMaterial(sherdItem);
    }

    public Map getSherds() {
        DecoratedPotBlockEntity.Decorations decorations = ((DecoratedPotBlockEntity) this.getSnapshot()).getDecorations();
        EnumMap sherds = new EnumMap(Side.class);

        sherds.put(Side.BACK, CraftMagicNumbers.getMaterial(decorations.back()));
        sherds.put(Side.LEFT, CraftMagicNumbers.getMaterial(decorations.left()));
        sherds.put(Side.RIGHT, CraftMagicNumbers.getMaterial(decorations.right()));
        sherds.put(Side.FRONT, CraftMagicNumbers.getMaterial(decorations.front()));
        return sherds;
    }

    public List getShards() {
        return (List) ((DecoratedPotBlockEntity) this.getSnapshot()).getDecorations().sorted().map(CraftMagicNumbers::getMaterial).collect(Collectors.toUnmodifiableList());
    }

    public CraftDecoratedPot copy() {
        return new CraftDecoratedPot(this);
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$DecoratedPot$Side() {
        int[] aint = CraftDecoratedPot.$SWITCH_TABLE$org$bukkit$block$DecoratedPot$Side;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Side.values().length];

            try {
                aint1[Side.BACK.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Side.FRONT.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[Side.LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                aint1[Side.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            CraftDecoratedPot.$SWITCH_TABLE$org$bukkit$block$DecoratedPot$Side = aint1;
            return aint1;
        }
    }
}
