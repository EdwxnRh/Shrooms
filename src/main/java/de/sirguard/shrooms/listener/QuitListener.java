package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.utils.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        GameManager.playersInTeamsEditMode.remove(player);

        GameManager.playersInBuildMode.remove(player);

        GameManager.playersMap.remove(player).getTeam().removePlayer(player);

        GameManager.votedMap.remove(player);
    }
}
