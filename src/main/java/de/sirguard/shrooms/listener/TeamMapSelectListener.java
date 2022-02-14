package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.Shrooms;
import de.sirguard.shrooms.enums.GameState;
import de.sirguard.shrooms.enums.Teams;
import de.sirguard.shrooms.objects.Team;
import de.sirguard.shrooms.utils.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class TeamMapSelectListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (GameManager.gameState == GameState.RUNNING || GameManager.playersInBuildMode.contains(event.getWhoClicked())) {
            return;
        }

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() != null) {
            String title = event.getView().getTitle();

            if (title.equals("§a§lTeams")) {
                int slot = event.getRawSlot();

                if (slot >= 0 && event.getCurrentItem() != null) {
                    for (Team team : GameManager.teams) {
                        team.removePlayer(player);
                    }

                    Team team = GameManager.teams[slot];
                    if (team.getPlayers().size() == GameManager.teamSize) {
                        player.sendMessage(Shrooms.ERROR + "§7This team is full!");
                        return;
                    }

                    team.addPlayer(player);
                    GameManager.playersMap.get(player.getUniqueId().toString()).setTeam(team);

                    player.sendMessage(Shrooms.prefix + "§7You are now in team §" + Teams.getTeamByDisplayName(team.getName()).getColor() + team.getName());
                }
            } else if (title.equals("§c§lVoting")) {
                if (event.getCurrentItem() == null) return;
                ItemStack item = event.getCurrentItem();
                String map = item.getItemMeta().getDisplayName().replaceAll(Pattern.compile("§7 - by §b\\w+").pattern(), "").replaceAll("§b", "");

                GameManager.votedMap.put(player, GameManager.maps.get(map));
                player.sendMessage(Shrooms.prefix + "§7You have voted for the map " + item.getItemMeta().getDisplayName());
            }
        }
    }
}
