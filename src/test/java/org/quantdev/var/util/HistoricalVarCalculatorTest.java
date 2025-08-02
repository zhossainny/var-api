package org.quantdev.var.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HistoricalVarCalculatorTest {

    @Test
    void testCalculate95Percentile() {
        List<Double> pnls = List.of(
                -100.0, -85.0, -70.0, -65.0, -60.0, -55.0, -50.0, -40.0, -35.0, -30.0,
                -25.0, -20.0, -15.0, -10.0, -5.0, 0.0, 5.0, 10.0, 15.0, 20.0
        );
        double var = HistoricalVarCalculator.calculate(pnls, 0.95);
        assertEquals(85.0, var); // sorted = [-40, -20, 10, 30, 50] → index 0 = abs(-40)
    }

    @Test
    void testCalculate99Percentile() {
        List<Double> pnls = List.of(
                -100.0, -85.0, -70.0, -65.0, -60.0, -55.0, -50.0, -40.0, -35.0, -30.0,
                -25.0, -20.0, -15.0, -10.0, -5.0, 0.0, 5.0, 10.0, 15.0, 20.0
        );
        double var = HistoricalVarCalculator.calculate(pnls, 0.99);
        assertEquals(100.0, var); // index 0 = abs(-100)
    }

    @Test
    void testEmptyPnLThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> HistoricalVarCalculator.calculate(List.of(), 0.95));
    }

    @Test
    void testInvalidConfidenceLevelThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> HistoricalVarCalculator.calculate(List.of(10.0, -10.0), -0.5));
    }
}
