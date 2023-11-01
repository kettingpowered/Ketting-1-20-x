package org.bukkit.craftbukkit.v1_20_R2.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import org.bukkit.inventory.meta.BookMeta.Spigot;
import org.spigotmc.ValidateUtils;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaBook extends CraftMetaItem implements BookMeta {

    static final CraftMetaItem.ItemMetaKey BOOK_TITLE = new CraftMetaItem.ItemMetaKey("title");
    static final CraftMetaItem.ItemMetaKey BOOK_AUTHOR = new CraftMetaItem.ItemMetaKey("author");
    static final CraftMetaItem.ItemMetaKey BOOK_PAGES = new CraftMetaItem.ItemMetaKey("pages");
    static final CraftMetaItem.ItemMetaKey RESOLVED = new CraftMetaItem.ItemMetaKey("resolved");
    static final CraftMetaItem.ItemMetaKey GENERATION = new CraftMetaItem.ItemMetaKey("generation");
    static final int MAX_PAGES = 100;
    static final int MAX_PAGE_LENGTH = 320;
    static final int MAX_TITLE_LENGTH = 32;
    protected String title;
    protected String author;
    protected List pages;
    protected Boolean resolved = null;
    protected Integer generation;
    private Spigot spigot = new CraftMetaBook.SpigotMeta();

    CraftMetaBook(CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaBook) {
            CraftMetaBook bookMeta = (CraftMetaBook) meta;

            this.title = bookMeta.title;
            this.author = bookMeta.author;
            this.resolved = bookMeta.resolved;
            this.generation = bookMeta.generation;
            if (bookMeta.pages != null) {
                this.pages = new ArrayList(bookMeta.pages.size());
                if (meta instanceof CraftMetaBookSigned) {
                    if (this instanceof CraftMetaBookSigned) {
                        this.pages.addAll(bookMeta.pages);
                    } else {
                        this.pages.addAll(Lists.transform(bookMeta.pages, CraftChatMessage::fromJSONComponent));
                    }
                } else if (this instanceof CraftMetaBookSigned) {
                    Iterator iterator = bookMeta.pages.iterator();

                    while (iterator.hasNext()) {
                        String page = (String) iterator.next();
                        Component component = CraftChatMessage.fromString(page, true, true)[0];

                        this.pages.add(CraftChatMessage.toJSON(component));
                    }
                } else {
                    this.pages.addAll(bookMeta.pages);
                }
            }
        }

    }

    CraftMetaBook(CompoundTag tag) {
        super(tag);
        if (tag.contains(CraftMetaBook.BOOK_TITLE.NBT)) {
            this.title = ValidateUtils.limit(tag.getString(CraftMetaBook.BOOK_TITLE.NBT), 8192);
        }

        if (tag.contains(CraftMetaBook.BOOK_AUTHOR.NBT)) {
            this.author = ValidateUtils.limit(tag.getString(CraftMetaBook.BOOK_AUTHOR.NBT), 8192);
        }

        if (tag.contains(CraftMetaBook.RESOLVED.NBT)) {
            this.resolved = tag.getBoolean(CraftMetaBook.RESOLVED.NBT);
        }

        if (tag.contains(CraftMetaBook.GENERATION.NBT)) {
            this.generation = tag.getInt(CraftMetaBook.GENERATION.NBT);
        }

        if (tag.contains(CraftMetaBook.BOOK_PAGES.NBT)) {
            ListTag pages = tag.getList(CraftMetaBook.BOOK_PAGES.NBT, 8);

            this.pages = new ArrayList(pages.size());
            boolean expectJson = this instanceof CraftMetaBookSigned;

            for (int i = 0; i < Math.min(pages.size(), 100); ++i) {
                String page = pages.getString(i);

                if (expectJson) {
                    page = CraftChatMessage.fromJSONOrStringToJSON(page, false, true, 320, false);
                } else {
                    page = this.validatePage(page);
                }

                this.pages.add(ValidateUtils.limit(page, 16384));
            }
        }

    }

    CraftMetaBook(Map map) {
        super(map);
        this.setAuthor(CraftMetaItem.SerializableMeta.getString(map, CraftMetaBook.BOOK_AUTHOR.BUKKIT, true));
        this.setTitle(CraftMetaItem.SerializableMeta.getString(map, CraftMetaBook.BOOK_TITLE.BUKKIT, true));
        Iterable pages = (Iterable) CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, CraftMetaBook.BOOK_PAGES.BUKKIT, true);

        if (pages != null) {
            this.pages = new ArrayList();
            Iterator iterator = pages.iterator();

            while (iterator.hasNext()) {
                Object page = iterator.next();

                if (page instanceof String) {
                    this.internalAddPage(this.deserializePage((String) page));
                }
            }
        }

        this.resolved = (Boolean) CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, CraftMetaBook.RESOLVED.BUKKIT, true);
        this.generation = (Integer) CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CraftMetaBook.GENERATION.BUKKIT, true);
    }

    protected String deserializePage(String pageData) {
        return this.validatePage(pageData);
    }

    protected String convertPlainPageToData(String page) {
        return page;
    }

    protected String convertDataToPlainPage(String pageData) {
        return pageData;
    }

    void applyToItem(CompoundTag itemData) {
        super.applyToItem(itemData);
        if (this.hasTitle()) {
            itemData.putString(CraftMetaBook.BOOK_TITLE.NBT, this.title);
        }

        if (this.hasAuthor()) {
            itemData.putString(CraftMetaBook.BOOK_AUTHOR.NBT, this.author);
        }

        if (this.pages != null) {
            ListTag list = new ListTag();
            Iterator iterator = this.pages.iterator();

            while (iterator.hasNext()) {
                String page = (String) iterator.next();

                list.add(StringTag.valueOf(page));
            }

            itemData.put(CraftMetaBook.BOOK_PAGES.NBT, list);
        }

        if (this.resolved != null) {
            itemData.putBoolean(CraftMetaBook.RESOLVED.NBT, this.resolved);
        }

        if (this.generation != null) {
            itemData.putInt(CraftMetaBook.GENERATION.NBT, this.generation);
        }

    }

    boolean isEmpty() {
        return super.isEmpty() && this.isBookEmpty();
    }

    boolean isBookEmpty() {
        return this.pages == null && !this.hasAuthor() && !this.hasTitle() && !this.hasGeneration() && this.resolved == null;
    }

    boolean applicableTo(Material type) {
        return type == Material.WRITTEN_BOOK || type == Material.WRITABLE_BOOK;
    }

    public boolean hasAuthor() {
        return this.author != null;
    }

    public boolean hasTitle() {
        return this.title != null;
    }

    public boolean hasPages() {
        return this.pages != null && !this.pages.isEmpty();
    }

    public boolean hasGeneration() {
        return this.generation != null;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean setTitle(String title) {
        if (title == null) {
            this.title = null;
            return true;
        } else if (title.length() > 32) {
            return false;
        } else {
            this.title = title;
            return true;
        }
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Generation getGeneration() {
        return this.generation == null ? null : Generation.values()[this.generation];
    }

    public void setGeneration(Generation generation) {
        this.generation = generation == null ? null : generation.ordinal();
    }

    public String getPage(int page) {
        Preconditions.checkArgument(this.isValidPage(page), "Invalid page number (%s)", page);
        return this.convertDataToPlainPage((String) this.pages.get(page - 1));
    }

    public void setPage(int page, String text) {
        Preconditions.checkArgument(this.isValidPage(page), "Invalid page number (%s/%s)", page, this.getPageCount());
        String newText = this.validatePage(text);

        this.pages.set(page - 1, this.convertPlainPageToData(newText));
    }

    public void setPages(String... pages) {
        this.setPages(Arrays.asList(pages));
    }

    public void addPage(String... pages) {
        String[] astring = pages;
        int i = pages.length;

        for (int j = 0; j < i; ++j) {
            String page = astring[j];

            page = this.validatePage(page);
            this.internalAddPage(this.convertPlainPageToData(page));
        }

    }

    String validatePage(String page) {
        if (page == null) {
            page = "";
        } else if (page.length() > 320) {
            page = page.substring(0, 320);
        }

        return page;
    }

    private void internalAddPage(String page) {
        if (this.pages == null) {
            this.pages = new ArrayList();
        } else if (this.pages.size() >= 100) {
            return;
        }

        this.pages.add(page);
    }

    public int getPageCount() {
        return this.pages == null ? 0 : this.pages.size();
    }

    public List getPages() {
        return (List) (this.pages == null ? ImmutableList.of() : (List) this.pages.stream().map(this::convertDataToPlainPage).collect(ImmutableList.toImmutableList()));
    }

    public void setPages(List pages) {
        if (pages.isEmpty()) {
            this.pages = null;
        } else {
            if (this.pages != null) {
                this.pages.clear();
            }

            Iterator iterator = pages.iterator();

            while (iterator.hasNext()) {
                String page = (String) iterator.next();

                this.addPage(page);
            }

        }
    }

    private boolean isValidPage(int page) {
        return page > 0 && page <= this.getPageCount();
    }

    public boolean isResolved() {
        return this.resolved == null ? false : this.resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public CraftMetaBook clone() {
        CraftMetaBook meta = (CraftMetaBook) super.clone();

        if (this.pages != null) {
            meta.pages = new ArrayList(this.pages);
        }

        meta.spigot = meta.new SpigotMeta();
        return meta;
    }

    int applyHash() {
        int original;
        int hash = original = super.applyHash();

        if (this.hasTitle()) {
            hash = 61 * hash + this.title.hashCode();
        }

        if (this.hasAuthor()) {
            hash = 61 * hash + 13 * this.author.hashCode();
        }

        if (this.pages != null) {
            hash = 61 * hash + 17 * this.pages.hashCode();
        }

        if (this.resolved != null) {
            hash = 61 * hash + 17 * this.resolved.hashCode();
        }

        if (this.hasGeneration()) {
            hash = 61 * hash + 19 * this.generation.hashCode();
        }

        return original != hash ? CraftMetaBook.class.hashCode() ^ hash : hash;
    }

    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        } else {
            CraftMetaBook that;

            if (meta instanceof CraftMetaBook && (that = (CraftMetaBook) meta) == (CraftMetaBook) meta) {
                if (this.hasTitle()) {
                    if (!that.hasTitle() || !this.title.equals(that.title)) {
                        return false;
                    }
                } else if (that.hasTitle()) {
                    return false;
                }

                if (this.hasAuthor()) {
                    if (!that.hasAuthor() || !this.author.equals(that.author)) {
                        return false;
                    }
                } else if (that.hasAuthor()) {
                    return false;
                }

                if (Objects.equals(this.pages, that.pages) && Objects.equals(this.resolved, that.resolved)) {
                    if (this.hasGeneration()) {
                        if (!that.hasGeneration() || !this.generation.equals(that.generation)) {
                            return false;
                        }
                    } else if (that.hasGeneration()) {
                        return false;
                    }

                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaBook || this.isBookEmpty());
    }

    Builder serialize(Builder builder) {
        super.serialize(builder);
        if (this.hasTitle()) {
            builder.put(CraftMetaBook.BOOK_TITLE.BUKKIT, this.title);
        }

        if (this.hasAuthor()) {
            builder.put(CraftMetaBook.BOOK_AUTHOR.BUKKIT, this.author);
        }

        if (this.pages != null) {
            builder.put(CraftMetaBook.BOOK_PAGES.BUKKIT, ImmutableList.copyOf(this.pages));
        }

        if (this.resolved != null) {
            builder.put(CraftMetaBook.RESOLVED.BUKKIT, this.resolved);
        }

        if (this.generation != null) {
            builder.put(CraftMetaBook.GENERATION.BUKKIT, this.generation);
        }

        return builder;
    }

    public Spigot spigot() {
        return this.spigot;
    }

    private class SpigotMeta extends Spigot {

        private String pageToJSON(String page) {
            if (CraftMetaBook.this instanceof CraftMetaBookSigned) {
                return page;
            } else {
                Component component = CraftChatMessage.fromString(page, true, true)[0];

                return CraftChatMessage.toJSON(component);
            }
        }

        private String componentsToPage(BaseComponent[] components) {
            return CraftMetaBook.this instanceof CraftMetaBookSigned ? ComponentSerializer.toString(components) : CraftChatMessage.fromJSONComponent(ComponentSerializer.toString(components));
        }

        public BaseComponent[] getPage(int page) {
            Preconditions.checkArgument(CraftMetaBook.this.isValidPage(page), "Invalid page number");
            return ComponentSerializer.parse(this.pageToJSON((String) CraftMetaBook.this.pages.get(page - 1)));
        }

        public void setPage(int page, BaseComponent... text) {
            if (!CraftMetaBook.this.isValidPage(page)) {
                throw new IllegalArgumentException("Invalid page number " + page + "/" + CraftMetaBook.this.getPageCount());
            } else {
                BaseComponent[] newText = text == null ? new BaseComponent[0] : text;

                CraftMetaBook.this.pages.set(page - 1, this.componentsToPage(newText));
            }
        }

        public void setPages(BaseComponent[]... pages) {
            this.setPages(Arrays.asList(pages));
        }

        public void addPage(BaseComponent[]... pages) {
            BaseComponent[][] abasecomponent = pages;
            int i = pages.length;

            for (int j = 0; j < i; ++j) {
                BaseComponent[] page = abasecomponent[j];

                if (page == null) {
                    page = new BaseComponent[0];
                }

                CraftMetaBook.this.internalAddPage(this.componentsToPage(page));
            }

        }

        public List getPages() {
            if (CraftMetaBook.this.pages == null) {
                return ImmutableList.of();
            } else {
                final ImmutableList copy = ImmutableList.copyOf(CraftMetaBook.this.pages);

                return new AbstractList() {
                    public BaseComponent[] get(int index) {
                        return ComponentSerializer.parse(SpigotMeta.this.pageToJSON((String) copy.get(index)));
                    }

                    public int size() {
                        return copy.size();
                    }
                };
            }
        }

        public void setPages(List pages) {
            if (pages.isEmpty()) {
                CraftMetaBook.this.pages = null;
            } else {
                if (CraftMetaBook.this.pages != null) {
                    CraftMetaBook.this.pages.clear();
                }

                Iterator iterator = pages.iterator();

                while (iterator.hasNext()) {
                    BaseComponent[] page = (BaseComponent[]) iterator.next();

                    this.addPage(page);
                }

            }
        }
    }
}
