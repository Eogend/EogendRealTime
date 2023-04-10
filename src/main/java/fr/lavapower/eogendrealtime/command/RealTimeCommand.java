package fr.lavapower.eogendrealtime.command;

import fr.lavapower.eogendrealtime.EogendRealTime;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RealTimeCommand implements CommandExecutor, TabCompleter
{
    EogendRealTime plugin;

    public RealTimeCommand(EogendRealTime plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length >= 1) {
            String world = strings[0];
            if (Bukkit.getWorld(world) == null) {
                commandSender.sendMessage("Ce monde n'existe pas !");
                return true;
            }

            if (plugin.getManager().triggerRealTime(world)) {
                commandSender.sendMessage("Temps réel activé pour " + world);
                return true;
            }

            commandSender.sendMessage("Temps réel désactivé pour " + world);
        }
        else
            commandSender.sendMessage("Usage : /realtime <world>");
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length == 1)
            return Bukkit.getWorlds().stream().map(WorldInfo::getName).filter(x -> strings[0].isEmpty() || x.toLowerCase().startsWith(strings[0].toLowerCase())).toList();
        return null;
    }
}