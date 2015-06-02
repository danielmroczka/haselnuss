package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.core.factory.HaselnussInstance;
import com.labs.dm.haselnuss.core.factory.HaselnussInstanceFactory;

/**
 * @author daniel
 * @since 26.05.2015
 */
 public final class Haselnuss {

    /**
     * Creates new instance or returns existed one
     *
     * @return
     */
    public static HaselnussInstance newInstance() {
        return HaselnussInstanceFactory.newHaselnussInstance();
    }
}
