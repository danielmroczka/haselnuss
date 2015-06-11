package com.labs.dm.haselnuss.server;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.hashmap.FastFileMapStorage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel on 2015-05-25.
 */
public class ConnectionPoolTest {

    private ConnectionPool pool;

    @Before
    public void setUp() {
        pool = new ConnectionPool();
    }

    @Test
    public void shouldAdd() {
        // GIVEN
        IFileStorage storage = new FastFileMapStorage("test");
        // WHEN
        pool.add("test", storage);
        // THEN
        assertEquals(storage, pool.get("test"));
    }

    @Test
    public void shouldNewInstanceBeEmpty() {
        assertEquals(0, pool.size());
    }

    @Test
    public void empty2() {
        assertNull(pool.get("test"));
        assertNotNull(pool.create("test"));
        assertNotNull(pool.get("test"));
    }

    @Test
    public void shouldRemove() {
        IFileStorage storage = new FastFileMapStorage("test");
        pool.add("test", storage);
        assertEquals(storage, pool.get("test"));
        pool.remove("test");
        assertNull(pool.get("test"));
        assertNotEquals(storage, pool.get("test"));
    }

    @Test
    public void shouldRemove2() {
        IFileStorage storage = new FastFileMapStorage("test");
        pool.add("test", storage);
        pool.get("test");
        pool.remove("test");
        //assertNull(storage);

    }
}