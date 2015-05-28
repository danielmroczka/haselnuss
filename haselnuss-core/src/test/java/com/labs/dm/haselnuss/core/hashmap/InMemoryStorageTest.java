package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by daniel on 2015-05-15.
 */
public class InMemoryStorageTest {

    @Test
    public void simple() {
        IStorage storage = Haselnuss.createHaselnussInstance().createInMemoryDatabase("mem");
        storage.put(123, "abc");
        assertEquals("abc", storage.get(123));
        assertNull(storage.get(1));
        storage.remove(123);
        assertNull(storage.get(123));

    }
}