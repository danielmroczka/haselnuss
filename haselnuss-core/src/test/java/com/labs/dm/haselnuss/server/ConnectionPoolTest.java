package com.labs.dm.haselnuss.server;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.hashmap.FastFileMapStorage;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel on 2015-05-25.
 */
public class ConnectionPoolTest {

    @Test
    public void shouldAdd() {
        ConnectionPool pool = new ConnectionPool();
        IFileStorage storage = new FastFileMapStorage("test");
        pool.add("test", storage);

        assertEquals(storage, pool.get("test"));
    }

    @Test
    public void empty() {
        ConnectionPool pool = new ConnectionPool();
        assertNotNull(pool.get("test"));
    }

    @Test
    public void shouldRemove() {
        ConnectionPool pool = new ConnectionPool();
        IFileStorage storage = new FastFileMapStorage("test");
        pool.add("test", storage);
        assertEquals(storage, pool.get("test"));
        pool.remove("test");
        assertNotNull(pool.get("test"));
        assertNotEquals(storage, pool.get("test"));
    }
}