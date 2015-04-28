package com.labs.dm.jkvdb.example.file;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;

/**
 *
 * @author daniel 
 */
public class Main {
    public static void main(String[] args) {
        IFileStorage storage = new SimpleFileMapStorage("test1");
        
        for (int i=0; i < 100000; i++) {
            storage.put(i, i);
        }
        
        storage.flush();
    }
}
