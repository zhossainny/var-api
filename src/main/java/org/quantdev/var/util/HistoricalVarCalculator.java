package org.quantdev.var.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoricalVarCalculator {

    public static double calculate(List<Double> pnlSeries, double confidenceLevel) {
        if (pnlSeries == null || pnlSeries.isEmpty()) {
            throw new IllegalArgumentException("PnL data cannot be null or empty");
        }
        if (confidenceLevel <= 0.0 || confidenceLevel >= 1.0) {
            throw new IllegalArgumentException("Confidence level must be between 0 and 1 (exclusive)");
        }

        List<Double> sortedPnL = new ArrayList<>(pnlSeries);
        Collections.sort(sortedPnL);

        //int index = (int) Math.ceil((1 - confidenceLevel) * sortedPnL.size()) - 1;
        int index = (int) Math.floor((1 - confidenceLevel) * sortedPnL.size());

        index = Math.max(0, index);

        return -sortedPnL.get(index);
    }
}