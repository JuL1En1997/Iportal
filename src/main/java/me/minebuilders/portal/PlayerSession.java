package me.minebuilders.portal;

import org.bukkit.Location;

public class PlayerSession {


    private Location loc1;
    private Location loc2;

    public PlayerSession(final Location l1, final Location l2) {
        this.setInfo(l1, l2);
    }

    public void setLoc1(final Location loc1) {
        this.loc1 = loc1;
    }

    public void setLoc2(final Location loc2) {
        this.loc2 = loc2;
    }

    public Location getLoc1() {
        return this.loc1;
    }

    public Location getLoc2() {
        return this.loc2;
    }

    public void setInfo(final Location l1, final Location l2) {
        this.setLoc1(l1);
        this.setLoc2(l2);
    }

    public boolean hasValidSelection() {
        return this.loc1 != null && this.loc2 != null;
    }

    public String getInvalidLoc() {
        if (this.loc1 == null) {
            return "Position 1";
        }
        return "Position 2";
    }

}
