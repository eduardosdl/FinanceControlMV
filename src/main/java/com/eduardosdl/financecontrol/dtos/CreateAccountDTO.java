package com.eduardosdl.financecontrol.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountDTO(
        @NotNull String accountNumber,
        @NotNull BigDecimal initialBalance
) {
}
