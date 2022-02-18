package de.iwsc.shrooms.utils;

import de.iwsc.shrooms.objects.Team;
import org.bukkit.entity.Player;

public class ScoreBoard extends ScoreBoardBuilder {

    public ScoreBoard(Player player) {
        super(player, "  §cMcShroooms.net  ");
    }

    @Override
    public void createScoreboard() {
        Team team = sPlayer.getTeam();

        setScore("§e§7§m                          ", 12);
        setScore("§7Balance:", 11);
        setScore("§e§c" + sPlayer.getBalance() + " Shrooms", 10);
        setScore("§c§d", 9);
        setScore("§7Team:", 8);
        setScore("§c" + (team != null ? team.getBalance() : 0) + " Shrooms", 7);
        setScore("§c§e", 6);
        setScore("§7Tower:", 5);
        setScore("§c" + (team != null ? (team.getTower() != null ? team.getTower().getProgress() : 0) : 0) + "%", 4);
        setScore("§c§a", 3);
        setScore("§7Kit:", 2);
        setScore("§c" + sPlayer.getKit(), 1);
        setScore("§f§7§m                          ", 0);
    }

    @Override
    public void update() {
        Team team = sPlayer.getTeam();

        setScore("§e§7§m                          ", 12);
        setScore("§7Balance:", 11);
        setScore("§e§c" + sPlayer.getBalance() + " Shrooms", 10);
        setScore("§c§d", 9);
        setScore("§7Team:", 8);
        setScore("§c" + (team != null ? team.getBalance() : 0) + " Shrooms", 7);
        setScore("§c§e", 6);
        setScore("§7Tower:", 5);
        setScore("§c" + (team != null ? (team.getTower() != null ? team.getTower().getProgress() : 0) : 0) + "%", 4);
        setScore("§c§a", 3);
        setScore("§7Kit:", 2);
        setScore("§c" + sPlayer.getKit(), 1);
        setScore("§f§7§m                          ", 0);
    }

    public static String shortInteger(int duration) {
        String string = "";

        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration -= duration / 60 / 60 * 60 * 60;
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration -= duration / 60 * 60;
        }
        if (duration >= 1)
            seconds = duration;

        if (minutes <= 9) {
            string = String.valueOf(string) + "0" + minutes + ":";
        } else {
            string = String.valueOf(string) + minutes + ":";
        }
        if (seconds <= 9) {
            string = String.valueOf(string) + "0" + seconds;
        } else {
            string = String.valueOf(string) + seconds;
        }
        return string;

    }
}
