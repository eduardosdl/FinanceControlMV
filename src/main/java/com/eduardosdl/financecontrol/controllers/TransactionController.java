package com.eduardosdl.financecontrol.controllers;

import com.eduardosdl.financecontrol.dtos.CreateTransactionDTO;
import com.eduardosdl.financecontrol.dtos.ResponseTransactionDTO;
import com.eduardosdl.financecontrol.models.Transaction;
import com.eduardosdl.financecontrol.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(transactionService.get(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<ResponseTransactionDTO>> getTransactions(@PathVariable @Valid UUID accountId) {
        return ResponseEntity.ok(transactionService.getByAccountId(accountId));
    }

    @PostMapping("/account/{accountId}")
    public ResponseEntity<ResponseTransactionDTO> createTransaction(
            @PathVariable @Valid UUID accountId,
            @RequestBody @Valid CreateTransactionDTO transaction
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(accountId, transaction));
    }
}
