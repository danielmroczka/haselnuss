package com.labs.dm.jkvdb;

import com.labs.dm.jkvdb.core.hashmap.InMemoryStorage;

/**
 * Created by daniel on 2015-05-13.
 */
public class DBManager {

    private DBManager() {

    }

    public static InMemoryStorage createInMemoryDatabase(String name) {
        return new InMemoryStorage(name);
    }
}
