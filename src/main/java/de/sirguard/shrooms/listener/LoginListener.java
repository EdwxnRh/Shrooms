package de.sirguard.shrooms.listener;


import de.sirguard.shrooms.Shrooms;
import de.sirguard.shrooms.enums.GameState;
import de.sirguard.shrooms.objects.Team;
import de.sirguard.shrooms.utils.GameManager;
import de.sirguard.shrooms.utils.ScoreBoard;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

public class LoginListener implements Listener {

    public static ArrayList<String> relogable_players = new ArrayList<>();

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if(GameManager.gameState == GameState.RUNNING) {
            if (relogable_players.contains(player.getUniqueId().toString())) {
                event.allow();
                player.sendMessage(Shrooms.prefix + "You are now back in the game!");
                GameManager.playersMap.get(player.getUniqueId().toString()).setScoreBoard(new ScoreBoard(player));
                Team team = GameManager.playersMap.get(player.getUniqueId().toString()).getTeam();
                GameManager.playersMap.get(player.getUniqueId().toString()).getTeam().removePlayer(player);
                GameManager.playersMap.get(player.getUniqueId().toString()).setTeam(team);

            } else {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("§cThe game already started!"));
            }
        }
    }
}
