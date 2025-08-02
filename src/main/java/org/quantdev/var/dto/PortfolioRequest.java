package org.quantdev.var.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PortfolioRequest {
    @NotEmpty(message = "Portfolio must contain at least one trade")
    @Valid
    private List<TradePnl> trades;

    @NotNull(message = "Confidence level is required")
    @Min(value = 0, message = "Confidence level must be between 0 and 1")
    @Max(value = 1, message = "Confidence level must be between 0 and 1")
    private Double confidenceLevel;

    public PortfolioRequest() {}

    public PortfolioRequest(List<TradePnl> trades, Double confidenceLevel) {
        this.trades = trades;
        this.confidenceLevel = confidenceLevel;
    }

    public List<TradePnl> getTrades() {
        return trades;
    }

    public void setTrades(List<TradePnl> trades) {
        this.trades = trades;
    }

    public Double getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(Double confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }
}
