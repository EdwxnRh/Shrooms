package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.Shrooms;
import de.sirguard.shrooms.enums.GameState;
import de.sirguard.shrooms.enums.Teams;
import de.sirguard.shrooms.objects.Map;
import de.sirguard.shrooms.utils.GameManager;
import de.sirguard.shrooms.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerInteractListener implements Listener {

    Inventory teamSelection;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(GameManager.gameState == GameState.RUNNING || GameManager.playersInBuildMode.contains(event.getPlayer())) return;

        Player player = event.getPlayer();

        event.setCancelled(true);

        if(event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        switch (player.getItemInHand().getType()) {
            case RED_BED -> {
                teamSelection = Bukkit.createInventory(null, GameManager.teamAmount > 9 ? 18 : 9, "§a§lTeams");
                for (int i = 0; i < GameManager.teamAmount; i++) {
                    Teams team = Teams.values()[i];
                    teamSelection.addItem(new ItemBuilder(Material.LEATHER_CHESTPLATE).setDisplayName(team.getTitle()).setLeatherColor(team.getLeatherColor()).build());
                }

                player.openInventory(teamSelection);
            }
            case NETHER_STAR -> {
                if(GameManager.gameState != GameState.VOTING) {
                    player.sendMessage(Shrooms.prefix + "The voting phase is already over!");
                    return;
                }

                Inventory votingInventory = Bukkit.createInventory(null, 9, "§c§lVoting");

                for (Map map : GameManager.maps.values()) {
                    ItemBuilder itemBuilder = new ItemBuilder(Material.MAP).setDisplayName("§b" + map.getName() + "§7 - by §b" + map.getBuilder());

                    int others = GameManager.countVotes(map.getName());

                    if (GameManager.votedMap.containsKey(player) && GameManager.votedMap.get(player).getName().equalsIgnoreCase(map.getName())) {
                        itemBuilder.setLore("§eVoted by you and " + (others - 1) + " other" + ((others - 1) == 1 ? "" : "s"));
                    } else {
                        itemBuilder.setLore("§eVoted by " + others + " other" + (others == 1 ? "" : "s"));
                    }

                    votingInventory.addItem(itemBuilder.build());
                }

                player.openInventory(votingInventory);
            }
            case CHEST -> {
                Inventory kitSelectionPageOne = Bukkit.createInventory(null, 9*5, "§b§lKits - Page: " + "1");
                Inventory kitSelectionPageTwo = Bukkit.createInventory(null, 9*5, "§b§lKits - Page: " + "2");

                ItemStack arrowLeft = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta arrowLeftMeta = (SkullMeta) arrowLeft.getItemMeta();
                arrowLeftMeta.setOwner("MHF_ArrowLeft");
                arrowLeftMeta.setDisplayName("§aNext Page");
                arrowLeft.setItemMeta(arrowLeftMeta);

                ItemStack arrowRight = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta arrowRightMeta = (SkullMeta) arrowLeft.getItemMeta();
                arrowRightMeta.setOwner("MHF_ArrowRight");
                arrowRightMeta.setDisplayName("§aNext Page");
                arrowRight.setItemMeta(arrowRightMeta);


                kitSelectionPageOne.setItem(18, arrowLeft);
                kitSelectionPageOne.setItem(26, arrowRight);
                kitSelectionPageTwo.setItem(18, arrowLeft);
                kitSelectionPageTwo.setItem(26, arrowRight);

                player.openInventory(kitSelectionPageOne);
            }
        }
    }
}
