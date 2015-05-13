package com.labs.dm.jkvdb.core.hashmap;

import com.labs.dm.jkvdb.core.IFileStorage;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author daniel
 */
public class SimpleFileMapStorage extends AbstractHashMapStorage implements Serializable, IFileStorage {

    private static final Logger logger = Logger.getLogger(SimpleFileMapStorage.class.getSimpleName());
    protected final String filename;

    public SimpleFileMapStorage(String dir, String name) {
        this.filename = dir + File.separator + name;
        load();
    }

    public SimpleFileMapStorage(String name) {
        this("target", name);
    }

    @Override
    public void flush() {
        if (autoCommit) {
            logger.warning("Autocommit is set to true there is no need to execute flush method");
        }
        try (FileOutputStream fos = new FileOutputStream(filename); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(map);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void load() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream ois = new ObjectInputStream(fis)) {
                map = (Map<Serializable, Serializable>) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void setAutoCommit(boolean value) {
        autoCommit = value;
    }
}
