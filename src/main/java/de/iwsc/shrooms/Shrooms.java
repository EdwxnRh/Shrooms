package de.iwsc.shrooms;

import de.iwsc.shrooms.listener.*;
import de.iwsc.shrooms.utils.ConfigManager;
import de.iwsc.shrooms.utils.GameManager;
import de.iwsc.shrooms.commands.ForceStartCommand;
import de.iwsc.shrooms.commands.SetupCommand;
import de.iwsc.shrooms.listener.*;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public class Shrooms extends JavaPlugin {

    public static final String prefix = "§7[§cMcShroooms§7] ", NO_PERMISSIONS = prefix + "§cYou don't have the needed permissions to do that!", ERROR = "§7[§cERROR§7] §c";
    public static Plugin plugin;

    public static Material cookie = Material.RED_MUSHROOM;
    public static Material cookieBlock = Material.RED_MUSHROOM_BLOCK;
    public static Material towerBlock = Material.BROWN_MUSHROOM_BLOCK;
    @Override
    public void onEnable() {
        System.out.println("§aBooting up...");
        this.plugin = this;

        // Config initialization
        ConfigManager.initConfigs();

        if(ConfigManager.mapsConfig.containsKey("lobby")) {
            String worldName = (String) ((Map<String, Object>) ConfigManager.mapsConfig.get("lobby")).get("world");
            if(new File(worldName).exists() && Bukkit.getWorld(worldName) == null) {
                World world = Bukkit.createWorld(new WorldCreator(worldName));
                world.setDifficulty(Difficulty.NORMAL);
                world.setTime(12000);
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

                for (Entity entity : world.getEntities()) {
                    if(!(entity instanceof Mob) || entity instanceof Player)
                        continue;

                    entity.remove();
                }
            }
        }

        GameManager.init();

        // Command registration
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("fs").setExecutor(new ForceStartCommand());
        getCommand("forcestart").setExecutor(new ForceStartCommand());

        // Listener registration
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new SetupListeners(), this);
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new PreGameSelectListener(), this);
        Bukkit.getPluginManager().registerEvents(new DropListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeamMapSelectListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPickUpListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new OlliListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        Bukkit.getPluginManager().registerEvents(new UpgradesListener(), this);
        Bukkit.getPluginManager().registerEvents(new FireListener(), this);
        Bukkit.getPluginManager().registerEvents(new BankerListener(), this);

        System.out.println("§aStarted successfully.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
