package com.labs.dm.haselnuss.core.factory;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.IStorage;
import com.labs.dm.haselnuss.core.hashmap.InMemoryStorage;
import com.labs.dm.haselnuss.server.ConnectionPool;
import com.labs.dm.haselnuss.server.http.RestServer;
import com.labs.dm.haselnuss.server.tcp.TcpServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.labs.dm.haselnuss.Consts.CONFIG_FILENAME;
import static com.labs.dm.haselnuss.Consts.LOG_FILENAME;

/**
 * @author daniel
 * @since 26.05.2015
 */
public class HaselnussInstance {

    private static final Logger logger = Logger.getLogger(HaselnussInstance.class.getSimpleName());
    private final ConnectionPool pool = new ConnectionPool();
    private final Properties properties = new Properties();

    public HaselnussInstance() {
        loadConfiguration();
        initLog();
    }

    public Properties getProperties() {
        return properties;
    }

    public IFileStorage createFileMapDatabase(String name) {
        return pool.create(name);
    }

    public IFileStorage createSharedFileMapDatabase(String name) {
        return pool.create(name);
    }

    public IStorage createSharedInMemoryDatabase(String name) {
        if (!pool.contains(name)) {
            pool.addMem(name, createInMemoryDatabase(name));
        }
        return pool.getMem(name);
    }

    public IStorage createInMemoryDatabase(String name) {
        return new InMemoryStorage(name);
    }

    public void unregister(String name) {
        pool.remove(name);
    }

    public RestServer createRestServer(int port) {
        return new RestServer(port);
    }

    public RestServer createRestServer() {
        int port = Integer.parseInt(properties.getProperty("http.port"));
        return createRestServer(port);
    }

    private void loadConfiguration() {
        try (InputStream input = HaselnussInstance.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME)) {
            logger.info("Loading settings from file: " + CONFIG_FILENAME);
            properties.load(input);
            logger.info("Loaded settings: " + properties.keySet());

        } catch (FileNotFoundException fnfe) {
            logger.severe("Configuration file not found.");
        } catch (IOException ex) {
            logger.severe(ex.getLocalizedMessage());
        }
    }

    private void initLog() {
        LogManager manager = LogManager.getLogManager();
        try (InputStream input = HaselnussInstance.class.getClassLoader().getResourceAsStream(LOG_FILENAME)) {
            manager.readConfiguration(input);
        } catch (IOException e) {
            logger.severe("Log configuration failed");
        }
    }

    public TcpServer createTcpServer(int port) {
        return new TcpServer(port);
    }

    public TcpServer createTcpServer() {
        int port = Integer.parseInt(properties.getProperty("tcp.port"));
        return createTcpServer(port);
    }
}
