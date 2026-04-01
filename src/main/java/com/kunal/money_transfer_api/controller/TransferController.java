package com.kunal.money_transfer_api.controller;

import com.kunal.money_transfer_api.dto.CreateAccountRequest;
import com.kunal.money_transfer_api.dto.TransferRequest;
import com.kunal.money_transfer_api.repo.AccountRepository;
import com.kunal.money_transfer_api.services.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/api")
public class TransferController {

    private final TransferService service;
    private final AccountRepository repo;

    public TransferController(TransferService service, AccountRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest req) {
        var beforeFrom = repo.getBalance(req.fromAccount());
        var beforeTo = repo.getBalance(req.toAccount());

        try {
            service.transfer(req.fromAccount(), req.toAccount(), req.amount());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

        var afterFrom = repo.getBalance(req.fromAccount());
        var afterTo = repo.getBalance(req.toAccount());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "before", Map.of(
                        req.fromAccount(), beforeFrom,
                        req.toAccount(), beforeTo
                ),
                "after", Map.of(
                        req.fromAccount(), afterFrom,
                        req.toAccount(), afterTo
                )
        ));
    }

    @GetMapping("/accounts")
    public Map<String, Object> accounts() {
        return Map.of("accounts", repo.all());
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest req) {
        service.createAccount(req.accountId(), req.initialBalance());
        return ResponseEntity.ok(Map.of("status", "account created"));
    }


}
