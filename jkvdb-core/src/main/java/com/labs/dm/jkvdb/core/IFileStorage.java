package com.labs.dm.jkvdb.core;

import java.io.IOException;

/**
 * @author daniel
 */
public interface IFileStorage extends IStorage
{

    /**
     * Saves data in memory into the file
     * @throws java.io.IOException
     */
    void flush() throws IOException;;

    /**
     * Loads data from file
     * @throws java.io.IOException
     */
    void load() throws IOException;

}
