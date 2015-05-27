package com.labs.dm.haselnuss.core.factory;

import org.junit.Test;

import static org.junit.Assert.*;

public class HaselnussInstanceFactoryTest
{
    @Test
    public void simpleTest() {
        assertNotNull(HaselnussInstanceFactory.newHaselnussInstance());
    }

}