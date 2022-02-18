package de.iwsc.shrooms.listener;

import de.iwsc.shrooms.enums.Teams;
import de.iwsc.shrooms.utils.GameManager;
import de.iwsc.shrooms.objects.Team;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.setCancelled(true);

        Team team = GameManager.playersMap.get(event.getPlayer().getUniqueId().toString()).getTeam();

        if(team != null) {
            Bukkit.broadcast(Component.text("§" + Teams.getTeamByDisplayName(team.getName()).getColor() + event.getPlayer().getName() + " §8>> §7").append(event.message()));
        } else {
            Bukkit.broadcast(Component.text("§7" + event.getPlayer().getName() + " §8>> §7").append(event.message()));
        }
    }
}
