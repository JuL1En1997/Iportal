package me.minebuilders.portal.commands;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.PlayerSession;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;
import me.minebuilders.portal.tasks.PortalCreation;
import me.minebuilders.portal.tasks.PortalCreationLegacy;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CreateCmd extends BaseCmd {


    public CreateCmd() {
        this.forcePlayer = true;
        this.cmdName = "create";

        // Change implemented by _JuL1En_ for improved functionality
        // Old Code:
        // this.argLength = 3;

        // New Code:
        this.argLength = 4;

        // Change implemented by _JuL1En_ for improved functionality
        // Old Code:
        // this.usage = "<name> <portal-type>";

        // New Code:
        this.usage = "<portalname> <portal-type> <sound-type>";
    }

    public boolean run() {
        IP plugin = IP.instance;
        if (!plugin.playerses.containsKey(player.getUniqueId())) {

            // Change implemented by _JuL1En_ for improved language support
            // Old Code:
            // Util.msg((CommandSender) player, ChatColor.RED + "You need to make a selection before making a portal!");

            // New Code:
            Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("noselection"));

        } else {
            PlayerSession st = (PlayerSession) plugin.playerses.get(player.getUniqueId());
            if (!st.hasValidSelection()) {

                // Change implemented by _JuL1En_ for improved language support
                // Old Code:
                // Util.msg((CommandSender) player, ChatColor.RED + "You need to make a selection before making a portal!");

                // New Code:
                Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("noselection"));

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

                    // Add implemented by _JuL1En_ for improved functionality
                    // Sets the permission for the newly created portal to "none".
                    // This indicates that, by default, no special permissions are required to use the portal.
                    fileConfiguration.set("portals." + s + ".permission", "none");


                } catch (NullPointerException e1) {
                    // If a NullPointerException occurs (e.g., if getSound returns null and
                    // valueOf is called on it), the sound in the configuration is set to "NONE".
                    fileConfiguration.set("portals." + s + ".sound", "NONE");
                    fileConfiguration.set("portals." + s + ".permission", "none");

                } finally {

                    // The following code section was pre-existing in the class and has been incorporated as is, without any modifications by _JuL1En_:
                    IP.data.save();
                    Bound b = new Bound(player.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), l2.getBlockX(), l2.getBlockY(), l2.getBlockZ());
                    try {
                        plugin.portals.add((Portal) type.getPortal().newInstance(new Object[]{s, b, Status.NOT_READY}));

                        // Change implemented by _JuL1En_ for reducing lagg
                        // Old Code:
                        // new PortalCreation(b).run();

                        // New Code:

                        try {
                            Class.forName("org.bukkit.block.data.BlockData");
                            new PortalCreation(b).run();
                        } catch (ClassNotFoundException e) {
                            new PortalCreationLegacy(b).run();
                        }

                    // The following code section was pre-existing in the class and has been incorporated as is, without any modifications by _JuL1En_:
                    } catch (Exception e) {

                        // Change implemented by _JuL1En_ for improved language support
                        // Old Code:
                        // Util.msg((CommandSender) player, "&cFailed to add portal to local list!");

                        // New Code:
                        Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("failedcreateportal"));

                        return true;
                    }

                    // Change implemented by _JuL1En_ for improved language support
                    // Old Code:
                    // Util.msg((CommandSender) player, "You created portal " + s + "!");

                    // New Code:
                    Util.msg((CommandSender) player, IP.languageManager.getFormattedMessage("createportal", s));

                }

                // End of code section modified or developed by _JuL1En_

            }
        }
        return true;
    }
}