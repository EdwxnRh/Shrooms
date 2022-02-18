package de.iwsc.shrooms.commands;

import de.iwsc.shrooms.utils.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ForceStartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (player.getUniqueId().toString().equals("5e703bac-a056-4060-9617-14c19ea54af2") || player.getUniqueId().toString().equals("5e703baca0564060961714c19ea54af2")) {

            GameManager.forcestarted = true;
            GameManager.loadVotedMap();
            GameManager.countdown = 5;

            player.sendMessage("§bForcestarted the game!");
        }


        return false;
    }
    // Force start command with permission that allows granted users to start the game, even if lobby isn't full / not enough players but at least 2

}
