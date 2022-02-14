package de.sirguard.shrooms.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.HashMap;

public class Team {
    private final String name;
    private Location spawn;
    private final ArrayList<Player> players;
    private Tower tower;
    private Banker banker;
    private Upgrades upgrades;
    private Merchant merchant;
    private Olli olli;

    private int balance;
    public HashMap<String, Integer> upg = new HashMap<>();

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();

        int defaultValue = 0;
        upg.put("frugatility", defaultValue);
        upg.put("speed", defaultValue);
        upg.put("prot", defaultValue);
        upg.put("resistance", defaultValue);
        upg.put("tower warper", defaultValue);
        upg.put("guard", defaultValue);
        upg.put("spy", defaultValue);

        upg.put("efficiency", defaultValue);
        upg.put("swiftness", defaultValue);
        upg.put("drop bonus", defaultValue);
        upg.put("quick spawn", defaultValue);
        upg.put("security", defaultValue);
        upg.put("discount", defaultValue);

    }

    public void addPlayer(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.getScoreboard().getTeam(name).addEntity(player);
        }

        this.players.add(player);
    }

    public void removePlayer(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.getScoreboard().getTeam(name).removeEntity(player);
        }

        this.players.remove(player);
    }

    public void addBalance() {
        this.balance++;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public void setBanker(Banker banker) {
        this.banker = banker;
    }

    public void setUpgrades(Upgrades upgrades) {
        this.upgrades = upgrades;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setOlli(Olli olli) {
        this.olli = olli;
    }

    public String getName() {
        return name;
    }

    public Location getSpawn() {
        return spawn;
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Tower getTower() {
        return tower;
    }

    public Banker getBanker() {
        return banker;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Upgrades getUpgrades() {
        return upgrades;
    }

    public Olli getOlli() {
        return olli;
    }
}
