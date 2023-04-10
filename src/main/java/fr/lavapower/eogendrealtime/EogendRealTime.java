package fr.lavapower.eogendrealtime;

import fr.lavapower.eogendrealtime.command.RealTimeCommand;
import fr.lavapower.eogendrealtime.configuration.Configuration;
import fr.lavapower.eogendrealtime.manager.RealTimeManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class EogendRealTime extends JavaPlugin {
    private Configuration configuration;
    private RealTimeManager manager;

    @Override
    public void onEnable() {
        // Setup config
        saveDefaultConfig();
        configuration = Configuration.fromConfig(getConfig());

        // Register command
        registerCommand("realtime", new RealTimeCommand(this));

        // Create Manager
        manager = new RealTimeManager(this);

        manager.launchRealTime();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public RealTimeManager getManager() {
        return manager;
    }

    private <T extends CommandExecutor & TabCompleter> void registerCommand(String name, T command)
    {
        PluginCommand pluginCommand = getCommand(name);
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }
}
