package com.labs.dm.jkvdb.core.hashmap;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.IStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * Created by daniel on 2015-05-21.
 */
public class FastFileMapStorageTest
{
    private IFileStorage storage;

    @Before
    public void before() throws IOException {
        storage = new FastFileMapStorage("target", "testcase1");
        storage.clean();
        storage.flush();
    }

    @After
    public void after() throws IOException {
        storage.clean();
        storage.flush();
    }

    @Test
    public void shouldNewStorageBeEmpty() {
        assertTrue(storage.size() == 0);
    }

    @Test
    public void shouldAdd() throws IOException {
        storage.put("key1", "value1");
        storage.flush();
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldFlushIfAutoCommit() throws IOException {
        storage.setAutoCommit(true);
        storage.put("key1", "value1");

        storage = new FastFileMapStorage("target", "testcase1");
        storage.load();
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldNotFlushIfNoAutoCommit() {
        storage.setAutoCommit(false);
        storage.put("key1", "value1");

        storage = new FastFileMapStorage("target", "testcase1");
        assertEquals(0, storage.size());
    }

    @Test
    public void shouldAddTwoKeys() throws IOException {
        assertEquals(0, storage.size());
        storage.put("1", "stringValue");
        storage.put(1, "intvalue");
        storage.flush();
        assertEquals(2, storage.size());
    }

    @Test
    public void shouldFlush() throws IOException {
        IFileStorage storage1 = new FastFileMapStorage("target", "ssflush1");
        storage1.load();
        storage1.setAutoCommit(true);
        storage1.remove("123");
        assertEquals(null, storage1.get("123"));
        storage1.put("123", "abc");

        IFileStorage storage2 = new FastFileMapStorage("target", "ssflush1");
        storage2.load();
        storage2.setAutoCommit(true);
        Serializable val = storage2.get("123");
        assertEquals("abc", val);
        storage2.remove("123");
    }

    @Test
    public void shouldRemove() throws IOException {
        storage.put(123, "value");
        storage.remove(123);
        Serializable val = storage.get(123);

        assertNull(val);
    }

    @Test
    public void wrongFilePath() throws IOException {
        IFileStorage storage = new FastFileMapStorage("xyz://non-existing", "xyz://non-existing");
        storage.flush();
    }

    //@Test
    public void raceConditionTest() {
        IFileStorage[] array = new IFileStorage[10];
        for (IFileStorage item : array) {
            storage = new FastFileMapStorage("target", "testcase1");
        }
        //private IFileStorage storage = new SimpleFileMapStorage("target", "testcase1");
    }

    //@Test
    public void benchmark() {
        long time = System.currentTimeMillis();
        IStorage storage = new FastFileMapStorage("target", "testcase2");
        for (int i = 0; i < 1000000; i++) {
            //      storage.add(i, "a");
        }
        //  storage.add(999, "stringValue"+99999, true);

        System.out.println(System.currentTimeMillis() - time);
        assertTrue(storage.size() > 0);
    }
}