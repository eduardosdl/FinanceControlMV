package com.eduardosdl.financecontrol.controllers;

import com.eduardosdl.financecontrol.models.reports.ClientBalance;
import com.eduardosdl.financecontrol.models.reports.ClientBalanceDetail;
import com.eduardosdl.financecontrol.models.reports.ClientTransactionInfo;
import com.eduardosdl.financecontrol.services.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientBalanceDetail> getReportClientBalance(@PathVariable @Valid UUID clientId) {
        return ResponseEntity.ok(reportService.getReportClientBalance(clientId));
    }

    @GetMapping("/client/{clientId}/period")
    public ResponseEntity<ClientBalanceDetail> getReportClientBalance(
            @PathVariable @Valid UUID clientId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(reportService.getReportBalanceClientByPeriod(clientId, startDate, endDate));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientBalance>> getReportBalanceAllClients() {
        return ResponseEntity.ok(reportService.getReportBalanceAllClients());
    }

    @GetMapping("/company/period")
    public ResponseEntity<List<ClientTransactionInfo>> getReportRevenueCompany(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {

        List<ClientTransactionInfo> report = reportService.gerReportCompanyRevenue(dataInicio, dataFim);
        return ResponseEntity.ok(report);
    }
}
