package com.eduardosdl.financecontrol.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionDTO(
        @NotBlank(message = "operationType deve ser 'C' ou 'D'") String operationType,
        @NotNull(message = "amount não pode ser null")
        @DecimalMin(value = "0.01", message = "amount deve ser maior que zero")
        BigDecimal amount
) {
}
