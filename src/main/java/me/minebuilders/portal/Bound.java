package me.minebuilders.portal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import java.util.ArrayList;

public class Bound {

    private int x;
    private int y;
    private int z;
    private int x2;
    private int y2;
    private int z2;
    private World world;
    private org.bukkit.util.Vector v1;
    private org.bukkit.util.Vector v2;

    public Bound(final String world, final int x, final int y, final int z, final int x2, final int y2, final int z2) {
        this.world = Bukkit.getWorld(world);
        this.x = Math.min(x, x2);
        this.y = Math.min(y, y2);
        this.z = Math.min(z, z2);
        this.x2 = Math.max(x, x2);
        this.y2 = Math.max(y, y2);
        this.z2 = Math.max(z, z2);
        this.v1 = new Location(this.world, (double)this.x, (double)this.y, (double)this.z).toVector();
        this.v2 = new Location(this.world, (double)this.x2, (double)this.y2, (double)this.z2).toVector();
    }

    public boolean isInRegion(final Vector l) {
        return l.isInAABB(this.v1, this.v2);

    }

    public ArrayList<Location> getBlocks(final Material type) {

        final ArrayList<Location> array = new ArrayList<Location>();

        for (int x3 = this.x; x3 <= this.x2; ++x3) {
            for (int y3 = y; y3 <= y2; ++y3) {
                for (int z3 = z; z3 <= z2; ++z3) {
                    Block b = world.getBlockAt(x3, y3, z3);
                    if (b.getType() == type) {
                        array.add(b.getLocation());
                    }
                }
            }
        }
        return array;
    }

}
