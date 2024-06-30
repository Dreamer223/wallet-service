package ru.dreamer.walletservice.entity;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    private UUID id;

    @Column(nullable = false)
    private double balance;

    @Version
    private Long version;

    public Wallet(UUID id, double balance, Long version) {
        this.id = UUID.randomUUID();
        this.balance = balance;
        this.version = version;
    }

    public Wallet() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
