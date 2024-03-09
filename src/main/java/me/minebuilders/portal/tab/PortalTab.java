// Begin of code section modified or developed by _JuL1En_

/**
 * This class implements the TabCompleter interface to provide auto-completion for commands related to portal management.
 * It handles command suggestions based on the player's permissions and the current state of their command input.
 * The class is designed to work within a Minecraft plugin architecture, enhancing the user experience by suggesting relevant commands and parameters.
 *
 * Key functionalities include suggesting portal commands like creation, deletion, and teleportation, along with dynamic suggestions for portal types and specific attributes such as sound types.
 * The implementation takes into account different player permissions, ensuring that suggestions are contextually relevant and secure.
 *
 * This class is part of the broader portal management system within the plugin, facilitating intuitive and efficient interaction with portal-related commands.
 *
 * Modifications and enhancements within this class have been developed by _JuL1En_, focusing on improving functionality and compatibility across different versions of Minecraft.
 */

package me.minebuilders.portal.tab;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PortalTab implements TabCompleter {

    // Class variable for the main plugin instance
    private IP plugin;

    // Constructor: Initializes the class with an instance of the main plugin.
    public PortalTab(IP instance) {
        this.plugin = instance;
    }


    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String label, String[] args) {

        // Check if the command sender is a player
        if (!(s instanceof Player)) {
            return null; // Return null if the sender is not a player, disabling tab completion
        }

        // Cast the CommandSender to Player to use player-specific methods.
        Player p = (Player) s;

        ArrayList<String> np = new ArrayList<>(); // A general-purpose list, not used in the provided code snippet.
        ArrayList<String> args1 = new ArrayList<>(); // List to store suggestions for the first argument of the command.
        ArrayList<String> args2 = new ArrayList<>(); // List to store suggestions for the second argument of the command.
        ArrayList<String> args3 = new ArrayList<>(); // List to store suggestions for the third argument of the command.
        List<String> sounds = new ArrayList<>(); // List to store sound suggestions, likely used when creating or modifying portals.


        if (args.length == 1) {
            // Handling suggestions for the first argument in the command.

            String input = args[0].toLowerCase(); // Convert the first argument to lowercase for case-insensitive comparison.

            // Check the player's permissions for various portal-related commands and add them to args1 if they have permission.
            if (p.hasPermission("iportal.wand")) {
                args1.add("wand");

            }

            if (p.hasPermission("iportal.create")) {
                args1.add("create");

            }

            if (p.hasPermission("iportal.setto")) {
                args1.add("setto");

            }

            if (p.hasPermission("iportal.refresh")) {
                args1.add("refresh");

            }

            if (p.hasPermission("iportal.delete")) {
                args1.add("delete");

            }

            if (p.hasPermission("iportal.toggle")) {
                args1.add("toggle");

            }

            if (p.hasPermission("iportal.list")) {
                args1.add("list");

            }

            if (p.hasPermission("iportal.tp")) {
                args1.add("tp");

            }

            List<String> completions = new ArrayList<>(); // Initialize the list for storing matching suggestions.

            // Loop through the possible commands in args1 and add those that match the player's input to the completions list.
            for (String suggestion : args1) {
                if (suggestion.toLowerCase().startsWith(input)) {
                    completions.add(suggestion);
                }
            }


            return completions; // Return the list of matching suggestions.

        } else if (args.length == 2) {
            // Handling suggestions for the second argument in the command.

            String input = args[1].toLowerCase(); // Convert the second argument to lowercase for case-insensitive comparison.

            // Check if the first argument is one of the portal management commands that involve interacting with a specific portal.
            if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("toggle") ||
                    args[0].equalsIgnoreCase("setto") || args[0].equalsIgnoreCase("tp")) {

                // If the player has permission to delete portals, gather all portal names for the suggestion list.
                if (p.hasPermission("iportal.delete")) {
                    List<String> portalNames = plugin.getData().getPortalNamesByType(null); // Retrieves all portal names without filtering by type.
                    args2.addAll(portalNames);
                }

                // Similar checks and logic for toggle, setto, and tp commands, adding all portal names to args2 for suggestions.
                if (p.hasPermission("iportal.toggle")) {
                    List<String> portalNames = plugin.getData().getPortalNamesByType(null);
                    args2.addAll(portalNames);
                }

                if (p.hasPermission("iportal.setto")) {
                    List<String> portalNames = plugin.getData().getPortalNamesByType(null);
                    args2.addAll(portalNames);
                }

                if (p.hasPermission("iportal.tp")) {
                    List<String> portalNames = plugin.getData().getPortalNamesByType(null);
                    args2.addAll(portalNames);
                }

                List<String> completions = new ArrayList<>(); // Initialize the list for storing matching suggestions.

                // Loop through the portal names in args2 and add those that match the player's input to the completions list.
                for (String suggestion : args2) {
                    if (suggestion.toLowerCase().startsWith(input)) {
                        completions.add(suggestion);
                    }
                }

                return completions; // Return the list of matching suggestions.
            }
        } else if (args.length == 3) {
            // Handling suggestions for the third argument in the command.

            // If the command is "create" and the player has the necessary permission,
            // provide suggestions for the portal type (DEFAULT, BUNGEE, RANDOM).
            if (p.hasPermission("iportal.create")) {
                if ((args[0].equalsIgnoreCase("create"))) {
                    args3.add("DEFAULT");
                    args3.add("BUNGEE");
                    args3.add("RANDOM");

                    return args3; // Return the portal type suggestions

                }

            }

            // If the command is "setto" and the player has permission,
            // suggest specific options based on the type of the selected portal
            if(p.hasPermission("iportal.setto")) {
                if(args[0].equalsIgnoreCase("setto")) {

                    String selectedPortalName = args[1]; // The name of the portal being configured.

                    // If the selected portal is of type BUNGEE and no third argument is provided yet,
                    // suggest "servername" as an option.
                    if(IP.data.getPortalNamesByType(PortalType.BUNGEE).contains(selectedPortalName) && (args[2].isEmpty())) {
                        args3.add("servername");
                    }

                    // Similarly, for portals of type RANDOM, suggest "radius" as an option.
                    if(IP.data.getPortalNamesByType(PortalType.RANDOM).contains(selectedPortalName) && (args[2].isEmpty())) {
                        args3.add("radius");
                    }

                }
                return args3; // Return the specific suggestions for the "setto" command.
            }


        } else if (args.length == 4) {
            // Handling suggestions for the fourth argument in the command.

            // Check if the player is trying to use the "create" command and has the necessary permission.
            if (p.hasPermission("iportal.create")) {
                if (args[0].equalsIgnoreCase("create")) {


                    String soundPrefix = args[3].toLowerCase();

                    // Iterate through all available sounds in Minecraft.
                    for (Sound sound : Sound.values()) {
                        String soundName = sound.name().toLowerCase();

                        // Add "NONE" as an option and then any sound names that start with the typed prefix.
                        if (soundName.startsWith(soundPrefix)) {
                            sounds.add("NONE");
                            sounds.add(sound.name());
                        }
                    }
                }
                return sounds; // Return the list of matching sound suggestions
            }

        }
        return np; // Return an empty list if none of the conditions are met.
    }
}

// End of code section modified or developed by _JuL1En_
