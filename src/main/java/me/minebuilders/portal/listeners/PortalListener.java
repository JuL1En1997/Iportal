package me.minebuilders.portal.listeners;

import java.util.ArrayList;
import java.util.List;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class PortalListener implements Listener {
    private IP plugin;

    private BlockFace[] faces = new BlockFace[] { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH };

    private ArrayList<String> delays = new ArrayList<String>();

    private List<String> whitelist = new ArrayList<String>();

    public PortalListener(IP instance) {
        this.plugin = instance;
        this.delays.clear();
    }

    @EventHandler
    public void onTeleport(PlayerPortalEvent event) {
        Player p = event.getPlayer();

        // Change implemented by _JuL1En_ for improved compatibility across Minecraft versions
        // Old Code:
        // Vector l = getNearLoc(Material.NETHER_PORTAL, p.getLocation()).toVector();

        // New Code:
        Vector l = getNearLoc(Util.getPortalMaterial(), p.getLocation()).toVector();

        for (Portal portal : plugin.portals) {
            if (portal.isInRegion(l))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTeleport(EntityPortalEnterEvent event) {

        Entity e = event.getEntity();

        if (e instanceof Player) {

            Player p = (Player)e;

            // Change implemented by _JuL1En_ for improved compatibility across Minecraft versions
            // Old Code:
            // Vector l = getNearLoc(Material.valueOf("NETHER_PORTAL"), p.getLocation()).toVector();

            // New Code:
            Vector l = getNearLoc(Util.getPortalMaterial(), p.getLocation()).toVector();

            for (Portal portal : this.plugin.portals) {
                if (portal.isInRegion(l) &&
                        !delays.contains(p.getName()) &&
                        !whitelist.contains(p.getName())) {


                        if (portal.getStatus() == Status.RUNNING) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable() {
                            public void run() {

                                if("none".equalsIgnoreCase(IP.data.getConfig().getString("portals." + portal.getName() + ".permission"))) {
                                    portal.Teleport(p);

                                    // Added by _JuL1En_ to enable functionality
                                    // for playing a specific sound when teleporting through the portal.
                                    IP.data.playSound(p, portal);

                                } else if(p.hasPermission("iportal.use." + portal.getName())) {
                                    portal.Teleport(p);

                                    // Added by _JuL1En_ to enable functionality
                                    // for playing a specific sound when teleporting through the portal.
                                    IP.data.playSound(p, portal);

                                } else {
                                    Vector direction = p.getLocation().getDirection().multiply(-1).setY(0.5);
                                    p.setVelocity(direction);

                                    Util.msg((CommandSender)p, IP.languageManager.getFormattedMessage("nopermission"));
                                }
                                
                            }
                        },  5L);

                    } else {

                        // Change implemented by _JuL1En_ for improved language support
                        // Old Code:
                        // Util.msg((CommandSender)p, "&cSorry, this portal is currently " + portal.getStatus().getName() + "&c!");

                        // New Code:
                        Util.msg((CommandSender)p, IP.languageManager.getFormattedMessage("portalstatus", portal.getStatus().getName()));

                    }
                    delay(p.getName());
                }
            }
        }
    }

    private void delay(String s) {

        delays.add(s);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable() {
            public void run() {
                PortalListener.this.delays.remove(s);
            }
        },  30L);
    }

    private void whitelist(final String s) {
        whitelist.add(s);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable() {
            public void run() {
                PortalListener.this.whitelist.remove(s);
            }
        },  100L);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerjoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Block b = p.getWorld().getBlockAt(p.getLocation());
        whitelist(p.getName());
        byte b1;
        int i;
        BlockFace[] arrayOfBlockFace;
        for (i = (arrayOfBlockFace = faces).length, b1 = 0; b1 < i; ) {
            BlockFace face = arrayOfBlockFace[b1];

            // Change implemented by _JuL1En_ for improved compatibility across Minecraft versions
            // Old Code:
            // if (b.getRelative(face).getType().equals(Material.NETHER_PORTAL))

            // New Code:
            if (b.getRelative(face).getType().equals(Util.getPortalMaterial()))

                p.teleport(getNearLoc(Material.AIR, b.getLocation()));
            b1++;
        }
    }

    private Location getNearLoc(Material mat, Location l) {
        Block b = l.getBlock();
        byte b1;
        int i;
        BlockFace[] arrayOfBlockFace;
        for (i = (arrayOfBlockFace = faces).length, b1 = 0; b1 < i; ) {
            BlockFace bf = arrayOfBlockFace[b1];
            Block rel = b.getRelative(bf);
            if (rel.getType() == mat)
                return rel.getLocation();
            b1++;
        }
        return l;
    }
}
