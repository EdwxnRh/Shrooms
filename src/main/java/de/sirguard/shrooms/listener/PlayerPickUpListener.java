package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.utils.GameManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickUpListener implements Listener {
    @EventHandler
    public void onItemPickCollect(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if(event.getItem().getItemStack().getType() == Material.RED_MUSHROOM) {
            GameManager.playersMap.get(player.getUniqueId().toString()).addBalance();
            event.getItem().remove();
        }
    }
}
