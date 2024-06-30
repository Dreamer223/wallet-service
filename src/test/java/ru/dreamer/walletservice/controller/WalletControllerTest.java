package ru.dreamer.walletservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dreamer.walletservice.entity.Wallet;
import ru.dreamer.walletservice.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    private UUID walletId;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        walletRepository.deleteAll();
        Wallet wallet = new Wallet();
        walletId = UUID.randomUUID();
        wallet.setId(walletId);
        wallet.setBalance(10000);
        walletRepository.save(wallet);
    }

    @Test
    public void testDeposit() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\": \"" + walletId + "\", \"operationType\": \"DEPOSIT\", \"amount\": 1000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(11000));
    }

    @Test
    public void testWithdraw() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\": \"" + walletId + "\", \"operationType\": \"WITHDRAW\", \"amount\": 1000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(9000));
    }

    @Test
    public void testInsufficientFunds() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\": \"" + walletId + "\", \"operationType\": \"WITHDRAW\", \"amount\": 20000}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds"));
    }
}