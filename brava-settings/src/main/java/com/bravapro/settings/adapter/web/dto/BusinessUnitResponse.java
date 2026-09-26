package com.bravapro.settings.adapter.web.dto;

import com.bravapro.settings.domain.model.BusinessUnit;

import java.util.UUID;

public record BusinessUnitResponse(
        UUID id,
        boolean main,
        String tradeName,
        String legalName,
        String documentType,
        String document,
        String municipalRegistration,
        String phone,
        String whatsapp,
        String email,
        String instagram,
        String website,
        String zipCode,
        String street,
        String number,
        String complement,
        String district,
        String city,
        String state,
        String logoUrl) {

    public static BusinessUnitResponse from(BusinessUnit unit) {
        return new BusinessUnitResponse(
                unit.getId(),
                unit.isMain(),
                unit.getTradeName(),
                unit.getLegalName(),
                unit.getDocumentType() != null ? unit.getDocumentType().name() : null,
                unit.getDocument(),
                unit.getMunicipalRegistration(),
                unit.getPhone(),
                unit.getWhatsapp(),
                unit.getEmail(),
                unit.getInstagram(),
                unit.getWebsite(),
                unit.getZipCode(),
                unit.getStreet(),
                unit.getNumber(),
                unit.getComplement(),
                unit.getDistrict(),
                unit.getCity(),
                unit.getState(),
                unit.getLogoUrl());
    }
}
