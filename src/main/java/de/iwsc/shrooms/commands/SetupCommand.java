package de.iwsc.shrooms.commands;

import com.google.gson.internal.LinkedTreeMap;
import de.iwsc.shrooms.Shrooms;
import de.iwsc.shrooms.enums.Teams;
import de.iwsc.shrooms.utils.ConfigManager;
import de.iwsc.shrooms.utils.GameManager;
import de.iwsc.shrooms.objects.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupCommand implements CommandExecutor {

    // setup command - probably starts with /setup lobby for lobby spawn and /setup map <builder> <size> for creating / saving the map
    // by using /setup map the spectator spawn will be set to the exact position of the cmd executor
    // -> than, /setup team <name> <color> : enables edit mode, hotbar items for setting tower, merchant, banker, upgrades, olli and team spawn
    // /setup spawner : sets spawner
    /*

    Falsche Befehleingabe fehlt
    Map Nachrichten ausgabe

     */

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 1) {
            switch (args[0]) {
                case "map" -> {
                    player.sendMessage(Shrooms.prefix + "Use §c/setup map <BUILDER> <SIZE>§7 to create a map.");
                    return true;
                }
                case "team" -> {
                    player.sendMessage(Shrooms.prefix + "Use §c/setup team <COLOR>§7 to create a team.");
                    return true;
                }
                case "tp" -> {
                    player.sendMessage(Shrooms.prefix + "Use §c/setup tp <MAP> §7 to teleport to the map.");
                    return true;
                }
            }
        }

        if (args.length == 0) {
            player.sendMessage(Component.text("\n§cInformation to Setup§r\n\n§7§l╰ §r§7Type §c/setup lobby §7to set the lobby\n§7§l╰ §r§7Type §c/setup map <BUILDER> <SIZE>§7 to create a map\n§7§l╰ §r§7Type §c/team <COLOR>§7 to create a team and start edit mode \n§7§l╰ §r§7Type §c/team tp <MAP>§7 to teleport to a map\n§7§l╰ §r§7Type §c/setup <CANCEL/SAVE> §7to cancel or save team spawns§r\n§7§l╰ §r§7Type §c/setup spawner §7to set a spawner for mushrooms"));
            return true;
            //WAS DAS LÖSCH DAS WTF: §c✿ Your server with the cute cow ✿

        }

        if (args.length == 1 && args[0].equalsIgnoreCase("lobby")) {
            Map<String, Object> lobbyLocationMap = ConfigManager.getLocationToMap(player.getLocation());

            ConfigManager.mapsConfig.put("lobby", lobbyLocationMap);
            ConfigManager.saveMapConfig();
            player.sendMessage(Shrooms.prefix + "You successfully set the lobby spawn.");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
            String worldName = args[1];

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                if (new File(worldName).exists()) {
                    player.sendMessage(Shrooms.prefix + "The world is loading...");

                    world = Bukkit.createWorld(new WorldCreator(worldName));
                    world.setDifficulty(Difficulty.NORMAL);
                    world.setTime(12000);
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

                    for (Entity entity : world.getEntities()) {
                        if(!(entity instanceof Mob) || entity instanceof Player)
                            continue;

                        entity.remove();
                    }

                    player.sendMessage(Shrooms.prefix + "The world was loaded successfully.");
                } else {
                    player.sendMessage(Shrooms.ERROR + "This world does not exist.");
                    return true;
                }
            }

            player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        } else if (args.length == 3 && args[0].equalsIgnoreCase("map")) {
            Map<String, Object> worldMap = new LinkedTreeMap<>();

            if (ConfigManager.mapsConfig.containsKey("maps")) {
                worldMap = ((Map) ConfigManager.mapsConfig.get("maps"));
            }

            if (worldMap.containsKey(player.getWorld().getName())) {
                player.sendMessage(Shrooms.ERROR + "This world is already registered!");
                return true;
            }

            try {
                Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                player.sendMessage(Shrooms.ERROR + "Use an integer as size!");
                return true;
            }

            Map<String, Object> worldInfoMap = new LinkedTreeMap<>();

            Map<String, Object> spectatorLocationMap = ConfigManager.getLocationToMap(player.getLocation());

            worldInfoMap.put("builder", args[1]);
            worldInfoMap.put("size", Integer.parseInt(args[2]));
            worldInfoMap.put("spectatorLocation", spectatorLocationMap);

            worldMap.put(player.getWorld().getName(), worldInfoMap);

            ConfigManager.mapsConfig.put("maps", worldMap);
            ConfigManager.saveMapConfig();

            player.sendMessage(Shrooms.prefix + "You successfully setup the map §b" + player.getWorld().getName());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("team")) {
            if (!Teams.isPossible(args[1])) {
                player.sendMessage(Shrooms.ERROR + "This is not a valid team name.");
                return true;
            }

            Map<String, Object> maps = (Map<String, Object>) ConfigManager.mapsConfig.getOrDefault("maps", new LinkedTreeMap<>());

            if (!maps.containsKey(player.getWorld().getName())) {
                player.sendMessage(Shrooms.ERROR + "The world must be added first!");
                return true;
            }

            Map<String, Object> map = (Map<String, Object>) maps.get(player.getWorld().getName());

            if (map.containsKey("teams")) {
                ArrayList<Map<String, Object>> teams = (ArrayList<Map<String, Object>>) map.get("teams");

                for (Map<String, Object> team : teams) {
                    if (((String) team.get("name")).equalsIgnoreCase(args[1])) {
                        player.sendMessage(Shrooms.ERROR + "This team already exists!");
                        return true;
                    }
                }
            }

            Team currentTeam = new Team(args[1]);

            boolean inEditMode = GameManager.playersInTeamsEditMode.containsKey(player);

            for (HashMap<String, Object> value : GameManager.playersInTeamsEditMode.values()) {
                if (((Team) value.get("team")).getName().equalsIgnoreCase(args[1]) || inEditMode) {
                    player.sendMessage(Shrooms.ERROR + "You are already in edit mode or someone else is editing this team! Please rejoin or restart the server to fix this issue.");
                    return true;
                }
            }

            int actionID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Shrooms.plugin, () -> player.sendActionBar(Component.text("§6/setup cancel §7| §6/setup save")), 0, 40);

            ItemStack[] preInv = player.getInventory().getContents();
            player.getInventory().clear();

            GameManager.playersInTeamsEditMode.put(player, new HashMap<>(Map.of("team", currentTeam, "actionID", actionID, "preInv", preInv)));

            ItemMeta itemMeta;

            ItemStack teamSpawn = new ItemStack(Material.PLAYER_HEAD);
            itemMeta = teamSpawn.getItemMeta();
            itemMeta.displayName(Component.text("§6Set Team-Spawn"));
            teamSpawn.setItemMeta(itemMeta);

            ItemStack bankerSpawn = new ItemStack(Material.GOLD_INGOT);
            itemMeta = bankerSpawn.getItemMeta();
            itemMeta.displayName(Component.text("§6Set Banker"));
            bankerSpawn.setItemMeta(itemMeta);

            ItemStack upgradesSpawn = new ItemStack(Material.AMETHYST_SHARD);
            itemMeta = upgradesSpawn.getItemMeta();
            itemMeta.displayName(Component.text("§6Set Upgrades"));
            upgradesSpawn.setItemMeta(itemMeta);

            ItemStack merchantSpawn = new ItemStack(Material.EMERALD);
            itemMeta = merchantSpawn.getItemMeta();
            itemMeta.displayName(Component.text("§6Set Merchant"));
            merchantSpawn.setItemMeta(itemMeta);

            ItemStack towerAxe = new ItemStack(Material.IRON_AXE);
            itemMeta = towerAxe.getItemMeta();
            itemMeta.displayName(Component.text("§6Set Tower"));
            itemMeta.lore(List.of(Component.text("§7Use §6right click §7to set location 1"), Component.text("§7Use §6left click §7to set location 2")));
            towerAxe.setItemMeta(itemMeta);

            ItemStack chefSpawn = new ItemStack(Material.CRIMSON_FUNGUS);
            itemMeta = chefSpawn.getItemMeta();
            itemMeta.displayName(Component.text("§6Set Shroomie Chef"));
            chefSpawn.setItemMeta(itemMeta);

            ItemStack shroomieSpawn = new ItemStack(Material.WARPED_FUNGUS);
            itemMeta = shroomieSpawn.getItemMeta();
            itemMeta.displayName(Component.text("§6Set Shroomie"));
            shroomieSpawn.setItemMeta(itemMeta);

            player.getInventory().setItem(0, teamSpawn);
            player.getInventory().setItem(1, bankerSpawn);
            player.getInventory().setItem(2, upgradesSpawn);
            player.getInventory().setItem(3, merchantSpawn);
            player.getInventory().setItem(6, towerAxe);
            player.getInventory().setItem(7, chefSpawn);
            player.getInventory().setItem(8, shroomieSpawn);

        } else if (args.length == 1 && args[0].equalsIgnoreCase("cancel")) {
            if (!GameManager.playersInTeamsEditMode.containsKey(player)) {
                player.sendMessage(Shrooms.ERROR + "You are not in editing mode!");
                return true;
            }

            player.sendMessage(Shrooms.prefix + "Canceled setup mode and discarded data!");

            ItemStack[] preInv = (ItemStack[]) GameManager.playersInTeamsEditMode.get(player).get("preInv");
            int actionID = (int) GameManager.playersInTeamsEditMode.get(player).get("actionID");

            player.getInventory().setContents(preInv);
            player.updateInventory();
            Bukkit.getScheduler().cancelTask(actionID);
            GameManager.playersInTeamsEditMode.remove(player);
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
            if (!GameManager.playersInTeamsEditMode.containsKey(player)) {
                player.sendMessage(Shrooms.ERROR + "You are not in editing mode!");
                return true;
            }

            Team team = (Team) GameManager.playersInTeamsEditMode.get(player).get("team");

            if (team.getBanker() == null || team.getMerchant() == null || team.getOlli() == null || team.getSpawn() == null ||
                    team.getTower() == null || team.getUpgrades() == null || team.getTower().getLocation1() == null ||
                    team.getTower().getLocation2() == null) {
                player.sendMessage(Shrooms.ERROR + "Not all settings have been set!");
            }

            Location teamSpawnLocation = team.getSpawn();

            Map<String, Object> maps = (Map<String, Object>) ConfigManager.mapsConfig.getOrDefault("maps", new LinkedTreeMap<>());
            Map<String, Object> map = (Map<String, Object>) maps.get(teamSpawnLocation.getWorld().getName());

            ArrayList<Map> list = new ArrayList<>();

            if (map.containsKey("teams"))
                list = (ArrayList<Map>) map.get("teams");

            list.add(Map.of(
                    "name", team.getName(),
                    "spawnLocation", ConfigManager.getLocationToMap(teamSpawnLocation),
                    "banker", Map.of(
                            "location", ConfigManager.getLocationToMap(team.getBanker().getLocation())
                    ),
                    "merchant", Map.of(
                            "location", ConfigManager.getLocationToMap(team.getMerchant().getLocation())
                    ),
                    "olli", Map.of(
                            "location", ConfigManager.getLocationToMap(team.getOlli().getLocation())
                    ),
                    "upgrades", Map.of(
                            "location", ConfigManager.getLocationToMap(team.getUpgrades().getLocation())
                    ),
                    "tower", Map.of(
                            "location1", ConfigManager.getLocationToMap(team.getTower().getLocation1()),
                            "location2", ConfigManager.getLocationToMap(team.getTower().getLocation2()),
                            "shroomieLocation", ConfigManager.getLocationToMap(team.getTower().getShroomieLocation())
                    )
            ));

            map.put("teams", list);

            maps.put(teamSpawnLocation.getWorld().getName(), map);

            ConfigManager.mapsConfig.put("maps", maps);
            ConfigManager.saveMapConfig();

            ItemStack[] preInv = (ItemStack[]) GameManager.playersInTeamsEditMode.get(player).get("preInv");
            int actionID = (int) GameManager.playersInTeamsEditMode.get(player).get("actionID");

            player.getInventory().setContents(preInv);
            player.updateInventory();
            Bukkit.getScheduler().cancelTask(actionID);
            GameManager.playersInTeamsEditMode.remove(player);
            player.sendMessage(Shrooms.prefix + "Canceled setup mode and saved data!");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("spawner")) {
            Map<String, Object> maps = (Map<String, Object>) ConfigManager.mapsConfig.getOrDefault("maps", new LinkedTreeMap<>());
            Map<String, Object> map = (Map<String, Object>) maps.get(player.getWorld().getName());

            ArrayList<Map> list = new ArrayList<>();

            if (map.containsKey("spawners"))
                list = (ArrayList<Map>) map.get("spawners");

            Map<String, Object> spawnerLocationMap = ConfigManager.getLocationToMap(player.getLocation().getBlock().getLocation().subtract(0, 1, 0));

            list.add(spawnerLocationMap);

            map.put("spawners", list);

            ConfigManager.saveMapConfig();
        } else if(args.length == 1 && args[0].equalsIgnoreCase("build")) {
            if(GameManager.playersInBuildMode.contains(player)) {
                GameManager.playersInBuildMode.remove(player);
                player.sendMessage(Shrooms.prefix + "You have exited the build mode!");
            } else {
                GameManager.playersInBuildMode.add(player);
                player.sendMessage(Shrooms.prefix + "You have entered the build mode!");
            }
        }
        return true;
    }
}