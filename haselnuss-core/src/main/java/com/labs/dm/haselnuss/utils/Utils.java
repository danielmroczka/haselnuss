package com.labs.dm.haselnuss.utils;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author daniel
 */
public class Utils {

    public static int pid() {
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
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
