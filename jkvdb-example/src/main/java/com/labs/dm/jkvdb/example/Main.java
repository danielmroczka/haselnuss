package com.labs.dm.jkvdb.example;

import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.SimpleFileMapStorage;

/**
 *
 * @author daniel 
 */
public class Main {
    public static void main(String[] args) {
        IStorage storage = new SimpleFileMapStorage("target/test1");
        
        for (int i=0; i < 100000; i++) {
            storage.put(i, i);
        }
    }
}
