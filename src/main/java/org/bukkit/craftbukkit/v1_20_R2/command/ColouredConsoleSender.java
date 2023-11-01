package org.bukkit.craftbukkit.v1_20_R2.command;

import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jline.Terminal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Attribute;
import org.fusesource.jansi.Ansi.Color;

public class ColouredConsoleSender extends CraftConsoleCommandSender {

    private final Terminal terminal = ((CraftServer) this.getServer()).getReader().getTerminal();
    private final Map replacements = new EnumMap(ChatColor.class);
    private final ChatColor[] colors = ChatColor.values();
    private final boolean jansiPassthrough = Boolean.getBoolean("jansi.passthrough");
    private static final char ANSI_ESC_CHAR = '\u001b';
    private static final String RGB_STRING = String.valueOf('\u001b') + "[38;2;%d;%d;%dm";
    private static final Pattern RBG_TRANSLATE = Pattern.compile(String.valueOf('§') + "x(" + '§' + "[A-F0-9]){6}", 2);

    protected ColouredConsoleSender() {
        this.replacements.put(ChatColor.BLACK, Ansi.ansi().a(Attribute.RESET).fg(Color.BLACK).boldOff().toString());
        this.replacements.put(ChatColor.DARK_BLUE, Ansi.ansi().a(Attribute.RESET).fg(Color.BLUE).boldOff().toString());
        this.replacements.put(ChatColor.DARK_GREEN, Ansi.ansi().a(Attribute.RESET).fg(Color.GREEN).boldOff().toString());
        this.replacements.put(ChatColor.DARK_AQUA, Ansi.ansi().a(Attribute.RESET).fg(Color.CYAN).boldOff().toString());
        this.replacements.put(ChatColor.DARK_RED, Ansi.ansi().a(Attribute.RESET).fg(Color.RED).boldOff().toString());
        this.replacements.put(ChatColor.DARK_PURPLE, Ansi.ansi().a(Attribute.RESET).fg(Color.MAGENTA).boldOff().toString());
        this.replacements.put(ChatColor.GOLD, Ansi.ansi().a(Attribute.RESET).fg(Color.YELLOW).boldOff().toString());
        this.replacements.put(ChatColor.GRAY, Ansi.ansi().a(Attribute.RESET).fg(Color.WHITE).boldOff().toString());
        this.replacements.put(ChatColor.DARK_GRAY, Ansi.ansi().a(Attribute.RESET).fg(Color.BLACK).bold().toString());
        this.replacements.put(ChatColor.BLUE, Ansi.ansi().a(Attribute.RESET).fg(Color.BLUE).bold().toString());
        this.replacements.put(ChatColor.GREEN, Ansi.ansi().a(Attribute.RESET).fg(Color.GREEN).bold().toString());
        this.replacements.put(ChatColor.AQUA, Ansi.ansi().a(Attribute.RESET).fg(Color.CYAN).bold().toString());
        this.replacements.put(ChatColor.RED, Ansi.ansi().a(Attribute.RESET).fg(Color.RED).bold().toString());
        this.replacements.put(ChatColor.LIGHT_PURPLE, Ansi.ansi().a(Attribute.RESET).fg(Color.MAGENTA).bold().toString());
        this.replacements.put(ChatColor.YELLOW, Ansi.ansi().a(Attribute.RESET).fg(Color.YELLOW).bold().toString());
        this.replacements.put(ChatColor.WHITE, Ansi.ansi().a(Attribute.RESET).fg(Color.WHITE).bold().toString());
        this.replacements.put(ChatColor.MAGIC, Ansi.ansi().a(Attribute.BLINK_SLOW).toString());
        this.replacements.put(ChatColor.BOLD, Ansi.ansi().a(Attribute.UNDERLINE_DOUBLE).toString());
        this.replacements.put(ChatColor.STRIKETHROUGH, Ansi.ansi().a(Attribute.STRIKETHROUGH_ON).toString());
        this.replacements.put(ChatColor.UNDERLINE, Ansi.ansi().a(Attribute.UNDERLINE).toString());
        this.replacements.put(ChatColor.ITALIC, Ansi.ansi().a(Attribute.ITALIC).toString());
        this.replacements.put(ChatColor.RESET, Ansi.ansi().a(Attribute.RESET).toString());
    }

    public void sendMessage(String message) {
        if (!this.jansiPassthrough && !this.terminal.isAnsiSupported()) {
            super.sendMessage(message);
        } else if (!this.conversationTracker.isConversingModaly()) {
            String result = convertRGBColors(message);
            ChatColor[] achatcolor = this.colors;
            int i = this.colors.length;

            for (int j = 0; j < i; ++j) {
                ChatColor color = achatcolor[j];

                if (this.replacements.containsKey(color)) {
                    result = result.replaceAll("(?i)" + color.toString(), (String) this.replacements.get(color));
                } else {
                    result = result.replaceAll("(?i)" + color.toString(), "");
                }
            }

            System.out.println(result + Ansi.ansi().reset().toString());
        }

    }

    private static String convertRGBColors(String input) {
        Matcher matcher = ColouredConsoleSender.RBG_TRANSLATE.matcher(input);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String s = matcher.group().replace("§", "").replace('x', '#');
            java.awt.Color color = java.awt.Color.decode(s);
            int red = color.getRed();
            int blue = color.getBlue();
            int green = color.getGreen();
            String replacement = String.format(ColouredConsoleSender.RGB_STRING, red, green, blue);

            matcher.appendReplacement(buffer, replacement);
        }

        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public static ConsoleCommandSender getInstance() {
        return (ConsoleCommandSender) (Bukkit.getConsoleSender() != null ? Bukkit.getConsoleSender() : new ColouredConsoleSender());
    }
}
