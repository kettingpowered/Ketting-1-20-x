package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Display;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.TextDisplay.TextAlignment;

public class CraftTextDisplay extends CraftDisplay implements TextDisplay {

    private static volatile int[] $SWITCH_TABLE$org$bukkit$entity$TextDisplay$TextAlignment;

    public CraftTextDisplay(CraftServer server, Display.TextDisplay entity) {
        super(server, (Display) entity);
    }

    public Display.TextDisplay getHandle() {
        return (Display.TextDisplay) super.getHandle();
    }

    public String toString() {
        return "CraftTextDisplay";
    }

    public String getText() {
        return CraftChatMessage.fromComponent(this.getHandle().getText());
    }

    public void setText(String text) {
        this.getHandle().setText(CraftChatMessage.fromString(text, true)[0]);
    }

    public int getLineWidth() {
        return this.getHandle().getLineWidth();
    }

    public void setLineWidth(int width) {
        this.getHandle().getEntityData().set(Display.TextDisplay.DATA_LINE_WIDTH_ID, width);
    }

    public Color getBackgroundColor() {
        int color = this.getHandle().getBackgroundColor();

        return color == -1 ? null : Color.fromARGB(color);
    }

    public void setBackgroundColor(Color color) {
        if (color == null) {
            this.getHandle().getEntityData().set(Display.TextDisplay.DATA_BACKGROUND_COLOR_ID, -1);
        } else {
            this.getHandle().getEntityData().set(Display.TextDisplay.DATA_BACKGROUND_COLOR_ID, color.asARGB());
        }

    }

    public byte getTextOpacity() {
        return this.getHandle().getTextOpacity();
    }

    public void setTextOpacity(byte opacity) {
        this.getHandle().setTextOpacity(opacity);
    }

    public boolean isShadowed() {
        return this.getFlag(1);
    }

    public void setShadowed(boolean shadow) {
        this.setFlag(1, shadow);
    }

    public boolean isSeeThrough() {
        return this.getFlag(2);
    }

    public void setSeeThrough(boolean seeThrough) {
        this.setFlag(2, seeThrough);
    }

    public boolean isDefaultBackground() {
        return this.getFlag(4);
    }

    public void setDefaultBackground(boolean defaultBackground) {
        this.setFlag(4, defaultBackground);
    }

    public TextAlignment getAlignment() {
        Display.TextDisplay.Align nms = Display.TextDisplay.getAlign(this.getHandle().getFlags());

        return TextAlignment.valueOf(nms.name());
    }

    public void setAlignment(TextAlignment alignment) {
        Preconditions.checkArgument(alignment != null, "Alignment cannot be null");
        switch ($SWITCH_TABLE$org$bukkit$entity$TextDisplay$TextAlignment()[alignment.ordinal()]) {
            case 1:
                this.setFlag(8, false);
                this.setFlag(16, false);
                break;
            case 2:
                this.setFlag(8, true);
                this.setFlag(16, false);
                break;
            case 3:
                this.setFlag(8, false);
                this.setFlag(16, true);
                break;
            default:
                throw new IllegalArgumentException("Unknown alignment " + alignment);
        }

    }

    private boolean getFlag(int flag) {
        return (this.getHandle().getFlags() & flag) != 0;
    }

    private void setFlag(int flag, boolean set) {
        byte flagBits = this.getHandle().getFlags();

        if (set) {
            flagBits = (byte) (flagBits | flag);
        } else {
            flagBits = (byte) (flagBits & ~flag);
        }

        this.getHandle().setFlags(flagBits);
    }

    static int[] $SWITCH_TABLE$org$bukkit$entity$TextDisplay$TextAlignment() {
        int[] aint = CraftTextDisplay.$SWITCH_TABLE$org$bukkit$entity$TextDisplay$TextAlignment;

        if (aint != null) {
            return aint;
        } else {
            int[] aint1 = new int[TextAlignment.values().length];

            try {
                aint1[TextAlignment.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                aint1[TextAlignment.LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                aint1[TextAlignment.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            CraftTextDisplay.$SWITCH_TABLE$org$bukkit$entity$TextDisplay$TextAlignment = aint1;
            return aint1;
        }
    }
}
