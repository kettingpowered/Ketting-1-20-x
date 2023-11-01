package org.bukkit.craftbukkit.v1_20_R2.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.SnbtPrinterTagVisitor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import org.jetbrains.annotations.NotNull;

public class CraftNBTTagConfigSerializer {

    private static final Pattern ARRAY = Pattern.compile("^\\[.*]");
    private static final Pattern INTEGER = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)?i", 2);
    private static final Pattern DOUBLE = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
    private static final TagParser MOJANGSON_PARSER = new TagParser(new StringReader(""));

    public static String serialize(@NotNull Tag base) {
        SnbtPrinterTagVisitor snbtVisitor = new SnbtPrinterTagVisitor();

        return snbtVisitor.visit(base);
    }

    public static Tag deserialize(Object object) {
        String snbtString;

        if (object instanceof String && (snbtString = (String) object) == (String) object) {
            try {
                return TagParser.parseTag(snbtString);
            } catch (CommandSyntaxException commandsyntaxexception) {
                throw new RuntimeException("Failed to deserialise nbt", commandsyntaxexception);
            }
        } else {
            return internalLegacyDeserialization(object);
        }
    }

    private static Tag internalLegacyDeserialization(@NotNull Object object) {
        if (object instanceof Map) {
            CompoundTag compound = new CompoundTag();
            Iterator iterator = ((Map) object).entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                compound.put((String) entry.getKey(), internalLegacyDeserialization(entry.getValue()));
            }

            return compound;
        } else if (!(object instanceof List)) {
            if (object instanceof String) {
                String string = (String) object;

                if (CraftNBTTagConfigSerializer.ARRAY.matcher(string).matches()) {
                    try {
                        return (new TagParser(new StringReader(string))).readArrayTag();
                    } catch (CommandSyntaxException commandsyntaxexception) {
                        throw new RuntimeException("Could not deserialize found list ", commandsyntaxexception);
                    }
                } else if (CraftNBTTagConfigSerializer.INTEGER.matcher(string).matches()) {
                    return IntTag.valueOf(Integer.parseInt(string.substring(0, string.length() - 1)));
                } else if (CraftNBTTagConfigSerializer.DOUBLE.matcher(string).matches()) {
                    return DoubleTag.valueOf(Double.parseDouble(string.substring(0, string.length() - 1)));
                } else {
                    Tag nbtBase = CraftNBTTagConfigSerializer.MOJANGSON_PARSER.type(string);

                    return (Tag) (nbtBase instanceof IntTag ? StringTag.valueOf(nbtBase.getAsString()) : (nbtBase instanceof DoubleTag ? StringTag.valueOf(String.valueOf(((DoubleTag) nbtBase).getAsDouble())) : nbtBase));
                }
            } else {
                throw new RuntimeException("Could not deserialize NBTBase");
            }
        } else {
            List list = (List) object;

            if (list.isEmpty()) {
                return new ListTag();
            } else {
                ListTag tagList = new ListTag();
                Iterator iterator1 = list.iterator();

                while (iterator1.hasNext()) {
                    Object tag = iterator1.next();

                    tagList.add(internalLegacyDeserialization(tag));
                }

                return tagList;
            }
        }
    }
}
