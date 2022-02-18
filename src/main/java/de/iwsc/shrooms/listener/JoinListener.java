package de.iwsc.shrooms.listener;

import de.iwsc.shrooms.Shrooms;
import de.iwsc.shrooms.enums.GameState;
import de.iwsc.shrooms.enums.Teams;
import de.iwsc.shrooms.utils.ConfigManager;
import de.iwsc.shrooms.utils.GameManager;
import de.iwsc.shrooms.utils.ItemBuilder;
import de.iwsc.shrooms.utils.ScoreBoard;
import de.iwsc.shrooms.objects.SPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.Map;

public class JoinListener implements Listener {

    public static final String VOTING_ITEM_NAME = "§b§lVoting-Menu";
    public static final String TEAM_ITEM_NAME = "§b§lTeam-Selector";
    public static final String KIT_ITEM_NAME = "§b§lKit-Selector";

    private final ItemStack voteItem;
    private final ItemStack teamSelector;
    private final ItemStack kitSelector;

    public JoinListener() {
        voteItem = new ItemBuilder(Material.NETHER_STAR).
                setDisplayName(VOTING_ITEM_NAME).setLore("§7Use this item to vote for a map.", "§7You can't change your vote").build();
        teamSelector = new ItemBuilder(Material.RED_BED).setDisplayName(TEAM_ITEM_NAME).setLore("§7Choose your team" , "").build();
        kitSelector = new ItemBuilder(Material.CHEST).setDisplayName(KIT_ITEM_NAME).setLore("§7Kits are strong skill-abilities", "§7given for your advantage").build();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // TODO: ChatColor
        if (GameManager.gameState == GameState.RUNNING) {
            if (LoginListener.relogable_players.contains(player.getUniqueId().toString())){
                LoginListener.relogable_players.remove(player.getUniqueId().toString());

                SPlayer sPlayer = GameManager.playersMap.get(player.getUniqueId().toString());
                de.iwsc.shrooms.objects.Team steam = sPlayer.getTeam();
                sPlayer.setBalance(sPlayer.getBalance());
                steam.addPlayer(player);
                GameManager.playersMap.get(player.getUniqueId().toString()).setTeam(steam);

                player.sendMessage(Shrooms.prefix + "§7You are now in team §" + Teams.getTeamByDisplayName(steam.getName()).getColor() + steam.getName());
                GameManager.playersMap.get(player.getUniqueId().toString()).setScoreBoard(new ScoreBoard(player));
                player.sendMessage(Shrooms.prefix + "You are now back in the game!");
            } else {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Shrooms.prefix + "§7You are now spectating the game!");
            }

        } else {
            if (ConfigManager.mapsConfig.containsKey("lobby")) {
                Map<String, Object> lobbyMap = ((Map<String, Object>) ConfigManager.mapsConfig.get("lobby"));
                String worldName = (String) lobbyMap.get("world");
                if (new File(worldName).exists() && Bukkit.getWorld(worldName) != null) {
                    Location location = ConfigManager.getLocationOfMap(lobbyMap);

                    player.teleport(location);
                }

                player.getInventory().clear();
                player.setGameMode(GameMode.ADVENTURE);

                GameManager.playersMap.put(player.getUniqueId().toString(), new SPlayer());
                GameManager.playersMap.get(player.getUniqueId().toString()).setScoreBoard(new ScoreBoard(player));

                for (Teams value : Teams.values()) {
                    if (player.getScoreboard().getTeam(value.getName()) == null) {
                        Team team = player.getScoreboard().registerNewTeam(value.getName());
                        team.setColor(ChatColor.getByChar(value.getColor()));
                        team.setPrefix("§" + value.getColor());
                        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
                        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
                    }
                }

                player.getInventory().setItem(2, teamSelector);
                player.getInventory().setItem(4, kitSelector);
                player.getInventory().setItem(6, voteItem);

                player.setLevel(0);
                player.setExp(0);
                player.setHealth(20);
                player.setFoodLevel(20);

                if (Bukkit.getOnlinePlayers().size() == GameManager.teamSize * GameManager.teamAmount && GameManager.countdown > 20) {
                    GameManager.countdown = 20;
                }
            }
        }
    }
}
