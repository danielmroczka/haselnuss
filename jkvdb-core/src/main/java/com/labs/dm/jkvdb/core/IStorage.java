package com.labs.dm.jkvdb.core;

import java.io.Serializable;

/**
 *
 * @author daniel
 */
public interface IStorage {

    boolean add(Serializable key, Serializable value);

    Serializable get(Serializable key);

    int size();

    void remove(Serializable key);
    
    void clean();
}
