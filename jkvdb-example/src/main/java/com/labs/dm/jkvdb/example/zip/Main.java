package com.labs.dm.jkvdb.example.zip;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;
import com.labs.dm.jkvdb.core.hashmap.ZipFileMapStorage;

/**
 *
 * @author daniel 
 */
public class Main {
    public static void main(String[] args) {
        IFileStorage storage = new ZipFileMapStorage("zip1");
        
        for (int i=0; i < 100000; i++) {
            storage.put(i, i);
        }
        
        System.out.println(storage.get(1000));
        
        storage.flush();
    }
}
