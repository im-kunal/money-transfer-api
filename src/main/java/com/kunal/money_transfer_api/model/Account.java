package com.kunal.money_transfer_api.model;

import java.math.BigDecimal;

public record Account(String id, BigDecimal balance) {}
