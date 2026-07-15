package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.port.in.ListTenantsUseCase;
import com.lashmanager.core.domain.port.out.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListTenantsUseCaseImpl implements ListTenantsUseCase {

    private final TenantRepository tenantRepository;
    private final PlatformAdminChecker platformAdminChecker;

    @Override
    public Page<TenantResult> execute(Pageable pageable) {
        platformAdminChecker.check();
        return tenantRepository.findAll(pageable)
                .map(t -> new TenantResult(t.getId(), t.getName(), t.getSchemaName(), t.isActive(), t.getCreatedAt()));
    }
}
