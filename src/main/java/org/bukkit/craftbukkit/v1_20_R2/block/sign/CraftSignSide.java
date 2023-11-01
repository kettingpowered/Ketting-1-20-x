package org.bukkit.craftbukkit.v1_20_R2.block.sign;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SignText;
import org.bukkit.DyeColor;
import org.bukkit.block.sign.SignSide;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftSign;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CraftSignSide implements SignSide {

    private String[] originalLines = null;
    private String[] lines = null;
    private SignText signText;

    public CraftSignSide(SignText signText) {
        this.signText = signText;
    }

    @NotNull
    public String[] getLines() {
        if (this.lines == null) {
            Component[] messages = this.signText.getMessages(false);

            this.lines = new String[messages.length];
            System.arraycopy(CraftSign.revertComponents(messages), 0, this.lines, 0, this.lines.length);
            this.originalLines = new String[this.lines.length];
            System.arraycopy(this.lines, 0, this.originalLines, 0, this.originalLines.length);
        }

        return this.lines;
    }

    @NotNull
    public String getLine(int index) throws IndexOutOfBoundsException {
        return this.getLines()[index];
    }

    public void setLine(int index, @NotNull String line) throws IndexOutOfBoundsException {
        this.getLines()[index] = line;
    }

    public boolean isGlowingText() {
        return this.signText.hasGlowingText();
    }

    public void setGlowingText(boolean glowing) {
        this.signText = this.signText.setHasGlowingText(glowing);
    }

    @Nullable
    public DyeColor getColor() {
        return DyeColor.getByWoolData((byte) this.signText.getColor().getId());
    }

    public void setColor(@NotNull DyeColor color) {
        this.signText = this.signText.setColor(net.minecraft.world.item.DyeColor.byId(color.getWoolData()));
    }

    public SignText applyLegacyStringToSignSide() {
        if (this.lines != null) {
            for (int i = 0; i < this.lines.length; ++i) {
                String line = this.lines[i] == null ? "" : this.lines[i];

                if (!line.equals(this.originalLines[i])) {
                    this.signText = this.signText.setMessage(i, CraftChatMessage.fromString(line)[0]);
                }
            }
        }

        return this.signText;
    }
}
