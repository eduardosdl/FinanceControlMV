package com.eduardosdl.financecontrol.controllers;

import com.eduardosdl.financecontrol.dtos.CreateAccountDTO;
import com.eduardosdl.financecontrol.dtos.UpdateAccountDTO;
import com.eduardosdl.financecontrol.models.Account;
import com.eduardosdl.financecontrol.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable @Valid UUID id, @RequestBody @Valid UpdateAccountDTO newAccountData) {
        return ResponseEntity.ok(accountService.update(id, newAccountData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid UUID id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    private ResponseEntity<List<Account>> getAccounts(@PathVariable @Valid UUID clientId) {
        return ResponseEntity.ok(accountService.getByClient(clientId));
    }

    @PostMapping("/client/{clientId}")
    private ResponseEntity<Account> createAccount(
            @PathVariable @Valid UUID clientId,
            @RequestBody @Valid CreateAccountDTO accountData
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(clientId, accountData));
    }
}
