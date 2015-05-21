package com.labs.dm.jkvdb.core.hashmap;

import com.labs.dm.jkvdb.core.IFileStorage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by daniel on 2015-05-20.
 */
public class SuperFastFileMapStorage extends AbstractHashMapStorage implements Serializable, IFileStorage {
    private static final Logger logger = Logger.getLogger(SuperFastFileMapStorage.class.getSimpleName());
    private static  final int BUFFER_SIZE = 64000;
    protected final String filename;

    public SuperFastFileMapStorage(String dir, String name) {
        this.filename = dir + File.separator + name;
    }

    public SuperFastFileMapStorage(String name) {
        this("target", name);
    }

    @Override
    public void flush() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                 (new FileOutputStream(new RandomAccessFile(filename, "rw").getFD())), BUFFER_SIZE))) {

            oos.writeInt(map.size());

            for (Map.Entry<Serializable, Serializable> entry : map.entrySet()) {
                oos.writeObject(entry.getKey());
                oos.writeObject(entry.getValue());
            }

        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }

    }

    @Override
    public void load() {

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
              (new FileInputStream(new RandomAccessFile(filename, "r").getFD())), BUFFER_SIZE))) {

            int size = ois.readInt();
            map = new HashMap<>(size);

            for (int item = 0; item < size; item++) {
                Serializable key = (Serializable) ois.readObject();
                Serializable val = (Serializable) ois.readObject();
                map.put(key, val);
            }

        } catch (IOException | ClassNotFoundException e1) {
            logger.severe(e1.getMessage());
        }
    }

    @Override
    public void setAutoCommit(boolean value) {
        autoCommit = value;
    }
}
