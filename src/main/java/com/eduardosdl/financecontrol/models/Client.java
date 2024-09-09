package com.eduardosdl.financecontrol.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "client")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        active = true;
    }
}
