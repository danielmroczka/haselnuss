package com.labs.dm.haselnuss.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * General purpose Storage Interface
 *
 * @author daniel
 */
public interface IStorage {

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

    Collection<Serializable> getAll();

    Collection<Serializable> getKeys();

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

    /**
     * Returns the size of storage
     *
     * @return
     */
    int size();

    /**
     *
     */
    void clean();

    void setAutoCommit(boolean value);

    void close();
}
