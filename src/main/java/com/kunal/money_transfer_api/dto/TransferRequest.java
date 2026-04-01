package com.kunal.money_transfer_api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull String fromAccount,
        @NotNull String toAccount,
        @NotNull BigDecimal amount
) {
}
