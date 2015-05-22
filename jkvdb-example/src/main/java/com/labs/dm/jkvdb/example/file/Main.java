package com.labs.dm.jkvdb.example.file;

import com.labs.dm.jkvdb.DBManager;
import com.labs.dm.jkvdb.core.IFileStorage;

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
        IFileStorage storage = DBManager.createFileMapDatabase("test111");

        for (int i = 0; i < COUNT; i++)
        {
            storage.put(i, "abcdefghijklmnoprtstuwxyz" + i);
        }
        time = System.nanoTime() - time;
        System.out.println("put in   \t" + Math.round(time/1e6) + " [ms] \t" + Math.round(COUNT / (time / 1e9)) + " op/s");

        time = System.nanoTime();
        storage.flush();
        time = System.nanoTime() - time;
        System.out.println("flush in \t" + Math.round(time/1e6) + " [ms] \t" + Math.round(COUNT / (time / 1e9)) + " op/s");

        time = System.nanoTime();
        storage.load();
        time = System.nanoTime() - time;
        System.out.println("load in \t" + Math.round(time / 1e6) + " [ms] \t" + Math.round(COUNT / (time / 1e9)) + " op/s");

        System.out.println(storage.get(95));

    }
}
