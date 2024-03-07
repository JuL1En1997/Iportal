package me.minebuilders.portal.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.Plugin;

public class PortalCreationLegacy implements Runnable, Listener {
    private int timerID;

    private Bound b;

    public PortalCreationLegacy(Bound b) {

        this.b = b;

        Bukkit.getPluginManager().registerEvents(this, (Plugin)IP.instance);

        this.timerID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)IP.instance, this, 100L);

        for (Location l : b.getBlocks(Util.getPortalMaterial()))
            l.getBlock().setType(Material.valueOf("AIR"));
        List<Integer> xValues = new ArrayList<Integer>();

        for (Location l : b.getBlocks(Material.valueOf("AIR"))) {
            Double dX = Double.valueOf(l.getX());
            xValues.add(Integer.valueOf(dX.intValue()));
        }

        boolean movesZ = false;
        int amount = xValues.size();
        int correctCount = 0;
        Iterator<Integer> iterator;

        for (iterator = xValues.iterator(); iterator.hasNext(); ) {
            int xx = ((Integer)iterator.next()).intValue();
            if (xx == ((Integer)xValues.get(0)).intValue())
                correctCount++;
        }

        if (correctCount == amount)
            movesZ = true;

        if (movesZ) {
            for (Location l : b.getBlocks(Material.valueOf("AIR"))) {
                l.getBlock().setType(Util.getPortalMaterial());
                l.getBlock().setData((byte)2);
            }

        } else {
            for (Location l : b.getBlocks(Material.valueOf("AIR")))
                l.getBlock().setType(Util.getPortalMaterial());
        }
    }

    public void run() {
        HandlerList.unregisterAll(this);
        Bukkit.getServer().getScheduler().cancelTask(this.timerID);
    }

    @EventHandler
    public void onPortalBreak(BlockPhysicsEvent event) {
        if (event.getBlock().getType() == Util.getPortalMaterial() && this.b.isInRegion(event.getBlock().getLocation().toVector()))
            event.setCancelled(true);
    }
}
