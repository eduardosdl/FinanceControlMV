package com.eduardosdl.financecontrol.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateClientDTO(
        @NotNull String name,
        @NotNull @Size(min = 11, max = 11, message = "O campo deve ter 11 dígitos") String cellPhone,
        @NotNull @Pattern(regexp = "\\d{11}|\\d{14}", message = "O campo deve ter 11 ou 14 dígitos") String document,
        @NotNull @Pattern(regexp = "PF|PJ", message = "O campo deve ser 'PF' ou 'PJ'") String clientType,
        @NotNull @Valid RequestAddressDTO address,
        @NotNull String accountNumber,
        @NotNull BigDecimal initialBalance
        ) {
}
