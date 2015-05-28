package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.core.factory.HaselnussInstance;
import org.junit.Test;

import static org.junit.Assert.*;

public class HaselnussTest
{
    @Test
    public void shouldCreateHaselnussInstance() throws Exception
    {
        //GIVEN
        //WHEN
        HaselnussInstance instance = Haselnuss.createHaselnussInstance();
        //THEN
        assertNotNull(instance);
    }

    @Test
    public void shouldCreateTheSameInstance() throws Exception
    {
        //GIVEN
        //WHEN
        HaselnussInstance instance1 = Haselnuss.createHaselnussInstance();
        HaselnussInstance instance2 = Haselnuss.createHaselnussInstance();
        //THEN
        assertEquals(instance1, instance2);
    }
}