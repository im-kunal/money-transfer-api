package com.kunal.money_transfer_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank String accountId,
        @NotNull @DecimalMin(value = "0.0") BigDecimal initialBalance
) {}