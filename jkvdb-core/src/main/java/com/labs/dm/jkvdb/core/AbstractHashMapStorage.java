package com.labs.dm.jkvdb.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author daniel
 */
public class AbstractHashMapStorage {

    protected Map<Serializable, Serializable> map = new HashMap<>(1000);

}
