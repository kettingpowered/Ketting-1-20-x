package org.bukkit.craftbukkit.v1_20_R2.persistence;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Primitives;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import org.bukkit.persistence.PersistentDataContainer;

public final class CraftPersistentDataTypeRegistry {

    private final Function CREATE_ADAPTER = this::createAdapter;
    private final Map adapters = new HashMap();

    private CraftPersistentDataTypeRegistry.TagAdapter createAdapter(Class type) {
        if (!Primitives.isWrapperType(type)) {
            type = Primitives.wrap(type);
        }

        if (Objects.equals(Byte.class, type)) {
            return this.createAdapter(Byte.class, ByteTag.class, ByteTag::valueOf, ByteTag::getAsByte);
        } else if (Objects.equals(Short.class, type)) {
            return this.createAdapter(Short.class, ShortTag.class, ShortTag::valueOf, ShortTag::getAsShort);
        } else if (Objects.equals(Integer.class, type)) {
            return this.createAdapter(Integer.class, IntTag.class, IntTag::valueOf, IntTag::getAsInt);
        } else if (Objects.equals(Long.class, type)) {
            return this.createAdapter(Long.class, LongTag.class, LongTag::valueOf, LongTag::getAsLong);
        } else if (Objects.equals(Float.class, type)) {
            return this.createAdapter(Float.class, FloatTag.class, FloatTag::valueOf, FloatTag::getAsFloat);
        } else if (Objects.equals(Double.class, type)) {
            return this.createAdapter(Double.class, DoubleTag.class, DoubleTag::valueOf, DoubleTag::getAsDouble);
        } else if (Objects.equals(String.class, type)) {
            return this.createAdapter(String.class, StringTag.class, StringTag::valueOf, StringTag::getAsString);
        } else if (Objects.equals(byte[].class, type)) {
            return this.createAdapter(byte[].class, ByteArrayTag.class, (arrayx) -> {
                return new ByteArrayTag(Arrays.copyOf(arrayx, arrayx.length));
            }, (nx) -> {
                return Arrays.copyOf(nx.getAsByteArray(), nx.size());
            });
        } else if (Objects.equals(int[].class, type)) {
            return this.createAdapter(int[].class, IntArrayTag.class, (arrayx) -> {
                return new IntArrayTag(Arrays.copyOf(arrayx, arrayx.length));
            }, (nx) -> {
                return Arrays.copyOf(nx.getAsIntArray(), nx.size());
            });
        } else if (Objects.equals(long[].class, type)) {
            return this.createAdapter(long[].class, LongArrayTag.class, (arrayx) -> {
                return new LongArrayTag(Arrays.copyOf(arrayx, arrayx.length));
            }, (nx) -> {
                return Arrays.copyOf(nx.getAsLongArray(), nx.size());
            });
        } else if (Objects.equals(PersistentDataContainer[].class, type)) {
            return this.createAdapter(PersistentDataContainer[].class, ListTag.class, (containerArrayx) -> {
                ListTag list = new ListTag();

                for (int i = 0; i < containerArrayx.length; ++i) {
                    list.add(((CraftPersistentDataContainer) containerArrayx[i]).toTagCompound());
                }

                return list;
            }, (tagx) -> {
                CraftPersistentDataContainer[] containerArray = new CraftPersistentDataContainer[tagx.size()];

                for (int i = 0; i < tagx.size(); ++i) {
                    CraftPersistentDataContainer container = new CraftPersistentDataContainer(this);
                    CompoundTag compound = tagx.getCompound(i);
                    Iterator iterator = compound.getAllKeys().iterator();

                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();

                        container.put(key, compound.get(key));
                    }

                    containerArray[i] = container;
                }

                return containerArray;
            });
        } else if (Objects.equals(PersistentDataContainer.class, type)) {
            return this.createAdapter(CraftPersistentDataContainer.class, CompoundTag.class, CraftPersistentDataContainer::toTagCompound, (tagx) -> {
                CraftPersistentDataContainer container = new CraftPersistentDataContainer(this);
                Iterator iterator = tagx.getAllKeys().iterator();

                while (iterator.hasNext()) {
                    String key = (String) iterator.next();

                    container.put(key, tagx.get(key));
                }

                return container;
            });
        } else {
            throw new IllegalArgumentException("Could not find a valid TagAdapter implementation for the requested type " + type.getSimpleName());
        }
    }

    private CraftPersistentDataTypeRegistry.TagAdapter createAdapter(Class primitiveType, Class nbtBaseType, Function builder, Function extractor) {
        return new CraftPersistentDataTypeRegistry.TagAdapter(primitiveType, nbtBaseType, builder, extractor);
    }

    public Tag wrap(Class type, Object value) {
        return ((CraftPersistentDataTypeRegistry.TagAdapter) this.adapters.computeIfAbsent(type, this.CREATE_ADAPTER)).build(value);
    }

    public boolean isInstanceOf(Class type, Tag base) {
        return ((CraftPersistentDataTypeRegistry.TagAdapter) this.adapters.computeIfAbsent(type, this.CREATE_ADAPTER)).isInstance(base);
    }

    public Object extract(Class type, Tag tag) throws ClassCastException, IllegalArgumentException {
        CraftPersistentDataTypeRegistry.TagAdapter adapter = (CraftPersistentDataTypeRegistry.TagAdapter) this.adapters.computeIfAbsent(type, this.CREATE_ADAPTER);

        Preconditions.checkArgument(adapter.isInstance(tag), "The found tag instance (%s) cannot store %s", tag.getClass().getSimpleName(), type.getSimpleName());
        Object foundValue = adapter.extract(tag);

        Preconditions.checkArgument(type.isInstance(foundValue), "The found object is of the type %s. Expected type %s", foundValue.getClass().getSimpleName(), type.getSimpleName());
        return type.cast(foundValue);
    }

    private class TagAdapter {

        private final Function builder;
        private final Function extractor;
        private final Class primitiveType;
        private final Class nbtBaseType;

        public TagAdapter(Class primitiveType, Class nbtBaseType, Function builder, Function extractor) {
            this.primitiveType = primitiveType;
            this.nbtBaseType = nbtBaseType;
            this.builder = builder;
            this.extractor = extractor;
        }

        Object extract(Tag base) {
            Preconditions.checkArgument(this.nbtBaseType.isInstance(base), "The provided NBTBase was of the type %s. Expected type %s", base.getClass().getSimpleName(), this.nbtBaseType.getSimpleName());
            return this.extractor.apply((Tag) this.nbtBaseType.cast(base));
        }

        Tag build(Object value) {
            Preconditions.checkArgument(this.primitiveType.isInstance(value), "The provided value was of the type %s. Expected type %s", value.getClass().getSimpleName(), this.primitiveType.getSimpleName());
            return (Tag) this.builder.apply(this.primitiveType.cast(value));
        }

        boolean isInstance(Tag base) {
            return this.nbtBaseType.isInstance(base);
        }
    }
}
