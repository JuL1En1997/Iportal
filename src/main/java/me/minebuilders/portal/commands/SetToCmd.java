package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SetToCmd extends BaseCmd {

    public SetToCmd() {
        this.forcePlayer = true;
        this.cmdName = "setto";
        this.argLength = 2;
        this.usage = "<name> <extra>";
    }

    public boolean run() {

        IP plugin = IP.instance;

        String name = args[1];

        for (Portal p : plugin.portals) {

            if (p.getName().equalsIgnoreCase(name)) {

                PortalType type = p.getType();
                String data = "";

                if (type == PortalType.BUNGEE && args.length >= 3) {
                    data = args[2];

                } else if (type == PortalType.CMD && args.length >= 3) {
                    data = Util.trim(args, 1);

                } else if (type == PortalType.DEFAULT) {
                    data = IP.data.compressLoc(player.getLocation());

                } else if (type == PortalType.RANDOM && args.length >= 3) {

                    if (Util.isInt(args[2]))
                        data = args[2];

                } else {
                    player.sendMessage(ChatColor.RED + "Wrong arguments for this portal type!");
                    return false;
                }

                p.setTarget(data);
                IP.data.getConfig().set("portals." + name + ".tpto", data);
                IP.data.save();
                Util.msg((CommandSender)player, "&a" + name + "'s target has been set!");
                p.setStatus(Status.RUNNING);
                return true;
            }
        }

        Util.msg((CommandSender)player, "&c" + name + " is not a valid portal!");
        return true;
    }
}
