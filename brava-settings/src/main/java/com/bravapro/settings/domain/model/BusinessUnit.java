package com.bravapro.settings.domain.model;

import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.model.DomainEntity;
import com.bravapro.settings.application.command.UpdateBusinessUnitCommand;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Empresa/filial da assinatura (tabela {@code business_unit} no tenant, como a BusinessUnit da Pontta). */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUnit implements DomainEntity {
    private UUID id;
    private boolean main;
    private String tradeName;
    private String legalName;
    private DocumentType documentType;
    private String document;
    private String municipalRegistration;
    private String phone;
    private String whatsapp;
    private String email;
    private String instagram;
    private String website;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String logoKey;
    private String logoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(UpdateBusinessUnitCommand command) {
        DocumentType type = parseDocumentType(command.getDocumentType());
        String digits = blankToNull(DocumentValidator.digitsOnly(command.getDocument()));
        if (digits != null) {
            if (type == null) {
                throw new BusinessException("Informe se o documento é CPF ou CNPJ");
            }
            if (!DocumentValidator.isValid(type, digits)) {
                throw new BusinessException(type == DocumentType.CPF ? "CPF inválido" : "CNPJ inválido");
            }
        }

        this.tradeName = command.getTradeName().trim();
        this.legalName = blankToNull(command.getLegalName());
        this.documentType = digits != null ? type : null;
        this.document = digits;
        this.municipalRegistration = blankToNull(command.getMunicipalRegistration());
        this.phone = blankToNull(command.getPhone());
        this.whatsapp = blankToNull(command.getWhatsapp());
        this.email = blankToNull(command.getEmail());
        this.instagram = blankToNull(command.getInstagram());
        this.website = blankToNull(command.getWebsite());
        this.zipCode = blankToNull(command.getZipCode());
        this.street = blankToNull(command.getStreet());
        this.number = blankToNull(command.getNumber());
        this.complement = blankToNull(command.getComplement());
        this.district = blankToNull(command.getDistrict());
        this.city = blankToNull(command.getCity());
        this.state = command.getState() == null || command.getState().isBlank()
                ? null
                : command.getState().trim().toUpperCase(Locale.ROOT);
        this.updatedAt = LocalDateTime.now();
    }

    public void changeLogo(String logoKey, String logoUrl) {
        this.logoKey = logoKey;
        this.logoUrl = logoUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void removeLogo() {
        changeLogo(null, null);
    }

    private static DocumentType parseDocumentType(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return DocumentType.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Tipo de documento inválido: use CPF ou CNPJ");
        }
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
