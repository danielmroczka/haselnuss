package com.labs.dm.haselnuss.core.factory;

import org.junit.Test;

import static org.junit.Assert.*;

public class HaselnussInstanceFactoryTest
{
    @Test
    public void shouldReurnsNotNullObject() {
        assertNotNull(HaselnussInstanceFactory.newHaselnussInstance());
    }

    @Test
    public void shouldReturnsTheSameInstance() {
        assertEquals(HaselnussInstanceFactory.newHaselnussInstance(), HaselnussInstanceFactory.newHaselnussInstance());
    }

}