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

import java.io.IOException;
import java.util.UUID;

public class PlayerDeathListener implements Listener {
    private final Plugin plugin;

    public PlayerDeathListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }

        saveLocation(event.getEntity().getUniqueId(), event.getEntity().getLocation());
    }

    @EventHandler
    public void OnPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 900, 4, true, false, true));
    }

    @EventHandler
    public void OnTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }

        switch (event.getCause()) {
            case END_PORTAL:
            case NETHER_PORTAL:
            case CHORUS_FRUIT:
            case ENDER_PEARL:
            case END_GATEWAY:
            case SPECTATE:
                return;
        }

        saveLocation(event.getPlayer().getUniqueId(), event.getFrom());
    }

    private void saveLocation(UUID uuid, Location location) {
        FileConfiguration config = new YamlConfiguration();
        config.set("location", location);
        try {
            config.save(plugin.getDataFolder() + "/data/" + uuid + ".yml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
