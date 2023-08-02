package io.github.elihuso.back.listener;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDeathListener implements Listener {

    private final Plugin plugin;
    public PlayerDeathListener(Plugin plugin){this.plugin = plugin;}

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) throws Exception {
        FileConfiguration config = new YamlConfiguration();
        Location location = event.getEntity().getLocation();
        config.set("x", location.getX());
        config.set("y", location.getY());
        config.set("z", location.getZ());
        config.set("world", location.getWorld().getName());
        config.save(plugin.getDataFolder() + "/" + event.getEntity().getUniqueId().toString());
    }

}
