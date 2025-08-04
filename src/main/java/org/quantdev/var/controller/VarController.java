package org.quantdev.var.controller;

import org.quantdev.var.dto.PortfolioRequest;
import org.quantdev.var.dto.TradeRequest;
import org.quantdev.var.dto.VarResponse;
import org.quantdev.var.service.VarCalculationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * REST controller for VaR calculation endpoints.
 * Provides APIs to calculate Value at Risk for trades and portfolios.
 */
@RestController
@RequestMapping("/api/var")
public class VarController {

    private final VarCalculationService service;

    public VarController(VarCalculationService service) {
        this.service = service;
    }

    @PostMapping("/trade")
    public ResponseEntity<VarResponse> calculateVarForTrade(@Valid @RequestBody TradeRequest request) {
        double var = service.calculateTradeVar(request);
        return ResponseEntity.ok(new VarResponse(var));
    }

    @PostMapping("/portfolio")
    public ResponseEntity<VarResponse> calculateVarForPortfolio(@Valid @RequestBody PortfolioRequest request) {
        double var = service.calculatePortfolioVar(request);
        return ResponseEntity.ok(new VarResponse(var));
    }
}