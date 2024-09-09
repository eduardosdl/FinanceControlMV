package com.eduardosdl.financecontrol.models.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Model para mapear relatorio de receita da empresa
public class ClientTransactionInfo {
    private String clientName;
    private Integer totalTransactions;
    private BigDecimal totalValueTransactions;
}
