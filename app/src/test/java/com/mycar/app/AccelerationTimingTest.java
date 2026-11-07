package com.mycar.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccelerationTimingTest {

    public static double calculateElapsedTime(long startTimeMs, long endTimeMs) {
        return (endTimeMs - startTimeMs) / 1000.0;
    }

    @Test
    public void testCalculateElapsedSeconds() {
        long start = 1000000000000L;
        long end = 1000000008500L; // 8.5 seconds later

        double elapsed = calculateElapsedTime(start, end);
        assertEquals(8.5, elapsed, 0.001);
    }
}
