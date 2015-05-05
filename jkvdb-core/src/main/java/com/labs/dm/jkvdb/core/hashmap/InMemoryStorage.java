package com.labs.dm.jkvdb.core.hashmap;

import com.labs.dm.jkvdb.core.IStorage;

/**
 *
 * @author daniel
 */
public class InMemoryStorage extends AbstractHashMapStorage implements IStorage {

    private final String name;
    
    public InMemoryStorage(String name) {
        this.name = name;
    }

    @Override
    public void setAutoCommit(boolean value) {
        
    }

    @Override
    public void flush() {
    }

}
