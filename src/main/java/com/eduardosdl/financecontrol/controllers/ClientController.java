package com.eduardosdl.financecontrol.controllers;

import com.eduardosdl.financecontrol.dtos.CreateClientDTO;
import com.eduardosdl.financecontrol.dtos.UpdateClientDTO;
import com.eduardosdl.financecontrol.models.Client;
import com.eduardosdl.financecontrol.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;


    @GetMapping
    private ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Client> get(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @PostMapping
    private ResponseEntity<Client> create(@RequestBody @Valid CreateClientDTO clientData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientData));
    }

    @PutMapping("/{id}")
    private ResponseEntity<Client> update(@PathVariable @Valid UUID id, @RequestBody @Valid UpdateClientDTO clientData) {
        return ResponseEntity.ok(clientService.update(id, clientData));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Objects> delete(@PathVariable @Valid UUID id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
