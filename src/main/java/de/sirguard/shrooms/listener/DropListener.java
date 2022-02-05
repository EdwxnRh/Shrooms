package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.enums.GameState;
import de.sirguard.shrooms.utils.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (GameManager.gameState == GameState.RUNNING) return;
        event.setCancelled(true);
    }
}
