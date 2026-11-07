package com.mycar.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DistanceCalculationTest {

    public static double calculateMetersToKilometers(float meters) {
        return meters / 1000.0;
    }

    @Test
    public void testMetersToKm() {
        assertEquals(1.5, calculateMetersToKilometers(1500f), 0.001);
        assertEquals(0.0, calculateMetersToKilometers(0f), 0.001);
        assertEquals(10.0, calculateMetersToKilometers(10000f), 0.001);
    }
}
