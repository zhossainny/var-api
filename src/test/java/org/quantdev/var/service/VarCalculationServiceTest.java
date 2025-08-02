package org.quantdev.var.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quantdev.var.dto.PortfolioRequest;
import org.quantdev.var.dto.TradePnl;
import org.quantdev.var.dto.TradeRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VarCalculationServiceTest {

    private VarCalculationService service;

    @BeforeEach
    void setUp() {
        service = new VarCalculationService();
    }

    @Test
    void testCalculateTradeVaR_valid() {
        TradePnl pnl = new TradePnl(List.of(100.0, -50.0, 20.0, -10.0, -90.0));
        TradeRequest request = new TradeRequest(pnl, 0.95);
        double result = service.calculateTradeVar(request);
        assertEquals(90.0, result);
    }

    @Test
    void testCalculateTradeVaR_invalidPnL() {
        TradePnl pnl = new TradePnl(List.of());
        TradeRequest request = new TradeRequest(pnl, 0.95);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> service.calculateTradeVar(request));
        assertEquals("PnL list must not be empty", thrown.getMessage());
    }

    @Test
    void testCalculatePortfolioVaR_valid() {
        TradePnl t1 = new TradePnl(List.of(100.0, -80.0, 60.0, -40.0, 20.0));
        TradePnl t2 = new TradePnl(List.of(-30.0, 50.0, -70.0, 90.0, -110.0));
        PortfolioRequest request = new PortfolioRequest(List.of(t1, t2), 0.95);
        double result = service.calculatePortfolioVar(request);
        assertEquals(90, result); // Update based on actual calculated result
    }


    @Test
    void testCalculatePortfolioVaR_invalidEmpty() {
        PortfolioRequest request = new PortfolioRequest(List.of(), 0.95);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> service.calculatePortfolioVar(request));
        assertEquals("Portfolio is empty", thrown.getMessage());
    }
}
