package com.labs.dm.haselnuss.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author daniel
 */
public class UtilsTest {

    @Test
    public void checksValidRange() throws Exception {
        int pid = Utils.getPID();
        assertTrue(pid >= 1024);
    }

    @Test
    public void shouldAlwaysReturnTheSamePID() throws Exception {
        int reference = Utils.getPID();

        for (int i = 0; i < 10; i++) {
            assertEquals(reference, Utils.getPID());
        }
    }

    @Test
    public void testIP() throws Exception {
        assertNotNull(Utils.getIpAddress());
    }


}
