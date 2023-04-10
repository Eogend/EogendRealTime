package fr.lavapower.eogendrealtime.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public record Configuration(List<String> worlds) {
    public static Configuration fromConfig(FileConfiguration config) {
        return new Configuration(config.getStringList("worlds"));
    }
}
