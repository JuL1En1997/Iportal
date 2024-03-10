package me.minebuilders.portal.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Data {
    private File file = null;

    private FileConfiguration config = null;

    private IP plugin;

    public Data(IP plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {

        if (!this.plugin.getDataFolder().exists())
            this.plugin.getDataFolder().mkdir();

        this.file = new File(this.plugin.getDataFolder(), "portals.yml");
        if (!this.file.exists()) {

            try {
                this.file.createNewFile();

            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create portals.yml!");
            }

            this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
            save();

            // Change implemented by _JuL1En_ for improved
            // Old Code:
            // Util.log("New portals.yml file successfully created!");

            // New Code:
            Bukkit.getConsoleSender().sendMessage("[Iportal] New §6portals.yml §ffile §asuccessfully created!");

        } else {

            this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);

            if (this.config.getConfigurationSection("portals") == null) {

                Util.log("Configuration section 'portals' is not found, creating a new one.");
                this.config.createSection("portals");
                save();

            } else {

                for (String s : this.config.getConfigurationSection("portals").getKeys(false)) {

                    Status status = Status.RUNNING;
                    String exit = "";
                    Bound b = null;
                    PortalType type = PortalType.DEFAULT;

                    try {
                        type = getType(this.config.getString("portals." + s + ".type"));
                        exit = this.config.getString("portals." + s + ".tpto");

                    } catch (Exception e) {

                        Util.warning("Unable to load teleport location for portal " + s + "!");
                        status = Status.BROKEN;
                    }

                    try {
                        b = new Bound(this.config.getString("portals." + s + "." + "world"), BC(s, "x"), BC(s, "y"), BC(s, "z"), BC(s, "x2"), BC(s, "y2"), BC(s, "z2"));

                    } catch (Exception e) {

                        Util.warning("Unable to load region bounds for portal " + s + "!");
                        status = Status.BROKEN;
                    }

                    try {

                        Portal p = (Portal) type.getPortal().newInstance(new Object[] { s, b, status });

                        this.plugin.portals.add(p);
                        if (exit != null && !exit.equals("")) {
                            p.setTarget(exit);
                            p.refresh();

                        } else {
                            p.setStatus(Status.BROKEN);
                        }

                    } catch (Exception e) {

                        Util.warning("Unable to load portal " + s + "!");
                    }
                    Util.log(String.valueOf(s) + " portal successfully Loaded!");
                }

                // Change implemented by _JuL1En_ for improved
                // Old Code: Util.log("New portals.yml file successfully created!");
                //

                // New Code:
                Bukkit.getConsoleSender().sendMessage("[Iportal] §6portals.yml §ffile §asuccessfully Loaded! ");
            }
        }
    }

    public void save() {

        try {
            this.config.save(this.file);

        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save portals.yml!");
        }
    }

    public FileConfiguration getConfig() {

        if (this.config == null)
            reloadConfig();
        return this.config;
    }

    public void reloadConfig() {

        if (this.file == null)
            this.file = new File(this.plugin.getDataFolder(), "portals.yml");
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }

    public int BC(String s, String st) {
        return this.config.getInt("portals." + s + "." + st);
    }

    public String compressLoc(Location l) {
        return String.valueOf(l.getWorld().getName()) + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ() + ":" + l.getYaw() + ":" + l.getPitch();
    }

    public PortalType getType(String s) {

        byte b;
        int i;
        PortalType[] arrayOfPortalType;

        for (i = (arrayOfPortalType = PortalType.values()).length, b = 0; b < i; ) {
            PortalType t = arrayOfPortalType[b];
            if (s.equalsIgnoreCase(t.name()))

                return t;
            b++;
        }
        return PortalType.DEFAULT;
    }

    // Begin of code section modified or developed by _JuL1En_

    /**
     * Retrieves the names of portals based on a specific PortalType.
     * If the passed type is `null`, the names of all portals are returned.
     * This method provides a type-safe way to query portal names,
     * preventing the use of invalid types.
     *
     * @param type The PortalType for which to query the portal names.
     *             Can be `null` to get the names of all portals.
     * @return A list of portal names that match the specified type.
     *         An empty list is returned if no matching portals are found or
     *         if the 'portals' configuration section does not exist.
     */

    public List<String> getPortalNamesByType(PortalType type) {
        List<String> portals = new ArrayList<>();

        // Checks if the 'portals' configuration section exists
        if (!this.config.isConfigurationSection("portals")) {

            return portals; // Returns an empty list if no portals are configured
        }

        // If type is `null`, this corresponds to a request for the names of all portals
        if (type == null) {

            // Adds all keys (portal names) from the 'portals' section to the list
            portals.addAll(this.config.getConfigurationSection("portals").getKeys(false));

            return portals;
        }

        // Iterates over all defined portals, adding only the names of portals
        // that match the specified PortalType
        for (String key : this.config.getConfigurationSection("portals").getKeys(false)) {
            String configType = this.config.getString("portals." + key + ".type");
            if (type.name().equalsIgnoreCase(configType)) {
                portals.add(key);
            }
        }

        return portals; // Returns the list of portal names
    }



    /**
     * Retrieves the name of a sound that matches the given argument.
     * This method iterates through all available sounds and returns the name
     * of the sound that matches the given argument, ignoring case sensitivity.
     *
     * @param arg The name of the sound to look for.
     * @return The name of the matching sound, or `null` if no match is found.
     */

    public String getSound(String arg) {

        // Iterate through all available sounds
        for(Sound sound : Sound.values()) {
            // Check if the sound's name matches the given argument (case-insensitive)
            if(sound.name().equalsIgnoreCase(arg)) {
                return sound.name(); // Return the matching sound name
            }
        }

        return null; // Return null if no matching sound is found
    }


    /**
     * Plays a sound for a player based on the sound name associated with a portal.
     * This method retrieves the sound name from the portal configuration, validates it,
     * and then plays the sound at the player's location if it is valid and not set to "NONE".
     *
     * @param p The player for whom to play the sound.
     * @param portal The portal from which to retrieve the sound name.
     */

    public void playSound(Player p, Portal portal) {

        // Retrieve the sound name for the given portal from the configuration
        // Convert to uppercase and replace dots with underscores to match the enum naming convention
        String soundName = IP.data.getConfig().getString("portals." + portal.getName() + ".sound").toUpperCase().replace(".", "_");

        // Check if the sound name is valid (not null, not "NONE", and not empty)
        if(soundName != null && !soundName.equalsIgnoreCase("NONE") && !soundName.isEmpty()) {
            try {
                // Attempt to get the Sound enum value from the sound name
                Sound sound = Sound.valueOf(soundName);
                // Play the sound at the player's location with volume and pitch set to 1.0
                p.playSound(p.getLocation(), sound, 1.0F, 1.0F);

            } catch (IllegalArgumentException e) {
                // Catch the exception if the sound name is invalid (not in the Sound enum)
                // No action is taken if the sound name is invalid
            }
        }
    }

    // End of code section modified or developed by _JuL1En_

}
