package com.eduardosdl.financecontrol.dtos;

import com.eduardosdl.financecontrol.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseTransactionDTO (
        UUID id,
        BigDecimal amount,
        String operationType,
        UUID accountId,
        LocalDateTime createdAt
) {
    public ResponseTransactionDTO(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getOperationType().name(),
                transaction.getAccount().getId(), // Retorna apenas o ID da conta
                transaction.getCreatedAt()
        );
    }
}
