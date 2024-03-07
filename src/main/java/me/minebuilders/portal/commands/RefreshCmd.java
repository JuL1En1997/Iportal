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
    }

    public boolean run() {
        IP plugin = IP.instance;
        String name = args[1];

        for (Portal p : plugin.portals) {
            if (p.getName().equalsIgnoreCase(name)) {
                p.refresh();
                Util.msg((CommandSender) player, "&a" + name + "'s has been refreshed!");
                p.setStatus(Status.RUNNING);
                return true;
            }
        }
        Util.msg((CommandSender) player, "&c" + name + " is not a valid portal!");
        return true;
    }
}
