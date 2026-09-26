package com.bravapro.settings.infrastructure.persistence.mapper;

import com.bravapro.settings.domain.model.BusinessUnit;
import com.bravapro.settings.domain.model.DocumentType;
import com.bravapro.settings.infrastructure.persistence.entity.BusinessUnitEntity;

import org.springframework.stereotype.Component;

@Component
public class BusinessUnitMapper {

    public BusinessUnit toDomain(BusinessUnitEntity entity) {
        return BusinessUnit.builder()
                .id(entity.getId())
                .main(entity.isMain())
                .tradeName(entity.getTradeName())
                .legalName(entity.getLegalName())
                .documentType(entity.getDocumentType() != null ? DocumentType.valueOf(entity.getDocumentType()) : null)
                .document(entity.getDocument())
                .municipalRegistration(entity.getMunicipalRegistration())
                .phone(entity.getPhone())
                .whatsapp(entity.getWhatsapp())
                .email(entity.getEmail())
                .instagram(entity.getInstagram())
                .website(entity.getWebsite())
                .zipCode(entity.getZipCode())
                .street(entity.getStreet())
                .number(entity.getNumber())
                .complement(entity.getComplement())
                .district(entity.getDistrict())
                .city(entity.getCity())
                .state(entity.getState())
                .logoKey(entity.getLogoKey())
                .logoUrl(entity.getLogoUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public BusinessUnitEntity toEntity(BusinessUnit domain) {
        return BusinessUnitEntity.builder()
                .id(domain.getId())
                .main(domain.isMain())
                .tradeName(domain.getTradeName())
                .legalName(domain.getLegalName())
                .documentType(domain.getDocumentType() != null ? domain.getDocumentType().name() : null)
                .document(domain.getDocument())
                .municipalRegistration(domain.getMunicipalRegistration())
                .phone(domain.getPhone())
                .whatsapp(domain.getWhatsapp())
                .email(domain.getEmail())
                .instagram(domain.getInstagram())
                .website(domain.getWebsite())
                .zipCode(domain.getZipCode())
                .street(domain.getStreet())
                .number(domain.getNumber())
                .complement(domain.getComplement())
                .district(domain.getDistrict())
                .city(domain.getCity())
                .state(domain.getState())
                .logoKey(domain.getLogoKey())
                .logoUrl(domain.getLogoUrl())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
