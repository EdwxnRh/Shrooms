package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.enums.GameState;
import de.sirguard.shrooms.utils.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(GameManager.playersInBuildMode.contains(event.getPlayer()) || GameManager.gameState != GameState.RUNNING)
            return;

        event.setCancelled(true);
    }
}
