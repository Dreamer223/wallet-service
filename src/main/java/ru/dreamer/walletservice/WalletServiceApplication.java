package ru.dreamer.walletservice;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletServiceApplication{
    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication.class, args);
    }

}
