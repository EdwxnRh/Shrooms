package de.iwsc.shrooms.objects;

import de.iwsc.shrooms.utils.ScoreBoard;

public class SPlayer {

    private Team team;
    private int balance = 0;
    private ScoreBoard scoreBoard;

    public String getKit() {
        return "default";
    }

    public void addBalance() {
        addBalance(1);
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public Team getTeam() {
        return team;
    }

    public int getBalance() {
        return balance;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public boolean isInCombat() {
        return false;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}
