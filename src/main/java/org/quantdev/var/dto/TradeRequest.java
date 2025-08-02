package org.quantdev.var.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TradeRequest {
    @NotNull(message = "Trade PnL is required")
    private TradePnl trade;

    @NotNull(message = "Confidence level is required")
    @Min(value = 0, message = "Confidence level must be between 0 and 1")
    @Max(value = 1, message = "Confidence level must be between 0 and 1")
    private Double confidenceLevel;

    public TradeRequest() {}

    public TradeRequest(TradePnl trade, Double confidenceLevel) {
        this.trade = trade;
        this.confidenceLevel = confidenceLevel;
    }

    public TradePnl getTrade() {
        return trade;
    }

    public void setTrade(TradePnl trade) {
        this.trade = trade;
    }

    public Double getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(Double confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }
}
