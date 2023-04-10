package fr.lavapower.eogendrealtime.manager;

import fr.lavapower.eogendrealtime.EogendRealTime;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.time.LocalDateTime;

public class RealTimeManager {
    private static final double SECONDS_TO_TICKS_FACTOR = 1000d / Math.pow(60d, 2d);

    private final EogendRealTime plugin;

    public RealTimeManager(EogendRealTime plugin) {
        this.plugin = plugin;
    }

    public boolean triggerRealTime(String world) {
        if(plugin.getConfiguration().worlds().contains(world))
            plugin.getConfiguration().worlds().remove(world);
        else
            plugin.getConfiguration().worlds().add(world);
        return plugin.getConfiguration().worlds().contains(world);
    }

    public void launchRealTime() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            LocalDateTime dateTime = LocalDateTime.now();
            int seconds = dateTime.getHour() * 3600 + dateTime.getMinute() * 60 + dateTime.getSecond();
            int result = 18000 + (int)(seconds * SECONDS_TO_TICKS_FACTOR);
            while(result > 24000)
                result -= 24000;

            for(String world : plugin.getConfiguration().worlds()) {
                World w = Bukkit.getWorld(world);
                if(w != null)
                {
                    if(Boolean.TRUE.equals(w.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)))
                        w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                    w.setTime(result);
                }
            }
        }, 0, 200);
    }
}
