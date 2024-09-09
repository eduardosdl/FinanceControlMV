package com.eduardosdl.financecontrol.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "address")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long addressNumber;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @OneToOne(optional = false ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
}
