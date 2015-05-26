package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.server.ConnectionPool;

/**
 * @author daniel
 * @since 26.05.2015
 */
public class HaselnussInstance {
    private final ConnectionPool pool = new ConnectionPool();

    public IFileStorage getStorage(String name) {
        return pool.get(name);
    }
}
