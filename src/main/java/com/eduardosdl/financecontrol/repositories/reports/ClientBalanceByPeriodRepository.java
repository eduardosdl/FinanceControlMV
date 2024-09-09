package com.eduardosdl.financecontrol.repositories.reports;

import com.eduardosdl.financecontrol.models.reports.ClientBalanceDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Repository
// busca o relatorio de saldo do cliente por periodo
public class ClientBalanceByPeriodRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ClientBalanceDetail execute(UUID clientId, LocalDate startDate, LocalDate endDate) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("REPORT_CLIENT_BALANCE_BY_PERIOD")
                .registerStoredProcedureParameter(1, UUID.class, ParameterMode.IN)          // Id do cliente
                .registerStoredProcedureParameter(2, Date.class, ParameterMode.IN)          // Data início
                .registerStoredProcedureParameter(3, Date.class, ParameterMode.IN)          // Data fim
                .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)       // Nome
                .registerStoredProcedureParameter(5, Timestamp.class, ParameterMode.OUT)    // Data cadastro
                .registerStoredProcedureParameter(6, String.class, ParameterMode.OUT)       // Endereço
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)      // Movimentações crédito
                .registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT)      // Movimentações débito
                .registerStoredProcedureParameter(9, Integer.class, ParameterMode.OUT)      // Total movimentações
                .registerStoredProcedureParameter(10, BigDecimal.class, ParameterMode.OUT)  // Valor pago
                .registerStoredProcedureParameter(11, BigDecimal.class, ParameterMode.OUT)  // Saldo inicial
                .registerStoredProcedureParameter(12, BigDecimal.class, ParameterMode.OUT); // Saldo atual

        query.setParameter(1, clientId);
        query.setParameter(2, java.sql.Date.valueOf(startDate));
        query.setParameter(3, java.sql.Date.valueOf(endDate));

        query.execute();

        String clientName = (String) query.getOutputParameterValue(4);
        Timestamp createdAt = (Timestamp) query.getOutputParameterValue(5);
        String address = (String) query.getOutputParameterValue(6);
        Integer creditTransactions = (Integer) query.getOutputParameterValue(7);
        Integer debitTransactions = (Integer) query.getOutputParameterValue(8);
        Integer totalTransactions = (Integer) query.getOutputParameterValue(9);
        BigDecimal totalValueTransactions = (BigDecimal) query.getOutputParameterValue(10);
        BigDecimal initialBalance = (BigDecimal) query.getOutputParameterValue(11);
        BigDecimal currentBalance = (BigDecimal) query.getOutputParameterValue(12);

        return new ClientBalanceDetail(clientName, createdAt, address, creditTransactions, debitTransactions, totalTransactions, totalValueTransactions, initialBalance, currentBalance);
    }
}
