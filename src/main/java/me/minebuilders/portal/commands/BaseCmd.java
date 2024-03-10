package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCmd {
    public CommandSender sender;

    public String[] args;

    public String cmdName;

    public int argLength = 0;

    public boolean forcePlayer = true;

    public String usage = "";

    public Player player;

    public boolean processCmd(CommandSender s, String[] arg) {
        this.sender = s;
        this.args = arg;
        if (forcePlayer) {
            if (!(s instanceof Player))
                return false;
            player = (Player)s;
        }
        if (!Util.hp(sender, cmdName)) {

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // sender.sendMessage(ChatColor.RED + "You do not have permission to use: " + ChatColor.WHITE + "/iportal " + cmdName);

            // New Code:
            sender.sendMessage(IP.languageManager.getFormattedMessage("nopermission", cmdName));

        } else if (argLength > arg.length) {

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // s.sendMessage(ChatColor.RED + "Wrong usage: " + sendHelpLine());

            // New Code:
            s.sendMessage(IP.languageManager.getFormattedMessage("wrongusage", sendHelpLine()));

        } else {
            return run();
        }
        return true;
    }

    public abstract boolean run();

    public String sendHelpLine() {
        return ChatColor.DARK_PURPLE + "/iportal " + ChatColor.LIGHT_PURPLE + cmdName + ChatColor.DARK_PURPLE + " " + usage;
    }
}
