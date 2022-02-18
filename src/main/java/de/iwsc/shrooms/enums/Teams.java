package de.iwsc.shrooms.enums;

import org.bukkit.Color;

public enum Teams {

    // SORTED LIST FOR STANDARD TEAM COLOR
    AQUA("Aqua", "b", Color.AQUA),
    RED("Red", "c", Color.RED),
    GREEN("Green", "a", Color.LIME),
    YELLOW("Yellow", "e", Color.YELLOW),

    LIGHT_PURPLE("Light Purple", "d", Color.FUCHSIA),
    GOLD("Gold", "6", Color.ORANGE),
    BLUE("Blue", "9", Color.BLUE),
    DARK_AQUA("Dark Aqua", "3", Color.TEAL),

    DARK_GRAY("Dark Gray", "8", Color.GRAY),
    GRAY("Gray", "7", Color.SILVER),
    DARK_GREEN("Dark Green", "2", Color.GREEN),
    DARK_RED("Dark Red", "4", Color.MAROON),
    DARK_BLUE("Dark Blue", "1", Color.NAVY),
    DARK_PURPLE("Dark Purple", "5", Color.PURPLE),
    WHITE("White", "f", Color.WHITE),
    BLACK("Black", "0", Color.BLACK);


    private final String name;
    private final String color;
    private final Color leatherColor;

    Teams(String name, String color, Color leatherColor) {
        this.name = name;
        this.color = color;
        this.leatherColor = leatherColor;
    }

    public static boolean isPossible(String teamName) {
        for (Teams value : Teams.values()) {
            if (value.name().equals(teamName))
                return true;
        }
        return false;
    }

    public static Teams getTeamByEnumName(String teamName) {
        for (Teams value : Teams.values()) {
            if (value.name().equals(teamName))
                return value;
        }
        return Teams.DARK_GRAY;
    }

    public static Teams getTeamByDisplayName(String teamDisplayName) {
        for (Teams value : Teams.values()) {
            if (value.getName().equals(teamDisplayName))
                return value;
        }
        return Teams.DARK_GRAY;
    }

    public static String getColorOfTeam(String teamName) {
        for (Teams value : Teams.values()) {
            if (value.name().equals(teamName))
                return value.getColor();
        }
        return "7";
    }

    public String getTitle() {
        return "§" + color + name;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Color getLeatherColor() {
        return leatherColor;
    }
}
