package com.bravapro.settings.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "business_unit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessUnitEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private boolean main;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "document_type")
    private String documentType;

    private String document;

    @Column(name = "municipal_registration")
    private String municipalRegistration;

    private String phone;
    private String whatsapp;
    private String email;
    private String instagram;
    private String website;

    @Column(name = "zip_code")
    private String zipCode;

    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;

    @Column(name = "logo_key")
    private String logoKey;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
