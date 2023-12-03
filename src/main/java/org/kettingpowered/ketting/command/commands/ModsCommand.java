package org.kettingpowered.ketting.command.commands;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModsCommand extends BukkitCommand {

    public ModsCommand() {
        super("mods");
        this.description = "Gets a list of mods running on the server";
        this.usageMessage = "/mods";
        this.setPermission("ketting.command.mods");
        this.setAliases(Arrays.asList("modlist"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String currentAlias, @NotNull String[] args) {
        if (!testPermission(sender)) return true;

        sender.sendMessage("Mods " + getModList());
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }

    @NotNull
    private String getModList() {
        StringBuilder modList = new StringBuilder();
        List<IModInfo> mods = ModList.get().getMods();

        for (int i = 0; i < mods.size(); i++) {
            IModInfo mod = mods.get(i);

            if (i > 0) {
                modList.append(ChatColor.WHITE);
                modList.append(", ");
            }

            boolean isLoaded = ModList.get().isLoaded(mod.getModId());

            modList.append((isLoaded ? ChatColor.GREEN : ChatColor.RED) + mod.getDisplayName());
        }

        return "(" + mods.size() + "): " + modList;
    }
}
