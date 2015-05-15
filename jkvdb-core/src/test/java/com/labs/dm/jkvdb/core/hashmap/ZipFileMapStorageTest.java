package com.labs.dm.jkvdb.core.hashmap;

import com.labs.dm.jkvdb.core.IFileStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * Created by daniel on 2015-05-15.
 */
public class ZipFileMapStorageTest {
    private IFileStorage storage;

    @Before
    public void before() throws IOException {
        storage = new ZipFileMapStorage("target", "ziptestcase1");
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
    @Ignore
    public void shouldFlushIfAutoCommit() {
        storage.setAutoCommit(true);
        storage.put("key1", "value1");

        storage = new ZipFileMapStorage("target", "testcase1");
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldNotFlushIfNoAutoCommit() {
        storage.setAutoCommit(false);
        storage.put("key1", "value1");

        storage = new ZipFileMapStorage("target", "testcase1");
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
    public void shouldRemove() throws IOException {
        storage.put(123, "value");
        storage.remove(123);
        Serializable val = storage.get(123);

        assertNull(val);
    }

    @Test
    public void wrongFilePath() throws IOException {
        IFileStorage storage = new ZipFileMapStorage("xyz://non-existing", "xyz://non-existing");
        storage.flush();
    }

}