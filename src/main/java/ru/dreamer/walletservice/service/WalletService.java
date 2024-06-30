package ru.dreamer.walletservice.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dreamer.walletservice.entity.Wallet;
import ru.dreamer.walletservice.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public synchronized Wallet handleTransaction(UUID walletId, String operationType, long amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("Wallet not found"));

        switch (operationType.toUpperCase()) {
            case "DEPOSIT":
                wallet.setBalance(wallet.getBalance() + amount);
                break;
            case "WITHDRAW":
                if (wallet.getBalance() < amount) {
                    throw new RuntimeException("Insufficient funds");
                }
                wallet.setBalance(wallet.getBalance() - amount);
                break;
            default:
                throw new RuntimeException("Invalid operation type");
        }

        return walletRepository.save(wallet);
    }

    public Optional<Wallet> getBalance(UUID walletId) {
        return walletRepository.findById(walletId);
    }
}
