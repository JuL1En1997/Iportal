package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import org.bukkit.Location;
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

                // Change implemented by _JuL1En_ for improved language support
                // Old Code:
                // Util.msg((CommandSender) player, "&aAttempting to remove " + name + "!");

                // New Code:
                  Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("trydeleteportal", name));

                // Change implemented by _JuL1En_ for improved compatibility across Minecraft versions
                // Old Code:
                // for (Location l : p.getBound().getBlocks(Material.NETHER_PORTAL)) {
                //     l.getBlock().breakNaturally();
                // }

                // New Code:

                for (Location l : p.getBound().getBlocks(Util.getPortalMaterial())) {
                    l.getBlock().breakNaturally();
                }


                IP.data.getConfig().set("portals." + name, null);
                IP.data.save();

                // Change implemented by _JuL1En_ for improved language support
                // Old Code:
                // Util.msg((CommandSender) player, "&a" + name + " has been deleted!");

                // New Code:
                Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("deleteportal", name));
            }
        }
        if (por == null) {

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // Util.msg((CommandSender) player, "&c" + name + " is not a valid portal!");

            // New Code:
            Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("invaildportal", name));

        } else {
            plugin.portals.remove(por);
        }
        return true;
    }
}
