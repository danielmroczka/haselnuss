package com.labs.dm.haselnuss.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author daniel
 */
public class UtilsTest {

    @Test
    public void checksValidRange() throws Exception {
        int pid = Utils.pid();
        assertTrue(pid >= 1024);
        assertTrue(pid <= 65536);
    }

    @Test
    public void shouldAlwaysReturnTheSamePID() throws Exception {
        int reference = Utils.pid();

        for (int i = 0; i < 10; i++) {
            assertEquals(reference, Utils.pid());
        }
    }
}
