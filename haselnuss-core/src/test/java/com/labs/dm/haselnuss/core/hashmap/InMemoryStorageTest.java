package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by daniel on 2015-05-15.
 */
public class InMemoryStorageTest {

    @Test
    public void shouldCreateInMemoryDatabase() {
        // GIVEN
        IStorage storage = Haselnuss.newInstance().createInMemoryDatabase("mem");
        // WHEN
        storage.put(123, "abc");
        // THEN
        assertEquals("abc", storage.get(123));
        assertNull(storage.get(1));
        storage.remove(123);
        assertNull(storage.get(123));
    }

    @Test
    public void shouldCreateSharedInMemoryDatabase() {
        // GIVEN
        IStorage storage = Haselnuss.newInstance().createInMemoryDatabase("shared");
        IStorage storage1 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        IStorage storage2 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        // WHEN
        storage.put("test", "key");
        // THEN
        assertNotNull(storage1);
        assertEquals(storage1, storage2);
        assertNotEquals(storage, storage1);
        assertEquals("key", storage.get("test"));
        assertNull("key", storage1.get("test"));
        assertNull("key", storage2.get("test"));
    }

    @Test
    public void shouldSharedInMemoryStorage() {
        // GIVEN
        IStorage storage1 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        IStorage storage2 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        // WHEN
        storage1.put(123, new Date());
        // THEN
        assertEquals(storage1.get(123), storage2.get(123));
    }

    @Test
    public void shouldUnregister() {
        // GIVEN
        IStorage storage1 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        storage1.put(1, 1);
        // WHEN
        Haselnuss.newInstance().unregister("shared");
        IStorage storage2 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        // THEN
        assertNotEquals(storage1, storage2);
        //assertNull(storage1);
        assertNull(storage2.get(1));
    }
}