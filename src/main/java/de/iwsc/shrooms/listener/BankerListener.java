package de.iwsc.shrooms.listener;

import de.iwsc.shrooms.utils.GameManager;
import de.iwsc.shrooms.utils.ItemBuilder;
import de.iwsc.shrooms.objects.SPlayer;
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

public class BankerListener implements Listener {
    private String title = "§6Banker";


    public void openGui(Player player) {
        player.openInventory(bankerinv());
    }

    @EventHandler
    public void onOlliClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager olli)) return;

        if (Objects.equals(olli.getCustomName(), "§aBanker")) {
            event.setCancelled(true);
            openGui(event.getPlayer());
        }
    }

    public Inventory bankerinv() {
        Inventory inv = Bukkit.createInventory(null, 9*1, title);

        ItemStack dep2 = new ItemBuilder(Material.COOKIE).setDisplayName("§6Deposit 2 Cookies").build();
        inv.setItem(0, dep2);
        ItemStack dep8 = new ItemBuilder(Material.COOKIE).setDisplayName("§6Deposit 8 Cookies").build();
        inv.setItem(2, dep8);
        ItemStack dep32 = new ItemBuilder(Material.COOKIE).setDisplayName("§6Deposit 32 Cookies").build();
        inv.setItem(4, dep32);
        ItemStack dep64 = new ItemBuilder(Material.COOKIE).setDisplayName("§6Deposit 64 Cookies").build();
        inv.setItem(6, dep64);
        ItemStack depblock = new ItemBuilder(Material.COOKIE).setDisplayName("§6Deposit a Cookie Block").setLore("§7Price: §c1 Cookie Block","§bShift Click to sell all").build();
        inv.setItem(8, depblock);
        return inv;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals(title)) return;
        SPlayer sPlayer = GameManager.playersMap.get(player.getUniqueId().toString());
        event.setCancelled(true);
        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§6Deposit 2 Cookies":
                if (sPlayer.getBalance() >= 2) {
                    sPlayer.addBalance(-2);
                    sPlayer.getTeam().addBalance(2);
                } else {
                    sPlayer.getTeam().addBalance(sPlayer.getBalance());
                    sPlayer.setBalance(0);
                }
                break;
            case "§6Deposit 8 Cookies":
                if (sPlayer.getBalance() >= 8) {
                    sPlayer.addBalance(-8);
                    sPlayer.getTeam().addBalance(8);
                } else {
                    sPlayer.getTeam().addBalance(sPlayer.getBalance());
                    sPlayer.setBalance(0);
                }
                break;
            case "§6Deposit 32 Cookies":
                if (sPlayer.getBalance() >= 32) {
                    sPlayer.addBalance(-32);
                    sPlayer.getTeam().addBalance(32);
                } else {
                    sPlayer.getTeam().addBalance(sPlayer.getBalance());
                    sPlayer.setBalance(0);
                }
                break;
            case "§6Deposit 64 Cookies":
                if (sPlayer.getBalance() >= 64) {
                    sPlayer.addBalance(-64);
                    sPlayer.getTeam().addBalance(64);
                } else {
                    sPlayer.getTeam().addBalance(sPlayer.getBalance());
                    sPlayer.setBalance(0);
                }
                break;
            case "§6Deposit a Cookie Block":
                if (event.getClick().isShiftClick()) {
                    if (player.getInventory().contains(Material.RED_MUSHROOM_BLOCK)) {
                        int amount = 0;
                        for (int i = 0; i < 35; i++) {
                            if (player.getInventory().getItem(i).getType().equals(Material.RED_MUSHROOM_BLOCK)) {
                                amount = player.getInventory().getItem(i).getAmount();
                                player.getInventory().clear(i);
                            }
                        }
                        sPlayer.addBalance(amount*2);
                    }
                } else {
                    if (player.getInventory().contains(Material.RED_MUSHROOM_BLOCK)) {
                        player.getInventory().remove(new ItemStack(Material.RED_MUSHROOM_BLOCK, 1));
                        sPlayer.addBalance(2);
                    }
                }
                break;
            default: break;
        }
    }

}
