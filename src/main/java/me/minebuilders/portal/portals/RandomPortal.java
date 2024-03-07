package me.minebuilders.portal.portals;

import java.util.Random;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.Status;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RandomPortal extends Portal {
    private int random = 0;

    private Random rg = new Random();

    public RandomPortal(String name, Bound region, Status status) {
        super(name, region, status);
    }

    public void Teleport(Player p) {
        p.teleport(locRandom(p.getWorld()));
    }

    public void setTarget(String s) {
        this.random = Integer.parseInt(s);
    }

    public PortalType getType() {
        return PortalType.BUNGEE;
    }

    private Location locRandom(World world) {
        int x = this.rg.nextInt(this.random);
        int z = this.rg.nextInt(this.random);
        Location loc1 = new Location(world, x, 59.0D, z);
        if (loc1.getBlock().getType() == Material.WATER)
            return locRandom(world);
        return new Location(world, x, (world.getHighestBlockYAt(loc1) + 2), z);
    }
}
