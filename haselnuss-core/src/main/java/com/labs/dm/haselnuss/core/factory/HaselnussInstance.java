package com.labs.dm.haselnuss.core.factory;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.IStorage;
import com.labs.dm.haselnuss.core.hashmap.InMemoryStorage;
import com.labs.dm.haselnuss.server.ConnectionPool;

import java.util.logging.Logger;

/**
 * @author daniel
 * @since 26.05.2015
 */
public class HaselnussInstance {

    private static final Logger logger = Logger.getLogger(HaselnussInstance.class.getSimpleName());
    private final ConnectionPool pool = new ConnectionPool();

    public IFileStorage createFileMapDatabase(String name) {
        return pool.create(name);
    }

    public IStorage createInMemoryDatabase(String name) {
        return new InMemoryStorage(name);
    }

    public IStorage createSharedInMemoryDatabase(String name) {
        return pool.create(name);
    }

}
