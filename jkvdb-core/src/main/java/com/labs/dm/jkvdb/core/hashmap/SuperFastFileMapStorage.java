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
    protected final String filename;
    private static  final int BUFFER_SIZE = 64000;

    public SuperFastFileMapStorage(String dir, String name) {
        this.filename = dir + File.separator + name;

    }

    public SuperFastFileMapStorage(String name) {
        this("target", name);
    }

    @Override
    public void flush() {

        try (ObjectOutputStream dos = new ObjectOutputStream(new BufferedOutputStream(
                 (new FileOutputStream(new RandomAccessFile(filename, "rw").getFD())), BUFFER_SIZE))) {

            dos.writeInt(map.size());

            for (Map.Entry<Serializable, Serializable> entry : map.entrySet()) {
                dos.writeObject(entry.getKey());
                dos.writeObject(entry.getValue());
            }

        } catch (IOException e1) {
            logger.severe(e1.getMessage());
        }


    }

    @Override
    public void load() {

        try (ObjectInputStream dos = new ObjectInputStream(new BufferedInputStream(
              (new FileInputStream(new RandomAccessFile(filename, "r").getFD())), BUFFER_SIZE))) {

            int size = dos.readInt();
            map = new HashMap<>(size);

            for (int item = 0; item < size; item++) {
                Serializable key = (Serializable) dos.readObject();
                Serializable val = (Serializable) dos.readObject();
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
