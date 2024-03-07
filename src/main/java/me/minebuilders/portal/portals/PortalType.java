package me.minebuilders.portal.portals;

import java.lang.reflect.Constructor;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.Status;

public enum PortalType {
    DEFAULT(Portal.class),
    BUNGEE(BungeePortal.class),
    RANDOM(RandomPortal.class),
    CMD(CmdPortal.class);

    private Class<?> con;

    PortalType(Class<?> con) {
        this.con = con;
    }

    public Constructor<?> getPortal() {
        Constructor<?> c = null;
        try {
            c = this.con.getConstructor(new Class[] { String.class, Bound.class, Status.class });
        } catch (Exception exception) {}
        return c;
    }
}
