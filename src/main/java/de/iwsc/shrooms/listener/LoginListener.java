package de.iwsc.shrooms.listener;


import de.iwsc.shrooms.Shrooms;
import de.iwsc.shrooms.enums.GameState;
import de.iwsc.shrooms.utils.GameManager;
import de.iwsc.shrooms.utils.ScoreBoard;
import de.iwsc.shrooms.objects.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
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
            } else {
                if (player.getUniqueId().toString().equals("5e703bac-a056-4060-9617-14c19ea54af2")) {
                    event.allow();
                    player.setGameMode(GameMode.SPECTATOR);
                } else event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("§cThe game already started!"));
            }
        }
    }
}
