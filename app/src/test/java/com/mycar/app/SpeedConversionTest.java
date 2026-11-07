package com.mycar.app;

import static org.junit.Assert.assertEquals;

import com.mycar.app.utils.FormatUtils;

import org.junit.Test;

public class SpeedConversionTest {

    @Test
    public void testMsToKmhConversion() {
        // 10 m/s should equal 36 km/h
        assertEquals(36.0, FormatUtils.msToKmh(10f), 0.001);

        // 27.7778 m/s should equal ~100 km/h
        assertEquals(100.0, FormatUtils.msToKmh(27.777778f), 0.01);

        // 0 m/s should equal 0 km/h
        assertEquals(0.0, FormatUtils.msToKmh(0f), 0.001);
    }
}
