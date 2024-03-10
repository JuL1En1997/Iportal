package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import org.bukkit.command.CommandSender;

public class RefreshCmd extends BaseCmd {

    public RefreshCmd() {
        this.forcePlayer = true;
        this.cmdName = "refresh";
        this.argLength = 2;

        // Change implemented by _JuL1En_ for improved functionality
        // Old Code:
        // this.usage = "<name>";

        // New Code:
        this.usage = "<portalname>";
    }

    public boolean run() {
        IP plugin = IP.instance;
        String name = args[1];

        for (Portal p : plugin.portals) {
            if (p.getName().equalsIgnoreCase(name)) {
                p.refresh();

                // Change implemented by _JuL1En_ for improved language support
                // Old Code:
                // Util.msg((CommandSender) player, "&a" + name + "'s has been refreshed!");

                // New Code:
                Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("refreshportal", name));

                p.setStatus(Status.RUNNING);
                return true;
            }
        }

        // Change implemented by _JuL1En_ for improved language support
        // Old Code:
        // Util.msg((CommandSender) player, "&c" + name + " is not a valid portal!");

        // New Code:
        Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("invaildportal", name));

        return true;
    }
}
