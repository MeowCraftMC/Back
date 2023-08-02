package io.github.elihuso.back.listener;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
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

    @EventHandler
    public void OnTeleport(PlayerTeleportEvent event) throws Exception {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL))
            return;
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL))
            return;
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))
            return;
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_GATEWAY))
            return;
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT))
            return;
        FileConfiguration config = new YamlConfiguration();
        Location location = event.getFrom();
        config.set("x", location.getX());
        config.set("y", location.getY());
        config.set("z", location.getZ());
        config.set("world", location.getWorld().getName());
        config.save(plugin.getDataFolder() + "/" + event.getPlayer().getUniqueId().toString());
    }

}
