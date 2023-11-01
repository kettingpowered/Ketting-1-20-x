package org.bukkit.craftbukkit.v1_20_R2.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.bukkit.ChatColor;

public final class CraftChatMessage {

    private static final Pattern LINK_PATTERN = Pattern.compile("((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))");
    private static final Map formatMap;

    static {
        Builder builder = ImmutableMap.builder();
        ChatFormatting[] achatformatting;
        int i = (achatformatting = ChatFormatting.values()).length;

        for (int j = 0; j < i; ++j) {
            ChatFormatting format = achatformatting[j];

            builder.put(Character.toLowerCase(format.toString().charAt(1)), format);
        }

        formatMap = builder.build();
    }

    public static ChatFormatting getColor(ChatColor color) {
        return (ChatFormatting) CraftChatMessage.formatMap.get(color.getChar());
    }

    public static ChatColor getColor(ChatFormatting format) {
        return ChatColor.getByChar(format.code);
    }

    public static Component fromStringOrNull(String message) {
        return fromStringOrNull(message, false);
    }

    public static Component fromStringOrNull(String message, boolean keepNewlines) {
        return message != null && !message.isEmpty() ? fromString(message, keepNewlines)[0] : null;
    }

    public static Component[] fromString(String message) {
        return fromString(message, false);
    }

    public static Component[] fromString(String message, boolean keepNewlines) {
        return fromString(message, keepNewlines, false);
    }

    public static Component[] fromString(String message, boolean keepNewlines, boolean plain) {
        return (new CraftChatMessage.StringMessage(message, keepNewlines, plain)).getOutput();
    }

    public static String toJSON(Component component) {
        return Component.Serializer.toJson(component);
    }

    public static String toJSONOrNull(Component component) {
        return component == null ? null : toJSON(component);
    }

    public static Component fromJSON(String jsonMessage) throws JsonParseException {
        return Component.Serializer.fromJson(jsonMessage);
    }

    public static Component fromJSONOrNull(String jsonMessage) {
        if (jsonMessage == null) {
            return null;
        } else {
            try {
                return fromJSON(jsonMessage);
            } catch (JsonParseException jsonparseexception) {
                return null;
            }
        }
    }

    public static Component fromJSONOrString(String message) {
        return fromJSONOrString(message, false);
    }

    public static Component fromJSONOrString(String message, boolean keepNewlines) {
        return fromJSONOrString(message, false, keepNewlines);
    }

    private static Component fromJSONOrString(String message, boolean nullable, boolean keepNewlines) {
        if (message == null) {
            message = "";
        }

        if (nullable && message.isEmpty()) {
            return null;
        } else {
            Component component = fromJSONOrNull(message);

            return component != null ? component : fromString(message, keepNewlines)[0];
        }
    }

    public static String fromJSONOrStringToJSON(String message) {
        return fromJSONOrStringToJSON(message, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, false, keepNewlines, Integer.MAX_VALUE, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message) {
        return fromJSONOrStringOrNullToJSON(message, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, true, keepNewlines, Integer.MAX_VALUE, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean nullable, boolean keepNewlines, int maxLength, boolean checkJsonContentLength) {
        if (message == null) {
            message = "";
        }

        if (nullable && message.isEmpty()) {
            return null;
        } else {
            Component component = fromJSONOrNull(message);

            if (component != null) {
                if (checkJsonContentLength) {
                    String content = fromComponent(component);
                    String trimmedContent = trimMessage(content, maxLength);

                    if (content != trimmedContent) {
                        return fromStringToJSON(trimmedContent, keepNewlines);
                    }
                }

                return message;
            } else {
                message = trimMessage(message, maxLength);
                return fromStringToJSON(message, keepNewlines);
            }
        }
    }

    public static String trimMessage(String message, int maxLength) {
        return message != null && message.length() > maxLength ? message.substring(0, maxLength) : message;
    }

    public static String fromStringToJSON(String message) {
        return fromStringToJSON(message, false);
    }

    public static String fromStringToJSON(String message, boolean keepNewlines) {
        Component component = fromString(message, keepNewlines)[0];

        return toJSON(component);
    }

    public static String fromStringOrNullToJSON(String message) {
        Component component = fromStringOrNull(message);

        return toJSONOrNull(component);
    }

    public static String fromJSONComponent(String jsonMessage) {
        Component component = fromJSONOrNull(jsonMessage);

        return fromComponent(component);
    }

    public static String fromComponent(Component component) {
        if (component == null) {
            return "";
        } else {
            StringBuilder out = new StringBuilder();
            boolean hadFormat = false;

            Component c;

            for (Iterator iterator = component.iterator(); iterator.hasNext();c.getContents().visit((xx) -> {
                out.append(xx);
                return Optional.empty();
            })) {
                c = (Component) iterator.next();
                Style modi = c.getStyle();
                TextColor color = modi.getColor();

                if (c.getContents() != ComponentContents.EMPTY || color != null) {
                    if (color != null) {
                        if (color.format != null) {
                            out.append(color.format);
                        } else {
                            out.append('§').append("x");
                            char[] achar;
                            int i = (achar = color.serialize().substring(1).toCharArray()).length;

                            for (int j = 0; j < i; ++j) {
                                char magic = achar[j];

                                out.append('§').append(magic);
                            }
                        }

                        hadFormat = true;
                    } else if (hadFormat) {
                        out.append(ChatColor.RESET);
                        hadFormat = false;
                    }
                }

                if (modi.isBold()) {
                    out.append(ChatFormatting.BOLD);
                    hadFormat = true;
                }

                if (modi.isItalic()) {
                    out.append(ChatFormatting.ITALIC);
                    hadFormat = true;
                }

                if (modi.isUnderlined()) {
                    out.append(ChatFormatting.UNDERLINE);
                    hadFormat = true;
                }

                if (modi.isStrikethrough()) {
                    out.append(ChatFormatting.STRIKETHROUGH);
                    hadFormat = true;
                }

                if (modi.isObfuscated()) {
                    out.append(ChatFormatting.OBFUSCATED);
                    hadFormat = true;
                }
            }

            return out.toString();
        }
    }

    public static Component fixComponent(MutableComponent component) {
        Matcher matcher = CraftChatMessage.LINK_PATTERN.matcher("");

        return fixComponent(component, matcher);
    }

    private static Component fixComponent(MutableComponent component, Matcher matcher) {
        if (component.getContents() instanceof LiteralContents) {
            LiteralContents text = (LiteralContents) component.getContents();
            String msg = text.text();

            if (matcher.reset(msg).find()) {
                matcher.reset();
                Style modifier = component.getStyle();
                ArrayList extras = new ArrayList();
                ArrayList extrasOld = new ArrayList(component.getSiblings());

                component = Component.empty();

                int pos;

                for (pos = 0; matcher.find(); pos = matcher.end()) {
                    String match = matcher.group();

                    if (!match.startsWith("http://") && !match.startsWith("https://")) {
                        match = "http://" + match;
                    }

                    MutableComponent prev = Component.literal(msg.substring(pos, matcher.start()));

                    prev.setStyle(modifier);
                    extras.add(prev);
                    MutableComponent link = Component.literal(matcher.group());
                    Style linkModi = modifier.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, match));

                    link.setStyle(linkModi);
                    extras.add(link);
                }

                MutableComponent prev = Component.literal(msg.substring(pos));

                prev.setStyle(modifier);
                extras.add(prev);
                extras.addAll(extrasOld);
                Iterator iterator = extras.iterator();

                while (iterator.hasNext()) {
                    Component c = (Component) iterator.next();

                    component.append(c);
                }
            }
        }

        List extras = component.getSiblings();

        for (int i = 0; i < extras.size(); ++i) {
            Component comp = (Component) extras.get(i);

            if (comp.getStyle() != null && comp.getStyle().getClickEvent() == null) {
                extras.set(i, fixComponent(comp.copy(), matcher));
            }
        }

        if (component.getContents() instanceof TranslatableContents) {
            Object[] subs = ((TranslatableContents) component.getContents()).getArgs();

            for (int i = 0; i < subs.length; ++i) {
                Object comp = subs[i];

                if (comp instanceof Component) {
                    Component c = (Component) comp;

                    if (c.getStyle() != null && c.getStyle().getClickEvent() == null) {
                        subs[i] = fixComponent(c.copy(), matcher);
                    }
                } else if (comp instanceof String && matcher.reset((String) comp).find()) {
                    subs[i] = fixComponent(Component.literal((String) comp), matcher);
                }
            }
        }

        return component;
    }

    private CraftChatMessage() {}

    private static final class StringMessage {

        private static final Pattern INCREMENTAL_PATTERN = Pattern.compile("(" + String.valueOf('§') + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + '§' + " \\n]|$))))|(\\n)", 2);
        private static final Pattern INCREMENTAL_PATTERN_KEEP_NEWLINES = Pattern.compile("(" + String.valueOf('§') + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + '§' + " ]|$))))", 2);
        private static final Style RESET = Style.EMPTY.withBold(false).withItalic(false).withUnderlined(false).withStrikethrough(false).withObfuscated(false);
        private final List list = new ArrayList();
        private MutableComponent currentChatComponent = Component.empty();
        private Style modifier;
        private final Component[] output;
        private int currentIndex;
        private StringBuilder hex;
        private final String message;
        private static volatile int[] $SWITCH_TABLE$net$minecraft$EnumChatFormat;

        private StringMessage(String message, boolean keepNewlines, boolean plain) {
            this.modifier = Style.EMPTY;
            this.message = message;
            if (message == null) {
                this.output = new Component[]{this.currentChatComponent};
            } else {
                this.list.add(this.currentChatComponent);
                Matcher matcher = (keepNewlines ? CraftChatMessage.StringMessage.INCREMENTAL_PATTERN_KEEP_NEWLINES : CraftChatMessage.StringMessage.INCREMENTAL_PATTERN).matcher(message);
                String match = null;

                int groupId;
                boolean needsAdd;

                for (needsAdd = false; matcher.find(); this.currentIndex = matcher.end(groupId)) {
                    groupId = 0;

                    do {
                        ++groupId;
                    } while ((match = matcher.group(groupId)) == null);

                    int index = matcher.start(groupId);

                    if (index > this.currentIndex) {
                        needsAdd = false;
                        this.appendNewComponent(index);
                    }

                    switch (groupId) {
                        case 1:
                            char c = match.toLowerCase(Locale.ENGLISH).charAt(1);
                            ChatFormatting format = (ChatFormatting) CraftChatMessage.formatMap.get(c);

                            if (c == 'x') {
                                this.hex = new StringBuilder("#");
                            } else if (this.hex != null) {
                                this.hex.append(c);
                                if (this.hex.length() == 7) {
                                    this.modifier = CraftChatMessage.StringMessage.RESET.withColor(TextColor.parseColor(this.hex.toString()));
                                    this.hex = null;
                                }
                            } else if (format.isFormat() && format != ChatFormatting.RESET) {
                                switch ($SWITCH_TABLE$net$minecraft$EnumChatFormat()[format.ordinal()]) {
                                    case 17:
                                        this.modifier = this.modifier.withObfuscated(Boolean.TRUE);
                                        break;
                                    case 18:
                                        this.modifier = this.modifier.withBold(Boolean.TRUE);
                                        break;
                                    case 19:
                                        this.modifier = this.modifier.withStrikethrough(Boolean.TRUE);
                                        break;
                                    case 20:
                                        this.modifier = this.modifier.withUnderlined(Boolean.TRUE);
                                        break;
                                    case 21:
                                        this.modifier = this.modifier.withItalic(Boolean.TRUE);
                                        break;
                                    default:
                                        throw new AssertionError("Unexpected message format");
                                }
                            } else {
                                this.modifier = CraftChatMessage.StringMessage.RESET.withColor(format);
                            }

                            needsAdd = true;
                            break;
                        case 2:
                            if (plain) {
                                this.appendNewComponent(matcher.end(groupId));
                            } else {
                                if (!match.startsWith("http://") && !match.startsWith("https://")) {
                                    match = "http://" + match;
                                }

                                this.modifier = this.modifier.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, match));
                                this.appendNewComponent(matcher.end(groupId));
                                this.modifier = this.modifier.withClickEvent((ClickEvent) null);
                            }
                            break;
                        case 3:
                            if (needsAdd) {
                                this.appendNewComponent(index);
                            }

                            this.currentChatComponent = null;
                    }
                }

                if (this.currentIndex < message.length() || needsAdd) {
                    this.appendNewComponent(message.length());
                }

                this.output = (Component[]) this.list.toArray(new Component[this.list.size()]);
            }
        }

        private void appendNewComponent(int index) {
            MutableComponent addition = Component.literal(this.message.substring(this.currentIndex, index)).setStyle(this.modifier);

            this.currentIndex = index;
            if (this.currentChatComponent == null) {
                this.currentChatComponent = Component.empty();
                this.list.add(this.currentChatComponent);
            }

            this.currentChatComponent.append((Component) addition);
        }

        private Component[] getOutput() {
            return this.output;
        }

        static int[] $SWITCH_TABLE$net$minecraft$EnumChatFormat() {
            int[] aint = CraftChatMessage.StringMessage.$SWITCH_TABLE$net$minecraft$EnumChatFormat;

            if (aint != null) {
                return aint;
            } else {
                int[] aint1 = new int[ChatFormatting.values().length];

                try {
                    aint1[ChatFormatting.AQUA.ordinal()] = 12;
                } catch (NoSuchFieldError nosuchfielderror) {
                    ;
                }

                try {
                    aint1[ChatFormatting.BLACK.ordinal()] = 1;
                } catch (NoSuchFieldError nosuchfielderror1) {
                    ;
                }

                try {
                    aint1[ChatFormatting.BLUE.ordinal()] = 10;
                } catch (NoSuchFieldError nosuchfielderror2) {
                    ;
                }

                try {
                    aint1[ChatFormatting.BOLD.ordinal()] = 18;
                } catch (NoSuchFieldError nosuchfielderror3) {
                    ;
                }

                try {
                    aint1[ChatFormatting.DARK_AQUA.ordinal()] = 4;
                } catch (NoSuchFieldError nosuchfielderror4) {
                    ;
                }

                try {
                    aint1[ChatFormatting.DARK_BLUE.ordinal()] = 2;
                } catch (NoSuchFieldError nosuchfielderror5) {
                    ;
                }

                try {
                    aint1[ChatFormatting.DARK_GRAY.ordinal()] = 9;
                } catch (NoSuchFieldError nosuchfielderror6) {
                    ;
                }

                try {
                    aint1[ChatFormatting.DARK_GREEN.ordinal()] = 3;
                } catch (NoSuchFieldError nosuchfielderror7) {
                    ;
                }

                try {
                    aint1[ChatFormatting.DARK_PURPLE.ordinal()] = 6;
                } catch (NoSuchFieldError nosuchfielderror8) {
                    ;
                }

                try {
                    aint1[ChatFormatting.DARK_RED.ordinal()] = 5;
                } catch (NoSuchFieldError nosuchfielderror9) {
                    ;
                }

                try {
                    aint1[ChatFormatting.GOLD.ordinal()] = 7;
                } catch (NoSuchFieldError nosuchfielderror10) {
                    ;
                }

                try {
                    aint1[ChatFormatting.GRAY.ordinal()] = 8;
                } catch (NoSuchFieldError nosuchfielderror11) {
                    ;
                }

                try {
                    aint1[ChatFormatting.GREEN.ordinal()] = 11;
                } catch (NoSuchFieldError nosuchfielderror12) {
                    ;
                }

                try {
                    aint1[ChatFormatting.ITALIC.ordinal()] = 21;
                } catch (NoSuchFieldError nosuchfielderror13) {
                    ;
                }

                try {
                    aint1[ChatFormatting.LIGHT_PURPLE.ordinal()] = 14;
                } catch (NoSuchFieldError nosuchfielderror14) {
                    ;
                }

                try {
                    aint1[ChatFormatting.OBFUSCATED.ordinal()] = 17;
                } catch (NoSuchFieldError nosuchfielderror15) {
                    ;
                }

                try {
                    aint1[ChatFormatting.RED.ordinal()] = 13;
                } catch (NoSuchFieldError nosuchfielderror16) {
                    ;
                }

                try {
                    aint1[ChatFormatting.RESET.ordinal()] = 22;
                } catch (NoSuchFieldError nosuchfielderror17) {
                    ;
                }

                try {
                    aint1[ChatFormatting.STRIKETHROUGH.ordinal()] = 19;
                } catch (NoSuchFieldError nosuchfielderror18) {
                    ;
                }

                try {
                    aint1[ChatFormatting.UNDERLINE.ordinal()] = 20;
                } catch (NoSuchFieldError nosuchfielderror19) {
                    ;
                }

                try {
                    aint1[ChatFormatting.WHITE.ordinal()] = 16;
                } catch (NoSuchFieldError nosuchfielderror20) {
                    ;
                }

                try {
                    aint1[ChatFormatting.YELLOW.ordinal()] = 15;
                } catch (NoSuchFieldError nosuchfielderror21) {
                    ;
                }

                CraftChatMessage.StringMessage.$SWITCH_TABLE$net$minecraft$EnumChatFormat = aint1;
                return aint1;
            }
        }
    }
}
