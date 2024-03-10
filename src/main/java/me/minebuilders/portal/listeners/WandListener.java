package me.minebuilders.portal.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.PlayerSession;
import me.minebuilders.portal.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class WandListener implements Listener {
    private IP plugin;

    private List<UUID> cd;

    public WandListener(IP instance) {
        this.cd = new ArrayList<UUID>();
        this.plugin = instance;
    }

    public void cooldown(final UUID uuid) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)plugin, new Runnable() {
            public void run() {
                WandListener.this.cd.remove(uuid);
            }
        },  10L);
    }

    @EventHandler
    public void onSelection(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack i = p.getItemInHand();
            if (i.getType() == Material.BLAZE_ROD &&
                    i.getItemMeta().hasDisplayName() &&
                    i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "iPortal Wand") &&
                    !cd.contains(p.getUniqueId()) &&
                    plugin.playerses.containsKey(p.getUniqueId())) {
                Location l = event.getClickedBlock().getLocation();
                PlayerSession ses = (PlayerSession)plugin.playerses.get(p.getUniqueId());
                ses.setLoc2(l);
                Util.msg((CommandSender)p, "Pos2: " + l.getX() + ", " + l.getY() + ", " + l.getZ());
                if (!ses.hasValidSelection())

                    // Change implemented by _JuL1En_ for improved language support
                    // Old Code:
                    // Util.msg((CommandSender)p, "Now you need to set position 1!");

                    // New Code:
                    Util.msg((CommandSender)p, IP.languageManager.getFormattedMessage("pos2info"));

                event.setCancelled(true);
                cd.add(p.getUniqueId());
                cooldown(p.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onSelection(BlockBreakEvent event) {
        Player p = event.getPlayer();
        ItemStack i = p.getItemInHand();
        if (i.getType() == Material.BLAZE_ROD &&
                i.getItemMeta().hasDisplayName() &&
                i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "iPortal Wand") &&
                plugin.playerses.containsKey(p.getUniqueId())) {
            Location l = event.getBlock().getLocation();
            PlayerSession ses = (PlayerSession)plugin.playerses.get(p.getUniqueId());
            ses.setLoc1(l);
            Util.msg((CommandSender)p, "Pos1: " + l.getX() + ", " + l.getY() + ", " + l.getZ());
            if (!ses.hasValidSelection())

                // Change implemented by _JuL1En_ for improved language support
                // Old Code:
                // Util.msg((CommandSender)p, "Now you need to set position 2!");

                // New Code:
                Util.msg((CommandSender)p, IP.languageManager.getFormattedMessage("pos1info"));

            event.setCancelled(true);
        }
    }
}
