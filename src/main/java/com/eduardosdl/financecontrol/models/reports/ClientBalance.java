package com.eduardosdl.financecontrol.models.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Model para mapear relatorio de saldo dos clientes
public class ClientBalance {
    private String clientName;
    private Timestamp createAt;
    private BigDecimal currentBalance;
}
