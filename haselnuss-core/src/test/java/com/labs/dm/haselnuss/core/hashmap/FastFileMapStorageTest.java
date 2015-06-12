package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.IStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * Created by daniel on 2015-05-21.
 */
public class FastFileMapStorageTest {

    private IFileStorage storage;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            System.out.println("Starting test: " + description.getMethodName());
        }
    };

    @Before
    public void before() throws IOException {
        storage = Haselnuss.newInstance().createFileMapDatabase("testcase1");
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

        storage = Haselnuss.newInstance().createFileMapDatabase("testcase1");
        storage.load();
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldNotFlushIfNoAutoCommit() {
        storage.setAutoCommit(false);
        storage.put("key1", "value1");

        storage = new FastFileMapStorage("testcase1");
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
        IFileStorage storage1 = Haselnuss.newInstance().createFileMapDatabase("flush");
        storage1.load();
        storage1.setAutoCommit(true);
        storage1.remove("123");
        assertEquals(null, storage1.get("123"));
        storage1.put("123", "abc");

        IFileStorage storage2 = Haselnuss.newInstance().createFileMapDatabase("flush");
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
        storage.load();
        storage.flush();
    }

    //@Test
    public void benchmark() {
        long time = System.currentTimeMillis();
        IStorage storage = new FastFileMapStorage("testcase2");
        for (int i = 0; i < 1000000; i++) {
            //      storage.add(i, "a");
        }
        //  storage.add(999, "stringValue"+99999, true);

        System.out.println(System.currentTimeMillis() - time);
        assertTrue(storage.size() > 0);
    }
}