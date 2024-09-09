package com.eduardosdl.financecontrol.services;

import com.eduardosdl.financecontrol.dtos.CreateTransactionDTO;
import com.eduardosdl.financecontrol.dtos.ResponseTransactionDTO;
import com.eduardosdl.financecontrol.exceptions.ValidationException;
import com.eduardosdl.financecontrol.models.Account;
import com.eduardosdl.financecontrol.models.Transaction;
import com.eduardosdl.financecontrol.repositories.AccountRepository;
import com.eduardosdl.financecontrol.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    AccountRepository accountRepository;

    public List<Transaction> getAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all transactions", e);
        }
    }

    public Transaction get(UUID transactionId) {
        try {
            return repository.findById(transactionId)
                    .orElseThrow(() -> new ValidationException("Transaction não encontrado"));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Transaction not found with ID: " + transactionId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transaction with ID: " + transactionId, e);
        }
    }

    public List<ResponseTransactionDTO> getByAccountId(UUID accountId) {
        try {
            List<Transaction> transactions = repository.findByAccountId(accountId);

            return transactions.stream()
                    .map(ResponseTransactionDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transactions for account ID: " + accountId, e);
        }
    }

    public ResponseTransactionDTO create(UUID accountId, CreateTransactionDTO transaction) {
        try {
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

            Transaction newTransaction = new Transaction();

            newTransaction.setAmount(transaction.amount());
            newTransaction.setAccount(account);
            newTransaction.setOperationType(Transaction.OperationType.valueOf(transaction.operationType()));

            return new ResponseTransactionDTO(repository.save(newTransaction));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Error creating transaction: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid operation type provided");
        } catch (Exception e) {
            throw new RuntimeException("Error creating transaction", e);
        }
    }
}
