package com.labs.dm.haselnuss.utils;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.*;
import java.util.logging.Logger;

/**
 * @author daniel
 * @since 0.0.1
 */
public class Utils {

    private static final Logger logger = Logger.getLogger(Utils.class.getSimpleName());

    /**
     * Returns Process Identificator
     *
     * @return
     */
    public static int getPID() {
        RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
        if (rmxb != null && rmxb.getName() != null) {
            String pid = rmxb.getName().substring(0, rmxb.getName().indexOf("@"));
            return Integer.valueOf(pid);
        } else {
            return 0;
        }
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            logger.severe(e.getMessage());
        }
    }

    public static String getIpAddress() {
        try
        {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e)
        {
            logger.severe(e.getMessage());
        }

        return "";
    }

}
