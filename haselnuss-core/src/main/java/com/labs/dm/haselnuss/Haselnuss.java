package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.core.factory.HaselnussInstance;
import com.labs.dm.haselnuss.core.factory.HaselnussInstanceFactory;

/**
 * @author daniel
 * @since 26.05.2015
 */
public class Haselnuss {

    public static HaselnussInstance createHaselnussInstance() {
        return HaselnussInstanceFactory.newHaselnussInstance();
    }
}
