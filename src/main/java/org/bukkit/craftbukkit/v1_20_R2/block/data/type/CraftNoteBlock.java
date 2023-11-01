package org.bukkit.craftbukkit.v1_20_R2.block.data.type;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;

public abstract class CraftNoteBlock extends CraftBlockData implements NoteBlock {

    private static final EnumProperty INSTRUMENT = getEnum("instrument");
    private static final IntegerProperty NOTE = getInteger("note");

    public Instrument getInstrument() {
        return (Instrument) this.get(CraftNoteBlock.INSTRUMENT, Instrument.class);
    }

    public void setInstrument(Instrument instrument) {
        this.set(CraftNoteBlock.INSTRUMENT, (Enum) instrument);
    }

    public Note getNote() {
        return new Note((Integer) this.get(CraftNoteBlock.NOTE));
    }

    public void setNote(Note note) {
        this.set((Property) CraftNoteBlock.NOTE, (Comparable) Integer.valueOf(note.getId()));
    }
}
