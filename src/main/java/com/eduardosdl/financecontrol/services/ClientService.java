package com.eduardosdl.financecontrol.services;

import com.eduardosdl.financecontrol.dtos.CreateAccountDTO;
import com.eduardosdl.financecontrol.dtos.CreateClientDTO;
import com.eduardosdl.financecontrol.dtos.UpdateClientDTO;
import com.eduardosdl.financecontrol.exceptions.ValidationException;
import com.eduardosdl.financecontrol.models.Client;
import com.eduardosdl.financecontrol.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    @Autowired
    AccountService accountService;

    @Autowired
    AddressService addressService;

    public List<Client> getAll() {
        return repository.findAll();
    }

    public Client getById(UUID clientId) {
        try {
            return repository.getReferenceById(clientId);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Cliente não encontrado com ID: " + clientId);
        }
    }

    public Client create(CreateClientDTO client) {
        try {
            var newClient = new Client();

            validateDocumentUniqueness(client.clientType(), client.document());

            checkClientType(client.clientType(), client.document(), newClient);

            newClient.setClientType(client.clientType());
            newClient.setName(client.name());
            newClient.setCellPhone(client.cellPhone());

            repository.save(newClient);

            CreateAccountDTO newAccount = new CreateAccountDTO(
                    client.accountNumber(),
                    client.initialBalance()
            );

            accountService.create(newClient.getId(), newAccount);
            addressService.create(client.address(), newClient.getId());

            return newClient;
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public Client update(UUID clientId, UpdateClientDTO updatedData) {
        try {
            var client = this.getById(clientId);

            validateDocumentUniqueness(client.getClientType(), updatedData.document());
            checkClientType(client.getClientType(), updatedData.document(), client);
            client.setName(updatedData.name());
            client.setCellPhone(updatedData.cellPhone());

            addressService.update(updatedData.address(), client.getId());

            return repository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void delete(UUID clientId) {
        try {
            var client = getById(clientId);

            accountService.deleteClientAccounts(clientId);
            client.setActive(false);

            repository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    private void checkClientType(String type, String docNumber, Client newClient) {
        if (Objects.equals(type, "PJ")) {
            newClient.setCnpj(docNumber);
        } else if (!Objects.equals(type, "PF")) {
            throw new ValidationException("O campo clientType deve ser 'PF' ou 'PJ'");
        } else {
            newClient.setCpf(docNumber);
        }
    }

    private void validateDocumentUniqueness(String clientType, String document) {
        Client existingClient;
        if (Objects.equals(clientType, "PJ")) {
            existingClient = repository.findByCnpj(document);
        } else if (Objects.equals(clientType, "PF")) {
            existingClient = repository.findByCpf(document);
        } else {
            throw new ValidationException("Tipo de cliente inválido para verificação de documento.");
        }

        if (existingClient != null) {
            throw new ValidationException("Já existe um cliente com este " +
                    (Objects.equals(clientType, "PJ") ? "CNPJ" : "CPF") + ".");
        }
    }
}
