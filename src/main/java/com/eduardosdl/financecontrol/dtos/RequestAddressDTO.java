package com.eduardosdl.financecontrol.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestAddressDTO(
        @NotNull Long addressNumber,
        @NotNull String street,
        @NotNull String neighborhood,
        @NotNull String city,
        @NotNull String state,
        @NotNull @Size(min = 8, max = 8, message = "O campo deve ter 8 dígitos") String zipCode
) {
}
