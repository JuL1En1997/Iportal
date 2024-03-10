package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.PlayerSession;
import me.minebuilders.portal.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class WandCmd extends BaseCmd {

    public WandCmd() {
        this.forcePlayer = true;
        this.cmdName = "wand";
        this.argLength = 1;
    }

    public boolean run() {

        IP plugin = IP.instance;

        if (plugin.playerses.containsKey(player.getUniqueId())) {
            plugin.playerses.remove(player.getUniqueId());

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // Util.msg((CommandSender)player, "Wand disabled!");

            // New Code:
            Util.msg((CommandSender)player, IP.languageManager.getFormattedMessage("wanddisabled"));

        } else {

            player.getInventory().addItem(new ItemStack[] { Util.getWand() });
            plugin.playerses.put(player.getUniqueId(), new PlayerSession(null, null));

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // Util.msg((CommandSender)player, "Wand enabled!");

            // New Code:
            Util.msg((CommandSender)player, IP.languageManager.getFormattedMessage("wandenabled"));

        }

        return true;
    }
}
