package com.labs.dm.jkvdb.core;

/**
 *
 * @author daniel
 */
public interface IFileStorage extends IStorage {

    /**
     * Saves data in memory into the file
     */
    void flush();

    /**
     * Loads data from file
     */
    void load();

}
