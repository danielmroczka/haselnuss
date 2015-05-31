package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2015-05-28.
 */
public class InMemoryStorageConcurrencyTest
{
    public static void main(String[] args)
    {
        IStorage storage1 = Haselnuss.createHaselnussInstance().createInMemoryDatabase("test");
        IStorage storage2 = Haselnuss.createHaselnussInstance().createInMemoryDatabase("test");

        storage1.put(123, "abc");
        System.out.println(storage2.get(123));
    }

    @Test
    public void raceConditionTest() throws Exception
    {
        IStorage storage = Haselnuss.createHaselnussInstance().createInMemoryDatabase("thread1");
        storage.put("key", 0);
        Thread[] t = new Thread[10];

        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Counter(storage));
            t[i].start();

        }

        for (Thread h : t) {
            h.join();
        }


        assertEquals(100000, storage.get("key"));

        storage.close();
    }

    private class Counter implements Runnable
    {
        private IStorage storage;

        Counter(IStorage storage)
        {
            this.storage = storage;
        }

        @Override
        public void run()
        {
            for (int i = 0; i < 10000; i++)
            {
                int val;
                synchronized (storage) {
                    val = (int) storage.get("key");
                    storage.put("key", ++val);
                }

            }

        }
    }
}
