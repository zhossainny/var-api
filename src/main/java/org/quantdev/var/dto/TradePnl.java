package org.quantdev.var.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class TradePnl {
    @NotEmpty(message = "PnL list cannot be empty")
    private List<Double> pnls;

    public TradePnl() {}

    public TradePnl(List<Double> pnls) {
        this.pnls = pnls;
    }

    public List<Double> getPnls() {
        return pnls;
    }

    public void setPnls(List<Double> pnls) {
        this.pnls = pnls;
    }
}