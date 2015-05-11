package com.labs.dm.jkvdb.core.hashmap;

import java.io.*;
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

    /**
     * @param name - the name of created storage
     */
    public ZipFileMapStorage(String name) {
        super(name);
    }

    @Override
    public void load() {
        File file = new File(filename);
        if (file.exists()) {
            try (ZipFile zipFile = new ZipFile(filename)) {

                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();

                    try (InputStream stream = zipFile.getInputStream(entry);
                            ObjectInputStream input = new ObjectInputStream(stream)) {
                        map = (Map<Serializable, Serializable>) input.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(ZipFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
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

        try (ZipOutputStream fos = new ZipOutputStream(new FileOutputStream(filename));
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            ZipEntry e = new ZipEntry(filename);
            fos.putNextEntry(e);
            oos.writeObject(map);
        } catch (IOException ex) {
            Logger.getLogger(ZipFileMapStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
