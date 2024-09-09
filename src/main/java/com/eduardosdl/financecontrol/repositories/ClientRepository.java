package com.eduardosdl.financecontrol.repositories;

import com.eduardosdl.financecontrol.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByCnpj(String cnpj);
    Client findByCpf(String cpf);
}
