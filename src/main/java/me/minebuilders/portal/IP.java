package me.minebuilders.portal;

import me.minebuilders.portal.listeners.CommandListener;
import me.minebuilders.portal.listeners.PortalListener;
import me.minebuilders.portal.listeners.WandListener;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.storage.Data;
import me.minebuilders.portal.tab.PortalTab;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class IP extends JavaPlugin {

    public List<Portal> portals = new ArrayList<Portal>();

    public HashMap<UUID, PlayerSession> playerses = new HashMap<UUID, PlayerSession>();

    public static IP instance;

    public static Data data;

    public void onEnable() {
        instance = this;
        data = new Data(this);
        Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
        getCommand("iportal").setExecutor((CommandExecutor)new CommandListener());
        getCommand("iportal").setTabCompleter((TabCompleter) new PortalTab(this));
        getServer().getPluginManager().registerEvents((Listener)new WandListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PortalListener(this), (Plugin)this);
        Util.log("IPortal has been enabled!");
    }

    public void onDisable() {
        instance = null;
        Util.log("IPortal has been disabled!");
    }

    public static Data getData() {
        return data;
    }
}
