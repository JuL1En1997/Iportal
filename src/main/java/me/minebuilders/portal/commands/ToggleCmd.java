package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

public class ToggleCmd extends BaseCmd {

    public ToggleCmd() {
        this.forcePlayer = false;
        this.cmdName = "toggle";

        // Change implemented by _JuL1En_ for improved functionality
        // Old Code:
        // this.usage = "<name>";

        // New Code:
        this.usage = "<portalname>";

        this.argLength = 2;
    }

    public boolean run() {

        IP plugin = IP.instance;
        String name = args[1];

        for (Portal p : plugin.portals) {

            if (p.getName().equalsIgnoreCase(name)) {

                if (p.getStatus() == Status.RUNNING) {

                    // Change implemented by _JuL1En_ for improved language support
                    // Old Code:
                    // Util.msg(sender, "&a" + name + " is now &cLOCKED&a!");

                    // New Code:
                    Util.msg(sender, IP.languageManager.getFormattedMessage("toggleportal", name));

                    p.setStatus(Status.NOT_READY);

                } else {

                    // Change implemented by _JuL1En_ for improved language support
                    // Old Code:
                    // Util.msg(sender, "&a" + name + " is now &2UNLOCKED&a!");

                    // New Code:
                    Util.msg(sender, IP.languageManager.getFormattedMessage("untoggleportal", name));

                    p.setStatus(Status.RUNNING);
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
