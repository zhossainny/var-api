package org.quantdev.var.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testTradeVaR_success() throws Exception {
        String requestJson = """
            {
                       "trade": {
                         "pnls": [100, -50, 20, -10, 15, -90, 60, -40, 10, -5]
                       },
                       "confidenceLevel": 0.95
                     }
                    \s
       \s""";

        mockMvc.perform(post("/api/var/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.var").value(90.0));
    }

    @Test
    void testPortfolioVaR_success() throws Exception {
        String requestJson = """
            {
                "trades": [
                    { "pnls": [10, -20, 30, -40, 50] },
                    { "pnls": [-5, 15, -25, 35, -45] }
                ],
                "confidenceLevel": 0.95
            }
        """;

        mockMvc.perform(post("/api/var/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.var").value(5.0));
    }

    //@Test
    void testTradeVaR_invalidPnls() throws Exception {
        String requestJson = """
            {
                                 "trade": {
                                   "pnls": []
                                 },
                                 "confidenceLevel": 0.95
                               }
                               
                    \s
       \s""";

        mockMvc.perform(post("/api/var/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['trade.pnls']").value("PnL list cannot be empty"));;

    }

    @Test
    void testPortfolioVaR_emptyTrades() throws Exception {
        String requestJson = """
            {
                "trades": [],
                "confidenceLevel": 0.95
            }
        """;

        mockMvc.perform(post("/api/var/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.trades").value("Portfolio must contain at least one trade"));
    }
}
