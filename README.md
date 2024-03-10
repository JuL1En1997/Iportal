# Iportal-Updated

IPortal-Updated is a plugin for Spigot, Paper, and other server types, offering an updated version of the original [IPortal](https://www.spigotmc.org/resources/iportal-transportation-bungeecord-portals.29552/) plugin. It facilitates easy world-to-world or server-to-server transports. With support for custom portals and BungeeCord, it's ideal for server admins and players alike. Designed for efficient movement, IPortal extends gameplay boundaries and encourages community interaction.

## Features

- **Portal Creation and Configuration**: Easily create and customize portals anywhere with simple commands.
- **Integration with Multiverse**: Create portals that seamlessly connect different worlds within your server.
- **Customizable Sounds**: Personalize your portals' sound effects to create an immersive experience.
- **Comprehensive Permission Control**: Detailed permission settings to manage access to portal functions.
- **BungeeCord Support**: Enables players to travel between servers within your network through portals.
- **Adjustable Portal Permissions**: Restrict access to certain portals based on player permissions.
- **Multilingual Support**: Localized messages for different player groups.

## Requirements

- Minecraft server (Bukkit, Spigot, Paper, etc.) version 1.8.8 or newer.
- Basic knowledge of server administration and configuration.

## Installation

1. Download the latest version of the Iportal plugin from [GitHub Releases](#).
2. Copy the downloaded `.jar` file into the `plugins` folder of your Minecraft server.
3. Restart your server or reload the plugins to complete the installation.
4. Check the console for messages confirming that the Iportal plugin has been successfully loaded.

## Configuration

### Language Settings

The "Iportal-Updated" plugin supports mulit languages through `.properties` files, allowing for easy customization of messages displayed in-game. By default, the plugin uses the `messages.properties` file. To change the language, you can copy the contents of a specific language file (e.g., `messages_de.properties` for German) into the `messages.properties` file.

#### How to change the language:

1. **Find the Language File**: Go to the `lang` folder in the Iportal plugin directory, where you'll find various `.properties` files for supported languages (e.g., `messages_de.properties`, `messages_en.properties`).
2. **Copy the Contents**: Open the file for your desired language (e.g., `messages_de.properties`) and copy all its contents.
3. **Replace the Default**: Open the `messages.properties` file and replace its contents with what you've copied from the chosen language file.
4. **Restart the Server**: Save the changes and restart your Minecraft server to apply the language change.

## Commands & Permissions

- `/iportal wand`: Toggles the portal creation wand.
  - Permission: `iportal.wand`
- `/iportal create <portalname> <portaltype default, bungee, random> <soundtype>`: Creates a new portal.
  - Permission: `iportal.create`
- `/iportal setto <portalname> <extra>`: Sets the teleport coordinates for the specified portal.
  - Permission: `iportal.setto`
- `/iportal refresh <portalname>`: Refreshes the specified portal.
  - Permission: `iportal.refresh`
- `/iportal delete <portalame>`: Deletes an existing portal.
  - Permission: `iportal.delete`
- `/iportal toggle <portalame>`: Toggles the specified portal on and off.
  - Permission: `iportal.toggle`
- `/iportal list`: Lists all portals and their statuses.
  - Permission: `iportal.list`
- `/iportal tp <portalame>`: Teleports the player to the coordinates set for the specified portal.
  - Permission: `iportal.tp`

## Support

For questions or suggestions, feel free to contact us via:

- Email: [julien.mc1997@gmail.com](mailto:julien.mc1997@gmail.com)
- Discord: _jul1en_#0832

For issues, errors, or bugs, feel free to create an [issue](https://github.com/JuL1En1997/Iportal/issues/new) in the GitHub Repository.

## Contributors

- Original Author: bob7l
- Resource Manager: Spoopy
- Updated since V2.0: _JuL1En_

## License

The Iportal-Updated plugin is released under the [GPL-3.0](LICENSE). See the `LICENSE` file in the repository for full license information.
