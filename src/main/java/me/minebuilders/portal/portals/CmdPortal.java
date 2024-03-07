package me.minebuilders.portal.portals;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.Status;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPortal extends Portal {
    private String[] commands;

    public CmdPortal(String name, Bound region, Status status) {
        super(name, region, status);
    }

    public void Teleport(Player p) {
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = this.commands).length, b = 0; b < i; ) {
            String s = arrayOfString[b];
            Bukkit.getServer().dispatchCommand((CommandSender)p, s);
            b++;
        }
    }

    public void setTarget(String s) {
        this.commands = s.split(",");
    }

    public PortalType getType() {
        return PortalType.CMD;
    }
}
