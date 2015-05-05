package com.labs.dm.jkvdb.core.hashmap;

import com.labs.dm.jkvdb.core.IFileStorage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class SimpleFileMapStorage extends AbstractHashMapStorage implements Serializable, IFileStorage {

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
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(map);
                //return true;
            } catch (IOException ex) {
                Logger.getLogger(SimpleFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(SimpleFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void load() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream ois = new ObjectInputStream(fis)) {
                map = (Map<Serializable, Serializable>) ois.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SimpleFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(SimpleFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SimpleFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void setAutoCommit(boolean value) {
        autoCommit = value;
    }
}


