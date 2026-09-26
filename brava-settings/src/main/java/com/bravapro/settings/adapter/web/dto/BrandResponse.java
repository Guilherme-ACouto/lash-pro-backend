package com.bravapro.settings.adapter.web.dto;

import com.bravapro.settings.domain.model.BusinessUnit;

/** Nome e logo da empresa pro cabeçalho — visível pra qualquer usuário da assinatura. */
public record BrandResponse(String tradeName, String logoUrl) {

    public static BrandResponse from(BusinessUnit unit) {
        return new BrandResponse(unit.getTradeName(), unit.getLogoUrl());
    }
}
