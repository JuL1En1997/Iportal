package me.minebuilders.portal.portals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BungeePortal extends Portal {
    private ByteArrayOutputStream stream;

    public BungeePortal(String name, Bound region, Status status) {
        super(name, region, status);
    }

    public void Teleport(Player p) {
        p.sendPluginMessage((Plugin)IP.instance, "BungeeCord", stream.toByteArray());
    }

    public void setTarget(String s) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bao);

        try {
            out.writeUTF("Connect");
            out.writeUTF(s);

        } catch (IOException iOException) {}
        stream = bao;
    }

    public PortalType getType() {
        return PortalType.BUNGEE;
    }
}
