package com.bravapro.core.application.usecase;

import com.bravapro.core.domain.exception.TenantNotFoundException;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.port.in.DeactivateTenantUseCase;
import com.bravapro.core.domain.port.out.TenantRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeactivateTenantUseCaseImpl implements DeactivateTenantUseCase {

    private final TenantRepository tenantRepository;
    private final PlatformAdminChecker platformAdminChecker;

    @Override
    public void execute(UUID tenantId) {
        platformAdminChecker.check();

        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(TenantNotFoundException::new);

        tenantRepository.save(tenant.toBuilder().active(false).build());

        if (log.isInfoEnabled()) {
            log.info("Tenant desativado: {} ({})", tenant.getId(), tenant.getName());
        }
    }
}
