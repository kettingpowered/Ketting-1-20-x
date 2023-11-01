package org.bukkit.craftbukkit.v1_20_R2.block.impl;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public final class CraftNote extends CraftBlockData implements NoteBlock, Powerable {

    private static final EnumProperty INSTRUMENT = getEnum(net.minecraft.world.level.block.NoteBlock.class, "instrument");
    private static final IntegerProperty NOTE = getInteger(net.minecraft.world.level.block.NoteBlock.class, "note");
    private static final BooleanProperty POWERED = getBoolean(net.minecraft.world.level.block.NoteBlock.class, "powered");

    public CraftNote() {}

    public CraftNote(BlockState state) {
        super(state);
    }

    public Instrument getInstrument() {
        return (Instrument) this.get(CraftNote.INSTRUMENT, Instrument.class);
    }

    public void setInstrument(Instrument instrument) {
        this.set(CraftNote.INSTRUMENT, (Enum) instrument);
    }

    public Note getNote() {
        return new Note((Integer) this.get(CraftNote.NOTE));
    }

    public void setNote(Note note) {
        this.set((Property) CraftNote.NOTE, (Comparable) Integer.valueOf(note.getId()));
    }

    public boolean isPowered() {
        return (Boolean) this.get(CraftNote.POWERED);
    }

    public void setPowered(boolean powered) {
        this.set((Property) CraftNote.POWERED, (Comparable) powered);
    }
}
