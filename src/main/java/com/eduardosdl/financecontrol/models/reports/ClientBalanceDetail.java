package com.eduardosdl.financecontrol.models.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Model para mapear relaotorio de movimentações do cliente
public class ClientBalanceDetail {
    private String name;
    private Timestamp createdAt;
    private String address;
    private Integer creditTransactions;
    private Integer debitTransactions;
    private Integer totalTransactions;
    private BigDecimal valueTransactions;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
}
