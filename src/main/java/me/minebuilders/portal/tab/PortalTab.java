package me.minebuilders.portal.tab;

import me.minebuilders.portal.IP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PortalTab implements TabCompleter {

    // Begin of code section modified or developed by _JuL1En_


    private IP plugin;

    public PortalTab(IP instance) {
        this.plugin = instance;
    }


    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String label, String[] args) {

        if(!(s instanceof Player)) {
            return null;
        }

        Player p = (Player) s;

        ArrayList<String> np = new ArrayList<>();
        ArrayList <String> args1 = new ArrayList<>();
        ArrayList <String> args2 = new ArrayList<>();
        ArrayList <String> args3 = new ArrayList<>();


            if(args.length == 1) {

                if(p.hasPermission("iportal.wand")) {
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

                return args1;

            } else if (args.length == 2) {

                if(args[0].equalsIgnoreCase("delete") ||
                        (args[0].equalsIgnoreCase("toggle")) ||
                        (args[0].equalsIgnoreCase("setto")) ||
                        (args[0].equalsIgnoreCase("tp"))) {

                    if(p.hasPermission("iportal.delete")) {
                        List<String> portalNames = plugin.getData().getPortalNames();
                        args2.addAll(portalNames);

                    }

                    if(p.hasPermission("iportal.toggle")) {
                        List<String> portalNames = plugin.getData().getPortalNames();
                        args2.addAll(portalNames);

                    }

                    if(p.hasPermission("iportal.setto")) {
                        List<String> portalNames = plugin.getData().getPortalNames();
                        args2.addAll(portalNames);

                    }

                    if(p.hasPermission("iportal.tp")) {
                        List<String> portalNames = plugin.getData().getPortalNames();
                        args2.addAll(portalNames);

                    }

                }
                return args2;

            } else if (args.length == 3) {

                if((args[0].equalsIgnoreCase("create"))) {
                    args3.add("DEFAULT");
                    args3.add("BUNGEE");
                    args3.add("RANDOM");
                }

                return args3;
            }

        return np;
    }

}

// End of code section modified or developed by _JuL1En_
