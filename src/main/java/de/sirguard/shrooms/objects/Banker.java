package de.sirguard.shrooms.objects;

import org.bukkit.Location;

public class Banker {

    private Location location;

    public Banker(Location location) {
        this.location = location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
