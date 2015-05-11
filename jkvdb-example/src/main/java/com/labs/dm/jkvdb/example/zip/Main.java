package com.labs.dm.jkvdb.example.zip;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.hashmap.ZipFileMapStorage;
import java.io.IOException;

/**
 * @author daniel
 */
public class Main
{

    static final int count = 1000000;

    public static void main(String[] args) throws IOException
    {
        long time = System.nanoTime();
        IFileStorage storage = new ZipFileMapStorage("zip1");

        for (int i = 0; i < count; i++)
        {
            storage.put(i, i);
        }

        storage.flush();
        time = System.nanoTime() - time;
        System.out.println(count / (time / 1e9) + " op/s");
    }
}
