package org.quantdev.var.service;

import org.quantdev.var.dto.PortfolioRequest;
import org.quantdev.var.dto.TradePnl;
import org.quantdev.var.dto.TradeRequest;
import org.quantdev.var.util.HistoricalVarCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Service class for Value at Risk (VaR) calculations.
 * <p>
 * Provides methods to calculate VaR for a single trade or an entire portfolio.
 * For portfolios, each trade can have a different length of PnL series.
 * The calculation aligns all PnL series to the most recent <b>minLength</b> days
 * (where <b>minLength</b> is the shortest PnL list among all trades).
 * For each day in this aligned window, the service sums the PnL across all trades
 * to create the portfolio PnL series used for VaR calculation.
 */
@Service
public class VarCalculationService {

    public double calculateTradeVar(TradeRequest request) {
        List<Double> pnls = request.getTrade().getPnls();
        // Defensive programming - even though @notEmpty is used on tradePnl and spring will trigger validation annotations
        //first before hitting this controller, MethodArgumentNotValidException is thrown from the controller layer
        // This is Service level error checking for internal use.
        if (pnls == null || pnls.isEmpty()) {
            throw new IllegalArgumentException("PnL list must not be empty");
        }
        if (pnls.size() < 2) {
            throw new IllegalArgumentException("At least two PnL entries are required");
        }
        return HistoricalVarCalculator.calculate(pnls, request.getConfidenceLevel());
    }

    public double calculatePortfolioVar(PortfolioRequest request) {
        List<TradePnl> trades = request.getTrades();

        if (trades == null || trades.isEmpty()) {
            throw new IllegalArgumentException("Portfolio is empty");
        }

        int minLength = trades.stream()
                .mapToInt(t -> t.getPnls().size())
                .min()
                .orElseThrow(() -> new IllegalArgumentException("Trade PnL series cannot be empty"));

        if (minLength < 2) {
            throw new IllegalArgumentException("Each trade must have at least 2 PnL values");
        }
        // Align all trade PnL series to the last minLength days.
        // For each day, sum the PnL across all trades to get the portfolio PnL.
        // This ensures all trades are aligned on the same historical window.
        List<Double> combined = new ArrayList<>();
        for (int i = 0; i < minLength; i++) {
            double dailySum = 0;
            for (TradePnl t : trades) {
                List<Double> pnl = t.getPnls();
                dailySum += pnl.get(pnl.size() - minLength + i);
            }
            combined.add(dailySum);
        }

        return HistoricalVarCalculator.calculate(combined, request.getConfidenceLevel());
    }
}
