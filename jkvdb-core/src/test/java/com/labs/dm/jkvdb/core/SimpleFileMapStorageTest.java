package com.labs.dm.jkvdb.core;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class SimpleFileMapStorageTest {

    private IStorage storage;

    @Before
    public void before() {
        storage = new SimpleFileMapStorage("target", "testcase1");
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
        storage.put("key1", "value1");
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldAddTwoKeys() {
        storage.put("1", "stringValue");
        storage.put(1, "intvalue");
        assertEquals(2, storage.size());
    }

    @Test
    public void benchmark() {
        long time = System.currentTimeMillis();
        IStorage storage = new SimpleFileMapStorage("target", "testcase2");
        for (int i = 0; i < 1000000; i++) {
          //      storage.add(i, "a");
        }
      //  storage.add(999, "stringValue"+99999, true);

        System.out.println(System.currentTimeMillis() - time);
        assertTrue(storage.size() > 0);
    }
}
