package me.minebuilders.portal;

import me.minebuilders.portal.listeners.CommandListener;
import me.minebuilders.portal.listeners.PortalListener;
import me.minebuilders.portal.listeners.WandListener;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.storage.Data;
import me.minebuilders.portal.storage.LanguageManager;
import me.minebuilders.portal.tab.PortalTab;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class IP extends JavaPlugin {

    public List<Portal> portals = new ArrayList<Portal>();

    public HashMap<UUID, PlayerSession> playerses = new HashMap<UUID, PlayerSession>();

    public static IP instance;

    public static Data data;

    public static LanguageManager languageManager;

    public void onEnable() {
        instance = this;

        getLogger().info("  _____                      _          _ ");
        getLogger().info(" |_   _|                    | |        | |");
        getLogger().info("   | |   _ __    ___   _ __ | |_  __ _ | |");
        getLogger().info("   | |  | '_ \\  / _ \\ | '__|| __|/ _` || |");
        getLogger().info("  _| |_ | |_) || (_) || |   | |_| (_| || |");
        getLogger().info(" |_____|| .__/  \\___/ |_|    \\__|\\__,_||_|");
        getLogger().info("        | |                               ");
        getLogger().info("        |_|                               ");

        String version = getDescription().getVersion();
        List<String> authors = Arrays.asList("Original Author: bob7l"," Resource Manager: Spoopy"," Updated since V2.0: _JuL1En_");


        Bukkit.getConsoleSender().sendMessage("[Iportal] §eV" + version + "§a coded by §e" + authors + " §ais initializing...");

        data = new Data(this);

        // Added by _JuL1En_
        // Initializes the LanguageManager with the current plugin instance (`this`).
        // This allows the LanguageManager to access plugin resources and configurations,
        // enabling it to load and manage localized messages for multilingual support.
        languageManager = new LanguageManager(this);

        Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");

        // Added by _JuL1En_
        // Assigns a TabCompleter to the "iportal" command, enabling automatic completion of command arguments,
        // based on the current plugin instance for context-sensitive suggestions.
        getCommand("iportal").setTabCompleter((TabCompleter) new PortalTab(this));

        getServer().getPluginManager().registerEvents((Listener)new WandListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PortalListener(this), (Plugin)this);

        Bukkit.getConsoleSender().sendMessage("[Iportal] §aPlugin has been enabled");

    }

    public void onDisable() {
        instance = null;
        Bukkit.getConsoleSender().sendMessage("[Iportal] §cPlugin has been disabled");
    }

    public static Data getData() {
        return data;
    }
}
