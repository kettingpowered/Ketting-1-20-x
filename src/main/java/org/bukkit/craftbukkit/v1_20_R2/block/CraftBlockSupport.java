package org.bukkit.craftbukkit.v1_20_R2.block;

import net.minecraft.world.level.block.SupportType;
import org.bukkit.block.BlockSupport;

public final class CraftBlockSupport {

    private static volatile int[] $SWITCH_TABLE$net$minecraft$world$level$block$EnumBlockSupport;
    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$BlockSupport;

    private CraftBlockSupport() {}

    public static BlockSupport toBukkit(SupportType support) {
        BlockSupport blocksupport;

        switch ($SWITCH_TABLE$net$minecraft$world$level$block$EnumBlockSupport()[support.ordinal()]) {
            case 1:
                blocksupport = BlockSupport.FULL;
                break;
            case 2:
                blocksupport = BlockSupport.CENTER;
                break;
            case 3:
                blocksupport = BlockSupport.RIGID;
                break;
            default:
                throw new IllegalArgumentException("Unsupported EnumBlockSupport type: " + support + ". This is a bug.");
        }

        return blocksupport;
    }

    public static SupportType toNMS(BlockSupport support) {
        SupportType supporttype;

        switch ($SWITCH_TABLE$org$bukkit$block$BlockSupport()[support.ordinal()]) {
            case 1:
                supporttype = SupportType.FULL;
                break;
            case 2:
                supporttype = SupportType.CENTER;
                break;
            case 3:
                supporttype = SupportType.RIGID;
                break;
            default:
                throw new IllegalArgumentException("Unsupported BlockSupport type: " + support + ". This is a bug.");
        }

        return supporttype;
    }

    static int[] $SWITCH_TABLE$net$minecraft$world$level$block$EnumBlockSupport() {
        int[] aint = CraftBlockSupport.$SWITCH_TABLE$net$minecraft$world$level$block$EnumBlockSupport;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[SupportType.values().length];

            try {
                aint1[SupportType.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[SupportType.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[SupportType.RIGID.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftBlockSupport.$SWITCH_TABLE$net$minecraft$world$level$block$EnumBlockSupport = aint1;
            return aint1;
        }
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$BlockSupport() {
        int[] aint = CraftBlockSupport.$SWITCH_TABLE$org$bukkit$block$BlockSupport;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[BlockSupport.values().length];

            try {
                aint1[BlockSupport.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[BlockSupport.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[BlockSupport.RIGID.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftBlockSupport.$SWITCH_TABLE$org$bukkit$block$BlockSupport = aint1;
            return aint1;
        }
    }
}
