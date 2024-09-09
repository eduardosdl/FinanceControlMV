package com.eduardosdl.financecontrol.repositories.reports;

import com.eduardosdl.financecontrol.models.reports.ClientBalance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
// busca o relatorio de saldo de todos os clientes
public class AllClientBalanceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<ClientBalance> execute() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("REPORT_BALANCE_ALL_CLIENTS")
                .registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR); // Cursor

        query.execute();

        List<Object[]> results = query.getResultList();
        List<ClientBalance> reports = new ArrayList<>();

        for (Object[] row : results) {
            String clientName = (String) row[0];
            Timestamp createdAt = (Timestamp) row[1];
            BigDecimal currentBalance = (BigDecimal) row[2];

            reports.add(new ClientBalance(clientName, createdAt, currentBalance));
        }

        return reports;
    }
}
