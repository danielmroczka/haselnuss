package com.labs.dm.haselnuss.server;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.hashmap.FastFileMapStorage;
import com.labs.dm.haselnuss.server.http.RestServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 2015-05-25.
 */
public class ConnectionPool {

    private Map<String, IFileStorage> map = new HashMap<>();
    private Map<String, Long> lastused = new HashMap<>();
    private Map<Integer, RestServer> restMap = new HashMap<>();

    /**
     * Adds new instance of IFileStorage
     *
     * @param name
     * @param storage
     */
    public synchronized void add(String name, IFileStorage storage) {
        map.put(name, storage);
        lastused.put(name, System.currentTimeMillis());
    }

    /**
     * Gets IFileStorage instance from the pool if exists.
     * Otherwise returns null object
     *
     * @param name
     * @return Instance of IFileStorage if existed in the pool or null object
     */
    public IFileStorage get(String name) {
        lastused.put(name, System.currentTimeMillis());
        return map.get(name);
    }

    /**
     * Gets IFileStorage instance from the pool if exists.
     * Otherwise creates a new instance and add into the pool
     *
     * @param name
     * @return Existed or new created instance. Never null object
     */
    public IFileStorage create(String name) {
        lastused.put(name, System.currentTimeMillis());
        if (!map.containsKey(name)) {
            IFileStorage storage = new FastFileMapStorage(name);
            try {
                storage.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            add(name, storage);
        }
        return map.get(name);
    }

    public void remove(String name) {
        map.remove(name);
        lastused.remove(name);
    }

    public int size() {
        return map.size();
    }

    public void flushAll() throws IOException {
        for (IFileStorage storage : map.values()) {
            storage.flush();
        }
    }

    public RestServer getRestServer(int port) {
        return restMap.get(port);
    }

    public void addRestServer(RestServer restServer) {
        restMap.put(restServer.getPort(), restServer);
    }

}
