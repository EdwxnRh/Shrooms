package de.sirguard.shrooms.listener;


import de.sirguard.shrooms.enums.GameState;
import de.sirguard.shrooms.utils.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if(GameManager.gameState == GameState.RUNNING) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("§cThe game already started!"));
        }
    }
}
