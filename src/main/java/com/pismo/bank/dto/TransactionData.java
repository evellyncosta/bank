package com.pismo.bank.dto;

import java.math.BigDecimal;

public record TransactionData(Long account_id, Long operation_type_id, BigDecimal amount) {
}
