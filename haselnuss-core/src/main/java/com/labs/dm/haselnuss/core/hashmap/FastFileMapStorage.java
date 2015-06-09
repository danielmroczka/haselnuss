package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IFileStorage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * Fast FileMapStorage implementation
 * <p>
 * Created by daniel on 2015-05-20.
 */
public class FastFileMapStorage extends AbstractHashMapStorage implements Serializable, IFileStorage {

    private static final Logger logger = Logger.getLogger(FastFileMapStorage.class.getSimpleName());

    private static final int BUFFER_SIZE = 256000;

    protected final String filename;

    public FastFileMapStorage(String dir, String name) {
        this.filename = new File(".").getAbsolutePath() + File.separator + dir + File.separator + name;
    }

    public FastFileMapStorage(String name) {
        File dir = new File(new File("").getAbsolutePath() + File.separator + Haselnuss.newInstance().getProperties().getProperty("data.dir"));
        if (!dir.exists()) {

            dir.mkdir();

        }
        this.filename = dir.getPath() + File.separator + name;
    }

    @Override
    public void flush() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                new DeflaterOutputStream(new FileOutputStream(new RandomAccessFile(filename, "rw").getFD())), BUFFER_SIZE))) {

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
        File file = new File(filename);

        if (file.exists()) {
            load(filename);
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Create new file failed", e);
            }
        }

    }

    private void load(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new InflaterInputStream
                        (new FileInputStream(new RandomAccessFile(filename, "r").getFD())), BUFFER_SIZE))) {

            int size = ois.readInt();
            map = new HashMap<>(size);

            for (int item = 0; item < size; item++) {
                map.put((Serializable) ois.readObject(), (Serializable) ois.readObject());
            }
        } catch (EOFException eof) {
            // nop
        } catch (IOException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Load failed", ex);
        }
        loaded = true;
    }

    @Override
    public void setAutoCommit(boolean value) {
        autoCommit = value;
    }

}
