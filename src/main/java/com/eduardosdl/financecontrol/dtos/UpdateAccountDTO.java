package com.eduardosdl.financecontrol.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateAccountDTO(
        @NotNull String accountNumber
) {
}
