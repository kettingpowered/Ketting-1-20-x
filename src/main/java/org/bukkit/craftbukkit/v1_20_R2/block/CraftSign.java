package org.bukkit.craftbukkit.v1_20_R2.block;

import com.google.common.base.Preconditions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.craftbukkit.v1_20_R2.block.sign.CraftSignSide;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSignOpenEvent.Cause;
import org.jetbrains.annotations.NotNull;

public class CraftSign extends CraftBlockEntityState implements Sign {

    private final CraftSignSide front = new CraftSignSide(((SignBlockEntity) this.getSnapshot()).getFrontText());
    private final CraftSignSide back = new CraftSignSide(((SignBlockEntity) this.getSnapshot()).getBackText());
    private static volatile int[] $SWITCH_TABLE$org$bukkit$block$sign$Side;

    public CraftSign(World world, SignBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftSign(CraftSign state) {
        super((CraftBlockEntityState) state);
    }

    public String[] getLines() {
        return this.front.getLines();
    }

    public String getLine(int index) throws IndexOutOfBoundsException {
        return this.front.getLine(index);
    }

    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        this.front.setLine(index, line);
    }

    public boolean isEditable() {
        return !this.isWaxed();
    }

    public void setEditable(boolean editable) {
        this.setWaxed(!editable);
    }

    public boolean isWaxed() {
        return ((SignBlockEntity) this.getSnapshot()).isWaxed();
    }

    public void setWaxed(boolean waxed) {
        ((SignBlockEntity) this.getSnapshot()).setWaxed(waxed);
    }

    public boolean isGlowingText() {
        return this.front.isGlowingText();
    }

    public void setGlowingText(boolean glowing) {
        this.front.setGlowingText(glowing);
    }

    @NotNull
    public SignSide getSide(Side side) {
        Preconditions.checkArgument(side != null, "side == null");
        switch ($SWITCH_TABLE$org$bukkit$block$sign$Side()[side.ordinal()]) {
            case 1:
                return this.front;
            case 2:
                return this.back;
            default:
                throw new IllegalArgumentException();
        }
    }

    public DyeColor getColor() {
        return this.front.getColor();
    }

    public void setColor(DyeColor color) {
        this.front.setColor(color);
    }

    public void applyTo(SignBlockEntity sign) {
        ((SignBlockEntity) this.getSnapshot()).setText(this.front.applyLegacyStringToSignSide(), true);
        ((SignBlockEntity) this.getSnapshot()).setText(this.back.applyLegacyStringToSignSide(), false);
        super.applyTo(sign);
    }

    public CraftSign copy() {
        return new CraftSign(this);
    }

    public static void openSign(Sign sign, Player player, Side side) {
        Preconditions.checkArgument(sign != null, "sign == null");
        Preconditions.checkArgument(side != null, "side == null");
        Preconditions.checkArgument(sign.isPlaced(), "Sign must be placed");
        Preconditions.checkArgument(sign.getWorld() == player.getWorld(), "Sign must be in same world as Player");
        if (CraftEventFactory.callPlayerSignOpenEvent(player, sign, side, Cause.PLUGIN)) {
            SignBlockEntity handle = (SignBlockEntity) ((CraftSign) sign).getTileEntity();

            handle.setAllowedPlayerEditor(player.getUniqueId());
            ((CraftPlayer) player).getHandle().openTextEdit(handle, Side.FRONT == side);
        }
    }

    public static Component[] sanitizeLines(String[] lines) {
        Component[] components = new Component[4];

        for (int i = 0; i < 4; ++i) {
            if (i < lines.length && lines[i] != null) {
                components[i] = CraftChatMessage.fromString(lines[i])[0];
            } else {
                components[i] = Component.empty();
            }
        }

        return components;
    }

    public static String[] revertComponents(Component[] components) {
        String[] lines = new String[components.length];

        for (int i = 0; i < lines.length; ++i) {
            lines[i] = revertComponent(components[i]);
        }

        return lines;
    }

    private static String revertComponent(Component component) {
        return CraftChatMessage.fromComponent(component);
    }

    static int[] $SWITCH_TABLE$org$bukkit$block$sign$Side() {
        int[] aint = CraftSign.$SWITCH_TABLE$org$bukkit$block$sign$Side;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[Side.values().length];

            try {
                aint1[Side.BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[Side.FRONT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            CraftSign.$SWITCH_TABLE$org$bukkit$block$sign$Side = aint1;
            return aint1;
        }
    }
}
