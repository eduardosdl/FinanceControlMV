package com.eduardosdl.financecontrol.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "client")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 11)
    private String cellPhone;

    @Column(nullable = false, length = 2)
    private String clientType;

    @Column(length = 11, unique = true)
    private String cpf;

    @Column(length = 14, unique = true)
    private String cnpj;

    private LocalDateTime createdAt;
    private Boolean active;

    // insere a data de criação e define o usuário como ativo
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        active = true;
    }
}
