package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;
import org.junit.Ignore;
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
    @Ignore
    public void raceConditionTest() throws Exception
    {
        IStorage storage = Haselnuss.createHaselnussInstance().createInMemoryDatabase("thread1");
        storage.put("key", 0);
        Thread t1 = new Thread(new Counter(storage));
        Thread t2 = new Thread(new Counter(storage));
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(20000, storage.get("key"));

        storage.close();
    }

    class Counter implements Runnable
    {
        Counter(IStorage storage)
        {
            this.storage = storage;
        }

        private IStorage storage;

        @Override
        public void run()
        {
            for (int i = 0; i < 10000; i++)
            {
                int val = (int) storage.get("key");
                storage.put("key", ++val);
            }

        }
    }
}
