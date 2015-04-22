package com.labs.dm.jkvdb.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class SimpleMapStorage implements Serializable, IStorage {

    private final String filename;

    private Map<Serializable, Serializable> map;

    public SimpleMapStorage(String name) {
        this("target", name);
    }

    public SimpleMapStorage(String dir, String name) {
        this.filename = dir + File.separator + name;
        load();
    }

    @Override
    public boolean add(Serializable key, Serializable value) {
        Serializable result = map.put(key, value);
        try {
            flush();
        } catch (IOException ex) {
            Logger.getLogger(SimpleMapStorage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result == null;
    }

    @Override
    public Serializable get(Serializable key) {
        return map.get(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    protected void flush() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(map);
            }
        }
    }

    private void load() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream ois = new ObjectInputStream(fis)) {
                map = (Map<Serializable, Serializable>) ois.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SimpleMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(SimpleMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SimpleMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
            map = new HashMap<>();
        }
    }

    @Override
    public void remove(Serializable key) {
        map.remove(key);
    }

    @Override
    public void clean() {
        map.clear();

        try {
            flush();
        } catch (IOException ex) {
            Logger.getLogger(SimpleMapStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
