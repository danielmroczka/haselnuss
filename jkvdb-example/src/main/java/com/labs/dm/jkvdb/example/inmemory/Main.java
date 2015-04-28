package com.labs.dm.jkvdb.example.inmemory;

import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.hashmap.InMemoryStorage;
import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;

/**
 *
 * @author daniel
 */
public class Main {

    public static void main(String[] args) {
        IStorage storage = new InMemoryStorage("foo");

        for (int i = 0; i < 100000; i++) {
            storage.put(i, i);
        }
        
        System.out.println(storage.get(100));
    }
}
