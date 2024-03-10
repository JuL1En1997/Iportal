// Begin of code section modified or developed by _JuL1En_

/**
 * LanguageManager for the IPortal Plugin
 *
 * This class manages language-specific resources, enabling the plugin to support multiple languages.
 * It loads localized messages from properties files within the 'lang' directory of the plugin's data folder,
 * allowing for dynamic language switching and enhancing the plugin's accessibility for users worldwide.
 *
 * Implemented by _JuL1En_ to provide improved multilingual support, this manager handles the detection and
 * loading of the appropriate language file based on server or player preferences. It also supports the
 * dynamic creation of language files from the plugin's resources, ensuring that all users receive messages
 * in their preferred language.
 *
 * Features:
 * - Multi-language support through localized messages.properties files.
 * - Dynamic language file creation if not present in the 'lang' directory.
 * - Console notifications for file creation and loading processes.
 * - Formatting and color-coding support for messages to enhance readability.
 */

package me.minebuilders.portal.storage;

import me.minebuilders.portal.IP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LanguageManager {

    private ResourceBundle messages; // Declares a variable for the resource bundle that contains the language messages.

    private IP plugin; // Declares a variable for the main plugin object.

    // Constructor that is called when an instance of this class is created.
    public LanguageManager(IP plugin) {
        // Creates a new 'lang' folder in the plugin's data directory if it does not exist.
        File langFolder = new File(plugin.getDataFolder(), "lang");
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }

        // List of language files to be created.
        List<String> files = Arrays.asList("messages.properties", "messages_de.properties", "messages_en.properties",
                "messages_es.properties", "messages_fr.properties", "messages_it.properties", "messages_nl.properties");

        // Loops through the list of language files.
        for (String fileName : files) {
            // Creates a File object for each language file in the 'lang' folder.
            File targetFile = new File(langFolder, fileName);

            // Checks if the file exists. If not, it is copied from the plugin resources.
            if (!targetFile.exists()) {
                try (InputStream in = plugin.getResource(fileName)) {
                    // If the file is found in the resources, it is copied to the 'lang' folder.
                    if(in != null) {
                        Files.copy(in, targetFile.toPath());
                        // Notifies in the console log about the successful creation of the file.
                        Bukkit.getConsoleSender().sendMessage("[IPortal] New §6" + targetFile.getName() + " §ffile successfully created!");
                    } else {
                        // Logs a warning if the file is not found in the resources.
                        plugin.getLogger().warning("Could not find resource: " + fileName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Sets the default language file (English) as the one to be loaded.
        String fileNameDefault = "messages.properties";
        File file = new File(langFolder, fileNameDefault);

        // Loads the properties file into the ResourceBundle object.
        try {
            this.messages = new PropertyResourceBundle(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            // Notifies in the console log about the successful loading of the default language file.
            Bukkit.getConsoleSender().sendMessage("[Iportal] §6messages.properties §ffile §asuccessfully loaded");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get formatted messages based on a key and optional arguments.
    public String getFormattedMessage(String key, Object... args) {

        // Retrieves the message based on the key.
        String message = messages.getString(key);

        // Formats the message with the provided arguments.
        String formattedMessage = MessageFormat.format(message, args);

        // Applies Minecraft color codes to the message and returns it.
        return ChatColor.translateAlternateColorCodes('&', formattedMessage);
    }
}

// End of code section modified or developed by _JuL1En_