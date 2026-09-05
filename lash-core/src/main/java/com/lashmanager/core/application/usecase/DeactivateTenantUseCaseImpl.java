package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.exception.TenantNotFoundException;
import com.lashmanager.core.domain.model.Tenant;
import com.lashmanager.core.domain.port.in.DeactivateTenantUseCase;
import com.lashmanager.core.domain.port.out.TenantRepository;
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

    tenantRepository.save(
        Tenant.builder()
            .id(tenant.getId())
            .name(tenant.getName())
            .schemaName(tenant.getSchemaName())
            .active(false)
            .createdAt(tenant.getCreatedAt())
            .build());

    if (log.isInfoEnabled()) {
      log.info("Tenant desativado: {} ({})", tenant.getId(), tenant.getName());
    }
  }
}
