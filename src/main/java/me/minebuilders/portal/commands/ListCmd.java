package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

public class ListCmd extends BaseCmd {


    public ListCmd() {
        this.forcePlayer = false;
        this.cmdName = "list";
        this.argLength = 1;
    }

    public boolean run() {
        IP plugin = IP.instance;
        Util.msg( sender, "&dPortals:");
        for (Portal p : plugin.portals)
            Util.msg( sender, "&5 - &d" + p.getName() + "  Status: " + p.getStatus().getName());
        return true;
    }
}