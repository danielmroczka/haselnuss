package com.labs.dm.jkvdb.core;

import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author daniel
 */
public class SimpleFileMapStorageTest
{

    private IFileStorage storage;

    @Before
    public void before() throws IOException
    {
        storage = new SimpleFileMapStorage("target", "testcase1");
        storage.clean();
        storage.flush();
    }

    @After
    public void after() throws IOException
    {
        storage.clean();
        storage.flush();
    }

    @Test
    public void shouldNewStorageBeEmpty()
    {
        assertTrue(storage.size() == 0);
    }

    @Test
    public void shouldAdd() throws IOException
    {
        storage.put("key1", "value1");
        storage.flush();
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldFlushIfAutoCommit()
    {
        storage.setAutoCommit(true);
        storage.put("key1", "value1");

        storage = new SimpleFileMapStorage("target", "testcase1");
        assertEquals(1, storage.size());
    }

    @Test
    public void shouldNotFlushIfNoAutoCommit()
    {
        storage.setAutoCommit(false);
        storage.put("key1", "value1");

        storage = new SimpleFileMapStorage("target", "testcase1");
        assertEquals(0, storage.size());
    }

    @Test
    public void shouldAddTwoKeys() throws IOException
    {
        storage.put("1", "stringValue");
        storage.put(1, "intvalue");
        storage.flush();
        assertEquals(2, storage.size());
    }
    
    @Test
    public void wrongFilePath() throws IOException {
        IFileStorage storage = new SimpleFileMapStorage("xyz://on-existig", "xyz://on-existig");
        storage.flush();
    }
    
    //@Test
    public void raceConditionTest() {
        IFileStorage[] array = new IFileStorage[10];
        for (IFileStorage item:array) {
            storage = new SimpleFileMapStorage("target", "testcase1");
        }
        //private IFileStorage storage = new SimpleFileMapStorage("target", "testcase1");
    }

    //@Test
    public void benchmark()
    {
        long time = System.currentTimeMillis();
        IStorage storage = new SimpleFileMapStorage("target", "testcase2");
        for (int i = 0; i < 1000000; i++)
        {
            //      storage.add(i, "a");
        }
        //  storage.add(999, "stringValue"+99999, true);

        System.out.println(System.currentTimeMillis() - time);
        assertTrue(storage.size() > 0);
    }
}
