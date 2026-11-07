package com.mycar.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OdometerValidationTest {

    public static boolean isValidNewOdometer(double currentOdometer, double newOdometer) {
        return newOdometer >= currentOdometer && newOdometer >= 0;
    }

    @Test
    public void testValidOdometerIncrease() {
        assertTrue(isValidNewOdometer(50000, 50150));
    }

    @Test
    public void testEqualOdometer() {
        assertTrue(isValidNewOdometer(50000, 50000));
    }

    @Test
    public void testInvalidLowerOdometer() {
        assertFalse(isValidNewOdometer(50000, 49900));
    }

    @Test
    public void testNegativeOdometer() {
        assertFalse(isValidNewOdometer(0, -100));
    }
}
