package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

public class TpCmd extends BaseCmd {

    public TpCmd() {
        this.forcePlayer = true;
        this.cmdName = "tp";
        this.usage = "<name>";
        this.argLength = 2;
    }
    public boolean run() {

        IP plugin = IP.instance;
        String name = args[1];

        for (Portal p : plugin.portals) {

            if (p.getName().equalsIgnoreCase(name)) {

                if (p.getStatus() == Status.RUNNING) {
                    p.Teleport(player);

                } else {

                    // Change implemented by _JuL1En_ for improved language support
                    // Old Code:
                    // Util.msg(sender, "&c" + name + " is not running!!");

                    // New Code:
                    Util.msg(sender, IP.languageManager.getFormattedMessage("tpportalnotrunning", name));

                }
                return true;
            }
        }

        // Change implemented by _JuL1En_ for improved language support
        // Old Code:
        // Util.msg(sender, "&c" + name + " is not a valid portal!");

        // New Code:
        Util.msg(sender, IP.languageManager.getFormattedMessage("invaildportal", name));

        return true;
    }
}
