package com.eduardosdl.financecontrol.repositories.reports;

import com.eduardosdl.financecontrol.models.reports.ClientBalanceDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Repository
// busca o relatorio de saldo do cliente
public class ClientBalanceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ClientBalanceDetail execute(UUID clientId) {
        // armazena procedure e cada parametro de envio e retorno
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("REPORT_CLIENT_BALANCE")
                .registerStoredProcedureParameter(1, UUID.class, ParameterMode.IN)          // id do cliente
                .registerStoredProcedureParameter(2, String.class, ParameterMode.OUT)       // Nome
                .registerStoredProcedureParameter(3, Timestamp.class, ParameterMode.OUT)    // Data cadastro
                .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)       // Endereço
                .registerStoredProcedureParameter(5, Integer.class, ParameterMode.OUT)      // Movimentações crédito
                .registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT)      // Movimentações débito
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)      // Total movimentações
                .registerStoredProcedureParameter(8, BigDecimal.class, ParameterMode.OUT)   // Valor pago
                .registerStoredProcedureParameter(9, BigDecimal.class, ParameterMode.OUT)   // Saldo inicial
                .registerStoredProcedureParameter(10, BigDecimal.class, ParameterMode.OUT); // Saldo atual

        // define os parametros necessarios para a procedure
        query.setParameter(1, clientId);

        query.execute();

        // captura o retorno de cada parametro e armazena para construir o retorno
        String clientName = (String) query.getOutputParameterValue(2);
        Timestamp createdAt = (Timestamp) query.getOutputParameterValue(3);
        String address = (String) query.getOutputParameterValue(4);
        Integer creditTransactions = (Integer) query.getOutputParameterValue(5);
        Integer debitTransactions = (Integer) query.getOutputParameterValue(6);
        Integer totalTransactions = (Integer) query.getOutputParameterValue(7);
        BigDecimal valueTransactions = (BigDecimal) query.getOutputParameterValue(8);
        BigDecimal initialBalance = (BigDecimal) query.getOutputParameterValue(9);
        BigDecimal currentBalance = (BigDecimal) query.getOutputParameterValue(10);

        return new ClientBalanceDetail(clientName, createdAt, address, creditTransactions, debitTransactions, totalTransactions, valueTransactions, initialBalance, currentBalance);
    }
}
