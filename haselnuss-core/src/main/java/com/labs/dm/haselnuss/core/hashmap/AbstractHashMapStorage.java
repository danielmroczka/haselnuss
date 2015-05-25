package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.core.IStorage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daniel
 */
public abstract class AbstractHashMapStorage implements IStorage {

    protected boolean autoCommit;
    protected boolean loaded;

    protected Map<Serializable, Serializable> map = new HashMap<>();

    @Override
    public boolean put(Serializable key, Serializable value) {
        Serializable result = map.put(key, value);
        if (autoCommit) {
            flush();
        }
        return result == null;
    }

    @Override
    public Serializable get(Serializable key) {
        return map.get(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void remove(Serializable key) {
        map.remove(key);
        if (autoCommit) {
            flush();
        }
    }

    @Override
    public void clean() {
        map.clear();
    }

    @Override
    public boolean set(Serializable key, Serializable value) {
        if (map.containsKey(key)) {
            map.put(key, value);
            return true;
        }

        return false;
    }

    abstract public void flush();

    public boolean loaded() {
        return loaded;
    }
}
