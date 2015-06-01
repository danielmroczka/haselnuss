package com.labs.dm.haselnuss.core.factory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author daniel
 * @since 27.05.2015
 */
public class HaselnussInstanceFactory {
    private static final AtomicInteger SEQ_ID_GEN = new AtomicInteger();
    private static final ConcurrentMap<String, HaselnussInstance> INSTANCE_MAP
            = new ConcurrentHashMap<>(5);

    private HaselnussInstanceFactory() {
    }

    public static HaselnussInstance newHaselnussInstance() {
        if (!INSTANCE_MAP.containsKey("FOO")) {
            INSTANCE_MAP.put("FOO", new HaselnussInstance());
        }
        return INSTANCE_MAP.get("FOO");
    }
}
