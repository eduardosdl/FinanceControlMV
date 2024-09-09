package com.eduardosdl.financecontrol.repositories;

import com.eduardosdl.financecontrol.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Address getByClientId(UUID clientId);
}
