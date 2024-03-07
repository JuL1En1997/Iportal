package me.minebuilders.portal.tasks;

import java.util.ArrayList;
import java.util.List;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.Plugin;

public class PortalCreation implements Runnable, Listener  {
    private int timerID;
    private Bound b;

    public PortalCreation(final Bound b) {
        this.b = b;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)IP.instance);
        this.timerID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)IP.instance, (Runnable)this, 100L);
        for (final Location l : b.getBlocks(Material.NETHER_PORTAL)) {
            l.getBlock().setType(Material.AIR);
        }
        final List<Integer> xValues = new ArrayList<Integer>();
        for (final Location i : b.getBlocks(Material.AIR)) {
            final Double dX = i.getX();
            xValues.add(dX.intValue());
        }
        boolean movesZ = false;
        final int amount = xValues.size();
        int correctCount = 0;
        for (final int xx : xValues) {
            if (xx == xValues.get(0)) {
                ++correctCount;
            }
        }
        if (correctCount == amount) {
            movesZ = true;
        }

         if (movesZ) {

            for (final Location j : b.getBlocks(Material.AIR)) {

                j.getBlock().setType(Material.NETHER_PORTAL);
                final Orientable or = (Orientable)j.getBlock().getBlockData();
                or.setAxis(Axis.Z);
                j.getBlock().setBlockData((BlockData)or);
            }

        } else {

            for (final Location j : b.getBlocks(Material.AIR)) {

                j.getBlock().setType(Material.NETHER_PORTAL);
                final Orientable or = (Orientable)j.getBlock().getBlockData();
                or.setAxis(Axis.X);
                j.getBlock().setBlockData((BlockData)or);
            }
        }
    }


    @Override
    public void run() {
        HandlerList.unregisterAll((Listener)this);
        Bukkit.getServer().getScheduler().cancelTask(this.timerID);
    }

    @EventHandler
    public void onPortalBreak(final BlockPhysicsEvent event) {
        if (event.getBlock().getType() == Material.NETHER_PORTAL && this.b.isInRegion(event.getBlock().getLocation().toVector())) {
            event.setCancelled(true);
        }
    }
}
