package com.eduardosdl.financecontrol.services;

import com.eduardosdl.financecontrol.models.reports.ClientBalance;
import com.eduardosdl.financecontrol.models.reports.ClientBalanceDetail;
import com.eduardosdl.financecontrol.models.reports.ClientTransactionInfo;
import com.eduardosdl.financecontrol.repositories.reports.AllClientBalanceRepository;
import com.eduardosdl.financecontrol.repositories.reports.ClientBalanceByPeriodRepository;
import com.eduardosdl.financecontrol.repositories.reports.ClientBalanceRepository;
import com.eduardosdl.financecontrol.repositories.reports.CompanyRevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReportService {
    @Autowired
    private ClientBalanceRepository clientBalanceRepository;

    @Autowired
    private ClientBalanceByPeriodRepository clientBalanceByPeriodRepository;

    @Autowired
    private AllClientBalanceRepository allClientBalanceRepository;

    @Autowired
    private CompanyRevenueRepository companyRevenueRepository;

    public ClientBalanceDetail getReportClientBalance(UUID clientId) {
        try {
            return clientBalanceRepository.execute(clientId);
        } catch (Exception e) {
            throw new RuntimeException("Error to get report client balance");
        }
    }

    public ClientBalanceDetail getReportBalanceClientByPeriod(UUID clientId, LocalDate startDate, LocalDate endDate) {
        try {
            return clientBalanceByPeriodRepository.execute(clientId, startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("Error to get report client balance by period");
        }
    }

    public List<ClientBalance> getReportBalanceAllClients() {
        try {
            return allClientBalanceRepository.execute();
        } catch (Exception e) {
            throw new RuntimeException("Error to get report all client balance ");
        }
    }

    public List<ClientTransactionInfo> gerReportCompanyRevenue(LocalDate startDate, LocalDate endDate) {
        try {
            return companyRevenueRepository.execute(startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("Error to get report company revenue ");
        }
    }
}
