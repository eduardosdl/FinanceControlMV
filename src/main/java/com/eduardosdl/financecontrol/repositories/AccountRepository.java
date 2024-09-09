package com.eduardosdl.financecontrol.repositories;

import com.eduardosdl.financecontrol.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByClientId(UUID clientId);
}
