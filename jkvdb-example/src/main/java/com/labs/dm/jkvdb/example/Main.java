package com.labs.dm.jkvdb.example;

import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.SimpleMapStorage;

/**
 *
 * @author daniel 
 */
public class Main {
    public static void main(String[] args) {
        IStorage storage = new SimpleMapStorage("target/test1");
        
        for (int i=0; i < 100000; i++) {
            storage.add(i, i);
        }
    }
}
