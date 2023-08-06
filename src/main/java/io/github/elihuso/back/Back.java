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
        if (!(sender instanceof Player)) {
            sender.sendMessage("Please use back command as a player.");
            return false;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("back")) {
            String filePath = this.getDataFolder() + "/data/" + player.getUniqueId() + ".yml";
            FileConfiguration config = new YamlConfiguration();
            try {
                config.load(filePath);
            } catch (Exception exception) {
                player.sendMessage(ChatColor.RED + "Cannot back to your previous location");
                // qyl27: silence is gold.
                return false;
            }
            Location location = config.getLocation("location");
            player.teleport(location);
            player.sendMessage(ChatColor.GREEN + "Teleport to your previous location.");
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
