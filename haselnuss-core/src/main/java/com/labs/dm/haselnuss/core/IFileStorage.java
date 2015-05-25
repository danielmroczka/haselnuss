package com.labs.dm.haselnuss.core;

import java.io.IOException;

/**
 * @author daniel
 */
public interface IFileStorage extends IStorage {

    /**
     * Saves data in memory into the file
     *
     * @throws java.io.IOException
     */
    void flush() throws IOException;

    /**
     * Loads new data from file.
     * Any existing data in the map will be replaced.
     *
     * @throws java.io.IOException
     */
    void load() throws IOException;

    boolean loaded();

}
