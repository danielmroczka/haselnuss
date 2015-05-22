package com.labs.dm.jkvdb;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.hashmap.FastFileMapStorage;
import com.labs.dm.jkvdb.core.hashmap.InMemoryStorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import static com.labs.dm.jkvdb.Consts.CONFIG_FILENAME;

/**
 * Created by daniel on 2015-05-13.
 */
public class DBManager {

    private static final Logger logger = Logger.getLogger(DBManager.class.getSimpleName());
    private Properties properties;

    private DBManager() {

    }

    public static InMemoryStorage createInMemoryDatabase(String name) {
        return new InMemoryStorage(name);
    }

    public static IFileStorage createFileMapDatabase(String name) {
        return new FastFileMapStorage(name);
    }

    private void loadConfiguration() {
        try (InputStream input = DBManager.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME)) {
            properties.load(input);
        } catch (FileNotFoundException fnfe) {
            logger.severe("Configuration file not found.");
        } catch (IOException ex) {
            logger.severe(ex.getLocalizedMessage());
        }
    }
}
