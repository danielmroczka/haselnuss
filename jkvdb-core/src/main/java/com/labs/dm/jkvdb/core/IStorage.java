package com.labs.dm.jkvdb.core;

import java.io.Serializable;

/**
 * General purpose Storage Interface
 *
 * @author daniel
 */
public interface IStorage
{

    /**
     * Create
     *
     * @param key
     * @param value
     * @return
     */
    boolean put(Serializable key, Serializable value);

    /**
     * Read
     *
     * @param key
     * @return
     */
    Serializable get(Serializable key);

    /**
     * Update
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(Serializable key, Serializable value);

    /**
     * Delete
     *
     * @param key
     */
    void remove(Serializable key);

    int size();

    /**
     *
     */
    void clean();

    void setAutoCommit(boolean value);
}
