package com.bravapro.settings.adapter.web.resource;

import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.settings.adapter.web.dto.BrandResponse;
import com.bravapro.settings.domain.port.in.BusinessUnitQueryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Nome + logo da empresa pro cabeçalho: qualquer usuário da assinatura (o cadastro completo é só admin). */
@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandQueryResource implements QueryPermissionAware {

    private final BusinessUnitQueryService businessUnitQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.authenticated();
    }

    @GetMapping
    public ResponseEntity<BrandResponse> get() {
        return ResponseEntity.ok(BrandResponse.from(businessUnitQueryService.getMain()));
    }
}
