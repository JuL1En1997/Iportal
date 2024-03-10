package me.minebuilders.portal.listeners;

import java.util.ArrayList;
import java.util.List;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.commands.BaseCmd;
import me.minebuilders.portal.commands.CreateCmd;
import me.minebuilders.portal.commands.DeleteCmd;
import me.minebuilders.portal.commands.ListCmd;
import me.minebuilders.portal.commands.RefreshCmd;
import me.minebuilders.portal.commands.SetToCmd;
import me.minebuilders.portal.commands.ToggleCmd;
import me.minebuilders.portal.commands.TpCmd;
import me.minebuilders.portal.commands.WandCmd;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {
    private List<BaseCmd> cmds = new ArrayList<BaseCmd>();

    public CommandListener() {
        cmds.add(new WandCmd());
        cmds.add(new CreateCmd());
        cmds.add(new SetToCmd());
        cmds.add(new RefreshCmd());
        cmds.add(new DeleteCmd());
        cmds.add(new ToggleCmd());
        cmds.add(new ListCmd());
        cmds.add(new TpCmd());
    }

    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (args.length == 0 || getCmdInstance(args[0]) == null) {

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // s.sendMessage(ChatColor.DARK_GRAY + "-------------(" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Your IPortal Commands" + ChatColor.DARK_GRAY + ")-------------");

            // New Code:
            s.sendMessage(IP.languageManager.getFormattedMessage("helplineheader"));

            for (BaseCmd cmd : cmds) {

                if (Util.hp(s, cmd.cmdName))
                    s.sendMessage(ChatColor.DARK_PURPLE + "  - " + cmd.sendHelpLine());
            }

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // s.sendMessage(ChatColor.DARK_GRAY + "---------------------------------------------------");

            // New Code:
              s.sendMessage(IP.languageManager.getFormattedMessage("helplinefooter"));
        } else {
            getCmdInstance(args[0]).processCmd(s, args);
        }

        return true;
    }

    private BaseCmd getCmdInstance(String s) {
        for (BaseCmd cmd : cmds) {
            if (cmd.cmdName.equalsIgnoreCase(s))
                return cmd;
        }
        return null;
    }
}
