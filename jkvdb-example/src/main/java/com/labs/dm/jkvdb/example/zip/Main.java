package com.labs.dm.jkvdb.example.zip;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;
import com.labs.dm.jkvdb.core.hashmap.ZipFileMapStorage;

/**
 *
 * @author daniel 
 */
public class Main {
    
    static int  count = 1000000;
    
    public static void main(String[] args) {
        long time = System.nanoTime();
        IFileStorage storage = new ZipFileMapStorage("zip1");

        for (int i = 0; i < count; i++) {
            storage.put(i, i);
        }

        storage.flush();
        time = System.nanoTime() - time;
        System.out.println(count / (time / 1e9) + " op/s");
    }
}
