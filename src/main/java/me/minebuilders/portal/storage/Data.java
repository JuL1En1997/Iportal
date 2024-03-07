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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
            Util.log("New portals.yml file successfully created!");
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
                Util.log("portals.yml file successfully Loaded!");
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

    public List<String> getPortalNames() {
        if (this.config.isConfigurationSection("portals")) {
            return new ArrayList<>(this.config.getConfigurationSection("portals").getKeys(false));
        }
        return new ArrayList<>(); // Leere Liste, falls keine Portale vorhanden sind
    }

}
