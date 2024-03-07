package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class DeleteCmd extends BaseCmd {


    public DeleteCmd() {
        this.forcePlayer = true;
        this.cmdName = "delete";
        this.argLength = 2;
        this.usage = "<name>";
    }

    public boolean run() {
        IP plugin = IP.instance;
        String name = args[1];
        Portal por = null;
        for (Portal p : plugin.portals) {
            if (p.getName().equalsIgnoreCase(name)) {
                por = p;
                Util.msg((CommandSender) player, "&aAttempting to remove " + name + "!");

                for (Location l : p.getBound().getBlocks(Material.NETHER_PORTAL)) {
                    l.getBlock().breakNaturally();
                }


                IP.data.getConfig().set("portals." + name, null);
                IP.data.save();
                Util.msg((CommandSender) player, "&a" + name + " has been deleted!");
            }
        }
        if (por == null) {
            Util.msg((CommandSender) player, "&c" + name + " is not a valid portal!");
        } else {
            plugin.portals.remove(por);
        }
        return true;
    }
}
