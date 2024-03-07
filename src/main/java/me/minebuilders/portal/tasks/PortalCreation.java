package me.minebuilders.portal.tasks;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

        for (final Location l : b.getBlocks(Util.getPortalMaterial())) {
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

        for (final Location j : b.getBlocks(Material.valueOf("AIR"))) {
            j.getBlock().setType(Util.getPortalMaterial());

            // Begin of code section modified or developed by _JuL1En_
            // This code dynamically sets portal blocks within a specified area based on the 'movesZ' flag. It adjusts the orientation
            // of portal blocks to either the Z or X axis using reflection to ensure compatibility across different Minecraft versions.

            try {
                // Reflectively access the 'getBlockData' method of the block to obtain its BlockData
                Method getBlockDataMethod = Block.class.getMethod("getBlockData");
                Object blockData = getBlockDataMethod.invoke(j.getBlock());

                // Check if the obtained BlockData object is an instance of 'Orientable'
                Class<?> orientableClass = Class.forName("org.bukkit.block.data.Orientable");
                if (orientableClass.isInstance(blockData)) {

                    // Reflectively access the 'setAxis' method to set the orientation of the portal
                    Method setAxisMethod = orientableClass.getMethod("setAxis", Class.forName("org.bukkit.Axis"));

                    // Determine the axis orientation based on the 'movesZ' variable and set the appropriate axis
                    Object axisZ = Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Axis"), movesZ ? "Z" : "X");
                    setAxisMethod.invoke(blockData, axisZ);

                    // Update the BlockData of the block with the new orientation
                    Method setBlockDataMethod = Block.class.getMethod("setBlockData", Class.forName("org.bukkit.block.data.BlockData"));
                    setBlockDataMethod.invoke(j.getBlock(), blockData);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // End of code section modified or developed by _JuL1En_

        }
    }


    @Override
    public void run() {
        HandlerList.unregisterAll((Listener)this);
        Bukkit.getServer().getScheduler().cancelTask(this.timerID);
    }

    @EventHandler
    public void onPortalBreak(final BlockPhysicsEvent event) {
        if (event.getBlock().getType() == Util.getPortalMaterial() && this.b.isInRegion(event.getBlock().getLocation().toVector())) {
            event.setCancelled(true);
        }
    }
}