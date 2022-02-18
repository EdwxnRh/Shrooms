package de.iwsc.shrooms.listener;

import de.iwsc.shrooms.Shrooms;
import de.iwsc.shrooms.objects.SPlayer;
import de.iwsc.shrooms.utils.GameManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickUpListener implements Listener {
    @EventHandler
    public void onItemPickCollect(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if(event.getItem().getItemStack().getType() == Shrooms.cookie) {
            event.setCancelled(true);
            GameManager.playersMap.get(player.getUniqueId().toString()).addBalance(event.getItem().getItemStack().getAmount());
            event.getItem().remove();


            for (SPlayer sPlayer : GameManager.playersMap.values()) {
                if(sPlayer.getScoreBoard() != null)
                    sPlayer.getScoreBoard().update();
            }
        }
    }
}
