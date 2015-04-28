package com.labs.dm.jkvdb.core.hashmap;

import com.labs.dm.jkvdb.core.IStorage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author daniel
 */
public abstract class AbstractHashMapStorage implements IStorage {

    protected Map<Serializable, Serializable> map = new HashMap<>(1000);

    @Override
    public boolean put(Serializable key, Serializable value) {
        return map.put(key, value) == null;
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

}
