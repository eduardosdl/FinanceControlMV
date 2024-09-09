package com.eduardosdl.financecontrol.services;

import com.eduardosdl.financecontrol.dtos.CreateAccountDTO;
import com.eduardosdl.financecontrol.dtos.UpdateAccountDTO;
import com.eduardosdl.financecontrol.exceptions.ValidationException;
import com.eduardosdl.financecontrol.models.Account;
import com.eduardosdl.financecontrol.models.Client;
import com.eduardosdl.financecontrol.repositories.AccountRepository;
import com.eduardosdl.financecontrol.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Account> getAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            // Log exception and handle accordingly
            throw new RuntimeException("Failed to fetch accounts", e);
        }
    }

    public List<Account> getByClient(UUID clientId) {
        try {
            return repository.findByClientId(clientId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch accounts for client ID: " + clientId, e);
        }
    }

    public Account create(UUID clientId, CreateAccountDTO accountData) {
        try {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new ValidationException("Cliente não encontrado"));

            Account newAccount = new Account();

            newAccount.setClient(client);
            newAccount.setAccountNumber(accountData.accountNumber());
            newAccount.setBalance(accountData.initialBalance());
            newAccount.setInitialBalance(accountData.initialBalance());

            return repository.save(newAccount);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create account for client ID: " + clientId, e);
        }
    }

    public Account update(UUID accountId, UpdateAccountDTO newAccountData) {
        try {
            Account account = repository.getReferenceById(accountId);

            account.setAccountNumber(newAccountData.accountNumber());

            return repository.save(account);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update account ID: " + accountId, e);
        }
    }

    // exclui logicamente a conta
    public void delete(UUID accountId) {
        try {
            Account account = repository.getReferenceById(accountId);

            account.setActive(false);

            repository.save(account);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete account ID: " + accountId, e);
        }
    }

    // exclui logicamente todas as contas de um cliente
    public void deleteClientAccounts(UUID clientId) {
        try {
            List<Account> accounts = repository.findByClientId(clientId);

            for (Account account : accounts) {
                account.setActive(false);
                repository.save(account);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete accounts for client ID: " + clientId, e);
        }
    }
}
