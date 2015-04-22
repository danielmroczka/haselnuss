package com.labs.dm.jkvdb.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class SimpleMapStorageTest {

    private IStorage storage;

    @Before
    public void before() {
        storage = new SimpleMapStorage("target", "testcase1");
    }

    @After
    public void after() {
        storage.clean();
    }

    @Test
    public void shouldNewStorageBeEmpty() {
        assertTrue(storage.size() == 0);
    }

    @Test
    public void shouldAdd() {
        storage.add("key1", "value1");
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldAddTwoKeys() {
        storage.add("1", "stringValue");
        storage.add(1, "intvalue");
        assertEquals(2, storage.size());
    }

}
