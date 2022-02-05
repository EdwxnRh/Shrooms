package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.objects.SPlayer;
import de.sirguard.shrooms.objects.Team;
import de.sirguard.shrooms.objects.Upgrades;
import de.sirguard.shrooms.utils.GameManager;
import de.sirguard.shrooms.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static de.sirguard.shrooms.utils.GameManager.teams;

public class UpgradesListener implements Listener {
    private final String GUI_NAME = "§aUpgrades Shop";

    public void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9*4, GUI_NAME);
        SPlayer sPlayer = GameManager.playersMap.get(player);
        Team team = sPlayer.getTeam();

        for (int i = 0; i < 10; i++) {
            inv.setItem(1, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
        inv.setItem(1, new ItemBuilder(Material.IRON_PICKAXE).setDisplayName("§9Efficiency" + team.upg.get("effi")).setLore("§3Upgrade your pickaxe with efficiency to mine faster!", "§7Price: §6").build());

        player.openInventory(inv);
    }

    @EventHandler
    public void onOlliClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager olli)) return;

        if (Objects.equals(olli.getCustomName(), "§aChef Shroomie")) {
            event.setCancelled(true);
            openGUI(event.getPlayer());
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) || event.getClickedInventory() == null ||
                !event.getView().getTitle().equals(GUI_NAME) || event.getCurrentItem() == null) return;

        SPlayer sPlayer = GameManager.playersMap.get(player);

        switch (event.getCurrentItem().getType()) {
            case GOLD_BLOCK:
                event.setCancelled(true);
                sPlayer.addBalance(-10);
                sPlayer.getTeam().getTower().addBalance(10);
                break;
        }
    }
}
