package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.Shrooms;
import de.sirguard.shrooms.enums.Teams;
import de.sirguard.shrooms.objects.*;
import de.sirguard.shrooms.utils.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class SetupListeners implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!GameManager.playersInTeamsEditMode.containsKey(event.getPlayer())) return;
        Team team = (Team) GameManager.playersInTeamsEditMode.get(event.getPlayer()).get("team");
        Player p = event.getPlayer();

        if(event.getHand() != EquipmentSlot.HAND) {
            event.setCancelled(true);
            return;
        }

        switch (p.getItemInHand().getType()) {
            case PLAYER_HEAD -> {
                team.setSpawn(p.getLocation());
                p.sendMessage(Shrooms.prefix + "You successfully set the spawn of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
            }
            case GOLD_INGOT -> {
                team.setBanker(new Banker(p.getLocation()));
                p.sendMessage(Shrooms.prefix + "You successfully set the Banker of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
            }
            case AMETHYST_SHARD -> {
                team.setUpgrades(new Upgrades(p.getLocation()));
                p.sendMessage(Shrooms.prefix + "You successfully set the Upgrades-Villager of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
            }
            case EMERALD -> {
                team.setMerchant(new Merchant(p.getLocation()));
                p.sendMessage(Shrooms.prefix + "You successfully set the Merchant of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
            }
            case IRON_AXE -> {
                if(team.getTower() == null)
                    team.setTower(new Tower());

                if(event.getAction().isRightClick()) {
                    team.getTower().setLocation1(Objects.requireNonNull(event.getClickedBlock()).getLocation());
                    p.sendMessage(Shrooms.prefix + "You successfully set §bposition 1 of the tower of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
                } else if(event.getAction().isLeftClick()) {
                    team.getTower().setLocation2(Objects.requireNonNull(event.getClickedBlock()).getLocation());
                    p.sendMessage(Shrooms.prefix + "You successfully set §bposition 2 of the tower of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
                }
            }
            case CRIMSON_FUNGUS -> {
                team.setOlli(new Olli(p.getLocation()));
                p.sendMessage(Shrooms.prefix + "You successfully set the Shroomie Chef of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
            }
            case WARPED_FUNGUS -> {
                if(team.getTower() == null)
                    team.setTower(new Tower());

                team.getTower().setShroomieLocation(p.getLocation());
                p.sendMessage(Shrooms.prefix + "You successfully set the Tower Shroomie of team §" + Teams.getColorOfTeam(team.getName()) + team.getName());
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onSetupDrop(PlayerDropItemEvent event) {
        if(!GameManager.playersInTeamsEditMode.containsKey(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onSetupItemDrag(InventoryClickEvent event) {
        if(!GameManager.playersInTeamsEditMode.containsKey((Player) event.getWhoClicked())) return;
        event.setCancelled(true);
    }

}
