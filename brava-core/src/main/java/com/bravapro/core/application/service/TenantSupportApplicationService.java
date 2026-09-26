package com.bravapro.core.application.service;

import com.bravapro.core.application.command.EnterTenantCommand;
import com.bravapro.core.domain.exception.TenantNotFoundException;
import com.bravapro.core.domain.model.SupportSession;
import com.bravapro.core.domain.port.in.TenantSupportUseCase;
import com.bravapro.core.domain.port.out.TenantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantSupportApplicationService {

    private final TenantSupportUseCase tenantSupportUseCase;
    private final TenantRepository tenantRepository;

    public SupportSession when(EnterTenantCommand command) {
        return tenantSupportUseCase.enter(tenantRepository
                .findById(command.getTenantId())
                .orElseThrow(TenantNotFoundException::new));
    }
}
