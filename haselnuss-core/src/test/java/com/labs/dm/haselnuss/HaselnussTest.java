package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.core.factory.HaselnussInstance;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HaselnussTest {

    @Test
    public void shouldCreateHaselnussInstance() throws Exception {
        //GIVEN
        //WHEN
        HaselnussInstance instance = Haselnuss.newInstance();
        //THEN
        assertNotNull(instance);
    }

    @Test
    public void shouldCreateTheSameInstance() throws Exception {
        //GIVEN
        //WHEN
        HaselnussInstance instance1 = Haselnuss.newInstance();
        HaselnussInstance instance2 = Haselnuss.newInstance();
        //THEN
        assertEquals(instance1, instance2);
    }
}