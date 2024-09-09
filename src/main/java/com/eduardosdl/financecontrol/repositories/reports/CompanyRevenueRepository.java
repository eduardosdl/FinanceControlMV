package com.eduardosdl.financecontrol.repositories.reports;

import com.eduardosdl.financecontrol.models.reports.ClientTransactionInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
// busca o relatorio de receita da empresa
public class CompanyRevenueRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<ClientTransactionInfo> execute(LocalDate dataInicio, LocalDate dataFim) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("REPORT_COMPANY_REVENUE")
                .registerStoredProcedureParameter(1, Date.class, ParameterMode.IN)          // Data início
                .registerStoredProcedureParameter(2, Date.class, ParameterMode.IN)          // Data fim
                .registerStoredProcedureParameter(3, void.class, ParameterMode.REF_CURSOR); // Cursor

        query.setParameter(1, java.sql.Date.valueOf(dataInicio));
        query.setParameter(2, java.sql.Date.valueOf(dataFim));

        query.execute();

        List<Object[]> results = query.getResultList();
        List<ClientTransactionInfo> reports = new ArrayList<>();

        for (Object[] row : results) {
            String clientName = (String) row[0];
            Integer totalTransactions = ((BigDecimal) row[1]).intValue();
            BigDecimal totalValueTransactions = (BigDecimal) row[2];

            reports.add(new ClientTransactionInfo(clientName, totalTransactions, totalValueTransactions));
        }

        return reports;
    }
}
