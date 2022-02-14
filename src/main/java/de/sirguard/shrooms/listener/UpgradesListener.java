package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.Shrooms;
import de.sirguard.shrooms.enums.Teams;
import de.sirguard.shrooms.objects.SPlayer;
import de.sirguard.shrooms.objects.Team;
import de.sirguard.shrooms.utils.GameManager;
import de.sirguard.shrooms.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class UpgradesListener implements Listener {
    private final String GUI_NAME = "§aUpgrades Shop";
    private final String GENERAL_UPGRADES = "§aGeneral Upgrades Shop";
    private final String TOWER_UPGRADES = "§aTower Upgrades Shop";
    private final String BASE_UPGRADES = "§aBase Upgrades Shop";
    private final int frugatility_price = 60;
    private final int speed_price = 20;
    private final int resistance_price = 300;
    private final int tower_warper_price = 75;
    private final int guard_price = 350;
    private final int spy_price = 350;

    private final int effiency_price = 15;
    private final int swiftness_price = 150;
    private final int armor_price = 150;
    private final int drop_bonus_price = 50;
    private final int quick_spawn_price = 75;
    private final int security_price = 120;
    private final int discount_price = 150;


    public void openGUI(Player player) {
        SPlayer sPlayer = GameManager.playersMap.get(player.getUniqueId().toString());
        Team team = sPlayer.getTeam();


       // inv.setItem(1, new ItemBuilder(Material.IRON_PICKAXE).setDisplayName("§9Efficiency" + team.upg.get("effi")).setLore("§3Upgrade your pickaxe with efficiency to mine faster!", "§7Price: §6").build());

        player.openInventory(generalUpgrades(sPlayer));
    }

    @EventHandler
    public void onUpgradesHandlerClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager upgrades)) return;
        if (Objects.equals(upgrades.getCustomName(), "§aUpgrades")) {
            event.setCancelled(true);
            openGUI(event.getPlayer());
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) || event.getClickedInventory() == null ||
                event.getCurrentItem() == null) return;

        if (!event.getView().getTitle().equals(GENERAL_UPGRADES) &&  !event.getView().getTitle().equals(TOWER_UPGRADES) &&  !event.getView().getTitle().equals(BASE_UPGRADES) ) return;
        SPlayer sPlayer = GameManager.playersMap.get(player.getUniqueId().toString());
        event.setCancelled(true);
        switch (event.getCurrentItem().getType()) {

            case BRICKS:
                player.openInventory(generalUpgrades(sPlayer));
                break;
            case GOLDEN_CHESTPLATE:
                player.openInventory(towerUpgrades(sPlayer));
                break;
            case BEACON:
                player.openInventory(baseUpgrades(sPlayer));
                break;
            default:
                break;
        }
        if (event.getView().getTitle().equals(GENERAL_UPGRADES)) {
            switch (event.getCurrentItem().getType()){


                default: break;
            }
        } else if (event.getView().getTitle().equals(BASE_UPGRADES)) {
            switch (event.getCurrentItem().getType()){


                default: break;
            }
        } else if (event.getView().getTitle().equals(TOWER_UPGRADES)) {
            switch (event.getCurrentItem().getType()){
                case GOLD_NUGGET:
                    if (sPlayer.getBalance() + sPlayer.getTeam().getBalance() > frugatility_price) {
                        int money = sPlayer.getBalance() - frugatility_price;
                        if (money <= sPlayer.getBalance()) {
                            sPlayer.addBalance(-money);
                        } else {
                            money -= sPlayer.getBalance();
                            sPlayer.setBalance(0);
                            sPlayer.getTeam().addBalance(-money);
                        }
                        sPlayer.getTeam().upg.put("frugatility", 1);
                        for (Player teamPlayer : sPlayer.getTeam().getPlayers()) {
                            teamPlayer.sendMessage(Shrooms.prefix + "§b" + player.getName() + " §9has bought frugatility I");
                        }
                    } else {
                        player.sendMessage(Shrooms.prefix + "You can't afford this upgrade!");
                    }
                    break;
                case VILLAGER_SPAWN_EGG:
                    if (sPlayer.getBalance() + sPlayer.getTeam().getBalance() > speed_price) {
                        int money = sPlayer.getBalance() - speed_price;
                        if (money <= sPlayer.getBalance()) {
                            sPlayer.addBalance(-money);
                        } else {
                            money -= sPlayer.getBalance();
                            sPlayer.setBalance(0);
                            sPlayer.getTeam().addBalance(-money);
                        }
                        sPlayer.getTeam().upg.put("speed", 1);
                        for (Player teamPlayer : sPlayer.getTeam().getPlayers()) {
                            teamPlayer.sendMessage(Shrooms.prefix + "§b" + player.getName() + " §9has bought speed I");
                        }
                    } else {
                        player.sendMessage(Shrooms.prefix + "You can't afford this upgrade!");
                    }
                    break;
                case OBSIDIAN:
                    if (sPlayer.getBalance() + sPlayer.getTeam().getBalance() > resistance_price) {
                        int money = sPlayer.getBalance() - resistance_price;
                        if (money <= sPlayer.getBalance()) {
                            sPlayer.addBalance(-money);
                        } else {
                            money -= sPlayer.getBalance();
                            sPlayer.setBalance(0);
                            sPlayer.getTeam().addBalance(-money);
                        }
                        sPlayer.getTeam().upg.put("frugatility", 1);
                        for (Player teamPlayer : sPlayer.getTeam().getPlayers()) {
                            teamPlayer.sendMessage(Shrooms.prefix + "§b" + player.getName() + " §9has bought resistance I");
                        }
                    } else {
                        player.sendMessage(Shrooms.prefix + "You can't afford this upgrade!");
                    }
                    break;
                case LIGHT_WEIGHTED_PRESSURE_PLATE:
                    if (sPlayer.getBalance() + sPlayer.getTeam().getBalance() > tower_warper_price) {
                        int money = sPlayer.getBalance() - tower_warper_price;
                        if (money <= sPlayer.getBalance()) {
                            sPlayer.addBalance(-money);
                        } else {
                            money -= sPlayer.getBalance();
                            sPlayer.setBalance(0);
                            sPlayer.getTeam().addBalance(-money);
                        }
                        sPlayer.getTeam().upg.put("frugatility", 1);
                        for (Player teamPlayer : sPlayer.getTeam().getPlayers()) {
                            teamPlayer.sendMessage(Shrooms.prefix + "§b" + player.getName() + " §9has bought tower warper I");
                        }
                    } else {
                        player.sendMessage(Shrooms.prefix + "You can't afford this upgrade!");
                    }
                    break;
                case NOTE_BLOCK:
                    if (sPlayer.getBalance() + sPlayer.getTeam().getBalance() > guard_price) {
                        int money = sPlayer.getBalance() - guard_price;
                        if (money <= sPlayer.getBalance()) {
                            sPlayer.addBalance(-money);
                        } else {
                            money -= sPlayer.getBalance();
                            sPlayer.setBalance(0);
                            sPlayer.getTeam().addBalance(-money);
                        }
                        sPlayer.getTeam().upg.put("frugatility", 1);
                        for (Player teamPlayer : sPlayer.getTeam().getPlayers()) {
                            teamPlayer.sendMessage(Shrooms.prefix + "§b" + player.getName() + " §9has bought guard I");
                        }
                    } else {
                        player.sendMessage(Shrooms.prefix + "You can't afford this upgrade!");
                    }
                    break;
                case ENDER_EYE:
                    if (sPlayer.getBalance() + sPlayer.getTeam().getBalance() > spy_price) {
                        int money = sPlayer.getBalance() - spy_price;
                        if (money <= sPlayer.getBalance()) {
                            sPlayer.addBalance(-money);
                        } else {
                            money -= sPlayer.getBalance();
                            sPlayer.setBalance(0);
                            sPlayer.getTeam().addBalance(-money);
                        }
                        sPlayer.getTeam().upg.put("frugatility", 1);
                        for (Player teamPlayer : sPlayer.getTeam().getPlayers()) {
                            teamPlayer.sendMessage(Shrooms.prefix + "§b" + player.getName() + " §9has bought spy I");
                        }
                    } else {
                        player.sendMessage(Shrooms.prefix + "You can't afford this upgrade!");
                    }
                    break;

                default: break;
            }
        }
    }

    private Inventory generalUpgrades(SPlayer player) {
        Inventory inv = Bukkit.createInventory(null, 9*3, GENERAL_UPGRADES);

        Material mat = Material.BLACK_STAINED_GLASS_PANE;
        for (int i = 9; i < 18; i++) {
            inv.setItem(i, new ItemStack(mat));
        }
        inv.setItem(18, new ItemStack(mat));
        inv.setItem(26, new ItemStack(mat));

        ItemStack generalItem = new ItemBuilder(Material.BRICKS).setDisplayName("§aGeneral Upgrades").setEnchantment(Enchantment.DURABILITY, 1, false).build();
        inv.setItem(19, generalItem);
        ItemStack teamItem = new ItemBuilder(Material.GOLDEN_CHESTPLATE).setDisplayName("§aTeam Upgrades").build();
        inv.setItem(20, teamItem);
        ItemStack baseItem = new ItemBuilder(Material.BEACON).setDisplayName("§aBase Upgrades").build();
        inv.setItem(21, baseItem);

        ItemStack effi = new ItemBuilder(Material.IRON_PICKAXE).setDisplayName("§9Effiency I").setLore("§3Your team's pickaxes are enchanted with §9Efficiency I", "\n", "§7Price: §6" + effiency_price).build();
        inv.setItem(0, effi);
        ItemStack swiftness = new ItemBuilder(Material.SUGAR).setDisplayName("§9Swiftness I").setLore("§3Your team receives the §9Swiftness I §3effect", "\n", "§7Price: §6" + swiftness_price).build();
        inv.setItem(1, swiftness);
        ItemStack armor = new ItemBuilder(Material.COOKIE).setDisplayName("§9Drop Bonus I").setLore("§3Your team ahs a §910% §3chance for more drops by Cookie Blocks", "\n", "§7Price: §6" + armor_price).build();
        inv.setItem(2, armor);
        ItemStack quick_spawn = new ItemBuilder(Material.CLOCK).setDisplayName("§9Quick Spawn I").setLore("§3You machines produce Cookies §920% §3faster", "\nand your Cookie Blocks respawn §920% §3faster", "\n", "§7Price: §6" + quick_spawn_price).build();
        inv.setItem(3, quick_spawn);
        ItemStack security = new ItemBuilder(Material.BEDROCK).setDisplayName("§9Security").setLore("§3Opponents will receive a blindness effect", "\n", "§7Price: §6" + security_price).build();
        inv.setItem(4, security);
        ItemStack discount = new ItemBuilder(Material.GOLD_NUGGET).setDisplayName("§9Discount").setLore("§3Reduce the cost of purchases in the shop by §930%", "\n", "§7Prize: §6" + discount_price).build();
        inv.setItem(5, discount);


        return inv;
    }

    private Inventory baseUpgrades(SPlayer player) {
        Inventory inv = Bukkit.createInventory(null, 9*3, BASE_UPGRADES);
        Material mat = Material.BLACK_STAINED_GLASS_PANE;
        for (int i = 9; i < 18; i++) {
            inv.setItem(i, new ItemStack(mat));
        }
        inv.setItem(18, new ItemStack(mat));
        inv.setItem(26, new ItemStack(mat));

        ItemStack generalItem = new ItemBuilder(Material.BRICKS).setDisplayName("§aGeneral Upgrades").build();
        inv.setItem(19, generalItem);
        ItemStack teamItem = new ItemBuilder(Material.GOLDEN_CHESTPLATE).setDisplayName("§aTower Upgrades").build();
        inv.setItem(20, teamItem);
        ItemStack baseItem = new ItemBuilder(Material.BEACON).setEnchantment(Enchantment.DURABILITY, 1, false).setDisplayName("§aBase Upgrades").build();
        inv.setItem(21, baseItem);






        return inv;
    }

    private Inventory towerUpgrades(SPlayer player) {
        Inventory inv = Bukkit.createInventory(null, 9*3, TOWER_UPGRADES);
        Material mat = Material.BLACK_STAINED_GLASS_PANE;

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, new ItemStack(mat));
        }
        inv.setItem(18, new ItemStack(mat));
        inv.setItem(26, new ItemStack(mat));


        ItemStack generalItem = new ItemBuilder(Material.BRICKS).setDisplayName("§aGeneral Upgrades").build();
        inv.setItem(19, generalItem);
        ItemStack teamItem = new ItemBuilder(Material.GOLDEN_CHESTPLATE).setDisplayName("§aTower Upgrades").setEnchantment(Enchantment.DURABILITY, 1, false).build();
        inv.setItem(20, teamItem);
        ItemStack baseItem = new ItemBuilder(Material.BEACON).setDisplayName("§aBase Upgrades").build();
        inv.setItem(21, baseItem);

        ItemStack goldnugget = new ItemBuilder(Material.GOLD_NUGGET).setDisplayName("§9Frugality I").setLore("§3Chef Shroomie has a §910% §3chance of saving a block", "\n", "§7Price: §6" + frugatility_price).build();
        inv.setItem(0, goldnugget);

        ItemStack vil = new ItemBuilder(Material.VILLAGER_SPAWN_EGG).setDisplayName("§9Speed I").setLore("§3Chef Shroomie builds §910% §3faster", "\n", "§7Price: §6" + speed_price).build();
        inv.setItem(1, vil);

        ItemStack obsidian = new ItemBuilder(Material.OBSIDIAN).setDisplayName("§9Resistance I").setLore("§3Chef Shroomie's blocks cannot be destroyed that easily anymore", "\n", "§7Price: §6" + resistance_price).build();
        inv.setItem(2, obsidian);

        ItemStack goldplate = new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE).setDisplayName("§9Tower Warper I").setLore("§3Your team is able to use the teleporter warper every 30 seconds", "\n", "§7Price: §6" + tower_warper_price).build();
        inv.setItem(3, goldplate);

        ItemStack noteblock = new ItemBuilder(Material.NOTE_BLOCK).setDisplayName("§9Guard").setLore("§3Once an opponent gets close to your tower, \n your team will be notified and they will become visible", "\n", "§7Price: §6" + guard_price).build();
        inv.setItem(4, noteblock);

        ItemStack endereye = new ItemBuilder(Material.ENDER_EYE).setDisplayName("§9Spy").setLore("§3Your team can always keep an eye on the opposing towers", "\n", "§7Price: §6" + spy_price).build();
        inv.setItem(5, endereye);

        return inv;
    }

}
