package com.kunal.money_transfer_api.repo;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {

    private final Map<String, BigDecimal> accountsRepo = new ConcurrentHashMap<>();

    public AccountRepository(){
        //Sample accounts for demo
        accountsRepo.put("A", BigDecimal.valueOf(1000));
        accountsRepo.put("B", BigDecimal.valueOf(1000));
    }
    public BigDecimal getBalance(String accountId){
        return accountsRepo.get(accountId);
    }

    public void updateBalance(String accountId, BigDecimal newBalance){
        accountsRepo.put(accountId, newBalance);
    }

    public boolean accountExist(String accountId){
        return accountsRepo.containsKey(accountId);
    }

    public void createAccount(String accountId, BigDecimal balance) {
        if (accountsRepo.containsKey(accountId)) {
            throw new IllegalArgumentException("Account already exists");
        }
        accountsRepo.put(accountId, balance);
    }

    public Map<String, BigDecimal> all() {
        return accountsRepo;
    }
}
