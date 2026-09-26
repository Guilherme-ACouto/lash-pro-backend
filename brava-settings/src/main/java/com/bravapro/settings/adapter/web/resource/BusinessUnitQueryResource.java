package com.bravapro.settings.adapter.web.resource;

import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.settings.adapter.web.dto.BusinessUnitResponse;
import com.bravapro.settings.domain.port.in.BusinessUnitQueryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Só leitura — comando em {@link BusinessUnitResource} (mesma URL base). Cadastro completo: só admin. */
@RestController
@RequestMapping("/api/settings/business-unit")
@RequiredArgsConstructor
public class BusinessUnitQueryResource implements QueryPermissionAware {

    private final BusinessUnitQueryService businessUnitQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.admin();
    }

    @GetMapping
    public ResponseEntity<BusinessUnitResponse> getMain() {
        return ResponseEntity.ok(BusinessUnitResponse.from(businessUnitQueryService.getMain()));
    }
}
