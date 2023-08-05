package io.github.elihuso.back.listener;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathListener implements Listener {

    private final Plugin plugin;
    public PlayerDeathListener(Plugin plugin){this.plugin = plugin;}

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) throws Exception {

        if (event.isCancelled())
            return;

        FileConfiguration config = new YamlConfiguration();
        Location location = event.getEntity().getLocation();
        config.set("x", location.getX());
        config.set("y", location.getY());
        config.set("z", location.getZ());
        config.set("world", location.getWorld().getName());
        config.save(plugin.getDataFolder() + "/" + event.getEntity().getUniqueId().toString());
    }

    @EventHandler
    public void OnPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 900, 4, true, false, true));
    }

    @EventHandler
    public void OnTeleport(PlayerTeleportEvent event) throws Exception {
        if(event.isCancelled())
            return;
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
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE))
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
