package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.Shrooms;
import de.sirguard.shrooms.utils.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = player.getKiller();

        if (killer != null) {
            event.deathMessage(Component.text(Shrooms.prefix + "§b" + player.getName() + " §7got killed by §b" + killer.getName()));
        } else {
            event.deathMessage(Component.text(Shrooms.prefix + "§b" + player.getName() + " §7died"));
        }

        player.teleport(GameManager.playersMap.get(player).getTeam().getSpawn());

        event.setCancelled(true);
    }
}
