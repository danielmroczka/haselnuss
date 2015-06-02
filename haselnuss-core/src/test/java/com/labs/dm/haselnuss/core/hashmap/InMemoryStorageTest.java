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
    public void simpleMem() {
        IStorage storage = Haselnuss.newInstance().createInMemoryDatabase("mem");
        storage.put(123, "abc");
        assertEquals("abc", storage.get(123));
        assertNull(storage.get(1));
        storage.remove(123);
        assertNull(storage.get(123));

    }

    @Test
    public void sharedMem() {
        IStorage storage1 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        IStorage storage2 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        IStorage storage = Haselnuss.newInstance().createInMemoryDatabase("mem");
        assertNotNull(storage1);
        assertEquals(storage1, storage2);
        assertNotEquals(storage, storage1);
    }

    @Test
    public void sharedMem2() {
        IStorage storage1 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");
        IStorage storage2 = Haselnuss.newInstance().createSharedInMemoryDatabase("shared");

        storage1.put(123, new Date());
        assertEquals(storage1.get(123), storage2.get(123));
    }
}