package com.labs.dm.jkvdb.example.file;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.hashmap.FastFileMapStorage;

import java.io.IOException;

/**
 * @author daniel
 */
public class Main
{

    static final int COUNT = 1000000;

    public static void main(String[] args) throws IOException
    {
        long time = System.nanoTime();
        IFileStorage storage = new FastFileMapStorage(".", "test4");

        for (int i = 0; i < COUNT; i++)
        {
            storage.put(i, i%10);
        }
        time = System.nanoTime() - time;
        System.out.println("put in   \t" + time/1e6 + " [ms] \t" + COUNT / (time / 1e9) + " op/s");

        time = System.nanoTime();
        storage.flush();
        time = System.nanoTime() - time;
        System.out.println("flush in \t" + time/1e6 + " [ms] \t" + COUNT / (time / 1e9) + " op/s");

        time = System.nanoTime();
        storage.load();
        time = System.nanoTime() - time;
        System.out.println("load in \t" + time / 1e6 + " [ms] \t" + COUNT / (time / 1e9) + " op/s");

        System.out.println(storage.get(95));

    }
}
