package me.minebuilders.portal.commands;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.PlayerSession;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;
import me.minebuilders.portal.tasks.PortalCreation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CreateCmd extends BaseCmd {


    public CreateCmd() {
        this.forcePlayer = true;
        this.cmdName = "create";
        this.argLength = 4;

        // Change implemented by _JuL1En_ for improved functionality
        // Old Code:
        // this.usage = "<name> <portal-type>";

        // New Code:
        this.usage = "<name> <portal-type> <sound-type>";
    }

    public boolean run() {
        IP plugin = IP.instance;
        if (!plugin.playerses.containsKey(player.getUniqueId())) {
            Util.msg((CommandSender) player, ChatColor.RED + "You need to make a selection before making a portal!");
        } else {
            PlayerSession st = (PlayerSession) plugin.playerses.get(player.getUniqueId());
            if (!st.hasValidSelection()) {
                Util.msg((CommandSender) player, ChatColor.RED + "You need to make a selection before making a portal!");
            } else {
                PortalType type = IP.data.getType(args[2]);
                Location l = st.getLoc1();
                Location l2 = st.getLoc2();
                String s = args[1];
                FileConfiguration fileConfiguration = IP.data.getConfig();
                fileConfiguration.set("portals." + s + ".world", player.getWorld().getName());
                fileConfiguration.set("portals." + s + ".x", Double.valueOf(l.getX()));
                fileConfiguration.set("portals." + s + ".y", Double.valueOf(l.getY()));
                fileConfiguration.set("portals." + s + ".z", Double.valueOf(l.getZ()));
                fileConfiguration.set("portals." + s + ".x2", Double.valueOf(l2.getX()));
                fileConfiguration.set("portals." + s + ".y2", Double.valueOf(l2.getY()));
                fileConfiguration.set("portals." + s + ".z2", Double.valueOf(l2.getZ()));

                // Add implemented by _JuL1En_ for improved functionality
                fileConfiguration.set("portals." + s + ".type", type.name());

                // Begin of code section modified or developed by _JuL1En_

                try {
                    // Attempts to find the sound based on the third argument (args[3]).
                    // The method IP.data.getSound returns the name of the sound, or null if not found.
                    Sound sound = Sound.valueOf(IP.data.getSound(args[3]));

                    // Checks if a valid sound was found and sets it in the configuration.
                    // If the sound is "NONE" or empty, "NONE" is set as the default.
                    if (sound != null && !sound.name().contains("NONE") && !sound.name().isEmpty()) {
                        fileConfiguration.set("portals." + s + ".sound", sound.name());

                    } else {
                        fileConfiguration.set("portals." + s + ".sound", "NONE");
                    }

                } catch (NullPointerException e1) {
                    // If a NullPointerException occurs (e.g., if getSound returns null and
                    // valueOf is called on it), the sound in the configuration is set to "NONE".
                    fileConfiguration.set("portals." + s + ".sound", "NONE");

                } finally {

                    // The following code section was pre-existing in the class and has been incorporated as is, without any modifications by _JuL1En_:
                    IP.data.save();
                    Bound b = new Bound(player.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), l2.getBlockX(), l2.getBlockY(), l2.getBlockZ());
                    try {
                        plugin.portals.add((Portal) type.getPortal().newInstance(new Object[]{s, b, Status.NOT_READY}));
                        new PortalCreation(b).run();
                    } catch (Exception e) {
                        Util.msg((CommandSender) player, "&cFailed to add portal to local list!");
                        return true;
                    }
                    Util.msg((CommandSender) player, "You created portal " + s + "!");
                }

                // End of code section modified or developed by _JuL1En_

            }
        }
        return true;
    }
}