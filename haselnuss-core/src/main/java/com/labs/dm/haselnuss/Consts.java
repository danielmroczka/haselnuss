package com.labs.dm.haselnuss;

/**
 * @author daniel
 */
public class Consts {

    public static final String CONFIG_FILENAME = "conf.properties";
    public static final String LOG_FILENAME = "logging.properties";
    public static final int TCP_DEFAULT_PORT = 6543;
    public static final int HTTP_DEFAULT_PORT = 8081;
    public static final String DB_EXTENSION = ".hdb";

    public enum SERVER_STATUS {
        STOPPED, STARTING, STARTED, STOPPING
    }

}
