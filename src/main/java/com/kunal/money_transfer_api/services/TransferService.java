package com.kunal.money_transfer_api.services;

import com.kunal.money_transfer_api.repo.AccountRepository;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final AccountRepository repo;

    public TransferService(AccountRepository repo){
        this.repo = repo;
    }

    public synchronized void transfer(String from, String to, BigDecimal amount){
        if(!repo.accountExist(from) || !repo.accountExist(to)){
            throw new IllegalArgumentException("Account not found");
        }

        var fromBalance = repo.getBalance(from);
        var toBalance = repo.getBalance(to);

        if(fromBalance.compareTo(amount)<0){
            throw new IllegalArgumentException("Insufficient funds");
        }

        repo.updateBalance(from, fromBalance.subtract(amount));
        repo.updateBalance(to, toBalance.add(amount));
    }

    public void createAccount(String id, BigDecimal balance) {
        repo.createAccount(id, balance);
    }
}
