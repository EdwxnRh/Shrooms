package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.enums.Teams;
import de.sirguard.shrooms.objects.SPlayer;
import de.sirguard.shrooms.utils.ConfigManager;
import de.sirguard.shrooms.utils.GameManager;
import de.sirguard.shrooms.utils.ItemBuilder;
import de.sirguard.shrooms.utils.ScoreBoard;
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

        if(ConfigManager.mapsConfig.containsKey("lobby")) {
            Map<String, Object> lobbyMap = ((Map<String, Object>) ConfigManager.mapsConfig.get("lobby"));
            String worldName = (String) lobbyMap.get("world");
            if(new File(worldName).exists() && Bukkit.getWorld(worldName) != null) {
                Location location = ConfigManager.getLocationOfMap(lobbyMap);

                player.teleport(location);
            }

            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);

            GameManager.playersMap.put(player, new SPlayer());
            GameManager.playersMap.get(player).setScoreBoard(new ScoreBoard(player));

            for (Teams value : Teams.values()) {
                if(player.getScoreboard().getTeam(value.getName()) == null) {
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

            if(Bukkit.getOnlinePlayers().size() == GameManager.teamSize * GameManager.teamAmount && GameManager.countdown > 20) {
                GameManager.countdown = 20;
            }
        }
    }
}
