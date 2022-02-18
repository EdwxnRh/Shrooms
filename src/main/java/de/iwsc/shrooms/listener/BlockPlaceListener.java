package de.iwsc.shrooms.listener;

import de.iwsc.shrooms.enums.GameState;
import de.iwsc.shrooms.utils.GameManager;
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
