package me.minebuilders.portal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.logging.Logger;

public class Util {

    private static final Logger log;

    static {
        log = Logger.getLogger("Minecraft");
    }

    public static void log(final String s) {
        // Change implemented by _JuL1En_ for improved
        // Old Code:
        // Util.log.info("[iPortal] " + s);

        // New Code:
        Util.log.info("[IPortal] " + s);
    }

    public static void warning(final String s) {
        // Change implemented by _JuL1En_ for improved
        // Old Code:
        // Util.log.warning("[iPortal] " + s);

        // New Code:
        Util.log.warning("[IPortal] " + s);
    }

    public static boolean hp(final CommandSender sender, final String s) {
        return sender.hasPermission("iportal." + s);
    }

    public static ItemStack getWand() {
        final ItemStack i = new ItemStack(Material.BLAZE_ROD);
        final ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.LIGHT_PURPLE + "iPortal Wand");
        i.setItemMeta(im);
        return i;
    }

    public static void msg(final CommandSender sender, final String s) {
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + "iPortal" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void scm(final CommandSender sender, final String s) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void broadcast(final String s) {
        Bukkit.getServer().broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + "iPortal" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', s));
    }

    public static boolean isInt(final String str) {
        try {
            Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String trim(final String[] args, final int minArgs) {
        String message = "";
        final int maxArgs = 99;
        for (int i = 0; i < args.length; ++i) {
            if (i > minArgs && i < maxArgs) {
                message = String.valueOf(message) + args[i] + " ";
            }
        }
        return message;
    }

    // Begin of code section modified or developed by _JuL1En_

    /**
     * Retrieves the appropriate Material enum for portal blocks, ensuring compatibility across different Minecraft versions.
     * This method primarily attempts to return the material for a Nether portal block, accommodating newer game versions.
     * If the NETHER_PORTAL material is not recognized (indicating an older game version), it defaults to the PORTAL material.
     *
     * @return Material for the portal block applicable to the current Minecraft version.
     */

    public static Material getPortalMaterial() {
        // This method attempts to return the Material enum value for a Nether portal block.
        // It tries to return the NETHER_PORTAL material, which is used in newer versions of Minecraft.
        try {
            return Material.valueOf("NETHER_PORTAL");

        } catch (IllegalArgumentException e) {
            // If NETHER_PORTAL is not found (likely in older Minecraft versions),
            // it falls back to returning the PORTAL material, which was used in earlier versions.
            return Material.valueOf("PORTAL");
        }
    }
    // End of code section modified or developed by _JuL1En_
}
