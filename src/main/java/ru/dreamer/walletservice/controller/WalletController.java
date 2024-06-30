package ru.dreamer.walletservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dreamer.walletservice.entity.Wallet;
import ru.dreamer.walletservice.service.WalletService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<?> handleTransaction(@RequestBody Map<String, Object> request) {
        try {
            UUID walletId = UUID.fromString(request.get("walletId").toString());
            String operationType = request.get("operationType").toString();
            long amount = Long.parseLong(request.get("amount").toString());

            Wallet wallet = walletService.handleTransaction(walletId, operationType, amount);
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable UUID walletId) {
        return walletService.getBalance(walletId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
