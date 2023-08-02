package io.github.elihuso.back;

import io.github.elihuso.back.listener.PlayerDeathListener;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.logging.Level;

public final class Back extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player)sender;
        }
        if (player == null)
            return false;
        if (command.getName().equalsIgnoreCase("back")) {
            String filePath = this.getDataFolder() + "/" + player.getUniqueId().toString();
            FileConfiguration config = new YamlConfiguration();
            try {
                config.load(filePath);
            }
            catch(Exception exception) {
                player.sendMessage(ChatColor.RED + "Cannot back to your previous location");
                getLogger().log(Level.WARNING, exception.getMessage());
                return false;
            }
            double x = config.getDouble("x");
            double y = config.getDouble("y");
            double z = config.getDouble("z");
            Location location = new Location(player.getWorld(), x, y, z);
            Location preLocation = player.getLocation();
            player.teleport(location);
            player.sendMessage(ChatColor.GREEN + "Teleport to your previous location.");
            config.set("x", preLocation.getX());
            config.set("y", preLocation.getY());
            config.set("z", preLocation.getZ());
            try {
                config.save(filePath);
            }
            catch (Exception exception) {
                getLogger().log(Level.WARNING, "Unable to save config of " + player.getUniqueId().toString());
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
