package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.IStorage;
import com.labs.dm.haselnuss.core.hashmap.FastFileMapStorage;
import com.labs.dm.haselnuss.core.hashmap.InMemoryStorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import static com.labs.dm.haselnuss.Consts.CONFIG_FILENAME;

/**
 * Created by daniel on 2015-05-13.
 */
public class InstanceManager {
    private static final Logger logger = Logger.getLogger(InstanceManager.class.getSimpleName());
    private static Map<String, IStorage> map = new HashMap<>();
    private Properties properties;

    public static IStorage createInMemoryDatabase(String name) {
        return new InMemoryStorage(name);
    }

    public static IStorage createSingletonInMemoryDatabase(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        IStorage storage = createInMemoryDatabase(name);
        map.put(name, storage);
        return storage;
    }

    public static IFileStorage createFileMapDatabase(String name) {
        return new FastFileMapStorage(name);
    }

    private void loadConfiguration() {
        try (InputStream input = InstanceManager.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME)) {
            properties.load(input);
        } catch (FileNotFoundException fnfe) {
            logger.severe("Configuration file not found.");
        } catch (IOException ex) {
            logger.severe(ex.getLocalizedMessage());
        }
    }
}
