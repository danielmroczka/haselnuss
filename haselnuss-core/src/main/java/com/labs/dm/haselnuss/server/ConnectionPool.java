package com.labs.dm.haselnuss.server;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.hashmap.FastFileMapStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 2015-05-25.
 */
public class ConnectionPool {
    private Map<String, IFileStorage> map = new HashMap<>();
    private Map<String, Long> lastused = new HashMap<>();

    public synchronized void add(String name, IFileStorage storage) {
        map.put(name, storage);
        lastused.put(name, System.currentTimeMillis());
    }

    public IFileStorage get(String name) {
        lastused.put(name, System.currentTimeMillis());
        // TODO Temporary workaround: -->
        if (!map.containsKey(name)) {
            IFileStorage storage = new FastFileMapStorage(name);
            try {
                storage.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put(name, storage);
        }
        // <--
        return map.get(name);
    }

    public void remove(String name) {
        map.remove(name);
    }

    public void flushAll() throws IOException {
        for (IFileStorage storage : map.values()) {
            storage.flush();
        }
    }
}
