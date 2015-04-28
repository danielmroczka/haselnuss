package com.labs.dm.jkvdb.core.hashmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Simple ZipFile Map Storage Implementation
 * 
 * @author daniel
 */
public class ZipFileMapStorage extends SimpleFileMapStorage {

    public ZipFileMapStorage(String dir, String name) {
        super(dir, name);
    }

    public ZipFileMapStorage(String name) {
        super(name);
    }

    @Override
    public void load() {
        File file = new File(filename);
        if (file.exists()) {
            try {
                try (ZipFile zipFile = new ZipFile(filename)) {
                    
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        try (InputStream stream = zipFile.getInputStream(entry); ObjectInputStream input = new ObjectInputStream(stream)) {
                            map = (Map<Serializable, Serializable>) input.readObject();
                        }
                    }
                }
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(ZipFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ZipFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void flush() {

        try (ZipOutputStream fos = new ZipOutputStream(new FileOutputStream(filename))) {
            ZipEntry e = new ZipEntry(filename);
            fos.putNextEntry(e);
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(map);
                //return true;
            } catch (IOException ex) {
                Logger.getLogger(ZipFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ZipFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
